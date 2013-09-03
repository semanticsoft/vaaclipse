package org.semanticsoft.vaaclipse.p2.processor;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.semanticsoft.vaaclipse.p2.iservice.ISitesManager;
import org.semanticsoft.vaaclipse.p2.sites.ui.ISitesView;
import org.semanticsoft.vaaclipse.p2.sites.ui.impl.SitesView;

public class P2SitesProcessor {

	@Execute
	public void setUp(IEclipseContext ctx, IProvisioningAgent agent,
			ISitesManager sitesManager) {

		ISitesView sitesView = new SitesView(agent, sitesManager);

		ctx.set(ISitesView.class, sitesView);

	}
}
