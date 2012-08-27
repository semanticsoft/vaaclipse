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

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MAddon;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MItem;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

@SuppressWarnings("restriction")
public class FastViewHandler {
	@Execute
	public void showFastView(MItem item, EModelService modelService, EPartService partService, MApplication application) {
		
		List<MAddon> addons = application.getAddons();
		for (MAddon addon : addons) {
			if (addon.getContributionURI().equals(MinMaxProcessor.MIN_MAX_CONTRIBUTION_URI)) {
				if (addon.getObject() instanceof MinMaxAddon) {
					MinMaxAddon minMaxAddon = (MinMaxAddon) addon.getObject();
					MPart part = minMaxAddon.item2Element.get(item);
					partService.activate(part);
				}
			}
		}
	}
}
