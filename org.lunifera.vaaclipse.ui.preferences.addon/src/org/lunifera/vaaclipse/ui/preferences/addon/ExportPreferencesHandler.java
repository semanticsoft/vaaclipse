/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon;

import javax.inject.Named;

import org.eclipse.core.internal.preferences.PreferencesService;
import org.eclipse.core.runtime.preferences.IPreferencesService;
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
	public boolean canExecute(@Optional PreferencesAuthorization prefAuthService, @Optional @Named(value="username") String userName) {
		boolean isAllowed = true;
		if (prefAuthService != null && userName != null) {
			isAllowed = prefAuthService.exportAllowed(userName);
		}
		return isAllowed;
	}

	@Execute
	public void execute(@Optional PreferencesAuthorization prefAuthService, @Optional @Named(value="username") String userName, IEclipseContext context) {
		if (!canExecute(prefAuthService, userName))
			return;
		
		ContextInjectionFactory.make(ExportPreferences.class, context);
	}
	
}
