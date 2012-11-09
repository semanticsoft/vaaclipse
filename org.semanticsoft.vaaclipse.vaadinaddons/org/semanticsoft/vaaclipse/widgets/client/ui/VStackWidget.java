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

package org.semanticsoft.vaaclipse.widgets.client.ui;

import org.semanticsoft.vaaclipse.widgets.client.ui.GeomUtils.Side;
import org.semanticsoft.vaadinaddons.boundsinfo.client.ui.BoundsUpdateManager;
import org.semanticsoft.vaadinaddons.boundsinfo.client.ui.VBoundsinfoVerticalLayout;

import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.RenderSpace;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.dd.HorizontalDropLocation;
import com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VAcceptCallback;
import com.vaadin.terminal.gwt.client.ui.dd.VDragEvent;
import com.vaadin.terminal.gwt.client.ui.layout.ChildComponentContainer;

import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;
import com.vaadin.terminal.gwt.client.VCaption;
import com.vaadin.terminal.gwt.client.VConsole;
import com.vaadin.terminal.gwt.client.ui.VPanel;
import com.vaadin.terminal.gwt.client.ui.VTabsheet;
import com.vaadin.terminal.gwt.client.ui.VTabsheetPanel;
import com.vaadin.terminal.gwt.client.ui.VVerticalLayout;
import com.vaadin.terminal.gwt.client.ui.dd.HorizontalDropLocation;
import com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VAcceptCallback;
import com.vaadin.terminal.gwt.client.ui.dd.VDragAndDropManager;
import com.vaadin.terminal.gwt.client.ui.dd.VDragEvent;
import com.vaadin.terminal.gwt.client.ui.dd.VDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VHasDropHandler;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.VDDTabSheet;
import fi.jasoft.dragdroplayouts.client.ui.VDragDropUtil;

