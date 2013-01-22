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

import java.net.URI;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.semanticsoft.vaaclipse.api.VaadinExecutorService;
import org.semanticsoft.vaaclipse.presentation.fastview.FastViewManager;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("restriction")
public class VaadinPresentationEngine extends GenericPresentationEngine {

	@Override
	public Object run(final MApplicationElement uiRoot,
			IEclipseContext appContext) {

		ContextInjectionFactory.make(FastViewManager.class, appContext);

		super.run(uiRoot, appContext);

		return "";
	}

	@Override
	@PostConstruct
	public void postConstruct(IEclipseContext context) {
		super.postConstruct(context);

		// Add the presentation engine to the context
		context.set(IPresentationEngine.class.getName(), this);

		// TODO use parameter or registry
		IContributionFactory contribFactory = context
				.get(IContributionFactory.class);
		try {
			rendererFactory = (RendererFactory) contribFactory
					.create("bundleclass://org.semanticsoft.vaaclipse.presentation/org.semanticsoft.vaaclipse.presentation.renderers.VaadinRendererFactory",
							context);
		} catch (Exception e) {
			logger.warn(e, "Could not create rendering factory");
		}
	}

	@Override
	public void stop() {
		super.stop();
		
		VaadinExecutorService executor = theApp.getContext().get(VaadinExecutorService.class);
		executor.removeAllAlwaysRunnables();
		
		UI ui = theApp.getContext().get(UI.class);
		ui.setContent(new VerticalLayout());
		URI appUri = ui.getPage().getLocation();
		ui.close();
		ui.getPage().setLocation(appUri);
	}
}
