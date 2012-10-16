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
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 
 * @author rushan
 *
 */
public class FileUtils
{
	public static String readFile(File file, String encoding) throws IOException
	{
		FileInputStream input = new FileInputStream(file);
		
		byte[] fileData = new byte[input.available()];

		input.read(fileData);
		input.close();
		return new String(fileData, encoding);
	}
}

