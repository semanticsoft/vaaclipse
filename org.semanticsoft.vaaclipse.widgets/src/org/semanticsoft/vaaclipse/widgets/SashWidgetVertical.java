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

import java.util.Collection;
import java.util.Map;

import com.vaadin.terminal.gwt.client.ui.VSplitPanel;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalSplitPanel;

/**
 * @author rushan
 * 
 */
public class SashWidgetVertical extends VerticalSplitPanel
{
	private SashLogic sashLogic = new SashLogic(this);

	public SashWidgetVertical()
	{
		this.setImmediate(true);
	}
	
	@Override
	public void setFirstComponent(Component c)
	{
		super.setFirstComponent(c);
		if (sashLogic != null)
			sashLogic.refreshState();
	}
	
	@Override
	public void setSecondComponent(Component c)
	{
		super.setSecondComponent(c);
		if (sashLogic != null)
			sashLogic.refreshState();
	}
	
	public void refreshState()
	{
		if (sashLogic != null)
			sashLogic.refreshState();
	}
	
	@Override
	public void childRequestedRepaint(Collection<RepaintRequestListener> alreadyNotified)
	{
		if (sashLogic != null)
			sashLogic.refreshState();
		super.childRequestedRepaint(alreadyNotified);
	}
	
	@Override
	public void changeVariables(Object source, Map<String, Object> variables)
	{
		try {
			super.changeVariables(source, variables);	
		}
		catch (Exception e)
		{
			
		}

		if (variables.containsKey("position") && !isLocked())
		{
			Float newPos = variables.get("position") instanceof Float ? (Float) variables.get("position") : ((Integer)variables.get("position")).floatValue();
			setSplitPosition(newPos, UNITS_PERCENTAGE, false);
		}

		if (variables.containsKey(VSplitPanel.SPLITTER_CLICK_EVENT_IDENTIFIER))
		{
			fireClick((Map<String, Object>) variables.get(VSplitPanel.SPLITTER_CLICK_EVENT_IDENTIFIER));
		}

	}
}
