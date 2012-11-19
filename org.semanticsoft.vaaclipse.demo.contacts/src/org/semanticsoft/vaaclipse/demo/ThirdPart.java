package org.semanticsoft.vaaclipse.demo;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class ThirdPart {
	private Table c;
	private IEventBroker broker;
	private EventHandler eventHandler;

	@PostConstruct
	public void postConstruct(VerticalLayout vl, IEventBroker broker) {
		c = new Table("I am a new text Arela");
		c.setImmediate(true);
		vl.addComponent(c);
		this.broker = broker;
		subscribictions()
		;
	}

	 @Inject
	 @Optional
	 public void respond(@EventTopic("MyEvent") String s){
	 System.err.println("HEREEEEEE");
	 if (s!=null)
	 c.setCaption("I am an evented object");
	 }

	public void subscribictions() {
		eventHandler = new EventHandler() {

			@Override
			public void handleEvent(Event event) {
				System.err.println(event);
				System.err.println("Testing");

			}
		};
		broker.subscribe("MyEvent", eventHandler);
	}

	@Focus
	public void onFocus() {
		// TODO Your code here
	}

	@PreDestroy
	public void pd() {
		broker.unsubscribe(eventHandler);
	}
}