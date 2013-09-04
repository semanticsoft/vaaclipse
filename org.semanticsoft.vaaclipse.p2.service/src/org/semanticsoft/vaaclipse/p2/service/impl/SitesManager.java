package org.semanticsoft.vaaclipse.p2.service.impl;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.internal.p2.artifact.repository.ArtifactRepositoryManager;
import org.eclipse.equinox.internal.p2.metadata.repository.MetadataRepositoryManager;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.operations.RepositoryTracker;
import org.eclipse.equinox.p2.repository.IRepository;
import org.eclipse.equinox.p2.repository.IRepositoryManager;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepository;
import org.eclipse.equinox.p2.repository.artifact.spi.ArtifactRepositoryFactory;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.semanticsoft.vaaclipse.p2.iservice.ISitesManager;
import org.semanticsoft.vaaclipse.p2.util.Utils;

/*******************************************************************************
 * Copyright (c) 2012 Klevis Ramo and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Klevis Ramo - initial API and implementation
 *******************************************************************************/
public class SitesManager implements ISitesManager {

	@Override
	public List<URI> listAllUpdateSites(IProvisioningAgent agent) {

		MetadataRepositoryManager metadataRepositoryManager = new MetadataRepositoryManager(
				agent);

		URI[] knownRepositories = metadataRepositoryManager
				.getKnownRepositories(MetadataRepositoryManager.REPOSITORIES_ALL);

		URI[] knownRepositories2 = metadataRepositoryManager
				.getKnownRepositories(MetadataRepositoryManager.REPOSITORIES_DISABLED);

		List<URI> finalRepositories = new ArrayList<URI>();

		Collections.addAll(finalRepositories, knownRepositories);
		Collections.addAll(finalRepositories, knownRepositories2);

		return finalRepositories;
	}

	@Override
	public String getReposiotoryName(IProvisioningAgent agent, URI uri) {
		MetadataRepositoryManager metadataRepositoryManager = new MetadataRepositoryManager(
				agent);

		return metadataRepositoryManager.getRepositoryProperty(uri,
				IRepository.PROP_NAME);
	}

	@Override
	public String getReposiotoryNickName(IProvisioningAgent agent, URI uri) {
		MetadataRepositoryManager metadataRepositoryManager = new MetadataRepositoryManager(
				agent);

		return metadataRepositoryManager.getRepositoryProperty(uri,
				IRepository.PROP_NICKNAME);
	}

	@Override
	public void setReposiotoryNickName(IProvisioningAgent agent, URI uri,
			String nickName) {
		MetadataRepositoryManager metadataRepositoryManager = new MetadataRepositoryManager(
				agent);
		ArtifactRepositoryManager artifactRepositoryManager = new ArtifactRepositoryManager(
				agent);

		metadataRepositoryManager.setRepositoryProperty(uri,
				IRepository.PROP_NICKNAME, nickName);
		artifactRepositoryManager.setRepositoryProperty(uri,
				IRepository.PROP_NICKNAME, nickName);
	}

	@Override
	public boolean isRepositoryEnabled(IProvisioningAgent agent, URI uri) {
		MetadataRepositoryManager metadataRepositoryManager = new MetadataRepositoryManager(
				agent);

		return metadataRepositoryManager.isEnabled(uri);

	}

	@Override
	public void setRepositoryEnabled(IProvisioningAgent agent, URI uri,
			boolean enable) {
		MetadataRepositoryManager metadataRepositoryManager = new MetadataRepositoryManager(
				agent);
		ArtifactRepositoryManager artifactRepositoryManager = new ArtifactRepositoryManager(
				agent);
		metadataRepositoryManager.setEnabled(uri, enable);
		artifactRepositoryManager.setEnabled(uri, enable);

	}

