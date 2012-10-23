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

package org.semanticsoft.vaaclipse.widgets;

import org.semanticsoft.vaadinaddons.boundsinfo.BoundsinfoVerticalLayout;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * @author rushan
 * 
 */
public class WorkbenchWindow extends Window
{
	private VerticalLayout windowBody;
	private BoundsinfoVerticalLayout windowCenterArea;
	private HorizontalLayout helperLayout;
	private SashWidgetHorizontal topSplitPanel;
	private CssLayout  perspectiveStackPanelContainer;
	private VerticalLayout windowContent;
	
	private VerticalLayout leftBarContainer = new VerticalLayout(); 
	private VerticalLayout rightBarContainer = new VerticalLayout();
	private VerticalLayout topBarContainer = new VerticalLayout(); 
	private HorizontalLayout bottomBarContainer = new HorizontalLayout();
	
	public WorkbenchWindow()
	{
//		leftBarContainer.setVisible(false);
//		rightBarContainer.setVisible(false);
//		topBarContainer.setVisible(false);
//		bottomBarContainer.setVisible(false);
		
		//leftBarContainer.setSizeFull();
		leftBarContainer.setWidth(-1);
		leftBarContainer.setHeight("100%");
		
		rightBarContainer.setWidth(-1);
		rightBarContainer.setHeight("100%");
		
		topBarContainer.setHeight(-1);
		topBarContainer.setWidth("100%");
		topBarContainer.setMargin(false);
		
		//bottomBarContainer.setSizeFull();
		bottomBarContainer.setHeight(-1);
		bottomBarContainer.setWidth("100%");
		bottomBarContainer.setMargin(false);
		
		windowContent = new VerticalLayout();
		windowContent.setSizeFull();
		this.setContent(windowContent);
		
		windowBody = new VerticalLayout();
		windowBody.setSizeFull();
		windowContent.addComponent(windowBody);
		windowContent.setExpandRatio(windowBody, 100);
		
		windowCenterArea = new BoundsinfoVerticalLayout();
		windowCenterArea.setSizeFull();

		
		helperLayout = new HorizontalLayout();
		helperLayout.setSizeFull();
		
		//Top panel - it contains the top trimbar and the perspective stack panel
		topSplitPanel = new SashWidgetHorizontal();
		topSplitPanel.setWidth("100%");
		topSplitPanel.setHeight(-1);
		topSplitPanel.setSplitPosition(85);
		perspectiveStackPanelContainer  = new CssLayout();
		perspectiveStackPanelContainer.setWidth("100px");
		perspectiveStackPanelContainer.setSizeUndefined();
		
		topSplitPanel.setFirstComponent(null);
		topSplitPanel.setSecondComponent(perspectiveStackPanelContainer);
		windowBody.addComponent(topSplitPanel);
		//------------------------
		helperLayout.addComponent(leftBarContainer);
		helperLayout.addComponent(windowCenterArea);
		helperLayout.addComponent(rightBarContainer);
		helperLayout.setExpandRatio(windowCenterArea, 100);
		
		windowBody.addComponent(topBarContainer);
		windowBody.addComponent(helperLayout);
		windowBody.setExpandRatio(helperLayout, 100);
		windowBody.addComponent(bottomBarContainer);
		//-------------------------------------------------------------------
	}
	
	public BoundsinfoVerticalLayout getClientArea()
	{
		return windowCenterArea;
	}
	
	public void setMenuBar(MenuBar menuBar)
	{
		for (int i = 0; i < windowContent.getComponentCount(); i++)
		{
			Component c = windowContent.getComponent(i);
			if (c instanceof MenuBar)
				windowContent.removeComponent(c);
		}
		
		menuBar.setWidth("100%");
		windowContent.addComponent(menuBar, 0);
	}
	
	public CssLayout getPerspectiveStackPanel()
	{
		return (CssLayout) perspectiveStackPanelContainer.getComponentIterator().next();
	}
	
	public void setPerspectiveStackPanel(Component perspectiveStackPanel)
	{
		perspectiveStackPanelContainer.removeAllComponents();
		if (perspectiveStackPanel != null)
		{
			perspectiveStackPanelContainer.addComponent(perspectiveStackPanel);
			topSplitPanel.refreshState();
		}
	}
	
	public void setLeftBar(Component bar)
	{
		if (bar == null)
		{
			leftBarContainer.removeAllComponents();
			return;
		}
		
		bar.setWidth(-1);
		bar.setHeight("100%");
		
		leftBarContainer.removeAllComponents();
		leftBarContainer.addComponent(bar);
	}
	
	public void setRightBar(Component bar)
	{
		if (bar == null)
		{
			rightBarContainer.removeAllComponents();
			return;
		}
		
		bar.setWidth(-1);
		bar.setHeight("100%");
		
		rightBarContainer.removeAllComponents();
		rightBarContainer.addComponent(bar);
	}
	
	public void setBottomBar(Component bar)
	{
		if (bar == null)
		{
			bottomBarContainer.removeAllComponents();
			return;
		}
		
		bar.setWidth("100%");
		bar.setHeight(-1);
		
		bottomBarContainer.removeAllComponents();
		bottomBarContainer.addComponent(bar);
	}
	
	public void setTopBar(Component bar)
	{
		if (bar == null)
		{
			topSplitPanel.setFirstComponent(bar);
			return;
		}
		
		bar.setWidth("99%");
		bar.setHeight(-1);
		topSplitPanel.setFirstComponent(bar);
		topSplitPanel.refreshState();
	}
}
