package org.semanticsoft.vaaclipse.eventbroker;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.services.internal.events.EventBroker;

public class SeparatedEventBrokerFactory extends ContextFunction {
	
	public SeparatedEventBrokerFactory()
	{
		//System.out.println("separated event broker factory start");
	}
	
	@Override
	public Object compute(IEclipseContext context) {
		System.err.println("Separated event broker is started! This is very good! If you don't see this message in your console log, then multisession doesn't work and you must correct your bundle start levels!");
		SeparatedEventBroker broker = context.getLocal(SeparatedEventBroker.class);
		if (broker == null) {
            broker = ContextInjectionFactory.make(SeparatedEventBroker.class, context);
            context.set(SeparatedEventBroker.class, broker);
            context.set(EventBroker.class, broker);
            context.set(IEventBroker.class, broker);
		}
		return broker;
	}
}
