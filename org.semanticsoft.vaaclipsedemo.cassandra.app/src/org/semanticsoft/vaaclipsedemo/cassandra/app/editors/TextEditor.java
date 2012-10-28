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

package org.semanticsoft.vaaclipsedemo.cassandra.app.editors;

import org.eclipse.e4.core.contexts.IEclipseContext;

import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import com.vaadin.Application;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import org.eclipse.e4.ui.workbench.UIEvents;

import org.eclipse.e4.ui.services.internal.events.EventBroker;

import javax.annotation.PostConstruct;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import java.io.IOException;
import javax.inject.Inject;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;

/**
 * @author rushan
 *
 */
public class TextEditor extends FileView
{
	protected Label text;
	protected String content;
	@Inject
	EventBroker eventBroker;
	@Inject
	IEclipseContext context;
	
	@Inject
	public TextEditor(VerticalLayout container, MInputPart inputPart)
	{
		super(inputPart.getInputURI());
		
		Panel e = new Panel();
		e.setSizeFull();
		((VerticalLayout)e.getContent()).setMargin(true);
		//e.setMargin(true);
		e.setScrollable(true);
		text = new Label(readContent());
		e.addComponent(text);
		container.addComponent(e);
	}

	protected TextEditor(String inputURI)
	{
		super(inputURI);
	}

	protected String readContent()
	{
		try
		{
			content = FileUtils.readFile(getFile(), "UTF-8");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			content = e.getMessage();
		}
		return content;
	}
}
