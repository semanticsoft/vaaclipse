/*******************************************************************************
 * Copyright (c) 2011 Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *******************************************************************************/

package org.semanticsoft.vaaclipse.presentation.widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;

import fi.jasoft.dragdroplayouts.DDTabSheet;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;

/**
 * @author rushan
 *
 */
public class StackWidget extends DDTabSheet
{
	public interface StateListener {
		void stateChanged(int newState, int oldState);
	}
	
	private transient CloseHandler userCloseHandler;
	public transient boolean maximizeEnabled = true;
	public transient boolean minimizeEnabled = true;
	private int state = 0;
	private List<StateListener> stateListeners = new ArrayList<StateListener>();
	
	public StackWidget()
	{
		this.addStyleName("folder_vaa_component");
		this.setDragMode(LayoutDragMode.CLONE);
//		this.setDropHandler(new VaadinDropHandler(workbench, this));
		
		super.setCloseHandler(new CloseHandler() {
			
			public void onTabClose(TabSheet tabsheet, Component tabContent) {
				tabsheet.removeComponent(tabContent);
				//TODO:
			}
		});
		
		this.addListener(new SelectedTabChangeListener() {
			
			public void selectedTabChange(SelectedTabChangeEvent event)
			{
				//TODO:
			}
		});
	}
	
	@Override
	public void setCloseHandler(CloseHandler handler) {
		this.userCloseHandler = handler;
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
	}

	public boolean isMinimizeEnabled()
	{
		return this.minimizeEnabled;
	}

	public void setMinimizeEnabled(boolean minimizeEnabled)
	{
		this.minimizeEnabled = minimizeEnabled;
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
		for (StateListener stateListener : this.stateListeners)
		{
			stateListener.stateChanged(newState, oldState);
		}
	}
}
