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

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.presentation.utils.HierarchyUtils;
import org.semanticsoft.vaaclipse.util.Utils;
import org.semanticsoft.vaaclipse.widgets.StackWidget;
import org.semanticsoft.vaaclipse.widgets.TwoStateToolbarButton;

import com.vaadin.terminal.Resource;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * @author rushan
 * 
 */
public class PerspectiveStackRenderer extends GenericRenderer
{
	private Map<MUIElement, HorizontalLayout> perspectivestack_perspectiveswitcher = new HashMap<>();
	private Map<MUIElement, TwoStateToolbarButton> perspective_button = new HashMap<>();
	
	public Map<MUIElement, TwoStateToolbarButton> getPerspective2ButtonMapping()
	{
		return perspective_button;
	}
	
	public Map<MUIElement, HorizontalLayout> getPerspectivestack2PerspectiveswitcherMapping()
	{
		return perspectivestack_perspectiveswitcher;
	}
	
	@Inject
	IEventBroker eventBroker;
	
	@Inject
	IEclipseContext eclipseContext;
	
	@Inject
	EPartService partService;

	private EventHandler selectPerspectiveHandler = new EventHandler() {
		public void handleEvent(Event event)
		{
			Object element = event.getProperty(UIEvents.EventTags.ELEMENT);

			if (!(element instanceof MPerspectiveStack))
				return;

			MPerspectiveStack stack = (MPerspectiveStack) element;
			if (stack.getRenderer() != PerspectiveStackRenderer.this)
				return;
			PerspectiveStackRenderer psr = (PerspectiveStackRenderer) stack.getRenderer();

			// Gather up the elements that are being 'hidden' by this change
			MUIElement oldSel = (MUIElement) event.getProperty(UIEvents.EventTags.OLD_VALUE);
			if (oldSel != null)
			{
				List<MUIElement> goingHidden = new ArrayList<MUIElement>();
				//hideElementRecursive(oldSel, goingHidden);
			}

			if (oldSel != null)
			{
				perspective_button.get(oldSel).setState(false);
				perspective_button.get(oldSel).setSwitchStateByUserClickEnabled(true);
			}
			
			if (stack.getSelectedElement() != null)
			{
				//psr.showTab(stack.getSelectedElement());
				((VerticalLayout)stack.getWidget()).removeAllComponents();
				connectReferencedElementsToPerspectiveWidgets(stack.getSelectedElement());
				((VerticalLayout)stack.getWidget()).addComponent((Component)stack.getSelectedElement().getWidget());
				perspective_button.get(stack.getSelectedElement()).setState(true);
				perspective_button.get(stack.getSelectedElement()).setSwitchStateByUserClickEnabled(false);
			}
		}
	};
	
	private void connectReferencedElementsToPerspectiveWidgets(MElementContainer<? extends MUIElement> container)
	{
		for (MUIElement e : container.getChildren())
		{
			if (e instanceof MPlaceholder)
			{
				MPlaceholder ph = (MPlaceholder) e;
				ComponentContainer phComponent = (ComponentContainer) ph.getWidget();
				Component refComponent = (Component) ph.getRef().getWidget();
				phComponent.addComponent(refComponent);
				ph.getRef().setCurSharedRef(ph);
				
				System.out.println("sfaf");
				MPartStack topLeftStack = HierarchyUtils.findTopLeftFolder(ph.getRef());
				if (topLeftStack != null)
				{
					if (ph.getTags().contains(IPresentationEngine.MAXIMIZED))
						((StackWidget)topLeftStack.getWidget()).setState(1);
					else if (ph.getTags().contains(IPresentationEngine.MINIMIZED))
						((StackWidget)topLeftStack.getWidget()).setState(-1);
					else
						((StackWidget)topLeftStack.getWidget()).setState(0);
				}
			}
			
			if (e instanceof MElementContainer<?>)
				connectReferencedElementsToPerspectiveWidgets((MElementContainer<MUIElement>) e);
		}
	}

	@PostConstruct
	public void postConstruct()
	{
		eventBroker.unsubscribe(selectPerspectiveHandler);
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT, selectPerspectiveHandler);
	}

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent)
	{
		HorizontalLayout perspectiveSwitcher = new HorizontalLayout();
		perspectiveSwitcher.setStyleName("perspectivepanel");
		perspectiveSwitcher.setSizeUndefined();
		
		perspectivestack_perspectiveswitcher.put(element, perspectiveSwitcher);
		
		VerticalLayout perspectiveStackContent = new VerticalLayout();
		perspectiveStackContent.setSizeFull();
		element.setWidget(perspectiveStackContent);
	}

	@Override
	public void processContents(MElementContainer<MUIElement> element)
	{
		if (element.getChildren().isEmpty())
			return;
		
		MPerspectiveStack perspectiveStack = (MPerspectiveStack)(MElementContainer<?>)element;
		for (final MPerspective perspective : perspectiveStack.getChildren())
		{
			if (perspective.isToBeRendered() && perspective.isVisible())
			{
				final TwoStateToolbarButton perspectiveButton = new TwoStateToolbarButton();
				
				String uri;
				if (perspective.getIconURI() != null)
					uri = perspective.getIconURI();
				else
					uri = "platform:/plugin/org.semanticsoft.vaaclipse.resources/VAADIN/themes/vaaclipse_default_theme/img/blank_perspective.png";
				
				Resource icon = new com.vaadin.terminal.ThemeResource(Utils.convertPath(uri));
				perspectiveButton.setIcon(icon);
				
				//TODO: uncoment
//				if (perspective.getLabel() != null)
//					perspectiveButton.setCaption(perspective.getLabel());
				
				if (perspective.getTooltip() != null)
				{
					perspectiveButton.setDescription(perspective.getLocalizedTooltip());
				}
				
				perspectiveButton.addListener(new ClickListener() {

					public void buttonClick(ClickEvent event)
					{
						MPerspectiveStack perspectiveStack = (MPerspectiveStack)(MElementContainer<?>)perspective.getParent();
						//perspectiveStack.setSelectedElement(perspective);
						partService.switchPerspective(perspective);
					}
				});
				
				HorizontalLayout perspectiveStackPanel = perspectivestack_perspectiveswitcher.get(perspectiveStack);
				perspectiveStackPanel.addComponent(perspectiveButton);
				
				perspective_button.put(perspective, perspectiveButton);	
			}
		}
		
		MPerspective selectedPerspective = perspectiveStack.getSelectedElement();
		
		if (selectedPerspective == null)
		{
			selectedPerspective = perspectiveStack.getChildren().get(0);
			//perspectiveStack.setSelectedElement(selectedPerspective);
			partService.switchPerspective(selectedPerspective);
		}
		else
		{
			//reset selected element (set selected element handler will work)
			perspectiveStack.setSelectedElement(null);
			perspectiveStack.setSelectedElement(selectedPerspective);
		}
		
		refreshPerspectiveStackVisibility(perspectiveStack);
	}
	
	private void refreshPerspectiveStackVisibility(MPerspectiveStack stack) {
		AbstractOrderedLayout perspectiveStackPanel = perspectivestack_perspectiveswitcher.get(stack);
		
		perspectiveStackPanel.setVisible(perspectiveStackPanel.getComponentCount() > 1);
	}
	
	@Override
	public void addChildGui(MUIElement child, MElementContainer<MUIElement> element)
	{
		MPerspectiveStack stack = (MPerspectiveStack)(MElementContainer<?>)element;
		refreshPerspectiveStackVisibility(stack);
	}
}
