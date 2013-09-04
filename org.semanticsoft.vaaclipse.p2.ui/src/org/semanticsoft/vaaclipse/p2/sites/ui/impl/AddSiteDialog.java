package org.semanticsoft.vaaclipse.p2.sites.ui.impl;

import java.io.OutputStream;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.semanticsoft.vaaclipse.p2.iservice.ISitesManager;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AddSiteDialog extends Window {

	private Label erroLabel;
	private TextField textField;
	private Upload uploadSites;
	ISitesManager sitesManager;
	IProvisioningAgent agent;
	private boolean installed = false;
	private TextField textRepoName;
	private String actionButtonLabel;
	boolean edit = false;

	public AddSiteDialog(ISitesManager sitesManager, IProvisioningAgent agent) {
		// TODO Auto-generated constructor stub

		this.sitesManager = sitesManager;
		this.agent = agent;
		edit = false;
		actionButtonLabel = "Add";
		initUI();

	}

	public AddSiteDialog(ISitesManager sitesManager, IProvisioningAgent agent,
			String uri, String nickName) {
		// TODO Auto-generated constructor stub

		this.sitesManager = sitesManager;
		this.agent = agent;

		edit = true;
		actionButtonLabel = "Modify";
		initUI();

		textField.setValue(uri);
		textRepoName.setValue(nickName);

	}

	private void initUI() {
		VerticalLayout content = new VerticalLayout();

		this.setContent(content);

		Receiver receiver = new Receiver() {

			@Override
			public OutputStream receiveUpload(String filename, String mimeType) {
				// TODO Auto-generated method stub
				return null;
			}

		};

		erroLabel = new Label();
		erroLabel.setImmediate(true);

		content.addComponent(erroLabel);
		textRepoName = new TextField("Name ");
		content.addComponent(textRepoName);
		textField = new TextField("Enter URL here");
		content.addComponent(textField);
		uploadSites = new Upload("Upload here", receiver);

		content.addComponent(uploadSites);
		Button buttonAction = new Button(actionButtonLabel);
		content.addComponent(buttonAction);
		buttonAction.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				if (edit) {
					IStatus validate = null;
					String uri = null;
					setInstalled(false);
					try {
						uri = textField.getValue();
						validate = sitesManager.validate(agent, uri);

					} catch (RuntimeException ex) {
						ex.printStackTrace();
						erroLabel.setValue(ex.getMessage());
						return;
					}

					erroLabel.setValue("");

					String message = validate.getMessage();

					if (!validate.isOK()) {
						erroLabel.setValue(message);
						return;
					}

					sitesManager.removeRepository(uri, agent);
					try {
						if (textRepoName.getValue() == null
								|| textRepoName.getValue().isEmpty())
							sitesManager.addRepository(uri, agent);
						else {
							sitesManager.addRepository(uri, agent,
									textRepoName.getValue());
						}
					} catch (RuntimeException e) {

						erroLabel.setValue(e.getMessage());

						return;
					}
					Notification.show("Repository Modified");
					setInstalled(true);
					AddSiteDialog.this.close();

				} else {
					IStatus validate = null;
					String uri = null;
					setInstalled(false);
					try {
						uri = textField.getValue();
						validate = sitesManager.validate(agent, uri);

					} catch (RuntimeException ex) {
						ex.printStackTrace();
						erroLabel.setValue(ex.getMessage());
						return;
					}

					erroLabel.setValue("");

					String message = validate.getMessage();

					if (!validate.isOK()) {
						erroLabel.setValue(message);
						return;
					}

					try {
						if (textRepoName.getValue() == null
								|| textRepoName.getValue().isEmpty())
							sitesManager.addRepository(uri, agent);
						else {
							sitesManager.addRepository(uri, agent,
									textRepoName.getValue());
						}
					} catch (RuntimeException e) {

						erroLabel.setValue(e.getMessage());

						return;
					}

					Notification.show("Repository added");
					setInstalled(true);
					AddSiteDialog.this.close();

				}
			}
		});
		this.setModal(true);
		this.center();

	}

	public boolean isInstalled() {
		return installed;
	}

	private void setInstalled(boolean installed) {
		this.installed = installed;
	}
}
