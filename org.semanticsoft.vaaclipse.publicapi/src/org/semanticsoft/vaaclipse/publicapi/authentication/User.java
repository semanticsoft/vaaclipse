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
	
	Object getObject();
	<T> void setObject(T object, Class<? super T> service);
}
