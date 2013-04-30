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
import java.util.List;

/**
 * @author rushan
 *
 */
public class TwoStateToolbarButton extends ToolbarButtonBase
{
	private String primaryStyle;
	private String selectedStyle;
	
	private boolean switchOn = false;
	private boolean switchStateByUserClickEnabled = true;
	private List<ClickListener> userListeners = new ArrayList<ClickListener>();
	
	public TwoStateToolbarButton() {
		this("Blank", null);
	}
	
	public TwoStateToolbarButton(String label, String iconURI) {
		
		super(label, iconURI);
		
		setPrimaryStyle("vaaclipsebutton");
		setSelectedStyle("pushed");
		
		setCheckedState(false);
		
		super.addClickListener(new ClickListener() {
					
			public void buttonClick(ClickEvent event) {
				if (switchStateByUserClickEnabled)
				{
					setCheckedState(!switchOn);
					
					for (ClickListener l : userListeners)
					{
						l.buttonClick(event);
					}
				}
			}
		});
	}
	
	public boolean getCheckedState()
	{
		return switchOn;
	}
	
	public void setCheckedState(boolean switchOn) {
		if (switchOn)
		{
			addStyleName(selectedStyle);
		}
		else
		{
			removeStyleName(selectedStyle);
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
	
	public String getPrimaryStyle()
	{
		return primaryStyle;
	}
	
	public void setPrimaryStyle(String primaryStyle)
	{
		removePrimaryStyle();
		this.primaryStyle = primaryStyle;
		this.addStyleName(this.primaryStyle);
	}
	
	public void removePrimaryStyle()
	{
		if (this.primaryStyle != null)
			this.removeStyleName(this.primaryStyle);
	}
		
	public String getSelectedStyle()
	{
		return selectedStyle;
	}
	
	public void setSelectedStyle(String selectedStyle)
	{
		if (this.switchOn)
			this.removeStyleName(this.selectedStyle);
		this.selectedStyle = selectedStyle;
		if (this.switchOn)
			this.addStyleName(this.selectedStyle);
	}
}
