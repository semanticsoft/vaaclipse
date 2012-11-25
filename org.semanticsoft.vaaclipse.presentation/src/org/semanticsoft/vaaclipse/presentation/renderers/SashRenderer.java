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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.presentation.engine.VaadinPresentationEngine;
import org.semanticsoft.vaaclipse.widgets.SashWidget;
import org.semanticsoft.vaaclipse.widgets.SashWidgetHorizontal;
import org.semanticsoft.vaaclipse.widgets.SashWidgetVertical;
import org.semanticsoft.vaaclipse.widgets.SplitPositionChangedListener;
import org.semanticsoft.vaaclipse.widgets.WorkbenchWindow;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractSplitPanel;
import com.vaadin.ui.AbstractSplitPanel.SplitterClickEvent;
import com.vaadin.ui.AbstractSplitPanel.SplitterClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.VerticalLayout;

public class SashRenderer extends GenericVaadinRenderer {

	@Inject
	EventBroker eventBroker;
	
	@Inject
	Logger logger;
	
	private boolean ignoreSashWeights = false;
	
	private EventHandler sashWeightHandler = new EventHandler() {
		public void handleEvent(Event event) {
			
			if (ignoreSashWeights)
				return;
			
			// Ensure that this event is for a MPartSashContainer
			MUIElement element = (MUIElement) event
					.getProperty(UIEvents.EventTags.ELEMENT);
			MElementContainer<MUIElement> parent = element.getParent();
			if (parent.getRenderer() != SashRenderer.this)
				return;
			
			MPartSashContainer sash = (MPartSashContainer)(MElementContainer<?>)element.getParent();
			setWeights(sash);
		}
	};
	
	private final EventHandler visibilityHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			MUIElement changedElement = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
			
