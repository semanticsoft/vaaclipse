/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipsedemo.mediaplayer.constants.MediaConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaLibrary;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Playlist;
import org.semanticsoft.vaaclipsedemo.mediaplayer.service.MediaService;
import org.vaadin.overlay.TextOverlay;

import com.vaadin.data.Container;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Property.ValueChangeEvent;
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
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

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
	private BeanItemContainer<Media> tableContainer;
	
	@Inject
	MWindow window;
	
	@Inject
	MediaService mediaLibService;

	@Inject
	public void PlaylistView(VerticalLayout parent, IEclipseContext context)
	{
		panel = new Panel();
		panel.setSizeFull();
		panel.getContent().setSizeFull();
		parent.addComponent(panel);
	}

	public void addMedia(Media media)
	{
		tableContainer.addItem(media);
		this.playlist.addMedia(media);
	}

	public void addMediaAfter(Media prevMedia, Media media)
	{
		tableContainer.addItemAfter(prevMedia, media);
		this.playlist.addMediaAfter(media);
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
		
//		TextOverlay notFoundOverlay = new TextOverlay(this.panel, "<i>[Drag here]</i>");
//		notFoundOverlay.setContentMode(TextOverlay.CONTENT_RAW);
//		notFoundOverlay.setComponentAnchor(Alignment.MIDDLE_CENTER);
//		notFoundOverlay.setOverlayAnchor(Alignment.MIDDLE_CENTER);
//		((Window)window.getWidget()).addComponent(notFoundOverlay);
		
		eventBroker.subscribe(MediaConstants.reversePlaylist, mediaOrderHandler);
		eventBroker.subscribe(MediaConstants.deleteMediaFromPlaylist, deleteMediaHandler);
	}

	private void initTable()
	{
		
		table = new Table();
		table.setSizeFull();
		table.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);

		this.panel.addComponent(table);

		MPart mediaLibPart = (MPart) modelService.find("org.semanticsoft.vaaclipsedemo.mediaplayer.part.medialib", app);
		MediaLibraryView mediaLibView = (MediaLibraryView) mediaLibPart.getObject();
		// TODO: mediaLibView can be null at initialization time... change this
		// code...
		final ClientSideCriterion acceptCriterion = new SourceIs(mediaLibView.getTree());

		tableContainer = new BeanItemContainer<Media>(Media.class);
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
				return new And(acceptCriterion, AcceptItem.ALL);
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
				System.out.println("media selected: " + media.getName());
			}
		});
	}
}
