package org.semanticsoft.vaaclipsedemo.mediaplayer.model;

/**
 * @author rushan
 *
 */
public class Media extends MediaEntry
{
	private String uri;
	private String description;
	
	public String getUri()
	{
		if (uri == null)
			return "";
		return uri;
	}
	
	public void setUri(String uri)
	{
		this.uri = uri;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Media)
		{
			Media media = (Media)obj;
			
			return media.getUri().equals(this.getUri());
		}
		else
			return false;
	}
	
	@Override
	public int hashCode()
	{
		return this.getUri().hashCode();
	}
}
