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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.semanticsoft.commons.geom.Bounds;
import org.semanticsoft.vaadinaddons.boundsinfo.client.ui.BoundsParser;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;

import fi.jasoft.dragdroplayouts.DDTabSheet;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;

/**
 * @author rushan
 *
 */
@com.vaadin.ui.ClientWidget(org.semanticsoft.vaaclipse.widgets.client.ui.VStackWidget.class)
public class StackWidget extends DDTabSheet
{
	public interface StateListener {
		void stateChanged(int newState, int oldState);
	}
	
	private Integer absoluteLeft;
	private Integer absoluteTop;
	private Integer offsetWidth;
	private Integer offsetHeight;
	
	public transient boolean maximizeEnabled = true;
	public transient boolean minimizeEnabled = true;
	private int state = 0;
	private List<StateListener> stateListeners = new ArrayList<StateListener>();
	
	public StackWidget()
	{
		this.addStyleName("folder_vaa_component");
		this.setDragMode(LayoutDragMode.CLONE);
//		this.setDropHandler(new VaadinDropHandler(workbench, this));
	}
	
	@Override
	public void changeVariables(Object source, Map<String, Object> variables) {
		super.changeVariables(source, variables);
		
		if (variables.containsKey("vaadock_tabsheet_state")) {
			int newState = (Integer) variables.get("vaadock_tabsheet_state");
			int oldState = state;
			state = newState;
			fireStateChangedEvent(state, oldState);
        }
		
		if (variables.containsKey("bounds"))
		{
			String boundsStr = (String) variables.get("bounds");
			if (boundsStr != null)
			{
				int[] bounds = BoundsParser.fromString(boundsStr);
				absoluteLeft = bounds[0];
				absoluteTop = bounds[1];
				offsetWidth = bounds[2];
				offsetHeight = bounds[3];
				System.out.println("update bounds of StackWidget: " + boundsStr);
			}
		}
	}
	
	public void setState(int state)
	{
		this.state = state;
		this.requestRepaint();
	}
	
	@Override
	public void paintContent(PaintTarget target) throws PaintException
	{
		super.paintContent(target);
		
		target.addAttribute("vaadock_tabsheet_state", state);
		target.addAttribute("maximize_enabled", this.maximizeEnabled);
		target.addAttribute("minimize_enabled", this.minimizeEnabled);
		target.addAttribute("svoi", 5);
	}

	public boolean isMaximizeEnabled()
	{
		return this.maximizeEnabled;
	}

	public void setMaximizeEnabled(boolean maximizeEnabled)
	{
		this.maximizeEnabled = maximizeEnabled;
		this.requestRepaint();
	}

	public boolean isMinimizeEnabled()
	{
		return this.minimizeEnabled;
	}

	public void setMinimizeEnabled(boolean minimizeEnabled)
	{
		this.minimizeEnabled = minimizeEnabled;
		this.requestRepaint();
	}

//	public void setSelectedTab(int pos)
//	{
//		if (pos >= 0 && pos <= this.getComponentCount())
//		{
//			Tab tab = this.getTab(pos);
//			this.setSelectedTab(tab.getComponent());
//		}
//	}
//
//	public Object getPlatformComponent(int pos)
//	{
//		return this.getTab(pos).getComponent();
//	}
	
	public List<StateListener> getStateListeners()
	{
		return Collections.unmodifiableList(stateListeners);
	}
	
	public void addStateListener(StateListener stateListener)
	{
		this.stateListeners.add(stateListener);
	}
	
	public void removeStateListener(StateListener stateListener)
	{
		this.stateListeners.remove(stateListener);
	}
	
	public void removeAllStateListeners()
	{
		this.stateListeners.clear();
	}
	
	private void fireStateChangedEvent(int newState, int oldState)
	{
		for (StateListener stateListener : new ArrayList<StateListener>(this.stateListeners))
		{
			stateListener.stateChanged(newState, oldState);
		}
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
}
