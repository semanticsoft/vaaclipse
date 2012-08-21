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

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.UIEvents.UIElement;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.presentation.widgets.StackWidget;
import org.semanticsoft.vaaclipse.presentation.widgets.StackWidget.StateListener;

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
}
