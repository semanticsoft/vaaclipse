package org.semanticsoft.vaaclipse.p2.install.ui.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.semanticsoft.vaaclipse.p2.install.ui.IRepositoryExplorer;
import org.semanticsoft.vaaclipse.p2.install.ui.IRepositoryLoader;
import org.semanticsoft.vaaclipse.p2.iservice.IInstallNewSoftwareService;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

/*******************************************************************************
 * Copyright (c) 2012 Klevis Ramo and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Klevis Ramo - initial API and implementation
 *******************************************************************************/
public class RepositoryLoader implements IRepositoryLoader {

	private CssLayout mainLayout = new CssLayout();
	private IRepositoryExplorer iRepositoryExplorer;

	private IInstallNewSoftwareService installService;
	List<IInstallableUnit> loadRepository = new ArrayList<>();

	private IProvisioningAgent provisioningAgent;
	String errorMessage;

	public RepositoryLoader(IRepositoryExplorer iRepositoryExplorer,
			IInstallNewSoftwareService installService,
			IProvisioningAgent provisioningAgent) {
		super();
		this.iRepositoryExplorer = iRepositoryExplorer;
		this.installService = installService;
		this.provisioningAgent = provisioningAgent;
		initUI();
	}

	boolean validate = true;

	public void initUI() {

		final Label errorLabel = new Label();
		errorLabel.setImmediate(true);
		Label label = new Label("Enter URI here");

		final TextField textField = new TextField();
		textField.setValue("http://localhost/MyUpdateSite");
		textField.setImmediate(true);

		mainLayout.addComponent(errorLabel);
		mainLayout.addComponent(label);
		mainLayout.addComponent(textField);

		textField.addShortcutListener(new ShortcutListener("Enter URI",
				ShortcutAction.KeyCode.ENTER, null) {

			@Override
			public void handleAction(Object sender, Object target) {
				// TODO Auto-generated method stub
				errorLabel.setValue("");
				validate = true;
				loadRepository.clear();
				String value = textField.getValue();

				if (value != null && !value.trim().isEmpty()) {
					try {
						loadRepository.addAll(getInstallService()
								.loadRepository(value, getProvisioningAgent()));

					} catch (Exception e) {
						validate = false;
						e.printStackTrace();

						errorLabel.setValue("URI is not valid");

						errorMessage = e.getMessage();
					}
					iRepositoryExplorer.addRepositories(loadRepository);

				}
			}

		});

	}

	@Override
	public IRepositoryExplorer getiRepositoryExplorer() {
		return iRepositoryExplorer;
	}

	@Override
	public void setiRepositoryExplorer(IRepositoryExplorer iRepositoryExplorer) {
		this.iRepositoryExplorer = iRepositoryExplorer;
	}

	@Override
	public Object getUIComponent() {
		// TODO Auto-generated method stub
		return mainLayout;
	}

	public IProvisioningAgent getProvisioningAgent() {
		return provisioningAgent;
	}

	@Override
	public void setProvisioningAgent(IProvisioningAgent provisioningAgent) {
		this.provisioningAgent = provisioningAgent;
	}

	public IInstallNewSoftwareService getInstallService() {
		return installService;
	}

	@Override
	public void setInstallService(IInstallNewSoftwareService installService) {
		this.installService = installService;
	}

	@Override
	public String errorMessage() {
		// TODO Auto-generated method stub
		return errorMessage;
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return validate;
	}

	@Override
	public void addRepositories(List<IInstallableUnit> list) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<IInstallableUnit> getRepositories() {
		// TODO Auto-generated method stub
		return null;
	}

}
