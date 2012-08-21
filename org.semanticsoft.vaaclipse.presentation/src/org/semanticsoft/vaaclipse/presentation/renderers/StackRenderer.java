/*******************************************************************************
 * Copyright (c) 2011 Rushan R. Gilmullin and others.
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

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.presentation.utils.Utils;
import org.semanticsoft.vaaclipse.presentation.widgets.StackWidget;
import org.semanticsoft.vaaclipse.presentation.widgets.StackWidget.StateListener;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;


@SuppressWarnings("restriction")
public class StackRenderer extends GenericRenderer {
	
	@PostConstruct
	public void postConstruct(EventBroker eventBroker)
	{
		EventHandler tagListener = new EventHandler() {
			@Override
			public void handleEvent(Event event) {

				Object changedObj = event.getProperty(EventTags.ELEMENT);
				String eventType = (String) event.getProperty(UIEvents.EventTags.TYPE);
				String tag = (String) event.getProperty(UIEvents.EventTags.NEW_VALUE);
				
				int location = modelService.getElementLocation((MUIElement) changedObj);
				if (!(changedObj instanceof MPartStack) || location == EModelService.IN_SHARED_AREA) {
					return;
				}
				
				final MPartStack stack = (MPartStack) changedObj;
				StackWidget stackWidget = (StackWidget)stack.getWidget();
				
				if (UIEvents.EventTypes.ADD.equals(eventType)) {
					if (IPresentationEngine.MINIMIZED.equals(tag)) {
						stackWidget.setState(-1);
					} else if (IPresentationEngine.MAXIMIZED.equals(tag)) {
						stackWidget.setState(1);
					}
				} else if (UIEvents.EventTypes.REMOVE.equals(eventType)) {
					stackWidget.setState(0);
				}
			}
		};
		
		eventBroker.subscribe(UIEvents.ApplicationElement.TOPIC_TAGS, tagListener);
	}
	
	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		StackWidget stackWidget = new StackWidget();
		stackWidget.setSizeFull();
		element.setWidget(stackWidget);
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> container) {
		TabSheet parentPane = (TabSheet) container.getWidget();

		for (MUIElement element : container.getChildren()) {
			MUILabel mLabel;
			if (element instanceof MPlaceholder)
				mLabel = (MUILabel) ((MPlaceholder) element).getRef();
			else
				mLabel = (MUILabel) element;
			
			boolean closable = false;
			if (mLabel instanceof MPart)
				closable = ((MPart) mLabel).isCloseable();
			
			Resource icon = mLabel.getIconURI() != null ? new ThemeResource(Utils.convertPath(mLabel.getIconURI())) : null;
			Tab tab = parentPane.addTab((com.vaadin.ui.Component) element.getWidget(), mLabel.getLocalizedLabel(), icon);
			tab.setClosable(closable);
		}
	}
	
	@Override
	public void hookControllerLogic(final MUIElement element)
	{
		int location = modelService.getElementLocation(element);
		if (location != EModelService.IN_SHARED_AREA) //if the stack not in shared area
		{
			((StackWidget)element.getWidget()).addStateListener(new StateListener() {
				
				@Override
				public void stateChanged(int newState, int oldState)
				{
					if (oldState == 0 && newState == 1)
						setState(element, IPresentationEngine.MAXIMIZED);
					else if (oldState == 1 && newState == 0)
						setState(element, null);
					else if (oldState == -1 && newState == 0)
						setState(element, null);
					else if (oldState == 0 && newState == -1)
						setState(element, IPresentationEngine.MINIMIZED);
				}
				
				private void setState(MUIElement element, String state) {
					element.getTags().remove(IPresentationEngine.MINIMIZED_BY_ZOOM);
					if (IPresentationEngine.MINIMIZED.equals(state)) {
						element.getTags().remove(IPresentationEngine.MAXIMIZED);
						element.getTags().add(IPresentationEngine.MINIMIZED);
					} else if (IPresentationEngine.MAXIMIZED.equals(state)) {
						element.getTags().remove(IPresentationEngine.MINIMIZED);
						element.getTags().add(IPresentationEngine.MAXIMIZED);
					} else {
						element.getTags().remove(IPresentationEngine.MINIMIZED);
						element.getTags().remove(IPresentationEngine.MAXIMIZED);
					}
				}
			});	
		}
		else
		{
			System.out.println("in shared area");
		}
	}

	@Override
	public void setVisible(MUIElement changedElement, boolean visible) {
		TabSheet tabPane = (TabSheet) changedElement.getWidget();
		tabPane.requestRepaintRequests();
		tabPane.getParent().requestRepaintRequests();
		tabPane.setVisible(visible);
	}

}