/**
 * Client side widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VStackWidget extends VDDTabSheet implements Paintable {

	/** Set the CSS class name to allow styling. */
	public static final String CLASSNAME = "v-stackwidget";

	public static final String CLICK_EVENT_IDENTIFIER = "click";

	/** The client side widget identifier */
	protected String paintableId;

	/** Reference to the server connection object. */
	protected ApplicationConnection client;
	
	private BoundsUpdateManager updateManager;
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	private String id;
    
    private Element dockZone1;
    private Element dockZone2;
    private Element dockZone3;
    private Element dockZone4;
    
    private Element dockZoneContainer;
    
    final String E4_ELEMENT_TYPE = "e4ElementType";
    
    private Side dockSide;
	private Element maximizeButton;
	private Element minimizeButton;
	private int state;
	
	private static final int MINIMIZED = -1;
	private static final int NORMAL = 0;
	private static final int MAXIMIZED = 1;
	
	private boolean maximizeEnabled = true;
	private boolean minimizeEnabled = true;
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 * The constructor should first call super() to initialize the component and
	 * then handle any initialization relevant to Vaadin.
	 */
	public VStackWidget() {
		super();
		
		Element tabs = (Element) getElement().getChild(0);
        
        Element scroller = DOM.getChild(tabs, 1);
        
        //scroller.setAttribute("style", "margin-right:35px;");
        scroller.setAttribute("style", "width:90px;");
        
        for (int i = 0; i < DOM.getChildCount(scroller); i++)
        {
        	Element child = DOM.getChild(scroller, i);
        	child.setAttribute("style", "float: left;");
        }
        
        Element buttonPanel = DOM.createDiv();
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
	}
	
	public int getState()
	{
		return state;
	}
	
	public void setState(int state)
    {
    	if (state != MINIMIZED && state != NORMAL && state != MAXIMIZED)
    		return;
    	
    	this.state = state;
    	
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
        if (DOM.eventGetTarget(event) == maximizeButton) 
        {
        	synchronized (this) 
        	{
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
        	synchronized (this) 
        	{
        		if (this.state == NORMAL)
        		{
        			setState(MINIMIZED);
        		}
        		else if (this.state == MINIMIZED)
        		{
        			setState(NORMAL);
        		}
        		
            	client.updateVariable(this.id, "vaadock_tabsheet_state", new Integer(this.state), true);
			}
        }
        else {
            super.onBrowserEvent(event);
        }
    }
    
    @Override
    protected void updateDropHandler(UIDL childUidl) {
        if (dropHandler == null) {
            dropHandler = new VAbstractDropHandler() {

                /*
                 * (non-Javadoc)
                 * 
                 * @see com.vaadin.terminal.gwt.client.ui.dd.VDropHandler#
                 * getApplicationConnection()
                 */
                public ApplicationConnection getApplicationConnection() {
                    return client;
                }

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
                 * #getPaintable()
                 */
                @Override
                public Paintable getPaintable() {
                    return VStackWidget.this;
                }

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
                 * #dragAccepted
                 * (com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
                 */
                @Override
                protected void dragAccepted(VDragEvent drag) {
                    dragOver(drag);
                    
                    //prepare inner drophandler owners
//                    if (!innerDropProcessed)
//                    {
//                    	processInnerHandlerOwners(VDDTabSheet.this, innerDropOwners);
//                    	innerDropProcessed = true;
//                    }
                }

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
                 * #drop(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
                 */
                @Override
                public boolean drop(VDragEvent drag) {

                    deEmphasis();

                    // Update the details
                    updateDropDetails(drag);
                    
                    if (dockZone1 != null && dockZoneContainer != null)
                    {
                    	removeDockZone();
                    }
                    
                    return postDropHook(drag) && super.drop(drag); 
                };

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
                 * #dragOver(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
                 */
                @Override
                public void dragOver(VDragEvent drag) {
                	
                	//VConsole.log("drag over");
                	
                    if (drag.getElementOver() == newTab) {
                        return;
                    }

                    deEmphasis();

                    updateDropDetails(drag);

                    postOverHook(drag);

                    // Check if we are dropping on our self
                    if (VStackWidget.this.equals(drag.getTransferable().getData(
                            Constants.TRANSFERABLE_DETAIL_COMPONENT))) {
                        return;
                    }

                    // Validate the drop
                    validate(new VAcceptCallback() {
                        public void accepted(VDragEvent event) {
                            emphasis(event.getElementOver(), event);
                        }
                    }, drag);
                };

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
                 * #dragLeave(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
                 */
                @Override
                public void dragLeave(VDragEvent drag) {
                    deEmphasis();
                    updateDropDetails(drag);
                    postLeaveHook(drag);
                    
                    if (dockZone1 != null && dockZoneContainer != null)
                    {
                    	removeDockZone();
                    }
                };
            };
        }

        // Update the rules
        dropHandler.updateAcceptRules(childUidl);
    }
    
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
        }
	}

	private boolean containsChild(ComplexPanel container, Widget c)
	{
		for (int i = 0; i < container.getWidgetCount(); i++)
		{
			Widget child = container.getWidget(i);
			if (child == c)
				return true;
			else if (child instanceof ComplexPanel)
			{
				if (containsChild((ComplexPanel) child, c))
					return true;
			}
			else if (child instanceof ChildComponentContainer)
			{
				Widget trueChild = ((ChildComponentContainer)child).getWidget();
				if (trueChild instanceof ComplexPanel)
				{
					if (containsChild((ComplexPanel) trueChild, c))
						return true;
				}
			}
		}
		return false;
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
    protected void updateDropDetails(VDragEvent event) 
    {
        Element element = event.getElementOver();
        Widget targetWidget = Util.findWidget(element, null);
        
        if (targetWidget == null)
        {
        	return;
        }
        
    	if (targetWidget != this)
    	{
    		Widget parent = targetWidget.getParent();
    		while (parent != null && parent != this)
    		{
    			parent = parent.getParent();
    		}
    		
    		if (parent == null)
    		{
    			return;
    		}
    		targetWidget = parent;
    	}
    	
    	MouseEventDetails details1 = new MouseEventDetails(
                event.getCurrentGwtEvent(), getElement());
    	
    	int mouseX = details1.getClientX();
		int mouseY = details1.getClientY();
		
        
        int barLeft = tabBar.getAbsoluteLeft();
        int barTop = tabBar.getAbsoluteTop();
        int barWidth = tabBar.getOffsetWidth();
        int barHeight = tabBar.getOffsetHeight();
        
        boolean overBar = mouseX > barLeft && mouseX < barLeft + barWidth && mouseY > barTop && mouseY < barTop + barHeight;
    	
    	if (overBar)
    	{
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
			Widget sourceWidget = (Widget) event.getTransferable().getDragSource();
			if (!(sourceWidget instanceof VDDTabSheet))
				return;
			VDDTabSheet sourceTabSheet = (VDDTabSheet) sourceWidget;
			VDDTabSheet targetTabSheet = this;
			
			VBoundsinfoVerticalLayout outerArea = findOuterArea(targetTabSheet);
				
			Widget boundingWidget = null;
			
			if (outerArea != null)
			{
				if ("area".equals(outerArea.getVariableValue(E4_ELEMENT_TYPE)))
    				boundingWidget = outerArea;	
			}
			else
				boundingWidget = targetTabSheet;
				
			if (boundingWidget == null)
				return;
        	
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
			
			Side side = GeomUtils.findDockSide(x0, y0, dx, dy, docX, docY, mousePos);
			//VConsole.log("dock side: " + side);
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
				 
				if (dockZone1 == null)
				{
					dockZone1 = DOM.createDiv();
					dockZone2 = DOM.createDiv();
					dockZone3 = DOM.createDiv();
					dockZone4 = DOM.createDiv();
				}
				
				//VConsole.log("x=" + _x + "; y=" + _y + "; w=" + _w + "; h=" + _h);
				
				if (side != dockSide)
	    		{
	    			int l = 3;
	    			String style1 = "position: absolute; left: " + _x + "px; top: " + _y + 
	    					"px; width: " + _w + "px; height: " + l + "px; background-image: url(/VAADIN/themes/dragdrop/vaadock/img/dockzone.png); z-index: 20000;";
					dockZone1.setAttribute("style", style1);
					
					String style2 = "position: absolute; left: " + _x + "px; top: " + (_y + _h - l) + 
	    					"px; width: " + _w + "px; height: " + l + "px; background-image: url(/VAADIN/themes/dragdrop/vaadock/img/dockzone.png); z-index: 20000;";
					dockZone2.setAttribute("style", style2);
					
					String style3 = "position: absolute; left: " + _x + "px; top: " + _y + 
	    					"px; width: " + l + "px; height: " + _h + "px; background-image: url(/VAADIN/themes/dragdrop/vaadock/img/dockzone.png); z-index: 20000;";
					dockZone3.setAttribute("style", style3);
					
					String style4 = "position: absolute; left: " + (_x + _w - l) + "px; top: " + _y + 
	    					"px; width: " + l + "px; height: " + _h + "px; background-image: url(/VAADIN/themes/dragdrop/vaadock/img/dockzone.png); z-index: 20000;";
					dockZone4.setAttribute("style", style4);
					
	    	        //setStyleName(dockZone, "v-etot-sukin-syn");
	    	        dockZoneContainer = boundingWidget.getElement();
	    	        DOM.appendChild(dockZoneContainer, dockZone1);
	    	        DOM.appendChild(dockZoneContainer, dockZone2);
	    	        DOM.appendChild(dockZoneContainer, dockZone3);
	    	        DOM.appendChild(dockZoneContainer, dockZone4);
	    	        
	    	        dockSide = side;
	    		}
			}
    	}
		
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
                                tabLeftRightDropRatio);
                event.getDropDetails().put(
                        Constants.DROP_DETAIL_HORIZONTAL_DROP_LOCATION,
                        location);
            }
        }
		
		// Add mouse event details
        MouseEventDetails details = new MouseEventDetails(
                event.getCurrentGwtEvent(), getElement());
        event.getDropDetails().put(Constants.DROP_DETAIL_MOUSE_EVENT,
                details.serialize());
    }
	
	private VBoundsinfoVerticalLayout findOuterArea(Widget w)
    {
    	if (w instanceof VBoundsinfoVerticalLayout)
    	{
    		VBoundsinfoVerticalLayout bl = (VBoundsinfoVerticalLayout) w;
			final String type = bl.getVariableValue(E4_ELEMENT_TYPE);
			if (type != null && ("area".equals(type) || "perspective".equals(type) || "window".equals(type)))
				return bl;
    	}
    	
    	if (w.getParent() != null)
			return findOuterArea(w.getParent());
		else
			return null;
    }

	/**
	 * Called whenever an update is received from the server
	 */
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client)
	{
		super.updateFromUIDL(uidl, client);

		// This call should be made first.
		// It handles sizes, captions, tooltips, etc. automatically.
		if (client.updateComponent(this, uidl, true))
		{
			// If client.updateComponent returns true there has been no changes
			// and we
			// do not need to update anything.
			return;
		}
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++
		id = uidl.getId();
        if (uidl.getIntAttribute("svoi") == 5)
        {
        	this.setState(uidl.getIntAttribute("vaadock_tabsheet_state"));
            this.maximizeEnabled = uidl.getBooleanAttribute("maximize_enabled");
            this.minimizeEnabled = uidl.getBooleanAttribute("minimize_enabled");
            
            if (!this.maximizeEnabled)
            	maximizeButton.setAttribute("style", "display: none;");
            if (!this.minimizeEnabled)
            	minimizeButton.setAttribute("style", "display: none;");
        }
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++

		// Save reference to server connection object to be able to send
		// user interaction later
		this.client = client;

		// Save the client side identifier (paintable id) for the widget
		paintableId = uidl.getId();
		
		updateManager = new BoundsUpdateManager(this, paintableId, client);
	}
	
	@Override
	public RenderSpace getAllocatedSpace(Widget child)
	{
		if (updateManager != null && this.getState() != MINIMIZED)
			updateManager.update();
		return super.getAllocatedSpace(child);
	}
	
//	@Override
//	public boolean requestLayout(Set<Paintable> child)
//	{
//		//updateRemoteBounds();
//		if (updateManager != null)
//			updateManager.update();
//		return super.requestLayout(child);
//	}

//	void updateRemoteBounds()
//	{
//		if (client != null)
//		{
//			client.updateVariable(paintableId, "absolute_left", this.getAbsoluteLeft(), true);
//			client.updateVariable(paintableId, "absolute_top", this.getAbsoluteTop(), true);
//			client.updateVariable(paintableId, "offset_width", this.getOffsetWidth(), true);
//			client.updateVariable(paintableId, "offset_height", this.getOffsetHeight(), true);
//		}
//	}
}
