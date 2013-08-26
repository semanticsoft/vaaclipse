package org.semanticsoft.vaaclipse.p2.install.ui.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.semanticsoft.vaaclipse.p2.install.ui.IBasicUI;
import org.semanticsoft.vaaclipse.p2.install.ui.IContainerP2Views;
import org.semanticsoft.vaaclipse.p2.install.ui.ILicenseView;
import org.semanticsoft.vaaclipse.p2.iservice.IInstallNewSoftwareService;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/*******************************************************************************
 * Copyright (c) 2012 Klevis Ramo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Klevis Ramo - initial API and implementation
 *******************************************************************************/
public class ContainerP2Views implements IContainerP2Views {

	List<IBasicUI> listUI = new ArrayList<>();
	Button buttonNext = new Button("Next");
	Button buttonPrevies = new Button("Back");
	Button buttonInstall = new Button("Install");
	VerticalLayout mainLayout = new VerticalLayout();
	IBasicUI selectedBasicUI = null;
	IInstallNewSoftwareService installService;
	int maxViews = 2;

	public ContainerP2Views(
			IInstallNewSoftwareService installNewSoftwareService,
			IBasicUI... listUI) {
		super();
		this.installService = installNewSoftwareService;
		addViews(Arrays.asList(listUI));

	}

	@Override
	public Object getUIComponent() {
		// TODO Auto-generated method stub
		return mainLayout;
	}

	@Override
	public void initUI() {
		// TODO Auto-generated method stub

		mainLayout.removeAllComponents();
		mainLayout.addComponent((Component) listUI.get(0).getUIComponent());

		selectedBasicUI = listUI.get(0);
		final CssLayout cssLayout = new CssLayout();

		cssLayout.addComponent(buttonPrevies);
		cssLayout.addComponent(buttonNext);
		buttonNext.setEnabled(true);
		buttonInstall.setEnabled(false);
		buttonPrevies.setEnabled(false);

		cssLayout.addComponent(buttonInstall);

		buttonInstall.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				boolean validate = listUI.get(maxViews - 1).validate();

				if (validate) {
					installService.installNewSoftware(((ILicenseView) listUI
							.get(maxViews - 1)).getRepos());

					HasComponents parent = buttonNext.getParent();
					while (!(parent instanceof Window)) {

						parent = parent.getParent();
					}

					Window w = (Window) parent;
					w.close();
					Notification.show("Software installed");
				}

			}
		});

		Button.ClickListener listenerButton = new Button.ClickListener() {

			int index = 0;

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				if (event.getButton() == buttonNext) {

					boolean validate = listUI.get(index).validate();
					if (index < maxViews - 1 && validate) {
						mainLayout.removeAllComponents();
						index++;
						mainLayout.addComponent((Component) listUI.get(index)
								.getUIComponent());
						handleButtons();
					}
				} else if (event.getButton() == buttonPrevies) {

					if (index > -1) {

						if (index > 0)

							index--;
						mainLayout.addComponent((Component) listUI.get(index)
								.getUIComponent());
						handleButtons();
					}
				}

				mainLayout.addComponent(cssLayout);
			}

			private void handleButtons() {
				if (index == 0) {
					buttonPrevies.setEnabled(false);
					buttonInstall.setEnabled(false);
				} else if (index == maxViews - 1) {
					buttonNext.setEnabled(false);
					buttonInstall.setEnabled(true);

				}

				if (index > 0) {
					buttonPrevies.setEnabled(true);

				}
				if (index < maxViews - 1) {
					buttonNext.setEnabled(true);

				}
			}
		};
		buttonNext.addClickListener(listenerButton);
		buttonPrevies.addClickListener(listenerButton);
		mainLayout.addComponent(cssLayout);
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

	@Override
	public void addViews(List<IBasicUI> views) {
		// TODO Auto-generated method stub

		if (views == null) {
			throw new IllegalArgumentException("Views must not be null");

		}
		maxViews = views.size();
		listUI.addAll(views);
	}

}
