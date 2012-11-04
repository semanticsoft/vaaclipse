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

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.widgets.SashWidgetHorizontal;
import org.semanticsoft.vaaclipse.widgets.SashWidgetVertical;

import com.vaadin.ui.AbstractSplitPanel;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;

@SuppressWarnings("restriction")
public class SashRenderer extends GenericRenderer {

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
			AbstractSplitPanel sashWidget = (AbstractSplitPanel) sash.getWidget();
			float pos = computeSashDividerPosition((MPartSashContainer) (MElementContainer<?>)element.getParent());
			if (pos > -1)
				sashWidget.setSplitPosition(pos);
		}
	};

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MPartSashContainer)) {
			return;
		}
		final MPartSashContainer partSashContainer = (MPartSashContainer) element;
		
		AbstractSplitPanel widget = null;
		
		if (partSashContainer.isHorizontal()) {
			widget = new SashWidgetHorizontal();
		} else {
			widget = new SashWidgetVertical();
		}	
		
		widget.setSizeFull();
		
		float pos = computeSashDividerPosition((MPartSashContainer) element);
		if (pos > -1)
			widget.setSplitPosition(pos);
		
		element.setWidget(widget);
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
	
	@Override
	public void hookControllerLogic(MUIElement element) {
		if (element instanceof MPartSashContainer) {
			final MPartSashContainer partSashContainer = (MPartSashContainer) element;

			final AbstractSplitPanel splitPane = (AbstractSplitPanel) partSashContainer.getWidget();
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
		AbstractSplitPanel splitPane = (AbstractSplitPanel) sash.getWidget();
		
		List<MPartSashContainerElement> children = sash.getChildren();
		
		MPartSashContainerElement first = children.size() > 0 && children.get(0).isToBeRendered()? children.get(0) : null;
		MPartSashContainerElement second = children.size() > 1 && children.get(1).isToBeRendered()? children.get(1) : null;
		
		if (first != null)
		{
			final Component firstWidget = (Component) first.getWidget();
			firstWidget.setVisible(first.isVisible());
//			if (firstWidget.getParent() != null)
//				((ComponentContainer)firstWidget.getParent()).removeComponent(firstWidget);
			splitPane.setFirstComponent(firstWidget);
		}
		if (second != null)
		{
			final Component secondWidget = (Component) second.getWidget();
			secondWidget.setVisible(second.isVisible());
//			if (secondWidget.getParent() != null)
//				((ComponentContainer)secondWidget.getParent()).removeComponent(secondWidget);
			splitPane.setSecondComponent((Component) second.getWidget());
		}
		
		float pos = computeSashDividerPosition(sash);
		if (pos > -1)
			splitPane.setSplitPosition(pos);
	}
	
	@Override
	@Deprecated
	public void refreshPlatformElement(MElementContainer<?> element)
	{
		MPartSashContainer sash = (MPartSashContainer) element;
		refreshSashContainer(sash);
		refreshRecursive(element);
	}

	private void refreshRecursive(MElementContainer<?> element)
	{
		for (MUIElement e : element.getChildren())
		{
			if (e instanceof MElementContainer<?>)
				refreshRecursive((MElementContainer<? extends MUIElement>) e);
			else if (e instanceof MPlaceholder && ((MPlaceholder)e).getRef() instanceof MElementContainer<?>)
				refreshRecursive((MElementContainer<? extends MUIElement>) ((MPlaceholder)e).getRef());
		}
		
		if (element.getWidget() instanceof SashWidgetHorizontal)
			((SashWidgetHorizontal)element.getWidget()).refreshState();
		else if (element.getWidget() instanceof SashWidgetVertical)
			((SashWidgetVertical)element.getWidget()).refreshState();
	}

	@Inject
	void postConstruct(IEventBroker eventBroker) {
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
	}
	
	@Override
	public void addChildGui(MUIElement child, MElementContainer<MUIElement> element)
	{
		if (!(child instanceof MPartSashContainerElement) || !((MElementContainer<?>)element instanceof MPartSashContainer))
			return;
		
		//refreshPlatformElement(element);
		refreshSashContainer((MPartSashContainer)(MElementContainer<?>)element);
	}
	
	@Override
	public void removeChildGui(MUIElement child, MElementContainer<MUIElement> element)
	{
		if (!(child instanceof MPartSashContainerElement) || !((MElementContainer<?>)element instanceof MPartSashContainer))
			return;
		
		//refreshPlatformElement(element);
		refreshSashContainer((MPartSashContainer)(MElementContainer<?>)element);
	}
	
	float computeSashDividerPosition(MPartSashContainer container)
	{
		if (container.getChildren().size() == 2)
		{
			MPartSashContainerElement child1 = container.getChildren().get(0);
			MPartSashContainerElement child2 = container.getChildren().get(1);
			
			double pos1, pos2;
			try
			{
				pos1 = child1.getContainerData() != null && child1.getContainerData().length() > 0 ? Double.parseDouble(child1.getContainerData()) : -1;
			}
			catch (NumberFormatException e)
			{
				pos1 = -1;
			}
			
			try
			{
				pos2 = child2.getContainerData() != null && child2.getContainerData().length() > 0 ? Double.parseDouble(child2.getContainerData()) : -1;
			}
			catch (NumberFormatException e)
			{
				pos2 = -1;
			}
			
			if (pos1 < 0 && pos2 < 0)
			{
				pos1 = 50;
				pos2 = 50;
			}
			else if (pos1 < 0)
			{
				pos1 = 100 - pos2;
			}
			else if (pos2 < 0)
			{
				pos2 = 100 - pos1;
			}
			
			return (float) (100*pos1/(pos1 + pos2));
		}
		else
			return -1.0f;
	}
}
