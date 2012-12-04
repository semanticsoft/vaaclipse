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

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.semanticsoft.vaaclipse.publicapi.editor.SavePromptSetup;

import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;


@SuppressWarnings("restriction")
public class PartRenderer extends VaadinRenderer {

	private Map<MPart, SavePromptSetup> savePrompts = new HashMap<MPart, SavePromptSetup>();
	
	@Inject
	IPresentationEngine renderingEngine;
	
	public SavePromptSetup getSavePromptSetup(MPart part)
	{
		return savePrompts.get(part);
	}
	
	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		VerticalLayout pane = new VerticalLayout();
		pane.setSizeFull();
		final MPart part = (MPart) element;

		CssLayout toolbarArea = new CssLayout();
		toolbarArea.setStyleName("mparttoolbararea");
		toolbarArea.setSizeUndefined();
		toolbarArea.setWidth("100%");
		pane.addComponent(toolbarArea);
		
		//create toolbar
		MToolBar toolbar = part.getToolbar();
		if (toolbar != null && toolbar.isVisible())
		{
			Component toolbarWidget = (Component) renderingEngine.createGui(toolbar);
			((AbstractLayout)toolbarWidget).setSizeUndefined();
			toolbarWidget.setStyleName("mparttoolbar");
			toolbarArea.addComponent(toolbarWidget);
		}
		
		VerticalLayout contributionArea = new VerticalLayout();
		contributionArea.setSizeFull();
		pane.addComponent(contributionArea);
		pane.setExpandRatio(contributionArea, 100);
		
		IEclipseContext localContext = part.getContext();
		localContext.set(Component.class, contributionArea);
		localContext.set(ComponentContainer.class, contributionArea);
		localContext.set(VerticalLayout.class, contributionArea);
		localContext.set(MPart.class, part);
		SavePromptSetup savePromptProvider = new SavePromptSetup();
		savePrompts.put(part, savePromptProvider);
		localContext.set(SavePromptSetup.class, savePromptProvider);
		if (part instanceof MInputPart)
			localContext.set(MInputPart.class, (MInputPart)part);

		IContributionFactory contributionFactory = (IContributionFactory) localContext.get(IContributionFactory.class
				.getName());
		Object newPart = contributionFactory.create(part.getContributionURI(), localContext);
		
		part.setObject(newPart);
		
		pane.setStyleName("part");

		element.setWidget(pane);
	}
}
