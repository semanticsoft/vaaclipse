/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.cassandra.app.handlers;

import com.vaadin.ui.UI;

import com.vaadin.ui.Component;
import com.vaadin.ui.Window;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.semanticsoft.vaadin.optiondialog.OptionDialog;

/**
 * @author rushan
 *
 */
public class AboutHandler
{
	@Execute
	public void about(UI ui)
	{
		OptionDialog.show(ui, "About", String.format("Cassandra - demo application for Vaaclipse Framework"), new String[] {"OK"}, 500, 150, Component.UNITS_PIXELS, OptionDialog.CLOSE_LISTENER);
	}
}
