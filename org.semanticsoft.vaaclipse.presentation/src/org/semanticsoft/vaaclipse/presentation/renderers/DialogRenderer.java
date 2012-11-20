package org.semanticsoft.vaaclipse.presentation.renderers;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.semanticsoft.vaaclipse.widgets.WorkbenchWindow;
import org.semanticsoft.vaadinaddons.boundsinfo.BoundsinfoVerticalLayout;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import e4modelextension.Dialog;

public class DialogRenderer extends GenericRenderer {
	
	@Inject
	private Application vaadinapp;
	
	@Inject
	private IEclipseContext context;
	
	@Inject
	private IPresentationEngine engine;
	
	@Inject
	private EHandlerService handlerService;
	
	@Inject
	private ECommandService commandService;

	@Inject
	private IContributionFactory factory;
	
	private WorkbenchWindow window;

	private BoundsinfoVerticalLayout clientArea;
	
	@Override
	public void createWidget(MUIElement element,
			MElementContainer<MUIElement> parent) {
		if (!(element instanceof Dialog))
		return ;
			HorizontalLayout hl = new HorizontalLayout();
			final Dialog dialog = (Dialog) element;
			window = new WorkbenchWindow();
			window.setCaption(dialog.getLabel());
			window.setPositionX(200);
			window.setPositionY(200);
			window.setWidth("500px");
			window.setHeight("500px");
			window.setModal(true);
			window.addComponent(hl);
			clientArea = window.getClientArea();
			dialog.getContext().set(HorizontalLayout.class, hl);
			dialog.getContext().set(VerticalLayout.class,clientArea);
			Object object = factory.create(dialog.getContributionURI(), dialog.getContext());
			dialog.setObject(object);
			element.setWidget(window);
			
	}
	
	@Override
	public void processContents(MElementContainer<MUIElement> element) {
		// TODO Auto-generated method stub
		super.processContents(element);
			
			clientArea.addComponent((com.vaadin.ui.Component)element.getSelectedElement().getWidget());
		}
	}
	
