/**
 * 
 */
package org.semanticsoft.vaaclipse.publicapi.editor;

/**
 * @author rushan
 *
 */
public class SavePromptSetup
{
	private String caption;
	private String message;
	private String iconUri;
	
	public String getCaption()
	{
		return caption;
	}
	
	public void setCaption(String caption)
	{
		this.caption = caption;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	public String getIconUri()
	{
		return iconUri;
	}
	
	public void setIconUri(String iconUri)
	{
		this.iconUri = iconUri;
	}
}
