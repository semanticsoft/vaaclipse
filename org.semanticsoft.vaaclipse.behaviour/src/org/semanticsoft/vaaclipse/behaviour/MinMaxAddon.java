/*******************************************************************************
 * Copyright (c) 2011, 2012 Kai Toedter, Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 	   Kai Toedter - initial API and implementation
 *     Rushan R. Gilmullin -  completion
 *******************************************************************************/

package org.semanticsoft.vaaclipse.behaviour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.impl.MenuFactoryImpl;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.commons.geom.Bounds;
import org.semanticsoft.vaaclipse.api.Events;
import org.semanticsoft.vaaclipse.api.WidgetInfo;
import org.semanticsoft.vaaclipse.presentation.engine.PresentationEngine;
import org.semanticsoft.vaaclipse.presentation.renderers.GenericRenderer;

@SuppressWarnings("restriction")
public class MinMaxAddon {
	public static final String ADDONS_MINMAX_TRIM_STACK_ID = "org.semanticsoft.vaaclipse.behaviour.trimStackId";

	static final String ID_SUFFIX = "(minimized)"; //$NON-NLS-1$

	// tags representing the min/max state
	public static final String MINIMIZED = IPresentationEngine.MINIMIZED;
	public static final String MAXIMIZED = IPresentationEngine.MAXIMIZED;
	public static final String MINIMIZED_BY_ZOOM = IPresentationEngine.MINIMIZED_BY_ZOOM;

	private final IEventBroker eventBroker;
	private final EModelService modelService;
	protected boolean ignoreTagChanges;

	@Inject
	private IEclipseContext context;
	
	@Inject
	EPartService partService;

	@Inject
	private EHandlerService handlerService;
	
	@Inject
	private WidgetInfo widgetInfo;

	private final MApplication application;
	
	private Map<MToolBar, MPerspective> barPerspectiveInfo = new HashMap<MToolBar, MPerspective>();
	private Set<MUIElement> minimizedElements = new HashSet<>();
	
	//TODO: dispose elements of this map
	public Map<MToolItem, MPart> item2Element = new HashMap<>();
	public Map<MPart, MUIElement> part2element = new HashMap<>();

	@Inject
	public MinMaxAddon(IEventBroker eventBroker, EModelService modelService,
			MApplication application) {
		this.application = application;
		this.eventBroker = eventBroker;
		this.modelService = modelService;
	}
	
