/*******************************************************************************
 * Copyright (c) 2011 Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *******************************************************************************/

package org.semanticsoft.vaaclipse.app;

import java.util.concurrent.ArrayBlockingQueue;

import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.internal.workbench.WorkbenchLogger;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.osgi.service.datalocation.Location;

@SuppressWarnings("restriction")
public class VaadinE4Application implements IApplication {
	
	private ArrayBlockingQueue<String> queue;
	private Logger logger = new WorkbenchLogger("org.semanticsoft.vaaclipse.app");
	
	private IApplicationContext appContext;
	
	private static VaadinE4Application instance;
	
	public static VaadinE4Application getInstance()
	{
		return instance;
	}
	
	public Location getInstanceLocation()
	{
		 return Activator.getDefault().getInstanceLocation();
	}
	
	public IApplicationContext getAppContext()
	{
		return appContext;
	}
	
	public Logger getLogger()
	{
		return logger;
	}
	
//	private Object monitor = new Object();

	@Override
	public Object start(IApplicationContext context) throws Exception {
		instance = this;
		appContext = context;
		
		logger.debug("VaadinE4Application.start()");
		context.applicationRunning();
		
		queue = new ArrayBlockingQueue<>(10);
		
		for (;;)
		{
			String msg = queue.poll();
			if (msg != null)
			{
				if (msg.equals("exit"))
					break;
			}
		}
		
		return EXIT_OK;
	}

	@Override
	public void stop() {
		// will never be invoked
	}

}
