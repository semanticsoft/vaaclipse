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

import org.semanticsoft.vaaclipse.publicapi.resources.ResourceHelper;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

/**
 * @author rushan
 *
 */
public class ToolbarButtonBase extends Button 
{
	public ToolbarButtonBase() 
	{
		this("Blank", null);
	}
	
	public ToolbarButtonBase(String label, String iconURI) 
	{
		this.setSizeUndefined();
		
		setLabelAndIcon(label, iconURI);
		
		//hack
		super.addClickListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) 
			{
				//change focus
				Component parent = event.getButton().getParent();
                while (parent != null) {
                        if(parent instanceof Component.Focusable) {
                                ((Component.Focusable) parent).focus();
                                break;
                        } else {
                                parent = parent.getParent();
                        }
                }
			}
		});		
	}

	public void setLabelAndIcon(String label, String iconURI) {
		
		//clear
		this.setCaption(null);
		this.setIcon(null);
		this.removeStyleName("icononly");
		this.removeStyleName("textonly");
		
		//setup icon and text
		if (iconURI == null && label == null)
			label = "Blank";
		
		if (iconURI != null) {
			Resource icon = ResourceHelper.createResource(iconURI);
			this.setIcon(icon);
		}
		
		if (label != null) {
			this.setCaption(label);
		}
		
		if (iconURI == null && label != null)
			this.addStyleName("textonly");
		else if (iconURI != null && label == null)
			this.addStyleName("icononly");
	}

}
