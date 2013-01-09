/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.cassandra.app.processor;

import org.apache.commons.io.FileUtils;

import org.semanticsoft.vaaclipsedemo.cassandra.app.CassandraActivator;

import org.semanticsoft.e4extension.service.EPartServiceExt;

import java.io.File;

import org.eclipse.e4.core.di.extensions.EventUtils;

import org.semanticsoft.vaaclipsedemo.cassandra.app.constants.CassandraConstants;

import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipsedemo.cassandra.app.editors.TextEditor;

/**
 * @author rushan
 *
 */
public class OpenFileProcessor
{
	private static final String fs = System.getProperty("file.separator");
	
	@Inject
	IEclipseContext context;
	
	@Inject
	EventBroker eventBroker;
	
	@Inject
	EPartServiceExt partServiceExt;
	
	private EventHandler openFileHandler = new EventHandler() {

		public void handleEvent(Event event)
		{
			File file = (File) event.getProperty(EventUtils.DATA);
			openFile(file);
		}
	};
	
	private void openFile(File file)
	{
		String path = file.getAbsolutePath();
		MInputPart part = partServiceExt.openUri(path);
		int lastLsIndex = path.lastIndexOf(fs);
		if (lastLsIndex > 0)
			part.setLabel(path.substring(lastLsIndex + 1));	
		
		String pathStr = path;
		int projectTreeRootIndex = path.indexOf(".cassandra");
		if (projectTreeRootIndex > -1)
		{
			int i = path.indexOf(fs, projectTreeRootIndex);
			pathStr = projectTreeRootIndex > -1 ? path.substring(i + 1) : path;
		}
		
		eventBroker.send(CassandraConstants.CONSOLE_LOG, "Open file: " + pathStr);
	}
	
	@Execute
	public void execute()
	{
		eventBroker.subscribe(CassandraConstants.OPEN_FILE, openFileHandler);
		
		File srcStore = CassandraActivator.getInstance().getSrcStore();
		String projectName = "org.semanticsoft.vaaclipsedemo.cassandra.app";
		File welcome = FileUtils.getFile(srcStore, projectName, "data", "Welcome.html");
		openFile(welcome);
	}
}
