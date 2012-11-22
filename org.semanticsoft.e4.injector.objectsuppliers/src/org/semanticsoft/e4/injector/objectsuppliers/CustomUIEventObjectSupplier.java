package org.semanticsoft.e4.injector.objectsuppliers;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.suppliers.IObjectDescriptor;
import org.eclipse.e4.ui.internal.di.UIEventObjectSupplier;

public class CustomUIEventObjectSupplier extends UIEventObjectSupplier {

	@Inject
	@Named("e4ApplicationInstanceId")
	private String eAI;
	
	public void postConstruct(IEclipseContext context){
		System.err.println("UIEVENT");
	}
	
	@Override
	protected String getTopic(IObjectDescriptor descriptor) {
		String topic = super.getTopic(descriptor);
		return eAI==null?topic:eAI+"/"+topic;
	}
	
}
