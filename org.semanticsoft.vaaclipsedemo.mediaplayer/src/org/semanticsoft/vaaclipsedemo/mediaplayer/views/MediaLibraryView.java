/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.views;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaCategory;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaLibrary;

import com.vaadin.Application;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.Item;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.data.util.FilesystemContainer.FileItem;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
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
	private MPart playerPart;
	private MPart mediaInfoPart;
	
	@Inject
	MediaLibrary mediaLibrary;
	
	@Inject
	EModelService modelService;
	
	@Inject
	MApplication app;
	
	@Inject
	public void MedialibraryView(VerticalLayout parent, IEclipseContext context)
	{
		panel = new Panel();
		panel.setSizeFull();
		parent.addComponent(panel);
		
		createProjectTree();
	}
	
	private void createProjectTree()
	{
		tree = new Tree();
		tree.setSizeFull();
		tree.setImmediate(true);
		panel.addComponent(tree);

		Hierarchical container = createMediaLibraryDataSource();
		tree.setContainerDataSource(container);

		tree.addListener(new ItemClickEvent.ItemClickListener() {

			public void itemClick(final ItemClickEvent event)
			{
				if (event.getButton() == ItemClickEvent.BUTTON_LEFT)
				{
//					tree.select(event.getItemId());
//					getConsole().println("time: " + time + "; last time: " + lastTime);
					
					Item item = event.getItem();
					Object object = item.getItemProperty(OBJECT_PROP).getValue();
					if (object != null && object instanceof Media)
					{
						Media media = (Media)object;
						
						MPart mediaInfoPart = getMediaInfoPart();
						MediaInfoView mediaInfoView = (MediaInfoView) mediaInfoPart.getObject();
						mediaInfoView.setMedia(media);
						
						MPart playerPart = getPlayerPart();
						playerPart.setLabel(media.getName());
						PlayerView playerView = (PlayerView) playerPart.getObject();
						playerView.setMedia(media);
					}
				}
			}
		});
		
		// Set tree to show the 'name' property as caption for items
		tree.setItemCaptionPropertyId(NAME_PROP);
		tree.setItemIconPropertyId(ICON_PROP);
		// tree.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
		
		// Expand whole tree
		for (Object id : tree.rootItemIds())
		{
			tree.expandItemsRecursively(id);
		}
	}
	
	private MPart getMediaInfoPart()
	{
		if (mediaInfoPart == null)
		{
			mediaInfoPart = findPart("org.semanticsoft.vaaclipsedemo.mediaplayer.part.mediainfo");
			return mediaInfoPart;
		}
		return mediaInfoPart;
	}
	
	private MPart getPlayerPart()
	{
		if (playerPart == null)
		{
			playerPart = findPart("org.semanticsoft.vaaclipsedemo.mediaplayer.part.player");
			return playerPart;
		}
		return playerPart;
	}
	
	private MPart findPart(String id)
	{
		List<MPart> elements = modelService.findElements(app, id, MPart.class, null, EModelService.IN_ACTIVE_PERSPECTIVE);
		if (!elements.isEmpty())
		{
			return elements.get(0);
		}
		else
			return null;
	}
	
	private Container.Hierarchical createMediaLibraryDataSource()
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
			childCategoryItem.getItemProperty(NAME_PROP).setValue(childCategory.getName());
			childCategoryItem.getItemProperty(ICON_PROP).setValue(new ThemeResource("org.semanticsoft.vaaclipsedemo.mediaplayer/icons/mediacategory.png"));
			childCategoryItem.getItemProperty(OBJECT_PROP).setValue(childCategory);
			fillContainer(childCategory, childCategoryItem, childCategoryPath, container);
		}
		
		for (Media media : category.getMediaList())
		{
			String mediaPath = categoryPath + "/" + media.getUri();
			Item mediaItem = container.addItem(mediaPath);
			if (!(category instanceof MediaLibrary))
				container.setParent(mediaPath, categoryPath);
			container.setChildrenAllowed(mediaPath, false);
			mediaItem.getItemProperty(NAME_PROP).setValue(media.getName());
			mediaItem.getItemProperty(ICON_PROP).setValue(new ThemeResource("org.semanticsoft.vaaclipsedemo.mediaplayer/icons/media.png"));
			mediaItem.getItemProperty(OBJECT_PROP).setValue(media);
		}
	}
}
