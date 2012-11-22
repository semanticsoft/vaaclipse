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
package org.vaadin.osgi.staticres;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;
import org.semanticsoft.vaaclipse.api.ResourceInfoProvider;
import org.semanticsoft.vaaclipse.util.Utils;

/**
 * This class runs as an OSGi component and serves the themes and widgetsets
 * directly from the core Vaadin bundle.
 * <p/>
 * 
 * To add your own theme or widget set create a fragment which contains your
 * theme/widgetset files and export those as packages. The
 * <code>Fragment-Host</code> should be set to the Vaadin core bundle. The
 * fragment containing your theme/widgetset resources will be added to the core
 * Vaadin bundle dynamically.
 * <p/>
 * 
 * Of course static resources should really be deployed separately to a web
 * server that proxies servlet requests on to the container.
 * 
 * @author brindy
 */
@SuppressWarnings("serial")
public class StaticResources extends HttpServlet {

	private HttpService httpService;
	private ResourceInfoProvider resourceInfoProvider;

	private String alias;

	private Bundle vaadin;

	public void bind(HttpService httpService) {
		this.httpService = httpService;
	}
	
	public void bindResourceInfoProvider(ResourceInfoProvider provider)
	{
		this.resourceInfoProvider = provider;
	}

	public void start(BundleContext ctx, Map<String, String> properties)
			throws Exception {
		// find the vaadin bundle:
		for (Bundle bundle : ctx.getBundles()) {
			if ("com.vaadin".equals(bundle.getSymbolicName())) {
				vaadin = bundle;
				break;
			}
		}
		alias = properties.get("http.alias");
		httpService.registerServlet(alias, this, null, null);
	}

	public void stop() {
		httpService.unregister(alias);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if (this.resourceInfoProvider == null)
			resp.sendError(HttpServletResponse.SC_CONFLICT);
		
		String path = req.getPathInfo();
		
		if (path.endsWith("css"))
		{
			resp.setContentType("text/css");
		}
		
		String resourcePath = alias + path;
		
		String bundlePath = Utils.restorePath(resourcePath, resourceInfoProvider.getUserVaadinTheme(), resourceInfoProvider.getApplicationCSS(), resourceInfoProvider.getApplicationtWidgetset(), resourceInfoProvider.getApplicationtWidgetsetName());
		
		try {
			URL u = new URL(bundlePath);

			InputStream in = u.openStream();
			OutputStream out = resp.getOutputStream();

			byte[] buffer = new byte[1024];
			int read = 0;
			while (-1 != (read = in.read(buffer))) {
				out.write(buffer, 0, read);
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

}
