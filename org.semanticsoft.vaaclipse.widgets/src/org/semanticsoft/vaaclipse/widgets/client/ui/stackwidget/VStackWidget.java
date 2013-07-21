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

package org.semanticsoft.vaaclipse.widgets.client.ui.stackwidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.semanticsoft.vaaclipse.widgets.client.ui.extlayout.VExtendedVerticalLayout;
import org.semanticsoft.vaaclipse.widgets.common.GeomUtils;
import org.semanticsoft.vaaclipse.widgets.common.Side;
import org.semanticsoft.vaaclipse.widgets.common.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.Util;
import com.vaadin.client.ui.dd.VDragEvent;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.dd.HorizontalDropLocation;

import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.VDragDropUtil;
import fi.jasoft.dragdroplayouts.client.ui.tabsheet.VDDTabSheet;

/**
 * Client side widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VStackWidget extends VDDTabSheet 
//,DragHandlerFinder
{

	/** Set the CSS class name to allow styling. */
	public static final String CLASSNAME = "v-stackwidget";

	public static final String CLICK_EVENT_IDENTIFIER = "click";
	 
	ApplicationConnection client;
	String id;
	
	//Minmax button support
	Element tabs;
	Element scroller;
	String originalScrollerMarginLeftAttribute;
	Element buttonPanel;
	
	//Docking support
	ComplexPanel tabBar;
	
	Integer dockSide;
	
    Element dockZone1;
    Element dockZone2;
    Element dockZone3;
    Element dockZone4;
    
    Element dockZoneContainer;
    
    final String E4_ELEMENT_TYPE = "e4ElementType";
    
	Element maximizeButton;
	Element minimizeButton;
	int state;
	
	private static final int MINIMIZED = -1;
	private static final int NORMAL = 0;
	private static final int MAXIMIZED = 1;
	
	private boolean minmaxEnabled = true;
	
	private String baseURL;
	
	//Relocate of part toolbar support
	//private boolean toolbarRelocated = false;
	private Element toolbarElement;
	private Map<Element, String> overflowRewritedElements;

	/**
	 * The constructor should first call super() to initialize the component and
	 * then handle any initialization relevant to Vaadin.
	 */
	public VStackWidget() {
		super();
		
		this.baseURL = GWT.getHostPageBaseURL();
		
		// Get the tabBar
        tabBar = (ComplexPanel) getChildren().get(0);
		
		tabs = (Element) getElement().getChild(0);
        
        scroller = DOM.getChild(tabs, 1);
        for (int i = 0; i < DOM.getChildCount(scroller); i++)
        {
        	Element child = DOM.getChild(scroller, i);
        	DOM.setStyleAttribute(child, "float", "left");
        }
        
        buttonPanel = DOM.createDiv();
        setStyleName(buttonPanel, "vaadock-tabsheet-button-panel");
        DOM.appendChild(tabs, buttonPanel);
        
        maximizeButton = DOM.createButton();
        setStyleName(maximizeButton, "v-vaadock-tabsheet-maximize-button");
        DOM.sinkEvents(maximizeButton, Event.ONCLICK);
        DOM.appendChild(buttonPanel, maximizeButton);
        
        minimizeButton = DOM.createButton();
        DOM.sinkEvents(minimizeButton, Event.ONCLICK);
        setStyleName(minimizeButton, "v-vaadock-tabsheet-minimize-button");
        DOM.appendChild(buttonPanel, minimizeButton);
        
        setMinmaxEnabled(true);
	}
	
	public boolean isMinmaxEnabled() 
	{
		return minmaxEnabled;
	}
	
	public void setMinmaxEnabled(boolean minmaxEnabled) 
	{
		this.minmaxEnabled = minmaxEnabled;
		
		if (this.minmaxEnabled)
		{
			DOM.setStyleAttribute(buttonPanel, "display", "");
			DOM.setStyleAttribute(scroller, "marginRight", "45px");
		}
		else
		{
			DOM.setStyleAttribute(buttonPanel, "display", "none");
			DOM.setStyleAttribute(scroller, "marginRight", "0px");
		}
	}
	
	@Override
	public void iLayout() {
		super.iLayout();
		
		processPartToolbar();
	}
	
	public void processPartToolbar()
	{
		updateLocationOfButtonPanel();
		updateLocationOfPartToolbar();
	}
	
	private void updateLocationOfButtonPanel()
	{
		if (isMinmaxEnabled()) {
			int buttonPanelHeight = tabs.getOffsetHeight();
	        int buttonPanelMarginTop = -buttonPanelHeight;
	        DOM.setStyleAttribute(buttonPanel, "height", buttonPanelHeight + "px");
	        DOM.setStyleAttribute(buttonPanel, "marginTop", buttonPanelMarginTop + "px");	
		}
	}
	
	public int getState()
	{
		return state;
	}
	
	public void setState(int state)
    {
    	if (!this.isMinmaxEnabled() && state != MINIMIZED && state != NORMAL && state != MAXIMIZED)
    		return;
    	
    	this.state = state;
    	//VConsole.log("VStackWidget: state = " + this.state);
    	
    	if (this.state == NORMAL)
    	{
    		this.state = NORMAL;
			setStyleName(maximizeButton, "v-vaadock-tabsheet-maximize-button");
			setStyleName(minimizeButton, "v-vaadock-tabsheet-minimize-button");
			maximizeButton.setAttribute("style", "");
			minimizeButton.setAttribute("style", "");
    	}
    	else if (this.state == MAXIMIZED)
    	{
    		this.state = MAXIMIZED;
			setStyleName(maximizeButton, "v-vaadock-tabsheet-restore-button");
			setStyleName(minimizeButton, "v-vaadock-tabsheet-minimize-button");
			maximizeButton.setAttribute("style", "");
			minimizeButton.setAttribute("style", "");
    	}
    	else if (this.state == MINIMIZED)
    	{
    		this.state = MINIMIZED;
    		setStyleName(maximizeButton, "v-vaadock-tabsheet-maximize-button");
			setStyleName(minimizeButton, "v-vaadock-tabsheet-restore-button");
			maximizeButton.setAttribute("style", "display: none;");
			minimizeButton.setAttribute("style", "");
    	}
    }
    
    @Override
    public void onBrowserEvent(Event event) 
    {
    	if (event.getTypeInt() == Event.ONCLICK)
    	{
    		if (DOM.eventGetTarget(event) == maximizeButton) 
            {
            	//VConsole.log(event.getType());
            	synchronized (this) 
            	{
            		event.stopPropagation();
            		
            		if (this.state == MAXIMIZED)
            		{
            			setState(NORMAL);
            		}
            		else if (this.state == NORMAL)
            		{
            			setState(MAXIMIZED);
            		}
            		
                	client.updateVariable(this.id, "vaadock_tabsheet_state", new Integer(this.state), true);
    			}
            }
            else if (DOM.eventGetTarget(event) == minimizeButton) 
            {
            	//VConsole.log(event.getType());
            	synchronized (this) 
            	{
            		event.stopPropagation();
            		
            		if (this.state == NORMAL)
            		{
            			setState(MINIMIZED);
            		}
            		else if (this.state == MINIMIZED)
            		{
            			setState(NORMAL);
            		}
            		else if (this.state == MAXIMIZED)
            		{
            			setState(MINIMIZED);
            		}
            		
                	client.updateVariable(this.id, "vaadock_tabsheet_state", new Integer(this.state), true);
    			}
            }
    	}
        
        super.onBrowserEvent(event);
        
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Drag-and-drop support
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public void deEmphasis() 
    {
    	super.deEmphasis();
    }
    
    @Override
    public void emphasis(Element element, VDragEvent event) 
    {
    	super.emphasis(element, event);
    }
    
    @Override
    public boolean postDropHook(VDragEvent drag) 
    {
    	removeDockZone();
    	return super.postDropHook(drag);
    }
    
    @Override
    protected void postOverHook(VDragEvent drag) 
    {
    	super.postOverHook(drag);
    }
    
    @Override
    public void postLeaveHook(VDragEvent drag) 
    {
    	super.postLeaveHook(drag);
    }
    
//  public VDropHandler findDragTarget(com.google.gwt.dom.client.Element element) {
//		
//      try {
//      	Widget w = Util.findWidget(
//                  (com.google.gwt.user.client.Element) element, null);
//          if (w == null) {
//              return null;
//          }
//          
//      	VDragEvent event = VDragAndDropManager.get().getDragEvent();
//      	Widget sourceWidget = (Widget) event.getTransferable().getDragSource();
//  		if (sourceWidget != null && sourceWidget instanceof VDDTabSheet)
//  		{
//              while (!(w instanceof VDDTabSheet) ) {
//                  w = w.getParent();
//                  if (w == null) {
//                      break;
//                  }
//              }
//  		}
//  		else
//  		{
//              while (!(w instanceof VHasDropHandler) ) {
//                  w = w.getParent();
//                  if (w == null) {
//                      break;
//                  }
//              }
//  		}
//  		
//  		if (w == null) {
//              return null;
//          } else {
//              VDropHandler dh = ((VHasDropHandler) w).getDropHandler();
//              return dh;
//          }
//      } catch (Exception e) {
//          return null;
//      }
//
//  }
    
    protected boolean isDockZoneExists()
    {
    	return dockZone1 != null && dockZoneContainer != null;
    }
    
    protected void removeDockZone()
	{
    	if (isDockZoneExists())
        {
    		dockSide = null;
    		DOM.removeChild(dockZoneContainer, dockZone1);
    		DOM.removeChild(dockZoneContainer, dockZone2);
    		DOM.removeChild(dockZoneContainer, dockZone3);
    		DOM.removeChild(dockZoneContainer, dockZone4);
        	dockZone1 = null;
        	dockZone2 = null;
        	dockZone3 = null;
        	dockZone4 = null;
        	dockZoneContainer = null;
    		                    	
        	//VConsole.log("removeDockZone: dock zone removed");
        }
	}
    
    public boolean updateRegion(VDragEvent event)
    {
    	//VConsole.log("updateDropDetails: start");
        Element element = event.getElementOver();
        Widget targetWidget = Util.findWidget(element, null);
        
        if (targetWidget == null)
        {
        	//VConsole.log("updateDropDetails: targetWidget is null. return.");
        	return false;
        }
        
    	if (targetWidget != this)
    	{
    		//VConsole.log("updateDropDetails: targetWidget != this");
    		Widget parent = targetWidget.getParent();
    		while (parent != null && parent != this)
    		{
    			parent = parent.getParent();
    		}
    		
    		if (parent == null)
    		{
    			//VConsole.log("updateDropDetails: parent not finded");
    			return false;
    		}
    		targetWidget = parent;
    		//VConsole.log("updateDropDetails: parent finded");
    	}
    	
    	MouseEventDetails details1 = MouseEventDetailsBuilder
                .buildMouseEventDetails(event.getCurrentGwtEvent(),
                        getElement());
    	
    	int mouseX = details1.getClientX();
		int mouseY = details1.getClientY();
        
        int barLeft = tabBar.getAbsoluteLeft();
        int barTop = tabBar.getAbsoluteTop();
        int barWidth = tabBar.getOffsetWidth();
        int barHeight = tabBar.getOffsetHeight();
        
        boolean overBar = mouseX > barLeft && mouseX < barLeft + barWidth && mouseY > barTop && mouseY < barTop + barHeight;
    	
    	if (overBar)
    	{
    		//VConsole.log("updateDropDetails: over bar");
    		removeDockZone();
    		
    		event.getDropDetails().put("targetWidgetClassName", targetWidget.getClass().getName());
			event.getDropDetails().put("dropType", "DropToTabsheetBar");
    		event.getDropDetails().put("targetWidgetAbsoluteLeft", targetWidget.getAbsoluteLeft());
    		event.getDropDetails().put("targetWidgetAbsoluteTop", targetWidget.getAbsoluteTop());
    		event.getDropDetails().put("targetWidgetOffsetWidth", targetWidget.getOffsetWidth());
    		event.getDropDetails().put("targetWidgetOffsetHeight", targetWidget.getOffsetHeight());
    	}
    	else
    	{
    		//VConsole.log("updateDropDetails: not over bar");
			Object sourceWidget = event.getTransferable().getDragSource();
			if (!(sourceWidget instanceof VStackWidget) && !(sourceWidget instanceof StackWidgetConnector))
			{
				//VConsole.log("updateDropDetails: return, because the sourceWidget is " + sourceWidget.getClass().getName());
				return false;
			}
			
			if (sourceWidget instanceof StackWidgetConnector)
				sourceWidget = ((StackWidgetConnector) sourceWidget).getWidget();
			
			//VConsole.log("updateDropDetails: sourceWidget is VStackWidget or StackWidgetConnector");
			
			VStackWidget targetTabSheet = this;
			
			if (targetTabSheet == sourceWidget && targetTabSheet.getTabCount() <= 1)
			{
				//VConsole.log("updateDropDetails: return, because target is match to source and has only one (current draggable) tab");
				return false;
			}
			
			VExtendedVerticalLayout outerArea = findOuterArea(targetTabSheet);
				
			Widget boundingWidget = null;
			
			if (outerArea != null)
			{
				//VConsole.log("updateDropDetails: outer area is finded");
				if ("area".equals(outerArea.getVariableValue(E4_ELEMENT_TYPE)))
    				boundingWidget = outerArea;	
			}
			else
			{
				boundingWidget = targetTabSheet;
				//VConsole.log("updateDropDetails: outer area not finded, boundingWidget = targetTabSheet");
			}
			
			if (boundingWidget == null)
			{
				//VConsole.log("updateDropDetails: return, because boundingWidget not founded");
				return false;
			}
        	
        	event.getDropDetails().put("targetWidgetClassName", boundingWidget.getClass().getName());
        	event.getDropDetails().put("dropType", "DropToTabsheetBody");
    		event.getDropDetails().put("targetWidgetAbsoluteLeft", boundingWidget.getAbsoluteLeft());
    		event.getDropDetails().put("targetWidgetAbsoluteTop", boundingWidget.getAbsoluteTop());
    		event.getDropDetails().put("targetWidgetOffsetWidth", boundingWidget.getOffsetWidth());
    		event.getDropDetails().put("targetWidgetOffsetHeight", boundingWidget.getOffsetHeight());
    		
			
			int x0 = boundingWidget.getAbsoluteLeft();
			int y0 = boundingWidget.getAbsoluteTop();
			int dx = boundingWidget.getOffsetWidth();
			int dy = boundingWidget.getOffsetHeight();
			
			int docPrcnt = 30;
			double docX = dx * docPrcnt / 100;
			double docY = dy * docPrcnt / 100;
			double d = 1;
			
			Vector mousePos = Vector.valueOf(mouseX, mouseY);
			
			Integer side = GeomUtils.findDockSide(x0, y0, dx, dy, docX, docY, mousePos);
			//VConsole.log("updateDropDetails: finded dock side = " + side + ", old dock side = " + dockSide);
			if (side != null)
			{
				double _x = 0, _y = 0, _w = 0, _h = 0;
				
				if (side == Side.LEFT)
				{
					_x = d;
					_y = d;
					_w = docX - d;
					_h = dy - 2*d;
				}
				else if (side == Side.TOP)
				{
					_x = d;
					_y = d;
					_w = dx - 2*d;
					_h = docY - d;
				}
				else if (side == Side.RIGHT)
				{
					_x = dx - docX;
					_y = d;
					_w = docX - d;
					_h = dy - 2*d;
				}
				else if (side == Side.BOTTOM)
				{
					_x = d;
					_y = dy - docY;
					_w = dx - 2*d;
					_h = docY - d;
				}
				else if (side == Side.CENTER)
				{
					_x = d;
					_y = d;
					_w = dx - 2*d;
					_h = dy - 2*d;
				}
				else
					return false;
				
				_x = x0 + _x;
				_y = y0 + _y;
				
				if (dockZone1 == null)
				{
					dockZone1 = DOM.createDiv();
					dockZone2 = DOM.createDiv();
					dockZone3 = DOM.createDiv();
					dockZone4 = DOM.createDiv();
				}
				
				//VConsole.log("updateDropDetails: x=" + _x + "; y=" + _y + "; w=" + _w + "; h=" + _h);
				
				if (side != dockSide)
	    		{
					//VConsole.log("updateDropDetails: dock side will be updated");
	    			int l = 3;
	    			String style1 = "position: absolute; left: " + _x + "px; top: " + _y + 
	    					"px; width: " + _w + "px; height: " + l + "px; background-image: url(" + baseURL + "VAADIN/themes/dragdrop/vaadock/img/dockzone.png); z-index: 20000;";
					dockZone1.setAttribute("style", style1);
					
					String style2 = "position: absolute; left: " + _x + "px; top: " + (_y + _h - l) + 
	    					"px; width: " + _w + "px; height: " + l + "px; background-image: url(" + baseURL + "VAADIN/themes/dragdrop/vaadock/img/dockzone.png); z-index: 20000;";
					dockZone2.setAttribute("style", style2);
					
					String style3 = "position: absolute; left: " + _x + "px; top: " + _y + 
	    					"px; width: " + l + "px; height: " + _h + "px; background-image: url(" + baseURL + "VAADIN/themes/dragdrop/vaadock/img/dockzone.png); z-index: 20000;";
					dockZone3.setAttribute("style", style3);
					
					String style4 = "position: absolute; left: " + (_x + _w - l) + "px; top: " + _y + 
	    					"px; width: " + l + "px; height: " + _h + "px; background-image: url(" + baseURL + "VAADIN/themes/dragdrop/vaadock/img/dockzone.png); z-index: 20000;";
					dockZone4.setAttribute("style", style4);
					
	    	        //setStyleName(dockZone, "v-etot-sukin-syn");
	    	        //dockZoneContainer = boundingWidget.getElement();
					dockZoneContainer = RootPanel.get().getElement();
	    	        DOM.appendChild(dockZoneContainer, dockZone1);
	    	        DOM.appendChild(dockZoneContainer, dockZone2);
	    	        DOM.appendChild(dockZoneContainer, dockZone3);
	    	        DOM.appendChild(dockZoneContainer, dockZone4);
	    	        
	    	        dockSide = side;
	    		}
			}
    	}
    	
    	return true;
    }
	
	/**
     * Updates the drop details while dragging. This is needed to ensure client
     * side criterias can validate the drop location.
     * 
     * @param widget
     *            The container which we are hovering over
     * @param event
     *            The drag event
     */
	@Override
    public void updateDropDetails(VDragEvent event) 
    {
		if (!updateRegion(event))
			return;
		
		Element element = event.getElementOver();
		Widget targetWidget = Util.findWidget(element, null);
		
		//--
		if (tabBar.getElement().isOrHasChild(element)) {

            if (targetWidget == tabBar) {
                // Ove3r the spacer

                // Add index
                event.getDropDetails().put(Constants.DROP_DETAIL_TO,
                        tabBar.getWidgetCount() - 1);

                // Add drop location
                event.getDropDetails().put(
                        Constants.DROP_DETAIL_HORIZONTAL_DROP_LOCATION,
                        HorizontalDropLocation.RIGHT);

            } else {

                // Add index
                event.getDropDetails().put(Constants.DROP_DETAIL_TO,
                        getTabPosition(targetWidget));

                // Add drop location
                HorizontalDropLocation location = VDragDropUtil
                        .getHorizontalDropLocation(element, Util
                                .getTouchOrMouseClientX(event
                                        .getCurrentGwtEvent()),
                                        getTabLeftRightDropRatio());
                event.getDropDetails().put(
                        Constants.DROP_DETAIL_HORIZONTAL_DROP_LOCATION,
                        location);
            }
        }
		
		// Add mouse event details
        MouseEventDetails details = MouseEventDetailsBuilder
                .buildMouseEventDetails(event.getCurrentGwtEvent(),
                        getElement());
        event.getDropDetails().put(Constants.DROP_DETAIL_MOUSE_EVENT,
                details.serialize());
    }
		
	private VExtendedVerticalLayout findOuterArea(Widget w)
    {
    	if (w instanceof VExtendedVerticalLayout)
    	{
    		//VConsole.log("VExtendedVerticalLayout finded in parent hierarchy");
    		VExtendedVerticalLayout bl = (VExtendedVerticalLayout) w;
			final String type = bl.getVariableValue(E4_ELEMENT_TYPE);
			if (type != null && ("area".equals(type) || "perspective".equals(type) || "window".equals(type)))
			{
				//VConsole.log("VExtendedVerticalLayout finded in parent hierarchy with type = " + type);
				return bl;
			}
    	}
    	
    	if (w.getParent() != null)
			return findOuterArea(w.getParent());
		else
			return null;
    }
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Part toolbar relocate support
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	private void updateLocationOfPartToolbar()
	{
		if (tp.getWidgetCount() == 0)
			return;
		
		Widget selectedWidget = tp.getWidget(tp.getVisibleWidget());
		
		if (selectedWidget == null || selectedWidget.getElement() == null)
			return;
		
		List pathToToolbar = findToolbarElement(selectedWidget.getElement(), 12);
		if (pathToToolbar.isEmpty())
			return;
		
		toolbarElement = (Element) pathToToolbar.get(pathToToolbar.size() - 1);
		
		String mR = DOM.getStyleAttribute(toolbarElement, "marginRight");
		String mT = DOM.getStyleAttribute(toolbarElement, "marginTop");
		
		if (mR != null && mT != null && !mR.trim().isEmpty() && !mT.trim().isEmpty())
		{//toolbar is relocated, check is there are space in tabs panel and if no, restore location of toolbar
			if (!hasSpace(toolbarElement))
			{
				restoreLocationOfPartToolbar();
			}
		}
		else
		{//toolbar is not relocated, check is there are space in tabs panel and if yes, move toolbar to tabs panel
			if (hasSpace(toolbarElement))
			{
				if (activeTabIndex >= 0 && this.getParent() != null)
					changeLocationOfPartToolbar(selectedWidget, pathToToolbar);	
			}
		}
	}
	
	private boolean hasSpace(Element toolbarElement)
	{
		Element tb = (Element) tabs.getChild(0);
		Element spacertd = (Element) tb.getChild(0).getChild(0).getLastChild();
		
		return tb.getOffsetWidth() - DOM.getElementPropertyInt((Element) spacertd.cast(), "offsetWidth") < getOffsetWidth()
                - (isMinmaxEnabled() ? buttonPanel.getOffsetWidth() : 0) - toolbarElement.getOffsetWidth() - 10;
	}
	
	private void changeLocationOfPartToolbar(Widget selectedWidget, List pathToToolbar)
	{
		overflowRewritedElements = new HashMap<Element, String>();
		Element selectedElementParent = selectedWidget.getParent().getElement();
		//Two DOM-elements of selectedWidget.getParent() should be included to overflow rewrite
		pathToToolbar.add(0, selectedElementParent.getChild(0));
		pathToToolbar.add(0, selectedElementParent);
		for (int i = 0; i < pathToToolbar.size(); i++)
		{
			Element element = (Element) pathToToolbar.get(i);
			String overflow = DOM.getElementProperty((Element) element, "overflow");
			if (!"".equals(overflow) && !"visible".equals(overflow) && !"".equals("inherit"))
			{
				DOM.setStyleAttribute(element, "overflow", "visible");
				overflowRewritedElements.put(element, overflow);
			}
		}
		
		updateGeometry();
	}

	private void updateGeometry()
	{
		int marginRight = (isMinmaxEnabled() ? buttonPanel.getOffsetWidth() : 0) + 5;
		int marginTop = tabs.getAbsoluteTop() - toolbarElement.getAbsoluteTop();
		
		DOM.setStyleAttribute(toolbarElement, "marginRight", marginRight + "px");
		DOM.setStyleAttribute(toolbarElement, "marginTop", marginTop + "px");
	}
	
	void restoreLocationOfPartToolbar()
	{
		if (toolbarElement == null)
			return;
		
		DOM.setStyleAttribute(toolbarElement, "marginRight", "");
		DOM.setStyleAttribute(toolbarElement, "marginTop", "");
		
		toolbarElement = null;
		overflowRewritedElements = null;
	}
	
	/**
	 * Find the toolbar element in rootElement. Searching in first elements throw first n element
	 * Return the path to toolbar element in order from rootElement to toolbar including rootElement and toolbar
	 * @return
	 */
	private List findToolbarElement(Element rootElement, int n)
	{
		List pathToParent = new ArrayList();
		pathToParent.add(rootElement);
		Element element = rootElement;
		int depth = 0;
		while (element.getChildCount() > 0 && ++depth < n)
		{
			Node node = element.getChild(0);
			if (node instanceof Element)
			{
				element = (Element) node;
				pathToParent.add(element);
				String className = element.getClassName();
				if (className != null && !className.contains("mparttoolbararea") && className.contains("mparttoolbar"))
					break;
			}
			else
				return new ArrayList();
		}
		
		if (element == null)
			pathToParent.clear();
		
		return pathToParent;
	}
}
