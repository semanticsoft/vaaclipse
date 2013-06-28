/*******************************************************************************
 * Copyright (c) 2012 by committers of lunifera.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Florian Pirchner - initial API and implementation
 *    
 *******************************************************************************/
package org.semanticsoft.vaaclipse.app.servlet;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.semanticsoft.vaaclipse.api.VaadinExecutorService;

import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.RequestHandler;
import com.vaadin.server.ServiceException;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.communication.UidlRequestHandler;

@SuppressWarnings("serial")
public class OSGiServletService extends VaadinServletService {

	private final IVaadinSessionFactory factory;
	private VaaclipseServerRpcHandler vaaclipseServerRpcHandler;
	
	public VaaclipseServerRpcHandler getVaaclipseServerRpcHandler() 
	{
		return vaaclipseServerRpcHandler;
	}
	
	public VaadinExecutorService getExecutorService()
	{
		return vaaclipseServerRpcHandler.getExecutorService();
	}
	
	public OSGiServletService(VaadinServlet servlet,
			DeploymentConfiguration deploymentConfiguration,
			IVaadinSessionFactory factory) throws ServiceException {
		super(servlet, deploymentConfiguration);

		this.factory = factory;
	}

	@Override
	public String getConfiguredWidgetset(VaadinRequest request) {
		return super.getConfiguredWidgetset(request);
	}

	@Override
	protected VaadinSession createVaadinSession(VaadinRequest request)
			throws ServiceException {
		return factory.createSession(request, getCurrentServletRequest());
	}
	
	@Override
	protected List<RequestHandler> createRequestHandlers()
			throws ServiceException {
		List<RequestHandler> handlers = super.createRequestHandlers();
		for (RequestHandler h : handlers)
		{
			if (h instanceof UidlRequestHandler)
			{
				Field rpcField = null;
				try {
					rpcField = h.getClass().getDeclaredField("rpcHandler");
					rpcField.setAccessible(true);
					vaaclipseServerRpcHandler = new VaaclipseServerRpcHandler();
					rpcField.set(h, vaaclipseServerRpcHandler);
				} catch (Exception e) {
					e.printStackTrace();
				}
				finally {
					if (rpcField != null)
						rpcField.setAccessible(false);
				}
				
				break;
			}
		}
		return handlers;
	}

	/**
	 * Creates new instances of vaadin sessions.
	 */
	public interface IVaadinSessionFactory {
		/**
		 * Returns a new instance of a vaadin session.
		 * 
		 * @param request
		 * @param httpServletRequest
		 * 
		 * @return
		 */
		VaadinSession createSession(VaadinRequest request,
				HttpServletRequest httpServletRequest);
	}
}
