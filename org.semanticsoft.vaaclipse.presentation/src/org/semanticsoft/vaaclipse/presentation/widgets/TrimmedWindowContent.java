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

package org.semanticsoft.vaaclipse.presentation.widgets;

import org.semanticsoft.vaaclipse.widgets.ExtendedVerticalLayout;
import org.semanticsoft.vaaclipse.widgets.TopbarComponent;
import org.semanticsoft.vaaclipse.widgets.common.Constants;

import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

/**
 * @author rushan
 * 
 */
public class TrimmedWindowContent extends VerticalLayout
{
	private VerticalLayout windowBody;
	private ExtendedVerticalLayout windowCenterArea;
	private HorizontalLayout helperLayout;
	private GridLayout topContainerPanel;
	
	private VerticalLayout leftBarContainer = new VerticalLayout(); 
	private VerticalLayout rightBarContainer = new VerticalLayout();
	private VerticalLayout topBarContainer = new VerticalLayout(); 
	private HorizontalLayout bottomBarContainer = new HorizontalLayout();
	
	private TopbarComponent topbar = new TopbarComponent();
	
	private boolean boundsValide = false;
	
	public TrimmedWindowContent()
	{
		leftBarContainer.setWidth(-1, Unit.PIXELS);
		leftBarContainer.setHeight("100%");
		
		rightBarContainer.setWidth(-1, Unit.PIXELS);
		rightBarContainer.setHeight("100%");
		
		topBarContainer.setHeight(-1, Unit.PIXELS);
		topBarContainer.setWidth("100%");
		topBarContainer.setMargin(false);
		
		//bottomBarContainer.setSizeFull();
		bottomBarContainer.setHeight(-1, Unit.PIXELS);
		bottomBarContainer.setWidth("100%");
		bottomBarContainer.setMargin(false);
		
		this.setSizeFull();
		
		windowBody = new VerticalLayout();
		windowBody.setSizeFull();
		this.addComponent(windowBody);
		this.setExpandRatio(windowBody, 100);
		
		windowCenterArea = new ExtendedVerticalLayout();
		windowCenterArea.setVariableValue(Constants.E4_ELEMENT_TYPE, Constants.TRIMMED_WINDOW_CLIENT_AREA);
		windowCenterArea.setSizeFull();
		
		helperLayout = new HorizontalLayout();
		helperLayout.setSizeFull();
		
		//Top panel - it contains the top trimbar and the perspective stack panel
		topContainerPanel = new GridLayout(2, 1);
		topContainerPanel.setColumnExpandRatio(0, 100);
		topContainerPanel.setColumnExpandRatio(1, 0);
		topContainerPanel.setSizeUndefined();
		topContainerPanel.setWidth("100%");
		
		windowBody.addComponent(topContainerPanel);
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
	
	public ExtendedVerticalLayout getClientArea()
	{
		return windowCenterArea;
	}
	
	public void setMenuBar(MenuBar menuBar)
	{
		for (int i = 0; i < this.getComponentCount(); i++)
		{
			Component c = this.getComponent(i);
			if (c instanceof MenuBar)
				this.removeComponent(c);
		}
		
		menuBar.setWidth("100%");
		this.addComponent(menuBar, 0);
	}
	
	public HorizontalLayout getPerspectiveStackPanel()
	{
		return (HorizontalLayout) topContainerPanel.getComponent(0, 1);
	}
	
	public void setPerspectiveStackPanel(HorizontalLayout perspectiveStackPanel)
	{
		if (perspectiveStackPanel == null)
		{
			this.topContainerPanel.removeComponent(1, 0);
		}
		else
		{
			perspectiveStackPanel.setSizeUndefined();
			this.topContainerPanel.addComponent(perspectiveStackPanel, 1, 0);	
		}
	}
	
	public void setLeftBar(Component bar)
	{
		if (bar == null)
		{
			leftBarContainer.removeAllComponents();
			return;
		}
		
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
		
		bottomBarContainer.removeAllComponents();
		bottomBarContainer.addComponent(bar);
	}
	
	public void setTopBar(Component bar)
	{
		if (bar == null)
		{
			this.topContainerPanel.removeComponent(topbar);
			return;
		}
		
		if (this.topContainerPanel.getComponent(0, 0) == null)
		{
			this.topContainerPanel.addComponent(topbar, 0, 0);
		}
		
		topbar.setContent(bar);
	}
	
	public TopbarComponent getTopbar() 
	{
		return topbar;
	}
	
	//-----------------------------------
	//-----------------------------------
	public boolean isBoundsValid()
	{
		return this.boundsValide;
	}
	
	public void invalidateBounds()
	{
		this.boundsValide = false;
	}
	
//	private void updateBounds(ComponentContainer container, Bounds currentBounds)
//	{
//		if (container instanceof ExtendedVerticalLayout)
//		{
//			ExtendedVerticalLayout bvl = (ExtendedVerticalLayout) container;
//			bvl.setBounds(currentBounds);
//		}
//		else if (container instanceof StackWidget)
//		{
//			StackWidget bvl = (StackWidget) container;
//			bvl.setBounds(currentBounds);
//		}
//		
//		if (container instanceof SashWidget)
//		{
//			AbstractSplitPanel splitPanel = (AbstractSplitPanel) container;
//			float splitPos = splitPanel.getSplitPosition() / 100;
//			if (splitPanel instanceof HorizontalSplitPanel)
//			{
//				int firstBoundsWidth = (int)(splitPos*currentBounds.w);
//				if (splitPanel.getFirstComponent() instanceof ComponentContainer)
//				{
//					Bounds leftBounds = new Bounds(
//							currentBounds.x, 
//							currentBounds.y, 
//							firstBoundsWidth, 
//							currentBounds.h
//						);
//					updateBounds((ComponentContainer) splitPanel.getFirstComponent(), leftBounds);	
//				}
//				
//				if (splitPanel.getSecondComponent() instanceof ComponentContainer)
//				{
//					Bounds rightBounds = new Bounds(
//							currentBounds.x + firstBoundsWidth, 
//							currentBounds.y, 
//							(int)(currentBounds.w - firstBoundsWidth), 
//							currentBounds.h
//						);
//					updateBounds((ComponentContainer) splitPanel.getSecondComponent(), rightBounds);
//				}
//			}
//			else if (splitPanel instanceof VerticalSplitPanel)
//			{
//				int firstBoundsHeight = (int)(splitPos*currentBounds.h);
//				if (splitPanel.getFirstComponent() instanceof ComponentContainer)
//				{
//					Bounds leftBounds = new Bounds(
//							currentBounds.x, 
//							currentBounds.y, 
//							currentBounds.w, 
//							firstBoundsHeight
//						);
//					updateBounds((ComponentContainer) splitPanel.getFirstComponent(), leftBounds);	
//				}
//				
//				if (splitPanel.getSecondComponent() instanceof ComponentContainer)
//				{
//					Bounds rightBounds = new Bounds(
//							currentBounds.x, 
//							currentBounds.y + firstBoundsHeight, 
//							(int)(currentBounds.w), 
//							currentBounds.h - firstBoundsHeight
//						);
//					updateBounds((ComponentContainer) splitPanel.getSecondComponent(), rightBounds);
//				}
//			}
//		}
//		else if (container instanceof ComponentContainer)
//		{
//			Iterator<Component> it = container.getComponentIterator();
//			while (it.hasNext())
//			{
//				Component c = it.next();
//				if (c instanceof ComponentContainer)
//				{
//					updateBounds((ComponentContainer) c, currentBounds);		
//				}
//			}
//		}
//	}
}
