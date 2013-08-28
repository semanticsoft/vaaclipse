package org.semanticsoft.vaaclipse.p2.uninstall.ui.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;

import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.semanticsoft.vaaclipse.p2.iservice.IUninstallSoftwareService;
import org.semanticsoft.vaaclipse.p2.uninstall.ui.IUninstallView;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
/*******************************************************************************
 * Copyright (c) 2012 Klevis Ramo and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Klevis Ramo - initial API and implementation
 *******************************************************************************/
public class UninstallView implements IUninstallView {
	VerticalLayout mainLayout = new VerticalLayout();
	private TreeTable treeTable;
	private Button buttonUninstall;
	List<IInstallableUnit> list;
	String errorMessage = "";
	IUninstallSoftwareService uninstallSoftwareService;
	IProvisioningAgent agent;

	public UninstallView(List<IInstallableUnit> list,
			IUninstallSoftwareService uninstallSoftwareService,
			IProvisioningAgent agent) {
		super();
		this.list = list;
		this.agent = agent;
		this.uninstallSoftwareService = uninstallSoftwareService;
		initUI();
		addRepositories(list);

	}

	public UninstallView(IUninstallSoftwareService uninstallSoftwareService,
			IProvisioningAgent agent) {
		super();
		this.agent = agent;
		this.uninstallSoftwareService = uninstallSoftwareService;
		initUI();
	}

	@Override
	public void addRepositories(List<IInstallableUnit> list) {
		// TODO Auto-generated method stub

		this.list = list;
		treeTable.removeAllItems();
		for (IInstallableUnit iInstallableUnit : list) {
			treeTable.addItem(new Object[] { iInstallableUnit.getId(),
					iInstallableUnit.getVersion().toString() },
					iInstallableUnit.getId());

		}

	}

	@Override
	public List<IInstallableUnit> getRepositories() {
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public Object getUIComponent() {
		// TODO Auto-generated method stub
		return mainLayout;
	}

	@Override
	public void initUI() {
		// TODO Auto-generated method stub

		OptionGroup optionGroup = new OptionGroup("Choose display mode");
		optionGroup.addItem("Group");
		optionGroup.addItem("Category");
		optionGroup.addItem("Any");
		optionGroup.setImmediate(true);
		optionGroup.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub

				Object value = event.getProperty().getValue();

				if (value.equals("Group")) {

					addRepositories(uninstallSoftwareService
							.listInstalledSoftware(agent,
									IUninstallSoftwareService.GROUP));
				} else if (value.equals("Category")) {
					addRepositories(uninstallSoftwareService
							.listInstalledSoftware(agent,
									IUninstallSoftwareService.CATEGORY));
				} else if (value.equals("Any")) {
					addRepositories(uninstallSoftwareService
							.listInstalledSoftware(agent,
									IUninstallSoftwareService.ANY));
				}
			}
		});

		mainLayout.addComponent(optionGroup);

		treeTable = new TreeTable("List of installed software");
		treeTable.addContainerProperty("Name", String.class, "");
		treeTable.addContainerProperty("Version", String.class, "");
		treeTable.setPageLength(10);
		treeTable.setWidth("40em");
		treeTable.setSelectable(true);
		treeTable.setImmediate(true);
		treeTable.setMultiSelect(true);

		mainLayout.addComponent(treeTable);

		buttonUninstall = new Button("Uninstall Selected");
		buttonUninstall.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				boolean validate = validate();
				if (!validate) {

					Notification.show(errorMessage());

				} else {
					// TO implement
					Collection<Object> value = (Collection<Object>) treeTable
							.getValue();
					List<IInstallableUnit> repositories = getRepositories();
					List<IInstallableUnit> listToUninstall = new ArrayList<IInstallableUnit>();
					for (Object object : value) {

						for (IInstallableUnit iInstallableUnit : repositories) {

							if (object.equals(iInstallableUnit.getId())) {

								listToUninstall.add(iInstallableUnit);

							}
						}

					}

					String uninstallSelected = "OK";

					try {
						uninstallSelected = uninstallSoftwareService
								.uninstallSelected(listToUninstall);
					} catch (Exception em) {
						Notification.show(em.getMessage(), Type.WARNING_MESSAGE);
						em.printStackTrace();
						return;
					}

					if (uninstallSelected == null) {
						Notification.show("Software Uninstalled!",
								Type.HUMANIZED_MESSAGE);

					} else {
						Notification.show(uninstallSelected,
								Type.WARNING_MESSAGE);
					}
				}

			}
		});
		mainLayout.addComponent(buttonUninstall);

	}

	@Override
	public String errorMessage() {
		// TODO Auto-generated method stub
		return errorMessage;
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		Collection<Object> value = (Collection<Object>) treeTable.getValue();

		if (value.isEmpty()) {
			errorMessage = "You must select at least one";
			return false;
		}
		return true;
	}

}
