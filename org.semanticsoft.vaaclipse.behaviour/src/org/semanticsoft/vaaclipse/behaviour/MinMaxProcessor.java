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

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MAddon;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.impl.ApplicationFactoryImpl;
import org.semanticsoft.vaaclipse.api.Behaviour;

@SuppressWarnings("restriction")
public class MinMaxProcessor {
	private static final String BUNDLE_CLASS = "bundleclass://org.semanticsoft.vaaclipse.behaviour";

	public static final String MIN_MAX_CONTRIBUTION_URI = BUNDLE_CLASS
			+ "/org.semanticsoft.vaaclipse.behaviour.MinMaxAddon";

	@Execute
	void addMinMaxAddon(MApplication application, IEclipseContext context) {
		List<MAddon> addons = application.getAddons();

		// Prevent multiple copies
		for (MAddon addon : addons) {
			if (addon.getContributionURI().equals(MIN_MAX_CONTRIBUTION_URI)) {
				return;
			}
		}
		
		MAddon minMaxAddon = ApplicationFactoryImpl.eINSTANCE.createAddon();
		minMaxAddon.setElementId("MinMaxAddon");
		minMaxAddon.setContributionURI(MIN_MAX_CONTRIBUTION_URI);
		application.getAddons().add(minMaxAddon);
	}
}
