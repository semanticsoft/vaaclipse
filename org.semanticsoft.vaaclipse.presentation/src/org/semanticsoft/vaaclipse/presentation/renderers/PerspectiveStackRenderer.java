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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.presentation.utils.Commons;
import org.semanticsoft.vaaclipse.presentation.utils.HierarchyUtils;
import org.semanticsoft.vaaclipse.publicapi.model.Tags;
import org.semanticsoft.vaaclipse.util.Utils;
import org.semanticsoft.vaaclipse.widgets.StackWidget;
import org.semanticsoft.vaaclipse.widgets.TwoStateToolbarButton;
import org.vaadin.peter.contextmenu.ContextMenu;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItem;

import com.vaadin.Application;
import com.vaadin.event.LayoutEvents;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * @author rushan
 * 
 */
public class PerspectiveStackRenderer extends VaadinRenderer
{
	private MPerspectiveStack perspectiveStackForSwitcher;
	private HorizontalLayout perspectiveSwitcher;
	private ContextMenu menu;
	private ContextMenuItem showTextItem;
	private Map<MPerspective, TwoStateToolbarButton> perspective_button = new HashMap<>();
	
	private MPerspective lastClickedPerspective;

	public HorizontalLayout getPerspectiveSwitcher()
	{
		return perspectiveSwitcher;
	}

	public MPerspectiveStack getPerspectiveStackForSwitcher()
	{
		return perspectiveStackForSwitcher;
	}

	@Inject
	IEventBroker eventBroker;

	@Inject
	IEclipseContext eclipseContext;

	@Inject
	EPartService partService;
	
	@Inject
	Application vaadinApp;
	
	private final EventHandler tagListener = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
//			if (ignoreTagChanges) {
//				return;
//			}

			Object changedObj = event.getProperty(EventTags.ELEMENT);
			
			if (!(changedObj instanceof MPerspectiveStack)) {
				return;
			}
			
			final MPerspectiveStack changedElement = (MPerspectiveStack) changedObj;
			
			String eventType = (String) event.getProperty(UIEvents.EventTags.TYPE);
			String tag = (String) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			String oldVal = (String) event.getProperty(UIEvents.EventTags.OLD_VALUE);

