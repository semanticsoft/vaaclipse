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

package org.semanticsoft.vaaclipse.presentation.renderers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.ui.internal.workbench.ContributionsAnalyzer;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MCoreExpression;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.MTrimContribution;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.ExpressionContext;
import org.semanticsoft.vaaclipse.api.VaadinExecutorService;

import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;


@SuppressWarnings("restriction")
public class TrimBarRenderer extends VaadinRenderer {

	@Inject
	MApplication app;
	
	@Inject
	EventBroker eventBroker;
	
	@Inject
	EModelService modelService;
	
	@Inject
	VaadinExecutorService execService;
	
	private HashMap<MTrimBar, ArrayList<ArrayList<MTrimElement>>> pendingCleanup = new HashMap<MTrimBar, ArrayList<ArrayList<MTrimElement>>>();
	
	@PostConstruct
	public void subsrcribe()
	{
		
	}
	
	@PreDestroy
	public void unsubscribe()
	{
		
	}
	
	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MTrimBar)) 
		{
			return;
		}
		
		MTrimBar mTrimBar = (MTrimBar) element;
		int orientation = mTrimBar.getSide().getValue();
		
		CssLayout trimBar = new CssLayout();
		
		if (orientation == SideValue.BOTTOM_VALUE)
		{
			trimBar.addStyleName("horizontaltrimbar");
		}
		else if (orientation == SideValue.TOP_VALUE)
		{
			trimBar.addStyleName("toptrimbar_11");
		}
		else if (orientation == SideValue.LEFT_VALUE || orientation == SideValue.RIGHT_VALUE)
			trimBar.addStyleName("verticaltrimbar");
		
		trimBar.setSizeUndefined();
		element.setWidget(trimBar);
	}

	@Override
	public void processContents(MElementContainer<MUIElement> container) {
		final MTrimBar trimBar = (MTrimBar)((MElementContainer<?>)container);
		int orientation = trimBar.getSide().getValue();
		CssLayout trimBarWidget = (CssLayout) container.getWidget();
		if (orientation == SideValue.TOP_VALUE || orientation == SideValue.BOTTOM_VALUE)
			trimBarWidget.setHeight(-1);
		else
			trimBarWidget.setWidth(-1);

		boolean isFirst = true;
		trimBarWidget.removeAllComponents();
		for (MUIElement element : container.getChildren()) {
			if (element.isToBeRendered())
			{
				ComponentContainer subToolbar = (ComponentContainer) element.getWidget();
				subToolbar.setVisible(element.isVisible());
				if (subToolbar != null) {
					if (orientation == SideValue.TOP_VALUE || orientation == SideValue.BOTTOM_VALUE)
						subToolbar.addStyleName("horizontaltrimelement");
					else
						subToolbar.addStyleName("verticaltrimelement");
					
					subToolbar.setSizeUndefined();
					
					trimBarWidget.addComponent(subToolbar);
					isFirst = false;
				}	
			}
		}
		
		//---
		IEclipseContext ctx = getContext(container);
		final ExpressionContext eContext = new ExpressionContext(ctx);
		
		//visible when support for original trimbar elements (without contributed)
		for (final MTrimElement child : trimBar.getChildren())
		{
			if (child.getVisibleWhen() != null) {
				ctx.runAndTrack(new RunAndTrack() {
					@Override
					public boolean changed(IEclipseContext context) {
						
						if (!trimBar.isToBeRendered()
								|| !trimBar.isVisible()
								|| trimBar.getWidget() == null) {
							return false;
						}
						
						final boolean rc = ContributionsAnalyzer.isVisible((MCoreExpression)child.getVisibleWhen(), eContext);
						execService.invokeLater(new Runnable() {
							
							@Override
							public void run()
							{
								child.setToBeRendered(rc);
							}
						});
						
						return true;
					}
				});
			}
		}
		
		//contributions
		ArrayList<MTrimContribution> toContribute = new ArrayList<MTrimContribution>();
		ContributionsAnalyzer.gatherTrimContributions(trimBar,
				app.getTrimContributions(), trimBar.getElementId(),
				toContribute, eContext);
		addTrimContributions(trimBar, toContribute, ctx, eContext);
		
		refreshVisibility(trimBar);
	}

	private void refreshVisibility(MTrimBar trimBar) {
		
		CssLayout trimBarWidget = (CssLayout) trimBar.getWidget();
		int orientation = trimBar.getSide().getValue();
		
		trimBarWidget.setVisible(trimBarWidget.getComponentCount() != 0);
		
		if (orientation == SideValue.TOP_VALUE)
		{
			MWindow window = modelService.getTopLevelWindowFor(trimBar);
			WorkbenchWindow windowWidget = (WorkbenchWindow) window.getWidget();
			
			TopbarComponent topbar = windowWidget.getTopbar();
			if (topbar != null)
				topbar.setVisible(trimBarWidget.getComponentCount() != 0);
		}
	}
	
	private void addTrimContributions(final MTrimBar trimModel,
			ArrayList<MTrimContribution> toContribute, IEclipseContext ctx,
			final ExpressionContext eContext) {
		HashSet<String> existingToolbarIds = new HashSet<String>();
		for (MTrimElement item : trimModel.getChildren()) {
			String id = item.getElementId();
			if (item instanceof MToolBar && id != null) {
				existingToolbarIds.add(id);
			}
		}

		boolean done = toContribute.size() == 0;
		while (!done) {
			ArrayList<MTrimContribution> curList = new ArrayList<MTrimContribution>(
					toContribute);
			int retryCount = toContribute.size();
			toContribute.clear();

			for (final MTrimContribution contribution : curList) {
				final ArrayList<MTrimElement> toRemove = new ArrayList<MTrimElement>();
				if (!ContributionsAnalyzer.processAddition(trimModel,
						contribution, toRemove, existingToolbarIds)) {
					toContribute.add(contribution);
				} else {
					if (contribution.getVisibleWhen() != null) {
						ctx.runAndTrack(new RunAndTrack() {
							@Override
							public boolean changed(IEclipseContext context) {
								if (!trimModel.isToBeRendered()
										|| !trimModel.isVisible()
										|| trimModel.getWidget() == null) {
									return false;
								}
								final boolean rc = ContributionsAnalyzer.isVisible(
										contribution, eContext);
								
								
								execService.invokeLater(new Runnable() {
									
									@Override
									public void run()
									{
										for (MTrimElement child : toRemove) {
											child.setToBeRendered(rc);
										}
									}
								});
								
								return true;
							}
						});
					}
					ArrayList<ArrayList<MTrimElement>> lists = pendingCleanup
							.get(trimModel);
					if (lists == null) {
						lists = new ArrayList<ArrayList<MTrimElement>>();
						pendingCleanup.put(trimModel, lists);
					}
					lists.add(toRemove);
				}
			}
			// We're done if the retryList is now empty (everything done) or
			// if the list hasn't changed at all (no hope)
			done = (toContribute.size() == 0)
					|| (toContribute.size() == retryCount);
		}
	}
	
	@Override
	public void addChildGui(MUIElement child, MElementContainer<MUIElement> element)
	{
		if (!(child instanceof MTrimElement && (MElementContainer<?>)element instanceof MTrimBar))
			return;
		
		MTrimBar trimBar = (MTrimBar)(MElementContainer<?>)element;
		
		final Component childWidget = (Component) child.getWidget();
		childWidget.setVisible(child.isVisible());
		childWidget.setSizeUndefined();
		int orientation = trimBar.getSide().getValue();
		if (orientation == SideValue.TOP_VALUE || orientation == SideValue.BOTTOM_VALUE)
			childWidget.addStyleName("horizontaltrimelement");
		else
			childWidget.addStyleName("verticaltrimelement");
		
		CssLayout trimWidget = (CssLayout) element.getWidget();
		int index = indexOf(child, element);
		trimWidget.addComponent(childWidget, index);
		
		refreshVisibility(trimBar);
		
		trimWidget.requestRepaint();
	}
	
	@Override
	public void removeChildGui(MUIElement child, MElementContainer<MUIElement> element)
	{
		if (!(child instanceof MTrimElement && (MElementContainer<?>)element instanceof MTrimBar))
			return;
		
		super.removeChildGui(child, element);
		
		CssLayout trimWidget = (CssLayout) element.getWidget();
		
		MTrimBar trimBar = (MTrimBar)(MElementContainer<?>)element;
		refreshVisibility(trimBar);
		
		trimWidget.requestRepaint();
	}
}
