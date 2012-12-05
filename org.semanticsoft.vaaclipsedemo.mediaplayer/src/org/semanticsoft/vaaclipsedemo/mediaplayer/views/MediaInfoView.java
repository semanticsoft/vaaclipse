/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.extensions.EventUtils;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipsedemo.mediaplayer.constants.MediaConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * @author rushan
 *
 */
public class MediaInfoView extends MediaInfoBase
{
	private Label name = new Label();
	private Label uri = new Label();
	private Label description = new Label("", Label.CONTENT_XHTML);

	private EventHandler meidaSelectedHandler = new EventHandler() {
		
		@Override
		public void handleEvent(Event event) {
			Object data = event.getProperty(EventUtils.DATA);
			if (data instanceof Media){
				setMedia((Media) data);
			}
			
		}
	};
	
	private EventHandler mediaChangedHandler = new EventHandler() {
			
			@Override
			public void handleEvent(Event event) {
				Object data = event.getProperty(EventUtils.DATA);
				if (data instanceof Media){
					insertMedia((Media) data);
				}
				
			}
		};
	
	@Inject
	public MediaInfoView(VerticalLayout parent, IEclipseContext context)
	{
		super(parent, context);
	}
	
	@PostConstruct
	public void pc(IEventBroker broker){
		broker.subscribe(MediaConstants.mediaSelected, meidaSelectedHandler);
		broker.subscribe(MediaConstants.mediaEntryChanged, mediaChangedHandler);
	}
	
	protected void insertMedia(Media media2)
	{
		name.setPropertyDataSource(new ObjectProperty<String>(media.getName(), String.class));
		uri.setPropertyDataSource(new ObjectProperty<String>(media.getUri(), String.class));
		description.setPropertyDataSource(new ObjectProperty<String>(media.getDescription(), String.class));
	}

	protected Component getNameComponent()
	{
		return this.name;
	}
	
	protected Component getDescriptionComponent()
	{
		return this.description;
	}
	
	protected Component getUriComponent()
	{
		return this.uri;
	}
	
	@PreDestroy
	public void pd(IEventBroker broker){
		broker.unsubscribe(meidaSelectedHandler);
		broker.unsubscribe(mediaChangedHandler);
	}
}
