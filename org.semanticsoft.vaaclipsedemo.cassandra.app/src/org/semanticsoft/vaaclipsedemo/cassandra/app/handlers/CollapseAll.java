package org.semanticsoft.vaaclipsedemo.cassandra.app.handlers;

import org.eclipse.e4.ui.model.application.MContribution;
import org.semanticsoft.vaaclipsedemo.cassandra.app.views.Console;

import org.eclipse.e4.core.services.events.IEventBroker;

import org.eclipse.e4.core.contexts.IEclipseContext;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.semanticsoft.vaaclipsedemo.cassandra.app.views.PackageExplorer;

public class CollapseAll
{
	@Execute
	public void execute(MApplication app, EModelService modelService, IEclipseContext context)
	{
		MPart part = (MPart)modelService.find("org.semanticsoft.vaaclipsedemo.cassandra.app.part.packageexplorer", app);
		
		PackageExplorer packageExplorer = (PackageExplorer) part.getObject();
		
		for (Object rootId : packageExplorer.tree.rootItemIds())
		{
			packageExplorer.tree.collapseItemsRecursively(rootId);
		}
		
//		System.out.println("send messgage by workbench with instanceid=" + context.get("e4ApplicationInstanceId"));
//		context.get(IEventBroker.class).send("test1", context.get("e4ApplicationInstanceId"));
	}
}
