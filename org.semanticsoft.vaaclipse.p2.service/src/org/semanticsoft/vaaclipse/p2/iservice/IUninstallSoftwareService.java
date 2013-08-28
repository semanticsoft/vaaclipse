package org.semanticsoft.vaaclipse.p2.iservice;

import java.util.List;

import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
/*******************************************************************************
 * Copyright (c) 2012 Klevis Ramo and others. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Klevis Ramo - initial API and implementation
 *******************************************************************************/
public interface IUninstallSoftwareService {
	int GROUP = 0;
	int CATEGORY = 1;
	int ANY = 2;

	List<IInstallableUnit> listInstalledSoftware(IProvisioningAgent agen, int i);

	String uninstallSelected(List<IInstallableUnit> listToUninstall);

}
 