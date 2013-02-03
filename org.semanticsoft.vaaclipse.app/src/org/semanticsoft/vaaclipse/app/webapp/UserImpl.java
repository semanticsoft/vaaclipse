package org.semanticsoft.vaaclipse.app.webapp;

import org.semanticsoft.vaaclipse.publicapi.authentication.User;

public class UserImpl implements User
{
	private String name;
	private String persistid;
	
	public UserImpl()
	{
		setName("guest");
	}
	
	public UserImpl(String name)
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
