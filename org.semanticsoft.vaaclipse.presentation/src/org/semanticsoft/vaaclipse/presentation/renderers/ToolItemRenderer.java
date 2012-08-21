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

import javax.inject.Inject;

import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.semanticsoft.vaaclipse.presentation.utils.Utils;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.NativeButton;

@SuppressWarnings("restriction")
public class ToolItemRenderer extends ItemRenderer
{
	@Inject
	IContributionFactory contributionFactory;

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent)
	{
		if (element instanceof MHandledToolItem || element instanceof MDirectToolItem)
		{
			MToolItem item = (MToolItem) element;

			NativeButton button = new NativeButton();
			button.addStyleName("vaadock-toolbar-button");

			if (item.getIconURI() != null)
			{
				Resource icon = new ThemeResource(Utils.convertPath(item.getIconURI()));
				button.setIcon(icon);
			}
			if (item.getTooltip() != null)
			{
				button.setDescription(item.getLocalizedTooltip());
			}
			element.setWidget(button);
		}
	}

	@Override
	public void hookControllerLogic(MUIElement me)
	{
		if (me instanceof MDirectToolItem)
		{
			final MDirectToolItem item = (MDirectToolItem) me;
			item.setObject(contributionFactory.create(item.getContributionURI(), getContext(item)));

			Button button = (Button) item.getWidget();
			button.addListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event)
				{
					processAction(item);
				}
			});
		}
		else if (me instanceof MHandledToolItem)
		{
			final MHandledItem item = (MHandledToolItem) me;

			Button button = (Button) item.getWidget();
			button.addListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event)
				{
					processParametrizedAction(item);
				}
			});
		}
	}
}
