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

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.widgets.SashWidgetHorizontal;
import org.semanticsoft.vaaclipse.widgets.SashWidgetVertical;

import com.vaadin.ui.AbstractSplitPanel;
import com.vaadin.ui.Component;

@SuppressWarnings("restriction")
public class SashRenderer extends GenericRenderer {

	private EventHandler sashOrientationHandler;
	private EventHandler sashWeightHandler;

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
		
		String dividerPos = element.getContainerData();
		
		widget.setSplitPosition(dividerPos != null ? Float.parseFloat(dividerPos) : 50.0f);
		
		element.setWidget(widget);
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> element) {
		if (element.getChildren().size() == 2) {
			MUIElement child1 = element.getChildren().get(0);
			MUIElement child2 = element.getChildren().get(1);
			
			Component childWidget1 = (Component) child1.getWidget();
			Component childWidget2 = (Component) child2.getWidget();
			//TODO:temp, remove after testing that widget visibility sync work ok
			childWidget1.setVisible(child1.isVisible());
			childWidget2.setVisible(child2.isVisible());
			
			AbstractSplitPanel sash = (AbstractSplitPanel) element.getWidget();
			sash.setFirstComponent(childWidget1);
			sash.setSecondComponent(childWidget2);
		} else {
			System.err.println("A sash has to have 2 children");
		}
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
	
	@Override
	public void refreshPlatformElement(MElementContainer<? extends MUIElement> element)
	{
		for (MUIElement e : element.getChildren())
		{
			if (e instanceof MElementContainer<?>)
				refreshPlatformElement((MElementContainer<? extends MUIElement>) e);
			else if (e instanceof MPlaceholder && ((MPlaceholder)e).getRef() instanceof MElementContainer<?>)
				refreshPlatformElement((MElementContainer<? extends MUIElement>) ((MPlaceholder)e).getRef());
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
//		sashWeightHandler = new EventHandler() {
//			@Override
//			public void handleEvent(Event event) {
//				// Ensure that this event is for a MPartSashContainer
//				MUIElement element = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
//				if (element.getRenderer() != SashRenderer.this) {
//					return;
//				}
//				
//				String dividerPos = element.getContainerData();
//				if (dividerPos != null) {
//					SashWidget1 splitPane = ((SashWidget1) element.getWidget());
//					int divPos = Integer.parseInt(dividerPos);
//					splitPane.setSplitPosition(divPos);
//				}
//			}
//		};
//
//		eventBroker.subscribe(UIEvents.UIElement.TOPIC_CONTAINERDATA, sashWeightHandler);
	}
}
