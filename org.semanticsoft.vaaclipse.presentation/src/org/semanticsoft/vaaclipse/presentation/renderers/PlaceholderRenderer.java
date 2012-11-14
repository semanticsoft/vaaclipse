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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.UIEvents.UIElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.presentation.utils.HierarchyUtils;
import org.semanticsoft.vaaclipse.widgets.StackWidget;
import org.semanticsoft.vaaclipse.widgets.StackWidget.StateListener;
import org.semanticsoft.vaadinaddons.boundsinfo.BoundsinfoVerticalLayout;

import com.vaadin.ui.Component;

/**
 * @author rushan
 *
 */
public class PlaceholderRenderer extends GenericRenderer
{
	private static Map<MUIElement, List<MPlaceholder>> renderedMap = new HashMap<MUIElement, List<MPlaceholder>>();
	
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

		List<MPlaceholder> renderedRefs = renderedMap.get(ref);
		if (renderedRefs == null) {
			renderedRefs = new ArrayList<MPlaceholder>();
			renderedMap.put(ref, renderedRefs);
		}

		if (!renderedRefs.contains(ph))
			renderedRefs.add(ph);

		//VerticalLayout phComp = new VerticalLayout();
		BoundsinfoVerticalLayout phComp = new BoundsinfoVerticalLayout();
		phComp.setSizeFull();
		phComp.setMargin(false);
		ph.setWidget(phComp);
		
		Component refWidget = (Component) ref.getWidget();
		if (refWidget == null) {
			ref.setToBeRendered(true);
			refWidget = (Component) renderingEngine.createGui(ref);
		} //only create widget, not connect to parent - the connecting reference widgets will be when concrete perspective will be set
//		else 
//		{
//			if (refWidget.getParent() != phComp) {
//				phComp.addComponent(refWidget);
//			}
//		}

//		if (ref instanceof MContext) {
//			IEclipseContext context = ((MContext) ref).getContext();
//			IEclipseContext newParentContext = getContext(ph);
//			if (context.getParent() != newParentContext) {
//				context.setParent(newParentContext);
//			}
//		}
	}

	private EventHandler widgetListener = new EventHandler() {
		public void handleEvent(Event event)
		{
			final MUIElement changedElement = (MUIElement) event.getProperty(EventTags.ELEMENT);
			if (!(changedElement instanceof MPartStack))
				return;
			
			int loc = modelService.getElementLocation(changedElement);
			if (loc != EModelService.IN_SHARED_AREA)
				return;
			
			MArea areaModel = findArea(changedElement);
			if (areaModel != null)
			{
				refreshState(areaModel);
			}
		}

		private MArea findArea(MUIElement element)
		{
			MUIElement parent = element.getParent();
			while (parent != null)
			{
				if (parent instanceof MArea)
					return (MArea) parent;
				parent = parent.getParent();
			}
			return null;
		}
	};
	
	EventHandler tagListener = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			Object changedObj = event.getProperty(EventTags.ELEMENT);
			String eventType = (String) event.getProperty(UIEvents.EventTags.TYPE);
			String tag = (String) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			
			if (!(changedObj instanceof MPlaceholder)) {
				return;
			}
			
			System.out.println("fsf");
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
		eventBroker.subscribe(UIElement.TOPIC_WIDGET, widgetListener);
		eventBroker.subscribe(UIEvents.ApplicationElement.TOPIC_TAGS, tagListener);
	}
	
	/**
	 * This method find the top right part stack (located in top right corner)
	 * and apply its maximuze and minimize buttons to entire area. The hide the
	 * maximize and minimize buttons ot other stacks in this area. So, there are
	 * implemented the eclipse3-like approach to manage area (in e4 swt renderer
	 * is used other way - if more than one stack is located in area, the top
	 * container-tabfolder is created for entire area and buttons of this
	 * container folder is used - i found this way is too heavy-weight).
	 */
	@SuppressWarnings("restriction")
	private void refreshState(final MArea area)
	{
		MPartStack topLeftStak = HierarchyUtils.findTopLeftFolder(area);
		if (topLeftStak != null)
		{
			StackWidget topLeftStackWidget = (StackWidget) topLeftStak.getWidget();
			if (topLeftStackWidget != null)
			{
				topLeftStackWidget.setMaximizeEnabled(true);
				topLeftStackWidget.setMinimizeEnabled(true);
				topLeftStackWidget.removeAllStateListeners();
				
				topLeftStackWidget.addStateListener(new StateListener() {
					
					@Override
					public void stateChanged(int newState, int oldState)
					{
						MPlaceholder ph = area.getCurSharedRef();
						if (oldState == 0 && newState == 1)
							setState(ph, IPresentationEngine.MAXIMIZED);
						else if (oldState == 1 && newState == 0)
							setState(ph, null);
						else if (oldState == -1 && newState == 0)
						{
							ph.getTags().remove(IPresentationEngine.MINIMIZED_BY_ZOOM);
							ph.getTags().remove(IPresentationEngine.MINIMIZED);
						}
						else if (oldState == 0 && newState == -1)
							setState(ph, IPresentationEngine.MINIMIZED);
					}

					private void setState(MUIElement element, String state)
					{
						element.getTags().remove(IPresentationEngine.MINIMIZED_BY_ZOOM);
						if (IPresentationEngine.MINIMIZED.equals(state))
						{
							element.getTags().remove(IPresentationEngine.MAXIMIZED);
							element.getTags().add(IPresentationEngine.MINIMIZED);
						}
						else if (IPresentationEngine.MAXIMIZED.equals(state))
						{
							element.getTags().remove(IPresentationEngine.MINIMIZED);
							element.getTags().add(IPresentationEngine.MAXIMIZED);
						}
						else
						{
							element.getTags().remove(IPresentationEngine.MINIMIZED);
							element.getTags().remove(IPresentationEngine.MAXIMIZED);
						}
					}
				});
			}

			List<MPartStack> stacks = modelService.findElements(area, null, MPartStack.class, null);

			for (MPartStack stack : stacks)
			{
				if (stack.isToBeRendered() && stack != topLeftStak)
				{
					StackWidget stackWidget = (StackWidget) stack.getWidget();
					if (stackWidget != null)
					{
						stackWidget.setMaximizeEnabled(false);
						stackWidget.setMinimizeEnabled(false);
						stackWidget.removeAllStateListeners();
					}
				}
			}
		}
	}
}
