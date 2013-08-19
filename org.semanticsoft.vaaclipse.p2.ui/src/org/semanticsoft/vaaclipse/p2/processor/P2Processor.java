package org.semanticsoft.vaaclipse.p2.processor;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.semanticsoft.vaaclipse.p2.install.ui.ILoadExplorRepoistory;
import org.semanticsoft.vaaclipse.p2.install.ui.IRepositoryExplorer;
import org.semanticsoft.vaaclipse.p2.install.ui.IRepositoryLoader;
import org.semanticsoft.vaaclipse.p2.install.ui.impl.LoadExplorRepositoryView;
import org.semanticsoft.vaaclipse.p2.install.ui.impl.P2TreeTable;
import org.semanticsoft.vaaclipse.p2.install.ui.impl.RepositoryLoader;
import org.semanticsoft.vaaclipse.p2.iservice.IInstallNewSoftwareService;

public class P2Processor {

	@Execute
	public void setUp(IEclipseContext ctx,
			IInstallNewSoftwareService installNewSoftwareService,
			IProvisioningAgent provisioningAgent) {

		P2TreeTable p2TreeTable = new P2TreeTable(installNewSoftwareService);
		RepositoryLoader repositoryLoader = new RepositoryLoader(p2TreeTable,
				installNewSoftwareService, provisioningAgent);

		LoadExplorRepositoryView loadExplorRepositoryView = new LoadExplorRepositoryView(
				repositoryLoader, p2TreeTable);

		ctx.set(IRepositoryExplorer.class, p2TreeTable);
		ctx.set(IRepositoryLoader.class, repositoryLoader);
		ctx.set(ILoadExplorRepoistory.class, loadExplorRepositoryView);
	}
}
