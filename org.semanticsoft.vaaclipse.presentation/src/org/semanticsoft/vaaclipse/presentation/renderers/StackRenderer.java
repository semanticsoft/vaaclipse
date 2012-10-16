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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.presentation.utils.Utils;
import org.semanticsoft.vaaclipse.widgets.StackWidget;
import org.semanticsoft.vaaclipse.widgets.StackWidget.StateListener;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;


@SuppressWarnings("restriction")
public class StackRenderer extends GenericRenderer {
	
	@Inject
	private EventBroker eventBroker;
	private Map<Component, MStackElement> vaatab2Element = new HashMap<Component, MStackElement>();
	
	private EventHandler tagListener = new EventHandler() {
		@Override
		public void handleEvent(Event event) {

			Object changedObj = event.getProperty(EventTags.ELEMENT);
			String eventType = (String) event.getProperty(UIEvents.EventTags.TYPE);
			String tag = (String) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			
			int location = modelService.getElementLocation((MUIElement) changedObj);
			if (!(changedObj instanceof MPartStack) || location == EModelService.IN_SHARED_AREA) {
				return;
			}
			
			final MPartStack stack = (MPartStack) changedObj;
			StackWidget stackWidget = (StackWidget)stack.getWidget();
			
			if (UIEvents.EventTypes.ADD.equals(eventType)) {
				if (IPresentationEngine.MINIMIZED.equals(tag)) {
					stackWidget.setState(-1);
				} else if (IPresentationEngine.MAXIMIZED.equals(tag)) {
					stackWidget.setState(1);
				}
			} else if (UIEvents.EventTypes.REMOVE.equals(eventType)) {
				stackWidget.setState(0);
			}
		}
	};
	
	private EventHandler selectElementHandler = new EventHandler() {
		public void handleEvent(Event event) {
			Object element = event.getProperty(UIEvents.EventTags.ELEMENT);

			if (!(element instanceof MPartStack))
				return;

			MPartStack stack = (MPartStack) element;
			if (stack.getRenderer() != StackRenderer.this)
				return;

			// Gather up the elements that are being 'hidden' by this change
			MUIElement oldSel = (MUIElement) event
					.getProperty(UIEvents.EventTags.OLD_VALUE);
//			if (oldSel != null) {
//				List<MUIElement> goingHidden = new ArrayList<MUIElement>();
//				hideElementRecursive(oldSel, goingHidden);
//			}

			if (stack.getSelectedElement() != null)
			{
				//lsr.showTab(stack.getSelectedElement());
				
				if (stack.getSelectedElement().getWidget() == null)
				{
					IPresentationEngine engine = (IPresentationEngine) context.get(IPresentationEngine.class.getName());
					engine.createGui(stack.getSelectedElement());
					
					int i = 0;
					for (MStackElement e : stack.getChildren())
					{
						if (e == stack.getSelectedElement())
							break;
						
						if (e.getWidget() != null)
							i++;
					}
					
					addTab((TabSheet) stack.getWidget(), stack.getSelectedElement(), i);
				}
				
				((TabSheet)stack.getWidget()).setSelectedTab((Component) stack.getSelectedElement().getWidget());
			}
		}
	};
	
	EventHandler itemUpdater = new EventHandler() {
		public void handleEvent(Event event) {
			MUIElement element = (MUIElement) event
					.getProperty(UIEvents.EventTags.ELEMENT);
			if (!(element instanceof MPart))
				return;

			MPart part = (MPart) element;

			String attName = (String) event
					.getProperty(UIEvents.EventTags.ATTNAME);
			Object newValue = event
					.getProperty(UIEvents.EventTags.NEW_VALUE);

			MPartStack stack = null;
			// is this a direct child of the stack?
			if (element.getParent() != null
					&& element.getParent().getRenderer() == StackRenderer.this) {
				stack = (MPartStack)(MElementContainer<?>)element.getParent();
			}
			else
			{
				// Do we have any stacks with place holders for the element
				// that's changed?
				MWindow win = modelService.getTopLevelWindowFor(part);
				List<MPlaceholder> refs = modelService.findElements(win, null,
						MPlaceholder.class, null);
				if (refs != null) {
					for (MPlaceholder ref : refs) {
						if (ref.getRef() != part)
							continue;

						MElementContainer<?> refParent = ref
								.getParent();
						// can be null, see bug 328296
						if (refParent != null
								&& refParent.getRenderer() instanceof StackRenderer) {
							stack = (MPartStack)refParent;
						}
					}
				}	
			}
			
			if (stack != null)
			{
				Tab tab = ((StackWidget)stack.getWidget()).getTab((Component) part.getWidget());
				updateTab(tab, part, attName, newValue);	
			}
		}
	};
	
	private void updateTab(Tab tab, MPart part, String attName, Object newValue)
	{
		if (UIEvents.UILabel.LABEL.equals(attName)) {
			String newName = (String) newValue;
			tab.setCaption(getLabel(part, newName));
		} else if (UIEvents.UILabel.ICONURI.equals(attName)) {
			Resource icon = part.getIconURI() != null ? new ThemeResource(Utils.convertPath(part.getIconURI())) : null;
			tab.setIcon(icon);
		} else if (UIEvents.UILabel.TOOLTIP.equals(attName)) {
			String newTTip = (String) newValue;
			tab.setDescription(newTTip);
		} else if (UIEvents.Dirtyable.DIRTY.equals(attName)) {
			Boolean dirtyState = (Boolean) newValue;
			String text = tab.getCaption();
			boolean hasAsterisk = text.length() > 0 && text.charAt(0) == '*';
			if (dirtyState.booleanValue()) {
				if (!hasAsterisk) {
					tab.setCaption('*' + text);
				}
			} else if (hasAsterisk) {
				tab.setCaption(text.substring(1));
			}
		}
	}
	
