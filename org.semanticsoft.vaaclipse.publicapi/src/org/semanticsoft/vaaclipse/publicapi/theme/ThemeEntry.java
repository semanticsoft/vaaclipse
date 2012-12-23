/**
 * 
 */
package org.semanticsoft.vaaclipse.publicapi.theme;

import java.util.List;

/**
 * @author rushan
 *
 */
public interface ThemeEntry
{
	/**
	 * Theme id
	 */
	String getId();
	
	/**
	 * Web id - this id is in uri (replace all dot's, etc)
	 */
	String getWebId();
	
	String getCssUri();
	List<String> getResourceLocationURIs();
}
