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


/**
 * @author rushan
 *
 */
public class ToolbarButton extends ToolbarButtonBase
{
	public static enum Type {Button, Link}
	
	private Type type;
	
	public ToolbarButton() {
		this("Blank", null);
	}
	
	public ToolbarButton(String label, String iconURI) {
		
		super(label, iconURI);
		setType(Type.Button);
	}
	
	public void setType(Type type)
	{
		if (type == Type.Link)
		{
			this.addStyleName("link");
			this.addStyleName("general");
		}
		else if (type == Type.Button)
		{
			this.addStyleName("vaaclipsebutton");
		}
		this.type = type;
	}
}
