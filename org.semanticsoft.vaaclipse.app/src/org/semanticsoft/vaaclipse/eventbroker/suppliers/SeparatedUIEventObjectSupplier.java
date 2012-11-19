package org.semanticsoft.vaaclipse.eventbroker.suppliers;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.suppliers.IObjectDescriptor;
import org.eclipse.e4.core.di.suppliers.IRequestor;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.internal.di.UIEventObjectSupplier;
import org.osgi.service.event.EventHandler;

public class SeparatedUIEventObjectSupplier extends UIEventObjectSupplier {
	

	class UIEventHandler implements EventHandler {

		final protected IRequestor requestor;
		final private String topic;

		public UIEventHandler(String topic, IRequestor requestor) {
			this.topic = topic;
			this.requestor = requestor;
		}

		public void handleEvent(org.osgi.service.event.Event event) {
			addCurrentEvent(topic, event);
			requestor.resolveArguments(false);
			removeCurrentEvent(topic);
			if( uiSync == null ) {
				if (logger != null)
					logger.log(Level.WARNING, "No realm found to process UI event " + event);
				return;
			} else {
				uiSync.syncExec(new Runnable() {
					public void run() {
						requestor.execute();
					}
				});
			}
		}		
	}
	
	@Inject
	protected UISynchronize uiSync;
	
	@Inject @Optional
	protected Logger logger;

	protected EventHandler makeHandler(String topic, IRequestor requestor) {
		return new UIEventHandler(topic, requestor);
	}

	protected String getTopic(IObjectDescriptor descriptor) {
		if (descriptor == null)
			return null;
		UIEventTopic qualifier = descriptor.getQualifier(UIEventTopic.class);
		return qualifier.value();
	}


}
