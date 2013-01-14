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

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

/**
 * @author rushan
 *
 */

public class TopbarComponent extends CustomComponent
{
	GridLayout layout = new GridLayout(3, 3);
	
	Label label00 = new Label();
	Label label01 = new Label();
	Label label02 = new Label();
	
	Label label10 = new Label();
	Label label11 = new Label();
	Label label12 = new Label();
	
	Label label20 = new Label();
	Label label21 = new Label();
	Label label22 = new Label();

	private Component content;

	public TopbarComponent()
	{
		setContent(new CssLayout());
		
		this.setSizeUndefined();
		this.setWidth("100%");
		
		layout.setSizeUndefined();
		layout.setWidth("100%");
		
		layout.addComponent(label00, 0, 0);
		layout.addComponent(label10, 0, 1);
		layout.addComponent(label20, 0, 2);
		
		layout.addComponent(label01, 1, 0);
		layout.addComponent(label21, 1, 2);
		
		layout.addComponent(label02, 2, 0);
		layout.addComponent(label12, 2, 1);
		layout.addComponent(label22, 2, 2);
		
		label00.setIcon(new ThemeResource("../vaaclipse_default_theme/img/toptrimbar_00.png"));
		label02.setIcon(new ThemeResource("../vaaclipse_default_theme/img/toptrimbar_02.png"));
		label20.setIcon(new ThemeResource("../vaaclipse_default_theme/img/toptrimbar_20.png"));
		label22.setIcon(new ThemeResource("../vaaclipse_default_theme/img/toptrimbar_22.png"));
		
		label01.addStyleName("toptrimbar_01");
		label21.addStyleName("toptrimbar_21");
		label10.addStyleName("toptrimbar_10");
		label12.addStyleName("toptrimbar_12");
		
		label01.setWidth("100%");
		label21.setWidth("100%");
		
		label10.setHeight("100%");
		label10.setWidth(-1, Unit.PIXELS);
		label12.setHeight("100%");
		label12.setWidth(-1, Unit.PIXELS);
		
		layout.setColumnExpandRatio(0, 0);
		layout.setColumnExpandRatio(1, 10);
		layout.setColumnExpandRatio(2, 0);
		
		this.setCompositionRoot(layout);
	}
	
	public Component getContent()
	{
		return content;
	}
	
	public void setContent(Component content)
	{
		layout.removeComponent(this.content);
		layout.addComponent(content, 1, 1);
		
		this.content = content;
		content.setSizeUndefined();
		content.setWidth("100%");
	}
}
