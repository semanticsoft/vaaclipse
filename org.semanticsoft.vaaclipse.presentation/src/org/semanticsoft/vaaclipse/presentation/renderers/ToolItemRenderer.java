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

import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.presentation.utils.Utils;
import org.semanticsoft.vaaclipse.widgets.TwoStateToolbarButton;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.NativeButton;

@SuppressWarnings("restriction")
public class ToolItemRenderer extends ItemRenderer
{
	@Inject
	IContributionFactory contributionFactory;
	
	@Inject
	EventBroker eventBroker;
	
	private EventHandler itemUpdater = new EventHandler() {
		public void handleEvent(Event event) {
			// Ensure that this event is for a MMenuItem
			if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MToolBarElement))
				return;

			MToolBarElement itemModel = (MToolBarElement) event
					.getProperty(UIEvents.EventTags.ELEMENT);

			Button ici = (Button) itemModel.getWidget();
			if (ici == null) {
				return;
			}

			String attName = (String) event.getProperty(UIEvents.EventTags.ATTNAME);
			String newValue = (String) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			if (UIEvents.UILabel.LABEL.equals(attName)) {
				ici.setCaption(newValue);
			} else if (UIEvents.UILabel.ICONURI.equals(attName)) {
				Resource icon = new ThemeResource(Utils.convertPath(newValue));
				ici.setIcon(icon);
			} else if (UIEvents.UILabel.TOOLTIP.equals(attName)) {
				ici.setDescription(newValue);
			}
		}
	};
	
	private EventHandler toBeRenderedUpdater = new EventHandler() {
		public void handleEvent(Event event) {
			// Ensure that this event is for a MMenuItem
			if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MToolBarElement))
				return;

			MToolBarElement itemModel = (MToolBarElement) event
					.getProperty(UIEvents.EventTags.ELEMENT);
			String attName = (String) event
					.getProperty(UIEvents.EventTags.ATTNAME);
			
			if (UIEvents.UIElement.VISIBLE.equals(attName)) {
				Button ici = (Button) itemModel.getWidget();
				
				if (ici == null) {
					return;
				}
				ici.setVisible(itemModel.isVisible());
			}
		}
	};

	private EventHandler selectionUpdater = new EventHandler() {
		public void handleEvent(Event event) {
			// Ensure that this event is for a MToolItem
			if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MToolBarElement))
				return;

			MToolBarElement itemModel = (MToolBarElement) event.getProperty(UIEvents.EventTags.ELEMENT);
			if (itemModel.getWidget() instanceof TwoStateToolbarButton)
			{
				TwoStateToolbarButton button = (TwoStateToolbarButton) itemModel.getWidget();
				Boolean newValue = (Boolean) event.getProperty(UIEvents.EventTags.NEW_VALUE);
				button.setState(newValue);
			}
		}
	};

	private EventHandler enabledUpdater = new EventHandler() {
		public void handleEvent(Event event) {
			// Ensure that this event is for a MMenuItem
			if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MToolBarElement))
				return;

			MToolBarElement itemModel = (MToolBarElement) event
					.getProperty(UIEvents.EventTags.ELEMENT);
			Button ici = (Button) itemModel.getWidget();
			Boolean newValue = (Boolean) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			ici.setEnabled(newValue);
		}
	};

	@PostConstruct
	public void init() {
		eventBroker.subscribe(UIEvents.UILabel.TOPIC_ALL, itemUpdater);
		eventBroker.subscribe(UIEvents.Item.TOPIC_SELECTED, selectionUpdater);
		eventBroker.subscribe(UIEvents.Item.TOPIC_ENABLED, enabledUpdater);
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_ALL, toBeRenderedUpdater);
	}

	@PreDestroy
	public void contextDisposed() {
		eventBroker.unsubscribe(itemUpdater);
		eventBroker.unsubscribe(selectionUpdater);
		eventBroker.unsubscribe(enabledUpdater);
		eventBroker.unsubscribe(toBeRenderedUpdater);
	}

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent)
	{
		if (element instanceof MHandledToolItem || element instanceof MDirectToolItem)
		{
			MToolItem item = (MToolItem) element;

			NativeButton button = new NativeButton();
			button.addStyleName("vaadock-toolbar-button");

			if (item.getIconURI() != null)
			{
				Resource icon = new ThemeResource(Utils.convertPath(item.getIconURI()));
				button.setIcon(icon);
			}
			if (item.getTooltip() != null)
			{
				button.setDescription(item.getLocalizedTooltip());
			}
			element.setWidget(button);
		}
	}

	@Override
	public void hookControllerLogic(MUIElement me)
	{
		if (me instanceof MDirectToolItem)
		{
			final MDirectToolItem item = (MDirectToolItem) me;
			item.setObject(contributionFactory.create(item.getContributionURI(), getContext(item)));

			Button button = (Button) item.getWidget();
			button.addListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event)
				{
					processAction(item);
				}
			});
		}
		else if (me instanceof MHandledToolItem)
		{
			final MHandledItem item = (MHandledToolItem) me;

			Button button = (Button) item.getWidget();
			button.addListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event)
				{
					processParametrizedAction(item);
				}
			});
		}
	}
}
