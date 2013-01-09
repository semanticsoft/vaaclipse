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
import java.net.MalformedURLException;
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
import org.semanticsoft.vaaclipse.publicapi.theme.Theme;
import org.semanticsoft.vaaclipse.publicapi.theme.ThemeConstants;
import org.semanticsoft.vaaclipse.publicapi.theme.ThemeContribution;
import org.semanticsoft.vaaclipse.publicapi.theme.ThemeEngine;
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
	private ThemeEngine themeEngine;

	private String alias;

	private Bundle vaadin;

	public void bind(HttpService httpService) {
		this.httpService = httpService;
	}
	
	public void bindResourceInfoProvider(ResourceInfoProvider provider)
	{
		this.resourceInfoProvider = provider;
	}
	
	public void bindThemeEngine(ThemeEngine themeEngine)
	{
		this.themeEngine = themeEngine;
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
		
		String themeId = (String) req.getSession().getAttribute(ThemeConstants.Attrubutes.themeid);
		InputStream in = getInputStream(resourcePath, 
				themeEngine.getTheme(themeId),
				resourceInfoProvider.getApplicationtWidgetset(), 
				resourceInfoProvider.getApplicationtWidgetsetName(), 
				resourceInfoProvider.getApplicationHeaderIcon());
		
		if (in == null)
		{
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		try {
			OutputStream out = resp.getOutputStream();

			byte[] buffer = new byte[1024];
			int read = 0;
			
			try {
				while (-1 != (read = in.read(buffer))) {
					out.write(buffer, 0, read);
				}	
			}
			finally {
				out.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		finally {
			in.close();
		}
	}
	
	public InputStream getInputStream(String url, Theme theme, String e4WidgetsetUri, String e4WidgetsetName, String headerIconUri)
	{
		String path = null;
		
		if (url.endsWith("favicon.ico"))
		{
			path = headerIconUri;
		}
		else
		{
			StringBuffer urlString = new StringBuffer(url);
			urlString.trimToSize();
			if (urlString.length() == 0)
				throw new IllegalArgumentException();
			if (urlString.charAt(0) != '/')
				throw new IllegalArgumentException();
			String processedUrl = urlString.substring(1);
			String[] segments = processedUrl.split("/");
			
			if (segments.length < 2)
				throw new IllegalArgumentException();
			
			if (!"VAADIN".equals(segments[0]))
				throw new IllegalArgumentException();
			
			if (segments[1].equals("widgetsets"))
			{
				//return "platform:/plugin/org.semanticsoft.vaaclipse.resources" + url;
				String widgetSetName = segments[2];
				if (e4WidgetsetName.equals(widgetSetName))
				{
					String restPath = buildSegments(segments, 3);
					path = e4WidgetsetUri + "/" + restPath;
				}
				else
				{
					path = "platform:/plugin/com.vaadin" + url;
				}
			}
			else if (segments[1].equals("themes"))
			{
				String themeName = segments[2];
				
				if (theme.getWebId().equals(themeName))
				{
					if ("styles.css".equals(segments[3]))
					{
						return theme.getCssAsStream();
					}
					else if ("original_styles.css".equals(segments[3]))
					{
						path = theme.getCssUri();
					}
					else
					{
						if ("plugin".equals(segments[3])) //this is absolute bundle path
						{
							String bundleName = segments[4];
							path = "platform:/plugin/" + bundleName + "/" + buildSegments(segments, 5);	
						}
						else
						{//this is relative theme path
							if (url.endsWith("css"))
							{
								String cssFileName = url.substring(url.lastIndexOf('/') + 1);
								cssFileName = cssFileName.substring(0, cssFileName.lastIndexOf('.'));
								ThemeContribution themeContribution = themeEngine.getThemeContributionByWebId(cssFileName);
								if (themeContribution != null)
									path = themeContribution.getCssUri();
								else
								{
									for (String cssUri : theme.getAllCssURIs())
									{
										String relPath = buildSegments(segments, 3);
										String cssPath = cssUri.substring(0, cssUri.lastIndexOf('/')) + "/" + relPath;
										try {
											URL cssUrl = new URL(cssPath);
											return cssUrl.openStream();	
										}
										catch (Exception e) {}
									}
								}
							}
							else
							{
								String relPath = buildSegments(segments, 3);
								return theme.getThemeResourceAsStream(relPath);
							}	
						}
					}
				}
				else //any theme
				{
					if ("styles.css".equals(segments[3]))
					{
						Theme inheritedTheme = themeEngine.getTheme(themeName);
						path = inheritedTheme.getCssUri();
					}
					else
						path = "platform:/plugin/com.vaadin" + url;
				}
			}
			else
				throw new IllegalArgumentException("хз что такое");	
		}
		
		try {
			URL u = new URL(path);
			return u.openStream();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String buildSegments(String[] segments, int start)
	{
		StringBuffer b = new StringBuffer();
		for (int i = start; i < segments.length; i++)
		{
			b.append(segments[i]);
			b.append('/');
		}
		if (b.length() > 0)
			return b.substring(0,  b.length() - 1);
		else
			return "";
	}
}
