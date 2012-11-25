package org.semanticsoft.vaaclipse.demo;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.extensions.EventUtils;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.demo.model.Contact;
import org.semanticsoft.vaaclipse.demo.model.ContactsFactory;
import org.semanticsoft.vaaclipse.demo.utils.IContactsEvents;

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

	private EventHandler contactHandler = new EventHandler() {

		@Override
		public void handleEvent(Event event) {
			Object contact = event.getProperty(EventUtils.DATA);
			if (contact instanceof Contact) {
				tree.select(contact);
			}

		}
	};
	private Tree tree;

	@PostConstruct
	public void pc(IEventBroker eventBroker, VerticalLayout ly) {
		this.layout = ly;
		buildUI();
		eventBroker.subscribe(IContactsEvents.CONTACT_SELECTED, contactHandler);
	}

	private void buildUI() {
		tree = new Tree(part.getLabel());
		List<Contact> list = cf.getContacts();
		for (Contact contact : list) {
			tree.addItem(contact);
		}
		tree.setChildrenAllowed(null, false);
		tree.addListener(new ItemClickListener() {
			private static final long serialVersionUID = -8401944431859296346L;

			@Override
			public void itemClick(ItemClickEvent event) {
				broker.send(IContactsEvents.CONTACT_SELECTED,
						(Contact) event.getItemId());
			}
		});

		layout.addComponent(tree);
	}
}