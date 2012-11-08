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

package org.semanticsoft.vaaclipse.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.vaadin.Application;
import com.vaadin.terminal.VariableOwner;
import com.vaadin.terminal.gwt.server.CommunicationManager;

public class VaadinOSGiCommunicationManager extends CommunicationManager
{
	Queue<Runnable> runnables = new LinkedList<>();
	
	public VaadinOSGiCommunicationManager(Application application)
	{
		super(application);
	}
	
	protected void changeVariables(Object source, final VariableOwner owner, Map<String, Object> m) {
        super.changeVariables(source, owner, m);
        
        exec();
    }
	
	private synchronized void exec()
	{
		Runnable runnable;
    	while ((runnable = runnables.poll()) != null)
        {
        	try
			{
        		runnable.run();
			}
			catch (Throwable e)
			{
				e.printStackTrace();
			}
        }
	}

	public synchronized void invokeLater(Runnable runnable)
	{
		this.runnables.add(runnable);
	}
	
	
}
