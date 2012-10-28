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

package org.semanticsoft.vaaclipse.presentation.renderers;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MOpaqueMenuItem;
import org.semanticsoft.vaaclipse.presentation.utils.Utils;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

@SuppressWarnings("restriction")
public class MenuItemRenderer extends ItemRenderer {

	@Inject
	IContributionFactory contributionFactory;

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (parent != null && element instanceof MMenuItem) {
			MMenuItem model = (MMenuItem) element;
			
			//--------------------
			//Prepare the text
			String text = prepareText(model);
			text = text.replaceAll("&", "");
			
			//Prepare the icon
			Resource icon = model.getIconURI() != null ? new ThemeResource(Utils.convertPath(model.getIconURI())) : null;
			
			//Prepare the command
			Command command = null;
			if (model instanceof MDirectMenuItem) {
				final MDirectMenuItem item = (MDirectMenuItem) model;
				item.setObject(contributionFactory.create(item.getContributionURI(), getContext(item)));
				
				command = createEventHandler(item);
			} else if (model instanceof MHandledMenuItem) {
				final MHandledMenuItem item = (MHandledMenuItem) model;
				command = createParametrizedCommandEventHandler(item);
			}
			
			
			MenuItem item = ((MenuItem)parent.getWidget()).addItem(text, icon, command);
			//-----------------

			element.setWidget(item);
		}
	}

	@Override
	public void processContents(MElementContainer<MUIElement> container) {
		//the child menu items already attached when created - vaadin API issue
	}

	@Override
	public void hookControllerLogic(MUIElement me) {
		//the listener already attached (when created - vaadin API issue)
	}

	@Override
	protected void setupContext(IEclipseContext context, MItem item)
	{
		context.set(MMenuItem.class, (MMenuItem)item);
		
		if (item instanceof MDirectMenuItem)
			context.set(MDirectMenuItem.class, (MDirectMenuItem)item);
		else if (item instanceof MHandledMenuItem)
			context.set(MHandledMenuItem.class, (MHandledMenuItem)item);
		else if (item instanceof MOpaqueMenuItem)
			context.set(MOpaqueMenuItem.class, (MOpaqueMenuItem)item);
	}
}
