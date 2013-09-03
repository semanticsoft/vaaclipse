package org.semanticsoft.vaaclipse.p2.processor;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.semanticsoft.vaaclipse.p2.iservice.IInstallNewSoftwareService;
import org.semanticsoft.vaaclipse.p2.iservice.ISitesManager;
import org.semanticsoft.vaaclipse.p2.iservice.IUninstallSoftwareService;

public class P2AllProcessors {

	@Execute
	public void setUpAll(IEclipseContext ctx,
			IInstallNewSoftwareService installNewSoftwareService,
			IProvisioningAgent provisioningAgent,
			IUninstallSoftwareService uninstallSoftwareService,
			ISitesManager sitesManager) {

		new P2InstallProcessor().setUp(ctx, installNewSoftwareService,
				provisioningAgent, uninstallSoftwareService);

		new P2SitesProcessor().setUp(ctx, provisioningAgent, sitesManager);
		new P2UninstallProcessor().setUp(ctx, uninstallSoftwareService,
				provisioningAgent);

	}
}
