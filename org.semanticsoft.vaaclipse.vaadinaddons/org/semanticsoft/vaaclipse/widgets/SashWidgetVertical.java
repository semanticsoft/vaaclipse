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

import java.util.Map;

import com.vaadin.ui.VerticalSplitPanel;

/**
 * @author rushan
 * 
 */
public class SashWidgetVertical extends VerticalSplitPanel implements SashWidget
{
	private SashWidgetExtension extension = new SashWidgetExtension(this);
	
	public void addListener(SplitPositionChangedListener listener)
	{
		this.extension.addListener(listener);
	}
	
	public SashWidgetVertical()
	{
		this.setImmediate(true);
	}
	
	@Override
	public void changeVariables(Object source, Map<String, Object> variables)
	{
		super.changeVariables(source, variables);
		
		extension.fireEvent(this.getSplitPosition());

//		if (variables.containsKey("position") && !isLocked())
//		{
//			Float newPos = variables.get("position") instanceof Float ? (Float) variables.get("position") : ((Integer)variables.get("position")).floatValue();
//			setSplitPosition(newPos, UNITS_PERCENTAGE, false);
//		}

//		if (variables.containsKey(VSplitPanel.SPLITTER_CLICK_EVENT_IDENTIFIER))
//		{
//			fireClick((Map<String, Object>) variables.get(VSplitPanel.SPLITTER_CLICK_EVENT_IDENTIFIER));
//		}

	}
}
