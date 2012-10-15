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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.datalocation.Location;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

public class BundleActivatorImpl implements BundleActivator
{

	private static final String F_META_AREA = ".metadata"; //$NON-NLS-1$
	private static final String F_PLUGIN_DATA = ".plugins"; //$NON-NLS-1$

	private static BundleActivatorImpl instance;

	private BundleContext context;

	private ServiceTracker locationServiceTracker;

	private IPath stateLocation;
	
	private File cassandraHome;

	public void start(BundleContext context) throws Exception
	{
		instance = this;
		this.context = context;

		upackProjects();
	}

	private void upackProjects()
	{
		String userHome = System.getProperty("user.home");
		cassandraHome = new File(userHome + File.separator + "/.cassandra");

		if (!cassandraHome.isFile()) // prevent that there are user file with this name
		{
			if (!cassandraHome.exists())
			{
				cassandraHome.mkdir();
				
				try {
					Bundle resourcesBundle = Platform.getBundle("org.semanticsoft.vaaclipsedemo.cassandra.resources");
					URL url = resourcesBundle.getEntry("src/cassandra.zip");
					//URL url = new URL("platform:/plugin/org.semanticsoft.vaaclipsedemo.cassandra.resources/src/cassandra.zip");
				    InputStream inputStream = url.openConnection().getInputStream();
				    extractFolder(cassandraHome, inputStream);
				     inputStream.close();
				} catch (Exception e) {
				    e.printStackTrace();
				}
			}
		}
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
	        //destFile = new File(newPath, destFile.getName());
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

	public IPath getStateLocation()
	{
		try
		{
			if (stateLocation == null)
			{
				Filter filter = context.createFilter(Location.INSTANCE_FILTER);
				if (locationServiceTracker == null)
				{
					locationServiceTracker = new ServiceTracker(context, filter, null);
					locationServiceTracker.open();
				}
				Location location = (Location) locationServiceTracker.getService();
				if (location != null)
				{
					IPath path = new Path(location.getURL().getPath());
					stateLocation = path.append(F_META_AREA).append(F_PLUGIN_DATA)
							.append(context.getBundle().getSymbolicName());
					stateLocation.toFile().mkdirs();
				}
			}
		}
		catch (InvalidSyntaxException e)
		{
			// ignore this. It should never happen as we have tested the above
			// format.
		}
		return stateLocation;
	}

	public static BundleActivatorImpl getInstance()
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
}
