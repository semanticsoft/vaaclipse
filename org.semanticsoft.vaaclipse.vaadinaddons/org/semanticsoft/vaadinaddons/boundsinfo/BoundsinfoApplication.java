package org.semanticsoft.vaadinaddons.boundsinfo;

import com.vaadin.Application;
import com.vaadin.ui.*;

public class BoundsinfoApplication extends Application {
	@Override
	public void init() {
		Window mainWindow = new Window("Boundsinfo Application");
		Label label = new Label("Hello Vaadin user");
		mainWindow.addComponent(label);
		setMainWindow(mainWindow);
	}

}
