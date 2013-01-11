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

package org.semanticsoft.vaaclipse.presentation.fastview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.api.Events;


/**
 * @author rushan
 *
 */
public class FastViewManager
{
	@Inject
	private IEclipseContext context;
	
	@Inject
	private EModelService modelService;
	
	private Map<MUIElement, SingleElementFastViewManager> element2man = new HashMap<MUIElement, SingleElementFastViewManager>();
	private Map<StackWidget, LayoutDragMode> layoutDragMode = new HashMap<>();
	private Map<MUIElement, List<MPartStack>> areaStackWidgets = new HashMap<>();
	
	private EventHandler minimizeHandler = new EventHandler() {
		
		@Override
		public void handleEvent(Event event)
		{
			MUIElement minimizedElement = (MUIElement) event.getProperty(Events.MinMaxEvents.PARAMETER_ELEMENT);
			
			if (element2man.containsKey(minimizedElement))
				return;
			
			//Disable drag mode
			if (minimizedElement instanceof MPartStack)
			{
				disableDrag((MPartStack)minimizedElement);
			}
			else if (minimizedElement instanceof MPlaceholder) //area
			{
				if (((MPlaceholder) minimizedElement).getRef() != null)
				{
					List<MPartStack> stacks = modelService.findElements(((MPlaceholder) minimizedElement).getRef(), null, MPartStack.class, null);
					
					for (MPartStack stack : stacks)
					{
						if (stack.getWidget() != null)
						{
							disableDrag(stack);	
						}
					}
					areaStackWidgets.put(minimizedElement, stacks);	
				}
			}
			
			//start single element fast view manager for minimizedElement
			MTrimBar trimBar = (MTrimBar) event.getProperty(Events.MinMaxEvents.PARAMETER_TRIMBAR);
			MToolBar toolBar = (MToolBar) event.getProperty(Events.MinMaxEvents.PARAMETER_TOOLBAR);
			
			IEclipseContext manContext = context.createChild();
			manContext.set(MUIElement.class, minimizedElement);
			manContext.set(MTrimBar.class, trimBar);
			manContext.set(MToolBar.class, toolBar);
			
			SingleElementFastViewManager man = ContextInjectionFactory.make(SingleElementFastViewManager.class, manContext);
			element2man.put(minimizedElement, man);
		}
	};
	
	private EventHandler restoreHandler = new EventHandler() {
			
			@Override
			public void handleEvent(Event event)
			{
				MUIElement minimizedElement = (MUIElement) event.getProperty(Events.MinMaxEvents.PARAMETER_ELEMENT);
				
				if (!element2man.containsKey(minimizedElement))
					return;
				
				element2man.remove(minimizedElement).dispose();
				
				//restore drag mode
				if (minimizedElement instanceof MPartStack)
				{
					restoreDrag((MPartStack) minimizedElement);
				}
				else if (minimizedElement instanceof MPlaceholder) //area
				{
					MPlaceholder ph = (MPlaceholder) minimizedElement;
					if (ph.getRef() != null)
					{
						List<MPartStack> stacks = areaStackWidgets.remove(minimizedElement);
						if (stacks != null)
						{
							for (MPartStack stack : stacks)
							{
								restoreDrag(stack);
							}
						}
					}
				}
			}
	};
	
//	private void gatherStacksWidgets(ComponentContainer container, List<StackWidget> stackWidgets)
//	{
//		Iterator<Component> it = container.getComponentIterator();
//		while (it.hasNext())
//		{
//			Component child = it.next();
//			if (child instanceof StackWidget)
//			{
//				stackWidgets.add((StackWidget) child);
//			}
//			
//			if (child instanceof ComponentContainer)
//			{
//				gatherStacksWidgets((ComponentContainer) child, stackWidgets);
//			}
//		}
//	}
	
	private void disableDrag(MPartStack stack)
	{
		StackWidget sw = (StackWidget) stack.getWidget();
		if (sw != null)
		{
			if (sw.getDragMode() != null && !sw.getDragMode().equals(LayoutDragMode.NONE))
			{
				layoutDragMode.put(sw, sw.getDragMode());
				sw.setDragMode(LayoutDragMode.NONE);
			}	
		}
	}
	
	private void restoreDrag(MPartStack stack)
	{
		StackWidget sw = (StackWidget) stack.getWidget();
		if (sw != null)
		{
			LayoutDragMode ldm = layoutDragMode.remove(sw);
			if (ldm != null)
			{
				sw.setDragMode(ldm);
			}	
		}
	}
	
	@PostConstruct
	public void postConstruct(IEventBroker eventBroker)
	{
		eventBroker.unsubscribe(minimizeHandler);
		eventBroker.subscribe(Events.MinMaxEvents.EVENT_MINIMIZE_ELEMENT, minimizeHandler);
		
		eventBroker.unsubscribe(restoreHandler);
		eventBroker.subscribe(Events.MinMaxEvents.EVENT_RESTORE_ELEMENT, restoreHandler);
	}
}
