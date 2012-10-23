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
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.semanticsoft.vaaclipse.widgets.StackWidget;

import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * @author rushan
 * 
 */
public class AreaRenderer extends GenericRenderer
{
	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent)
	{
		if (!(element instanceof MArea))
			return;

		MArea area = (MArea) element;
		AbstractOrderedLayout areaComp;
		if (area.isHorizontal())
			areaComp = new HorizontalLayout();
		else
			areaComp = new VerticalLayout();
		areaComp.addStyleName("area_background");
		areaComp.setSizeFull();
		element.setWidget(areaComp);
	}

	@Override
	public void processContents(MElementContainer<MUIElement> container)
	{
		AbstractOrderedLayout parentPane = (AbstractOrderedLayout) container.getWidget();
		parentPane.removeAllComponents();
		for (MUIElement element : container.getChildren())
		{
			if (element instanceof MPlaceholder)
				element = ((MPlaceholder) element).getRef();
			parentPane.addComponent((Component) element.getWidget());
		}
	}
	
	public static MArea findArea(MUIElement element) {
		MUIElement parent = element.getParent();
		while (parent != null) {
			if (parent instanceof MArea)
				return (MArea) parent;
			parent = parent.getParent();
		}
		return null;
	}
	
	@Override
	public void addChild(MUIElement child, MElementContainer<MUIElement> element)
	{
		if (!(child instanceof MPartSashContainerElement))
			return;
		
		super.addChild(child, element);
		
		AbstractOrderedLayout areaWidget = (AbstractOrderedLayout) element.getWidget();
		int index = element.getChildren().indexOf(child);
		areaWidget.addComponent((Component) child.getWidget(), index);
	}
}
