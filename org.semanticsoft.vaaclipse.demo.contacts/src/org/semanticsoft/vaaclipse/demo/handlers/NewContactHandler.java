 
package org.semanticsoft.vaaclipse.demo.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class NewContactHandler {
	
	@CanExecute
	public boolean ce(EPartService ps){
		return ps.findPart("new.contact.part")==null;
	}
	
	@Execute
	public void execute(EModelService modelService, MApplication app, EPartService partService) {
		MPartStack partStack = (MPartStack) modelService.find("contacts.stack", app);
		MPart part = partService.createPart("new.contact.part");
		partStack.getChildren().add(part);
		partService.activate(part);
	}
		
}