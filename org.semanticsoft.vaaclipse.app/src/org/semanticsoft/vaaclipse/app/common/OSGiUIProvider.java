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
package org.semanticsoft.vaaclipse.app.common;

import org.semanticsoft.vaaclipse.app.VaadinE4Application;
import org.semanticsoft.vaaclipse.app.webapp.VaadinUI;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class OSGiUIProvider extends UIProvider {

	public OSGiUIProvider() {
		super();
	}

	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
		return VaadinUI.class;
	}

	@Override
	public UI createInstance(UICreateEvent event) {
		return new VaadinUI();
	}
	
	@Override
	public String getTheme(UICreateEvent event) {
		return VaadinE4Application.getInstance().getCssTheme();
	}
}
