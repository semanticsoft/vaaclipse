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

package org.semanticsoft.vaaclipse.presentation.renderers;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

@SuppressWarnings("restriction")
public class MenuBarRenderer extends GenericRenderer
{

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent)
	{
		if (!(element instanceof MMenu))
			return;
		
		if (MWindow.class.isAssignableFrom(parent.getClass()))
		{
			MenuBar menuBar = new MenuBar();
			element.setWidget(menuBar);	
		}
		else 
		{
			String text = ((MUILabel) element).getLocalizedLabel();
			text = text.replaceAll("&", "");

			MenuItem item = null;
			if (parent.getWidget() instanceof MenuBar)
				item = ((MenuBar)parent.getWidget()).addItem(text, null, null);
			else if (parent.getWidget() instanceof MenuItem)
				item = ((MenuItem)parent.getWidget()).addItem(text, null, null);
			
			element.setWidget(item);
		}
	}

	@Override
	public void processContents(MElementContainer<MUIElement> container)
	{
		//the child menu items already attached when created - vaadin API issue
	}
}
