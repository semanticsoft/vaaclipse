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

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.presentation.engine.GenericPresentationEngine;
import org.semanticsoft.vaaclipse.presentation.utils.Commons;
import org.semanticsoft.vaaclipse.presentation.utils.HierarchyUtils;
import org.semanticsoft.vaaclipse.publicapi.model.Tags;
import org.semanticsoft.vaaclipse.publicapi.resources.ResourceHelper;
import org.semanticsoft.vaaclipse.widgets.StackWidget;
import org.semanticsoft.vaaclipse.widgets.TwoStateToolbarButton;
import org.semanticsoft.vaadin.optiondialog.OptionDialog;
import org.vaadin.peter.contextmenu.ContextMenu;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItem;
import org.vaadin.peter.contextmenu.ContextMenu.ContextMenuItemClickEvent;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author rushan
 * 
 */
public class PerspectiveStackRenderer extends VaadinRenderer
{
	private MPerspectiveStack perspectiveStackForSwitcher;
	private HorizontalLayout perspectiveSwitcherPanel;
	private Map<MPerspective, TwoStateToolbarButton> perspective_button = new HashMap<MPerspective, TwoStateToolbarButton>();
	private Map<Component, ContextMenu> button2ContextMenu = new HashMap<Component, ContextMenu>();
	private Map<ContextMenu, Component> contextMenu2Button = new HashMap<ContextMenu, Component>();
	
	private MPerspective activePerspective;
	@Inject
	private UI vaadinUI;

	public HorizontalLayout getPerspectiveSwitcher()
	{
		return perspectiveSwitcherPanel;
	}

	public MPerspectiveStack getPerspectiveStackForSwitcher()
	{
		return perspectiveStackForSwitcher;
	}

	@Inject
	IEventBroker eventBroker;

	@Inject
	MApplication application;
	
	@Inject
	EPartService partService;
	
	@Inject
	GenericPresentationEngine engine;
	
	static final String PERSPECTIVE_LABEL = "PerspectiveLabel";
	static final String PERSPECTIVE_ICON = "PerspectiveIcon";
	
	private final EventHandler tagListener = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
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
					
					button.setLabelAndIcon(null, Commons.trim(perspective.getIconURI()));
					
