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

import com.vaadin.ui.VerticalLayout;

/**
 * Server side component for the VBoundsinfoVerticalLayout widget.
 */
public class BoundsinfoVerticalLayout extends VerticalLayout
{
	public static interface BoundsUpdateListener {
		void processEvent(BoundsinfoVerticalLayout layout);
	}
	
	private Integer absoluteLeft = 50;
	private Integer absoluteTop = 50;
	private Integer offsetWidth = 200;
	private Integer offsetHeight = 200;
	
	private Map<String, String> variables = new HashMap<String, String>();
	private boolean enableBoundsUpdate = false;
	
	private List<BoundsUpdateListener> boundsUpdateListeners = new ArrayList<BoundsinfoVerticalLayout.BoundsUpdateListener>();
	
	public void addBoundsUpdateListener(BoundsUpdateListener l)
	{
		this.boundsUpdateListeners.add(l);
	}

//	@Override
//	public void paintContent(PaintTarget target) throws PaintException
//	{
//		super.paintContent(target);
//
//		// Paint any component specific content by setting attributes
//		// These attributes can be read in updateFromUIDL in the widget.
//		target.addAttribute("clicks", clicks);
//		target.addAttribute("message", message);
//
//		target.addAttribute(VBoundsinfoVerticalLayout.ENABLE_BOUNDS_UPDATE, enableBoundsUpdate);
//		target.addAttribute(VBoundsinfoVerticalLayout.VARIABLES, variables);
//	}

//	@Override
//	public void changeVariables(Object source, Map<String, Object> variables)
//	{
//		super.changeVariables(source, variables);
//		
//		if (variables.containsKey("bounds"))
//		{
//			System.out.println("update bounds of BoundsInfoVerticalLayout");
//			String boundsStr = (String) variables.get("bounds");
//
//			int[] bounds = BoundsParser.fromString(boundsStr);
//			absoluteLeft = bounds[0];
//			absoluteTop = bounds[1];
//			offsetWidth = bounds[2];
//			offsetHeight = bounds[3];
//			
//			for (BoundsUpdateListener l : this.boundsUpdateListeners)
//			{
//				l.processEvent(this);
//			}
//		}
//		
//		if (variables.containsKey("absolute_top"))
//		{
//			absoluteTop = (Integer) variables.get("absolute_top");	
//		}
//		
//		if (variables.containsKey("offset_width"))
//		{
//			offsetWidth = (Integer) variables.get("offset_width");	
//		}
//		
//		if (variables.containsKey("offset_height"))
//		{
//			offsetHeight = (Integer) variables.get("offset_height");	
//		}
//		
////		System.out.println("absoluteLeft: " + absoluteLeft);
////		System.out.println("absoluteTop: " + absoluteTop);
////		System.out.println("offsetWidth: " + offsetWidth);
////		System.out.println("offsetHeight: " + offsetHeight);
//		
//	}
	
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
