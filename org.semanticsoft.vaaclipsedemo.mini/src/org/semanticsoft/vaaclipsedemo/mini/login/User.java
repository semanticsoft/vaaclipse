/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mini.login;

/**
 * @author rushan
 *
 */
public class User
{
	private String name;
	private String passwordHash;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getPasswordHash()
	{
		return passwordHash;
	}
	
	public void setPasswordHash(String passwordHash)
	{
		this.passwordHash = passwordHash;
	}
}
