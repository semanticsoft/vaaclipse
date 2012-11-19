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

package org.semanticsoft.vaaclipse.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.emf.common.util.URI;

public class Utils {
	public static String convertPath(String uriString) {
		URI uri = URI.createURI(uriString);
		
		String bundleName = uri.segment(1);
		
		if ("com.vaadin".equals(bundleName) || "org.semanticsoft.vaaclipse.resources".equals(bundleName))
		{
			if (uri.segmentCount() < 5 || !"VAADIN".equals(uri.segment(2)) || !"themes".equals(uri.segment(3)))
				throw new IllegalArgumentException("Illegal path " + uriString + ". Path must start with VAADIN/themes/<theme_name>");
			
			StringBuilder bundlePath = new StringBuilder();
			for (int i = 5; i < uri.segmentCount(); i++) {
				if (bundlePath.length() != 0) {
					bundlePath.append("/");
				}
				bundlePath.append(uri.segment(i));
			}
			
			return "../" + uri.segment(4) + "/" + bundlePath.toString();
		}
		else
		{
			StringBuilder bundlePath = new StringBuilder();
			for (int i = 1; i < uri.segmentCount(); i++) {
				if (bundlePath.length() != 0) {
					bundlePath.append("/");
				}
				bundlePath.append(uri.segment(i));
			}
			
			return bundlePath.toString();
		}
		
//		if (uri.segmentCount() < 5 || !"VAADIN".equals(uri.segment(2)) || !"themes".equals(uri.segment(3)))
//			throw new IllegalArgumentException("Illegal path " + uriString + ". Path must start with VAADIN/themes/<theme_name>");
		
//		StringBuilder bundlePath = new StringBuilder();
//		for (int i = 5; i < uri.segmentCount(); i++) {
//			if (bundlePath.length() != 0) {
//				bundlePath.append("/");
//			}
//			bundlePath.append(uri.segment(i));
//		}
//		
//		return "../" + uri.segment(4) + "/" + bundlePath.toString();
		
		
	}
	
	public static String restorePath(String url, String theme, String e4CssUri)
	{
		//platform:/plugin/org.semanticsoft.vaaclipsedemo.mediaplayer.resources/VAADIN/themes/mediaplayer/img/mediainfo.png
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
			return "platform:/plugin/com.vaadin" + url;
		}
		else if (segments[1].equals("themes"))
		{
			String themeName = segments[2];
			
			if ("vaaclipse_default_theme".equals(themeName))
			{
				//return "platform:/plugin/org.semanticsoft.vaaclipse.resources" + url;
				return "platform:/plugin/com.vaadin" + url;
			}
			else if (theme.equals(themeName))
			{
				String themeEntry = segments[3];
				if ("styles.css".equals(themeEntry))
				{
					return e4CssUri;
				}
				else
				{
					if (url.endsWith("css"))
					{
						//calculate relative to e4CssUri path
//						String base = "/" + buildSegments(Arrays.copyOfRange(segments, 0, 3), 0);
//						URI baseUri = URI.createURI(base);
//						URI uri = URI.createURI(url);
//						URI relative = uri.deresolve(baseUri);
//						URI e4Css = URI.createURI(e4CssUri);
//						URI result = relative.resolve(e4Css);						
//						return result.toPlatformString(false);
						
						String relPath = buildSegments(segments, 3);
						return e4CssUri.substring(0, e4CssUri.lastIndexOf("/")) + "/" + relPath;
					}
					else
					{//this is theme name
						String bundleName = segments[3];
						return "platform:/plugin/" + bundleName + "/" + buildSegments(segments, 4);
					}
				}
			}
			else //any theme
			{
				return "platform:/plugin/com.vaadin" + url;
			}
		}
		else
			throw new IllegalArgumentException("хз что такое");
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
