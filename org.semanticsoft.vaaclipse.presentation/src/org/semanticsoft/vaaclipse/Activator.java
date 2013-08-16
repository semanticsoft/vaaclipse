/**
 * 
 */
package org.semanticsoft.vaaclipse;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author rushan
 *
 */
public class Activator implements BundleActivator
{
	private static Activator instance;

	private BundleContext context;

	public void start(BundleContext context) throws Exception
	{
		instance = this;
		this.context = context;
	}
	
	public BundleContext getContext() {
		return context;
	}
	
	public static Activator getInstance() {
		return instance;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
