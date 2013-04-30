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

package org.semanticsoft.vaaclipse.presentation.fastview;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.api.Behaviour;
import org.semanticsoft.vaaclipse.presentation.widgets.TrimmedWindowContent;
import org.semanticsoft.vaaclipse.widgets.ExtendedVerticalLayout;
import org.semanticsoft.vaaclipse.widgets.FastView;

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
/**
 * @author rushan
 *
 */
public class SingleElementFastViewManager
{

	@Inject
	UI vaadinUI;
	
	@Inject
	Behaviour behaviour;
	
	@Inject
	MUIElement minimizedElement;
	@Inject
	MTrimBar trimBar;
	@Inject
	MToolBar toolBar;
	
	private MWindow window;
	private Panel vaadinWindow;
	
	//--------------------------------------------
	//--------------------------------------------
	
	private static final String STATE_XSIZE = "XSize"; //$NON-NLS-1$

	private static final String STATE_YSIZE = "YSize"; //$NON-NLS-1$

	private boolean isShowing = false;
	private FastView hostPane;

	@Inject
	EModelService modelService;

	@Inject
	EPartService partService;

	@Inject
	protected IEventBroker eventBroker;
	
	/**
	 * This is the old way to subscribe to UIEvents. You should consider using the new way as shown
	 * by handleTransientDataEvents() and described in the article at
	 * http://wiki.eclipse.org/Eclipse4/RCP/Event_Model
	 */
	private EventHandler closeHandler = new EventHandler() {
		public void handleEvent(org.osgi.service.event.Event event) {
			if (!isShowing)
				return;

			// The only time we don't close is if I've selected my tab.
			MUIElement changedElement = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);

			// Perspective changed, close the visible stacks
			if (changedElement instanceof MPerspectiveStack) {
				showStack(false);
				return;
			}

			if (changedElement == getLeafPart(minimizedElement)) {
				fixToolItemSelection(changedElement);
				return;
			}

			showStack(false);
		}
	};

	private void fixToolItemSelection(MUIElement element) {
		//TODO: implement correctly this method
//		if (trimStackTB == null || trimStackTB.isDisposed())
//			return;
//
//		if (isEditorStack()) {
//			trimStackTB.getItem(1).setSelection(element != null);
//			if (element != null)
//				trimStackTB.getItem(1).setData(element);
//		} else if (isPerspectiveStack()) {
//			for (ToolItem item : trimStackTB.getItems()) {
//				boolean result = item.getData() == null ? false : item.getData() == element;
//				item.setSelection(result);
//			}
//		} else {
//			for (ToolItem item : trimStackTB.getItems()) {
//				boolean result = item.getData() == null ? false : item.getData() == element;
//				item.setSelection(result);
//			}
//		}

	}

	private boolean isEditorStack() {
		return minimizedElement instanceof MPlaceholder;
	}

	private boolean isPerspectiveStack() {
		return minimizedElement instanceof MPerspectiveStack;
	}

	private MPart getLeafPart(MUIElement element) {
		if (element instanceof MPlaceholder)
			return getLeafPart(((MPlaceholder) element).getRef());

		if (element instanceof MElementContainer<?>)
			return getLeafPart(((MElementContainer<?>) element).getSelectedElement());

		if (element instanceof MPart)
			return (MPart) element;

		return null;
	}

	private EventHandler openHandler = new EventHandler() {

		public void handleEvent(org.osgi.service.event.Event event) {
			if (isShowing)
				return;
			
			MPerspective currentPerspective = modelService.getPerspectiveFor(minimizedElement);
			MPerspective activePerspective = modelService.getActivePerspective(window);
			
			if (currentPerspective != activePerspective)
				return;

			MPart changedElement = (MPart) event.getProperty(UIEvents.EventTags.ELEMENT);
			
			// Open if shared area
			if (getLeafPart(minimizedElement) == changedElement) {
				showStack(true);
				return;
			}

			MUIElement selectedElement = null;

			if (minimizedElement instanceof MPlaceholder) {
				selectedElement = ((MPlaceholder) minimizedElement).getRef();
			} else if (minimizedElement instanceof MPartStack) {
				selectedElement = ((MPartStack) minimizedElement).getSelectedElement();
			}

			if (selectedElement == null)
				return;

			if (selectedElement instanceof MPlaceholder)
				selectedElement = ((MPlaceholder) selectedElement).getRef();

			if (changedElement != selectedElement)
				return;

			showStack(true);
		}
	};

	/**
	 * This is the old way to subscribe to UIEvents. You should consider using the new way as shown
	 * by handleTransientDataEvents() and described in the article at
	 * http://wiki.eclipse.org/Eclipse4/RCP/Event_Model
	 */
	private EventHandler toBeRenderedHandler = new EventHandler() {
		public void handleEvent(org.osgi.service.event.Event event) {
			if (minimizedElement == null || trimBar == null)
				return;
			
			MUIElement changedElement = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);

			// if our stack is going away, so should we
			if (changedElement == minimizedElement && !minimizedElement.isToBeRendered()) {
				if (hostPane != null)
					vaadinUI.removeWindow(hostPane);
				hostPane = null;
				return;
			}
		}
	};

	@PostConstruct
	void postConstruct() {
		
		if (window == null)
			window = (MWindow) (trimBar.getParent() != null ? (MElementContainer<?>)trimBar.getParent() : ((EObject) trimBar).eContainer());
		vaadinWindow = (Panel)window.getWidget();
		
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_TOBERENDERED, toBeRenderedHandler);
		eventBroker.subscribe(UIEvents.UILifeCycle.BRINGTOTOP, openHandler);
		eventBroker.subscribe(UIEvents.UILifeCycle.ACTIVATE, closeHandler);
	}
	
	void dispose()
	{
		showStack(false);
		
		eventBroker.unsubscribe(toBeRenderedHandler);
		eventBroker.unsubscribe(openHandler);
		eventBroker.unsubscribe(closeHandler);
	}

	/**
	 * This is the old way to subscribe to UIEvents. You should consider using the new way as shown
	 * by handleTransientDataEvents() and described in the article at
	 * http://wiki.eclipse.org/Eclipse4/RCP/Event_Model
	 */
	@PreDestroy
	void removeListeners() {
		eventBroker.unsubscribe(openHandler);
		eventBroker.unsubscribe(closeHandler);
	}

	private String getLabelText(MUILabel label) {
		// Use override text if available
		if (label instanceof MUIElement) {
			String text = getOverrideTitleToolTip((MUIElement) label);
			if (text != null)
				return text;
		}

		String string = label.getLabel();
		return string == null ? "" : string; //$NON-NLS-1$
	}
	
	private String getOverrideTitleToolTip(MUIElement element) {
		String result = null;

		Object stringObject = element.getTransientData().get(
				IPresentationEngine.OVERRIDE_TITLE_TOOL_TIP_KEY);
		if (stringObject != null && stringObject instanceof String)
			result = (String) stringObject;

		return result;
	}

	private MUILabel getLabelElement(MUIElement element) {
		if (element instanceof MPlaceholder)
			element = ((MPlaceholder) element).getRef();

		return (MUILabel) (element instanceof MUILabel ? element : null);
	}

	/**
	 * Create the popup menu that will appear when a minimized part has been selected by the cursor.
	 */