	private EventHandler widgetHandler = new EventHandler() {
		public void handleEvent(org.osgi.service.event.Event event) {
			Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);
			if (!minimizedElements.contains(changedObj))
				return;
			MUIElement minimizedElement = (MUIElement) changedObj;
			if (minimizedElement.getWidget() != null) {
				MToolBar toolBar = getToolBarForMinimizedElement(minimizedElement);
				updateToolBar(minimizedElement, toolBar);
			}
		}
	};
	
	private EventHandler toBeRenderedHandler = new EventHandler() {
		public void handleEvent(org.osgi.service.event.Event event) {
			MUIElement changedElement = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);

			// if one of the kids changes state, re-scrape the CTF
			MUIElement parentElement = changedElement.getParent();
			if (minimizedElements.contains(parentElement)) {
				updateToolBar(parentElement, getToolBarForMinimizedElement(parentElement));
			}
		}
	};
	
	private EventHandler childrenHandler = new EventHandler() {
		public void handleEvent(org.osgi.service.event.Event event) {
			Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);
			
			// if a child has been added or removed, re-scape the CTF
			if (minimizedElements.contains(changedObj)) {
				updateToolBar((MUIElement) changedObj, getToolBarForMinimizedElement((MUIElement) changedObj));
			}
		}
	};

	@PostConstruct
	void postConstruct() {
		eventBroker.subscribe(UIEvents.ApplicationElement.TOPIC_TAGS, tagListener);
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT, selectPerspectiveHandler);
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_VISIBLE, visibilityHandler);
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_WIDGET, widgetHandler);
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_CHILDREN, childrenHandler);
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_TOBERENDERED, toBeRenderedHandler);

		//context.set(Behaviour.class, this);
	}
	
	@PreDestroy
	void preDestroy()
	{
		eventBroker.unsubscribe(tagListener);
		eventBroker.unsubscribe(selectPerspectiveHandler);
		eventBroker.unsubscribe(visibilityHandler);
		eventBroker.unsubscribe(widgetHandler);
		eventBroker.unsubscribe(childrenHandler);
		eventBroker.unsubscribe(toBeRenderedHandler);	
	}

	private final EventHandler tagListener = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (ignoreTagChanges) {
				return;
			}

			Object changedObj = event.getProperty(EventTags.ELEMENT);
			String eventType = (String) event.getProperty(UIEvents.EventTags.TYPE);
			String tag = (String) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			String oldVal = (String) event.getProperty(UIEvents.EventTags.OLD_VALUE);

			if (!(changedObj instanceof MUIElement)) {
				return;
			}

			final MUIElement changedElement = (MUIElement) changedObj;

			if (UIEvents.EventTypes.ADD.equals(eventType)) {
				if (MINIMIZED.equals(tag)) {
					minimize(changedElement);
				} else if (MAXIMIZED.equals(tag)) {
					maximize(changedElement);
				}
			} else if (UIEvents.EventTypes.REMOVE.equals(eventType)) {
				if (MINIMIZED.equals(oldVal)) {
					restore(changedElement);
				} else if (MAXIMIZED.equals(oldVal)) {
					unzoom(changedElement);
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

			// Gather up the elements that are being 'hidden' by this change
			MUIElement oldSel = (MUIElement) event.getProperty(UIEvents.EventTags.OLD_VALUE);

			if (oldSel != null)
			{
				for (Map.Entry<MToolBar, MPerspective> entry : barPerspectiveInfo.entrySet())
				{
					if (oldSel == entry.getValue())
					{
						MToolBar toolBar = entry.getKey();
						if (toolBar.isVisible())
						{
							toolBar.setVisible(false);
						}
					}
				}
			}
			
			if (stack.getSelectedElement() != null)
			{
				for (Map.Entry<MToolBar, MPerspective> entry : barPerspectiveInfo.entrySet())
				{
					if (stack.getSelectedElement() == entry.getValue())
					{
						MToolBar toolBar = entry.getKey();
						if (!toolBar.isVisible())
						{
							toolBar.setVisible(true);
							MTrimBar parentTrimBar = (MTrimBar)(MElementContainer<?>)toolBar.getParent();
							if (!parentTrimBar.isVisible())
								parentTrimBar.setVisible(true);
						}
					}
				}
			}
		}
	};
	
	private final EventHandler visibilityHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			MUIElement changedElement = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);

			if (changedElement instanceof MTrimElement)
			{
				MTrimBar parentTrimBar = (MTrimBar)(MElementContainer<?>)changedElement.getParent();
				if (!parentTrimBar.isVisible())
					return;
				
				boolean hasVisibleChilds = false;
				for (MTrimElement trimElelement : parentTrimBar.getChildren())
				{
					if (trimElelement.isVisible())
					{
						hasVisibleChilds = true;
						break;
					}
				}
				
				if (!hasVisibleChilds)
					parentTrimBar.setVisible(false);
			}
		}
	};

	private void setState(MUIElement element, String state) {
		element.getTags().remove(MINIMIZED_BY_ZOOM);
		if (MINIMIZED.equals(state)) {
			element.getTags().remove(MAXIMIZED);
			element.getTags().add(MINIMIZED);
		} else if (MAXIMIZED.equals(state)) {
			element.getTags().remove(MINIMIZED);
			element.getTags().add(MAXIMIZED);
		} else {
			element.getTags().remove(MINIMIZED);
			element.getTags().remove(MAXIMIZED);
		}
	}

	protected void minimize(MUIElement element) {
		if (!element.isToBeRendered()) {
			return;
		}
		MToolBar toolBar = createTrim(element);
		element.setVisible(false);
		minimizedElements.add(element);
		// Activate a part other than the trimStack so that if the tool item is pressed
		// immediately it will still open the stack.
		partService.requestActivation();
		
		//send the minimize event
		Map<String, Object> parameters = new HashMap<>();
		parameters.put(Events.MinMaxEvents.PARAMETER_ELEMENT, element);
		parameters.put(Events.MinMaxEvents.PARAMETER_TOOLBAR, toolBar);
		parameters.put(Events.MinMaxEvents.PARAMETER_TRIMBAR, toolBar.getParent());
		eventBroker.send(Events.MinMaxEvents.EVENT_MINIMIZE_ELEMENT, parameters);
	}

	protected void unzoom(MUIElement element) {
		MWindow win = modelService.getTopLevelWindowFor(element);

		List<MPartStack> stacks = modelService.findElements(win, null, MPartStack.class, null, EModelService.PRESENTATION);
		for (MPartStack theStack : stacks) {
			if (theStack.getWidget() != null && theStack.getTags().contains(MINIMIZED)
					&& theStack.getTags().contains(MINIMIZED_BY_ZOOM)) {
				theStack.getTags().remove(MINIMIZED);
			}
		}
		
		List<MPlaceholder> placeholders = modelService.findElements(win, null, MPlaceholder.class, null, EModelService.PRESENTATION);
		for (MPlaceholder ph : placeholders) {
			if (ph.getWidget() != null && ph.getTags().contains(MINIMIZED)
					&& ph.getTags().contains(MINIMIZED_BY_ZOOM)) {
				ph.getTags().remove(MINIMIZED);
			}
		}
	}

	public void resetWindows(MUIElement element) {
		ignoreTagChanges = true;
		MWindow window = modelService.getTopLevelWindowFor(element);

		List<MPartStack> stacks = modelService.findElements(window, null, MPartStack.class, null,
				EModelService.PRESENTATION);
		for (MPartStack partStack : stacks) {
			if (partStack.getWidget() != null) {
				if (partStack.getTags().contains(MINIMIZED)) {
					partStack.getTags().remove(MINIMIZED);
				}
				if (partStack.getTags().contains(MAXIMIZED)) {
					partStack.getTags().remove(MAXIMIZED);
				}
				if (partStack.getTags().contains(MINIMIZED_BY_ZOOM)) {
					partStack.getTags().remove(MINIMIZED_BY_ZOOM);
				}
			}
			partStack.setVisible(true);
		}
		ignoreTagChanges = false;

		PresentationEngine presentationEngine = (PresentationEngine) context.get(IPresentationEngine.class);
		if (window instanceof MTrimmedWindow) {
			MTrimmedWindow trimmedWindow = (MTrimmedWindow) window;
			List<MTrimBar> trimBars = trimmedWindow.getTrimBars();
			for (MTrimBar trimBar : trimBars) {
				if (trimBar.getSide() == SideValue.LEFT || trimBar.getSide() == SideValue.RIGHT) {
					trimBar.getChildren().clear();
					trimBar.setVisible(false);
				}
			}
		}
	}

	protected void restore(MUIElement element) {
		
		//if the element is minimized, then the widget of this element maybe (but not fact) detached from its real parent
		//and attached to popup window and the widget's visible property is true. We must undo this changes
		//before start the restore operation
		Object currentParent = widgetInfo.getParent(element.getWidget());
		Object realParent = element.getParent().getWidget();
		if (currentParent != realParent) //if the current parent is not equal (or null) the real parent,
		{//then reattach the widget to real parent
			((GenericRenderer)element.getParent().getRenderer()).processContents(element.getParent());
		}
		
		element.getTags().remove(MINIMIZED_BY_ZOOM);
		element.getTags().remove(MINIMIZED);
		element.setVisible(true);
		minimizedElements.remove(element);
		
		//remove the trimbar
		PresentationEngine presentationEngine = (PresentationEngine) application.getContext().get(IPresentationEngine.class);
		
		MTrimBar trimBar = getTrimBarForMinimizedElement(element);
		MToolBar toolBar = getToolBarForMinimizedElement(element);
		
		if (trimBar != null || toolBar != null) {
			
			barPerspectiveInfo.remove(toolBar);
			
			toolBar.setToBeRendered(false);
			trimBar.getChildren().remove(toolBar);
			if (trimBar.getChildren().size() == 0) {
				trimBar.setVisible(false);
			}
		}
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put(Events.MinMaxEvents.PARAMETER_ELEMENT, element);
		eventBroker.send(Events.MinMaxEvents.EVENT_RESTORE_ELEMENT, parameters);
	}

	protected void maximize(MUIElement element) {
		if (!element.isToBeRendered()) {
			return;
		}

		MWindow mWindow = getWindowFor(element);
		MPerspective persp = modelService.getActivePerspective(mWindow);

		List<String> maxTag = new ArrayList<String>();
		maxTag.add(MAXIMIZED);
		List<MUIElement> curMax = modelService.findElements(persp == null ? mWindow : persp, null, MUIElement.class,
				maxTag);
		if (curMax.size() > 0) {
			for (MUIElement maxElement : curMax) {
				if (maxElement == element) {
					continue;
				}
				ignoreTagChanges = true;
				try {
					maxElement.getTags().remove(MAXIMIZED);
				} finally {
					ignoreTagChanges = false;
				}
			}
		}

		//Stacks
		List<MPartStack> stacks = modelService.findElements(persp == null ? mWindow : persp, null, MPartStack.class,
				null, EModelService.PRESENTATION);
		for (MPartStack theStack : stacks) {
			if (theStack == element || !theStack.isToBeRendered()) {
				continue;
			}

			// Exclude stacks in DW's
//			if (getWindowFor(theStack) != mWindow) {
//				continue;
//			}

			int location = modelService.getElementLocation(theStack);
			if (location != EModelService.IN_SHARED_AREA && theStack.getWidget() != null
					&& !theStack.getTags().contains(MINIMIZED)) {
				theStack.getTags().add(MINIMIZED_BY_ZOOM);
				theStack.getTags().add(MINIMIZED);
			}
		}
		
		//Placeholders
		List<MPlaceholder> placeholders = modelService.findElements(persp == null ? mWindow : persp, null, MPlaceholder.class,
				null, EModelService.PRESENTATION);
		for (MPlaceholder thePlaceholder : placeholders) {
			if (thePlaceholder == element || !thePlaceholder.isToBeRendered() || !(thePlaceholder.getRef() instanceof MElementContainer<?>)) {
				continue;
			}

			// Exclude stacks in DW's
//			if (getWindowFor(thePlaceholder) != mWindow) {
//				continue;
//			}

			int location = modelService.getElementLocation(thePlaceholder);
			if (location != EModelService.IN_SHARED_AREA && thePlaceholder.getWidget() != null
					&& !thePlaceholder.getTags().contains(MINIMIZED)) {
				thePlaceholder.getTags().add(MINIMIZED_BY_ZOOM);
				thePlaceholder.getTags().add(MINIMIZED);
			}
		}
	}

	private MWindow getWindowFor(MUIElement element) {
		MUIElement parent = element.getParent();

		// We rely here on the fact that a DW's 'getParent' will return
		// null since it's not in the 'children' hierarchy
		while (parent != null && !(parent instanceof MWindow)) {
			parent = parent.getParent();
		}

		// A detached window will end up with getParent() == null
		return (MWindow) parent;
	}

	private MToolBar createTrim(MUIElement element) {
		MTrimmedWindow window = (MTrimmedWindow) getWindowFor(element);
		MPerspective activePerspective = modelService.getActivePerspective(window);
		
		if (element.getElementId() == null)
			element.setElementId(UUID.randomUUID().toString());
		
		// Is there already a TrimControl there ?
		String trimId = element.getElementId() + getMinimizedElementSuffix(element);
		MToolBar toolBar = (MToolBar) modelService.find(trimId, window);
		
		if (toolBar == null) {
			toolBar = MenuFactoryImpl.eINSTANCE.createToolBar();
			toolBar.setElementId(trimId);
			
			// Check if we have a cached location
			MTrimBar bar = getBarForElement(element, window);
			bar.getChildren().add(toolBar);
			bar.setVisible(true);

			// get the parent trim bar, see bug 320756
			PresentationEngine presentationEngine = (PresentationEngine) context.get(IPresentationEngine.class);

			updateToolBar(element, toolBar);

			if (bar.getWidget() == null) {
				// ask it to be rendered
				bar.setToBeRendered(true);

				// create the widget
				presentationEngine.createGui(bar);
			} else {
				toolBar.setToBeRendered(true);
				presentationEngine.createGui(toolBar);
			}
		} else {
			// get the parent trim bar, see bug 320756
			MUIElement parent = toolBar.getParent();
			parent.setVisible(true);
			if (parent.getWidget() == null) {
				// ask it to be rendered
				parent.setToBeRendered(true);
				// create the widget
				PresentationEngine presentationEngine = (PresentationEngine) context.get(IPresentationEngine.class);
				presentationEngine.createGui(parent);
			}
			
			updateToolBar(element, toolBar);
			
			toolBar.setToBeRendered(true);
		}
		
		barPerspectiveInfo.put(toolBar, activePerspective);
		return toolBar;
	}

	private void updateToolBar(MUIElement element, MToolBar toolBar)
	{
		toolBar.getChildren().clear();
		
		MDirectToolItem toolItem = MenuFactoryImpl.eINSTANCE.createDirectToolItem();
		toolItem.setIconURI("platform:/plugin/org.semanticsoft.vaaclipse.resources/VAADIN/themes/vaaclipse_default_theme/img/restore1.png");
		toolItem.setContributionURI("bundleclass://org.semanticsoft.vaaclipse.behaviour/org.semanticsoft.vaaclipse.behaviour.RestoreHandler");
		//toolItem.getTransientData().put("minimizedStack", element);
		toolItem.setContainerData(element.getElementId());
		toolBar.getChildren().add(toolItem);
		
		if (element instanceof MPlaceholder)
		{
			MDirectToolItem partItem = MenuFactoryImpl.eINSTANCE.createDirectToolItem();
			partItem.setElementId(UUID.randomUUID().toString());
			partItem.setContributionURI("bundleclass://org.semanticsoft.vaaclipse.behaviour/org.semanticsoft.vaaclipse.behaviour.FastViewHandler");

			MUILabel labelElement = getLabelElement(element);
			if (labelElement != null)
			{
				if (labelElement.getIconURI() != null && labelElement.getIconURI().trim().length() > 0)
					partItem.setIconURI(labelElement.getIconURI());
				else
					partItem.setIconURI("platform:/plugin/org.semanticsoft.vaaclipse.resources/VAADIN/themes/vaaclipse_default_theme/img/editor_area.png");
				toolBar.getChildren().add(partItem);
				MPart part = getLeafPart(element);
				item2Element.put(partItem, part);
				part2element.put(part, element);
			}
		}
		else
		{
			@SuppressWarnings("unchecked")
			MElementContainer<MUIElement> partStack = (MElementContainer<MUIElement>) element;
			for (MUIElement stackElement : partStack.getChildren()) {
				if (!stackElement.isToBeRendered()) {
					continue;
				}
				
				MUIElement el = stackElement instanceof MPlaceholder ? ((MPlaceholder)stackElement).getRef() : stackElement;
				if (el instanceof MPart)
				{
					MDirectToolItem partItem = MenuFactoryImpl.eINSTANCE.createDirectToolItem();
					partItem.setContributionURI("bundleclass://org.semanticsoft.vaaclipse.behaviour/org.semanticsoft.vaaclipse.behaviour.FastViewHandler");

					MUILabel labelElement = getLabelElement(stackElement);
					String iconURI = labelElement.getIconURI();
					if (iconURI == null || iconURI.trim().isEmpty())
						iconURI = "platform:/plugin/org.semanticsoft.vaaclipse.resources/VAADIN/themes/vaaclipse_default_theme/img/blank_part_label.png";
					partItem.setIconURI(iconURI);
					toolBar.getChildren().add(partItem);
					
					
					item2Element.put(partItem, (MPart) el);	
					part2element.put((MPart)el, element);
				}
			}
		}
	}
	
	private MPart getLeafPart(MUIElement element) {
		if (element instanceof MPlaceholder)
			return getLeafPart(((MPlaceholder) element).getRef());

		if (element instanceof MElementContainer<?>)
			return getLeafPart(((MElementContainer<?>) element).getSelectedElement());

		if (element instanceof MPart)
			return (MPart) element;

		return null;
	}

	private MUILabel getLabelElement(MUIElement element) {
		if (element instanceof MPlaceholder) {
			element = ((MPlaceholder) element).getRef();
		}

		return (MUILabel) (element instanceof MUILabel ? element : null);
	}

	private MTrimBar getBarForElement(MUIElement element, MTrimmedWindow window) {
		SideValue side = SideValue.LEFT;
//		SideValue side = getCachedBar(element);
//		if (side == null) {
			Bounds winBounds = widgetInfo.getBounds(window);
			int winCenterX = winBounds.w / 2;
			widgetInfo.invalidateBounds(window);
			Bounds stackBounds = widgetInfo.getBounds(window, element);
			int stackCenterX = stackBounds.x + (stackBounds.w / 2);
			side = stackCenterX < winCenterX ? SideValue.LEFT : SideValue.RIGHT;
//		}
		MTrimBar bar = modelService.getTrim(window, side);

		return bar;
	}

	private String getMinimizedElementSuffix(MUIElement element) {
		String id = ID_SUFFIX;
		MPerspective persp = modelService.getPerspectiveFor(element);
		if (persp != null) {
			id = '(' + persp.getElementId() + ')';
		}
		return id;
	}

//	@Override
	public MTrimBar getTrimBarForMinimizedElement(MUIElement element)
	{
		MToolBar toolBar = getToolBarForMinimizedElement(element);
		return (MTrimBar)(MElementContainer<?>)toolBar.getParent();
	}

//	@Override
	public MToolBar getToolBarForMinimizedElement(MUIElement element)
	{
		MTrimmedWindow window = (MTrimmedWindow) getWindowFor(element);
		String trimId = element.getElementId() + getMinimizedElementSuffix(element);
		return (MToolBar) modelService.find(trimId, window);
	}
	
//	@Override
	public MUIElement getMinimizedParentForPart(MPart part)
	{
		return part2element.get(part);
	}
}
