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
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.internal.workbench.ContributionsAnalyzer;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarContribution;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarSeparator;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.semanticsoft.vaaclipse.presentation.utils.GuiUtils;

import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;


@SuppressWarnings("restriction")
public class ToolBarRenderer extends BasicMenuToolbarTrimbarRenderer {	
	@Inject
	private MApplication application;
	@Inject
	private EModelService modelService;
	@Inject
	IEventBroker eventBroker;

	@PostConstruct
	public void init() {
		context.set(ToolBarRenderer.class, this);
	}

	@PreDestroy
	public void contextDisposed() {
		
	}
	
	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MToolBar)) {
			return;
		}
		
		MToolBar toolbarModel = (MToolBar) element;
		processContribution(toolbarModel);

		AbstractLayout toolBarWidget;
		
		if ((MElementContainer<?>)toolbarModel.getParent() instanceof MTrimBar)
		{
			MTrimBar parentTrimBar = (MTrimBar)(MElementContainer<?>)toolbarModel.getParent();
			int orientation = parentTrimBar.getSide().getValue();
			
			if (orientation == SideValue.TOP_VALUE || orientation == SideValue.BOTTOM_VALUE)
			{
				toolBarWidget = new HorizontalLayout() {
					@Override
					public void addComponent(Component c)
					{
						if (!c.getStyleName().contains("horizontalseparator"))
							c.addStyleName("horizontaltoolbarlement");
						super.addComponent(c);
					}
					
					@Override
					public void addComponent(Component c, int index)
					{
						if (!c.getStyleName().contains("horizontalseparator"))
							c.addStyleName("horizontaltoolbarlement");
						super.addComponent(c, index);
					}
				};
				toolBarWidget.addStyleName("horizontaltoolbar");
			}
			else
			{
				toolBarWidget = new VerticalLayout() {
					@Override
					public void addComponent(Component c)
					{
						if (!c.getStyleName().contains("verticalseparator"))
							c.addStyleName("verticaltoolbarlement");
						super.addComponent(c);
					}
					
					@Override
					public void addComponent(Component c, int index)
					{
						if (!c.getStyleName().contains("verticalseparator"))
							c.addStyleName("verticaltoolbarlement");
						super.addComponent(c, index);
					}
				};
				toolBarWidget.addStyleName("verticaltoolbar");
			}
			
			
			Component separator = GuiUtils.createSeparator(toolbarModel);
			if (separator != null)
				toolBarWidget.addComponent(separator);
		}
		else
			toolBarWidget = new HorizontalLayout();
		
		toolBarWidget.setSizeUndefined();
		toolBarWidget.addStyleName("toolbar");
		
		for (String css : toolbarModel.getTags())
		{
			toolBarWidget.addStyleName(css);
		}
		
		element.setWidget(toolBarWidget);
	}
	
	/**
	 * @param element
	 */
	private void processContribution(MToolBar toolbarModel) {
		final ArrayList<MToolBarContribution> toContribute = new ArrayList<MToolBarContribution>();
		ContributionsAnalyzer.XXXgatherToolBarContributions(toolbarModel,
				application.getToolBarContributions(),
				toolbarModel.getElementId(), toContribute);
		generateContributions(toolbarModel, toContribute);
	}

	/**
	 * @param toolbarModel
	 * @param toContribute
	 */
	private void generateContributions(MToolBar toolbarModel,
			ArrayList<MToolBarContribution> toContribute) {

		AbstractLayout manager = (AbstractLayout) toolbarModel.getWidget();
		boolean done = toContribute.size() == 0;
		while (!done) {
			ArrayList<MToolBarContribution> curList = new ArrayList<MToolBarContribution>(
					toContribute);
			int retryCount = toContribute.size();
			toContribute.clear();

			for (final MToolBarContribution contribution : curList) {
				if (!processAddition(toolbarModel, manager, contribution)) {
					toContribute.add(contribution);
				}
			}
			// We're done if the retryList is now empty (everything done) or
			// if the list hasn't changed at all (no hope)
			done = (toContribute.size() == 0)
					|| (toContribute.size() == retryCount);
		}
	}

	/**
	 * @param toolbarModel
	 * @param manager
	 * @param contribution
	 * @param existingSeparatorNames
	 * @return <code>true</code> if the contribution was successfuly processed
	 */
	private boolean processAddition(final MToolBar toolbarModel,
			final AbstractLayout manager, MToolBarContribution contribution) {
		final ToolBarContributionRecord record = new ToolBarContributionRecord(
				toolbarModel, contribution, this);
		if (!record.mergeIntoModel()) {
			return false;
		}
		if (record.anyVisibleWhen()) {
			final IEclipseContext parentContext = modelService
					.getContainingContext(toolbarModel);
			parentContext.runAndTrack(new RunAndTrack() {
				@Override
				public boolean changed(IEclipseContext context) {
					if (toolbarModel.getWidget() == null) {
						return false;
					}

					record.updateVisibility(parentContext.getActiveLeaf());
					
					return true;
				}
			});
		}

		return true;
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> container) {
		MToolBar toolBar = (MToolBar)(MElementContainer<?>)container;
		AbstractLayout toolBarWidget = (AbstractLayout) container.getWidget();
		if (toolBarWidget != null)
		{
			for (MUIElement element : container.getChildren()) {
				if (element instanceof MHandledToolItem || element instanceof MDirectToolItem) {
					toolBarWidget.addComponent((Component) element.getWidget());
				} else if (element instanceof MToolBarSeparator) {
					toolBarWidget.addComponent(GuiUtils.createSeparator(toolBar));
				}
			}	
		}
	}
	
	@Override
	public void addChildGui(MUIElement child, MElementContainer<MUIElement> element)
	{
		if (!(child instanceof MToolBarElement && (MElementContainer<?>)element instanceof MToolBar))
			return;
		
		MToolBar toolBar = (MToolBar)(MElementContainer<?>)element;
		
		AbstractOrderedLayout toolbarWidget = (AbstractOrderedLayout) element.getWidget();
		Component childWidget = (Component) child.getWidget();
		if (toolbarWidget == null || childWidget == null)
			return;
		int index = indexOf(child, element) + 1; //+1 becouse the first element is toolbar drag handler (separator)
		if (element instanceof MToolBarSeparator) 
		{
			toolbarWidget.addComponent(GuiUtils.createSeparator(toolBar), index);
		} 
		else  {
			toolbarWidget.addComponent(childWidget, index);
		}
		
		toolbarWidget.requestRepaint();
	}
}