//	private void createPopupMenu() {
//		trimStackMenu = new Menu(trimStackTB);
//		trimStackTB.setMenu(trimStackMenu);
//
//		MenuItem closeItem = new MenuItem(trimStackMenu, SWT.NONE);
//		closeItem.setText(Messages.TrimStack_CloseText);
//		closeItem.addListener(SWT.Selection, new Listener() {
//			public void handleEvent(Event event) {
//				partService.hidePart((MPart) selectedToolItem.getData());
//			}
//		});
//	}
	
	ClickListener layoutClickListener = new ClickListener() {
		
		@Override
		public void click(ClickEvent event) {
			if (isShowing)
        		showStack(false);
        	partService.requestActivation();
		}
    };

	public synchronized void showStack(boolean show) {
		Component ctf = (Component) minimizedElement.getWidget();
		
		if (show && !isShowing) 
		{
			hostPane = getHostPane();
			VerticalLayout vl = new VerticalLayout();
			vl.setSizeFull();
			hostPane.setContent(vl);

			ctf.setVisible(true);
			
			TrimmedWindowContent windowContent = (TrimmedWindowContent) vaadinWindow.getContent();
			ExtendedVerticalLayout clientArea = windowContent.getClientArea();
			
			hostPane.setTrimmedWindowClientArea(clientArea);
			//hostPane.setContent(ctf);
			vl.addComponent(ctf);
			
			vaadinUI.addWindow(hostPane);
			vaadinWindow.addClickListener(layoutClickListener);
			
			isShowing = true;
		} 
		else if (!show && isShowing) 
		{
			if (hostPane != null) {
				vaadinUI.removeWindow(hostPane);
				// capture the current shell's bounds
				toolBar.getPersistedState().put(STATE_XSIZE, Float.toString(Float.valueOf(hostPane.getWidth())));
				toolBar.getPersistedState().put(STATE_YSIZE, Float.toString(Float.valueOf(hostPane.getHeight())));
			}
			
			fixToolItemSelection(null);
			
			vaadinWindow.removeClickListener(layoutClickListener);
			
			partService.requestActivation();
			
			isShowing = false;
		}
	}

	private FastView getHostPane() {
		if (hostPane != null)
			return hostPane;

		// Create one
		hostPane = new FastView();
		hostPane.setSide(trimBar.getSide().getValue());
		hostPane.setClosable(false);
		hostPane.setDraggable(false);
		if (trimBar.getSide() == SideValue.RIGHT)
			hostPane.setResizable(false);
		//hostPane.setResizeLazy(true);
		hostPane.setStyleName("loading-window");
		
		//TODO: implement closing by esc
//		hostPane.addListener(SWT.Traverse, new Listener() {
//			public void handleEvent(Event event) {
//				if (event.character == SWT.ESC) {
//					partService.requestActivation();
//				}
//			}
//		});

		
		return hostPane;
	}

}
