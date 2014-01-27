/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;

import e4modelextension.VaaclipseApplication;

/**
 * @author rushan
 *
 */
public class PreferencesAddon {

	@Inject
	MApplication app;
	
	@PostConstruct
	void init() {
		VaaclipseApplication vaaApp = (VaaclipseApplication) app;
		
//		for (PreferencesPage page : vaaApp.getPreferencesPages()) {
//			String scope = page.getPreferencesScope();
//			String absPath = "configuration/org.eclipse.core.runtime.preferences.O"
//		}
	}
	
}
