package org.semanticsoft.vaaclipse.p2.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.equinox.internal.p2.metadata.repository.MetadataRepositoryManager;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.operations.InstallOperation;
import org.eclipse.equinox.p2.operations.ProvisioningJob;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.query.IQuery;
import org.eclipse.equinox.p2.query.IQueryResult;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.semanticsoft.vaaclipse.p2.iservice.IInstallNewSoftwareService;

public class InstallNewSoftwareService implements IInstallNewSoftwareService {
	NullProgressMonitor nullProgressMonitor = new NullProgressMonitor();

	URI uri;

	IProvisioningAgent agent;

	@Override
	public List<IInstallableUnit> loadRepository(String uriString,
			IProvisioningAgent agent) {

		this.agent = agent;
		try {
			uri = new URI(uriString);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		MetadataRepositoryManager metadataRepositoryManager = new MetadataRepositoryManager(
				agent);
		IMetadataRepository loadRepository = null;
		try {
			loadRepository = metadataRepositoryManager.loadRepository(uri, 0,
					nullProgressMonitor);
		} catch (ProvisionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		IQuery<IInstallableUnit> createQuery = QueryUtil.createIUGroupQuery();
		IQueryResult<IInstallableUnit> query = loadRepository.query(
				createQuery, nullProgressMonitor);
		List<IInstallableUnit> list = new ArrayList<IInstallableUnit>();
		for (IInstallableUnit iInstallableUnit : query) {

			list.add(iInstallableUnit);

		}

		return list;

	}

	@Override
	public String installNewSoftware(
			List<IInstallableUnit> listIInstallableUnits) {

		if (agent == null || listIInstallableUnits == null || uri == null) {

			throw new IllegalArgumentException(
					"You must first call load repository");
		}
		String resolutionDetails = "";
		try {
			final ProvisioningSession session = new ProvisioningSession(agent);
			InstallOperation installOperation = new InstallOperation(session,
					listIInstallableUnits);

			installOperation.getProvisioningContext().setArtifactRepositories(
					new URI[] { uri });
			installOperation.getProvisioningContext().setMetadataRepositories(
					new URI[] { uri });

			IStatus resolveModal = installOperation
					.resolveModal(nullProgressMonitor);

			resolutionDetails = installOperation.getResolutionDetails();
			
			ProvisioningJob provisioningJob = installOperation
					.getProvisioningJob(null);
			
			provisioningJob.runModal(nullProgressMonitor);
		} catch (IllegalArgumentException runtimeException) {
			throw runtimeException;
		}
		return resolutionDetails;
	}

	@Override
	public String loadAndInstallNewSoftware(String uriString,
			IProvisioningAgent agent) {

		return installNewSoftware(loadRepository(uriString, agent));
	}

}
