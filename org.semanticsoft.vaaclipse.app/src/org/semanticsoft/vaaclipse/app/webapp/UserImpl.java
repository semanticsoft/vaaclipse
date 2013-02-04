package org.semanticsoft.vaaclipse.app.webapp;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.semanticsoft.vaaclipse.publicapi.authentication.User;

public class UserImpl implements User
{
	private String name;
	private String persistid;
	
	private Object object;
	
	@Inject
	IEclipseContext context;
	
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

	@Override
	public Object getObject()
	{
		return object;
	}

	@Override
	public <T> void setObject(T object, Class<? super T> service)
	{
		this.object = object;
		this.context.set(service, object);
	}
}
