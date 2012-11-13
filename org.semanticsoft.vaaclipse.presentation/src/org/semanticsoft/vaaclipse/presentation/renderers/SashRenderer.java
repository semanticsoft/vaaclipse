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
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.vaadin.ui.AbstractSplitPanel;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

@SuppressWarnings("restriction")
public class SashRenderer extends GenericRenderer {

	@Inject
	EventBroker eventBroker;
	
	private EventHandler sashOrientationHandler;
	
	private EventHandler sashWeightHandler = new EventHandler() {
		public void handleEvent(Event event) {
			// Ensure that this event is for a MPartSashContainer
			MUIElement element = (MUIElement) event
					.getProperty(UIEvents.EventTags.ELEMENT);
			MElementContainer<MUIElement> parent = element.getParent();
			if (parent.getRenderer() != SashRenderer.this)
				return;

			MPartSashContainer sash = (MPartSashContainer)(MElementContainer<?>)element.getParent();
			setWeights((MPartSashContainer) (MElementContainer<?>)element.getParent());
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
		final MPartSashContainer partSashContainer = (MPartSashContainer) element;
		
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		
		element.setWidget(layout);
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> element) {
//		if (element.getChildren().size() == 2) {
//			MUIElement child1 = element.getChildren().get(0);
//			MUIElement child2 = element.getChildren().get(1);
//			
//			Component childWidget1 = (Component) child1.getWidget();
//			Component childWidget2 = (Component) child2.getWidget();
//			//TODO:temp, remove after testing that widget visibility sync work ok
//			childWidget1.setVisible(child1.isVisible());
//			childWidget2.setVisible(child2.isVisible());
//			
//			AbstractSplitPanel sash = (AbstractSplitPanel) element.getWidget();
//			sash.setFirstComponent(childWidget1);
//			sash.setSecondComponent(childWidget2);
//		} else {
//			System.err.println("A sash has to have 2 children");
//		}
		
		refreshSashContainer((MPartSashContainer)(MElementContainer<?>)element);
	}
	
	public void generateSplitPanelStructure(MPartSashContainer sash)
	{
		VerticalLayout layout = (VerticalLayout) sash.getWidget();
		layout.removeAllComponents();
		
		ComponentContainer sashWidget = null;
		
		List<MPartSashContainerElement> renderableAndVisible = filterRenderableAndVisibleElements(sash);
		
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
			sashWidget = sash.isHorizontal() ? new HorizontalSplitPanel() : new VerticalSplitPanel();
			AbstractSplitPanel currentSashWidget = (AbstractSplitPanel) sashWidget;
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
						AbstractSplitPanel newSashWidget = sash.isHorizontal() ? new HorizontalSplitPanel() : new VerticalSplitPanel();
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

	private List<MPartSashContainerElement> filterRenderableAndVisibleElements(MPartSashContainer sash)
	{
		List<MPartSashContainerElement> renderableAndVisible = new ArrayList<>();
		for (MPartSashContainerElement e : sash.getChildren())
		{
			if (e.isToBeRendered() && e.isVisible())
				renderableAndVisible.add(e);
		}
		return renderableAndVisible;
	}
	
	void setWeights(MPartSashContainer sash)
	{
		List<MPartSashContainerElement> renderableAndVisible = filterRenderableAndVisibleElements(sash);
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
	
	@Override
	public void hookControllerLogic(MUIElement element) {
		if (element instanceof MPartSashContainer) {
			final MPartSashContainer partSashContainer = (MPartSashContainer) element;

//			final AbstractSplitPanel splitPane = (AbstractSplitPanel) partSashContainer.getWidget();
//			splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
//				@Override
//				public void propertyChange(PropertyChangeEvent event) {
//					if (splitPane.getLeftComponent() != null && splitPane.getRightComponent() != null) {
//						partSashContainer.setContainerData(((Integer) event.getNewValue()).toString());
//					}
//				}
//			});
		}
	}
	
	public void refreshSashContainer(MPartSashContainer sash)
	{
		generateSplitPanelStructure(sash);
	}

	@PostConstruct
	void postConstruct() {
//		sashOrientationHandler = new EventHandler() {
//			@Override
//			public void handleEvent(Event event) {
//				// Ensure that this event is for a MPartSashContainer
//				MUIElement element = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
//				if (element.getRenderer() != SashRenderer.this) {
//					return;
//				}
//				Orientation orientation;
//				if (((MPartSashContainer) element).isHorizontal()) {
//					orientation = SashWidget1.Orientation.HORIZONTAL;
//				} else {
//					orientation = SashWidget1.Orientation.VERTICAL;
//				}
//				((SashWidget1) element.getWidget()).setOrientation(orientation);
//			}
//		};
//
//		eventBroker.subscribe(UIEvents.GenericTile.TOPIC_HORIZONTAL, sashOrientationHandler);
//

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
