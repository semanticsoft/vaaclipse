/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.semanticsoft.vaadin.optiondialog.OptionDialog;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.UI;

public class AboutHandler 
{
	
	@Execute
	public void about(UI ui)
	{
		OptionDialog.show(ui, "About", String.format("Mediaplayer - demo application for Vaaclipse Framework"), new String[] {"OK"}, 500, 100, Unit.PIXELS, OptionDialog.CLOSE_LISTENER);
	}	
	
}
