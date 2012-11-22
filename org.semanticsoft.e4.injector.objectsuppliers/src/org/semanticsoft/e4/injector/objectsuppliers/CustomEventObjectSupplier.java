package org.semanticsoft.e4.injector.objectsuppliers;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.internal.extensions.EventObjectSupplier;
import org.eclipse.e4.core.di.suppliers.IObjectDescriptor;

public class CustomEventObjectSupplier extends EventObjectSupplier{
	
	@Inject
	@Named("e4ApplicationInstanceId")
	private String eAI;
	
	@PostConstruct
	public void postConstruct(IEclipseContext context){
		System.err.println("EVENT");
	}
	
	@Override
	protected String getTopic(IObjectDescriptor descriptor) {
		String topic = super.getTopic(descriptor);
		return eAI==null?topic:eAI+"/"+topic;
	}

}
