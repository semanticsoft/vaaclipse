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

package org.semanticsoft.vaaclipse.app;

import java.util.concurrent.ArrayBlockingQueue;

import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.internal.workbench.WorkbenchLogger;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.osgi.service.datalocation.Location;
import org.semanticsoft.vaaclipse.api.ResourceInfoProvider;
import org.semanticsoft.vaaclipse.app.webapp.VaadinWebApplication;

@SuppressWarnings("restriction")
public class VaadinE4Application implements IApplication, ResourceInfoProvider {

	private ArrayBlockingQueue<String> queue;
	private Logger logger = new WorkbenchLogger(
			"org.semanticsoft.vaaclipse.app");

	private IApplicationContext appContext;

	private static VaadinE4Application instance;

	public static final String EXIT = "EXIT";

	private String appAuthProvider;
	private VaadinWebApplication webApplication;

	public static VaadinE4Application getInstance() {
		return instance;
	}

	@Override
	public String getCssTheme() {
		return webApplication.getThemeId();
	}

	@Override
	public String getApplicationtWidgetset() {
		return webApplication.getWidgetsetName();
	}

	@Override
	public String getApplicationtWidgetsetName() {
		return webApplication.getWidgetsetName();
	}

	@Override
	public String getApplicationHeaderIcon() {
		return webApplication.getWidgetsetName();
	}

	public String getApplicationAuthenticationProvider() {
		return appAuthProvider;
	}

	public Location getInstanceLocation() {
		return Activator.getDefault().getInstanceLocation();
	}

	public IApplicationContext getAppContext() {
		return appContext;
	}

	public Logger getLogger() {
		return logger;
	}

	@Override
	public Object start(IApplicationContext context) throws Exception {
		instance = this;
		appContext = context;

		registerServices();

		logger.debug("VaadinE4Application.start()");
		context.applicationRunning();

		queue = new ArrayBlockingQueue<>(10);

		startWebApplication();

		// showFrame();

		String msg;
		while (!(msg = queue.take()).equals(EXIT)) {
			logger.debug(msg);
		}

		return EXIT_OK;
	}

	private void registerServices() {
		Activator
				.getDefault()
				.getBundle()
				.getBundleContext()
				.registerService(ResourceInfoProvider.class.getName(), this,
						null);
	}

	private void startWebApplication() throws Exception {
		String port = System.getProperty("org.osgi.service.http.port");
		if (port == null) {
			port = "8080";
		}

		appAuthProvider = appContext
				.getBrandingProperty("applicationAuthenticationProvider");
		webApplication = new VaadinWebApplication(Activator.getDefault()
				.getBundle());
		webApplication.setAlias("/");
		webApplication.setPort(Integer.valueOf(port));
		webApplication.setWidgetsetName(appContext
				.getBrandingProperty("applicationWidgetset"));
		webApplication.setHeaderIcon(appContext
				.getBrandingProperty("applicationHeaderIcon"));
		webApplication.setThemeId(appContext.getBrandingProperty("cssTheme"));
		boolean productionMode = false;
		try {
			productionMode = Boolean
					.valueOf(appContext
							.getBrandingProperty("org.semanticsoft.vaaclipse.app.vaadin.production_mode"));
		} catch (Exception e) {
		}
		webApplication.setProductionMode(productionMode);

		// activate the application
		webApplication.activate();
	}

	@Override
	public void stop() {
		webApplication.deactivate();
	}

}
