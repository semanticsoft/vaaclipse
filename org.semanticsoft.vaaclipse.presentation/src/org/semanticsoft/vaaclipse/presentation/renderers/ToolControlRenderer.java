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

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolControl;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.semanticsoft.vaaclipse.presentation.utils.GuiUtils;

import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * Create a contribute part.
 */
public class ToolControlRenderer extends GenericVaadinRenderer {
	
	@Inject
	EModelService modelService;
	
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) 
	{
		if (!(element instanceof MToolControl))
			return;
		
		MToolControl toolControl = (MToolControl) element;
		
		ComponentContainer toolControlContainer;
		if ((MElementContainer<?>)element.getParent() instanceof MTrimBar)
		{
			MTrimBar trimBar = (MTrimBar)(MElementContainer<?>)element.getParent();
			if (trimBar.getSide() == SideValue.LEFT || trimBar.getSide() == SideValue.RIGHT)
				toolControlContainer = new VerticalLayout();
			else
				toolControlContainer = new HorizontalLayout();
			//add the drag handler (separator)
			toolControlContainer.addComponent(GuiUtils.createSeparator(toolControl));
		}
		else
			toolControlContainer = new VerticalLayout();
		
		IEclipseContext parentContext = modelService.getContainingContext(element);

		// Create a context just to contain the parameters for injection
		IContributionFactory contributionFactory = parentContext
				.get(IContributionFactory.class);

		IEclipseContext localContext = EclipseContextFactory.create();

		localContext.set(Component.class, toolControlContainer);
		localContext.set(ComponentContainer.class, toolControlContainer);
		localContext.set(MToolControl.class, toolControl);
		
		Object tcImpl = contributionFactory.create(toolControl.getContributionURI(), parentContext, localContext);
		toolControl.setObject(tcImpl);
		toolControl.setWidget(toolControlContainer);
	}

}
