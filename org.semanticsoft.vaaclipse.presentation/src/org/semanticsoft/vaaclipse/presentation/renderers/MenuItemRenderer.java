/*******************************************************************************
 * Copyright (c) 2012 Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *******************************************************************************/

package org.semanticsoft.vaaclipse.presentation.renderers;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MOpaqueMenuItem;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.publicapi.resources.BundleResource;

import com.vaadin.terminal.Resource;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

@SuppressWarnings("restriction")
public class MenuItemRenderer extends ItemRenderer {

	@Inject
	IContributionFactory contributionFactory;
	
	@Inject
	IEventBroker eventBroker;
	
	private EventHandler itemUpdater = new EventHandler() {
		public void handleEvent(Event event) {
			// Ensure that this event is for a MMenuItem
			if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MMenuItem))
				return;

			MMenuItem itemModel = (MMenuItem) event
					.getProperty(UIEvents.EventTags.ELEMENT);

			MenuItem ici = (MenuItem) itemModel.getWidget();
			if (ici == null) {
				return;
			}

			String attName = (String) event.getProperty(UIEvents.EventTags.ATTNAME);
			String newValue = (String) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			if (UIEvents.UILabel.LABEL.equals(attName)) {
				ici.setText(newValue);
			} else if (UIEvents.UILabel.ICONURI.equals(attName)) {
				Resource icon = BundleResource.valueOf(newValue);
				ici.setIcon(icon);
			} else if (UIEvents.UILabel.TOOLTIP.equals(attName)) {
				ici.setDescription(newValue);
			}
		}
	};
	
	private EventHandler enabledUpdater = new EventHandler() {
		public void handleEvent(Event event) {
			// Ensure that this event is for a MMenuItem
			if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MMenuItem))
				return;

			MMenuItem itemModel = (MMenuItem) event
					.getProperty(UIEvents.EventTags.ELEMENT);
			MenuItem ici = (MenuItem) itemModel.getWidget();
			if (ici != null) {
				Boolean newValue = (Boolean) event.getProperty(UIEvents.EventTags.NEW_VALUE);
				ici.setEnabled(newValue);
			}
		}
	};
	
	@PostConstruct
	public void postConstruct()
	{
		eventBroker.subscribe(UIEvents.UILabel.TOPIC_ALL, itemUpdater);
		eventBroker.subscribe(UIEvents.Item.TOPIC_ENABLED, enabledUpdater);
	}
	
	@PreDestroy
	public void preDestroy()
	{
		eventBroker.unsubscribe(itemUpdater);
		eventBroker.unsubscribe(enabledUpdater);
	}

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		
		if (!element.isToBeRendered())
			return;
		
		if (element instanceof MMenuItem) {
			MMenuItem model = (MMenuItem) element;
			
			//--------------------
			//Prepare the text
			String text = prepareText(model);
			text = text.replaceAll("&", "");
			
			//Prepare the icon
			Resource icon = model.getIconURI() != null ? BundleResource.valueOf(model.getIconURI()) : null;
			
			//Prepare the command
			Command command = null;
			if (model instanceof MDirectMenuItem) {
				final MDirectMenuItem item = (MDirectMenuItem) model;
				item.setObject(contributionFactory.create(item.getContributionURI(), getContext(item)));
				
				command = createEventHandler(item);
			} else if (model instanceof MHandledMenuItem) {
				final MHandledMenuItem item = (MHandledMenuItem) model;
				command = createParametrizedCommandEventHandler(item);
			}
			
			MUIElement nextRenderableAndVisible = findNextRendarableAndVisible(element, parent);
			MenuItem item = null;
			if (nextRenderableAndVisible == null)
				item = ((MenuItem)parent.getWidget()).addItem(text, icon, command);
			else
				item = ((MenuItem)parent.getWidget()).addItemBefore(text, icon, command, (MenuItem)nextRenderableAndVisible.getWidget());
			//-----------------

			element.setWidget(item);
			
			updateItemEnablement(model);
			item.setEnabled(model.isEnabled());
			
			registerEnablementUpdaters(model);
		}
	}
	
	protected void updateItemEnablement(MItem item) {
		if (!(item.getWidget() instanceof MenuItem))
			return;

		MenuItem widget = (MenuItem) item.getWidget();
		if (widget == null)
			return;
		
		item.setEnabled(canExecute(item));
	}

	private boolean canExecute(MItem item)
	{
		if (item instanceof MHandledItem)
			return canExecuteItem((MHandledItem) item);
		else if (item instanceof MDirectMenuItem)
			return canExecuteItem((MDirectMenuItem) item);
		else
			return false;
	}
	
	private boolean canExecuteItem(MDirectMenuItem item) {
		final IEclipseContext eclipseContext = getContext(item);
		eclipseContext.set(MItem.class, item);
		setupContext(eclipseContext, item);
		return (boolean) ContextInjectionFactory.invoke(item, CanExecute.class, eclipseContext, true);
	}

	@Override
	public void hookControllerLogic(MUIElement me) {
		//the listener already attached (when created - vaadin API issue)
	}

	@Override
	protected void setupContext(IEclipseContext context, MItem item)
	{
		context.set(MMenuItem.class, (MMenuItem)item);
		
		if (item instanceof MDirectMenuItem)
			context.set(MDirectMenuItem.class, (MDirectMenuItem)item);
		else if (item instanceof MHandledMenuItem)
			context.set(MHandledMenuItem.class, (MHandledMenuItem)item);
		else if (item instanceof MOpaqueMenuItem)
			context.set(MOpaqueMenuItem.class, (MOpaqueMenuItem)item);
	}
	
	@Override
	public void setVisible(MUIElement changedElement, boolean visible)
	{
		((MenuItem)changedElement.getWidget()).setVisible(visible);
	}
}
