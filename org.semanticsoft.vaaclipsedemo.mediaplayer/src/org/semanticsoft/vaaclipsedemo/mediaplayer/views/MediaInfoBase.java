/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.views;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;

import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * @author rushan
 *
 */
public abstract class MediaInfoBase
{

	protected Media media;
	private HorizontalLayout layout = new HorizontalLayout();
	private GridLayout grid;

	public MediaInfoBase(VerticalLayout parent, IEclipseContext context)
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
			Component nc = getNameComponent();
			nc.setWidth("100%");
			grid.addComponent(nc, 1, 0);
			grid.addComponent(new Label("URI: "), 0, 1);
			Component uc = getUriComponent();
			uc.setWidth("100%");
			grid.addComponent(uc, 1, 1);
			grid.addComponent(new Label("Description: "), 0, 2);
			Component dc = getDescriptionComponent();
			dc.setSizeFull();
			grid.addComponent(dc, 1, 2);
			layout.addComponent(grid);
			
			grid.setColumnExpandRatio(0, 20);
			grid.setColumnExpandRatio(1, 80);
			grid.setRowExpandRatio(0, 10);
			grid.setRowExpandRatio(1, 10);
			grid.setRowExpandRatio(2, 100);
			
			grid.setSizeFull();
		}
		
		insertMedia(media);
	}
	
	protected abstract void insertMedia(Media media);

	protected abstract Component getNameComponent();
	
	protected abstract Component getDescriptionComponent();
	
	protected abstract Component getUriComponent();
}
