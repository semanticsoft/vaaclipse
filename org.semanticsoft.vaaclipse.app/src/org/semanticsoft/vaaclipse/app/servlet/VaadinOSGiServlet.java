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
 *				- org.vaadin.artur.icepush.ICEPushServlet from Arthur Signell
 *
 * Contributors:
 *    Florian Pirchner - implementation
 *    
 *******************************************************************************/
package org.semanticsoft.vaaclipse.app.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.icepush.servlet.MainServlet;
import org.semanticsoft.vaaclipse.app.common.OSGiUIProvider;
import org.vaadin.artur.icepush.ICEPush;
import org.vaadin.artur.icepush.JavascriptProvider;

import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;

/**
 * Used to create instances of applications that have been registered with the
 * container via a component factory.
 * 
 */
public class VaadinOSGiServlet extends VaadinServlet {

	private static final long serialVersionUID = 1L;

	private MainServlet mainservlet;
	private JavascriptProvider javascriptProvider;

	/**
	 * Default constructor.
	 * 
	 * @param webApplication
	 *            The vaadin web application.
	 */
	public VaadinOSGiServlet() {

	}

	@Override
	protected OSGiServletService createServletService(
			DeploymentConfiguration deploymentConfiguration)
			throws ServiceException {

		// create the servlet service initialized with the ui provider
		OSGiServletService service = new OSGiServletService(this,
				deploymentConfiguration,
				new OSGiServletService.IVaadinSessionFactory() {
					@Override
					public VaadinSession createSession(VaadinRequest request,
							HttpServletRequest httpServletRequest) {
						VaadinSession session = new VaadinSession(
								request.getService());
						session.addUIProvider(new OSGiUIProvider());
						return session;
					}
				});
		service.init();
		return service;
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		try {
			super.init(servletConfig);
		} catch (ServletException e) {
			if (e.getMessage().equals(
					"Application not specified in servlet parameters")) {
				// Ignore if application is not specified to allow the same
				// servlet to be used for only push in portals
			} else {
				throw e;
			}
		}
		mainservlet = new MainServlet(servletConfig.getServletContext());

		try {
			javascriptProvider = new JavascriptProvider(getServletContext()
					.getContextPath());

			ICEPush.setCodeJavascriptLocation(javascriptProvider
					.getCodeLocation());
		} catch (IOException e) {
			throw new ServletException("Error initializing JavascriptProvider",
					e);
		}
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if (pathInfo != null
				&& pathInfo.equals("/" + javascriptProvider.getCodeName())) {
			// Serve icepush.js
			serveIcePushCode(request, response);
			return;
		}

		if (request.getRequestURI().endsWith(".icepush")) {
			// Push request
			try {
				mainservlet.service(request, response);
			} catch (ServletException e) {
				throw e;
			} catch (IOException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			// Vaadin request
			super.service(request, response);
		}
	}

	private void serveIcePushCode(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String icepushJavscript = javascriptProvider.getJavaScript();

		response.setHeader("Content-Type", "text/javascript");
		response.getOutputStream().write(icepushJavscript.getBytes());
	}

	@Override
	public void destroy() {
		super.destroy();
		mainservlet.shutdown();
	}
}
