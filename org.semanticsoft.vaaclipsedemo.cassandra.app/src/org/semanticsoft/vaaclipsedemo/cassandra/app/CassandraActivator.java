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

package org.semanticsoft.vaaclipsedemo.cassandra.app;

import org.semanticsoft.vaaclipsedemo.cassandra.app.user.UserCounter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class CassandraActivator implements BundleActivator
{
	private static CassandraActivator instance;

	private BundleContext context;
	private File cassandraHome;
	private File srcStore;
	
	private UserCounter userCounter;
	private Date startTime;

	public void start(BundleContext context) throws Exception
	{
		instance = this;
		this.context = context;

		upackProjects();
		
		this.userCounter = new UserCounter();
		this.startTime = new Date();
	}

	private void upackProjects() throws Exception
	{
		cassandraHome = FileUtils.getFile(FileUtils.getUserDirectory(), "/.cassandra");

		if (!cassandraHome.isFile()) // prevent that there are user file with this name
		{
			if (!cassandraHome.exists())
				cassandraHome.mkdir();
			
			Bundle resourcesBundle = Platform.getBundle("org.semanticsoft.vaaclipsedemo.cassandra.app");
			
			//create the src root with unique name (to prevent the intersection with the user data in case he have own folder .cassandra in user home)
			File srcRoot = FileUtils.getFile(cassandraHome, "63048fd1-69d0-4eb8-be75-bb33964f821c");
			String version = resourcesBundle.getVersion().toString();
			srcStore = FileUtils.getFile(srcRoot, version);
			
			if (!srcStore.exists())
			{
				srcStore.mkdir();
				
				URL url = resourcesBundle.getEntry("data/cassandra.zip");
				InputStream inputStream = url.openConnection().getInputStream();
				try {
					extractFolder(srcStore, inputStream);
				}
				finally {
					inputStream.close();
				}
			}
		}
		else
			throw new RuntimeException("There is the file with name .cassandra in user home");
	}

	static public void extractFolder(File destPath, InputStream inputStream) throws ZipException, IOException
	{
		int BUFFER = 2048;
		ZipInputStream zis = new ZipInputStream(new BufferedInputStream(inputStream));

		ZipEntry entry;
		// Process each entry
		while ((entry = zis.getNextEntry()) != null)
		{
			// grab a zip file entry
			String currentEntry = entry.getName();
			File destCatalog = new File(destPath, currentEntry);
			// destFile = new File(newPath, destFile.getName());
			File destinationParent = destCatalog.getParentFile();

			// create the parent directory structure if needed
			destinationParent.mkdirs();

			if (!entry.isDirectory())
			{
				int count;
				byte data[] = new byte[BUFFER];
				// write the files to the disk
				FileOutputStream fos = new FileOutputStream(destCatalog);
				BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = zis.read(data, 0, BUFFER)) != -1)
				{
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
			}
		}
	}

	public void stop(BundleContext context) throws Exception
	{
		this.context = null;
		instance = null;
	}

	public static CassandraActivator getInstance()
	{
		return instance;
	}

	public BundleContext getBundleContext()
	{
		return context;
	}

	public File getHomeDirectory()
	{
		return cassandraHome;
	}
	
	public File getSrcStore()
	{
		return srcStore;
	}
	
	public UserCounter getUserCounter()
	{
		return userCounter;
	}
	
	public Date getStartTime()
	{
		return startTime;
	}
}
