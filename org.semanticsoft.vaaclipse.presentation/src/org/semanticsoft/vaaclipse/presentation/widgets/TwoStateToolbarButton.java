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

import com.vaadin.terminal.Resource;

/**
 * @author rushan
 *
 */
public class TwoStateToolbarButton extends ToolbarButton
{
	private boolean switchOn = false;
	private boolean switchStateByUserClickEnabled = true;
	private List<ClickListener> userListeners = new ArrayList<ClickListener>();
	
	public TwoStateToolbarButton() {
		setState(false);
		
		super.addListener(new ClickListener() {
					
			public void buttonClick(ClickEvent event) {
				if (switchStateByUserClickEnabled)
				{
					setState(!switchOn);
					
					for (ClickListener l : userListeners)
					{
						l.buttonClick(event);
					}
				}
			}			
		});
	}
	
	public boolean getState()
	{
		return switchOn;
	}
	
	public void setState(boolean switchOn) {
		if (switchOn)
		{
			removeStyleName("vaadock-toolbar-button");
			addStyleName("vaadock-toolbar-button_selected");
		}
		else
		{
			removeStyleName("vaadock-toolbar-button_selected");
			addStyleName("vaadock-toolbar-button");
		}
		
		this.switchOn = switchOn;
	}
	
	@Override
	public void addListener(ClickListener listener)
	{
		this.userListeners.add(listener);
	}
	
	public boolean isSwitchStateByUserClickEnabled()
	{
		return switchStateByUserClickEnabled;
	}
	
	public void setSwitchStateByUserClickEnabled(boolean switchStateByUserClickEnabled)
	{
		this.switchStateByUserClickEnabled = switchStateByUserClickEnabled;
	}
}
