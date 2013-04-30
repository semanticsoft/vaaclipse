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
 *				- com.c4biz.osgiutils.vaadin.equinox.shiro.WebResourcesHttpContext from Cristiano Gaviao
 *
 * Contributors:
 *    Florian Pirchner - migrated to vaadin 7 and copied into org.lunifera namespace
 *    
 *******************************************************************************/
package org.semanticsoft.vaaclipse.app.servlet;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.service.http.HttpContext;

public class WebResourcesHttpContext implements HttpContext, BundleListener {

	private Set<Bundle> resourceBundles = new HashSet<Bundle>();
	private Bundle bundle;

	public WebResourcesHttpContext(Bundle bundle) {
		this.bundle = bundle;
		init(bundle.getBundleContext());
	}

	@Override
	public boolean handleSecurity(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		return true;
	}

	@Override
	public URL getResource(final String name) {
		URL resource = null;
		// iterate the server bundle, client bundle and fragments
		for (Bundle bundle : resourceBundles) {
			String uri = name.startsWith("/") ? name : "/" + name;
			String root = (String) bundle.getHeaders().get("Vaadin-Resources");
			if (root != null && !root.equals("") && !".".equals(root)) {
				uri = "/" + root + uri;
			}
			if (null != (resource = bundle.getResource(uri))) {
				break;
			}
		}
		return resource;
	}

	@Override
	public String getMimeType(final String name) {
		URL resource = getResource(name);
		if (null != resource) {
			try {
				return resource.openConnection().getContentType();
			} catch (final IOException e) {
				return null;
			}
		}
		return null;
	}

	private void checkBundleForResources(Bundle bundle) {
		if (isClientBundle(bundle) || isServerBundle(bundle)
				|| isThemesBundle(bundle)) {
			resourceBundles.add(bundle);
		} else if (null != bundle.getHeaders().get("Vaadin-Resources")) {
			resourceBundles.add(bundle);
		} else {
			resourceBundles.remove(bundle);
		}
	}

	/**
	 * Returns true if the bundle is the vaadin server bundle.
	 * 
	 * @param bundle
	 * @return
	 */
	private boolean isServerBundle(Bundle bundle) {
		return bundle.getSymbolicName().equals("com.vaadin.server");
	}

	/**
	 * Returns true, if the bundle is the vaadin client bundle.
	 * 
	 * @param bundle
	 * @return
	 */
	private boolean isClientBundle(Bundle bundle) {
		return bundle.getSymbolicName().equals("com.vaadin.client")
				|| bundle.getSymbolicName()
						.equals("com.vaadin.client-compiled");
	}

	/**
	 * Returns true, if the bundle is the vaadin themes bundle.
	 * 
	 * @param bundle
	 * @return
	 */
	private boolean isThemesBundle(Bundle bundle) {
		return bundle.getSymbolicName().equals("com.vaadin.themes");
	}

	@Override
	public void bundleChanged(BundleEvent event) {
		if (event.getBundle() == this.bundle) {
			if (event.getType() == BundleEvent.STOPPED) {
				this.bundle.getBundleContext().removeBundleListener(this);
				return;
			}
		}

		if (event.getType() == BundleEvent.UNINSTALLED) {
			resourceBundles.remove(event.getBundle());
		} else {
			checkBundleForResources(event.getBundle());
		}
	}

	public void init(BundleContext ctx) {
		ctx.addBundleListener(this);
		for (Bundle bundle : ctx.getBundles()) {
			checkBundleForResources(bundle);
		}
	}
}
