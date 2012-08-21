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

import com.vaadin.terminal.Resource;
import com.vaadin.ui.NativeButton;

/**
 * @author rushan
 *
 */
public class ToolbarButton extends NativeButton
{
	public ToolbarButton()
	{
		init();
	}
	
	public ToolbarButton(Resource icon)
	{
		setIcon(icon);
		init();
	}
	
	private void init()
	{
		addStyleName("vaadock-toolbar-button");
	}
}
