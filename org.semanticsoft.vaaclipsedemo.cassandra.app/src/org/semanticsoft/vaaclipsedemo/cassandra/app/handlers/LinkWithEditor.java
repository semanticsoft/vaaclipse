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

package org.semanticsoft.vaaclipsedemo.cassandra.app.handlers;

import com.vaadin.ui.Component;

import org.eclipse.e4.ui.model.application.ui.MUIElement;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.semanticsoft.vaaclipsedemo.cassandra.app.views.PackageExplorer;

import org.eclipse.e4.core.di.annotations.Execute;

/**
 * @author rushan
 *
 */
public class LinkWithEditor
{
	private boolean link = false;
	
	@Execute
	public void execute(MApplication app, EModelService modelService)
	{
		MPart part = (MPart)modelService.find("org.semanticsoft.vaaclipsedemo.cassandra.app.part.packageexplorer", app);
		
		//hack
//		link = !link;
//		MUIElement button = modelService.find("org.semanticsoft.vaaclipsedemo.cassandra.app.directtoolitem.linkwitheditor", app);
//		Component vbutton = (Component) button.getWidget();
//		
//		if (link)
//		{
//			vbutton.removeStyleName("vaadock-toolbar-button");
//			vbutton.addStyleName("vaadock-toolbar-button_selected");
//		}
//		else
//		{
//			vbutton.removeStyleName("vaadock-toolbar-button_selected");
//			vbutton.addStyleName("vaadock-toolbar-button");
//		}
	}
}
