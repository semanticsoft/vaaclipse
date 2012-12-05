/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.editors;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.semanticsoft.vaaclipse.publicapi.editor.SavePromptSetup;
import org.semanticsoft.vaaclipsedemo.mediaplayer.constants.MediaConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.service.MediaService;
import org.semanticsoft.vaaclipsedemo.mediaplayer.views.MediaInfoBase;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * @author rushan
 *
 */
public class MediaInfoEditor extends MediaInfoBase
{
	@Inject
	IEventBroker eventBroker;
	
	@Inject
	public MediaInfoEditor(VerticalLayout parent, IEclipseContext context, MediaService service, MInputPart inputPart, SavePromptSetup promptProvider)
	{
		super(parent, context);
		Media media = service.findMedia(inputPart.getInputURI());
		if (media != null)
		{
			setMedia(media);
		}
		
		promptProvider.setCaption("Save media");
		promptProvider.setMessage(String.format("Media %s has been modified. Save changes?", media.getName()));
		
		this.name.setImmediate(true);
		this.description.setImmediate(true);
		this.uri.setImmediate(true);
		
		this.name.addListener(textChangeListener);
		this.description.addListener(textChangeListener);
		this.uri.addListener(textChangeListener);
	}
	
	@Inject
	private MDirtyable dirtable;

	private TextField name = new TextField();
	private TextField description = new TextField();
	private TextField uri = new TextField();
	private TextChangeListener textChangeListener = new TextChangeListener() {
		
		@Override
		public void textChange(TextChangeEvent event)
		{
			System.out.println("value changed");
			if (!dirtable.isDirty())
				dirtable.setDirty(true);
		}
	};
	
	@Override
	protected Component getNameComponent()
	{
		return this.name;
	}
	
	@Override
	protected Component getDescriptionComponent()
	{
		return this.description;
	}
	
	@Override
	protected Component getUriComponent()
	{
		return this.uri;
	}

	protected void insertMedia(Media media)
	{
		name.setPropertyDataSource(new ObjectProperty<String>(media.getName(), String.class));
		uri.setPropertyDataSource(new ObjectProperty<String>(media.getUri(), String.class));
		description.setPropertyDataSource(new ObjectProperty<String>(media.getDescription(), String.class));
	}
	
	@Persist
	public void persist()
	{
		this.media.setName(name.getValue().toString());
		this.media.setUri(uri.getValue().toString());
		this.media.setDescription(description.getValue().toString());
		
		dirtable.setDirty(false);
		
		eventBroker.send(MediaConstants.mediaEntryChanged, this.media);
	}
}
