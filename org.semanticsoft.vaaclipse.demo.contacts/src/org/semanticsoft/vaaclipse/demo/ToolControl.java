package org.semanticsoft.vaaclipse.demo;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.semanticsoft.vaaclipse.demo.model.Address;
import org.semanticsoft.vaaclipse.demo.model.IContactsConstants;
import org.semanticsoft.vaaclipse.demo.model.Person;

import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;

public class ToolControl {
	
	private final class MyListener implements ClickListener {
		private final IEventBroker broker;

		private MyListener(IEventBroker broker) {
			this.broker = broker;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			System.err.println("Clicked");
			broker.send(IContactsConstants.personSelected, new Person("Frederick","Barbarossa",31,new Address()));
			
		}
	}

	@PostConstruct
	public void pc(ComponentContainer vl, IEventBroker broker){
		Button b = new Button("I am a toolbar control");
		vl.addComponent(b);
		b.addListener(new MyListener(broker));
		TextField c = new TextField();
		c.setInputPrompt("Enter command");
		vl.addComponent(c);
	}

}
