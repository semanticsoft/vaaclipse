/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.views;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

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
	private Label description = new Label();
	
	private GridLayout grid;
	
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
			
			grid.setColumnExpandRatio(0, 30);
			grid.setColumnExpandRatio(1, 70);
		}
		
		name.setPropertyDataSource(new ObjectProperty<String>(media.getName(), String.class));
		uri.setPropertyDataSource(new ObjectProperty<String>(media.getUri(), String.class));
	}
}
