package org.semanticsoft.vaaclipse.demo.lifecycle;



import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;

public class LifecycleHandler {

	
	
	@PostContextCreate
	public void pcc(Logger logger){
		logger.info("Lifecycle handler is picked up");
	}
	
}
