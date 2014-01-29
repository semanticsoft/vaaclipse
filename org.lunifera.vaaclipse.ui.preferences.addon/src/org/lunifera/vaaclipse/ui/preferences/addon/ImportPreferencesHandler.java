/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.lunifera.vaaclipse.ui.preferences.addon.internal.impexp.ImportPreferences;

/**
 * @author rushan
 *
 */
public class ImportPreferencesHandler {

	@CanExecute
	public boolean canExecute(@Optional PreferencesAuthorization prefAuthService) {
		boolean isAllowed = true;
		if (prefAuthService != null) {
			isAllowed = prefAuthService.importAllowed();
		}
		return isAllowed;
	}

	@Execute
	public void execute(@Optional PreferencesAuthorization prefAuthService, IEclipseContext context) {
		if (!canExecute(prefAuthService))
			return;
		
		ContextInjectionFactory.make(ImportPreferences.class, context);
	}	
}
