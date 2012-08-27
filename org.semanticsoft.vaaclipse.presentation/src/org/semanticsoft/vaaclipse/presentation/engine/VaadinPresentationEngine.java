/*******************************************************************************
 * Copyright (c) 2012 Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *******************************************************************************/

package org.semanticsoft.vaaclipse.presentation.engine;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.equinox.app.IApplication;
import org.semanticsoft.vaaclipse.api.Behaviour;
import org.semanticsoft.vaaclipse.presentation.fastview.FastViewManager;
import org.semanticsoft.vaaclipse.presentation.renderers.WorkbenchWindowRenderer;


@SuppressWarnings("restriction")
public class VaadinPresentationEngine extends GenericPresentationEngine {
	
	@Override
	public Object run(final MApplicationElement uiRoot, IEclipseContext appContext) {
		
		ContextInjectionFactory.make(FastViewManager.class, appContext);
		
		if (uiRoot instanceof MApplication) {
			MApplication theApp = (MApplication) uiRoot;
			for (MWindow window : theApp.getChildren()) {
				createGui(window);
			}
		}

//		synchronized (uiRoot) {
//			try {
//				uiRoot.wait();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//		System.out.println("VaadinPresentationEngine.run(): Finished");
//		return IApplication.EXIT_OK;
		return "";
	}

	@Override
	@PostConstruct
	public void postConstruct(IEclipseContext context) {
		super.postConstruct(context);

		System.out.println("VaadinPresentationEngine.postConstruct()");
		// Add the presentation engine to the context
		context.set(IPresentationEngine.class.getName(), this);

		// TODO use parameter or registry
		IContributionFactory contribFactory = context.get(IContributionFactory.class);
		try {
			rendererFactory = (RendererFactory) contribFactory.create(
					"bundleclass://org.semanticsoft.vaaclipse.presentation/org.semanticsoft.vaaclipse.presentation.renderers.VaadinRendererFactory", context);
		} catch (Exception e) {
			logger.warn(e, "Could not create rendering factory");
		}
	}
}
