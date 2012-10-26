/*******************************************************************************
 * Copyright (c) 2011 Kai Toedter and others.
 * 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Kai Toedter - initial API and implementation
 ******************************************************************************/

package org.semanticsoft.vaaclipse.presentation.engine;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.equinox.app.IApplication;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.presentation.renderers.GenericRenderer;

import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;

/**
 * This engine was adopted from Kai Toedter's generic renderer project. I place it in vaaclipse packages temproraly -
 * until the generic renderer will be the part of eclipse project.
 * @author Kai Toedter
 */
@SuppressWarnings("restriction")
public class GenericPresentationEngine implements PresentationEngine {

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
			GenericRenderer parentRenderer = (GenericRenderer) changedElement.getRenderer();
			
			if (parentRenderer == null)
				return;
			
			String eventType = (String) event.getProperty(UIEvents.EventTags.TYPE);
			if (UIEvents.EventTypes.ADD.equals(eventType)) {
				MUIElement added = (MUIElement) event.getProperty(UIEvents.EventTags.NEW_VALUE);
				
				if (added.getWidget() == null)
					createGui(added);
				if (added.getWidget() != null && changedElement.getWidget() != null)
					parentRenderer.addChild(added, changedElement);
			} 
			else if (UIEvents.EventTypes.REMOVE.equals(eventType)) 
			{
				MUIElement removed = (MUIElement) event.getProperty(UIEvents.EventTags.OLD_VALUE);
				if (removed.getWidget() != null && changedElement.getWidget() != null)
					parentRenderer.removeChild(removed, changedElement);
			}
		}
	};

	private final EventHandler visibilityHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			MUIElement changedElement = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
			System.out.println("visibilityHandler: " + changedElement);

			GenericRenderer renderer = (GenericRenderer) changedElement.getRenderer();
			if (renderer == null) {
				return;
			}

			renderer.setVisible(changedElement, changedElement.isVisible());

			MElementContainer<MUIElement> parent = changedElement.getParent();
			if (parent == null) {
				parent = (MElementContainer<MUIElement>) ((EObject) changedElement).eContainer();
			}

			GenericRenderer parentRenderer = (GenericRenderer) parent.getRenderer();
			if (parentRenderer != null) {
				parentRenderer.refreshPlatformElement(parent);
			}
		}
	};

	@Override
	public Object createGui(MUIElement element, Object parentWidget, IEclipseContext parentContext) {
		System.out.println("GenericPresentationEngine.createGui(): This method should not be used.");
		return null;
	}

	@Override
	public Object createGui(MUIElement element, MElementContainer<MUIElement> parent, IEclipseContext parentContext) {
		
		Object currentWidget = element.getWidget();
		if (currentWidget != null) {

			Component control = (Component) currentWidget;
			// make sure the control is visible
			if (!(element instanceof MPlaceholder))
				control.setVisible(true);
			
			Component parentWidget = (Component) parent.getWidget();
			if (parentWidget instanceof ComponentContainer) {
				ComponentContainer currentParent = (ComponentContainer) control.getParent();
				if (currentParent != parentWidget) {
					// the parents are different so we should reparent it
					// it was done the below int SWT renderer (see commented code bellow):
					//control.setParent((Composite) parentWidget);
					//but in Vaadin and some other toolkits it is unable do so in all cases
					//so the best solution is use the processProcess method to allow the parent to add childs properly
					//So call the process content of the parent rednerer for the parent element:
					((GenericRenderer)parent.getRenderer()).processContents(parent);
				}
			}
			
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

				// System.out.println("create context: " +
				// eclipseContext.toString() + " parent context: "
				// + parentContext.toString());

				// make sure the context knows about these variables that have
				// been defined in the model
				for (String variable : ctxt.getVariables()) {
					eclipseContext.declareModifiable(variable);
				}

				Map<String, String> props = ctxt.getProperties();
				for (String key : props.keySet()) {
					eclipseContext.set(key, props.get(key));
				}

				E4Workbench.processHierarchy(element);
				//eclipseContext.activate();
			}
		}

		GenericRenderer renderer = rendererFactory.getRenderer(element);
		element.setRenderer(renderer);

		// TODO check if parents are needed
		if (parent == null) {
			System.out.println("GenericPresentationEngine.createGui(): no parent: " + element + " parent: " + parent);
		}
		renderer.createWidget(element, parent);

		// Does not work: why?
		// if (parent != null) {
		// element.setParent(parent);
		// }

		// TODO set visible here?
		//element.setVisible(true);

		if (element instanceof MElementContainer) {

			// first create the GUI for the children
			@SuppressWarnings("unchecked")
			MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) element;
			for (MUIElement child : container.getChildren()) {
				createGui(child);
			}

			// then let the renderer process them
			renderer.processContents(container);
		}
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
		System.out.println("GenericPresentationEngine.removeGui(): " + element);

		((GenericRenderer) element.getRenderer()).removeWidget(element, null);
	}

	@Override
	public Object run(MApplicationElement uiRoot, IEclipseContext appContext) {
		System.out.println("GenericPresentationEngine.run(): " + uiRoot + ":" + appContext);
		if (uiRoot instanceof MApplication) {
			MApplication theApp = (MApplication) uiRoot;
			for (MWindow window : theApp.getChildren()) {
				createGui(window);
			}
		}
		System.out.println("GenericPresentationEngine.run(): Finished");
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		System.out.println("GenericPresentationEngine.stop()");
	}

	@PostConstruct
	public void postConstruct(IEclipseContext context) {
		System.out.println("GenericPresentationEngine.postConstruct()");
		// Add the presentation engine to the context
		context.set(IPresentationEngine.class.getName(), this);
		
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_CHILDREN, childrenHandler);
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_VISIBLE, visibilityHandler);
	}
	
	@PreDestroy
	public void destroy(IEclipseContext context) {
		context.remove(IPresentationEngine.class.getName());
		
		eventBroker.unsubscribe(childrenHandler);
		eventBroker.unsubscribe(visibilityHandler);
	}

	@Override
	public void refreshGui(MElementContainer<?> element) {
		GenericRenderer renderer = rendererFactory.getRenderer(element);
		if (element instanceof MElementContainer<?>) {
			MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) element;
			
			renderer.refreshPlatformElement(container);
		}
	}
}
