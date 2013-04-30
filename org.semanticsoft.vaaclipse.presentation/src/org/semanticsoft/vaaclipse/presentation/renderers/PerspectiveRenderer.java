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
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

/**
 * @author rushan
 *
 */
public class PerspectiveRenderer extends VaadinRenderer
{
	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) 
	{
		if (!(element instanceof MPerspective))
			return;
		
		VerticalLayout perspectivePanel = new VerticalLayout();
		perspectivePanel.setSizeFull();
		element.setWidget(perspectivePanel);
		
		MPerspective p = (MPerspective) element;
		
		if (p.getIconURI() == null && p.getLabel() == null)
		{
			p.setIconURI("platform:/plugin/org.semanticsoft.vaaclipse.resources/VAADIN/themes/vaaclipse_default_theme/img/blank_perspective.png");
		}
	}
	
	@Override
	public void processContents(MElementContainer<MUIElement> element)
	{
		VerticalLayout perspectivePanel = (VerticalLayout) element.getWidget();
		for (MUIElement e : element.getChildren()) {
			if (e.isToBeRendered() && e.getWidget() != null) {
				perspectivePanel.addComponent((Component) e.getWidget());
			}
		}
	}
	
	@Override
	public void addChildGui(MUIElement child, MElementContainer<MUIElement> element)
	{
		if (!(child instanceof MPartSashContainerElement))
			return;
		
		VerticalLayout sw = (VerticalLayout) element.getWidget();
		int index = indexOf(child, element);
		sw.addComponent((Component)child.getWidget(), index);
	}
}
