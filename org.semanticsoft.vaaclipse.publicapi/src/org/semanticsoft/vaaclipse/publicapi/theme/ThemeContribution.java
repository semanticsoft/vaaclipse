/**
 * 
 */
package org.semanticsoft.vaaclipse.publicapi.theme;

/**
 * @author rushan
 *
 */
public interface ThemeContribution extends ThemeEntry
{
	public static final String MAIN_CSS = "MAIN_CSS";
	public static final String INHERITED_IMPORTS = "INHERITED_IMPORTS";
	
	String getInsertPosition();
}
