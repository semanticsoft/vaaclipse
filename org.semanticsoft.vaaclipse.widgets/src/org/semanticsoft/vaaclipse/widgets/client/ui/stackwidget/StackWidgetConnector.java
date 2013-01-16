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

package org.semanticsoft.vaaclipse.widgets.client.ui.stackwidget;

import org.semanticsoft.vaaclipse.widgets.StackWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.UIDL;
import com.vaadin.client.VConsole;
import com.vaadin.shared.ui.Connect;

import fi.jasoft.dragdroplayouts.client.ui.tabsheet.DDTabsheetConnector;

/**
 * @author rushan
 *
 */
@Connect(StackWidget.class)
public class StackWidgetConnector extends DDTabsheetConnector
{
	@Override
	protected Widget createWidget() 
	{
		return GWT.create(VStackWidget.class);
	}
	
	@Override
    public VStackWidget getWidget() 
	{
        return (VStackWidget)super.getWidget();
    }

	/**
	 * Called whenever an update is received from the server
	 */
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client)
	{
		VStackWidget stackWidget = getWidget();
		
		stackWidget.id = uidl.getId();
		stackWidget.client = client;
		
		super.updateFromUIDL(uidl, client);
		
        if (uidl.getIntAttribute("svoi") == 5)
        {
        	int state = uidl.getIntAttribute("vaadock_tabsheet_state");
        	VConsole.log("VStackWidget: set initial state = " + state);
			stackWidget.setState(state);
        	stackWidget.maximizeEnabled = uidl.getBooleanAttribute("maximize_enabled");
        	stackWidget.minimizeEnabled = uidl.getBooleanAttribute("minimize_enabled");
            
            if (!stackWidget.maximizeEnabled)
            	stackWidget.maximizeButton.setAttribute("style", "display: none;");
            if (!stackWidget.minimizeEnabled)
            	stackWidget.minimizeButton.setAttribute("style", "display: none;");
        }
	}
}
