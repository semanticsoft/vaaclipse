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

package org.semanticsoft.vaaclipse.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.semanticsoft.vaaclipse.widgets.client.ui.extlayout.VExtendedVerticalLayout;

import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.ui.LegacyComponent;
import com.vaadin.ui.VerticalLayout;

public class ExtendedVerticalLayout extends VerticalLayout implements LegacyComponent
{
	private Map<String, String> variables = new HashMap<String, String>();
	private boolean enableBoundsUpdate = false;

	@Override
	public void paintContent(PaintTarget target) throws PaintException
	{
		target.addAttribute(VExtendedVerticalLayout.VARIABLES, variables);
	}

	/**
	 * Receive and handle events and other variable changes from the client.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void changeVariables(Object source, Map<String, Object> variables)
	{
		
//		System.out.println("absoluteLeft: " + absoluteLeft);
//		System.out.println("absoluteTop: " + absoluteTop);
//		System.out.println("offsetWidth: " + offsetWidth);
//		System.out.println("offsetHeight: " + offsetHeight);
		
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
