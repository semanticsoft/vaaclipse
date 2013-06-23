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

import javax.servlet.http.HttpServletRequest;

import org.semanticsoft.vaaclipse.app.common.OSGiUIProvider;

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
}
