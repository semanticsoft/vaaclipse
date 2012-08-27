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

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;

import com.vaadin.ui.Component;
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
		
		CssLayout trimBar = new CssLayout();
		
		if (orientation == SideValue.TOP_VALUE || orientation == SideValue.BOTTOM_VALUE)
			trimBar.addStyleName("horizontaltrimbar");
		else
			trimBar.addStyleName("verticaltrimbar");
		
		trimBar.setSizeUndefined();
		element.setWidget(trimBar);
	}

	@Override
	public void processContents(MElementContainer<MUIElement> container) {
		MTrimBar trimBar = (MTrimBar)((MElementContainer<?>)container);
		int orientation = trimBar.getSide().getValue();
		CssLayout toolBar = (CssLayout) container.getWidget();
		if (orientation == SideValue.TOP_VALUE || orientation == SideValue.BOTTOM_VALUE)
			toolBar.setHeight(-1);
		else
			toolBar.setWidth(-1);

		boolean isFirst = true;
		toolBar.removeAllComponents();
		for (MUIElement element : container.getChildren()) {
			//CssLayout subToolbar = (CssLayout) renderer.createGui(element);
			CssLayout subToolbar = (CssLayout) element.getWidget();
			subToolbar.setVisible(element.isVisible());
			if (subToolbar != null) {
				if (orientation == SideValue.TOP_VALUE || orientation == SideValue.BOTTOM_VALUE)
					subToolbar.addStyleName("horizontaltoolbar");
				else
					subToolbar.addStyleName("verticaltoolbar");
				
				subToolbar.setSizeUndefined();
				
				if (!isFirst) {
					//TODO: исправить
					com.vaadin.ui.Panel separator = new Panel();
					separator.setSizeUndefined();
					if (orientation == SideValue.TOP_VALUE || orientation == SideValue.BOTTOM_VALUE)
					{
						separator.addStyleName("horizontalseparator");
					}
					else
					{
						separator.addStyleName("verticalseparator");
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
		((Component)element.getWidget()).requestRepaint();
	}
}
