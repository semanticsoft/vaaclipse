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

package org.semanticsoft.vaaclipse.widgets.client.ui.extlayout;

import java.util.Set;

import org.semanticsoft.vaaclipse.widgets.ExtendedVerticalLayout;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.UIDL;
import com.vaadin.client.ValueMap;
import com.vaadin.client.ui.orderedlayout.VerticalLayoutConnector;
import com.vaadin.shared.ui.Connect;

/**
 * @author rushan
 *
 */
@Connect(ExtendedVerticalLayout.class)
public class ExtendedVerticalLayoutConnector extends VerticalLayoutConnector
{
	@Override
    public VExtendedVerticalLayout getWidget() 
	{
        return (VExtendedVerticalLayout)super.getWidget();
    }

	/**
	 * Called whenever an update is received from the server
	 */
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client)
	{
		VExtendedVerticalLayout layout = getWidget();
		
		layout.paintableId = uidl.getId();
		
		if(uidl.hasAttribute(VExtendedVerticalLayout.VARIABLES)){	
    		ValueMap vmap = uidl.getMapAttribute(VExtendedVerticalLayout.VARIABLES);
    		Set<String> indexes = vmap.getKeySet();
    		for(String index : indexes){
    			layout.variables.put(index, vmap.getString(index));
    		}
    	}
		
		layout.enableBoundsUpdate = uidl.getBooleanAttribute(VExtendedVerticalLayout.ENABLE_BOUNDS_UPDATE);
		if (layout.enableBoundsUpdate)
			layout.updateManager = new BoundsUpdateManager(layout, uidl.getId(), client);
	}
}
