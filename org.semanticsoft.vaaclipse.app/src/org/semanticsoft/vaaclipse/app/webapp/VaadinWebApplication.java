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
package org.semanticsoft.vaaclipse.app.webapp;

import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.semanticsoft.vaaclipse.app.common.Constants;
import org.semanticsoft.vaaclipse.app.common.OSGiUIProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VaadinWebApplication {

	private static Logger logger = LoggerFactory
			.getLogger(VaadinWebApplication.class);

	private BundleContext bundleContext;

	private final String id;
	private String alias;
	private int port;
	private String themeId;
	private String widgetsetName;
	private String headerIcon;
	private boolean productionMode;

	private HttpServiceTracker tracker;

	public VaadinWebApplication(Bundle bundle) {
		this.bundleContext = bundle.getBundleContext();
		id = bundle.getSymbolicName();
	}

	public void activate() {
		tracker = new HttpServiceTracker(bundleContext, this);
		logger.debug("The alias that will be tracked is:\"" + alias);
		tracker.open();
	}

	public void deactivate() {
		logger.debug("Tracker for alias" + tracker.getAlias() + " was removed.");
		if (tracker != null) {
			tracker.close();
			tracker = null;
		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias
	 *            the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the widgetsetName
	 */
	public String getWidgetsetName() {
		return widgetsetName;
	}

	/**
	 * @param widgetsetName
	 *            the widgetsetName to set
	 */
	public void setWidgetsetName(String widgetsetName) {
		this.widgetsetName = widgetsetName;
	}

	/**
	 * @return the productionMode
	 */
	public boolean isProductionMode() {
		return productionMode;
	}

	/**
	 * @param productionMode
	 *            the productionMode to set
	 */
	public void setProductionMode(boolean productionMode) {
		this.productionMode = productionMode;
	}

	/**
	 * @return the headerIcon
	 */
	public String getHeaderIcon() {
		return headerIcon;
	}

	/**
	 * @param headerIcon
	 *            the headerIcon to set
	 */
	public void setHeaderIcon(String headerIcon) {
		this.headerIcon = headerIcon;
	}

	/**
	 * @return the themeId
	 */
	public String getThemeId() {
		return themeId;
	}

	/**
	 * @param themeId
	 *            the themeId to set
	 */
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	@SuppressWarnings("rawtypes")
	public void updated(Dictionary properties) {
		if (properties.get(Constants.PROP_WIDGETSET) != null) {
			widgetsetName = (String) properties.get(Constants.PROP_WIDGETSET);
		}
	}

	public OSGiUIProvider getUiProvider() {
		return new OSGiUIProvider();
	}

}
