/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers.theme;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.semanticsoft.vaaclipse.publicapi.theme.ThemeManager;

/**
 * @author rushan
 *
 */
public class ChangeThemeHandler
{
	@Execute
	public void changeTheme(ThemeManager themeManager, @Named("theme") String theme)
	{
		if (theme != null)
			themeManager.setTheme(theme);
	}
}
