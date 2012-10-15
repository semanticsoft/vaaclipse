/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.cassandra.app.views;

import java.util.concurrent.Executors;

import java.util.concurrent.ExecutorService;

import java.util.concurrent.Executor;

import org.eclipse.e4.ui.model.application.MContribution;

import org.eclipse.e4.ui.model.application.ui.MUIElement;

import javax.annotation.PreDestroy;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.services.events.IEventBroker;

import com.vaadin.Application;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.data.util.FilesystemContainer.FileItem;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.service.FileTypeResolver;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import java.io.File;
import java.lang.reflect.Field;
import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.e4extension.service.EPartServiceExt;
import org.semanticsoft.vaaclipsedemo.cassandra.app.BundleActivatorImpl;

/**
 * @author rushan
 *
 */
public class PackageExplorer
{
	private static final Action ACTION_ADD = new Action("Add child item");
	private static final Action ACTION_DELETE = new Action("Delete");
	private static final Action[] ACTIONS = new Action[] { ACTION_ADD, ACTION_DELETE };
	
	@Inject
	private IEclipseContext context;
	@Inject
	private Application app;
	
	@Inject
	private MApplication application;

	@Inject
	private EModelService modelService;
	
	@Inject
	private EPartServiceExt partServiceExt;
	
	private MArea editorArea;
	private Console console;

	// Gui components
	public Tree tree;
	public Panel panel;
	
	IEventBroker eventBroker;

	@Inject
	public void PackageExplorer(VerticalLayout parent, IEclipseContext context)
	{
		panel = new Panel();
		panel.setSizeFull();
		panel.setScrollable(true);
		parent.addComponent(panel);
		
		File demoRoot = BundleActivatorImpl.getInstance().getHomeDirectory();
		
		createProjectTree(demoRoot);
		
		eventBroker = context.get(IEventBroker.class);
	}
	
	@PostConstruct
	void registerHandler()
	{
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT, selectElementHandler);
	}
	
	@PreDestroy
	void unregisterHandlers()
	{
		eventBroker.unsubscribe(selectElementHandler);
	}
	
	private EventHandler selectElementHandler = new EventHandler() {
		public void handleEvent(Event event) {
			Object element = event.getProperty(UIEvents.EventTags.ELEMENT);

			if (!(element instanceof MPartStack))
				return;
			
			MPartStack stack = (MPartStack) element;
			if (stack.getSelectedElement() instanceof MInputPart)
			{
				MInputPart inputPart = (MInputPart)stack.getSelectedElement();
				final File f = new File(inputPart.getInputURI());
				tree.select(f);	
			}
		}
	};

	private void createProjectTree(File demoRoot)
	{
		tree = new Tree("Cassandra Demo");
		tree.setSizeFull();
		tree.setImmediate(true);
		panel.addComponent(tree);
		
		FilesystemContainer fsc = new FilesystemContainer(demoRoot, true);
		FileTypeResolver.addExtension("java", "java");
		FileTypeResolver.addIcon("java", new ThemeResource("img/java.png"));
		FileTypeResolver.addExtension("xml", "xml");
		FileTypeResolver.addIcon("xml", new ThemeResource("img/xml.png"));
		FileTypeResolver.addExtension("css", "css");
		FileTypeResolver.addIcon("css", new ThemeResource("img/css.png"));
		FileTypeResolver.addIcon("image/png", new ThemeResource("img/img.png"));
		
		FileTypeResolver.addIcon("inode/directory", new ThemeResource("img/folder.png"));

		tree.setContainerDataSource(fsc);

		tree.addListener(new ItemClickEvent.ItemClickListener() {

			long lastTime = 0;
			File lastFile;
			
			public void itemClick(final ItemClickEvent event)
			{
				if (event.getButton() == ItemClickEvent.BUTTON_LEFT)
				{
					long time = System.currentTimeMillis();
					if (lastTime > 0 && time - lastTime < 300)
					{
						tree.select(event.getItemId());
						
//						getConsole().println("time: " + time + "; last time: " + lastTime);
						
						FileItem fileItem = (FileItem) event.getItem();
						try
						{
							for (Field f : FileItem.class.getDeclaredFields())
							{
								if (f.getName().equals("file"))
								{
									f.setAccessible(true);
									final File file = (File) f.get(fileItem);
									if (!file.equals(lastFile))
									{
										openFile(file);
										lastFile = file;
									}
									break;
								}
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					lastTime = time;
				}
			}
		});
		
		// Set tree to show the 'name' property as caption for items
		tree.setItemCaptionPropertyId(FilesystemContainer.PROPERTY_NAME);
		tree.setItemIconPropertyId(FilesystemContainer.PROPERTY_ICON);
		// tree.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
		
		// Expand whole tree
		for (Object id : tree.rootItemIds())
		{
			tree.expandItemsRecursively(id);
		}
	}
	
	protected void openFile(File file)
	{
		if (editorArea == null)
			editorArea = (MArea) modelService.find("org.semanticsoft.vaaclipsedemo.cassandra.app.editorarea", application);
		
		partServiceExt.openUri(editorArea, file.getAbsolutePath());
		getConsole().println("Open file: " + file.getAbsolutePath());
	}
	
	private Console getConsole()
	{
		if (console == null)
		{
			console = (Console) 
					((MContribution)modelService.find("org.semanticsoft.vaaclipsedemo.cassandra.app.part.console", application)).getObject();
		}
		
		return console;
	}
}
