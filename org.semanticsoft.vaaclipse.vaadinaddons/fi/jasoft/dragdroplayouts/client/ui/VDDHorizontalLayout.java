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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;
import com.vaadin.terminal.gwt.client.ui.VHorizontalLayout;
import com.vaadin.terminal.gwt.client.ui.dd.HorizontalDropLocation;
import com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VAcceptCallback;
import com.vaadin.terminal.gwt.client.ui.dd.VDragEvent;
import com.vaadin.terminal.gwt.client.ui.dd.VDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VHasDropHandler;
import com.vaadin.terminal.gwt.client.ui.layout.ChildComponentContainer;

import fi.jasoft.dragdroplayouts.DDHorizontalLayout;
import fi.jasoft.dragdroplayouts.client.ui.VLayoutDragDropMouseHandler.DragStartListener;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;

/**
 * Client side counterpart for {@link DDHorizontalLayout}
 */
public class VDDHorizontalLayout extends VHorizontalLayout implements
        VHasDragMode, VHasDropHandler, DragStartListener {

    public static final float DEFAULT_HORIZONTAL_DROP_RATIO = 0.2f;

    public static final String OVER = "v-ddorderedlayout-over";
    public static final String OVER_SPACED = OVER + "-spaced";

    private Widget currentlyEmphasised;

    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    private float cellLeftRightDropRatio = DEFAULT_HORIZONTAL_DROP_RATIO;

    private VAbstractDropHandler dropHandler;

    protected boolean iframeCoversEnabled = false;

    private final VDragFilter dragFilter = new VDragFilter();

    private final IframeCoverUtility iframeCoverUtility = new IframeCoverUtility();

    public VDDHorizontalLayout() {
        super();
        ddMouseHandler.addDragStartListener(this);
    }

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
     * com.vaadin.terminal.gwt.client.ui.VOrderedLayout#updateFromUIDL(com.vaadin
     * .terminal.gwt.client.UIDL,
     * com.vaadin.terminal.gwt.client.ApplicationConnection)
     */
    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {

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

        // Handle drop ratio settings
        handleCellDropRatioUpdate(modifiedUIDL);

        // Iframe cover check
        iframeCoverUtility.setIframeCoversEnabled(iframeCoversEnabled,
                getElement());

        dragFilter.update(modifiedUIDL, client);
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

    /**
     * Handles updates the the hoover zones of the cell which specifies at which
     * position a component is dropped over a cell
     * 
     * @param uidl
     *            The UIDL
     */
    private void handleCellDropRatioUpdate(UIDL uidl) {
        if (uidl.hasAttribute(Constants.ATTRIBUTE_HORIZONTAL_DROP_RATIO)) {
            cellLeftRightDropRatio = uidl
                    .getFloatAttribute(Constants.ATTRIBUTE_HORIZONTAL_DROP_RATIO);
        }
    }

    /**
     * Removes any applies drag and drop style applied by emphasis()
     */
    private void deEmphasis() {
        if (currentlyEmphasised != null) {
            // Universal over style
            UIObject.setStyleName(currentlyEmphasised.getElement(), OVER, false);
            UIObject.setStyleName(currentlyEmphasised.getElement(),
                    OVER_SPACED, false);

            // Horizontal styles
            UIObject.setStyleName(currentlyEmphasised.getElement(), OVER + "-"
                    + HorizontalDropLocation.LEFT.toString().toLowerCase(),
                    false);
            UIObject.setStyleName(currentlyEmphasised.getElement(), OVER + "-"
                    + HorizontalDropLocation.CENTER.toString().toLowerCase(),
                    false);
            UIObject.setStyleName(currentlyEmphasised.getElement(), OVER + "-"
                    + HorizontalDropLocation.RIGHT.toString().toLowerCase(),
                    false);

            currentlyEmphasised = null;
        }
    }

    /**
     * Returns the horizontal location within the cell when hoovering over the
     * cell. By default the cell is devided into three parts: left,center,right
     * with the ratios 10%,80%,10%;
     * 
     * @param container
     *            The widget container
     * @param event
     *            The drag event
     * @return The horizontal drop location
     */
    private HorizontalDropLocation getHorizontalDropLocation(Widget container,
            VDragEvent event) {
        return VDragDropUtil.getHorizontalDropLocation(container.getElement(),
                Util.getTouchOrMouseClientX(event.getCurrentGwtEvent()),
                cellLeftRightDropRatio);
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
     * Updates the drop details while dragging. This is needed to ensure client
     * side criterias can validate the drop location.
     * 
     * @param widget
     *            The container which we are hovering over
     * @param event
     *            The drag event
     */
    protected void updateDropDetails(Widget widget, VDragEvent event) {
        if (widget == null) {
            // Null check
            return;
        }

        /*
         * The horizontal position within the cell
         */
        event.getDropDetails().put(
                Constants.DROP_DETAIL_HORIZONTAL_DROP_LOCATION,
                getHorizontalDropLocation(widget, event));

        /*
         * The index over which the drag is. Can be used by a client side
         * criteria to verify that a drag is over a certain index.
         */
        WidgetCollection widgets = getChildren();
        event.getDropDetails().put(Constants.DROP_DETAIL_TO,
                widgets.indexOf(widget));

        /*
         * Add Classname of component over the drag. This can be used by a a
         * client side criteria to verify that a drag is over a specific class
         * of component.
         */
        if (widget instanceof ChildComponentContainer) {
            Widget w = ((ChildComponentContainer) widget).getWidget();
            if (w != null) {
                String className = w.getClass().getName();
                event.getDropDetails().put(Constants.DROP_DETAIL_OVER_CLASS,
                        className);
            } else {
                event.getDropDetails().put(Constants.DROP_DETAIL_OVER_CLASS,
                        this.getClass().getName());
            }
        } else {
            event.getDropDetails().put(Constants.DROP_DETAIL_OVER_CLASS,
                    this.getClass().getName());
        }

        // Add mouse event details
        MouseEventDetails details = new MouseEventDetails(
                event.getCurrentGwtEvent(),
                VDDHorizontalLayout.this.getElement());
        event.getDropDetails().put(Constants.DROP_DETAIL_MOUSE_EVENT,
                details.serialize());
    }

    /**
     * Empasises the drop location of the component when hovering over a
     * ĆhildComponentContainer. Passing null as the container removes any
     * previous emphasis.
     * 
     * @param container
     *            The container which we are hovering over
     * @param event
     *            The drag event
     */
    protected void emphasis(Widget container, VDragEvent event) {

        // Remove emphasis from previous hovers
        deEmphasis();

        // Null check..
        if (container == null) {
            return;
        }

        currentlyEmphasised = container;

        // Assign the container the drag and drop over style
        if (spacingEnabled) {
            UIObject.setStyleName(container.getElement(), OVER_SPACED, true);
        } else {
            UIObject.setStyleName(container.getElement(), OVER, true);
        }

        if (container != this) {
            // Add drop location specific style
            UIObject.setStyleName(container.getElement(), OVER
                    + "-"
                    + getHorizontalDropLocation(container, event).toString()
                            .toLowerCase(), true);
        } else {
            UIObject.setStyleName(container.getElement(), OVER + "-"
                    + HorizontalDropLocation.CENTER.toString().toLowerCase(),
                    true);
        }
    }

    /**
     * Returns the current drag mode which determines how the drag is visualized
     */
    public LayoutDragMode getDragMode() {
        return dragMode;
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
            dropHandler = new DropHandler();
        }

        // Update the rules
        dropHandler.updateAcceptRules(childUidl);
    }

    /**
     * Drophandler for handling drops on the Horizontal layout
     * 
     */
    protected class DropHandler extends VAbstractDropHandler {

        protected Map<Element, ChildComponentContainer> elementContainerMap;

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
         * @see com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
         * #getPaintable()
         */
        @Override
        public Paintable getPaintable() {
            return VDDHorizontalLayout.this;
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
         * #dragAccepted (com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
         */
        @Override
        protected void dragAccepted(VDragEvent drag) {
            dragOver(drag);
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
         * #drop(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
         */
        @Override
        public boolean drop(VDragEvent drag) {

            // Un-emphasis any selections
            emphasis(null, null);

            // Update the details
            updateDropDetails(getContainerFromDragEvent(drag), drag);

            elementContainerMap = null;

            return postDropHook(drag) && super.drop(drag);
        };

        /**
         * Finds the container (or widget) that the drag event was over
         * 
         * @param event
         *            The drag event
         * @return
         */
        protected ChildComponentContainer getContainerFromDragEvent(
                VDragEvent event) {
            if (elementContainerMap == null) {
                elementContainerMap = new HashMap<Element, ChildComponentContainer>();
            }

            ChildComponentContainer cont = null;

            // Check if we have a reference stored
            cont = elementContainerMap.get(event.getElementOver());

            if (cont == null) {
                // Else search for the element
                for (ChildComponentContainer c : widgetToComponentContainer
                        .values()) {
                    if (DOM.isOrHasChild(c.getElement(), event.getElementOver())) {
                        cont = c;
                        elementContainerMap.put(event.getElementOver(), cont);
                        break;
                    }
                }
            }

            return cont;
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
         * #dragOver(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
         */
        @Override
        public void dragOver(VDragEvent drag) {

            // Remove any emphasis
            emphasis(null, null);

            // Update the dropdetails so we can validate the drop
            ChildComponentContainer c = getContainerFromDragEvent(drag);
            if (c != null) {
                updateDropDetails(c, drag);
            } else {
                updateDropDetails(VDDHorizontalLayout.this, drag);
            }

            postOverHook(drag);

            // Validate the drop
            validate(new VAcceptCallback() {
                public void accepted(VDragEvent event) {
                    ChildComponentContainer c = getContainerFromDragEvent(event);
                    if (c != null) {
                        emphasis(c, event);
                    } else {
                        emphasis(VDDHorizontalLayout.this, event);
                    }
                }
            }, drag);
        };

        /*
         * (non-Javadoc)
         * 
         * @see com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
         * #dragLeave(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
         */
        @Override
        public void dragLeave(VDragEvent drag) {
            emphasis(null, drag);
            elementContainerMap = null;
            postLeaveHook(drag);
        }
    }

    /**
     * Get the drop handler attached to the Layout
     */
    public VDropHandler getDropHandler() {
        return dropHandler;
    }
}
