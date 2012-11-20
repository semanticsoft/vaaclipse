 
package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;

import e4modelextension.Dialog;

public class TestHandler {
	
	
	
	@Execute
	public void execute(IEclipseContext c, MWindow window, Dialog dialog) {
		System.err.println("This is done when wizard is closed");
		window.getChildren().remove(dialog);
	}
		
}