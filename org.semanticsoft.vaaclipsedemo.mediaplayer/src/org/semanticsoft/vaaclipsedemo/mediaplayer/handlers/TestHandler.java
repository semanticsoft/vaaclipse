 
package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers;

import org.eclipse.e4.core.di.annotations.Execute;

public class TestHandler {
	@Execute
	public void execute() {
		System.err.println("This is done when wizard is closed");
	}
		
}