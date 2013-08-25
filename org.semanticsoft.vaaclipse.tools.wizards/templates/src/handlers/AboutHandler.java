package @@handlersPackageName@@;

import org.eclipse.e4.core.di.annotations.Execute;

import com.vaadin.Application;

public class AboutHandler {
	@Execute
	public void execute(Application vaadinApp) {
		vaadinApp.getMainWindow().showNotification("Vaadin E4 Application");
	}
}