			if ((MElementContainer<?>)changedElement.getParent() instanceof MPartSashContainer)
			{
				MPartSashContainer sash = (MPartSashContainer) (MElementContainer<?>)changedElement.getParent();
				
				((SashRenderer)sash.getRenderer()).refreshSashContainer(sash);
				
				boolean visible = false;
				for (MPartSashContainerElement child : sash.getChildren())
				{
					if (child.isVisible())
					{
						visible = true;
						break;
					}
				}
				if (sash.isVisible() != visible)
					sash.setVisible(visible);
			}
		}
	};

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MPartSashContainer)) {
			return;
		}
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		
		element.setWidget(layout);
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> element) {
		refreshSashContainer((MPartSashContainer)(MElementContainer<?>)element);
	}
	
	public void generateSplitPanelStructure(MPartSashContainer sash)
	{
		VerticalLayout layout = (VerticalLayout) sash.getWidget();
		layout.removeAllComponents();
		
		ComponentContainer sashWidget = null;
		
		@SuppressWarnings("unchecked")
		List<MPartSashContainerElement> renderableAndVisible = (List<MPartSashContainerElement>) filterRenderableAndVisibleElements(sash);
		
		if (renderableAndVisible.isEmpty())
		{
			sashWidget = new VerticalLayout();
		}
		else if (renderableAndVisible.size() == 1)
		{
			sashWidget = new VerticalLayout();
			MPartSashContainerElement child = renderableAndVisible.get(0);
			sashWidget.addComponent((Component) child.getWidget());
		}
		else
		{
			sashWidget = sash.isHorizontal() ? new SashWidgetHorizontal() : new SashWidgetVertical();
			AbstractSplitPanel currentSashWidget = (AbstractSplitPanel) sashWidget;
			currentSashWidget.setLocked(sash.getTags().contains(VaadinPresentationEngine.NO_RESIZE));
			for (int i = 0; i < renderableAndVisible.size(); i++)
			{
				MPartSashContainerElement child = renderableAndVisible.get(i);
				
				if (currentSashWidget.getFirstComponent() == null)
				{
					currentSashWidget.setFirstComponent((Component) child.getWidget());
				}
				else
				{
					if (i == renderableAndVisible.size() -1)
					{
						currentSashWidget.setSecondComponent((Component) child.getWidget());
					}
					else
					{
						AbstractSplitPanel newSashWidget = sash.isHorizontal() ? new SashWidgetHorizontal() : new SashWidgetVertical();
						newSashWidget.setLocked(sash.getTags().contains(VaadinPresentationEngine.NO_RESIZE));
						newSashWidget.setFirstComponent((Component) child.getWidget());
						currentSashWidget.setSecondComponent(newSashWidget);
						currentSashWidget = newSashWidget;	
					}
				}
			}
		}
		
		sashWidget.setSizeFull();
		layout.addComponent(sashWidget);
		
		setWeights(sash);
	}
	
	void setWeights(MPartSashContainer sash)
	{
		@SuppressWarnings("unchecked")
		List<MPartSashContainerElement> renderableAndVisible = (List<MPartSashContainerElement>) filterRenderableAndVisibleElements(sash);
		if (renderableAndVisible.size() < 2)
			return;
		
		Map<MPartSashContainerElement, Double> weights = new HashMap<>();
		Map<Component, MPartSashContainerElement> map = new HashMap<>();
		double total_weight = 0;
		for (MPartSashContainerElement children : renderableAndVisible)
		{
			String data = children.getContainerData();
			double weight = 0;
			if (data != null)
			{
				try
				{
					weight = Double.parseDouble(data);
				}
				catch (NumberFormatException e) {}
			}
			if (weight==0) 
				logger.warn("Please set container data on "+children.getElementId()+" else it may give unexpected behavior");
			map.put((Component) children.getWidget(), children);
			weights.put(children, weight);
			total_weight += weight;
		}
		
		AbstractSplitPanel topSashWidget = (AbstractSplitPanel) ((VerticalLayout)sash.getWidget()).getComponent(0);
		AbstractSplitPanel currentSashWidget = topSashWidget;
		while (true)
		{
			MPartSashContainerElement e1 = map.get(currentSashWidget.getFirstComponent());
			//the first - is always element
			double w = weights.get(e1);
			double pos = (w/total_weight)*100;
			currentSashWidget.setSplitPosition((float) pos);
			
			if (map.containsKey(currentSashWidget.getSecondComponent()))
				break;
			
			currentSashWidget = (AbstractSplitPanel) currentSashWidget.getSecondComponent();
			total_weight = total_weight - w;
		}
	}
	
	public void refreshSashContainer(MPartSashContainer sash)
	{
		generateSplitPanelStructure(sash);
	}

	@PostConstruct
	void postConstruct() {
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_CONTAINERDATA, sashWeightHandler);
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_VISIBLE, visibilityHandler);
	}
	
	@PreDestroy
	public void preDestroy()
	{
		eventBroker.unsubscribe(sashWeightHandler);
		eventBroker.unsubscribe(visibilityHandler);
	}
	
	@Override
	public void hookControllerLogic(MUIElement element) 
	{
		final MPartSashContainer sash = (MPartSashContainer) element;
		
		List<MPartSashContainerElement> renderableAndVisible = (List<MPartSashContainerElement>) filterRenderableAndVisibleElements(sash);
		
		if (renderableAndVisible.size() > 1)
		{
			for (MPartSashContainerElement child : renderableAndVisible)
			{
				Component childComponent = (Component) child.getWidget();
				if (childComponent.getParent() instanceof SashWidget)
				{
					SashWidget sashWidget = (SashWidget) childComponent.getParent();
					sashWidget.addListener(new SplitPositionChangedListener() {
						
						@Override
						public void processEvent(AbstractSplitPanel splitPanel, float newSplitPos) {
							AbstractComponent firstWidget = (AbstractComponent) splitPanel.getFirstComponent();
							
							//filter renderable and visible again (list can be changed)
							List<MPartSashContainerElement> renderableAndVisible = (List<MPartSashContainerElement>) filterRenderableAndVisibleElements(sash);
							MPartSashContainerElement firstChild = null;
							double rest_weight = 0;
							List<MPartSashContainerElement> restChilds = new LinkedList<>();
							for (int i = 0; i < renderableAndVisible.size(); i++)
							{
								MPartSashContainerElement child = renderableAndVisible.get(i);
								if (firstWidget.equals(child.getWidget()))
								{
									firstChild = child;
								}
								
								if (firstChild != null)
								{
									try {
										double w = Double.parseDouble(child.getContainerData());
										rest_weight += w;
									}
									catch (NumberFormatException e) {
										logger.error("Changing weights of SashContainer's childs is failed. Can not parse children container data");
										return;
									}
									
									restChilds.add(child);
								}
							}
							
							if (restChilds.size() > 1)
							{
								//String debugstr = "weights: ";
								ignoreSashWeights = true;
								
								double rest_weight_except_first = rest_weight - Double.parseDouble(firstChild.getContainerData());
								
								double newW1 = (newSplitPos / 100) * rest_weight;
								double new_rest_weight_except_first = rest_weight - newW1;
								long longVal1 = Math.round(newW1);
								firstChild.setContainerData(Long.toString(longVal1));
								//debugstr += longVal1;
								for (int i = 1; i < restChilds.size(); i++)
								{
									MPartSashContainerElement child = restChilds.get(i);
									double w = Double.parseDouble(child.getContainerData());
									double newW = (w/rest_weight_except_first) * new_rest_weight_except_first;
									long longVal = Math.round(newW);
									
									child.setContainerData(Long.toString(longVal));
									//debugstr += ", " + longVal;
								}
								
								ignoreSashWeights = false;
								
								//System.out.println(debugstr);
								
								//ATTENTION! Really line below is not required if code above works correctly.
								//But if there are any wrong behaviour appear then we have wrong synchronized state
								//that may caused side effects, so we do back syncronization (and bug if it occur become obvious).
								//This is also zeroed weight mismatch occuring when double rounded (and possible when vaadin process changes),
								//so we avoid mismatch accumulating. 
								//Most likely in the future this will be deleted (when this code will be proved that all ok).
								setWeights(sash);
							}
							else
								logger.error("Changing SashContainer child weights is failed. User changes is not processed correctly");
							
							//and last thing what we must do - tell the WorkbenchWindow to recalculate bounds of it content
							//(because bounds of some content of workbench window changed after sash widget split position changed)
							MWindow window = modelService.getTopLevelWindowFor(sash);
							WorkbenchWindow wWindow = (WorkbenchWindow) window.getWidget();
							wWindow.updateWindowContentBounds();
						}
					});
				}
				else
					logger.error("Error in  widget hierarchy detected - if sash container has more than one element its child widget must has SashWidget as a parent");
			}	
		}
	}
	
	@Override
	public void addChildGui(MUIElement child, MElementContainer<MUIElement> element)
	{
		if (!(child instanceof MPartSashContainerElement) || !((MElementContainer<?>)element instanceof MPartSashContainer))
			return;
		
		refreshSashContainer((MPartSashContainer)(MElementContainer<?>)element);
	}
	
	@Override
	public void removeChildGui(MUIElement child, MElementContainer<MUIElement> element)
	{
		if (!(child instanceof MPartSashContainerElement) || !((MElementContainer<?>)element instanceof MPartSashContainer))
			return;
		
		refreshSashContainer((MPartSashContainer)(MElementContainer<?>)element);
	}
}
