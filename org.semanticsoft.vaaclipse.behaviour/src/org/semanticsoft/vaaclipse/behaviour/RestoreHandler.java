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

package org.semanticsoft.vaaclipse.behaviour;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MItem;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

@SuppressWarnings("restriction")
public class RestoreHandler {
	@Execute
	public void restore(MItem item, EModelService modelService, MWindow window, MApplication application) {
		String stackId = item.getContainerData();
		MUIElement element = modelService.find(stackId, window);
		element.getTags().remove(IPresentationEngine.MINIMIZED_BY_ZOOM);
		element.getTags().remove(IPresentationEngine.MINIMIZED);
	}
}
