package org.semanticsoft.vaaclipse.p2.install.ui;

import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.semanticsoft.vaaclipse.p2.iservice.IInstallNewSoftwareService;

public interface IRepositoryLoader extends IBasicUI {

	IRepositoryExplorer getiRepositoryExplorer();

	void setiRepositoryExplorer(IRepositoryExplorer iRepositoryExplorer);

	

	void setProvisioningAgent(IProvisioningAgent provisioningAgent);

	void setInstallService(IInstallNewSoftwareService installService);

}
