package org.semanticsoft.vaaclipsedemo.mediaplayer.toolcontrols;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.semanticsoft.e4extension.service.EPartServiceExt;
import org.semanticsoft.vaaclipsedemo.mediaplayer.constants.MediaConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaCategory;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaLibrary;

import com.vaadin.Application;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window.Notification;

public class AddToLibraryToolControl
{
	private Button playMediaButton;
	private TextField textField;
	
	@Inject
	IEventBroker broker;
	
	@Inject
	MediaLibrary mediaLibrary;
	
	@Inject
	EPartServiceExt partServiceExt;
	
	@Inject
	Application vaadinApp;

	@PostConstruct
	public void postConstruct(ComponentContainer cc)
	{
		playMediaButton = new Button("Play media: ");
		playMediaButton.setIcon(new ThemeResource("org.semanticsoft.vaaclipsedemo.mediaplayer/icons/watch.png"));
		playMediaButton.setDescription("Play media");
		textField = new TextField();
		textField.setWidth("20em");
		textField.setValue("http://www.youtube.com/v/0417pQz7iIk");

		playMediaButton.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event)
			{
				if (textField.getValue() == null)
					return;
				
				String uri = textField.getValue().toString().trim();
				if ((uri.isEmpty()))
					return;

				setMedia(uri);
			}
		});
		
		Button addToLibraryButton = new Button("Add...");
		addToLibraryButton.setIcon(new ThemeResource("org.semanticsoft.vaaclipsedemo.mediaplayer/icons/add.png"));
		addToLibraryButton.setDescription("Add media to library");
		addToLibraryButton.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event)
			{
				if (textField.getValue() == null)
					return;
				
				String uri = textField.getValue().toString().trim();
				if ((uri.isEmpty()))
					return;
				
				MediaCategory category = null;
				if (mediaLibrary.getSelectedMediaEntry() instanceof MediaCategory)
					category = (MediaCategory) mediaLibrary.getSelectedMediaEntry();
				else if (mediaLibrary.getSelectedMediaEntry() instanceof Media)
				{
					Media selectedMedia = (Media) mediaLibrary.getSelectedMediaEntry();
					category = selectedMedia.getParent();
				}
				
				if (category != null)
				{
					Media newMedia = setMedia(uri);
					category.addMedia(newMedia);
					
					broker.send(MediaConstants.mediaEntryAdded, newMedia);
					
					MInputPart part = partServiceExt.openUri(newMedia.getUri());
					part.setLabel(newMedia.getName());
				}
				else
				{
					vaadinApp.getMainWindow().showNotification("Select category in media library", Notification.TYPE_WARNING_MESSAGE);
				}
			}
		});
		
		cc.addComponent(playMediaButton);
		cc.addComponent(textField);
		cc.addComponent(addToLibraryButton);
	}
	
	private Media setMedia(String uri)
	{
		Media media = new Media();
		media.setName("No name");
		media.setUri(uri);
		media.setDescription("");
		broker.send(MediaConstants.mediaSelected, media);
		return media;
	}
}
