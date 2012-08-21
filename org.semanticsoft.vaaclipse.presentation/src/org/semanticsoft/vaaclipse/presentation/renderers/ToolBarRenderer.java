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
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarSeparator;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;


@SuppressWarnings("restriction")
public class ToolBarRenderer extends GenericRenderer {

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MToolBar)) {
			return;
		}

		CssLayout toolBar = new CssLayout();
		element.setWidget(toolBar);
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> container) {
		CssLayout toolBar = (CssLayout) container.getWidget();
		toolBar.removeAllComponents();
		for (MUIElement element : container.getChildren()) {
			if (element instanceof MHandledToolItem || element instanceof MDirectToolItem) {
				toolBar.addComponent((Component) element.getWidget());
			} else if (element instanceof MToolBarSeparator) {
				toolBar.addComponent(new Label("Separator"));
			}
		}
	}
}