					ContextMenu menu = button2ContextMenu.get(button);
//					menu.removeItem(showTextItem);
//					showTextItem = menu.addItem("Show Text");
//					showTextItem.addStyleName("close-perspective-item"); //bugfixing style for ie9 (context menu addon has bug for ie9)
				}
			} 
			else if (UIEvents.EventTypes.REMOVE.equals(eventType) && Tags.ICONS_ONLY.equals(oldVal)) {
				for (Map.Entry<MPerspective, TwoStateToolbarButton> entry : perspective_button.entrySet())
				{
					MPerspective perspective = entry.getKey();
					TwoStateToolbarButton button = entry.getValue();
					button.setLabelAndIcon(Commons.trim(perspective.getLabel()), Commons.trim(perspective.getIconURI()));
					
					ContextMenu menu = button2ContextMenu.get(button);
//					menu.removeItem(showTextItem);
//					showTextItem = menu.addItem("Hide Text");
//					showTextItem.addStyleName("close-perspective-item"); //bugfixing style for ie9 (context menu addon has bug for ie9)
				}
			}
		}
	};
	
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
			}

			if (oldSel != null)
			{
				perspective_button.get(oldSel).setCheckedState(false);
				perspective_button.get(oldSel).setSwitchStateByUserClickEnabled(true);
				
				hideElementRecursive(oldSel);
			}
			
			((VerticalLayout) stack.getWidget()).removeAllComponents();

			if (stack.getSelectedElement() != null)
			{
				showElementRecursive(stack.getSelectedElement());
				((VerticalLayout) stack.getWidget()).addComponent((Component) stack.getSelectedElement().getWidget());
				perspective_button.get(stack.getSelectedElement()).setCheckedState(true);
				perspective_button.get(stack.getSelectedElement()).setSwitchStateByUserClickEnabled(false);
			}
			else if (oldSel instanceof MElementContainer<?>)
				disconnectReferencedElementsFromPerspectiveWidgets((MElementContainer<? extends MUIElement>) oldSel);
		}
	};
	
	private void disconnectReferencedElementsFromPerspectiveWidgets(MElementContainer<? extends MUIElement> container)
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
					phComponent.removeComponent(refComponent);
				}
			}
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
	public boolean isLazy()
	{
		return true;
	}

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent)
	{
		if (perspectiveSwitcherPanel == null)
			initializedPerspectiveSwticherPanel((MPerspectiveStack)element);

		VerticalLayout perspectiveStackContent = new VerticalLayout();
		perspectiveStackContent.setSizeFull();
		element.setWidget(perspectiveStackContent);
	}

	private void initializedPerspectiveSwticherPanel(MPerspectiveStack perspectiveStack)
	{
		if (perspectiveSwitcherPanel != null)
			return;
		//initialize perspective switcher panel
		perspectiveStackForSwitcher = perspectiveStack;
		boolean iconsOnly = perspectiveStackForSwitcher.getTags().contains(Tags.ICONS_ONLY);
		
		perspectiveSwitcherPanel = new HorizontalLayout();
		perspectiveSwitcherPanel.setStyleName("perspectivepanel");
		perspectiveSwitcherPanel.setSizeUndefined();
		 
		 Button openPerspectiveButton = new Button("Open");
			openPerspectiveButton.addStyleName("vaaclipsebutton");
			openPerspectiveButton.addStyleName("icononly");
			openPerspectiveButton.setIcon(new ThemeResource("../vaaclipse_default_theme/img/open_perspective.png"));
			perspectiveSwitcherPanel.addComponent(openPerspectiveButton);
			
			openPerspectiveButton.addListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event)
				{
					openOpenPerspectiveWindow();
					
					//change focus
					Component parent = event.getButton().getParent();
	                while (parent != null) {
	                        if(parent instanceof Component.Focusable) {
	                                ((Component.Focusable) parent).focus();
	                                break;
	                        } else {
	                                parent = parent.getParent();
	                        }
	                }
				}
			});
			
			//add separator between openPerspectiveButton and perspective's buttons
			Label separator = new Label();
			separator.setSizeUndefined();
			separator.addStyleName("horizontalseparator");
			separator.setHeight("100%");
			perspectiveSwitcherPanel.addComponent(separator);
			
		 //add buttons to perspective switch panel	
		 for (final MPerspective perspective : perspectiveStackForSwitcher.getChildren())
		 {
			 Component button = createPerspectiveButton(perspective);
			 if (button != null)
				 perspectiveSwitcherPanel.addComponent(button);
		 }
	}

	private Component createPerspectiveButton(final MPerspective perspective)
	{
		if (!perspective.isVisible())
			return null;
		boolean iconsOnly = perspectiveStackForSwitcher.getTags().contains(Tags.ICONS_ONLY);
		String label = iconsOnly ? null : Commons.trim(perspective.getLabel());
		 String iconURI = Commons.trim(perspective.getIconURI());
		 
		 final TwoStateToolbarButton button = new TwoStateToolbarButton(label, iconURI);
		 
		 if (perspective.getTooltip() != null)
		 {
			 button.setDescription(perspective.getLocalizedTooltip());
		 }

		 button.addListener(new ClickListener() {

			 public void buttonClick(ClickEvent event)
			 {
				 MPerspectiveStack perspectiveStack = (MPerspectiveStack) (MElementContainer<?>) perspective.getParent();
				 switchPerspective(perspective);
			 }
		 });
		 
		 //TODO: replace VerticalLayout on more thin layout (for example SimpleLayout addon which consist of just one div)
//		 VerticalLayout wrapperLayout = new VerticalLayout();
//		 wrapperLayout.setSizeUndefined();
//		 wrapperLayout.addComponent(button);
//		 wrapperLayout.addListener(new LayoutEvents.LayoutClickListener() {
//
//			 @Override
//			 public void layoutClick(LayoutClickEvent event)
//			 {
//				 if (LayoutClickEvent.BUTTON_RIGHT == event.getButton())
//				 {
//					 lastClickedPerspective = perspective;
////					 menu.open(event.getClientX(), event.getClientY());
//				 }
//			 }
//		 });
		 
		 //Create context menu
		// Context menu
			ContextMenu menu = new ContextMenu();
			contextMenu2Button.put(menu, button);
			button2ContextMenu.put(button, menu);
			
			final ContextMenuItem showTextItem;
			
			final ContextMenuItem closeItem = menu.addItem("Close");
			//closeItem.setSeparatorVisible(true);
			
			if (iconsOnly)
				showTextItem = menu.addItem("Show Text");
			else
				showTextItem = menu.addItem("Hide Text");
			
			//showTextItem.addStyleName("close-perspective-item"); //bugfixing style for ie9 (context menu addon has bug for ie9)
			
			 menu.addItemClickListener(new ContextMenu.ContextMenuItemClickListener() {
				 
				 @Override
				public void contextMenuItemClicked(ContextMenuItemClickEvent event)
				{
					 ContextMenuItem clickedItem = (ContextMenuItem) event.getSource();
					 
					 if (clickedItem == closeItem)
						{
							if (perspective == activePerspective)
							{
								MPerspective prevRenderableAndVisiblePerspective = null, nextRenderableAndVisiblePerspective = null;
								boolean startSearch = false;
								for (MPerspective p : perspectiveStackForSwitcher.getChildren())
								{
									if (startSearch && p.isToBeRendered() && p.isVisible())
									{
										nextRenderableAndVisiblePerspective = p;
										break;
									}
									
									if (p == perspective)
										startSearch = true;
									
									if (!startSearch && p.isToBeRendered() && p.isVisible())
									{
										prevRenderableAndVisiblePerspective = p;
									}
								}
								
								MPerspective newSelectedPerspective = nextRenderableAndVisiblePerspective != null ? nextRenderableAndVisiblePerspective 
										: prevRenderableAndVisiblePerspective;
								
								if (newSelectedPerspective != null)
									switchPerspective(newSelectedPerspective);	
							}
							
							perspective.setToBeRendered(false);
						}
						else if (clickedItem == showTextItem)
						{
							if (perspectiveStackForSwitcher.getTags().contains(Tags.ICONS_ONLY))
								perspectiveStackForSwitcher.getTags().remove(Tags.ICONS_ONLY);
							else
								perspectiveStackForSwitcher.getTags().add(Tags.ICONS_ONLY);
						}
				}
			 });
		 
		 menu.setAsContextMenuOf(button);
		
		 perspective_button.put(perspective, button);
		 //return wrapperLayout;
		 return button;
	}

	@Override
	public void processContents(MElementContainer<MUIElement> element)
	{
		if (element.getChildren().isEmpty())
			return;

		MPerspectiveStack perspectiveStack = (MPerspectiveStack) (MElementContainer<?>) element;
		MPerspective selectedPerspective = perspectiveStack.getSelectedElement();

		if (selectedPerspective == null)
		{
			selectedPerspective = (MPerspective) findFirstRenderableAndVisibleElement(perspectiveStack);
			if (selectedPerspective != null)
				switchPerspective(selectedPerspective);
		}
		else if (!selectedPerspective.isToBeRendered() || !selectedPerspective.isVisible())
		{
			selectedPerspective = (MPerspective) findFirstRenderableAndVisibleElement(perspectiveStack);
			if (selectedPerspective != null)
				switchPerspective(selectedPerspective);
			else
				perspectiveStack.setSelectedElement(null);
		}
		else
		{
			// reset selected element (set selected element handler will work)
			perspectiveStack.setSelectedElement(null);
			switchPerspective(selectedPerspective);
		}

		refreshPerspectiveStackVisibility(perspectiveStack);
	}

	private void switchPerspective(MPerspective perspective)
	{
		if (perspective.isToBeRendered() && perspective.getWidget() == null)
			engine.createGui(perspective);
		partService.switchPerspective(perspective);
		this.activePerspective = perspective;
		if (perspective.getElementId() != null)
		{
			String perspectiveId = perspective.getElementId().trim();
			application.getContext().set("activePerspective", perspectiveId);
		}
	}

	private void refreshPerspectiveStackVisibility(MPerspectiveStack stack)
	{
		perspectiveSwitcherPanel.setVisible(stack.getChildren().size() > 0);
	}

	@Override
	public void addChildGui(MUIElement child, MElementContainer<MUIElement> element)
	{
		MPerspectiveStack stack = (MPerspectiveStack) (MElementContainer<?>) element;
		MPerspective p = (MPerspective) child;
		
		Component button = createPerspectiveButton(p);
		//shift on 2 - the first child - open perspective button, the second child - is separator
		int index = indexOf(child, element) + 2;
		perspectiveSwitcherPanel.addComponent(button, index);
		
		refreshPerspectiveStackVisibility(stack);
	}
	
	@Override
	public void removeChildGui(MUIElement child, MElementContainer<MUIElement> element)
	{
		MPerspectiveStack stack = (MPerspectiveStack) (MElementContainer<?>) element;
		MPerspective p = (MPerspective) child;
		
		Button button = perspective_button.get(p);
		perspectiveSwitcherPanel.removeComponent(button);
		
		ContextMenu menu = button2ContextMenu.remove(button);
		contextMenu2Button.remove(menu);
		
		refreshPerspectiveStackVisibility(stack);
	}
	
	private void openOpenPerspectiveWindow()
	{
		OptionDialog dlg = new OptionDialog();
		dlg.setCaption("Open perspective");
		dlg.setModal(true);
		dlg.setWidth("300");
		dlg.setHeight("600");
		dlg.addOption(0, "OK");
		dlg.addOption(1, "CANCEL");
		
		vaadinUI.addWindow(dlg);
		
		dlg.setComponentProvider(new OptionDialog.ComponentProvider() {
			
			Panel panel;
			Table list;
			IndexedContainer container = new IndexedContainer();			
			
			@Override
			public Component getComponent(OptionDialog optionDialog)
			{
				if (panel == null)
				{
					container.addContainerProperty(PERSPECTIVE_ICON, Resource.class, null);
					container.addContainerProperty(PERSPECTIVE_LABEL, String.class, null);
					
					for (MPerspective p : perspectiveStackForSwitcher.getChildren())
					{
						Item item = container.addItem(p.getElementId());
						item.getItemProperty(PERSPECTIVE_ICON).setValue(ResourceHelper.createResource(p.getIconURI()));
						item.getItemProperty(PERSPECTIVE_LABEL).setValue(p.getLabel());
					}
					
					panel = new Panel();
					list = new Table();
					panel.setContent(list);
					list.setSizeFull();
					list.setSelectable(true);
					list.setMultiSelect(false);
					list.setContainerDataSource(container);
					list.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
					list.setVisibleColumns(new Object[] { PERSPECTIVE_LABEL });
					list.setRowHeaderMode(Table.ROW_HEADER_MODE_ICON_ONLY);
					list.setItemIconPropertyId(PERSPECTIVE_ICON);
					list.setColumnExpandRatio(PERSPECTIVE_LABEL, 1);
					panel.setContent(list);
				}
				return panel;
			}
			
			@Override
			public void optionSelected(OptionDialog dlg, int optionId)
			{
				if (optionId == 0)
				{
					//selected perspective's elementId
					String perspectiveId = (String) list.getValue();
					if (perspectiveId != null)
					{
						MPerspective perspective = (MPerspective) modelService.find(perspectiveId, perspectiveStackForSwitcher);
						if (!perspective.isToBeRendered())
							perspective.setToBeRendered(true);
						switchPerspective(perspective);
					}
				}
				dlg.close();
			}
			
			@Override
			public void setMessage(String message) {}
		});
	}
	
	//-------------------------------------------------------
	//-------------------------------------------------------
	private void hideElementRecursive(MUIElement element) {
		if (element == null || element.getWidget() == null)
			return;
		
		if (element instanceof MPlaceholder) {
			MPlaceholder ph = (MPlaceholder) element;
			element = ph.getRef();
		}
		
		// Hide any floating windows
		if (element instanceof MWindow && element.getWidget() != null) {
			element.setVisible(false);
		}
		
		if (element instanceof MElementContainer<?>) {
			MElementContainer<?> container = (MElementContainer<?>) element;
			for (MUIElement childElement : container.getChildren()) {
				hideElementRecursive(childElement);
			}
			
			// OK, now process detached windows
			if (element instanceof MWindow) {
				for (MWindow w : ((MWindow) element).getWindows()) {
					hideElementRecursive(w);
				}
			} else if (element instanceof MPerspective) {
				for (MWindow w : ((MPerspective) element).getWindows()) {
					hideElementRecursive(w);
				}
			}
		}
	}

	private void showElementRecursive(MUIElement element) 
	{
		if (!element.isToBeRendered())
			return;
		
		if (element instanceof MPlaceholder && element.getWidget() != null) {
			MPlaceholder ph = (MPlaceholder) element;
			MUIElement ref = ph.getRef();
			ref.setCurSharedRef(ph);

			ComponentContainer phComponent = (ComponentContainer) ph.getWidget();
			Component refComponent = (Component) ph.getRef().getWidget();
			phComponent.addComponent(refComponent);

			element = ref;
			
			//top right folder
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
		
		if (element instanceof MContext) 
		{
			IEclipseContext context = ((MContext) element).getContext();
			if (context != null) 
			{
				IEclipseContext newParentContext = modelService
						.getContainingContext(element);
				if (context.getParent() != newParentContext) 
				{
					context.setParent(newParentContext);
				}
			}
		}

		// Show any floating windows
		if (element instanceof MWindow && element.getWidget() != null) {
			int visCount = 0;
			for (MUIElement kid : ((MWindow) element).getChildren()) {
				if (kid.isToBeRendered() && kid.isVisible())
					visCount++;
			}
			if (visCount > 0)
				element.setVisible(true);
		}

		if (element instanceof MElementContainer<?>) {
			MElementContainer<?> container = (MElementContainer<?>) element;
			List<MUIElement> kids = new ArrayList<MUIElement>(
					container.getChildren());
			for (MUIElement childElement : kids) {
				showElementRecursive(childElement);
			}

			// OK, now process detached windows
			if (element instanceof MWindow) {
				for (MWindow w : ((MWindow) element).getWindows()) {
					showElementRecursive(w);
				}
			} else if (element instanceof MPerspective) {
				for (MWindow w : ((MPerspective) element).getWindows()) {
					showElementRecursive(w);
				}
			}
		}
	}		
}
