package org.semanticsoft.vaaclipse.demo.lifecycle;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.core.di.extensions.EventUtils;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.demo.model.Contact;
import org.semanticsoft.vaaclipse.demo.utils.IContactsEvents;

public class ContactsAddon {
	
	Map<Contact, MPart> contactPartMap = new HashMap<Contact, MPart>();

	protected MPartStack partStack;
	
	private EventHandler eventHandler = new EventHandler() {
		
		@Override
		public void handleEvent(Event event) {
			System.err.println("P");
			Object property = event.getProperty(EventUtils.DATA);
			if (property instanceof Contact){
				Contact contact = (Contact) property;
				if (contactPartMap.get(contact)==null){
					if (partStack == null) partStack = findPartStack();
					MPart part = partService.createPart("contact.part.descriptor");
					partStack.getChildren().add(part);
					partService.activate(part);
					contactPartMap.put(contact, part);
				}
				else
					partService.activate(contactPartMap.get(contact));
			}
		}
	};

	private EModelService modelService;

	private MApplication app;

	private EPartService partService;

	@PostConstruct
	private void pc(MApplication app, IEventBroker broker, EModelService modelService, EPartService partService){
		broker.subscribe(IContactsEvents.CONTACT_SELECTED, eventHandler);
		this.modelService = modelService;
		this.app = app;
		this.partService = partService;
	}

	
	protected MPartStack findPartStack() {

		MPartStack element = (MPartStack) modelService.find("contacts.stack", app);
		if (element==null) System.err.println("Error finding stack");
		return element;
	}
	
	@PreDestroy
	private void pd(IEventBroker broker){
		broker.unsubscribe(eventHandler);
	}

}
