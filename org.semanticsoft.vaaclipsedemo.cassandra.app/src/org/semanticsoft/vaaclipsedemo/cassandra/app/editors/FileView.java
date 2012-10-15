/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.cassandra.app.editors;

import java.io.File;

/**
 * @author rushan
 *
 */
public abstract class FileView
{
	protected String inputURI;
	protected File file;
	
	protected FileView(String inputURI)
	{
		this.inputURI = inputURI;
	}


	public File getFile()
	{
		if (inputURI == null)
			return null;
		if (file == null)
			file = new File(inputURI);
		return file;
	}
	
	public void setContentDescriptor(String contentDescriptor)
	{
		this.inputURI = (String) contentDescriptor;
	}
}
