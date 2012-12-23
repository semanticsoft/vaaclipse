/**
 * 
 */
package org.semanticsoft.vaaclipse.publicapi.theme;

/**
 * @author rushan
 *
 */
public interface ThemeEngine
{
	Theme getTheme(String themeId);
	Theme getThemeByWebId(String themeWebId);
	ThemeContribution getThemeContributionByWebId(String contributionWebId);
}
