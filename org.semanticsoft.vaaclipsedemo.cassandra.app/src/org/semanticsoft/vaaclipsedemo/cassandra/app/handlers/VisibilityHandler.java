 
package org.semanticsoft.vaaclipsedemo.cassandra.app.handlers;

import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

public class VisibilityHandler {
	
	private MWindow w;

	@PostConstruct
	public void pc(MApplication app, EModelService modelService){
		w = (MWindow) modelService.find("org.semanticsoft.vaaclipsedemo.cassandra.app.trimmedwindow.0", app);
	}
	
	@CanExecute
	public boolean can(MApplication app, EModelService modelService){
		return w!=null;//this button is meant to show/hide this specific popup
		
	}
	
	@Execute
	public void execute(IEventBroker broker) {
		w.setVisible(!w.isVisible());
		broker.send("TEST", new BigDecimal(3));
		broker.send("UITEST", new BigDecimal(13));
		//note that this manipulation just from the model level, the rest is done by the renderer listeners
	}
		
}