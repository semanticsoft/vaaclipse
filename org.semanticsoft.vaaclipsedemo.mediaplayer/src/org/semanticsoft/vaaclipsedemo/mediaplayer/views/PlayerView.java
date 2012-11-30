package org.semanticsoft.vaaclipsedemo.mediaplayer.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.extensions.EventUtils;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.osgi.service.event.Event;
import org.semanticsoft.vaaclipsedemo.mediaplayer.constants.IMediaConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.osgi.service.event.EventHandler;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * @author rushan
 *
 */
public class PlayerView
{

	@Inject
	MPart part;
	
	private Media media;
	
	private HorizontalLayout layout = new HorizontalLayout();
//	private Label label = new Label("empty");
	private Embedded e;
	
	private EventHandler mediaSelectedHandler = new EventHandler() {
		
		@Override
		public void handleEvent(Event event) {
			Object data = event.getProperty(EventUtils.DATA);
			if (data instanceof Media){
				Boolean autoplay = (Boolean) event.getProperty(IMediaConstants.autoPlay);
				setMedia((Media) data, autoplay);
				part.setLabel(((Media) data).getName());
			}
			
		}
	};

	@Inject
	public PlayerView(VerticalLayout parent, IEclipseContext context, IEventBroker broker)
	{
		layout.setSizeFull();
		parent.addComponent(layout);
	}
	
	@PostConstruct
	public void pc(IEventBroker b){
		b.subscribe(IMediaConstants.mediaSelected, mediaSelectedHandler);
	}
	
	public Media getMedia()
	{
		return media;
	}
	
	public void setMedia(Media media, boolean autoplay)
	{
		this.media = media;
		
		if (e == null)
		{
			e = new Embedded();
	        e.setMimeType("application/x-shockwave-flash");
	        e.setParameter("allowFullScreen", "true");
			layout.addComponent(e);
		}
		
		e.setSizeFull();
		e.setSource(new ExternalResource(media.getUri() + (autoplay ? "&autoplay=1" : "") ));
	}
	
	@PreDestroy
	public void pd(IEventBroker broker){
		broker.unsubscribe(mediaSelectedHandler);
	}
}
