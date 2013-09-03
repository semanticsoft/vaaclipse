package org.semanticsoft.vaaclipse.p2.sites.ui.impl;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.semanticsoft.vaaclipse.p2.iservice.ISitesManager;
import org.semanticsoft.vaaclipse.p2.sites.ui.ISitesView;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

/*******************************************************************************
 * Copyright (c) 2012 Klevis Ramo and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Klevis Ramo - initial API and implementation
 *******************************************************************************/
public class SitesView implements ISitesView {

	VerticalLayout mainLayout = new VerticalLayout();
	private TreeTable treeTable;
	IProvisioningAgent agent;
	ISitesManager sitesManager;

	@Override
	public Object getUIComponent() {
		// TODO Auto-generated method stub
		return mainLayout;
	}

	public void loadSites() {

		treeTable.removeAllItems();
		List<URI> listAllUpdateSites = sitesManager.listAllUpdateSites(agent);
		for (URI uri : listAllUpdateSites) {

			String string = uri.toString();

			treeTable.addItem(new Object[] { "", uri.toString(), "true" }, uri);
		}

	}

	public SitesView(IProvisioningAgent agent, ISitesManager sitesManager) {
		super();
		this.agent = agent;
		this.sitesManager = sitesManager;
		initUI();
		loadSites();
	}

	public void addSite() {
		final AddSiteDialog addSiteDialog = new AddSiteDialog(sitesManager,
				agent);
		addSiteDialog.addCloseListener(new CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {
				// TODO Auto-generated method stub

				if (addSiteDialog.isInstalled()) {

					loadSites();

				}
			}
		});

		mainLayout.getUI().addWindow(addSiteDialog);

	}

	public void removeSite(String uri) {
		try {
			sitesManager.removeRepository(uri, agent);
		} catch (RuntimeException e) {

			e.printStackTrace();

			return;
		}
	}

	@Override
	public void initUI() {
		// TODO Auto-generated method stub

		CssLayout buttonLayout = new CssLayout();

		Button buttonAdd = new Button("Add");
		buttonAdd.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				addSite();
			}
		});
		buttonLayout.addComponent(buttonAdd);
		Button buttonRemove = new Button("Remove");
		buttonRemove.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				Collection<URI> value = (Collection<URI>) treeTable.getValue();
				if(value.isEmpty()){
					
					Notification.show("Mus select at least one");
					return;
				}
				for (URI u : value) {

					removeSite(u.toString());

				}

				loadSites();
			}
		});
		buttonLayout.addComponent(buttonRemove);
		Button buttonEdit = new Button("Edit");
		buttonLayout.addComponent(buttonEdit);
		VerticalLayout verticalLayoutButton = new VerticalLayout();

		CheckBox enableChek = new CheckBox("Enable");
		verticalLayoutButton.addComponent(enableChek);
		Button reloadButton = new Button("Reload");
		verticalLayoutButton.addComponent(reloadButton);
		Button importButton = new Button("Import");
		verticalLayoutButton.addComponent(importButton);
		Button exportButton = new Button("Export");
		verticalLayoutButton.addComponent(exportButton);

		treeTable = new TreeTable("List of avaible software sites");
		treeTable.addContainerProperty("Name", String.class, "");
		treeTable.addContainerProperty("Location", String.class, "");
		treeTable.addContainerProperty("Enable", String.class, "");
		treeTable.setPageLength(5);
		treeTable.setWidth("40em");
		treeTable.setSelectable(true);
		treeTable.setImmediate(true);
		treeTable.setMultiSelect(true);

		HorizontalLayout simpleContainer = new HorizontalLayout();
		simpleContainer.addComponent(treeTable);
		simpleContainer.addComponent(verticalLayoutButton);

		mainLayout.addComponent(buttonLayout);
		mainLayout.addComponent(simpleContainer);

	}

	@Override
	public String errorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

}
