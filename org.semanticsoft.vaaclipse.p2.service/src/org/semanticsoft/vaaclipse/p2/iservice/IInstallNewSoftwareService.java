package org.semanticsoft.vaaclipse.p2.iservice;

import java.util.List;

import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;

public interface IInstallNewSoftwareService {

	List<IInstallableUnit> loadRepository(String uriString,
			IProvisioningAgent agent);

	String installNewSoftware(List<IInstallableUnit> listIInstallableUnits);

	String loadAndInstallNewSoftware(String uriString, IProvisioningAgent agent);

	String validate(List<IInstallableUnit> listIInstallableUnits);

	
	
}
