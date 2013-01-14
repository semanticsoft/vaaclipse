///*******************************************************************************
// * Copyright (c) 2012 by committers of lunifera.org
// *
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the Eclipse Public License v1.0
// * which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v10.html
// * 
// * Information:
// * 		Based on original sources of 
// * 				- org.vaadin.osgi.Activator from Chris Brind
// *				- com.c4biz.osgiutils.vaadin.equinox.shiro.ApplicationRegister from Cristiano Gaviao
// *
// * Contributors:
// *    Florian Pirchner - migrated to vaadin 7 and copied into org.lunifera namespace
// *    
// *******************************************************************************/
//package org.semanticsoft.vaaclipse.app.webapp;
//
//import java.util.Dictionary;
//import java.util.Hashtable;
//
//import javax.servlet.Filter;
//
//import org.eclipse.equinox.http.servlet.ExtendedHttpService;
//import org.osgi.service.cm.ConfigurationException;
//import org.osgi.service.cm.ManagedService;
//import org.osgi.service.component.ComponentFactory;
//import org.osgi.service.http.HttpContext;
//import org.semanticsoft.vaaclipse.app.Activator;
//import org.semanticsoft.vaaclipse.app.common.Constants;
//import org.semanticsoft.vaaclipse.app.servlet.VaadinOSGiServlet;
//import org.semanticsoft.vaaclipse.app.servlet.WebResourcesHttpContext;
//
//import com.vaadin.server.VaadinServlet;
//import com.vaadin.server.VaadinSession;
//
///**
// * This class is responsible for registering the {@link ComponentFactory} as a
// * vaadin {@link VaadinSession}. It is a {@link ManagedService} so that it can
// * receive properties which are then passed in to the {@link VaadinOSGiServlet}
// * as init parameters, e.g. to enable production mode.
// */
//@SuppressWarnings("deprecation")
//public class VaadinWebApplicationRegister implements ManagedService {
//
//	private final ExtendedHttpService http;
//
//	private VaadinServlet servlet;
//
//	private Filter filter;
//
//	private final String RESOURCE_BASE = "/VAADIN";
//	private VaadinWebApplication webApplication;
//
//	private String currentAlias;
//
//	public VaadinWebApplicationRegister(ExtendedHttpService http,
//			VaadinWebApplication webApplication) {
//		super();
//		this.http = http;
//		this.webApplication = webApplication;
//	}
//
//	public void kill() {
//		if (filter != null) {
//			http.unregisterFilter(filter);
//			filter = null;
//		}
//		if (currentAlias != null) {
//			try {
//				http.unregister(currentAlias);
//				http.unregister(RESOURCE_BASE);
//			} catch (java.lang.IllegalArgumentException e) {
//				// ignore in case alias was not found exception
//			}
//		}
//		if (servlet != null) {
//			servlet.destroy();
//			servlet = null;
//		}
//	}
//
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@Override
//	public void updated(Dictionary properties) throws ConfigurationException {
//		kill();
//
//		try {
//			if (properties == null) {
//				properties = new Hashtable();
//			}
//
//			// update the webapplication
//			//
//			webApplication.updated(properties);
//
//			// update the properties
//			//
//			properties.put(Constants.PROP_WIDGETSET,
//					webApplication.getWidgetsetName());
//			properties.put(Constants.PROP_PRODUCTION_MODE,
//					Boolean.toString(webApplication.isProductionMode()));
//
//			servlet = new VaadinOSGiServlet(webApplication);
//			HttpContext defaultContext = new WebResourcesHttpContext(Activator
//					.getDefault().getBundle());
//			http.registerResources(RESOURCE_BASE, RESOURCE_BASE, defaultContext);
//			currentAlias = webApplication.getAlias();
//			http.registerServlet(String.format("/%s", currentAlias), servlet,
//					properties, defaultContext);
//
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//}
