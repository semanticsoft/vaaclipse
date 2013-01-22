/**
 * 
 */
package org.semanticsoft.vaaclipse.api;

/**
 * @author rushan
 *
 */
public interface VaadinExecutorService
{
	void invokeLater(Runnable runnable);
	void invokeLater(Object key, Runnable runnable);
	void removeAllInvokeLater();
	boolean containsKey(Object key);
	void invokeLaterAlways(Runnable runnable);
	void removeAlwaysRunnable(Runnable runnable);
	void removeAllAlwaysRunnables();
}
