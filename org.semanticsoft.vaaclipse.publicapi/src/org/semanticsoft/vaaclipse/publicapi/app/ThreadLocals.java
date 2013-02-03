/**
 * 
 */
package org.semanticsoft.vaaclipse.publicapi.app;

import org.eclipse.e4.core.contexts.IEclipseContext;

/**
 * @author rushan
 *
 */
public class ThreadLocals
{
	private static final ThreadLocal<IEclipseContext> eclipseContext = new ThreadLocal<>();
	
	public static IEclipseContext getRootContext()
	{
		return eclipseContext.get();
	}
	
	public static void setRootContext(IEclipseContext context)
	{
		eclipseContext.set(context);
	}
	
}
