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

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.semanticsoft.vaaclipse.presentation.engine.PresentationEngine;
import org.semanticsoft.vaaclipse.widgets.WorkbenchWindow;

import com.vaadin.Application;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Window;


@SuppressWarnings("restriction")
public class WorkbenchWindowRenderer extends GenericRenderer {

	@Inject
	private IEclipseContext eclipseContext;
	
	@Inject
	Application vaadinapp;

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (element instanceof MWindow) {
			MWindow mWindow = (MWindow) element;
			WorkbenchWindow window = new WorkbenchWindow();
			window.setPositionX(mWindow.getX());
			window.setPositionX(mWindow.getY());
			window.setWidth(mWindow.getWidth());
			window.setHeight(mWindow.getHeight());
			window.setCaption(mWindow.getLocalizedLabel());
			element.setWidget(window);
			((MWindow) element).getContext().set(Window.class, window);
			//TODO:temp
			window.setSizeFull();
			vaadinapp.setMainWindow(window);
			eclipseContext.set(Window.class, window); //main window - temp hack
		}
	}

	@Override
	public void hookControllerLogic(final MUIElement element) {
//		if (element instanceof MWindow) {
//			final MWindow mWindow = (MWindow) element;
//			Window window = (Window) mWindow.getWidget();
//			window.addWindowListener(new WindowAdapter() {
//				@Override
//				public void windowClosing(WindowEvent evt) {
//					if ((MUIElement) element.getParent() instanceof MApplication) {
//						MApplication application = (MApplication) (MUIElement) element.getParent();
//						synchronized (application) {
//							application.notifyAll();
//						}
//					}
//				}
//			});
//
//			window.getContentPane().addHierarchyBoundsListener(new HierarchyBoundsListener() {
//
//				@Override
//				public void ancestorMoved(HierarchyEvent e) {
//					mWindow.setX(e.getChanged().getX());
//					mWindow.setY(e.getChanged().getY());
//				}
//
//				@Override
//				public void ancestorResized(HierarchyEvent e) {
//					mWindow.setWidth(e.getChanged().getWidth());
//					mWindow.setHeight(e.getChanged().getHeight());
//				}
//			});
//		}
	}

	@Override
	public void processContents(MElementContainer<MUIElement> element) {
		if ((MUIElement) element instanceof MWindow) {
			MWindow window = (MWindow) ((MUIElement) element);
			WorkbenchWindow vWindow = (WorkbenchWindow) element.getWidget();
			PresentationEngine engine = (PresentationEngine) context.get(IPresentationEngine.class.getName());
			
			for (MUIElement e : element.getChildren()) {
				if (e.getWidget() != null) {
					if (e instanceof MPerspectiveStack)
					{
						vWindow.setPerspectiveStackPanel(
							((PerspectiveStackRenderer)e.getRenderer()).getPerspectivestack2PerspectiveswitcherMapping().get(e));
					}
					
					vWindow.getClientArea().addComponent((com.vaadin.ui.Component) e.getWidget());
				}
			}
			
			if (window.getMainMenu() != null) {
				engine.createGui(window.getMainMenu());
				MenuBar menu = (MenuBar) window.getMainMenu().getWidget();
				vWindow.setMenuBar(menu);
			}
			
			//-------------------------------------------------------------------
			if (window instanceof MTrimmedWindow) {
				MTrimmedWindow tWindow = (MTrimmedWindow) window;
				for (MTrimBar trim : tWindow.getTrimBars()) {
					Component c = (com.vaadin.ui.Component) engine.createGui(trim);
					switch (trim.getSide()) {
					case BOTTOM:
						vWindow.setBottomBar(c);
						break;
					case LEFT:
						vWindow.setLeftBar(c);
						break;
					case RIGHT:
						vWindow.setRightBar(c);
						break;
					case TOP:
						vWindow.setTopBar(c);
						break;
					}
				}
			}
		}
	}

	@Override
	public void refreshPlatformElement(MElementContainer<?> element) {
		if ((MUIElement) element instanceof MTrimmedWindow) {
			MTrimmedWindow window = (MTrimmedWindow) ((MUIElement) element);
			WorkbenchWindow vWindow = (WorkbenchWindow) element.getWidget();
			
			for (MTrimBar trim : window.getTrimBars()) {
				Component c = (Component) trim.getWidget();
				switch (trim.getSide()) {
				case BOTTOM:
					vWindow.setBottomBar(c);
					break;
				case LEFT:
					vWindow.setLeftBar(c);
					break;
				case RIGHT:
					vWindow.setRightBar(c);
					break;
				case TOP:
					vWindow.setTopBar(c);
					break;
				}
			}
		
		}
	}

	@Override
	public void removeChild(MUIElement element, MElementContainer<MUIElement> parent) {
		
	}
}