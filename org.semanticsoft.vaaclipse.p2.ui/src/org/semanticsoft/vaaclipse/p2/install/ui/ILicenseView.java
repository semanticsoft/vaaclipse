package org.semanticsoft.vaaclipse.p2.install.ui;

import java.util.List;

import org.eclipse.equinox.p2.metadata.IInstallableUnit;

public interface ILicenseView extends IBasicUI {

	
public void addRepositories(List<IInstallableUnit> list)	;
	
List<IInstallableUnit> getRepos();
}
