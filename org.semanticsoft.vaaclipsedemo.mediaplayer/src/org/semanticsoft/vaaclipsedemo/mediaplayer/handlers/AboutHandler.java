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
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.semanticsoft.vaadin.optiondialog.OptionDialog;

import com.vaadin.Application;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public class AboutHandler 
{
	
	@Execute
	public void about(MWindow window)
	{
		Window vWindow = (Window) window.getWidget();
		
		OptionDialog.show(vWindow, "About", String.format("Mediaplayer - demo application for Vaaclipse Framework"), new String[] {"OK"}, 500, 100, Component.UNITS_PIXELS, OptionDialog.CLOSE_LISTENER);
	}	
	
}
