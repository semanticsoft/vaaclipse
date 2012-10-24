package org.semanticsoft.vaaclipse.presentation.renderers;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MToolControl;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.VerticalLayout;

/**
 * Create a contribute part.
 */
public class ToolControlRenderer extends GenericRenderer {
	
	@Inject
	EModelService modelService;
	
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) 
	{
		if (!(element instanceof MToolControl))
			return;
		
		VerticalLayout toolControlContainer = new VerticalLayout();
		
		MToolControl toolControl = (MToolControl) element;
		
		IEclipseContext parentContext = modelService.getContainingContext(element);

		// Create a context just to contain the parameters for injection
		IContributionFactory contributionFactory = parentContext
				.get(IContributionFactory.class);

		IEclipseContext localContext = EclipseContextFactory.create();

		localContext.set(Component.class, toolControlContainer);
		localContext.set(ComponentContainer.class, toolControlContainer);
		localContext.set(VerticalLayout.class, toolControlContainer);
		localContext.set(MToolControl.class, toolControl);
		
		Object tcImpl = contributionFactory.create(toolControl.getContributionURI(), parentContext, localContext);
		toolControl.setObject(tcImpl);
		toolControl.setWidget(toolControlContainer);
	}

}
