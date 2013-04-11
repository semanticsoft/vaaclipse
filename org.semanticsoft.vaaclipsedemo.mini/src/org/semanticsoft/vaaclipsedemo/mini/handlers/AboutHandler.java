package org.semanticsoft.vaaclipsedemo.mini.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.semanticsoft.vaaclipse.api.VaadinExecutorService;

import com.vaadin.ui.Notification;

public class AboutHandler {
	@Execute
	public void execute(VaadinExecutorService executorService) {
		
		executorService.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Notification.show("Invoke later!");
			}
		});
	}
}
