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

import com.vaadin.ui.themes.Reindeer;

import com.vaadin.shared.ui.label.ContentMode;

import org.apache.commons.io.IOUtils;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import java.io.FileInputStream;
import java.io.IOException;
import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.eclipse.e4.ui.services.internal.events.EventBroker;

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
	Panel panel;
	
	@Inject
	public TextEditor(VerticalLayout container, MInputPart inputPart)
	{
		super(inputPart.getInputURI());
		
		panel = new Panel();
		panel.addStyleName(Reindeer.PANEL_LIGHT);
		panel.addStyleName("viewpanel");
		panel.setSizeFull();
		text = new Label();
		panel.setContent(text);
		container.addComponent(panel);
		
		setupText();
	}
	
	protected void setupText()
	{
		text.setContentMode(ContentMode.PREFORMATTED);
		text.addStyleName("texteditor");
		text.setValue(readContent());
	}
	
	protected String readContent()
	{
		try
		{
			FileInputStream inputStream = new FileInputStream(getFile());
		    try {
		        content = IOUtils.toString(inputStream);
		    } finally {
		        inputStream.close();
		    }
		}
		catch (IOException e)
		{
			e.printStackTrace();
			content = e.getMessage();
		}
		return content;
	}
}
