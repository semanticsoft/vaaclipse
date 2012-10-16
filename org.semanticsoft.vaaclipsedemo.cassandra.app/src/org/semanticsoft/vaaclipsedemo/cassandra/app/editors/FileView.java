/*******************************************************************************
 * Copyright (c) 2012 Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *******************************************************************************/

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
