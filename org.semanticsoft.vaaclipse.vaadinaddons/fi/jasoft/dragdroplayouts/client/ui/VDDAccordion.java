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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;
import com.vaadin.terminal.gwt.client.VCaption;
import com.vaadin.terminal.gwt.client.ui.VAccordion;
import com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VAcceptCallback;
import com.vaadin.terminal.gwt.client.ui.dd.VDragEvent;
import com.vaadin.terminal.gwt.client.ui.dd.VDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VHasDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VerticalDropLocation;

import fi.jasoft.dragdroplayouts.client.ui.VLayoutDragDropMouseHandler.DragStartListener;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VDDTabContainer;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;

public class VDDAccordion extends VAccordion implements VHasDragMode,
        VHasDropHandler, DragStartListener, VDDTabContainer {

    public static final String CLASSNAME_OVER = "dd-over";
    public static final String CLASSNAME_SPACER = "spacer";

    public static final float DEFAULT_VERTICAL_RATIO = 0.2f;

    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    private VAbstractDropHandler dropHandler;

    private float tabTopBottomDropRatio = DEFAULT_VERTICAL_RATIO;

    private StackItem currentlyEmphasised;

    private ApplicationConnection client;

    private final Map<Element, StackItem> elementTabMap = new HashMap<Element, StackItem>();

    private final Widget spacer;

    // The drag mouse handler which handles the creation of the transferable
    private final VLayoutDragDropMouseHandler ddMouseHandler = new VLayoutDragDropMouseHandler(
            this, dragMode);

    protected boolean iframeCoversEnabled = false;

    private final VDragFilter dragFilter = new VTabDragFilter(this);

    private final IframeCoverUtility iframeCoverUtility = new IframeCoverUtility();

    public VDDAccordion() {
        spacer = GWT.create(HTML.class);
        spacer.setWidth("100%");
        spacer.setStyleName(CLASSNAME_SPACER);
        ddMouseHandler.addDragStartListener(this);
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
        return dragMode != LayoutDragMode.NONE
                && dragFilter.isDraggable(widget);
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
                    return VDDAccordion.this;
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
                    Widget w = (Widget) drag.getTransferable().getData(
                            Constants.TRANSFERABLE_DETAIL_COMPONENT);
                    if (w instanceof VCaption) {
                        // Convert dragged caption into the real component
                        StackItem item = (StackItem) w.getParent();
                        drag.getTransferable().setData(
                                Constants.TRANSFERABLE_DETAIL_COMPONENT,
                                item.getComponent());
                    }

                    // Update the details
                    updateDropDetails(drag);

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

                    deEmphasis();

                    updateDropDetails(drag);

                    postOverHook(drag);

                    Widget w = (Widget) drag.getTransferable().getData(
                            Constants.TRANSFERABLE_DETAIL_COMPONENT);
                    if (VDDAccordion.this.equals(w)) {
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
                };
            };
        }

        // Update the rules
        dropHandler.updateAcceptRules(childUidl);
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
        StackItem tab = getTabByElement(event.getElementOver());
        if (tab != null) {
            // Add index
            int index = getWidgetIndex(tab);
            event.getDropDetails().put(Constants.DROP_DETAIL_TO, index);

            // Add drop location
            VerticalDropLocation location = getDropLocation(tab, event);
            event.getDropDetails().put(
                    Constants.DROP_DETAIL_VERTICAL_DROP_LOCATION, location);

            // Add mouse event details
            MouseEventDetails details = new MouseEventDetails(
                    event.getCurrentGwtEvent(), getElement());
            event.getDropDetails().put(Constants.DROP_DETAIL_MOUSE_EVENT,
                    details.serialize());
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
                ddMouseHandler.attach();

            } else if (dragMode == LayoutDragMode.NONE) {

                // Remove iframe covers
                iframeCoversEnabled = false;

                // Remove mouse down handler
                ddMouseHandler.detach();
            }
        }
    }

    private StackItem getTabByElement(Element element) {
        StackItem item = elementTabMap.get(element);
        if (item == null) {
            for (int i = 0; i < getTabCount(); i++) {
                StackItem tab = (StackItem) getWidget(i);
                if (tab.getElement().isOrHasChild(element)) {
                    item = tab;
                    elementTabMap.put(element, tab);
                }
            }
        }

        return item;
    }

    private VerticalDropLocation getDropLocation(StackItem tab, VDragEvent event) {
        VerticalDropLocation location;
        if (tab.isOpen()) {
            location = VDragDropUtil.getVerticalDropLocation(tab.getElement(),
                    Util.getTouchOrMouseClientY(event.getCurrentGwtEvent()),
                    tabTopBottomDropRatio);
        } else {
            location = VDragDropUtil.getVerticalDropLocation(tab.getWidget(0)
                    .getElement(), Util.getTouchOrMouseClientY(event
                    .getCurrentGwtEvent()), tabTopBottomDropRatio);
        }
        return location;
    }

    /**
     * Emphasisizes a container element
     * 
     * @param element
     */
    protected void emphasis(Element element, VDragEvent event) {

        // Find the tab
        StackItem tab = getTabByElement(element);

        if (tab != null && currentlyEmphasised != tab) {

            VerticalDropLocation location = getDropLocation(tab, event);

            if (location == VerticalDropLocation.MIDDLE) {
                if (tab.isOpen()) {
                    tab.addStyleName(CLASSNAME_OVER);
                } else {
                    tab.getWidget(0).addStyleName(CLASSNAME_OVER);
                }
            } else if (!spacer.isAttached()) {
                if (location == VerticalDropLocation.TOP) {
                    insert(spacer, getElement(), getWidgetIndex(tab), true);
                    tab.setHeight((tab.getOffsetHeight() - spacer
                            .getOffsetHeight()) + "px");
                } else if (location == VerticalDropLocation.BOTTOM) {
                    insert(spacer, getElement(), getWidgetIndex(tab) + 1, true);
                    int newHeight = tab.getOffsetHeight()
                            - spacer.getOffsetHeight();
                    if (getWidgetIndex(spacer) == getWidgetCount() - 1) {
                        newHeight -= spacer.getOffsetHeight();
                    }
                    if (newHeight >= 0) {
                        tab.setHeight(newHeight + "px");
                    }
                }
            }
            currentlyEmphasised = tab;
        }
    }

    /**
     * Removes any previous emphasis made by drag&drop
     */
    protected void deEmphasis() {
        if (currentlyEmphasised != null) {
            currentlyEmphasised.removeStyleName(CLASSNAME_OVER);
            currentlyEmphasised.getWidget(0).removeStyleName(CLASSNAME_OVER);
            if (spacer.isAttached()) {

                int newHeight = currentlyEmphasised.getHeight()
                        + spacer.getOffsetHeight();

                if (getWidgetIndex(spacer) == getWidgetCount() - 1) {
                    newHeight += spacer.getOffsetHeight();
                }

                currentlyEmphasised.setHeight(newHeight + "px");

                remove(spacer);
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
        if (uidl.hasAttribute(Constants.ATTRIBUTE_VERTICAL_DROP_RATIO)) {
            tabTopBottomDropRatio = uidl
                    .getFloatAttribute(Constants.ATTRIBUTE_VERTICAL_DROP_RATIO);
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

        for (final Iterator<Object> it = uidl.getChildIterator(); it.hasNext();) {
            final UIDL childUIDL = (UIDL) it.next();
            if (childUIDL.getTag().equals("-ac")) {
                updateDropHandler(childUIDL);
                break;
            }
        }

        UIDL modifiedUidl = VDragDropUtil.removeDragDropCriteraFromUIDL(uidl);
        super.updateFromUIDL(modifiedUidl, client);

        // Handles changes in dropHandler
        handleDragModeUpdate(modifiedUidl);

        // Handle drop ratio settings
        handleCellDropRatioUpdate(modifiedUidl);

        // Cover iframes if necessery
        iframeCoverUtility.setIframeCoversEnabled(iframeCoversEnabled,
                getElement());

        // Drag filters
        dragFilter.update(modifiedUidl, client);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fi.jasoft.dragdroplayouts.client.ui.interfaces.VDDTabContainer#
     * getTabContentPosition(com.google.gwt.user.client.ui.Widget)
     */
    public int getTabContentPosition(Widget w) {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * fi.jasoft.dragdroplayouts.client.ui.interfaces.VDDTabContainer#getTabPosition
     * (com.google.gwt.user.client.ui.Widget)
     */
    public int getTabPosition(Widget tab) {
        // TODO Auto-generated method stub
        return 0;
    }
}
