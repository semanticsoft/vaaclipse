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

package org.semanticsoft.vaaclipsedemo.cassandra.app.views;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.vaadin.data.util.ObjectProperty;

import com.vaadin.ui.Label;

import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;

/**
 * @author rushan
 *
 */
public class Console
{
	private Panel panel;
	private IEventBroker eventBroker;
	private Label text = new Label();
	private StringBuffer content = new StringBuffer();
	private static final String ls = System.getProperty("line.separator");

	@Inject
	public void Console(VerticalLayout parent, IEclipseContext context)
	{
		eventBroker = context.get(IEventBroker.class);
		
		panel = new Panel();
		panel.setSizeFull();
		panel.setScrollable(true);
		parent.addComponent(panel);
		
		text.setContentMode(Label.CONTENT_PREFORMATTED);
		panel.addComponent(text);
		
		println("Cassandra demo for vaaclipse - the vaadin renderer for e4");
		println("You can find information about vaaclipse here: http://semanticsoft.github.com/vaaclipse/");
		println("Java version: " + System.getProperty("java.version"));
		
		Bundle vaadinBundle = Platform.getBundle("com.vaadin");
		println("Vaadin version: " + vaadinBundle.getVersion().toString());
		
		println("Application bundles:");
		
		Bundle appBundle = Platform.getBundle("org.semanticsoft.vaaclipsedemo.cassandra.app");
		println(appBundle.getSymbolicName() + ", " + "version:" + appBundle.getVersion().toString());
		
//		Bundle resourcesBundle = Platform.getBundle("org.semanticsoft.vaaclipsedemo.cassandra.resources");
//		println(resourcesBundle.getSymbolicName() + ", " + "version:" + resourcesBundle.getVersion().toString());
	}
	
	public void print(String msg)
	{
		content.append(msg);
		text.setPropertyDataSource(new ObjectProperty<String>(content, String.class));
	}
	
	public void println(String msg)
	{
		content.append(msg);
		content.append(ls);
		text.setPropertyDataSource(new ObjectProperty<String>(content, String.class));
	}
}
