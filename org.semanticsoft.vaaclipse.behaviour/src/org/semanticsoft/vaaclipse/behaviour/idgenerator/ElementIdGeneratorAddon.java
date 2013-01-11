package org.semanticsoft.vaaclipse.behaviour.idgenerator;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class ElementIdGeneratorAddon {
	@Inject
	IEventBroker eventBroker;
	
	@Inject
	MApplication app;

	private EventHandler childrenHandler = new EventHandler() {
		
		public void handleEvent(Event event) {
		
			Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);
			if (!(changedObj instanceof MElementContainer<?>))
				return;
			
			String eventType = (String) event.getProperty(UIEvents.EventTags.TYPE);
			if (UIEvents.EventTypes.ADD.equals(eventType)) {
				
				Object newValue = event.getProperty(UIEvents.EventTags.NEW_VALUE);
				if (!(newValue instanceof MUIElement))
					return;
				
				MUIElement element = (MUIElement) newValue;
				
				if (element.getElementId() == null || element.getElementId().trim().isEmpty())
				{
					element.setElementId(UUID.randomUUID().toString());
				}
			}
		}
	};

	@PostConstruct
	void init(IEclipseContext context) {
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_CHILDREN, childrenHandler);
	}

	@PreDestroy
	void removeListeners() {
		eventBroker.unsubscribe(childrenHandler);
	}
}
