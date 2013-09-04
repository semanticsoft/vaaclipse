package org.semanticsoft.vaaclipse.p2.iservice;

import java.net.URI;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
/*******************************************************************************
 * Copyright (c) 2012 Klevis Ramo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Klevis Ramo - initial API and implementation
 *******************************************************************************/
public interface ISitesManager {

	List<URI>  listAllUpdateSites(IProvisioningAgent agent);

	IStatus validate(IProvisioningAgent agent, String uri);

	void addRepository(String uri, IProvisioningAgent agent);

	void removeRepository(String uri, IProvisioningAgent agent);

	String getReposiotoryName(IProvisioningAgent agent, URI uri);

	boolean isRepositoryEnabled(IProvisioningAgent agent, URI uri);

	void setRepositoryEnabled(IProvisioningAgent agent, URI uri, boolean enable);

	String getReposiotoryNickName(IProvisioningAgent agent, URI uri);

	void setReposiotoryNickName(IProvisioningAgent agent, URI uri,
			String nickName);

	void addRepository(String uri, IProvisioningAgent agent, String nickName);

}
