package org.semanticsoft.vaaclipse.demo.model;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.extensions.EventUtils;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.demo.utils.IContactsEvents;
@Creatable
public class ContactsFactory {

	private ArrayList<Contact> contacts;
	private EventHandler eventHandler = new EventHandler() {
		
		@Override
		public void handleEvent(Event event) {
			Contact property = (Contact) event.getProperty(EventUtils.DATA);
			contacts.add(property);
		}
	};

	@Inject
	public ContactsFactory() {
		this.setContacts(new ArrayList<Contact>());
	}
	
	@PostConstruct
	private void pc(IEventBroker broker, MWindow win){
		Contact contact = new Contact();
		contact.setFirstName("Rushan");
		contact.setLastName("Gilmulin");
		contact.setAge(30);
		contact.setCompany("Semanticsoft");
		getContacts().add(contact);
		Contact sopot = new Contact();
		sopot.setAge(24);
		sopot.setFirstName("Sopot");
		sopot.setLastName("Cela");
		sopot.setCompany("Eclipse");
		getContacts().add(sopot);
		broker.subscribe(IContactsEvents.NEW_CONTACT, eventHandler );
	}
	
	@PreDestroy
	private void pd(IEventBroker broker){
		getContacts().clear();
		setContacts(null);
		broker.unsubscribe(eventHandler);
	}

	public ArrayList<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(ArrayList<Contact> contacts) {
		this.contacts = contacts;
	}
}
