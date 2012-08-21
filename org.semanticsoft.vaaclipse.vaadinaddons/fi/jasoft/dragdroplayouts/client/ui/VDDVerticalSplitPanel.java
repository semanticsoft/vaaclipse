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

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;
import com.vaadin.terminal.gwt.client.ui.VSplitPanelVertical;
import com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VAcceptCallback;
import com.vaadin.terminal.gwt.client.ui.dd.VDragEvent;
import com.vaadin.terminal.gwt.client.ui.dd.VDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VHasDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VerticalDropLocation;

import fi.jasoft.dragdroplayouts.client.ui.VLayoutDragDropMouseHandler.DragStartListener;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;

public class VDDVerticalSplitPanel extends VSplitPanelVertical implements
        VHasDragMode, VHasDropHandler, DragStartListener {

    public static final String OVER = "v-ddsplitpanel-over";

    public static final String OVER_SPLITTER = OVER + "-splitter";

    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    private VAbstractDropHandler dropHandler;

    private ApplicationConnection client;

    private Element firstContainer;

    private Element secondContainer;

    private Element splitter;

    private Element currentEmphasis;

    protected boolean iframeCoversEnabled = false;

    private final VDragFilter dragFilter = new VDragFilter();

    private final IframeCoverUtility iframeCoverUtility = new IframeCoverUtility();

    // The drag mouse handler which handles the creation of the transferable
    private final VLayoutDragDropMouseHandler ddMouseHandler = new VLayoutDragDropMouseHandler(
            this, dragMode);

    public VDDVerticalSplitPanel() {
        super();
        ddMouseHandler.addDragStartListener(this);
    }

    @Override
    protected void onUnload() {
        super.onUnload();
        ddMouseHandler.detach();
        iframeCoverUtility.setIframeCoversEnabled(false, getElement());
    }

    @Override
    protected void constructDom() {
        super.constructDom();

        // Save references
        Element wrapper = getElement().getChild(0).cast();
        secondContainer = wrapper.getChild(0).cast();
        firstContainer = wrapper.getChild(1).cast();
        splitter = wrapper.getChild(2).cast();
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.vaadin.terminal.gwt.client.ui.VSplitPanel#updateFromUIDL(com.vaadin
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

        UIDL modifiedUIDL = VDragDropUtil.removeDragDropCriteraFromUIDL(uidl);
        super.updateFromUIDL(modifiedUIDL, client);

        // Handles changes in dropHandler
        handleDragModeUpdate(modifiedUIDL);

        // Iframe cover check
        iframeCoverUtility.setIframeCoversEnabled(iframeCoversEnabled,
                getElement());

        dragFilter.update(modifiedUIDL, client);
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
                    return VDDVerticalSplitPanel.this;
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

                    // Un-emphasis any selections
                    deEmphasis();

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
                    if (VDDVerticalSplitPanel.this.equals(w)) {
                        return;
                    }

                    // Validate the drop
                    validate(new VAcceptCallback() {
                        public void accepted(VDragEvent event) {
                            emphasis(event.getElementOver());
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
                    postLeaveHook(drag);
                };
            };
        }

        // Update the rules
        dropHandler.updateAcceptRules(childUidl);
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
     * Emphasisizes a container element
     * 
     * @param element
     */
    protected void emphasis(Element element) {
        // Remove previous emphasis
        deEmphasis();

        if (element == firstContainer || element == secondContainer) {
            element.addClassName(OVER);
            currentEmphasis = element;
        } else if (splitter.isOrHasChild(element)) {
            currentEmphasis = splitter.getChild(0).cast();
            currentEmphasis.addClassName(OVER_SPLITTER);
        }
    }

    /**
     * Removes any previous emphasis made by drag&drag
     */
    protected void deEmphasis() {
        if (currentEmphasis != null) {
            currentEmphasis.removeClassName(OVER);
            currentEmphasis.removeClassName(OVER_SPLITTER);
            currentEmphasis = null;
        }
    }

    /**
     * Returns the container element which wraps the first (left-most) component
     * 
     * @return
     */
    protected Element getFirstContainer() {
        return firstContainer;
    }

    /**
     * Returns the container element which wraps the second (right-most)
     * component
     * 
     * @return
     */
    protected Element getSecondContainer() {
        return secondContainer;
    }

    /**
     * Returns the splitter element
     * 
     * @return
     */
    protected Element getSplitter() {
        return splitter;
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
        Element over = event.getElementOver();

        // Resolve where the drop was made
        VerticalDropLocation location = null;
        Widget content = null;
        if (firstContainer.isOrHasChild(over)) {
            location = VerticalDropLocation.TOP;
            content = Util.findWidget((Element) firstContainer.getChild(0),
                    null);
        } else if (splitter.isOrHasChild(over)) {
            location = VerticalDropLocation.MIDDLE;
            content = this;
        } else if (secondContainer.isOrHasChild(over)) {
            location = VerticalDropLocation.BOTTOM;
            content = Util.findWidget((Element) secondContainer.getChild(0),
                    null);
        }

        event.getDropDetails().put(
                Constants.DROP_DETAIL_VERTICAL_DROP_LOCATION, location);

        if (content != null) {
            event.getDropDetails().put(Constants.DROP_DETAIL_OVER_CLASS,
                    content.getClass().getName());
        } else {
            event.getDropDetails().put(Constants.DROP_DETAIL_OVER_CLASS,
                    this.getClass().getName());
        }

        // Add mouse event details
        MouseEventDetails details = new MouseEventDetails(
                event.getCurrentGwtEvent(), getElement());
        event.getDropDetails().put(Constants.DROP_DETAIL_MOUSE_EVENT,
                details.serialize());
    }
}
