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

import java.util.Collections;

import java.util.ArrayList;

import java.util.List;

import org.apache.commons.io.FileUtils;

import com.vaadin.Application;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.data.util.FilesystemContainer.FileItem;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.service.FileTypeResolver;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import java.io.File;
import java.lang.reflect.Field;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.extensions.EventUtils;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.e4extension.service.EPartServiceExt;
import org.semanticsoft.vaaclipse.publicapi.resources.BundleResource;
import org.semanticsoft.vaaclipsedemo.cassandra.app.CassandraActivator;
import org.semanticsoft.vaaclipsedemo.cassandra.app.constants.CassandraConstants;

/**
 * @author rushan
 *
 */
public class PackageExplorer
{
	private static final Action ACTION_ADD = new Action("Add child item");
	private static final Action ACTION_DELETE = new Action("Delete");
	private static final Action[] ACTIONS = new Action[] { ACTION_ADD, ACTION_DELETE };
	private static final String PROJECT_TREE_ROOT = "Cassandra Demo";
	
	@Inject
	private IEclipseContext context;
	@Inject
	private Application app;
	
	@Inject
	private MApplication application;

	@Inject
	IEventBroker eventBroker;
	
	@Inject
	private EPartService partService;
	
	@Inject
	private EPartServiceExt partServiceExt;
	
	@Inject
	private MPart part;
	
	private MToolItem linkWithEditorItem;
	

	// Gui components
	public Tree tree;
	public Panel panel;
	
	//State
	private boolean linkWithEditor = false;
	
	
	private EventHandler collapseAllHandler = new EventHandler() {
		
		public void handleEvent(Event arg0)
		{
			for (Object rootId : tree.rootItemIds())
			{
				tree.collapseItemsRecursively(rootId);
			}
		}
	};
	
	private EventHandler linkWithEditorHandler = new EventHandler() {
		
		public void handleEvent(Event event)
		{
			Boolean link = (Boolean) event.getProperty(EventUtils.DATA);
			setLinkWithEditor(link);
		}
	};

	@Inject
	public void PackageExplorer(VerticalLayout parent)
	{
		panel = new Panel();
		panel.setSizeFull();
		parent.addComponent(panel);
		
		createProjectTree();
	}
	
	@PostConstruct
	void registerHandler()
	{
		//this is not work, becouse the find service search only in children
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
		
		eventBroker.subscribe(CassandraConstants.COLLAPSE_ALL, collapseAllHandler);
		eventBroker.subscribe(CassandraConstants.LINK_WITH_EDITOR, linkWithEditorHandler);
	}
	
	@PreDestroy
	void unregisterHandlers()
	{
		eventBroker.unsubscribe(activatePartHandler);
		eventBroker.unsubscribe(collapseAllHandler);
		eventBroker.unsubscribe(linkWithEditorHandler);
	}
	
	private EventHandler activatePartHandler = new EventHandler() {
		public void handleEvent(Event event) {
			Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
			
			if (!(element instanceof MInputPart))
				return;
			
			selectTreeNode((MInputPart) element);
		}
	};
	private File demoRoot;

	private void createProjectTree()
	{
		demoRoot = CassandraActivator.getInstance().getSrcStore();
		
		tree = new Tree();
		tree.setSizeFull();
		tree.setImmediate(true);
		panel.addComponent(tree);
		
		FilesystemContainer fsc = new FilesystemContainer(demoRoot, true);
		FileTypeResolver.addExtension("java", "java");
		FileTypeResolver.addIcon("java", BundleResource.valueOf("platform:/plugin/org.semanticsoft.vaaclipsedemo.cassandra.app/img/java.png"));
		FileTypeResolver.addExtension("xml", "xml");
		FileTypeResolver.addIcon("xml", BundleResource.valueOf("platform:/plugin/org.semanticsoft.vaaclipsedemo.cassandra.app/img/xml.png"));
		FileTypeResolver.addExtension("e4xmi", "e4xmi");
		FileTypeResolver.addIcon("e4xmi", BundleResource.valueOf("platform:/plugin/org.semanticsoft.vaaclipsedemo.cassandra.app/img/xmi.png"));
		FileTypeResolver.addExtension("css", "css");
		FileTypeResolver.addIcon("css", BundleResource.valueOf("platform:/plugin/org.semanticsoft.vaaclipsedemo.cassandra.app/img/css.png"));
		FileTypeResolver.addExtension("html", "html");
		FileTypeResolver.addIcon("html", BundleResource.valueOf("platform:/plugin/org.semanticsoft.vaaclipsedemo.cassandra.app/img/html.png"));
		
		FileTypeResolver.addIcon("image/png", BundleResource.valueOf("platform:/plugin/org.semanticsoft.vaaclipsedemo.cassandra.app/img/img.png"));
		
		FileTypeResolver.addIcon("inode/directory", BundleResource.valueOf("platform:/plugin/org.semanticsoft.vaaclipsedemo.cassandra.app/img/folder.png"));

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
										eventBroker.send(CassandraConstants.OPEN_FILE, file);
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
		
		for (Object id : tree.rootItemIds())
		{
			tree.expandItem(id);
		}
		
		String projectName = "org.semanticsoft.vaaclipsedemo.cassandra.app";
		File data = FileUtils.getFile(demoRoot, projectName, "data");
		if (data != null)
			tree.expandItem(data);
		
		File rootPackage = FileUtils.getFile(demoRoot, projectName, "src");
		if (rootPackage != null)
			tree.expandItemsRecursively(rootPackage);
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
			if (partService.getActivePart() instanceof MInputPart)
			{
				MInputPart inputPart = (MInputPart) partService.getActivePart();
				if (inputPart != null)
					selectTreeNode(inputPart);	
			}
			
			eventBroker.subscribe(UIEvents.UILifeCycle.ACTIVATE, activatePartHandler);
		}
		else
			eventBroker.unsubscribe(activatePartHandler);
			
	}

	private void selectTreeNode(MInputPart inputPart)
	{
		File f = new File(inputPart.getInputURI());
		File dir = f.getParentFile();
		List<File> pathToParent = new ArrayList<File>();
		pathToParent.add(dir);
		while (!dir.equals(demoRoot) && dir != null)
		{
			dir = dir.getParentFile();
			pathToParent.add(dir);
		}
		Collections.reverse(pathToParent);
		for (File d : pathToParent)
		{
			tree.expandItem(d);
		}
		tree.select(f);
	}
}
