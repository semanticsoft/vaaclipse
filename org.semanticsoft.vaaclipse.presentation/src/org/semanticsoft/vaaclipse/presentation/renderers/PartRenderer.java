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

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import com.vaadin.ui.Panel;


@SuppressWarnings("restriction")
public class PartRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		Panel pane = new Panel();
		pane.setSizeFull();
		final MPart part = (MPart) element;

		// Create a context for this part
		IEclipseContext localContext = part.getContext();
		localContext.set(Panel.class, pane);

		IContributionFactory contributionFactory = (IContributionFactory) localContext.get(IContributionFactory.class
				.getName());
		Object newPart = contributionFactory.create(part.getContributionURI(), localContext);
		part.setObject(newPart);
		
		pane.setStyleName("page_component");

		element.setWidget(pane);
	}
}
