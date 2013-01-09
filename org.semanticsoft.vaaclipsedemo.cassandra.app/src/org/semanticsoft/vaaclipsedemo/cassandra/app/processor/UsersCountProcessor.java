/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.cassandra.app.processor;

import org.semanticsoft.vaaclipsedemo.cassandra.app.CassandraActivator;

import org.eclipse.e4.core.di.annotations.Execute;

/**
 * @author rushan
 *
 */
public class UsersCountProcessor
{
	@Execute
	public void incUserCount()
	{
		CassandraActivator.getInstance().getUserCounter().increment();
	}
}
