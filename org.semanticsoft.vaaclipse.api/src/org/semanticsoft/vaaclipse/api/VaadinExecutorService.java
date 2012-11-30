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
	void invokeLaterAlways(Runnable runnable);
	void removeAlwaysRunnable(Runnable runnable);
}
