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
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipsedemo.mediaplayer.constants.MediaConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaLibrary;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Playlist;
import org.semanticsoft.vaaclipsedemo.mediaplayer.service.MediaService;

import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.AbstractSelect.AbstractSelectTargetDetails;
import com.vaadin.ui.AbstractSelect.AcceptItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.VerticalLayout;

/**
 * @author rushan
 * 
 */
public class PlaylistView
{
	private Panel panel;

	@Inject
	MediaLibrary mediaLibrary;

	@Inject
	Playlist playlist;

	@Inject
	EModelService modelService;

	@Inject
	MApplication app;
	
	@Inject
	IEventBroker eventBroker;
	
	private Table table;
	private CustomBeanItemContainer<Media> tableContainer;
	
	@Inject
	MWindow window;
	
	@Inject
	MediaService mediaLibService;
	
	private static class CustomBeanItemContainer<T> extends BeanItemContainer<T>
	{
		public CustomBeanItemContainer(Class<? super T> type) throws IllegalArgumentException
		{
			super(type);
			// TODO Auto-generated constructor stub
		}
		
		public void update()
		{
			fireItemSetChange();
		}
	}

	@Inject
	public void PlaylistView(VerticalLayout parent, IEclipseContext context)
	{
		panel = new Panel();
		panel.setSizeFull();
		parent.addComponent(panel);
	}

	public void addMedia(Media media)
	{
		tableContainer.addItem(media);
		this.playlist.addMedia(media);
		updatePlaylistStyle();
	}

	public void addMediaAfter(Media prevMedia, Media media)
	{
		tableContainer.addItemAfter(prevMedia, media);
		this.playlist.addMediaAfter(media);
		updatePlaylistStyle();
	}
	
	EventHandler mediaOrderHandler = new EventHandler() {
		
		@Override
		public void handleEvent(Event event)
		{
			tableContainer.removeAllItems();
			for (Media media : playlist.getMediaList())
			{
				tableContainer.addItem(media);
			}
		}
	};
	
	EventHandler deleteMediaHandler = new EventHandler() {
			
			@Override
			public void handleEvent(Event event)
			{
				Media deletedMedia = (Media) event.getProperty(IEventBroker.DATA);
				if (deletedMedia != null)
				{
					tableContainer.removeItem(deletedMedia);
					updatePlaylistStyle();
				}
			}
		};
		
	private EventHandler mediaChangedHandler = new EventHandler() {
		
		@Override
		public void handleEvent(Event event) {
			Object data = event.getProperty(EventUtils.DATA);
			if (data instanceof Media){
				tableContainer.update();
			}
		}
	};

	@PostConstruct
	public void postCostruct()
	{
		initTable();
		for (Media media : this.playlist.getMediaList())
		{
			tableContainer.addItem(media);
		}
		
		updatePlaylistStyle();
		
		eventBroker.subscribe(MediaConstants.reversePlaylist, mediaOrderHandler);
		eventBroker.subscribe(MediaConstants.deleteMediaFromPlaylist, deleteMediaHandler);
		eventBroker.subscribe(MediaConstants.mediaEntryChanged, mediaChangedHandler);
	}
	
	@PreDestroy
	public void preDestroy()
	{
		eventBroker.unsubscribe(mediaOrderHandler);
		eventBroker.unsubscribe(deleteMediaHandler);
		eventBroker.unsubscribe(mediaChangedHandler);
	}

	private void initTable()
	{
		
		table = new Table();
		table.setSizeFull();
		table.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);

		this.panel.setContent(table);
		
		tableContainer = new CustomBeanItemContainer<Media>(Media.class);
		table.setContainerDataSource(tableContainer);
		table.setVisibleColumns(new Object[] { "name" });

		// Handle drop in table: move hardware item or subtree to the table
		table.setDragMode(TableDragMode.ROW);
		table.setDropHandler(new DropHandler() {
			public void drop(DragAndDropEvent dropEvent)
			{

				if (!(dropEvent.getTransferable() instanceof DataBoundTransferable))
					return;

				DataBoundTransferable t = (DataBoundTransferable) dropEvent.getTransferable();
				if (!(t.getSourceContainer() instanceof Container.Hierarchical))
				{
					return;
				}

				AbstractSelectTargetDetails dropData = ((AbstractSelectTargetDetails) dropEvent.getTargetDetails());

				Object sourceItemId = t.getItemId();

				if (sourceItemId == null)
					return;

				Media media = mediaLibService.findMediaById(mediaLibrary, sourceItemId.toString(), "/");

				Media targetMedia = (Media) dropData.getItemIdOver();
				Media prevMedia = targetMedia != null ? tableContainer.prevItemId(targetMedia) : null;

				if (targetMedia != null)
				{
					switch (dropData.getDropLocation())
					{
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
					addMedia(media);
				}
			}

			public AcceptCriterion getAcceptCriterion()
			{
				return AcceptItem.ALL;
			}
		});
		
		this.table.setImmediate(true);
		this.table.setSelectable(true);
		this.table.setMultiSelect(false);
		
		this.table.addListener(new Table.ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				Media media = (Media) table.getValue();
				playlist.setSelectedMedia(media);
			}
		});
	}
	
	private void updatePlaylistStyle()
	{
		if (this.playlist.getMediaList().isEmpty())
			table.addStyleName("emptyplaylist");
		else
			table.removeStyleName("emptyplaylist");
	}
}