	private String getLabel(MUILabel itemPart, String newName) {
		if (newName == null) {
			newName = ""; //$NON-NLS-1$
		}
		if (itemPart instanceof MDirtyable && ((MDirtyable) itemPart).isDirty()) {
			newName = '*' + newName;
		}
		return newName;
	}
	
	@PostConstruct
	public void postConstruct()
	{
		eventBroker.subscribe(UIEvents.ApplicationElement.TOPIC_TAGS, tagListener);
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT, selectElementHandler);	
		eventBroker.subscribe(UIEvents.UILabel.TOPIC_ALL, itemUpdater);
	}
	
	@PreDestroy
	public void preDestroy()
	{
		eventBroker.unsubscribe(tagListener);
		eventBroker.unsubscribe(selectElementHandler);
		eventBroker.unsubscribe(itemUpdater);
	}
	
	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		StackWidget stackWidget = new StackWidget();
		stackWidget.setSizeFull();
		element.setWidget(stackWidget);
	}

	@Override
	public void processContents(final MElementContainer<MUIElement> container) {
		TabSheet parentPane = (TabSheet) container.getWidget();
		
		vaatab2Element.clear();
		
		for (MUIElement element : container.getChildren()) {
			if (element instanceof MStackElement)
				addTab(parentPane, (MStackElement) element, parentPane.getComponentCount());
		}
	}

	private void addTab(TabSheet parentPane, MStackElement element, int pos)
	{
		MUILabel mLabel;
		if (element instanceof MPlaceholder)
			mLabel = (MUILabel) ((MPlaceholder) element).getRef();
		else
			mLabel = (MUILabel) element;
		
		boolean closable = false;
		if (mLabel instanceof MPart)
			closable = ((MPart) mLabel).isCloseable();
		
		Resource icon = mLabel.getIconURI() != null ? new ThemeResource(Utils.convertPath(mLabel.getIconURI())) : null;
		Tab tab = parentPane.addTab((com.vaadin.ui.Component) element.getWidget(), mLabel.getLocalizedLabel(), icon, pos);
		tab.setClosable(closable);
		tab.setDescription(mLabel.getLocalizedTooltip());
		
		vaatab2Element.put((Component) element.getWidget(), element);
	}
	
	@Override
	public void hookControllerLogic(final MUIElement element)
	{
		final StackWidget sw = (StackWidget)element.getWidget();
		
		int location = modelService.getElementLocation(element);
		if (location != EModelService.IN_SHARED_AREA) //if the stack not in shared area
		{
			sw.addStateListener(new StateListener() {
				
				@Override
				public void stateChanged(int newState, int oldState)
				{
					if (oldState == 0 && newState == 1)
						setState(element, IPresentationEngine.MAXIMIZED);
					else if (oldState == 1 && newState == 0)
						setState(element, null);
					else if (oldState == -1 && newState == 0)
					{
						element.getTags().remove(IPresentationEngine.MINIMIZED_BY_ZOOM);
						element.getTags().remove(IPresentationEngine.MINIMIZED);
					}
					else if (oldState == 0 && newState == -1)
						setState(element, IPresentationEngine.MINIMIZED);
				}
				
				private void setState(MUIElement element, String state) {
					element.getTags().remove(IPresentationEngine.MINIMIZED_BY_ZOOM);
					if (IPresentationEngine.MINIMIZED.equals(state)) {
						element.getTags().remove(IPresentationEngine.MAXIMIZED);
						element.getTags().add(IPresentationEngine.MINIMIZED);
					} else if (IPresentationEngine.MAXIMIZED.equals(state)) {
						element.getTags().remove(IPresentationEngine.MINIMIZED);
						element.getTags().add(IPresentationEngine.MAXIMIZED);
					} else {
						element.getTags().remove(IPresentationEngine.MINIMIZED);
						element.getTags().remove(IPresentationEngine.MAXIMIZED);
					}
				}
			});
		}
		else
		{
			System.out.println("in shared area");
		}
		
		sw.addListener(new SelectedTabChangeListener() {
			
			public void selectedTabChange(SelectedTabChangeEvent event)
			{
				MStackElement stackElement = vaatab2Element.get(sw.getSelectedTab());
				if (stackElement != null)
				{
					MPartStack stack = (MPartStack)(MElementContainer<?>)stackElement.getParent();
					if (stack != null && stack.getSelectedElement() != stackElement)
					{
						stack.setSelectedElement(stackElement);	
					}
				}
			}
		});
	}

	@Override
	public void setVisible(MUIElement changedElement, boolean visible) {
		TabSheet tabPane = (TabSheet) changedElement.getWidget();
		tabPane.requestRepaintRequests();
		tabPane.getParent().requestRepaintRequests();
		tabPane.setVisible(visible);
	}
}
