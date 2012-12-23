/**
 * 
 */
package org.semanticsoft.vaaclipse.theme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author rushan
 *
 */
public class ThemeEntryImpl
{
	protected String id;
	protected String webId;
	protected String cssUri;
	protected List<String> resourceUri = new ArrayList<>();
	
	public ThemeEntryImpl(String id)
	{
		this.id = id;
		this.webId = this.id.replaceAll("\\.", "-");
	}

	public String getId()
	{
		return id;
	}
	
	public String getWebId()
	{
		return webId;
	}
	
	public String getCssUri()
	{
		return cssUri;
	}
	
	public void setCssUri(String cssUri)
	{
		this.cssUri = cssUri;
	}
	
	public List<String> getResourceLocationURIs()
	{
		return Collections.unmodifiableList(this.resourceUri);
	}
	
	public void addResourceUri(String resourceUri)
	{
		resourceUri = processUri(resourceUri);
		this.resourceUri.add(resourceUri);
	}
	
	private String processUri(String uri)
	{
		if (uri == null)
			return null;
		
		uri = uri.trim();
		if (uri.length() > 0)
		{
			char last = uri.charAt(uri.length() - 1);
			if (last != '/')
			{
				uri += "/";
			}
		}
		return uri;
	}
}
