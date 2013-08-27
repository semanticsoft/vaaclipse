package org.semanticsoft.vaaclipse.p2.install.ui;

import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.semanticsoft.vaaclipse.p2.iservice.IInstallNewSoftwareService;

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
public interface IRepositoryLoader extends IBasicUI {

	IRepositoryExplorer getiRepositoryExplorer();

	void setiRepositoryExplorer(IRepositoryExplorer iRepositoryExplorer);

	

	void setProvisioningAgent(IProvisioningAgent provisioningAgent);

	void setInstallService(IInstallNewSoftwareService installService);

}
