/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.lunifera.vaaclipse.ui.preferences.addon.internal.impexp.ExportPreferences;

/**
 * @author rushan
 *
 */
public class ExportPreferencesHandler {
	
	@CanExecute
	public boolean canExecute(@Optional PreferencesAuthorization prefAuthService) {
		boolean isAllowed = true;
		if (prefAuthService != null) {
			isAllowed = prefAuthService.exportAllowed();
		}
		return isAllowed;
	}

	@Execute
	public void execute(@Optional PreferencesAuthorization prefAuthService, IEclipseContext context) {
		if (!canExecute(prefAuthService))
			return;
		
		ContextInjectionFactory.make(ExportPreferences.class, context);
	}
	
}
