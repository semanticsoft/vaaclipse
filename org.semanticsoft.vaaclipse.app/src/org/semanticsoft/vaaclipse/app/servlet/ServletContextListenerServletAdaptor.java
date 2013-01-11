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
 *				- com.c4biz.osgiutils.vaadin.equinox.shiro.ServletContextListenerServletAdaptor from Cristiano Gaviao
 *
 * Contributors:
 *    Florian Pirchner - migrated to vaadin 7 and copied into org.lunifera namespace
 *    
 *******************************************************************************/
package org.semanticsoft.vaaclipse.app.servlet;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ServletContextListenerServletAdaptor implements Servlet {
	private ServletConfig config;
	private ServletContextListener listener;
	private Servlet delegate;

	public ServletContextListenerServletAdaptor(
			ServletContextListener listener, Servlet delegate) {
		this.listener = listener;
		this.delegate = delegate;
	}

	public void init(ServletConfig config) throws ServletException {
		this.config = config;
		listener.contextInitialized(new ServletContextEvent(config
				.getServletContext()));
		delegate.init(config);
	}

	public void service(ServletRequest req, ServletResponse resp)
			throws ServletException, IOException {
		delegate.service(req, resp);
	}

	public void destroy() {
		delegate.destroy();
		listener.contextDestroyed(new ServletContextEvent(config
				.getServletContext()));
		config = null;
	}

	public ServletConfig getServletConfig() {
		return config;
	}

	public String getServletInfo() {
		return "";
	}
}
