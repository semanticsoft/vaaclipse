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

package org.semanticsoft.vaaclipse.widgets.client.ui;

import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.RenderSpace;
import com.vaadin.terminal.gwt.client.UIDL;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

import fi.jasoft.dragdroplayouts.client.ui.VDDTabSheet;

/**
 * Client side widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VStackWidget extends VDDTabSheet implements Paintable {

	/** Set the CSS class name to allow styling. */
	public static final String CLASSNAME = "v-stackwidget";

	public static final String CLICK_EVENT_IDENTIFIER = "click";

	/** The client side widget identifier */
	protected String paintableId;

	/** Reference to the server connection object. */
	protected ApplicationConnection client;

	/**
	 * The constructor should first call super() to initialize the component and
	 * then handle any initialization relevant to Vaadin.
	 */
	public VStackWidget() {
		// This method call of the Paintable interface sets the component
		// style name in DOM tree
		//setStyleName(CLASSNAME);
	}

	/**
	 * Called whenever an update is received from the server
	 */
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client)
	{

		super.updateFromUIDL(uidl, client);

		// This call should be made first.
		// It handles sizes, captions, tooltips, etc. automatically.
		if (client.updateComponent(this, uidl, true))
		{
			// If client.updateComponent returns true there has been no changes
			// and we
			// do not need to update anything.
			return;
		}

		// Save reference to server connection object to be able to send
		// user interaction later
		this.client = client;

		// Save the client side identifier (paintable id) for the widget
		paintableId = uidl.getId();
	}
	
	@Override
	public RenderSpace getAllocatedSpace(Widget child)
	{
		updateRemoteBounds();
		return super.getAllocatedSpace(child);
	}

	void updateRemoteBounds()
	{
		if (client != null)
		{
			client.updateVariable(paintableId, "absolute_left", this.getAbsoluteLeft(), true);
			client.updateVariable(paintableId, "absolute_top", this.getAbsoluteTop(), true);
			client.updateVariable(paintableId, "offset_width", this.getOffsetWidth(), true);
			client.updateVariable(paintableId, "offset_height", this.getOffsetHeight(), true);
		}
	}
}
