package org.semanticsoft.vaaclipse.demo;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.extensions.EventUtils;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.demo.model.Contact;
import org.semanticsoft.vaaclipse.demo.utils.IContactsEvents;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Form;
import com.vaadin.ui.VerticalLayout;

public class ContactPart {
	
	
	private IEventBroker broker;
	protected Contact formContact;
	
	@Inject
	private MPart part;
	
	private EventHandler contactHandler = new EventHandler() {
		
		@Override
		public void handleEvent(Event event) {
			Object contact = event.getProperty(EventUtils.DATA);
			if (contact instanceof Contact){
				form.setItemDataSource(new BeanItem<Contact>((Contact) contact));
				formContact = (Contact) contact;
				part.setLabel(formContact.getFirstName());
			}
			
		}
	};
	private VerticalLayout layout;
	private Form form = new Form();

	@PostConstruct
	private void pc(IEventBroker broker, VerticalLayout vl){
		this.broker = broker;
		broker.subscribe(IContactsEvents.CONTACT_SELECTED, contactHandler);
		this.layout = vl;
		buildUI();
		
	}
	
	private void buildUI() {
		form.setImmediate(true);
		layout.addComponent(form);
	}

	@PreDestroy
	private void pd(){
		System.err.println("PreDestroy");
		broker.unsubscribe(contactHandler);
	}
	
	@Focus
	public void focus(){
		
	}

}
