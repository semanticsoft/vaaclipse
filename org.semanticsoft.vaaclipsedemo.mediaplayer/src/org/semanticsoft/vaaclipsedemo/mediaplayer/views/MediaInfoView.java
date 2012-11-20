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
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.IMediaConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

/**
 * @author rushan
 *
 */
public class MediaInfoView
{
	private Media media;
	
	private HorizontalLayout layout = new HorizontalLayout();
	private Label name = new Label();
	private Label uri = new Label();
	private Label description = new Label("", Label.CONTENT_XHTML);
	
	private GridLayout grid;

	private EventHandler meidaSelectedHandler = new EventHandler() {
		
		@Override
		public void handleEvent(Event event) {
			Object data = event.getProperty(EventUtils.DATA);
			if (data instanceof Media){
				setMedia((Media) data);
			}
			
		}
	};
	
	@Inject
	public void PlayerView(VerticalLayout parent, IEclipseContext context)
	{
		layout.setSizeFull();
		parent.addComponent(layout);
		description.setSizeFull();
	}
	
	@PostConstruct
	public void pc(IEventBroker broker){
		broker.subscribe(IMediaConstants.mediaSelected, meidaSelectedHandler);
	}
	
	public Media getMedia()
	{
		return media;
	}
	
	public void setMedia(Media media)
	{
		this.media = media;
		
		if (grid == null)
		{
			grid = new GridLayout(2, 3);
			
			grid.addComponent(new Label("Name: "), 0, 0);
			grid.addComponent(name, 1, 0);
			grid.addComponent(new Label("URI: "), 0, 1);
			grid.addComponent(uri, 1, 1);
			grid.addComponent(new Label("Description: "), 0, 2);
			grid.addComponent(description, 1, 2);
			layout.addComponent(grid);
			
			grid.setColumnExpandRatio(0, 20);
			grid.setColumnExpandRatio(1, 80);
			grid.setRowExpandRatio(0, 10);
			grid.setRowExpandRatio(1, 10);
			grid.setRowExpandRatio(2, 100);
			
			grid.setSizeFull();
		}
		
		name.setPropertyDataSource(new ObjectProperty<String>(media.getName(), String.class));
		uri.setPropertyDataSource(new ObjectProperty<String>(media.getUri(), String.class));
		description.setPropertyDataSource(new ObjectProperty<String>(media.getDescription(), String.class));
	}
	
	@PreDestroy
	public void pd(IEventBroker broker){
		broker.unsubscribe(meidaSelectedHandler);
	}
}
