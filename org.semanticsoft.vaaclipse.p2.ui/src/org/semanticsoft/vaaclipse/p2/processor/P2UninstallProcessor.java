package org.semanticsoft.vaaclipse.p2.processor;

import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.semanticsoft.vaaclipse.p2.iservice.IUninstallSoftwareService;
import org.semanticsoft.vaaclipse.p2.uninstall.ui.IUninstallView;
import org.semanticsoft.vaaclipse.p2.uninstall.ui.impl.UninstallView;
/*******************************************************************************
 * Copyright (c) 2012 Klevis Ramo and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Klevis Ramo - initial API and implementation
 *******************************************************************************/
public class P2UninstallProcessor {

	@Execute
	public void setUp(IEclipseContext ctx,
			IUninstallSoftwareService uninstallSoftwareService,
			IProvisioningAgent provisioningAgent) {

		// start uninstall config
		List<IInstallableUnit> listInstalledSoftware = uninstallSoftwareService
				.listInstalledSoftware(provisioningAgent,
						IUninstallSoftwareService.GROUP);
		IUninstallView uninstallView = null;
		if (listInstalledSoftware != null)
			uninstallView = new UninstallView(listInstalledSoftware,
					uninstallSoftwareService, provisioningAgent);
		else {
			uninstallView = new UninstallView(

			uninstallSoftwareService, provisioningAgent);
		}
		ctx.set(IUninstallView.class, uninstallView);
	}
}
