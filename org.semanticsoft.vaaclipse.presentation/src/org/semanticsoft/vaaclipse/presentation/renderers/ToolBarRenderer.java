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
import org.eclipse.e4.ui.model.application.ui.basic.MTrimElement;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarContribution;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarSeparator;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.ElementContainer;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;


@SuppressWarnings("restriction")
public class ToolBarRenderer extends GenericRenderer {

	private Map<MToolBar, CssLayout> modelToManager = new HashMap<MToolBar, CssLayout>();
	private Map<CssLayout, MToolBar> managerToModel = new HashMap<CssLayout, MToolBar>();
	private Map<MToolBarElement, Component> modelToContribution = new HashMap<MToolBarElement, Component>();
	private Map<Component, MToolBarElement> contributionToModel = new HashMap<Component, MToolBarElement>();
	private Map<MToolBarElement, ToolBarContributionRecord> modelContributionToRecord = new HashMap<MToolBarElement, ToolBarContributionRecord>();
	private Map<MToolBarElement, ArrayList<ToolBarContributionRecord>> sharedElementToRecord = new HashMap<MToolBarElement, ArrayList<ToolBarContributionRecord>>();
	
	@Inject
	private MApplication application;
	@Inject
	private EModelService modelService;
	@Inject
	IEventBroker eventBroker;

//	private EventHandler childAdditionUpdater = new EventHandler() {
//		public void handleEvent(Event event) {
//			// Ensure that this event is for a MMenuItem
//			if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MToolBar))
//				return;
//			MToolBar toolbarModel = (MToolBar) event
//					.getProperty(UIEvents.EventTags.ELEMENT);
//			String eventType = (String) event
//					.getProperty(UIEvents.EventTags.TYPE);
//			if (UIEvents.EventTypes.ADD.equals(eventType)) {
//				Object obj = toolbarModel;
//				processContents((MElementContainer<MUIElement>) obj);
//			}
//		}
//	};

	@PostConstruct
	public void init() {
		//eventBroker.subscribe(ElementContainer.TOPIC_CHILDREN, childAdditionUpdater);
		
		context.set(ToolBarRenderer.class, this);

	}

	@PreDestroy
	public void contextDisposed() {
		//eventBroker.unsubscribe(childAdditionUpdater);
	}
	
	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (!(element instanceof MToolBar)) {
			return;
		}
		
		MToolBar toolbarModel = (MToolBar) element;
		processContribution(toolbarModel);

		CssLayout toolBar = new CssLayout();
		element.setWidget(toolBar);
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

		CssLayout manager = getManager(toolbarModel);
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
			final CssLayout manager, MToolBarContribution contribution) {
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
					if (getManager(toolbarModel) == null) {
						// tool bar no longer being managed, ignore it
						return false;
					}

					record.updateVisibility(parentContext.getActiveLeaf());
					
					return true;
				}
			});
		}

		return true;
	}
	
	public CssLayout getManager(MToolBar model) {
		return modelToManager.get(model);
	}

	public MToolBar getToolBarModel(CssLayout manager) {
		return managerToModel.get(manager);
	}

	public void linkModelToManager(MToolBar model, CssLayout manager) {
		modelToManager.put(model, manager);
		managerToModel.put(manager, model);
	}

	public void clearModelToManager(MToolBar model, CssLayout manager) {
		modelToManager.remove(model);
		managerToModel.remove(manager);
	}

	public Component getContribution(MToolBarElement element) {
		return modelToContribution.get(element);
	}

	public MToolBarElement getToolElement(Component item) {
		return contributionToModel.get(item);
	}

	public void linkModelToWidget(MToolBarElement model,
			Component item) {
		modelToContribution.put(model, item);
		contributionToModel.put(item, model);
	}

	public void clearModelToWidget(MToolBarElement model,
			Component item) {
		modelToContribution.remove(model);
		contributionToModel.remove(item);
	}

	public ArrayList<ToolBarContributionRecord> getList(MToolBarElement item) {
		ArrayList<ToolBarContributionRecord> tmp = sharedElementToRecord
				.get(item);
		if (tmp == null) {
			tmp = new ArrayList<ToolBarContributionRecord>();
			sharedElementToRecord.put(item, tmp);
		}
		return tmp;
	}

	public void linkElementToContributionRecord(MToolBarElement element,
			ToolBarContributionRecord record) {
		modelContributionToRecord.put(element, record);
	}

	public ToolBarContributionRecord getContributionRecord(
			MToolBarElement element) {
		return modelContributionToRecord.get(element);
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> container) {
		CssLayout toolBar = (CssLayout) container.getWidget();
		if (toolBar != null)
		{
			toolBar.removeAllComponents();
			for (MUIElement element : container.getChildren()) {
				if (element instanceof MHandledToolItem || element instanceof MDirectToolItem) {
					toolBar.addComponent((Component) element.getWidget());
				} else if (element instanceof MToolBarSeparator) {
					toolBar.addComponent(new Label("Separator"));
				}
			}	
		}
	}
	
	@Override
	public void addChild(MUIElement child, MElementContainer<MUIElement> element)
	{
		if (!(child instanceof MToolBarElement && (MElementContainer<?>)element instanceof MToolBar))
			return;
		
		super.addChild(child, element);
		
		CssLayout toolbarWidget = (CssLayout) element.getWidget();
		Component childWidget = (Component) child.getWidget();
		if (toolbarWidget == null || childWidget == null)
			return;
		int index = element.getChildren().indexOf(child);
		int pos = toolbarWidget.getComponentCount();
		if (index == 0)
			pos = index;
		else
		{
			MUIElement prevChild = element.getChildren().get(index - 1);
			for (int k = 0; k < toolbarWidget.getComponentCount(); k++)
			{
				if (toolbarWidget.getComponent(k).equals(prevChild.getWidget()))
				{
					pos = k + 1;
					break;
				}
			}
		}
		
		if (element instanceof MToolBarSeparator) 
		{
			toolbarWidget.addComponent(new Label("Separator"), pos);
		} 
		else  {
			toolbarWidget.addComponent(childWidget, pos);
		}
		
		toolbarWidget.requestRepaint();
	}
}
