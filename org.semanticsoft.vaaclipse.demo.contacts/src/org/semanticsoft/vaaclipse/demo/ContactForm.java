package org.semanticsoft.vaaclipse.demo;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.core.di.extensions.EventUtils;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.demo.model.IContactsConstants;
import org.semanticsoft.vaaclipse.demo.model.Person;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Form;
import com.vaadin.ui.VerticalLayout;

public class ContactForm {
	
	private EventHandler eventHandler = new EventHandler() {
		
		@Override
		public void handleEvent(Event event) {
			Person property = (Person) event.getProperty(EventUtils.DATA);
			if (property==null) return;
			setPerson(property);
			
		}
	};
	private Form form;

	@PostConstruct
	public void pc(IEventBroker broker, VerticalLayout vl){
		broker.subscribe(IContactsConstants.personSelected, eventHandler);
		form = new Form();
		form.setImmediate(true);
		form.setItemDataSource(new BeanItem<Person>(new Person()));
		vl.addComponent(form);
	}
	
	private void setPerson(Person person){
		form.setItemDataSource(new BeanItem<Person>(person));
	}
	
	@PreDestroy
	public void pd(IEventBroker broker){
		broker.unsubscribe(eventHandler);
	}

}
