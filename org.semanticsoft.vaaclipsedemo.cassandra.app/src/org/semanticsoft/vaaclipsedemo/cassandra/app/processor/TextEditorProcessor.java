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

package org.semanticsoft.vaaclipsedemo.cassandra.app.processor;

import org.semanticsoft.vaaclipsedemo.cassandra.app.editors.TextEditor;

import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

/**
 * @author rushan
 * 
 */
public class TextEditorProcessor
{
	@Inject
	IEclipseContext context;
	@Inject
	EventBroker eventBroker;
	
	private EventHandler activateHandler = new EventHandler() {

		public void handleEvent(Event event)
		{
			MPart part = (MPart) event.getProperty(UIEvents.EventTags.ELEMENT);
			if (part instanceof MInputPart)
			{
				if (part.getObject() instanceof TextEditor)
					context.set("editortype", "texteditor");
				else
					context.set("editortype", "editor");
			}
			else
				context.set("editortype", "");
		}
	};
	
	@Execute
	public void execute()
	{
		eventBroker.subscribe(UIEvents.UILifeCycle.ACTIVATE, activateHandler);
	}
}
