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

import java.util.HashMap;
import java.util.Map;

import com.vaadin.client.ui.VVerticalLayout;

public class VExtendedVerticalLayout extends VVerticalLayout
{	
	public static final String VARIABLES = "_variables_";
	public static final String ENABLE_BOUNDS_UPDATE = "enable_bounds_update";

	/** The client side widget identifier */
	String paintableId;
	
	BoundsUpdateManager updateManager;
	
	Map<String, String> variables = new HashMap<String, String>();
	Boolean enableBoundsUpdate = false;
	
	@Override
	public void recalculateLayoutHeight() 
	{
		if (updateManager != null)
			updateManager.update();
		
		super.recalculateLayoutHeight();
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
