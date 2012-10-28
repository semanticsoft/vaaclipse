/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.cassandra.app.processor;

import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

/**
 * @author rushan
 * 
 */
public class TextEditorProcessor
{
	@Inject
	IEclipseContext context;
	@Inject
	EventBroker eventBroker;
	
	private EventHandler activateHandler = new EventHandler() {

		public void handleEvent(Event event)
		{
			System.out.println("test");
			context.set("editortype", "texteditor");
		}
	};
	
	@Execute
	public void execute()
	{
		eventBroker.subscribe(UIEvents.UILifeCycle.ACTIVATE, activateHandler);
	}
}
