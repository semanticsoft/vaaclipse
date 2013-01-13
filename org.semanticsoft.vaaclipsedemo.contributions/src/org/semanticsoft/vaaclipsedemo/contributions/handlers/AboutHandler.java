package org.semanticsoft.vaaclipsedemo.contributions.handlers;

import org.eclipse.e4.core.di.annotations.Execute;

import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

public class AboutHandler {
	@Execute
	public void execute(UI vaadinUi) {
		Notification.show("Vaaclipse Contribution Demo");
	}
}
