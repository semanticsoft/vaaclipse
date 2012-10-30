package org.semanticsoft.vaaclipse.behaviour.cleanup;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class CleanupAddon {
	@Inject
	IEventBroker eventBroker;

	@Inject
	EModelService modelService;

	@Inject
	MApplication app;

	private EventHandler childrenHandler = new EventHandler() {
		
		private boolean ignoreChildrenChanges = false;
		
		public void handleEvent(Event event) {
			
			if (ignoreChildrenChanges)
				return;
			
			Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);
			String eventType = (String) event.getProperty(UIEvents.EventTags.TYPE);
			if (UIEvents.EventTypes.REMOVE.equals(eventType)) {
				final MElementContainer<?> container = (MElementContainer<?>) changedObj;
				MUIElement containerParent = container.getParent();

				// Determine the elements that should *not* ever be auto-destroyed
				if (container instanceof MApplication || container instanceof MPerspectiveStack
						|| container instanceof MMenuElement || container instanceof MTrimBar
						|| container instanceof MToolBar || container instanceof MArea
						|| container.getTags().contains(IPresentationEngine.NO_AUTO_COLLAPSE)) {
					return;
				}

				if (container instanceof MWindow && containerParent instanceof MApplication) {
					return;
				}

				// Stall the removal to handle cases where the container is only transiently empty

				// Remove it from the display if no visible children
				int tbrCount = modelService.toBeRenderedCount(container);

				// Cache the value since setting the TBR may change the result
				boolean lastStack = isLastEditorStack(container);
				if (tbrCount == 0 && !lastStack) {
					container.setToBeRendered(false);
				}

				// Remove it from the model if it has no children at all
				MElementContainer<?> lclContainer = container;
				if (lclContainer.getChildren().size() == 0) {
					MElementContainer<MUIElement> parent = container.getParent();
					if (parent != null && !lastStack) {
						container.setToBeRendered(false);
						parent.getChildren().remove(container);
					} else if (container instanceof MWindow) {
						// Must be a Detached Window
						MUIElement eParent = (MUIElement) ((EObject) container)
								.eContainer();
						if (eParent instanceof MPerspective) {
							((MPerspective) eParent).getWindows().remove(container);
						} else if (eParent instanceof MWindow) {
							((MWindow) eParent).getWindows().remove(container);
						}
					}
				} else if (container.getChildren().size() == 1
						&& container instanceof MPartSashContainer) {
					// if a sash container has only one element then remove it and move
					// its child up to where it used to be
					MUIElement theChild = container.getChildren().get(0);
					MElementContainer<MUIElement> parentContainer = container
							.getParent();
					if (parentContainer != null) {
						ignoreChildrenChanges = true;
						int index = parentContainer.getChildren().indexOf(container);
						//theChild.setContainerData(container.getContainerData());
						container.getChildren().remove(theChild);
						parentContainer.getChildren().remove(container);
						parentContainer.getChildren().add(index, theChild);
						container.setToBeRendered(false);
						ignoreChildrenChanges = false;
					}
				}
			}
		}
	};

	private EventHandler renderingChangeHandler = new EventHandler() {
		public void handleEvent(Event event) {
			MUIElement changedObj = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
			MElementContainer<MUIElement> container = null;
			if (changedObj.getCurSharedRef() != null)
				container = changedObj.getCurSharedRef().getParent();
			else
				container = changedObj.getParent();

			// this can happen for shared parts that aren't attached to any placeholders
			if (container == null) {
				return;
			}

			// never hide top-level windows
			MUIElement containerElement = container;
			if (containerElement instanceof MWindow && containerElement.getParent() != null) {
				return;
			}

			// These elements should neither be shown nor hidden based on their containment state
			if (isLastEditorStack(containerElement) || containerElement instanceof MPerspective
					|| containerElement instanceof MPerspectiveStack)
				return;

			Boolean toBeRendered = (Boolean) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			if (toBeRendered) {
				// Bring the container back if one of its children goes visible
				if (!container.isToBeRendered())
					container.setToBeRendered(true);
			} else {
				// Never hide the container marked as no_close
				if (container.getTags().contains(IPresentationEngine.NO_AUTO_COLLAPSE)) {
					return;
				}

				int visCount = modelService.countRenderableChildren(container);

				// Remove stacks with no visible children from the display (but not the
				// model)
				final MElementContainer<MUIElement> theContainer = container;
				if (visCount == 0) {
					if (!isLastEditorStack(theContainer))
						theContainer.setToBeRendered(false);
				} else {
					// if there are rendered elements but none are 'visible' we should
					// make the container invisible as well
					boolean makeInvisible = true;

					// OK, we have rendered children, are they 'visible' ?
					for (MUIElement kid : container.getChildren()) {
						if (!kid.isToBeRendered())
							continue;
						if (kid.isVisible()) {
							makeInvisible = false;
							break;
						}
					}

					if (makeInvisible) {
						container.setVisible(false);
					}
				}
			}
		}
	};

	@PostConstruct
	void init(IEclipseContext context) {
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_CHILDREN, childrenHandler);
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_TOBERENDERED, renderingChangeHandler);
	}

	@PreDestroy
	void removeListeners() {
		eventBroker.unsubscribe(childrenHandler);
		eventBroker.unsubscribe(renderingChangeHandler);
	}

	boolean isLastEditorStack(MUIElement element) {
		return modelService.isLastEditorStack(element);
	}
}
