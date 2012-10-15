package org.semanticsoft.vaaclipsedemo.cassandra.app.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.semanticsoft.vaaclipsedemo.cassandra.app.views.PackageExplorer;

public class CollapseAll
{
	@Execute
	public void execute(MApplication app, EModelService modelService)
	{
		MPart part = (MPart)modelService.find("org.semanticsoft.vaaclipsedemo.cassandra.app.part.packageexplorer", app);
		
		PackageExplorer packageExplorer = (PackageExplorer) part.getObject();
		
		for (Object rootId : packageExplorer.tree.rootItemIds())
		{
			packageExplorer.tree.collapseItemsRecursively(rootId);
		}
	}
}
