package org.semanticsoft.vaaclipse.p2.install.ui;

import java.util.List;

import org.eclipse.equinox.p2.metadata.IInstallableUnit;

public interface IRepositoryExplorer extends IBasicUI {

	void fill(List<IInstallableUnit> iInstallableUnits);

	void addRootItem(IInstallableUnit iInstallableUnit);

	void addRoottems(List<IInstallableUnit> iInstallableUnits);

	void addSubItems(List<IInstallableUnit> childIInstallableUnits,
			IInstallableUnit root);

	void addSubItem(IInstallableUnit childIInstallableUnit,
			IInstallableUnit root);

}
