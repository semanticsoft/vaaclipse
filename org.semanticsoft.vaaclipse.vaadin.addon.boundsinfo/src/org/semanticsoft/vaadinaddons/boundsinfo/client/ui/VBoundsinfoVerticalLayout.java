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

package org.semanticsoft.vaadinaddons.boundsinfo.client.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.Paintable;
import com.vaadin.client.RenderSpace;
import com.vaadin.client.UIDL;
import com.vaadin.client.ValueMap;
import com.vaadin.client.ui.VVerticalLayout;

/**
 * Client side widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VBoundsinfoVerticalLayout extends VVerticalLayout implements Paintable
{

	/** Set the CSS class name to allow styling. */
	public static final String CLASSNAME = "v-boundsinfoverticallayout";

	public static final String CLICK_EVENT_IDENTIFIER = "click";
	
	public static final String VARIABLES = "_variables_";
	public static final String ENABLE_BOUNDS_UPDATE = "enable_bounds_update";

	/** The client side widget identifier */
	protected String paintableId;
	
	private BoundsUpdateManager updateManager;
	
	private Map<String, String> variables = new HashMap<String, String>();
	private Boolean enableBoundsUpdate = false;

	/** Reference to the server connection object. */
	// protected ApplicationConnection client;

	/**
	 * The constructor should first call super() to initialize the component and
	 * then handle any initialization relevant to Vaadin.
	 */
	public VBoundsinfoVerticalLayout()
	{
		// This method call of the Paintable interface sets the component
		// style name in DOM tree
		// setStyleName(CLASSNAME);
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
		
		if(uidl.hasAttribute(VARIABLES)){	
    		ValueMap vmap = uidl.getMapAttribute(VARIABLES);
    		Set<String> indexes = vmap.getKeySet();
    		for(String index : indexes){
    			variables.put(index, vmap.getString(index));
    		}
    	}
		
		this.enableBoundsUpdate = uidl.getBooleanAttribute(ENABLE_BOUNDS_UPDATE);
		if (this.enableBoundsUpdate)
			updateManager = new BoundsUpdateManager(this, paintableId, client);
	}

	@Override
	public RenderSpace getAllocatedSpace(Widget child)
	{
		if (updateManager != null)
			updateManager.update();
		return super.getAllocatedSpace(child);
	}
	
	public String getVariableValue(String varName)
	{
		return this.variables.get(varName);
	}
	
	public void setVariableValue(String varName, String varValue)
	{
		this.variables.put(varName, varValue);
	}
}