			if (UIEvents.EventTypes.ADD.equals(eventType) && Tags.ICONS_ONLY.equals(tag)) 
			{
				for (Map.Entry<MPerspective, TwoStateToolbarButton> entry : perspective_button.entrySet())
				{
					MPerspective perspective = entry.getKey();
					TwoStateToolbarButton button = entry.getValue();
					clearButton(button);
					setupStyleTextIcon(null, Commons.trim(perspective.getIconURI()), button);
				}
				menu.removeItem(showTextItem);
				showTextItem = menu.addItem("Show Text");
			} 
			else if (UIEvents.EventTypes.REMOVE.equals(eventType) && Tags.ICONS_ONLY.equals(oldVal)) {
				for (Map.Entry<MPerspective, TwoStateToolbarButton> entry : perspective_button.entrySet())
				{
					MPerspective perspective = entry.getKey();
					TwoStateToolbarButton button = entry.getValue();
					clearButton(button);
					setupStyleTextIcon(Commons.trim(perspective.getLabel()), Commons.trim(perspective.getIconURI()), button);
				}
				menu.removeItem(showTextItem);
				showTextItem = menu.addItem("Hide Text");
			}
		}
	};
	
	private void clearButton(Button button)
	{
		button.setCaption(null);
		button.setIcon(null);
		button.removeStyleName("icononly");
		button.removeStyleName("textonly");
	}

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
				// hideElementRecursive(oldSel, goingHidden);
			}

			if (oldSel != null)
			{
				perspective_button.get(oldSel).setState(false);
				perspective_button.get(oldSel).setSwitchStateByUserClickEnabled(true);
			}

			if (stack.getSelectedElement() != null)
			{
				// psr.showTab(stack.getSelectedElement());
				((VerticalLayout) stack.getWidget()).removeAllComponents();
				connectReferencedElementsToPerspectiveWidgets(stack.getSelectedElement());
				((VerticalLayout) stack.getWidget()).addComponent((Component) stack.getSelectedElement().getWidget());
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
				if (ph.isToBeRendered())
				{
					ComponentContainer phComponent = (ComponentContainer) ph.getWidget();
					Component refComponent = (Component) ph.getRef().getWidget();
					phComponent.addComponent(refComponent);
				}

				ph.getRef().setCurSharedRef(ph);

				MPartStack topLeftStack = HierarchyUtils.findTopLeftFolder(ph.getRef());
				if (topLeftStack != null)
				{
					if (ph.getTags().contains(IPresentationEngine.MAXIMIZED))
						((StackWidget) topLeftStack.getWidget()).setState(1);
					else if (ph.getTags().contains(IPresentationEngine.MINIMIZED))
						((StackWidget) topLeftStack.getWidget()).setState(-1);
					else
						((StackWidget) topLeftStack.getWidget()).setState(0);
				}
			}

			if (e instanceof MElementContainer<?>)
				connectReferencedElementsToPerspectiveWidgets((MElementContainer<MUIElement>) e);
		}
	}

	@PostConstruct
	public void postConstruct()
	{
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT, selectPerspectiveHandler);
		eventBroker.subscribe(UIEvents.ApplicationElement.TOPIC_TAGS, tagListener);
	}
	
	@PreDestroy
	public void preDestroy()
	{
		eventBroker.unsubscribe(selectPerspectiveHandler);
		eventBroker.unsubscribe(tagListener);
	}

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent)
	{
		if (perspectiveSwitcher == null)
		{
			perspectiveStackForSwitcher = (MPerspectiveStack) element;
			perspectiveSwitcher = new HorizontalLayout();
			perspectiveSwitcher.setStyleName("perspectivepanel");
			perspectiveSwitcher.setSizeUndefined();

			// Context menu
			menu = new ContextMenu();
			perspectiveSwitcher.addComponent(menu);
			
			final ContextMenuItem closeItem = menu.addItem("Close");
			closeItem.setSeparatorVisible(true);
			if (perspectiveStackForSwitcher.getTags().contains(Tags.ICONS_ONLY))
				showTextItem = menu.addItem("Show Text");
			else
				showTextItem = menu.addItem("Hide Text");
			
			 menu.addListener(new ContextMenu.ClickListener() {

				@Override
				public void contextItemClick(org.vaadin.peter.contextmenu.ContextMenu.ClickEvent event)
				{
					ContextMenuItem clickedItem = event.getClickedItem();
					if (clickedItem == closeItem)
					{
						//vaadinApp.getMainWindow().showNotification("Close request for: " + lastClickedPerspective.getLabel());
						
						
					}
					
					if (clickedItem == showTextItem)
					{
						//vaadinApp.getMainWindow().showNotification("Show text request for: " + lastClickedPerspective.getLabel());
						if (perspectiveStackForSwitcher.getTags().contains(Tags.ICONS_ONLY))
							perspectiveStackForSwitcher.getTags().remove(Tags.ICONS_ONLY);
						else
							perspectiveStackForSwitcher.getTags().add(Tags.ICONS_ONLY);
					}
				}
			 });
		}

		VerticalLayout perspectiveStackContent = new VerticalLayout();
		perspectiveStackContent.setSizeFull();
		element.setWidget(perspectiveStackContent);
	}

	@Override
	public void processContents(MElementContainer<MUIElement> element)
	{
		if (element.getChildren().isEmpty())
			return;

		MPerspectiveStack perspectiveStack = (MPerspectiveStack) (MElementContainer<?>) element;
		
		if (perspectiveStack == perspectiveStackForSwitcher) //add buttons only for perspective stack with switcher
		{
			boolean iconsOnly = perspectiveStack.getTags().contains(Tags.ICONS_ONLY);
			
			for (final MPerspective perspective : perspectiveStack.getChildren())
			{
				if (perspective.isVisible())
				{
					String label = iconsOnly ? null : Commons.trim(perspective.getLabel());
					String iconURI = Commons.trim(perspective.getIconURI());

					final TwoStateToolbarButton button = new TwoStateToolbarButton();

					// ----
					setupStyleTextIcon(label, iconURI, button);
					// ----

					if (perspective.getTooltip() != null)
					{
						button.setDescription(perspective.getLocalizedTooltip());
					}

					button.addListener(new ClickListener() {

						public void buttonClick(ClickEvent event)
						{
							MPerspectiveStack perspectiveStack = (MPerspectiveStack) (MElementContainer<?>) perspective
									.getParent();
							// perspectiveStack.setSelectedElement(perspective);
							switchPerspective(perspective);
						}
					});

					//TODO: replace VerticalLayout on more thin layout (for example SimpleLayout addon which consist of just one div)
					VerticalLayout wrapperLayout = new VerticalLayout();
					wrapperLayout.setSizeUndefined();
					wrapperLayout.addComponent(button);
					wrapperLayout.addListener(new LayoutEvents.LayoutClickListener() {

						@Override
						public void layoutClick(LayoutClickEvent event)
						{
							if (LayoutClickEvent.BUTTON_RIGHT == event.getButton())
							{
								lastClickedPerspective = perspective;
								menu.show(event.getClientX(), event.getClientY());
							}
						}
					});
					
					perspectiveSwitcher.addComponent(wrapperLayout);

					perspective_button.put(perspective, button);
				}
			}	
		}
		
		MPerspective selectedPerspective = perspectiveStack.getSelectedElement();

		if (selectedPerspective == null)
		{
			selectedPerspective = perspectiveStack.getChildren().get(0);
			// perspectiveStack.setSelectedElement(selectedPerspective);
			switchPerspective(selectedPerspective);
		}
		else
		{
			// reset selected element (set selected element handler will work)
			perspectiveStack.setSelectedElement(null);
			switchPerspective(selectedPerspective);
		}

		refreshPerspectiveStackVisibility(perspectiveStack);
	}

	private void setupStyleTextIcon(String label, String iconURI, final TwoStateToolbarButton button)
	{
		if (iconURI == null && label == null)
		{
			iconURI = "platform:/plugin/org.semanticsoft.vaaclipse.resources/VAADIN/themes/vaaclipse_default_theme/img/blank_perspective.png";
			Resource icon = new com.vaadin.terminal.ThemeResource(Utils.convertPath(iconURI));
			button.setIcon(icon);
			button.addStyleName("icononly");
		}
		else
		{
			if (iconURI != null)
			{
				Resource icon = new ThemeResource(Utils.convertPath(iconURI));
				button.setIcon(icon);
			}
			else
				button.addStyleName("textonly");

			if (label != null)
			{
				button.setCaption(label);
			}
			else
				button.addStyleName("icononly");
		}
	}

	private void switchPerspective(MPerspective perspective)
	{
		partService.switchPerspective(perspective);
		if (perspective.getElementId() != null)
		{
			String perspectiveId = perspective.getElementId().trim();
			eclipseContext.set("activePerspective", perspectiveId);
		}
	}

	private void refreshPerspectiveStackVisibility(MPerspectiveStack stack)
	{
		perspectiveSwitcher.setVisible(perspectiveSwitcher.getComponentCount() > 1);
	}

	@Override
	public void addChildGui(MUIElement child, MElementContainer<MUIElement> element)
	{
		MPerspectiveStack stack = (MPerspectiveStack) (MElementContainer<?>) element;
		refreshPerspectiveStackVisibility(stack);
	}
}
