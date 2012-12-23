package org.semanticsoft.vaaclipse.publicapi.theme;

/**
 * @author rushan
 *
 */
public interface ThemeManager
{
	String getThemeId();
	Theme getTheme();
	void setTheme(String themeId);
	ThemeEngine getThemeEngine();
}
