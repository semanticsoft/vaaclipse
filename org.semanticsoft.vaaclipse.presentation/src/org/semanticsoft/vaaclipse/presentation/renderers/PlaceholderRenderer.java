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

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.presentation.utils.HierarchyUtils;
import org.semanticsoft.vaaclipse.widgets.StackWidget;
import org.semanticsoft.vaadinaddons.boundsinfo.BoundsinfoVerticalLayout;

import com.vaadin.ui.Component;

/**
 * @author rushan
 *
 */
public class PlaceholderRenderer extends VaadinRenderer
{	
	@Inject
	IPresentationEngine renderingEngine;
	
	@Inject
	private IEventBroker eventBroker;
	
	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent)
	{
		MPlaceholder ph = (MPlaceholder) element;
		final MUIElement ref = ph.getRef();
		ref.setCurSharedRef(ph);
		
		//VerticalLayout phComp = new VerticalLayout();
		BoundsinfoVerticalLayout phComp = new BoundsinfoVerticalLayout();
		phComp.setEnableBoundsUpdate(false);
		phComp.setSizeFull();
		phComp.setMargin(false);
		ph.setWidget(phComp);
		
		Component refWidget = (Component) ref.getWidget();
		if (refWidget == null) {
			ref.setToBeRendered(true);
			refWidget = (Component) renderingEngine.createGui(ref);
		}
		
		if (refWidget.getParent() != phComp) {
			phComp.addComponent(refWidget);
		}

		if (ref instanceof MContext) {
			IEclipseContext context = ((MContext) ref).getContext();
			IEclipseContext newParentContext = getContext(ph);
			if (context.getParent() != newParentContext) {
				context.setParent(newParentContext);
			}
		}
	}
	
	EventHandler tagListener = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			Object changedObj = event.getProperty(EventTags.ELEMENT);
			String eventType = (String) event.getProperty(UIEvents.EventTags.TYPE);
			String tag = (String) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			
			if (!(changedObj instanceof MPlaceholder)) {
				return;
			}
			
			final MPlaceholder ph = (MPlaceholder) changedObj;
			MPartStack topLeftStack = HierarchyUtils.findTopLeftFolder(ph.getRef());
			if (topLeftStack != null)
			{
				StackWidget stackWidget = (StackWidget)topLeftStack.getWidget();
				
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
		}
	};

	@PostConstruct
	void postConstruct()
	{
		eventBroker.subscribe(UIEvents.ApplicationElement.TOPIC_TAGS, tagListener);
	}
	
	@PreDestroy
	void preDestroy()
	{
		eventBroker.unsubscribe(tagListener);
	}
}
