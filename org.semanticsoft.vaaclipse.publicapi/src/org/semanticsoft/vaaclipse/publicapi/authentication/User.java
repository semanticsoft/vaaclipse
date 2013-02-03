/**
 * 
 */
package org.semanticsoft.vaaclipse.publicapi.authentication;

/**
 * @author rushan
 *
 */
public interface User
{
	String getName();
	void setName(String name);
	
	String getPersistid();
	void setPersistid(String persistid);
}
