package org.semanticsoft.vaaclipse.p2.processor;

import javax.management.InvalidApplicationException;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.semanticsoft.vaaclipse.p2.install.ui.IContainerP2Views;
import org.semanticsoft.vaaclipse.p2.install.ui.ILicenseView;
import org.semanticsoft.vaaclipse.p2.install.ui.ILoadExplorRepoistory;
import org.semanticsoft.vaaclipse.p2.install.ui.IRepositoryExplorer;
import org.semanticsoft.vaaclipse.p2.install.ui.IRepositoryLoader;
import org.semanticsoft.vaaclipse.p2.install.ui.impl.ContainerP2Views;
import org.semanticsoft.vaaclipse.p2.install.ui.impl.LicenseView;
import org.semanticsoft.vaaclipse.p2.install.ui.impl.LoadExplorRepositoryView;
import org.semanticsoft.vaaclipse.p2.install.ui.impl.P2TreeTable;
import org.semanticsoft.vaaclipse.p2.install.ui.impl.RepositoryLoader;
import org.semanticsoft.vaaclipse.p2.iservice.IInstallNewSoftwareService;

/*******************************************************************************
 * Copyright (c) 2012 Klevis Ramo and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Klevis Ramo - initial API and implementation
 *******************************************************************************/
public class P2Processor {

	@Execute
	public void setUp(IEclipseContext ctx,
			IInstallNewSoftwareService installNewSoftwareService,
			IProvisioningAgent provisioningAgent) {

		P2TreeTable p2TreeTable = new P2TreeTable(installNewSoftwareService);
		RepositoryLoader repositoryLoader = new RepositoryLoader(p2TreeTable,
				installNewSoftwareService, provisioningAgent);

		ILicenseView licenseView = new LicenseView();

		LoadExplorRepositoryView loadExplorRepositoryView = new LoadExplorRepositoryView(
				repositoryLoader, p2TreeTable, installNewSoftwareService);

		ctx.set(IRepositoryExplorer.class, p2TreeTable);
		ctx.set(IRepositoryLoader.class, repositoryLoader);
		ctx.set(ILoadExplorRepoistory.class, loadExplorRepositoryView);
		ctx.set(IContainerP2Views.class, new ContainerP2Views(
				installNewSoftwareService, loadExplorRepositoryView,
				licenseView));
		ctx.set(ILicenseView.class, licenseView);

	}
}
