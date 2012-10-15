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

