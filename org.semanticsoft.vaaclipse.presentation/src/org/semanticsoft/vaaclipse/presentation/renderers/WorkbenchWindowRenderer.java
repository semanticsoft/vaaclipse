/*******************************************************************************
 * Copyright (c) 2011 Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *******************************************************************************/

package org.semanticsoft.vaaclipse.presentation.renderers;

import java.util.Map;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindowElement;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.semanticsoft.vaaclipse.presentation.constants.Constants;
import org.semanticsoft.vaaclipse.presentation.engine.PresentationEngine;
import org.semanticsoft.vaaclipse.presentation.widgets.SashWidgetHorizontal;

import com.vaadin.Application;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


@SuppressWarnings("restriction")
public class WorkbenchWindowRenderer extends GenericRenderer {

	private VerticalLayout windowBody;
	private VerticalLayout windowCenterArea;
	private HorizontalLayout helperLayout;
	private SashWidgetHorizontal topSplitPanel;
	
	@Inject
	private IEclipseContext eclipseContext;

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (element instanceof MWindow) {
			MWindow mWindow = (MWindow) element;
			Window window = new Window();
			window.setPositionX(mWindow.getX());
			window.setPositionX(mWindow.getY());
			window.setWidth(mWindow.getWidth());
			window.setHeight(mWindow.getHeight());
			window.setCaption(mWindow.getLocalizedLabel());
			element.setWidget(window);
			((MWindow) element).getContext().set(Window.class, window);
			Application vaadinapp = (Application) eclipseContext.get("vaadinapp");
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
			Window vWindow = (Window) element.getWidget();
			PresentationEngine engine = (PresentationEngine) context.get(IPresentationEngine.class.getName());
			
			VerticalLayout windowContent = new VerticalLayout();
			windowContent.setSizeFull();
			vWindow.setContent(windowContent);
			
			if (window.getMainMenu() != null) {
				engine.createGui(window.getMainMenu(), element);
				com.vaadin.ui.Component menu = (com.vaadin.ui.Component) window.getMainMenu().getWidget();
				menu.setWidth("100%");
				windowContent.addComponent(menu);
			}
			
			//-------------------------------------------------------------------
			windowBody = new VerticalLayout();
			windowBody.setSizeFull();
			windowContent.addComponent(windowBody);
			windowContent.setExpandRatio(windowBody, 100);
			
			windowCenterArea = new VerticalLayout();
			windowCenterArea.setSizeFull();
			
			Component perspectiveStackPanel = null;
			for (MUIElement e : element.getChildren()) {
				if (e.getWidget() != null) {
					if (e instanceof MPerspectiveStack)
					{
						perspectiveStackPanel = ((PerspectiveStackRenderer)e.getRenderer()).getPerspectivestack2PerspectiveswitcherMapping().get(e);
					}
					
					windowCenterArea.addComponent((com.vaadin.ui.Component) e.getWidget());
				}
			}
			
			helperLayout = new HorizontalLayout();
			helperLayout.setSizeFull();
			
			//Top panel - it contains the top trimbar and the perspective stack panel
			topSplitPanel = new SashWidgetHorizontal();
			topSplitPanel.setWidth("100%");
			topSplitPanel.setHeight(Constants.trimBarThickness);
			topSplitPanel.setSplitPosition(85);
			CssLayout perspectiveStackPanelContainer = null;
			if (perspectiveStackPanel != null)
			{
				perspectiveStackPanelContainer = new CssLayout();
				perspectiveStackPanelContainer.setWidth("100px");
				perspectiveStackPanelContainer.setWidth("100px");
				perspectiveStackPanelContainer.setSizeUndefined();
				perspectiveStackPanelContainer.addComponent(perspectiveStackPanel);	
			}
			
			topSplitPanel.setFirstComponent(null);
			topSplitPanel.setSecondComponent(perspectiveStackPanelContainer); //perspectiveStackPanelContainer can be null - it's ok
			windowBody.addComponent(topSplitPanel);
			//------------------------
			
			helperLayout.addComponent(windowCenterArea);
			helperLayout.setExpandRatio(windowCenterArea, 100);
			windowBody.addComponent(helperLayout);
			windowBody.setExpandRatio(helperLayout, 100);
			//-------------------------------------------------------------------
			
			if (window instanceof MTrimmedWindow) {
				MTrimmedWindow tWindow = (MTrimmedWindow) window;
				for (MTrimBar trim : tWindow.getTrimBars()) {
					com.vaadin.ui.Component c = (com.vaadin.ui.Component) engine.createGui(trim);
					//windowBody.setExpandRatio(helperLayout, 0);
					if (c != null) {
						switch (trim.getSide()) {
						case BOTTOM:
							c.setWidth("100%");
							c.setHeight(Constants.trimBarThickness);
							windowBody.addComponent(c, windowBody.getComponentCount() - 1);
							break;
						case LEFT:
							c.setWidth(Constants.trimBarThickness);
							c.setHeight("100%");
							helperLayout.addComponent(c, 0);
							break;
						case RIGHT:
							c.setWidth(Constants.trimBarThickness);
							c.setHeight("100%");
							helperLayout.addComponent(c, windowBody.getComponentCount() - 1);
							break;
						case TOP:
							c.setWidth("99%");
							c.setHeight(Constants.trimBarThickness);
							topSplitPanel.setFirstComponent(c);
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public void refreshPlatformElement(MElementContainer<?> element) {
		if ((MUIElement) element instanceof MWindow) {
			MWindow window = (MWindow) ((MUIElement) element);
			
			if (window instanceof MTrimmedWindow) {
				MTrimmedWindow tWindow = (MTrimmedWindow) window;
				for (MTrimBar trim : tWindow.getTrimBars()) {
//					((TrimBarRenderer)trim.getRenderer()).refreshPlatformElement(trim);
					com.vaadin.ui.Component c = (com.vaadin.ui.Component) trim.getWidget();
					if (c != null) {
						boolean isVisible = trim.isVisible();
						if (!isVisible) {
							windowBody.removeComponent(c);
						} else {
							switch (trim.getSide()) {
							case BOTTOM:
								if (windowBody.getComponentIndex(helperLayout) < windowBody.getComponentCount())
									windowBody.removeComponent(windowBody.getComponent(windowBody.getComponentCount()-1));
								windowBody.addComponent(c, windowBody.getComponentCount() - 1);
								break;
							case LEFT:
								if (helperLayout.getComponentIndex(windowCenterArea) > 0)
									helperLayout.removeComponent(helperLayout.getComponent(0));
								helperLayout.addComponent(c, 0);
								break;
							case RIGHT:
								if (helperLayout.getComponentIndex(windowCenterArea) < helperLayout.getComponentCount())
									helperLayout.removeComponent(helperLayout.getComponent(helperLayout.getComponentCount()-1));
								helperLayout.addComponent(c, helperLayout.getComponentCount() - 1);
								break;
							case TOP:
								c.setWidth("100%");
								topSplitPanel.setFirstComponent(c);
								break;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void removeChild(MUIElement element, MElementContainer<MUIElement> parent) {
		
	}
}