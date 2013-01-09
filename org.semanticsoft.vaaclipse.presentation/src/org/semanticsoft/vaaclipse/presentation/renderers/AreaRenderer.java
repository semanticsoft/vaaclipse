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

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.api.VaadinExecutorService;
import org.semanticsoft.vaaclipse.presentation.utils.HierarchyUtils;
import org.semanticsoft.vaaclipse.widgets.StackWidget;
import org.semanticsoft.vaaclipse.widgets.StackWidget.StateListener;
import org.semanticsoft.vaadinaddons.boundsinfo.BoundsinfoVerticalLayout;

import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

/**
 * @author rushan
 * 
 */
public class AreaRenderer extends VaadinRenderer
{
	@Inject
	IEventBroker eventBroker;
	
	@Inject
	VaadinExecutorService executorService;
	
	private EventHandler childrenHandler = new EventHandler() {
		public void handleEvent(Event event)
		{
			Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);
			
			//Stacks can not contains another stacks (yes, theoretically it can contains the stack in child placeholder, but
			//we search only top level stacks)
			if (changedObj instanceof MPartStack)
				return;
			
			@SuppressWarnings("unchecked")
			MElementContainer<? extends MUIElement> changedElement = (MElementContainer<MUIElement>) changedObj;
			
			if (!changedElement.isToBeRendered() || changedElement.getWidget() == null)
				return;
			
			String eventType = (String) event.getProperty(UIEvents.EventTags.TYPE);
			MUIElement child = null;
			if (UIEvents.EventTypes.ADD.equals(eventType))
				child = (MUIElement) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			else if (UIEvents.EventTypes.REMOVE.equals(eventType))
				child = (MUIElement) event.getProperty(UIEvents.EventTags.OLD_VALUE);
			
			if (child == null || !child.isToBeRendered())
				return;
			
			if (!(child instanceof MPartStack || child instanceof MPartSashContainer))
				return;
			
			processChangedElement(changedElement);
		}
	};
	
	private EventHandler toBeRenderedHandler = new EventHandler() {
		public void handleEvent(Event event) {

			MUIElement changedElement = (MUIElement) event
					.getProperty(UIEvents.EventTags.ELEMENT);
			
			if (!(changedElement instanceof MPartStack || changedElement instanceof MPartSashContainer))
				return;
			
			processChangedElement(changedElement);
		}
	};
	
	private void processChangedElement(MUIElement changedElement)
	{
		MArea area = null;
		//if it is area we directly process it
		if (changedElement instanceof MArea)
			area = (MArea) changedElement;
		else
		{//otherwise we find parent area
			int loc = modelService.getElementLocation(changedElement);
			if (loc != EModelService.IN_SHARED_AREA)
				return;
			
			area = findArea(changedElement);	
		}
		
		if (area != null)
		{
			final MArea closureArea = area;
			executorService.invokeLater(closureArea, new Runnable() {
				
				@Override
				public void run()
				{
					refreshTopRightState(closureArea);
				}
			});
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
	
	@PostConstruct
	public void subscribe()
	{
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_CHILDREN, childrenHandler);
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_TOBERENDERED, toBeRenderedHandler);
	}
	
	@PreDestroy
	public void unsubscribe()
	{
		eventBroker.unsubscribe(childrenHandler);
		eventBroker.unsubscribe(toBeRenderedHandler);
	}
	
	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent)
	{
		if (!(element instanceof MArea))
			return;

		MArea area = (MArea) element;
		AbstractOrderedLayout areaComp;
		if (area.isHorizontal())
			areaComp = new HorizontalLayout();
		else
		{
			BoundsinfoVerticalLayout bi = new BoundsinfoVerticalLayout();
			bi.setEnableBoundsUpdate(false);
			bi.setVariableValue("e4ElementType", "area");
			areaComp = bi;
		}
		areaComp.addStyleName("area");
		areaComp.setSizeFull();
		element.setWidget(areaComp);
	}

	@Override
	public void processContents(MElementContainer<MUIElement> container)
	{
		MArea area = (MArea)(MElementContainer<?>)container;
		AbstractOrderedLayout parentPane = (AbstractOrderedLayout) area.getWidget();
		parentPane.removeAllComponents();
		for (MUIElement element : area.getChildren())
		{
			if (element.isToBeRendered())
			{
				if (element instanceof MPlaceholder)
					element = ((MPlaceholder) element).getRef();
				parentPane.addComponent((Component) element.getWidget());	
			}
		}
		
		refreshTopRightState(area);
	}
	
	/**
	 * This method finds the top right part stack (located in top right corner)
	 * and apply its maximize and minimize buttons to entire area. Then hide the
	 * maximize and minimize buttons of other stacks in this area. So, there are
	 * implemented the eclipse3-like approach to manage area (in e4 swt renderer
	 * is used other way - if more than one stack is located in area, the top
	 * container-tabfolder is created for entire area and buttons of this
	 * container folder is used - i found this way is too heavy-weight).
	 */
	@SuppressWarnings("restriction")
	private void refreshTopRightState(final MArea area)
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
						else if (oldState == 1 && newState == -1)
						{
							ph.getTags().remove(IPresentationEngine.MINIMIZED);
							ph.getTags().remove(IPresentationEngine.MAXIMIZED);
							ph.getTags().add(IPresentationEngine.MINIMIZED);
						}
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
	
	@Override
	public void addChildGui(MUIElement child, MElementContainer<MUIElement> element)
	{
		if (!(child instanceof MPartSashContainerElement))
			return;
		
		AbstractOrderedLayout areaWidget = (AbstractOrderedLayout) element.getWidget();
		int index = indexOf(child, element);
		areaWidget.addComponent((Component) child.getWidget(), index);
	}
}
