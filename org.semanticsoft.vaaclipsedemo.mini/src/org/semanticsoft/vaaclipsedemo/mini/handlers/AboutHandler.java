package org.semanticsoft.vaaclipsedemo.mini.handlers;

import org.eclipse.e4.core.di.annotations.Execute;

import com.vaadin.ui.Notification;

public class AboutHandler {
	@Execute
	public void execute() {
		Notification.show("Vaaclipse Mini Application");
	}
}
