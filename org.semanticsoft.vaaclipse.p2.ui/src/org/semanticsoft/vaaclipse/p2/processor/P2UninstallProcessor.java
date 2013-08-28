package org.semanticsoft.vaaclipse.p2.processor;

import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.semanticsoft.vaaclipse.p2.iservice.IUninstallSoftwareService;
import org.semanticsoft.vaaclipse.p2.uninstall.ui.IUninstallView;
import org.semanticsoft.vaaclipse.p2.uninstall.ui.impl.UninstallView;

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
