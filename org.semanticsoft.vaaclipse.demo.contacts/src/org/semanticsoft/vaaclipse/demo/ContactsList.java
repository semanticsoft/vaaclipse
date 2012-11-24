 
package org.semanticsoft.vaaclipse.demo;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.semanticsoft.vaaclipse.demo.model.Contact;
import org.semanticsoft.vaaclipse.demo.model.ContactsFactory;
import org.semanticsoft.vaaclipse.demo.utils.IContactsEvents;

import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;


public class ContactsList {
	
	@Inject
	public ContactsFactory cf;
	@Inject
	private IEventBroker broker;
	private VerticalLayout layout;
	@Inject
	private MPart part;
	
	@PostConstruct
	public void pc(IEventBroker eventBroker, VerticalLayout ly){
		this.layout = ly;
		buildUI();
	}

	private void buildUI() {
		Tree tree = new Tree(part.getLabel());
		List<Contact> list = cf.getContacts();
		for (Contact contact : list) {
			tree.addItem(contact);
		}
		tree.setChildrenAllowed(null, false);
		tree.addListener(new ItemClickListener() {
			private static final long serialVersionUID = -8401944431859296346L;

			@Override
			public void itemClick(ItemClickEvent event) {
				System.err.println("Sending contact");
				broker.send(IContactsEvents.CONTACT_SELECTED, (Contact) event.getItemId());
			}
		});
		
		layout.addComponent(tree);
	}
}