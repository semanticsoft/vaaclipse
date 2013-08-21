package org.semanticsoft.vaaclipse.p2.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
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
	NullProgressMonitor nullProgressMonitor;

	URI uri;

	IProvisioningAgent agent;
	IMetadataRepository loadRepository = null;

	@Override
	public List<IInstallableUnit> loadRepository(String uriString,
			IProvisioningAgent agent) {

		nullProgressMonitor = new NullProgressMonitor();
		this.agent = agent;
		try {
			uri = new URI(uriString);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		MetadataRepositoryManager metadataRepositoryManager = new MetadataRepositoryManager(
				agent);

		try {
			loadRepository = metadataRepositoryManager.loadRepository(uri, 0,
					nullProgressMonitor);
		} catch (ProvisionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		IQuery<IInstallableUnit> createQuery = QueryUtil
				.createIUCategoryQuery();

		IQueryResult<IInstallableUnit> query = loadRepository.query(
				createQuery, nullProgressMonitor);
		List<IInstallableUnit> list = toList(query);

		return list;

	}

	private List<IInstallableUnit> toList(IQueryResult<IInstallableUnit> query) {
		List<IInstallableUnit> list = new ArrayList<IInstallableUnit>();
		for (IInstallableUnit iInstallableUnit : query) {

			System.out.println(iInstallableUnit);
			list.add(iInstallableUnit);

		}
		return list;
	}

	@Override
	public boolean isCategory(IInstallableUnit installableUnit) {

		return QueryUtil.isCategory(installableUnit);
	}

	@Override
	public List<IInstallableUnit> extractFromCategory(IInstallableUnit category) {

		IQuery<IInstallableUnit> createIUCategoryMemberQuery = QueryUtil
				.createIUCategoryMemberQuery(category);

		IQueryResult<IInstallableUnit> query = loadRepository.query(
				createIUCategoryMemberQuery, nullProgressMonitor);

		return toList(query);
	}

	@Override
	public String validate(List<IInstallableUnit> listIInstallableUnits) {
		final ProvisioningSession session = new ProvisioningSession(agent);
		InstallOperation installOperation = new InstallOperation(session,
				listIInstallableUnits);

		installOperation.getProvisioningContext().setArtifactRepositories(
				new URI[] { uri });
		installOperation.getProvisioningContext().setMetadataRepositories(
				new URI[] { uri });

		IStatus resolveModal = installOperation
				.resolveModal(new NullProgressMonitor());

		String resolutionDetails = installOperation.getResolutionDetails();
		return resolutionDetails;

	}

	@Override
	public String installNewSoftware(
			List<IInstallableUnit> listIInstallableUnits) {

		if (uri == null || agent == null || nullProgressMonitor == null) {

			throw new IllegalArgumentException(
					"Must first call method laod repository");
		}
		List<IInstallableUnit> listFinalToInstall = new ArrayList<>();
		for (IInstallableUnit iInstallableUnit : listIInstallableUnits) {

			if (!QueryUtil.isGroup(iInstallableUnit)) {
				listFinalToInstall.add(iInstallableUnit);
			}
		}

		listFinalToInstall.addAll(getUpdatedGroups());

		listIInstallableUnits = listFinalToInstall;

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

			String resolutionDetails = installOperation.getResolutionDetails();

			if (!resolveModal.isOK()) {
				return resolutionDetails;
			}
			if (resolveModal.getSeverity() == IStatus.ERROR) {
				return resolutionDetails;
			}

			if (resolveModal.getCode() == IStatus.ERROR) {

				return resolutionDetails;
			} else if (resolveModal.getCode() == IStatus.WARNING) {
				return resolutionDetails;
			} else if (resolveModal.getCode() == IStatus.CANCEL) {
				return resolutionDetails;
			} else if (resolveModal.getCode() == IStatus.INFO) {
				return resolutionDetails;
			}

			ProvisioningJob provisioningJob = installOperation
					.getProvisioningJob(null);

			provisioningJob.addJobChangeListener(new JobChangeAdapter() {

				@Override
				public void scheduled(IJobChangeEvent event) {
					// TODO Auto-generated method stub

					super.scheduled(event);
				}

				@Override
				public void sleeping(IJobChangeEvent event) {
					// TODO Auto-generated method stub

					super.sleeping(event);
				}

				@Override
				public void aboutToRun(IJobChangeEvent event) {
					// TODO Auto-generated method stub

					super.aboutToRun(event);

				}

				@Override
				public void running(IJobChangeEvent event) {
					// TODO Auto-generated method stub

					super.running(event);
				}

				@Override
				public void done(IJobChangeEvent event) {
					// TODO Auto-generated method stub

					super.done(event);
				}

			});

			IStatus run = provisioningJob.runModal(nullProgressMonitor);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

			throw e;
		}

		return "Wait a minute and see to plugins folder if jar was added than restart application";
	}

	private List<IInstallableUnit> getUpdatedGroups() {
		nullProgressMonitor = new NullProgressMonitor();
		this.agent = agent;

		MetadataRepositoryManager metadataRepositoryManager = new MetadataRepositoryManager(
				agent);

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
		List<IInstallableUnit> list = toList(query);
		return list;
	}

	@Override
	public String loadAndInstallNewSoftware(String uriString,
			IProvisioningAgent agent) {

		return installNewSoftware(loadRepository(uriString, agent));
	}

}
