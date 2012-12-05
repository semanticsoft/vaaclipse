package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers.quit;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;

public class QuitHandler {
	
	@Execute
	public void execute(IWorkbench workbench)
	{		
		workbench.close();
	}
}
