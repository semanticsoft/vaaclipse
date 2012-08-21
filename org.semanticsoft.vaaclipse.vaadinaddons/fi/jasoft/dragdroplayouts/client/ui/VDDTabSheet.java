/*
 * Copyright 2011 John Ahlroos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fi.jasoft.dragdroplayouts.client.ui;

import java.util.Iterator;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;
import com.vaadin.terminal.gwt.client.VCaption;
import com.vaadin.terminal.gwt.client.ui.VTabsheet;
import com.vaadin.terminal.gwt.client.ui.VTabsheetPanel;
import com.vaadin.terminal.gwt.client.ui.VVerticalLayout;
import com.vaadin.terminal.gwt.client.ui.dd.HorizontalDropLocation;
import com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VAcceptCallback;
import com.vaadin.terminal.gwt.client.ui.dd.VDragEvent;
import com.vaadin.terminal.gwt.client.ui.dd.VDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VHasDropHandler;
import com.vaadin.terminal.gwt.client.ui.layout.ChildComponentContainer;

import fi.jasoft.dragdroplayouts.client.ui.GeomUtils.Side;
import fi.jasoft.dragdroplayouts.client.ui.VLayoutDragDropMouseHandler.DragStartListener;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VDDTabContainer;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;

public class VDDTabSheet extends VTabsheet implements VHasDragMode,
        VHasDropHandler, DragStartListener, VDDTabContainer {

    public static final String CLASSNAME_NEW_TAB = "new-tab";
    public static final String CLASSNAME_NEW_TAB_LEFT = "new-tab-left";
    public static final String CLASSNAME_NEW_TAB_RIGHT = "new-tab-right";
    public static final String CLASSNAME_NEW_TAB_CENTER = "new-tab-center";

    public static final float DEFAULT_HORIZONTAL_DROP_RATIO = 0.2f;

    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    private VAbstractDropHandler dropHandler;

    private float tabLeftRightDropRatio = DEFAULT_HORIZONTAL_DROP_RATIO;

    private ApplicationConnection client;

    private final ComplexPanel tabBar;
    private final VTabsheetPanel tabPanel;

    private final Element spacer;

    private Element currentlyEmphasised;

    private final Element newTab = DOM.createDiv();

    protected boolean iframeCoversEnabled = false;

    private final VDragFilter dragFilter = new VTabDragFilter(this);

    private final IframeCoverUtility iframeCoverUtility = new IframeCoverUtility();
    
    private String id;
    //private Element dockZone;
    
    private Element dockZone1;
    private Element dockZone2;
    private Element dockZone3;
    private Element dockZone4;
    
    private Element dockZoneContainer;
//    private Widget dockZoneOwner;
    private Side dockSide;
	private Element maximizeButton;
	private Element minimizeButton;
	private int state;
	
	private static final int MINIMIZED = -1;
	private static final int NORMAL = 0;
	private static final int MAXIMIZED = 1;
	
	private boolean maximizeEnabled = true;
	private boolean minimizeEnabled = true;

    public VDDTabSheet() {
        super();

        newTab.setClassName(CLASSNAME_NEW_TAB);

        // Get the tabBar
        tabBar = (ComplexPanel) getChildren().get(0);

        // Get the content
        tabPanel = (VTabsheetPanel) getChildren().get(1);

        // Get the spacer
        Element tBody = tabBar.getElement();
        spacer = tBody.getChild(tBody.getChildCount() - 1).getChild(0)
                .getChild(0).cast();

        ddMouseHandler.addDragStartListener(this);
        
//        test = DOM.createDiv();
//        setStyleName(test, "v-etot-sukin-syn");
//        DOM.appendChild(tabPanel.getElement(), test);
        
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
    
    public void setState(int state)
    {
    	if (state != MINIMIZED && state != NORMAL && state != MAXIMIZED)
    		return;
    	
    	this.state = state;
    	
    	if (this.state == NORMAL)
    	{
    		this.state = NORMAL;
			setStyleName(maximizeButton, "v-vaadock-tabsheet-maximize-button");
    	}
    	else if (this.state == MAXIMIZED)
    	{
    		this.state = MAXIMIZED;
			setStyleName(maximizeButton, "v-vaadock-tabsheet-restore-button");
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
        			this.state = MINIMIZED;
        		}
        		
            	client.updateVariable(this.id, "vaadock_tabsheet_state", new Integer(this.state), true);
			}
        }
        else {
            super.onBrowserEvent(event);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.user.client.ui.Widget#onUnload()
     */
    @Override
    protected void onUnload() {
        super.onUnload();
        ddMouseHandler.detach();
        iframeCoverUtility.setIframeCoversEnabled(false, getElement());
    }

    // The drag mouse handler which handles the creation of the transferable
    private final VLayoutDragDropMouseHandler ddMouseHandler = new VLayoutDragDropMouseHandler(
            this, dragMode);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.vaadin.terminal.gwt.client.ui.dd.VHasDropHandler#getDropHandler()
     */
    public VDropHandler getDropHandler() {
        return dropHandler;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fi.jasoft.dragdroplayouts.client.ui.VHasDragMode#getDragMode()
     */
    public LayoutDragMode getDragMode() {
        return dragMode;
    }

    /**
     * A hook for extended components to post process the the drop before it is
     * sent to the server. Useful if you don't want to override the whole drop
     * handler.
     */
    protected boolean postDropHook(VDragEvent drag) {
        // Extended classes can add content here...
        return true;
    }

    /**
     * A hook for extended components to post process the the enter event.
     * Useful if you don't want to override the whole drophandler.
     */
    protected void postEnterHook(VDragEvent drag) {
        // Extended classes can add content here...
    }

    /**
     * A hook for extended components to post process the the leave event.
     * Useful if you don't want to override the whole drophandler.
     */
    protected void postLeaveHook(VDragEvent drag) {
        // Extended classes can add content here...
    }

    /**
     * A hook for extended components to post process the the over event. Useful
     * if you don't want to override the whole drophandler.
     */
    protected void postOverHook(VDragEvent drag) {
        // Extended classes can add content here...
    }

    /**
     * Can be used to listen to drag start events, must return true for the drag
     * to commence. Return false to interrupt the drag:
     */
    public boolean dragStart(Widget widget, LayoutDragMode mode) {
        Widget w = tabPanel.getWidget(getTabPosition(widget));
        return dragMode != LayoutDragMode.NONE && dragFilter.isDraggable(w);
    }

    /**
     * Creates a drop handler if one does not already exist and updates it from
     * the details received from the server.
     * 
     * @param childUidl
     *            The UIDL
     */
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
                    return VDDTabSheet.this;
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

                    if (drag.getElementOver() == newTab) {
                        return;
                    }

                    deEmphasis();

                    updateDropDetails(drag);

                    postOverHook(drag);

                    // Check if we are dropping on our self
                    if (VDDTabSheet.this.equals(drag.getTransferable().getData(
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
    
    protected void removeDockZone()
	{
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
    protected void updateDropDetails(VDragEvent event) {
        Element element = event.getElementOver();
        
        Widget targetWidget = Util.findWidget(element, null);
        if (targetWidget != null)
        {
    		if (targetWidget == tabPanel)
    		{
    			Widget sourceWidget = (Widget) event.getTransferable().getDragSource();
    			VVerticalLayout sourceDockArea = findDockArea(sourceWidget);
    			
            	VVerticalLayout targetDockArea = findDockArea(targetWidget);
            	
            	Widget boundingWidget;
            	if (sourceDockArea != targetDockArea)
            	{
            		if (containsChild(sourceDockArea, targetDockArea))
    				{
    					//dragFromOuterDocAreaToInnerDocArea = true;
            			boundingWidget = targetDockArea;
    				}
            		else
            		{
            			event.getDropDetails().put("targetWidgetClassName", targetWidget.getClass().getName());
                		event.getDropDetails().put("targetWidgetAbsoluteLeft", targetWidget.getAbsoluteLeft());
                		event.getDropDetails().put("targetWidgetAbsoluteTop", targetWidget.getAbsoluteTop());
                		event.getDropDetails().put("targetWidgetOffsetWidth", targetWidget.getOffsetWidth());
                		event.getDropDetails().put("targetWidgetOffsetHeight", targetWidget.getOffsetHeight());
            			return;
            		}
            	}
            	else
            		boundingWidget = targetWidget.getParent();
            	
            	event.getDropDetails().put("targetWidgetClassName", boundingWidget.getClass().getName());
        		event.getDropDetails().put("targetWidgetAbsoluteLeft", boundingWidget.getAbsoluteLeft());
        		event.getDropDetails().put("targetWidgetAbsoluteTop", boundingWidget.getAbsoluteTop());
        		event.getDropDetails().put("targetWidgetOffsetWidth", boundingWidget.getOffsetWidth());
        		event.getDropDetails().put("targetWidgetOffsetHeight", boundingWidget.getOffsetHeight());
        		
    			MouseEventDetails details1 = new MouseEventDetails(
                        event.getCurrentGwtEvent(), getElement());
        		int mouseX = details1.getClientX();
    			int mouseY = details1.getClientY();
    			
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
    					
    				if (side != dockSide)
    	    		{
    	    			int l = 3;
    	    			String style1 = "position: absolute; left: " + _x + "px; top: " + _y + 
    	    					"px; width: " + _w + "px; height: " + l + "px; background-image: url(/vaadin_development2/VAADIN/themes/dragdrop/vaadock/img/dockzone.png); z-index: 20000;";
    					dockZone1.setAttribute("style", style1);
    					
    					String style2 = "position: absolute; left: " + _x + "px; top: " + (_y + _h - l) + 
    	    					"px; width: " + _w + "px; height: " + l + "px; background-image: url(/vaadin_development2/VAADIN/themes/dragdrop/vaadock/img/dockzone.png); z-index: 20000;";
    					dockZone2.setAttribute("style", style2);
    					
    					String style3 = "position: absolute; left: " + _x + "px; top: " + _y + 
    	    					"px; width: " + l + "px; height: " + _h + "px; background-image: url(/vaadin_development2/VAADIN/themes/dragdrop/vaadock/img/dockzone.png); z-index: 20000;";
    					dockZone3.setAttribute("style", style3);
    					
    					String style4 = "position: absolute; left: " + (_x + _w - l) + "px; top: " + _y + 
    	    					"px; width: " + l + "px; height: " + _h + "px; background-image: url(/vaadin_development2/VAADIN/themes/dragdrop/vaadock/img/dockzone.png); z-index: 20000;";
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
    		else
    		{
    			event.getDropDetails().put("targetWidgetClassName", targetWidget.getClass().getName());
                		event.getDropDetails().put("targetWidgetAbsoluteLeft", targetWidget.getAbsoluteLeft());
                		event.getDropDetails().put("targetWidgetAbsoluteTop", targetWidget.getAbsoluteTop());
                		event.getDropDetails().put("targetWidgetOffsetWidth", targetWidget.getOffsetWidth());
                		event.getDropDetails().put("targetWidgetOffsetHeight", targetWidget.getOffsetHeight());
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
    }
    
    private VVerticalLayout findDockArea(Widget w)
    {
    	if (w instanceof VVerticalLayout)
    		return (VVerticalLayout) w;
    	else
    	{
    		if (w.getParent() != null)
    			return findDockArea(w.getParent());
    		else
    			return null;
    	}
    }

    /**
     * Handles drag mode changes recieved from the server
     * 
     * @param uidl
     *            The UIDL
     */
    private void handleDragModeUpdate(UIDL uidl) {
        if (uidl.hasAttribute(VHasDragMode.DRAGMODE_ATTRIBUTE)) {
            LayoutDragMode[] modes = LayoutDragMode.values();
            dragMode = modes[uidl
                    .getIntAttribute(VHasDragMode.DRAGMODE_ATTRIBUTE)];
            ddMouseHandler.updateDragMode(dragMode);
            if (dragMode != LayoutDragMode.NONE) {
                // Cover iframes if necessery
                iframeCoversEnabled = uidl
                        .getBooleanAttribute(IframeCoverUtility.SHIM_ATTRIBUTE);

                // Listen to mouse down events
                ddMouseHandler.attachTo(tabBar);

            } else if (dragMode == LayoutDragMode.NONE) {
                // Remove iframe covers
                iframeCoversEnabled = false;

                // Remove mouse down handler
                ddMouseHandler.detach();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.vaadin.terminal.gwt.client.ui.VTabsheet#updateFromUIDL(com.vaadin
     * .terminal.gwt.client.UIDL,
     * com.vaadin.terminal.gwt.client.ApplicationConnection)
     */
    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        this.client = client;
        
        id = uidl.getId();

        for (final Iterator<Object> it = uidl.getChildIterator(); it.hasNext();) {
            final UIDL childUIDL = (UIDL) it.next();
            if (childUIDL.getTag().equals("-ac")) {
                updateDropHandler(childUIDL);
            }
        }

        UIDL modifiedUIDL = VDragDropUtil.removeDragDropCriteraFromUIDL(uidl);
        super.updateFromUIDL(modifiedUIDL, client);

        // Handles changes in dropHandler
        handleDragModeUpdate(modifiedUIDL);

        // Handle drop ratio settings
        handleCellDropRatioUpdate(modifiedUIDL);

        // Handle iframe covering
        iframeCoverUtility.setIframeCoversEnabled(iframeCoversEnabled,
                getElement());

        // Update dragfilter
        dragFilter.update(uidl, client);
        
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
    }

    /**
     * Emphasisizes a container element
     * 
     * @param element
     */
    protected void emphasis(Element element, VDragEvent event) {

        boolean internalDrag = event.getTransferable().getDragSource() == this;

        if (tabBar.getElement().isOrHasChild(element)) {
            Widget w = Util.findWidget(element, null);

            if (w == tabBar && !internalDrag) {
                // Over spacer
                Element spacerContent = spacer.getChild(0).cast();
                spacerContent.appendChild(newTab);
                currentlyEmphasised = element;

            } else if (w instanceof VCaption) {
                // Over a tab
                VCaption tab = (VCaption) w;
                HorizontalDropLocation location = VDragDropUtil
                        .getHorizontalDropLocation(element, Util
                                .getTouchOrMouseClientX(event
                                        .getCurrentGwtEvent()),
                                tabLeftRightDropRatio);

                if (location == HorizontalDropLocation.LEFT) {

                    int index = getTabPosition(w);

                    if (index == 0) {
                        currentlyEmphasised = tab.getElement();
                        currentlyEmphasised
                                .addClassName(CLASSNAME_NEW_TAB_LEFT);
                    } else {
                        Widget prevTab = tabBar.getWidget(index - 1);
                        currentlyEmphasised = prevTab.getElement();
                        currentlyEmphasised
                                .addClassName(CLASSNAME_NEW_TAB_RIGHT);
                    }

                } else if (location == HorizontalDropLocation.RIGHT) {
                    tab.getElement().addClassName(CLASSNAME_NEW_TAB_RIGHT);
                    currentlyEmphasised = tab.getElement();
                } else {
                    tab.getElement().addClassName(CLASSNAME_NEW_TAB_CENTER);
                    currentlyEmphasised = tab.getElement();
                }

            }
        }
    }

    /**
     * Removes any previous emphasis made by drag&drop
     */
    protected void deEmphasis() {
        if (currentlyEmphasised != null
                && tabBar.getElement().isOrHasChild(currentlyEmphasised)) {
            Widget w = Util.findWidget(currentlyEmphasised, null);

            currentlyEmphasised.removeClassName(CLASSNAME_NEW_TAB_LEFT);
            currentlyEmphasised.removeClassName(CLASSNAME_NEW_TAB_RIGHT);
            currentlyEmphasised.removeClassName(CLASSNAME_NEW_TAB_CENTER);

            if (w == tabBar) {
                // Over spacer
                Element spacerContent = spacer.getChild(0).cast();
                spacerContent.removeChild(newTab);
            }

            currentlyEmphasised = null;
        }
    }

    /**
     * Handles updates the the hoover zones of the tab which specifies at which
     * position a component is dropped over a tab
     * 
     * @param uidl
     *            The UIDL
     */
    private void handleCellDropRatioUpdate(UIDL uidl) {
        if (uidl.hasAttribute("hDropRatio")) {
            tabLeftRightDropRatio = uidl.getFloatAttribute("hDropRatio");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * fi.jasoft.dragdroplayouts.client.ui.interfaces.VDDTabContainer#getTabPosition
     * (com.google.gwt.user.client.ui.Widget)
     */
    public int getTabPosition(Widget tab) {
        int idx = -1;
        for (int i = 0; i < tabBar.getWidgetCount(); i++) {
            Widget w = tabBar.getWidget(i);
            if (w.getElement().isOrHasChild(tab.getElement())) {
                idx = i;
                break;
            }
        }
        return idx;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fi.jasoft.dragdroplayouts.client.ui.interfaces.VDDTabContainer#
     * getTabContentPosition(com.google.gwt.user.client.ui.Widget)
     */
    public int getTabContentPosition(Widget content) {
        return tabPanel.getWidgetIndex(content);
    }
}
