/*******************************************************************************
 * Copyright (c) 2011 Kai Toedter, Rushan R. Gilmullin and others.
 * 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Kai Toedter - initial API and implementation
 *     Rushan R. Gilmullin - adoption to vaaclipse and other changes
 ******************************************************************************/

package org.semanticsoft.vaaclipse.presentation.engine;

import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.di.PersistState;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.MContribution;
import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.equinox.app.IApplication;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.presentation.renderers.GenericRenderer;

/**
 * This engine was adopted from Kai Toedter's generic renderer project. I place it in vaaclipse packages temproraly -
 * until the generic renderer will be the part of eclipse project.
 * @author Kai Toedter
 */
@SuppressWarnings("restriction")
public class GenericPresentationEngine implements PresentationEngine {

	protected MApplication theApp;
	
	@Inject
	protected Logger logger;

	protected RendererFactory rendererFactory;

	@Inject
	EModelService modelService;

	@Inject
	EventBroker eventBroker;

	private final EventHandler childrenHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);

			if (!(changedObj instanceof MElementContainer<?>)) {
				return;
			}

			@SuppressWarnings("unchecked")
			MElementContainer<MUIElement> changedElement = (MElementContainer<MUIElement>) changedObj;
			
			// if changedElement has no GUI, not process adding and removing. Add will be processed later, in createGui for changedElement - 
			//the child GUI will be created in processContent
			if (changedElement.getWidget() == null)
				return;
			
			GenericRenderer parentRenderer = (GenericRenderer) changedElement.getRenderer();
			
			if (parentRenderer == null)
				return;
			
			String eventType = (String) event.getProperty(UIEvents.EventTags.TYPE);
			if (UIEvents.EventTypes.ADD.equals(eventType)) {
				MUIElement added = (MUIElement) event.getProperty(UIEvents.EventTags.NEW_VALUE);
				GenericRenderer renderer = rendererFactory.getRenderer(added);
				if (added.getWidget() == null && !renderer.isLazy())
					createGui(added);
				if (changedElement.getWidget() != null && added.isToBeRendered())
					parentRenderer.addChildGui(added, changedElement);
				
				
				// If the element being added is a placeholder, check to see
				// if it's 'globally visible' and, if so, remove all other
				// 'local' placeholders referencing the same element.
				int newLocation = modelService.getElementLocation(added);
				if (newLocation == EModelService.IN_SHARED_AREA || newLocation == EModelService.OUTSIDE_PERSPECTIVE) 
				{
					MWindow topWin = modelService.getTopLevelWindowFor(added);
					modelService.hideLocalPlaceholders(topWin, null);
				}
			}
			else if (UIEvents.EventTypes.REMOVE.equals(eventType)) 
			{
				MUIElement removed = (MUIElement) event.getProperty(UIEvents.EventTags.OLD_VALUE);
				
				// Ensure that the element about to be removed is not the
				// selected element
				if (changedElement.getSelectedElement() == removed)
					changedElement.setSelectedElement(null);
				
				if (removed.getWidget() != null && changedElement.getWidget() != null && removed.isToBeRendered())
					parentRenderer.removeChildGui(removed, changedElement);
			}
		}
	};
	
	private EventHandler toBeRenderedHandler = new EventHandler() {
		public void handleEvent(Event event) {

			MUIElement changedElement = (MUIElement) event
					.getProperty(UIEvents.EventTags.ELEMENT);
			MElementContainer<?> parent = changedElement.getParent();
			
			if (parent == null)
				return;
			
			GenericRenderer parentRenderer = (GenericRenderer) parent.getRenderer();
			
			if (parentRenderer == null)
				return;

			// Handle Detached Windows
			if (parent == null) {
				parent = (MElementContainer<?>) ((EObject) changedElement)
						.eContainer();
			}

			// If the parent isn't displayed who cares?
			if (!(parent instanceof MApplication)
					&& (parent == null || parent.getWidget() == null))
				return;

			if (changedElement.isToBeRendered()) 
			{
				GenericRenderer renderer = rendererFactory.getRenderer(changedElement);
				if (changedElement.getWidget() == null && !renderer.isLazy())
					createGui(changedElement);
				
				parentRenderer.addChildGui(changedElement, (MElementContainer<MUIElement>) parent);				
			} 
			else {
				// Ensure that the element about to be removed is not the
				// selected element
				if (parent.getSelectedElement() == changedElement)
					parent.setSelectedElement(null);
				
				// Un-maximize the element before tearing it down
				if (changedElement.getTags().contains(MAXIMIZED))
					changedElement.getTags().remove(MAXIMIZED);
				
				removeGui(changedElement);
			}

		}
	};

	private final EventHandler visibilityHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			MUIElement changedElement = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
			
			GenericRenderer renderer = (GenericRenderer) changedElement.getRenderer();
			if (renderer == null) {
				return;
			}
			
			renderer.setVisible(changedElement, changedElement.isVisible());
		}
	};

	@Override
	public Object createGui(MUIElement element, Object parentWidget, IEclipseContext parentContext) {
		System.out.println("GenericPresentationEngine.createGui(): This method should not be used.");
		return null;
	}

	@Override
	public Object createGui(MUIElement element, MElementContainer<MUIElement> parent, IEclipseContext parentContext) {
		if (!element.isToBeRendered()) return null;
		
		GenericRenderer renderer = rendererFactory.getRenderer(element);
		
		Object currentWidget = element.getWidget();
		if (currentWidget != null) {

//			Object control = currentWidget;		
//			Object parentWidget = parent.getWidget();
//			if (parentWidget instanceof ComponentContainer) {
//				ComponentContainer currentParent = (ComponentContainer) control.getParent();
//				if (currentParent != null && currentParent != parentWidget) {
//					currentParent.removeComponent(control);
//				}
//			}
			
			// Reparent the context (or the kid's context)
			if (element instanceof MContext) {
				IEclipseContext ctxt = ((MContext) element).getContext();
				if (ctxt != null)
					ctxt.setParent(parentContext);
			} else {
				List<MContext> childContexts = modelService.findElements(
						element, null, MContext.class, null);
				for (MContext c : childContexts) {
					// Ensure that we only reset the context of our direct
					// children
					MUIElement kid = (MUIElement) c;
					MUIElement _parent = kid.getParent();
					if (_parent == null && kid.getCurSharedRef() != null)
						_parent = kid.getCurSharedRef().getParent();
					if (!(element instanceof MPlaceholder) && _parent != element)
						continue;

					if (c.getContext() != null
							&& c.getContext().getParent() != parentContext) {
						c.getContext().setParent(parentContext);
					}
				}
			}

			// Now that we have a widget let the parent (if any) know
			if (element.getParent() instanceof MUIElement) {
				MElementContainer<MUIElement> parentElement = element.getParent();
				GenericRenderer parentRenderer = (GenericRenderer) parentElement.getRenderer();
				if (parentRenderer != null)
				{
					//TODO: check is this needed
					//parentRenderer.refreshPlatformElement(parentElement);
					//old swt specific code:
					//parentRenderer.childRendered(parentElement, element);
				}
			}
			return element.getWidget();
		}
		
		if (element instanceof MContext) {

			MContext ctxt = (MContext) element;

			// Assert.isTrue(ctxt.getContext() == null,
			// "Before rendering Context should be null");
			if (ctxt.getContext() == null) {
				IEclipseContext eclipseContext = getContext(parent).createChild(getContextName(element));
				populateModelInterfaces(ctxt, eclipseContext, element.getClass().getInterfaces());
				ctxt.setContext(eclipseContext);
				
				// make sure the context knows about these variables that have
				// been defined in the model
				for (String variable : ctxt.getVariables()) {
					eclipseContext.declareModifiable(variable);
				}

				Map<String, String> props = ctxt.getProperties();
				for (String key : props.keySet()) {
					eclipseContext.set(key, props.get(key));
				}

//				E4Workbench.processHierarchy(element);
				//eclipseContext.activate();
			}
		}
		
		element.setRenderer(renderer);
		
		renderer.createWidget(element, parent);
		if (element.getWidget() != null)
		{
			renderer.bindWidget(element);
		}
		
		if (element instanceof MElementContainer) {

			// first create the GUI for the children
			@SuppressWarnings("unchecked")
			MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) element;
			for (MUIElement child : container.getChildren()) {
				if (!renderer.isLazy())
					createGui(child);
			}

			// then let the renderer process them
			renderer.processContents(container);
		}
		
		//set element visibility
		if (element.isToBeRendered() && element.getWidget() != null)
			renderer.setVisible(element, element.isVisible());
		
		renderer.hookControllerLogic(element);

		return element.getWidget();
	}

	private String getContextName(MUIElement element) {
		StringBuilder builder = new StringBuilder(element.getClass().getSimpleName());
		String elementId = element.getElementId();
		if (elementId != null && elementId.length() != 0) {
			builder.append(" (").append(elementId).append(") ");
		}
		builder.append("Context");
		return builder.toString();
	}

	private static void populateModelInterfaces(MContext contextModel, IEclipseContext context, Class<?>[] interfaces) {
		for (Class<?> intf : interfaces) {
			context.set(intf.getName(), contextModel);
			populateModelInterfaces(contextModel, context, intf.getInterfaces());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object createGui(final MUIElement element) {
		// Obtain the necessary parent widget
		MElementContainer<MUIElement> parent = element.getParent();
		if (parent == null && ((EObject) element).eContainer() instanceof MElementContainer<?>) {
			parent = (MElementContainer<MUIElement>) ((EObject) element).eContainer();
		}
		
		// Obtain the necessary parent context
		IEclipseContext parentContext = null;
		if (element.getCurSharedRef() != null) {
			MPlaceholder ph = element.getCurSharedRef();
			parentContext = getContext(ph.getParent());
		} else if (parentContext == null && element.getParent() != null) {
			parentContext = getContext(element.getParent());
		} else if (parentContext == null && element.getParent() == null) {
			parentContext = getContext((MUIElement) ((EObject) element)
					.eContainer());
		}
		
		return createGui(element, parent, parentContext);
	}

	private IEclipseContext getContext(MUIElement parent) {
		if (parent instanceof MContext) {
			return ((MContext) parent).getContext();
		}
		return modelService.getContainingContext(parent);
	}
	
	@Override
	public void removeGui(MUIElement element) {
		
		//((GenericRenderer) element.getRenderer()).removeWidget(element, null);
		
		//((GenericRenderer) element.getRenderer()).disposeWidget(element);
		
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		// We call 'hideChild' *before* checking if the actual element
		// has been rendered in order to pick up cases of 'lazy loading'
		MUIElement parent = element.getParent();
		GenericRenderer parentRenderer = (GenericRenderer) (parent != null ? parent.getRenderer() : null);
				
		if (parentRenderer != null && parent.getWidget() != null) {
			parentRenderer.removeChildGui(element, (MElementContainer<MUIElement>) parent);
		}
		
		GenericRenderer renderer = (GenericRenderer) element.getRenderer();

		// If the element hasn't been rendered then this is a NO-OP
		if (renderer != null) {

			if (element instanceof MElementContainer<?>) {
				MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) element;
				MUIElement selectedElement = container.getSelectedElement();
				List<MUIElement> children = container.getChildren();
				for (MUIElement child : children) {
					// remove stuff in the "back" first
					if (child != selectedElement) {
						removeGui(child);
					}
				}

				if (selectedElement != null
						&& children.contains(selectedElement)) {
					// now remove the selected element
					removeGui(selectedElement);
				}
			}
			else if (element instanceof MPlaceholder)
			{
				MPlaceholder ph = (MPlaceholder) element;
				MUIElement refElement = ph.getRef();
				
				int pcount = 0;
				MWindow window = modelService.getTopLevelWindowFor(element);
				for (MPlaceholder p : modelService.findElements(window, null, MPlaceholder.class, null)) {
					if (p.getRef() == refElement)
						pcount++;
				}
				
				assert pcount > 0;
				
				if (pcount == 1) {
					removeGui(refElement);
				}
			}

			if (element instanceof MPerspective) {
				MPerspective perspective = (MPerspective) element;
				for (MWindow subWindow : perspective.getWindows()) {
					removeGui(subWindow);
				}
			} else if (element instanceof MWindow) {
				MWindow window = (MWindow) element;
				for (MWindow subWindow : window.getWindows()) {
					removeGui(subWindow);
				}

				if (window instanceof MTrimmedWindow) {
					MTrimmedWindow trimmedWindow = (MTrimmedWindow) window;
					for (MUIElement trimBar : trimmedWindow.getTrimBars()) {
						removeGui(trimBar);
					}
				}
			}

			if (element instanceof MContribution) {
				MContribution contribution = (MContribution) element;
				Object client = contribution.getObject();
				IEclipseContext parentContext = renderer.getContext(element);
				if (parentContext != null && client != null) {
					try {
						ContextInjectionFactory.invoke(client,
								PersistState.class, parentContext, null);
					} catch (Exception e) {
						if (logger != null) {
							logger.error(e);
						}
					}
				}
			}
			
			if (element instanceof MPart) {
				MPart part = (MPart) element;
				MToolBar toolBar = part.getToolbar();
				if (toolBar != null) {
					
					if (toolBar.getWidget() != null) {
						((GenericRenderer)(toolBar.getRenderer())).unbindWidget(toolBar);
					}
				}

				for (MMenu menu : part.getMenus()) {
					removeGui(menu);
				}
			}
			
			renderer.unbindWidget(element);
			renderer.disposeWidget(element);
			element.setRenderer(null);
			element.setWidget(null);
			
			// unset the client object
			if (element instanceof MContribution) {
				MContribution contribution = (MContribution) element;
				Object client = contribution.getObject();
				IEclipseContext parentContext = renderer.getContext(element);
				if (parentContext != null && client != null) {
					try {
						ContextInjectionFactory.uninject(client, parentContext);
					} catch (Exception e) {
						if (logger != null) {
							logger.error(e);
						}
					}
				}
				contribution.setObject(null);
			}

			// dispose the context
			if (element instanceof MContext) {
				clearContext((MContext) element);
			}
		}
	}
	
	private void clearContext(MContext contextME) {
		MContext ctxt = (MContext) contextME;
		IEclipseContext lclContext = ctxt.getContext();
		if (lclContext != null) {
			IEclipseContext parentContext = lclContext.getParent();
			IEclipseContext child = parentContext.getActiveChild();
			if (child == lclContext) {
				child.deactivate();
			}

			ctxt.setContext(null);
			lclContext.dispose();
		}
	}

	@Override
	public Object run(MApplicationElement uiRoot, IEclipseContext appContext) {
		appContext.set(GenericPresentationEngine.class, this);
		if (uiRoot instanceof MApplication) {
			theApp = (MApplication) uiRoot;
			for (MWindow window : theApp.getChildren()) {
				createGui(window);
			}
		}
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		if (theApp != null) {
			for (MWindow window : theApp.getChildren()) {
				if (window.getWidget() != null) {
					removeGui(window);
				}
			}
		}
	}
	
	public void postConstruct(IEclipseContext context) {
		// Add the presentation engine to the context
		context.set(IPresentationEngine.class.getName(), this);
		
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_TOBERENDERED, toBeRenderedHandler);
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_VISIBLE, visibilityHandler);
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_CHILDREN, childrenHandler);
	}
	
	@PreDestroy
	public void destroy(IEclipseContext context) {
		context.remove(IPresentationEngine.class.getName());
		
		eventBroker.unsubscribe(toBeRenderedHandler);
		eventBroker.unsubscribe(visibilityHandler);
		eventBroker.unsubscribe(childrenHandler);
	}
}
