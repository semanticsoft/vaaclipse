/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.cassandra.app.handlers;

import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import java.text.SimpleDateFormat;
import org.eclipse.e4.core.di.annotations.Execute;
import org.semanticsoft.vaaclipsedemo.cassandra.app.CassandraActivator;
import org.semanticsoft.vaadin.optiondialog.OptionDialog;

/**
 * @author rushan
 *
 */
public class UsersCountHandler
{
	@Execute
	public void showLoggedUsersCount(UI ui)
	{
		int usersCount = CassandraActivator.getInstance().getUserCounter().getValue();
		String startTime = new SimpleDateFormat("dd.MM.yyyy HH:mm z").format(CassandraActivator.getInstance().getStartTime());
		
		OptionDialog.show(ui, "Total Logged Users Count", String.format("%s users was logged to Cassandra since application has started at %s", usersCount, startTime), new String[] {"OK"}, 500, 100, Component.UNITS_PIXELS, OptionDialog.CLOSE_LISTENER);
	}
}
