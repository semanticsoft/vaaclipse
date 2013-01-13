package org.semanticsoft.vaaclipsedemo.mediaplayer.toolcontrols;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.semanticsoft.e4extension.service.EPartServiceExt;
import org.semanticsoft.vaaclipse.publicapi.resources.BundleResource;
import org.semanticsoft.vaaclipsedemo.mediaplayer.constants.MediaConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.handlers.medialib.AddMedia;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaLibrary;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class AddToLibraryToolControl {
	private Button playMediaButton;
	private TextField textField;

	@Inject
	IEventBroker broker;

	@Inject
	MediaLibrary mediaLibrary;

	@Inject
	EPartServiceExt partServiceExt;

	@Inject
	UI vaadinUi;

	@Inject
	EModelService modelService;

	@Inject
	IEclipseContext context;

	@Inject
	MWindow window;

	@PostConstruct
	public void postConstruct(ComponentContainer cc) {
		playMediaButton = new Button("Play media: ");
		playMediaButton
				.setIcon(BundleResource
						.valueOf("platform:/plugin/org.semanticsoft.vaaclipsedemo.mediaplayer/icons/watch.png"));
		playMediaButton.setDescription("Play media");
		textField = new TextField();
		textField.setWidth("20em");
		textField.setValue("http://www.youtube.com/v/0417pQz7iIk");

		playMediaButton.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (textField.getValue() == null)
					return;

				String uri = textField.getValue().toString().trim();
				if ((uri.isEmpty()))
					return;

				Media media = new Media();
				media.setName("No name");
				media.setUri(uri);
				media.setDescription("");
				broker.send(MediaConstants.mediaEntrySelected, media);
			}
		});

		Button addToLibraryButton = new Button("Add...");
		addToLibraryButton
				.setIcon(BundleResource
						.valueOf("platform:/plugin/org.semanticsoft.vaaclipsedemo.mediaplayer/icons/add.png"));
		addToLibraryButton.setDescription("Add media to library");
		addToLibraryButton.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (textField.getValue() == null)
					return;

				String uri = textField.getValue().toString().trim();
				if ((uri.isEmpty()))
					return;

				// Don't forget create and use local context, not touch the
				// context of current control!
				IEclipseContext localContext = context.createChild();
				Object addMediaHandler = ContextInjectionFactory.make(
						AddMedia.class, localContext);
				localContext.set(String.class, uri); // media uri
				ContextInjectionFactory.invoke(addMediaHandler, Execute.class,
						localContext);
			}
		});

		cc.addComponent(playMediaButton);
		cc.addComponent(textField);
		cc.addComponent(addToLibraryButton);
	}
}
