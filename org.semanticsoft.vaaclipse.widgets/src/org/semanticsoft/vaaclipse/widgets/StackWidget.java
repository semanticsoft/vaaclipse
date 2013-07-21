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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.shared.Connector;
import com.vaadin.ui.Component;

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
	
	private boolean minMaxEnabled = true;
	private int state = 0;
	private List<StateListener> stateListeners = new ArrayList<StateListener>();
	
	public StackWidget()
	{
		this.addStyleName("stackwidget");
		this.setDragMode(LayoutDragMode.CLONE);
	}
	
	/**
	 * The hack that allow avoid bug in DDTabSheet when sometimes draggable tabs become non draggable
	 */
//	@Override
//	public void beforeClientResponse(boolean initial) {
//		super.beforeClientResponse(initial);
//		
//		Iterator<Component> componentIterator = getComponentIterator();
//        getState().draggable = new ArrayList<Connector>();
//        while (componentIterator.hasNext()) {
//            Component c = componentIterator.next();
//            getState().draggable.add(c);
//        }
//	}
	
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
		this.markAsDirty();
	}
	
	@Override
	public void paintContent(PaintTarget target) throws PaintException
	{
		super.paintContent(target);
		
		target.addAttribute("vaadock_tabsheet_state", state);
		target.addAttribute("minmax_enabled", this.minMaxEnabled);
		target.addAttribute("svoi", 5);
	}
	
	public boolean isMinMaxEnabled() {
		return minMaxEnabled;
	}
	
	public void setMinMaxEnabled(boolean minMaxEnabled) {
		this.minMaxEnabled = minMaxEnabled;
		this.requestRepaint();
	}
	
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
}
