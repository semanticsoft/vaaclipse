package org.semanticsoft.vaaclipse.demo.model;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
@Singleton
@Creatable
public class ContactsFactory {

	private ArrayList<Contact> contacts;

	@Inject
	public ContactsFactory() {
		this.setContacts(new ArrayList<Contact>());
	}
	
	@PostConstruct
	private void pc(){
		Contact contact = new Contact();
		contact.setFirstName("Rushan");
		contact.setLastName("Gilmulin");
		contact.setAge(35);
		contact.setCompany("Semanticsoft");
		getContacts().add(contact);
		getContacts().add(new Contact());
	}
	
	@PreDestroy
	private void pd(){
		getContacts().clear();
		setContacts(null);
	}

	public ArrayList<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(ArrayList<Contact> contacts) {
		this.contacts = contacts;
	}
}
