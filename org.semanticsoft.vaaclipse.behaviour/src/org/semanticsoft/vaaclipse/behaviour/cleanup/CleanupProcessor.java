package org.semanticsoft.vaaclipse.behaviour.cleanup;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MAddon;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.impl.ApplicationFactoryImpl;

public class CleanupProcessor {
	@Execute
	void addCleanupAddon(MApplication app) {
		List<MAddon> addons = app.getAddons();

		// Prevent multiple copies
		for (MAddon addon : addons) {
			if (addon.getContributionURI().contains("org.semanticsoft.vaaclipse.behaviour.CleanupAddon"))
				return;
		}

		// Insert the addon into the system
		MAddon cleanupAddon = ApplicationFactoryImpl.eINSTANCE.createAddon();
		cleanupAddon.setElementId("CleanupAddon"); //$NON-NLS-1$
		cleanupAddon
				.setContributionURI("bundleclass://org.semanticsoft.vaaclipse.behaviour/org.semanticsoft.vaaclipse.behaviour.cleanup.CleanupAddon"); //$NON-NLS-1$
		app.getAddons().add(cleanupAddon);
		
		System.out.println("Cleanup processor is started");
	}
}
