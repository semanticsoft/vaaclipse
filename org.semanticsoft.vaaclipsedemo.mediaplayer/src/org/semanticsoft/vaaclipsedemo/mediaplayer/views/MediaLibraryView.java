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
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.publicapi.resources.BundleResource;
import org.semanticsoft.vaaclipsedemo.mediaplayer.constants.MediaConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaCategory;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaEntry;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaLibrary;
import org.semanticsoft.vaaclipsedemo.mediaplayer.service.MediaService;

import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.TreeDragMode;
import com.vaadin.ui.VerticalLayout;

/**
 * @author rushan
 *
 */
public class MediaLibraryView {
	
	private static final String NAME_PROP = "name";
	private static final String ICON_PROP = "icon";
	private static final String OBJECT_PROP = "object";
	
	private Panel panel;
	private Tree tree;

	@Inject
	MediaLibrary mediaLibrary;
	
	@Inject
	EModelService modelService;
	
	@Inject
	MApplication app;
	
	@Inject
	IEventBroker broker;
	
	@Inject
	MediaService mediaService;
	
	private HierarchicalContainer container;
	
	private EventHandler mediaEntryChangedHandler = new EventHandler() {
		
		@Override
		public void handleEvent(Event event) {
			Object data = event.getProperty(EventUtils.DATA);
			if (data instanceof MediaEntry){
				MediaEntry media = (MediaEntry) data;
				String id = mediaService.getId(media);
				Item item = container.getItem(id);
				item.getItemProperty(NAME_PROP).setValue(media.getName());
			}
		}
	};
	
	private EventHandler mediaEntryAdded = new EventHandler() {
			
			@Override
			public void handleEvent(Event event) {
				Object data = event.getProperty(EventUtils.DATA);
				if (data instanceof MediaEntry){
					MediaEntry mediaEntry = (MediaEntry) data;
					MediaCategory parent = mediaEntry.getParent();
					String id = mediaService.getId(mediaEntry);
					String parentid = mediaService.getId(parent);
					Item item = container.addItem(id);
					container.setParent(id, parentid);
					setupMediaEntry(mediaEntry, item);
				}
			}
		};
		
	private EventHandler mediaEntryRemoved = new EventHandler() {
				
				@Override
				public void handleEvent(Event event) {
					Object data = event.getProperty(EventUtils.DATA);
					if (data instanceof MediaEntry){
						MediaEntry mediaEntry = (MediaEntry) data;
						String id = mediaService.getId(mediaEntry);
						container.removeItemRecursively(id);
					}
				}
			};
	
	@PostConstruct
	public void postConstruct(VerticalLayout parent, IEclipseContext context)
	{
		panel = new Panel();
		panel.setSizeFull();
		parent.addComponent(panel);
		
		createMediaLibraryTree();
		
		broker.subscribe(MediaConstants.mediaEntryChanged, mediaEntryChangedHandler);
		broker.subscribe(MediaConstants.mediaEntryAdded, mediaEntryAdded);
		broker.subscribe(MediaConstants.mediaEntryRemoved, mediaEntryRemoved);
	}
	
	@PreDestroy
	public void preDestory()
	{
		broker.unsubscribe(mediaEntryChangedHandler);
		broker.unsubscribe(mediaEntryAdded);
		broker.unsubscribe(mediaEntryRemoved);
	}
	
	private void createMediaLibraryTree()
	{
		tree = new Tree();
		tree.setDragMode(TreeDragMode.NODE);
		tree.setSizeFull();
		tree.setImmediate(true);
		panel.setContent(tree);

		container = createMediaLibraryDataSource();
		tree.setContainerDataSource(container);

		tree.addListener(new ItemClickEvent.ItemClickListener() {

			private static final long serialVersionUID = 1L;

			public void itemClick(final ItemClickEvent event)
			{
				if (event.getButton() == ItemClickEvent.BUTTON_LEFT)
				{
					Item item = event.getItem();
					Object object = item.getItemProperty(OBJECT_PROP).getValue();
					if (object != null && object instanceof MediaEntry)
					{
						MediaEntry media = (MediaEntry)object;
						
						mediaLibrary.setSelectedMediaEntry(media);
						broker.send(MediaConstants.mediaEntrySelected, media);
					}
				}
			}
		});
		
		// Set tree to show the 'name' property as caption for items
		tree.setItemCaptionPropertyId(NAME_PROP);
		tree.setItemIconPropertyId(ICON_PROP);
		
		// Expand whole tree
		for (Object id : tree.rootItemIds())
		{
			tree.expandItemsRecursively(id);
		}
	}
		
	private HierarchicalContainer createMediaLibraryDataSource()
	{
		HierarchicalContainer container = new HierarchicalContainer();
		container.addContainerProperty(NAME_PROP, String.class, "No Name");
		container.addContainerProperty(ICON_PROP, ThemeResource.class, null);
		container.addContainerProperty(OBJECT_PROP, Object.class, null);
		fillContainer(mediaLibrary, null, "", container);
		return container;
	}
	
	private void fillContainer(MediaCategory category, Item categoryItem, String categoryPath, HierarchicalContainer container)
	{
		for (MediaCategory childCategory : category.getCategories())
		{
			String childCategoryPath = categoryPath + "/" + childCategory.getName();
			Item childCategoryItem = container.addItem(childCategoryPath);
			if (!(category instanceof MediaLibrary))
				container.setParent(childCategoryPath, categoryPath);
			setupCategory(childCategory, childCategoryItem);
			fillContainer(childCategory, childCategoryItem, childCategoryPath, container);
		}
		
		for (Media media : category.getMediaList())
		{
			String mediaPath = categoryPath + "/" + media.getUri();
			Item mediaItem = container.addItem(mediaPath);
			if (!(category instanceof MediaLibrary))
				container.setParent(mediaPath, categoryPath);
			container.setChildrenAllowed(mediaPath, false);
			setupItem(media, mediaItem);
		}
	}
	
	private void setupMediaEntry(MediaEntry mediaEntry, Item item)
	{
		if (mediaEntry instanceof MediaCategory)
		{
			setupCategory((MediaCategory) mediaEntry, item);
		}
		else if (mediaEntry instanceof Media)
		{
			setupItem((Media) mediaEntry, item);
		}
	}

	private void setupCategory(MediaCategory childCategory, Item childCategoryItem)
	{
		childCategoryItem.getItemProperty(NAME_PROP).setValue(childCategory.getName());
		childCategoryItem.getItemProperty(ICON_PROP).setValue(BundleResource.valueOf("platform:/plugin/org.semanticsoft.vaaclipsedemo.mediaplayer/icons/mediacategory.png"));
		childCategoryItem.getItemProperty(OBJECT_PROP).setValue(childCategory);
	}

	private void setupItem(Media media, Item mediaItem)
	{
		mediaItem.getItemProperty(NAME_PROP).setValue(media.getName());
		mediaItem.getItemProperty(ICON_PROP).setValue(BundleResource.valueOf("platform:/plugin/org.semanticsoft.vaaclipsedemo.mediaplayer/icons/media.png"));
		mediaItem.getItemProperty(OBJECT_PROP).setValue(media);
	}
	
	public Tree getTree()
	{
		return tree;
	}
}
