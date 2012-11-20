/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.views;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaLibrary;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaLibraryService;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Playlist;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.And;
import com.vaadin.event.dd.acceptcriteria.ClientSideCriterion;
import com.vaadin.event.dd.acceptcriteria.SourceIs;
import com.vaadin.ui.AbstractSelect.AbstractSelectTargetDetails;
import com.vaadin.ui.AbstractSelect.AcceptItem;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

/**
 * @author rushan
 *
 */
public class PlaylistView
{
	private Panel panel;
	
	private MPart playerPart;
	private MPart mediaInfoPart;
	
	@Inject
	MediaLibrary mediaLibrary;
	
	@Inject
	Playlist playlist;
	
	@Inject
	EModelService modelService;
	
	@Inject
	MApplication app;
	private Table table;
	private BeanItemContainer<Media> tableContainer;
	
	MPart mediaLibPart;
	
	@Inject
	public void PlaylistView(VerticalLayout parent, IEclipseContext context)
	{
		panel = new Panel();
		panel.setSizeFull();
		parent.addComponent(panel);
	}
	
	public void addMedia(Media media)
	{
		if (this.playlist.getMediaList().isEmpty())
			tableContainer.removeAllItems(); //drop the dummy media with help text
		
		table.setCaption("");
		tableContainer.addItem(media);
	}
	
	public void addMediaAfter(Media prevMedia, Media media)
	{
		if (this.playlist.getMediaList().isEmpty())
			tableContainer.removeAllItems(); //drop the dummy media with help text
		
		table.setCaption(null);
		tableContainer.addItemAfter(prevMedia, media);
	}
	
	@PostConstruct
	public void postCostruct()
	{
		mediaLibPart = (MPart) modelService.find("org.semanticsoft.vaaclipsedemo.mediaplayer.part.medialib", app);
		
		initTable();
		for (Media media : this.playlist.getMediaList())
		{
			addMedia(media);
		}
		
		if (this.playlist.getMediaList().isEmpty())
		{
			Media dummyMedia = new Media();
			dummyMedia.setName("Drag the media from library");
			addMedia(dummyMedia);
		}
	}
	
	private void initTable() {
		
		 table = new Table();
		 table.setSizeFull();
		 
		 this.panel.addComponent(table);
		
		MediaLibraryView mediaLibView =  (MediaLibraryView) mediaLibPart.getObject();
		//TODO: mediaLibView can be null at initialization time... change this code...
		final ClientSideCriterion acceptCriterion = new SourceIs(mediaLibView.getTree());
		 
        tableContainer = new BeanItemContainer<Media>(Media.class);
        table.setContainerDataSource(tableContainer);
        table.setVisibleColumns(new Object[] { "name" });
        
        // Handle drop in table: move hardware item or subtree to the table
        table.setDragMode(TableDragMode.ROW);
        table.setDropHandler(new DropHandler() {
            public void drop(DragAndDropEvent dropEvent) {
                
            	if (!(dropEvent.getTransferable() instanceof DataBoundTransferable))
            		return;
            	
                DataBoundTransferable t = (DataBoundTransferable) dropEvent.getTransferable();
                if (!(t.getSourceContainer() instanceof Container.Hierarchical)) {
                    return;
                }
                
                AbstractSelectTargetDetails dropData = ((AbstractSelectTargetDetails) dropEvent.getTargetDetails());
                
                Object sourceItemId = t.getItemId();
                
                if (sourceItemId == null)
                	return;
                
                Media media = MediaLibraryService.findMediaById(mediaLibrary, sourceItemId.toString(), "/");
                
                Media targetMedia = (Media) dropData.getItemIdOver();
                Media prevMedia = targetMedia != null ? tableContainer.prevItemId(targetMedia) : null;
                
                if (targetMedia != null)
                {
                    switch (dropData.getDropLocation()) {
                    case BOTTOM:
                    	addMediaAfter(targetMedia, media);
                        break;
                    case MIDDLE:
                    case TOP:
                    	addMediaAfter(prevMedia, media);
                        break;
                    }	
                }
                else
                {
                	tableContainer.addItem(media);
                }
            }

            public AcceptCriterion getAcceptCriterion() {
                return new And(acceptCriterion, AcceptItem.ALL);
            }
        });
    }
}
