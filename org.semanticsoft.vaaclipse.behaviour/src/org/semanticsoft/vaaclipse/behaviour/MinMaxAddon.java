/*******************************************************************************
 * Copyright (c) 2011 Kai Toedter, Rushan R. Gilmullin and others.
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
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
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
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.impl.MenuFactoryImpl;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.presentation.engine.PresentationEngine;
import org.semanticsoft.vaaclipse.presentation.renderers.GenericRenderer;

@SuppressWarnings("restriction")
public class MinMaxAddon {
	public static final String ADDONS_MINMAX_TRIM_STACK_ID = "org.semanticsoft.vaaclipse.behaviour.trimStackId";

	static String ID_SUFFIX = "(minimized)"; //$NON-NLS-1$

	// tags representing the min/max state
	public static String MINIMIZED = IPresentationEngine.MINIMIZED;
	public static String MAXIMIZED = IPresentationEngine.MAXIMIZED;
	public static String MINIMIZED_BY_ZOOM = IPresentationEngine.MINIMIZED_BY_ZOOM;

	private final IEventBroker eventBroker;
	private final EModelService modelService;
	protected boolean ignoreTagChanges;

	@Inject
	private IEclipseContext context;

	@Inject
	private EHandlerService handlerService;

	private final MApplication application;

	@Inject
	public MinMaxAddon(IEventBroker eventBroker, EModelService modelService,
			MApplication application) {
		this.application = application;
		this.eventBroker = eventBroker;
		this.modelService = modelService;
	}

	@PostConstruct
	void hookListeners() {
		eventBroker.subscribe(UIEvents.ApplicationElement.TOPIC_TAGS, tagListener);
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT, selectPerspectiveHandler);
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_VISIBLE, visibilityHandler);
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
							System.out.println(toolBar.isVisible());
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
		createTrim(element);
		element.setVisible(false);
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
					presentationEngine.refreshGui(trimBar);
				}
			}
		}
		presentationEngine.refreshGui(window);
	}

	protected void restore(MUIElement element) {
		element.getTags().remove(MINIMIZED_BY_ZOOM);
		element.getTags().remove(MINIMIZED);
		element.setVisible(true);
		
		MWindow window = getWindowFor(element);
		String trimId = element.getElementId() + getMinimizedElementSuffix(element);
		//remove the trimbar
		PresentationEngine presentationEngine = (PresentationEngine) application.getContext().get(IPresentationEngine.class);
		MToolBar barToBeRemoved = null;
		MTrimBar currentTrimBar = null;
		if (window instanceof MTrimmedWindow) {
			MTrimmedWindow trimmedWindow = (MTrimmedWindow) window;
			List<MTrimBar> trimBars = trimmedWindow.getTrimBars();
			for (MTrimBar trimBar : trimBars) {
				currentTrimBar = trimBar;
				if (trimBar.getSide() == SideValue.LEFT || trimBar.getSide() == SideValue.RIGHT || trimBar.getSide() == SideValue.BOTTOM) {
					List<MTrimElement> children = trimBar.getChildren();
					for (MTrimElement child : children) {
						MToolBar bar = (MToolBar) child;
						if (bar.getElementId().equals(trimId))
							barToBeRemoved = bar;
					}
				}
			}
		}
		
		if (barToBeRemoved != null) {
			
			barPerspectiveInfo.remove(barToBeRemoved);
			
			currentTrimBar.getChildren().remove(barToBeRemoved);
			if (currentTrimBar.getChildren().size() == 0) {
				currentTrimBar.setVisible(false);
			}
			presentationEngine.refreshGui(currentTrimBar);
		}
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
		

		// now let the parent check if the children are visible
		GenericRenderer parentRenderer = (GenericRenderer) element.getParent().getRenderer();
		if (parentRenderer != null) {
			parentRenderer.refreshPlatformElement(element.getParent());
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
	
	private Map<MToolBar, MPerspective> barPerspectiveInfo = new HashMap<MToolBar, MPerspective>();

	private void createTrim(MUIElement element) {
		MTrimmedWindow window = (MTrimmedWindow) getWindowFor(element);
		MPerspective activePerspective = modelService.getActivePerspective(window);

		// Is there already a TrimControl there ?
		String trimId = element.getElementId() + getMinimizedElementSuffix(element);
		System.out.println("Trim Id: " + trimId);
		MToolBar toolBar = (MToolBar) modelService.find(trimId, window);
		
		if (toolBar == null) {
			toolBar = MenuFactoryImpl.eINSTANCE.createToolBar();
			toolBar.setElementId(trimId);
			
			MDirectToolItem toolItem = MenuFactoryImpl.eINSTANCE.createDirectToolItem();
			toolItem.setIconURI("platform:/plugin/org.semanticsoft.vaaclipse.resources/VAADIN/themes/vaadock_eclipse_demo/img/restore1.png");
			toolItem.setContributionURI("bundleclass://org.semanticsoft.vaaclipse.behaviour/org.semanticsoft.vaaclipse.behaviour.RestoreHandler");
			toolItem.setContainerData(element.getElementId());

			toolBar.getChildren().add(toolItem);
			// Check if we have a cached location
			MTrimBar bar = getBarForElement(element, window);
			bar.getChildren().add(toolBar);
			bar.setVisible(true);

			// get the parent trim bar, see bug 320756
			PresentationEngine presentationEngine = (PresentationEngine) context.get(IPresentationEngine.class);

			if (element instanceof MPlaceholder)
			{
				MDirectToolItem partItem = MenuFactoryImpl.eINSTANCE.createDirectToolItem();
				partItem.setContributionURI("bundleclass://org.semanticsoft.vaaclipse.behaviour/org.semanticsoft.vaaclipse.behaviour.FastViewHandler");

				MUILabel labelElement = getLabelElement(element);
				if (labelElement != null)
				{
					partItem.setIconURI(labelElement.getIconURI());
					toolBar.getChildren().add(partItem);
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

					MDirectToolItem partItem = MenuFactoryImpl.eINSTANCE.createDirectToolItem();
					partItem.setContributionURI("bundleclass://org.semanticsoft.vaaclipse.behaviour/org.semanticsoft.vaaclipse.behaviour.FastViewHandler");

					MUILabel labelElement = getLabelElement(stackElement);
					partItem.setIconURI(labelElement.getIconURI());
					toolBar.getChildren().add(partItem);
				}	
			}

			if (bar.getWidget() == null) {
				// ask it to be rendered
				bar.setToBeRendered(true);

				// create the widget
				presentationEngine.createGui(bar);
			} else {
				toolBar.setToBeRendered(true);
				presentationEngine.createGui(toolBar);
				presentationEngine.refreshGui(bar);
			}

			presentationEngine.refreshGui(window);
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
			toolBar.setToBeRendered(true);
		}
		
		barPerspectiveInfo.put(toolBar, activePerspective);
	}

	private MUILabel getLabelElement(MUIElement element) {
		if (element instanceof MPlaceholder) {
			element = ((MPlaceholder) element).getRef();
		}

		return (MUILabel) (element instanceof MUILabel ? element : null);
	}

	private MTrimBar getBarForElement(MUIElement element, MTrimmedWindow window) {
		SideValue side = SideValue.LEFT;
		//SideValue side = HierarchyUtils.findNearestSide(window, element);
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

}
