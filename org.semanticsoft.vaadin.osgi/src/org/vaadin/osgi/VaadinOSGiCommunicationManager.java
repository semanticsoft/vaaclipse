package org.vaadin.osgi;

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
		VaadinOSGiApplicationManager.getInstance().setCommunicationManager(this);
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