	@Override
	public IStatus validate(IProvisioningAgent agent, String uri) {
		RepositoryTracker repositoryTracker = new RepositoryTracker() {

			@Override
			public void removeRepositories(URI[] locations,
					ProvisioningSession session) {
				// TODO Auto-generated method stub

			}

			@Override
			public void refreshRepositories(URI[] locations,
					ProvisioningSession session, IProgressMonitor monitor) {
				// TODO Auto-generated method stub

			}

			@Override
			public URI[] getKnownRepositories(ProvisioningSession session) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void addRepository(URI location, String nickname,
					ProvisioningSession session) {
				// TODO Auto-generated method stub

			}

			@Override
			public void reportLoadFailure(URI location,
					ProvisionException exception) {
				// TODO Auto-generated method stub
				super.reportLoadFailure(location, exception);

			}

		};
		try {

			List<URI> listAllUpdateSites = listAllUpdateSites(agent);
			for (URI uri2 : listAllUpdateSites) {

				if (uri2.toString().equals(uri)) {
					throw new RuntimeException("Repository alwrady added");
				}
			}
			URI u = null;
			NullProgressMonitor nullProgressMonitor = new NullProgressMonitor();
			IStatus validateRepositoryLocation = repositoryTracker
					.validateRepositoryLocation(new ProvisioningSession(agent),
							new URI(uri), true, new NullProgressMonitor());

			if (validateRepositoryLocation.isOK()) {

				if (!Utils.containsString(uri, "http", false)

				&& !(uri.contains(".jar") || uri.contains(".zip"))) {

					u = new File(uri).toURI();

				}

				if (u == null)
					try {
						u = new URI(uri);
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				MetadataRepositoryManager metadataRepositoryManager = new MetadataRepositoryManager(
						agent);
				IMetadataRepository loadRepository = null;
				try {
					loadRepository = metadataRepositoryManager.loadRepository(
							u, IRepositoryManager.REPOSITORIES_ALL,
							nullProgressMonitor);
				} catch (ProvisionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e.getMessage());
				}
				return Status.OK_STATUS;

			} else
				return validateRepositoryLocation;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Status.OK_STATUS;
	}

	@Override
	public void addRepository(String uri, IProvisioningAgent agent) {
		MetadataRepositoryManager metadataRepositoryManager = new MetadataRepositoryManager(
				agent);
		ArtifactRepositoryManager artifactRepositoryManager = new ArtifactRepositoryManager(
				agent);
		try {

			URI location = new URI(uri);
			metadataRepositoryManager.addRepository(location);
			artifactRepositoryManager.addRepository(location);
			metadataRepositoryManager.setRepositoryProperty(location,
					IRepository.PROP_SYSTEM, Boolean.FALSE.toString());
			artifactRepositoryManager.setRepositoryProperty(location,
					IRepository.PROP_SYSTEM, Boolean.FALSE.toString());
			setReposiotoryNickName(agent, location,
					getReposiotoryName(agent, location));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO: handle exception

			throw e;
		}
	}

	@Override
	public void addRepository(String uri, IProvisioningAgent agent,
			String nickName) {
		MetadataRepositoryManager metadataRepositoryManager = new MetadataRepositoryManager(
				agent);

		ArtifactRepositoryManager artifactRepositoryManager = new ArtifactRepositoryManager(
				agent);
		try {

			URI location = new URI(uri);
			metadataRepositoryManager.addRepository(location);
			artifactRepositoryManager.addRepository(location);
			metadataRepositoryManager.setRepositoryProperty(location,
					IRepository.PROP_SYSTEM, Boolean.FALSE.toString());
			artifactRepositoryManager.setRepositoryProperty(location,
					IRepository.PROP_SYSTEM, Boolean.FALSE.toString());
			setReposiotoryNickName(agent, location, nickName);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO: handle exception

			throw e;
		}

	}

	@Override
	public void removeRepository(String uri, IProvisioningAgent agent) {
		MetadataRepositoryManager metadataRepositoryManager = new MetadataRepositoryManager(
				agent);
		ArtifactRepositoryManager artifactRepositoryManager = new ArtifactRepositoryManager(
				agent);
		try {
			metadataRepositoryManager.removeRepository(new URI(uri));
			artifactRepositoryManager.removeRepository(new URI(uri));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO: handle exception

			throw e;
		}
	}
}
