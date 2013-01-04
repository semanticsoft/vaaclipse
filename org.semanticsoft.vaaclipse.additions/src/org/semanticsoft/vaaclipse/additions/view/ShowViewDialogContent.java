/**
 * 
 */
package org.semanticsoft.vaaclipse.additions.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.semanticsoft.vaaclipse.publicapi.resources.BundleResource;
import org.semanticsoft.vaadin.optiondialog.OptionDialog;
import org.semanticsoft.vaadin.optiondialog.OptionDialog.ComponentProvider;

import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.TreeDragMode;


/**
 * @author rushan
 *
 */
class ShowViewDialogContent implements ComponentProvider
{
	private static final String NAME_PROP = "name";
	private static final String ICON_PROP = "icon";
	private static final String OBJECT_PROP = "object";
	
	final private static String CATEGORY_TAG = "categoryTag:";
	final private static int CATEGORY_TAG_LENGTH = CATEGORY_TAG.length();
	
	private Tree tree;
	private Panel panel;
	private HierarchicalContainer container;
	private OptionDialog optionDialog;
	
//	private Map<Integer, MPartDescriptor> descriptorsMap = new HashMap<Integer, MPartDescriptor>();
	
	@Inject
	private EPartService partService;
	
	@Inject
	private MApplication application;
	
	@Inject
	IEclipseContext context;
	
	@Override
	public Component getComponent(OptionDialog optionDialog)
	{
		this.optionDialog = optionDialog;
		return this.panel;
	}
	
	@PostConstruct
	public void init()
	{
		panel = new Panel();
		createTree();
	}
	
	@Override
	public void optionSelected(OptionDialog optionDialog, int optionId)
	{
		if (optionId == 0)
		{
			Object selected = tree.getValue();
			if (selected instanceof MPartDescriptor)
			{
				optionDialog.close();
				
				MPartDescriptor descriptor = (MPartDescriptor)selected;
				showView(descriptor);
			}
		}
		else if (optionId == 1)
			optionDialog.close();
	}
	
	public void showView(MPartDescriptor... descriptors)
	{
		for (MPartDescriptor descriptor : descriptors) 
		{
			partService.showPart(descriptor.getElementId(), PartState.ACTIVATE);
		}
	}
	
	@Override
	public void setMessage(String message) {}
	
	private void createTree()
	{
		tree = new Tree();
		tree.setDragMode(TreeDragMode.NODE);
		tree.setSizeFull();
		tree.setImmediate(true);
		panel.addComponent(tree);

		container = createDataSource();
		tree.setContainerDataSource(container);
		
		// Set tree to show the 'name' property as caption for items
		tree.setItemCaptionPropertyId(NAME_PROP);
		tree.setItemIconPropertyId(ICON_PROP);
		
		tree.addListener(new ItemClickEvent.ItemClickListener() {

			private static final long serialVersionUID = 1L;

			public void itemClick(final ItemClickEvent event)
			{
				if (event.getButton() == ItemClickEvent.BUTTON_LEFT)
				{
					Item item = event.getItem();
					Object object = item.getItemProperty(OBJECT_PROP).getValue();
					optionDialog.setOptionEnabled(0, object instanceof MPartDescriptor);
				}
			}
		});
		
		// Expand whole tree
		for (Object id : tree.rootItemIds())
		{
			tree.expandItemsRecursively(id);
		}
	}
	
	private void setupCategoryItem(Item categoryItem, String category)
	{
		categoryItem.getItemProperty(NAME_PROP).setValue(category);
		categoryItem.getItemProperty(ICON_PROP).setValue(BundleResource.valueOf("platform:/plugin/org.semanticsoft.vaaclipse.resources/VAADIN/themes/vaaclipse_default_theme/img/folder.png"));
		categoryItem.getItemProperty(OBJECT_PROP).setValue(category);
	}
	
	private void setupDescriptorItem(Item descriptorItem, MPartDescriptor descriptor)
	{
		descriptorItem.getItemProperty(NAME_PROP).setValue(descriptor.getLabel());
		descriptorItem.getItemProperty(ICON_PROP).setValue(BundleResource.valueOf(descriptor.getIconURI()));
		descriptorItem.getItemProperty(OBJECT_PROP).setValue(descriptor);
	}

	private HierarchicalContainer createDataSource()
	{
		HierarchicalContainer data = new HierarchicalContainer();
		
		data.addContainerProperty(NAME_PROP, String.class, "No Name");
		data.addContainerProperty(ICON_PROP, ThemeResource.class, null);
		data.addContainerProperty(OBJECT_PROP, Object.class, null);
		
		List<MPartDescriptor> descriptors = application.getDescriptors();
		Set<String> categoryTags = new HashSet<String>();
		
		for (MPartDescriptor descriptor : descriptors) 
		{
			List<String> tags = descriptor.getTags();
			String category = null;
			boolean isView = false;
			for (String tag : tags) {
				if (tag.equals("View"))
					isView = true;
				else if (tag.startsWith(CATEGORY_TAG)) {
					category = tag.substring(CATEGORY_TAG_LENGTH);
				}
			}
			if (isView) {
				Item descriptorItem = data.addItem(descriptor);
				setupDescriptorItem(descriptorItem, descriptor);
				
				if (category != null)
				{
					if (!categoryTags.contains(category))
					{
						categoryTags.add(category);
						Item categoryItem = data.addItem(category);
						setupCategoryItem(categoryItem, category);	
					}
					
					data.setParent(descriptor, category);
				}
			}
		}
		
		return data;
	}
}
