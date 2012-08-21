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
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.semanticsoft.vaaclipse.presentation.constants.Constants;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Panel;


@SuppressWarnings("restriction")
public class TrimBarRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MTrimBar)) {
			return;
		}
		
		MTrimBar mTrimBar = (MTrimBar) element;
		int orientation = mTrimBar.getSide().getValue();
		
		CssLayout toolBar = new CssLayout();
		
//		if (orientation == SideValue.TOP_VALUE || orientation == SideValue.BOTTOM_VALUE)
//			toolBar.addStyleName("bottomsidebar_minimized_tabsheet");
//		else
//			toolBar.addStyleName("sidesidebar_minimized_tabsheet");
		toolBar.setSizeUndefined();
		element.setWidget(toolBar);
	}

	@Override
	public void processContents(MElementContainer<MUIElement> container) {
		MTrimBar trimBar = (MTrimBar)((MElementContainer<?>)container);
		int orientation = trimBar.getSide().getValue();
		IPresentationEngine renderer = (IPresentationEngine) context.get(IPresentationEngine.class.getName());
		CssLayout toolBar = (CssLayout) container.getWidget();
		if (orientation == SideValue.TOP_VALUE || orientation == SideValue.BOTTOM_VALUE)
			toolBar.setHeight(Constants.trimBarThickness);
		else
			toolBar.setWidth(Constants.trimBarThickness);

		boolean isFirst = true;
		toolBar.removeAllComponents();
		for (MUIElement element : container.getChildren()) {
			CssLayout subToolbar = (CssLayout) renderer.createGui(element);
			subToolbar.setVisible(element.isVisible());
			if (subToolbar != null) {
				if (orientation == SideValue.TOP_VALUE || orientation == SideValue.BOTTOM_VALUE)
					subToolbar.addStyleName("bottomsidebar_minimized_tabsheet");
				else
					subToolbar.addStyleName("sidesidebar_minimized_tabsheet");
				
				subToolbar.setSizeUndefined();
				
				if (!isFirst) {
					//TODO: исправить
					com.vaadin.ui.Panel separator = new Panel();
					separator.setSizeUndefined();
					if (orientation == SideValue.TOP_VALUE || orientation == SideValue.BOTTOM_VALUE)
					{
						separator.addStyleName("bottom_separator");
					}
					else
					{
						separator.addStyleName("side_separator");
					}
					
					toolBar.addComponent(separator);
				}
				toolBar.addComponent(subToolbar);
				isFirst = false;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void refreshPlatformElement(MElementContainer<?> element)
	{
		processContents((MElementContainer<MUIElement>) element);
	}
}
