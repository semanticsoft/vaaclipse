/**
 * 
 */
package org.semanticsoft.vaaclipse.publicapi.authentication;

/**
 * @author rushan
 *
 */
public class User
{
	private String name;
	private String persistid;
	
	public User()
	{
		
	}
	
	public User(String name)
	{
		setName(name);
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getPersistid()
	{
		if (this.persistid == null)
			return this.name;
		return persistid;
	}
	
	public void setPersistid(String persistid)
	{
		this.persistid = persistid;
	}
}
