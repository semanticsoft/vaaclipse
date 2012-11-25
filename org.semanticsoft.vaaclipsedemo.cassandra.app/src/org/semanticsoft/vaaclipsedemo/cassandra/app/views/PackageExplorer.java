/*******************************************************************************
 * Copyright (c) 2012 Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *******************************************************************************/

package org.semanticsoft.vaaclipsedemo.cassandra.app.views;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;

import org.eclipse.e4.ui.workbench.modeling.EPartService;

import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;

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
	private static final String fs = System.getProperty("file.separator");
	private static final String PROJECT_TREE_ROOT = "Cassandra Demo";
	
	@Inject
	private IEclipseContext context;
	@Inject
	private Application app;
	
	@Inject
	private MApplication application;

	@Inject
	private EModelService modelService;
	
	@Inject
	private EPartService partService;
	
	@Inject
	private EPartServiceExt partServiceExt;
	
	@Inject
	private MPart part;
	
	private MToolItem linkWithEditorItem;
	
	private MArea editorArea;
	private Console console;

	// Gui components
	public Tree tree;
	public Panel panel;
	
	//State
	private boolean linkWithEditor = false;
	
	IEventBroker eventBroker;
	
	

	@Inject
	public void PackageExplorer(VerticalLayout parent, IEclipseContext context)
	{
		panel = new Panel();
		panel.setSizeFull();
		parent.addComponent(panel);
		
		File demoRoot = BundleActivatorImpl.getInstance().getHomeDirectory();
		
		createProjectTree(demoRoot);
		
		eventBroker = context.get(IEventBroker.class);
	}
	
	@PostConstruct
	void registerHandler()
	{
		//this is not work, becouse the find service search only in childs
		//linkWithEditorItem = (MToolItem) modelService.find("org.semanticsoft.vaaclipsedemo.cassandra.app.directtoolitem.linkwitheditor", application);
		
		for (MToolBarElement e : part.getToolbar().getChildren())
		{
			if ("org.semanticsoft.vaaclipsedemo.cassandra.app.directtoolitem.linkwitheditor".equals(e.getElementId()))
			{
				linkWithEditorItem = (MToolItem) e;
			}
		}
		
		if (linkWithEditorItem != null)
			setLinkWithEditor(linkWithEditorItem.isSelected());
		
//		eventBroker.subscribe("test1", new EventHandler() {
//			
//			public void handleEvent(Event event)
//			{
//				String msg = event.getProperty(IEventBroker.DATA).toString();
//				String str = String.format("workbench with instanceid %s receive message from workbench with instanceid=%s ", context.get("e4ApplicationInstanceId"), msg);
//				System.out.println(str);
//			}
//		});
	}
	
	@PreDestroy
	void unregisterHandlers()
	{
		System.out.println("pckexplorer destroy");
		eventBroker.unsubscribe(activatePartHandler);
	}
	
	private EventHandler activatePartHandler = new EventHandler() {
		public void handleEvent(Event event) {
			Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
			
			if (!(element instanceof MInputPart))
				return;
			
			selectTreeNode((MInputPart) element);
		}
	};

	private void createProjectTree(File demoRoot)
	{
		tree = new Tree(PROJECT_TREE_ROOT);
		tree.setSizeFull();
		tree.setImmediate(true);
		panel.addComponent(tree);
		
		FilesystemContainer fsc = new FilesystemContainer(demoRoot, true);
		FileTypeResolver.addExtension("java", "java");
		FileTypeResolver.addIcon("java", new ThemeResource("org.semanticsoft.vaaclipsedemo.cassandra.app/img/java.png"));
		FileTypeResolver.addExtension("xml", "xml");
		FileTypeResolver.addIcon("xml", new ThemeResource("org.semanticsoft.vaaclipsedemo.cassandra.app/img/xml.png"));
		FileTypeResolver.addExtension("css", "css");
		FileTypeResolver.addIcon("css", new ThemeResource("org.semanticsoft.vaaclipsedemo.cassandra.app/img/css.png"));
		FileTypeResolver.addIcon("image/png", new ThemeResource("org.semanticsoft.vaaclipsedemo.cassandra.app/img/img.png"));
		
		FileTypeResolver.addIcon("inode/directory", new ThemeResource("org.semanticsoft.vaaclipsedemo.cassandra.app/img/folder.png"));

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
		
		String path = file.getAbsolutePath();
		MInputPart part = partServiceExt.openUri(editorArea, path);
		int lastLsIndex = path.lastIndexOf(fs);
		if (lastLsIndex > 0)
			part.setLabel(path.substring(lastLsIndex + 1));	
		
		
		String pathStr = path;
		int projectTreeRootIndex = path.indexOf(".cassandra");
		if (projectTreeRootIndex > -1)
		{
			int i = path.indexOf(fs, projectTreeRootIndex);
			pathStr = projectTreeRootIndex > -1 ? path.substring(i + 1) : path;
		}
		
		getConsole().println("Open file: " + pathStr);
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
	
	public boolean isLinkWithEditor()
	{
		return linkWithEditor;
	}
	
	public void setLinkWithEditor(boolean linkWithEditor)
	{	
		this.linkWithEditor = linkWithEditor;
		
		if (this.linkWithEditor)
		{
			MInputPart inputPart = (MInputPart) partService.getActivePart();
			if (inputPart != null)
				selectTreeNode(inputPart);
			
			eventBroker.subscribe(UIEvents.UILifeCycle.ACTIVATE, activatePartHandler);
		}
		else
			eventBroker.unsubscribe(activatePartHandler);
			
	}

	private void selectTreeNode(MInputPart inputPart)
	{
		final File f = new File(inputPart.getInputURI());
		tree.select(f);
	}
}
