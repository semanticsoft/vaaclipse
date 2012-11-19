/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.model;

/**
 * @author rushan
 *
 */
public class MediaEntry
{
	protected String name = "NoName";
	protected MediaCategory parent;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public MediaCategory getParent()
	{
		return parent;
	}
	
	void setParent(MediaCategory parent)
	{
		this.parent = parent;
	}
}
