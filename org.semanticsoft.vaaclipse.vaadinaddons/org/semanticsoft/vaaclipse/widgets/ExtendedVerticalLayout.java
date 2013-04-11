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

import org.semanticsoft.commons.geom.Bounds;
import org.semanticsoft.vaaclipse.widgets.client.ui.extlayout.VExtendedVerticalLayout;

import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.ui.LegacyComponent;
import com.vaadin.ui.VerticalLayout;

public class ExtendedVerticalLayout extends VerticalLayout implements LegacyComponent
{
	public static interface BoundsUpdateListener {
		void processEvent(ExtendedVerticalLayout layout);
	}
	
	private Integer absoluteLeft;
	private Integer absoluteTop;
	private Integer offsetWidth;
	private Integer offsetHeight;
	
	private Map<String, String> variables = new HashMap<String, String>();
	private boolean enableBoundsUpdate = false;
	
	private List<BoundsUpdateListener> boundsUpdateListeners = new ArrayList<ExtendedVerticalLayout.BoundsUpdateListener>();
	
	public void addBoundsUpdateListener(BoundsUpdateListener l)
	{
		this.boundsUpdateListeners.add(l);
	}

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
		if (variables.containsKey("absolute_top"))
		{
			absoluteTop = (Integer) variables.get("absolute_top");	
		}
		
		if (variables.containsKey("offset_width"))
		{
			offsetWidth = (Integer) variables.get("offset_width");	
		}
		
		if (variables.containsKey("offset_height"))
		{
			offsetHeight = (Integer) variables.get("offset_height");	
		}
		
//		System.out.println("absoluteLeft: " + absoluteLeft);
//		System.out.println("absoluteTop: " + absoluteTop);
//		System.out.println("offsetWidth: " + offsetWidth);
//		System.out.println("offsetHeight: " + offsetHeight);
		
	}
	
	public boolean hasBoundsInfo()
	{
		return this.absoluteTop != null;
	}
	
	public Integer getAbsoluteLeft()
	{
		return absoluteLeft;
	}
	
	public Integer getAbsoluteTop()
	{
		return absoluteTop;
	}
	
	public Integer getOffsetWidth()
	{
		return offsetWidth;
	}
	
	public Integer getOffsetHeight()
	{
		return offsetHeight;
	}
	
	public Bounds getBounds()
	{
		if (hasBoundsInfo())
			return new Bounds(absoluteLeft, absoluteTop, offsetWidth, offsetHeight);
		else
			return null;
	}
	
	public void setBounds(Bounds bounds)
	{
		this.absoluteLeft = bounds.x;
		this.absoluteTop = bounds.y;
		this.offsetWidth = bounds.w;
		this.offsetHeight = bounds.h;
	}
	
	public String getVariableValue(String varName)
	{
		return this.variables.get(varName);
	}
	
	public void setVariableValue(String varName, String varValue)
	{
		this.variables.put(varName, varValue);
	}
	
	public boolean isEnableBoundsUpdate()
	{
		return enableBoundsUpdate;
	}
	
	public void setEnableBoundsUpdate(boolean enableBoundsUpdate)
	{
		this.enableBoundsUpdate = enableBoundsUpdate;
	}
}
