/*******************************************************************************
 * Copyright (c) 2012 by committers of lunifera.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Information:
 * 		Based on original sources of 
 * 				- org.vaadin.osgi.Activator from Chris Brind
 *				- com.c4biz.osgiutils.vaadin.equinox.shiro.HttpServiceTracker from Cristiano Gaviao
 *
 * Contributors:
 *    Florian Pirchner - migrated to vaadin 7 and copied into org.lunifera namespace
 *    
 *******************************************************************************/
package org.semanticsoft.vaaclipse.app.webapp;

import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Map;

import org.eclipse.equinox.http.servlet.ExtendedHttpService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.component.ComponentFactory;
import org.osgi.util.tracker.ServiceTracker;
import org.semanticsoft.vaaclipse.app.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This tracker takes a {@link ComponentFactory} and then creates a
 * {@link VaadinWebApplicationRegister} class which is then registered as a
 * {@link ManagedService} to receive configuration for that specific
 * application.
 * 
 * @author brindy - initial contribution cvgaviao - using ExtendedHttpService
 *         that added support to filters
 */
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class HttpServiceTracker extends ServiceTracker {

	private static Logger logger = LoggerFactory
			.getLogger(HttpServiceTracker.class);

	private VaadinWebApplication webApplication;

	public String getAlias() {
		return webApplication.getAlias();
	}

	private Map<ExtendedHttpService, VaadinWebApplicationRegister> configs = new IdentityHashMap<ExtendedHttpService, VaadinWebApplicationRegister>();

	public HttpServiceTracker(BundleContext ctx,
			VaadinWebApplication webApplication) {
		super(ctx, ExtendedHttpService.class.getName(), null);
		this.webApplication = webApplication;
	}

	@Override
	public Object addingService(ServiceReference reference) {
		ExtendedHttpService http = (ExtendedHttpService) super
				.addingService(reference);

		// register the application
		VaadinWebApplicationRegister config = new VaadinWebApplicationRegister(
				http, webApplication);

		logger.debug("Application for alias \"" + getAlias()
				+ "\" was created.");

		// save it for later
		configs.put(http, config);

		// register as a managed service so that alternative properties can
		// be provided
		Hashtable<String, String> properties = new Hashtable<String, String>();
		properties.put(org.osgi.framework.Constants.SERVICE_PID,
				Constants.PROP_MANAGED_SERVICE_PREFIX + "." + getAlias());
		context.registerService(ManagedService.class.getName(), config,
				properties);

		try {
			config.updated(null);
		} catch (ConfigurationException e) {
			logger.warn("Initial setup caused exception: !" + e);
		}

		return http;
	}

	@Override
	public void removedService(ServiceReference reference, Object service) {
		configs.remove(service).kill();
		logger.debug("Application for alias \"" + getAlias()
				+ "\" was removed.");

		super.removedService(reference, service);
	}

}
