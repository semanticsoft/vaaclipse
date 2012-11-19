package org.semanticsoft.vaaclipsedemo.mediaplayer.views;

import javax.inject.Inject;


import org.eclipse.e4.core.contexts.IEclipseContext;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;

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
	private Media media;
	
	private HorizontalLayout layout = new HorizontalLayout();
//	private Label label = new Label("empty");
	private Embedded e;
	
	@Inject
	public void PlayerView(VerticalLayout parent, IEclipseContext context)
	{
		layout.setSizeFull();
		parent.addComponent(layout);
	}
	
	public Media getMedia()
	{
		return media;
	}
	
	public void setMedia(Media media)
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
		e.setSource(new ExternalResource(media.getUri()));
	}
}
