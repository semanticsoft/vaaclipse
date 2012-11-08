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

package org.semanticsoft.vaaclipse.presentation.utils;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimElement;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

/**
 * @author rushan
 *
 */
public class GuiUtils
{
	public static Component createSeparator(MTrimElement trimElement)
	{
		if ((MElementContainer<?>)trimElement.getParent() instanceof MTrimBar)
		{
			Label separator = new Label();
			separator.setSizeUndefined();
			
			MTrimBar parentTrimBar = (MTrimBar)(MElementContainer<?>)trimElement.getParent();
			int orientation = parentTrimBar.getSide().getValue();
			
			if (orientation == SideValue.TOP_VALUE || orientation == SideValue.BOTTOM_VALUE)
			{
				separator.addStyleName("horizontalseparator");
				separator.setHeight("100%");
			}
			else
			{
				separator.addStyleName("verticalseparator");
				separator.setWidth("100%");
			}
			return separator;
		}
		else
			return null;
		
	}
}
