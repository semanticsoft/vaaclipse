/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.semanticsoft.vaaclipse.app;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.eclipse.e4.core.contexts.IEclipseContext;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;
import com.vaadin.terminal.gwt.server.CommunicationManager;
import com.vaadin.terminal.gwt.server.WebApplicationContext;

/**
 * Used to create instances of applications that have been registered with the
 * container via a component factory.
 * 
 * @author brindy
 */
@SuppressWarnings("serial")
public class VaadinOSGiServlet extends AbstractApplicationServlet {

	private Set<VaadinSession> sessions = new HashSet<VaadinSession>();
	private VaadinOSGiCommunicationManager communicationManager;
	
	public VaadinOSGiServlet() {
		
	}
	
	@Override
	@Deprecated
	public CommunicationManager createCommunicationManager(Application application)
	{
		communicationManager = new VaadinOSGiCommunicationManager(application);
		return communicationManager;
	}
	
	public VaadinOSGiCommunicationManager getCommunicationManager()
	{
		return communicationManager;
	}

	@Override
	protected Class<? extends Application> getApplicationClass()
			throws ClassNotFoundException {
		// not used as the component factory creates instances for us
		// but has to return something or getSystemMessages causes a NPE
		return Application.class;
	}

	@Override
	protected Application getNewApplication(HttpServletRequest request)
			throws ServletException {
		final VaadinApplication vaadinapp = new VaadinApplication(this);
		final VaadinSession info = new VaadinSession(vaadinapp,
				request.getSession());

		info.session.setAttribute(VaadinOSGiServlet.class.getName(),
				new HttpSessionListener() {

					@Override
					public void sessionDestroyed(HttpSessionEvent arg0) {
						info.dispose();
					}

					@Override
					public void sessionCreated(HttpSessionEvent arg0) {

					}

				});
		System.out.println("Ready: " + info);
		return vaadinapp;
	}

	@Override
	public void destroy() {
		super.destroy();

		HashSet<VaadinSession> sessions = new HashSet<VaadinSession>();
		sessions.addAll(this.sessions);
		this.sessions.clear();
		for (VaadinSession info : sessions) {
			info.dispose();
		}
	}

	/**
	 * Track the component instance and session. If this is disposed the entire
	 * associated http session is also disposed.
	 */
	class VaadinSession {

		Application app;

		final HttpSession session;

		public VaadinSession(Application app, HttpSession session) {
			this.app = app;
			this.session = session;
			sessions.add(this);
		}

		public void dispose() {
			System.out.println("Disposing: " + this);
			if (app != null) {
				app.close();
			}

			session.removeAttribute(VaadinOSGiServlet.class.getName());
			session.removeAttribute(WebApplicationContext.class.getName());

			sessions.remove(this);
		}

	}

}
