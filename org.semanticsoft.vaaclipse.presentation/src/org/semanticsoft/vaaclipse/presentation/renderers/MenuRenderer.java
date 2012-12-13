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
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.ui.internal.workbench.ContributionsAnalyzer;
import org.eclipse.e4.ui.model.application.ui.MCoreExpression;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.workbench.modeling.ExpressionContext;
import org.semanticsoft.vaaclipse.api.MenuContributionService;
import org.semanticsoft.vaaclipse.api.VaadinExecutorService;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

@SuppressWarnings("restriction")
public class MenuRenderer extends BasicMenuToolbarTrimbarRenderer
{
	@Inject
	MenuContributionService contributionService;
	
	@Inject
	VaadinExecutorService execService;
	
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
	public void processContents(MElementContainer<MUIElement> element)
	{
		final MMenu menu = (MMenu)(MElementContainer<?>)element;
		
		final IEclipseContext ctx = getContext(menu);
		final ExpressionContext eContext = new ExpressionContext(ctx);
		
		//Before contribution added:
		//visible when support for original trimbar elements (without contributed)
		for (final MMenuElement child : menu.getChildren())
		{
			if (child.getVisibleWhen() != null) {
				ctx.runAndTrack(new RunAndTrack() {
					@Override
					public boolean changed(IEclipseContext context) {
						
						if (!menu.isToBeRendered()
								|| !menu.isVisible()
								|| menu.getWidget() == null) {
							System.err.println("remove context tracker");
							return false;
						}
						
						final boolean rc = ContributionsAnalyzer.isVisible((MCoreExpression)child.getVisibleWhen(), eContext);
						Runnable runnable = new Runnable() {
							
							@Override
							public void run()
							{
								child.setToBeRendered(rc);
							}
						};
						execService.invokeLater(runnable);
						
						return true;
					}
				});
			}
		}
		//Then add contributions using contribution service:
		contributionService.addContributions((MMenu)(MElementContainer<?>)element);
	}
	
	@Override
	public void disposeWidget(MUIElement element)
	{
		contributionService.removeContributions((MMenu)(MElementContainer<?>)element);
	}
	
	@Override
	public void addChildGui(MUIElement child, MElementContainer<MUIElement> element)
	{
		//Do nothing - child will be attaced in createGui
	}
	
	@Override
	public void removeChildGui(MUIElement element, MElementContainer<MUIElement> parent)
	{
		MenuItem childItem = (MenuItem) element.getWidget();
		
		if (parent.getWidget() instanceof MenuBar)
		{
			MenuBar bar = (MenuBar) parent.getWidget();
			bar.removeItem(childItem);
		}
		else if (parent.getWidget() instanceof MenuItem)
		{
			MenuItem parentItem = (MenuItem) parent.getWidget();
			parentItem.removeChild(childItem);
		}
	}
}
