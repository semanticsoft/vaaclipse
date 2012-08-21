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
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;
import com.vaadin.terminal.gwt.client.ui.VGridLayout;
import com.vaadin.terminal.gwt.client.ui.dd.HorizontalDropLocation;
import com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VAcceptCallback;
import com.vaadin.terminal.gwt.client.ui.dd.VDragEvent;
import com.vaadin.terminal.gwt.client.ui.dd.VDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VHasDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VerticalDropLocation;
import com.vaadin.terminal.gwt.client.ui.layout.ChildComponentContainer;

import fi.jasoft.dragdroplayouts.client.ui.VLayoutDragDropMouseHandler.DragStartListener;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;

public class VDDGridLayout extends VGridLayout implements VHasDragMode,
        VHasDropHandler, DragStartListener {

    public static final String CLASSNAME = "v-ddgridlayout";
    public static final String OVER = CLASSNAME + "-over";

    public static final float DEFAULT_HORIZONTAL_RATIO = 0.2f;
    public static final float DEFAULT_VERTICAL_RATIO = 0.2f;

    private VAbstractDropHandler dropHandler;

    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    private final HTML dragShadow = new HTML("");

    private float cellLeftRightDropRatio = DEFAULT_HORIZONTAL_RATIO;

    private float cellTopBottomDropRatio = DEFAULT_VERTICAL_RATIO;

    protected final AbsolutePanel canvas;

    protected ApplicationConnection client;

    protected boolean iframeCoversEnabled = false;

    private final VDragFilter dragFilter = new VDragFilter();

    private final IframeCoverUtility iframeCoverUtility = new IframeCoverUtility();

    public VDDGridLayout() {
        super();
        canvas = (AbsolutePanel) getWidget();
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

    // The drag mouse handler which handles the creation of the transferable
    private final VLayoutDragDropMouseHandler ddMouseHandler = new VLayoutDragDropMouseHandler(
            this, dragMode);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.vaadin.terminal.gwt.client.ui.VGridLayout#updateFromUIDL(com.vaadin
     * .terminal.gwt.client.UIDL,
     * com.vaadin.terminal.gwt.client.ApplicationConnection)
     */
    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        super.updateFromUIDL(uidl, client);

        this.client = client;

        // Update drop handler if necessary
        UIDL c = null;
        for (final Iterator<Object> it = uidl.getChildIterator(); it.hasNext();) {
            c = (UIDL) it.next();
            if (c.getTag().equals("-ac")) {
                updateDropHandler(c);
                break;
            }
        }

        handleDragModeUpdate(uidl);
        handleCellDropRatioUpdate(uidl);

        // Iframe cover check
        iframeCoverUtility.setIframeCoversEnabled(iframeCoversEnabled,
                getElement());

        dragFilter.update(uidl, client);
    }

    /**
     * Returns the drop handler used when the user drops a component over the
     * Grid Layout
     */
    public VDropHandler getDropHandler() {
        return dropHandler;
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
                iframeCoversEnabled = true;

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

    private void updateDropDetails(VDragEvent event) {
        CellDetails cd = getCellDetails(event);
        if (cd != null) {
            // Add row
            event.getDropDetails().put(Constants.DROP_DETAIL_ROW,
                    Integer.valueOf(cd.row));

            // Add column
            event.getDropDetails().put(Constants.DROP_DETAIL_COLUMN,
                    Integer.valueOf(cd.column));

            // Add horizontal position
            HorizontalDropLocation hl = getHorizontalDropLocation(cd, event);
            event.getDropDetails().put(
                    Constants.DROP_DETAIL_HORIZONTAL_DROP_LOCATION, hl);

            // Add vertical position
            VerticalDropLocation vl = getVerticalDropLocation(cd, event);
            event.getDropDetails().put(
                    Constants.DROP_DETAIL_VERTICAL_DROP_LOCATION, vl);

            // Check if the cell we are hovering over has content
            boolean hasContent = false;
            ChildComponentContainer container = null;
            for (ChildComponentContainer cont : widgetToComponentContainer
                    .values()) {
                if (DOM.isOrHasChild(cont.getElement(), event.getElementOver())) {
                    hasContent = true;
                    container = cont;
                    break;
                }
            }
            event.getDropDetails().put(Constants.DROP_DETAIL_EMPTY_CELL,
                    !hasContent);

            if (hasContent) {
                /*
                 * Add Classname of component over the drag. This can be used by
                 * a a client side criteria to verify that a drag is over a
                 * specific class of component.
                 */
                Widget w = container.getWidget();
                if (w != null) {
                    String className = w.getClass().getName();
                    event.getDropDetails().put(
                            Constants.DROP_DETAIL_OVER_CLASS, className);
                } else {
                    event.getDropDetails().put(
                            Constants.DROP_DETAIL_OVER_CLASS,
                            VDDGridLayout.this);
                }

            }

            // Add mouse event details
            MouseEventDetails details = new MouseEventDetails(
                    event.getCurrentGwtEvent(), getElement());
            event.getDropDetails().put(Constants.DROP_DETAIL_MOUSE_EVENT,
                    details.serialize());
        }
    }

    /**
     * Returns the horizontal drop location
     * 
     * @param cell
     *            The cell details
     * @param event
     *            The drag event
     * @return
     */
    private HorizontalDropLocation getHorizontalDropLocation(CellDetails cell,
            VDragEvent event) {

        // Get the horizontal location
        HorizontalDropLocation hdetail;
        int x = Util.getTouchOrMouseClientX(event.getCurrentGwtEvent())
                - canvas.getAbsoluteLeft() - cell.x;

        assert (x >= 0 && x <= cell.width);

        if (x < cell.width * cellLeftRightDropRatio) {
            hdetail = HorizontalDropLocation.LEFT;
        } else if (x < cell.width * (1.0 - cellLeftRightDropRatio)) {
            hdetail = HorizontalDropLocation.CENTER;
        } else {
            hdetail = HorizontalDropLocation.RIGHT;
        }
        return hdetail;
    }

    /**
     * Returns the vertical drop location
     * 
     * @param cell
     *            The cell details
     * @param event
     *            The drag event
     * @return
     */
    private VerticalDropLocation getVerticalDropLocation(CellDetails cell,
            VDragEvent event) {

        // Get the vertical location
        VerticalDropLocation vdetail;
        int y = Util.getTouchOrMouseClientY(event.getCurrentGwtEvent())
                - canvas.getAbsoluteTop() - cell.y;

        assert (y >= 0 && y <= cell.height);

        if (y < cell.height * cellTopBottomDropRatio) {
            vdetail = VerticalDropLocation.TOP;
        } else if (y < cell.height * (1.0 - cellTopBottomDropRatio)) {
            vdetail = VerticalDropLocation.MIDDLE;
        } else {
            vdetail = VerticalDropLocation.BOTTOM;
        }
        return vdetail;
    }

    /**
     * Emphasizes a component container when user is hovering a dragged
     * component over the container.
     * 
     * @param container
     *            The container
     * @param event
     */
    protected void emphasis(CellDetails cell, VDragEvent event) {

        // Remove any existing empasis
        deEmphasis();

        // Ensure we are not dragging ourself into ourself
        Widget draggedComponent = (Widget) event.getTransferable().getData(
                Constants.TRANSFERABLE_DETAIL_COMPONENT);
        if (draggedComponent == VDDGridLayout.this) {
            return;
        }

        HorizontalDropLocation hl = getHorizontalDropLocation(cell, event);
        VerticalDropLocation vl = getVerticalDropLocation(cell, event);

        // Apply over style
        UIObject.setStyleName(dragShadow.getElement(), OVER, true);

        // Add vertical location dependent style
        UIObject.setStyleName(dragShadow.getElement(), OVER + "-"
                + vl.toString().toLowerCase(), true);

        // Add horizontal location dependent style
        UIObject.setStyleName(dragShadow.getElement(), OVER + "-"
                + hl.toString().toLowerCase(), true);
    }

    /**
     * Removes any emphasis previously set by emphasis
     */
    private void deEmphasis() {

        UIObject.setStyleName(dragShadow.getElement(), OVER, false);

        // Horizontal styles
        UIObject.setStyleName(dragShadow.getElement(), OVER + "-"
                + HorizontalDropLocation.LEFT.toString().toLowerCase(), false);
        UIObject.setStyleName(dragShadow.getElement(), OVER + "-"
                + HorizontalDropLocation.CENTER.toString().toLowerCase(), false);
        UIObject.setStyleName(dragShadow.getElement(), OVER + "-"
                + HorizontalDropLocation.RIGHT.toString().toLowerCase(), false);

        // Vertical styles
        UIObject.setStyleName(dragShadow.getElement(), OVER + "-"
                + VerticalDropLocation.TOP.toString().toLowerCase(), false);
        UIObject.setStyleName(dragShadow.getElement(), OVER + "-"
                + VerticalDropLocation.MIDDLE.toString().toLowerCase(), false);
        UIObject.setStyleName(dragShadow.getElement(), OVER + "-"
                + VerticalDropLocation.BOTTOM.toString().toLowerCase(), false);

    }

    public LayoutDragMode getDragMode() {
        return dragMode;
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
        if (uidl.hasAttribute(Constants.ATTRIBUTE_VERTICAL_DROP_RATIO)) {
            cellTopBottomDropRatio = uidl
                    .getFloatAttribute(Constants.ATTRIBUTE_VERTICAL_DROP_RATIO);
        }
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
     * Creates a drop handler if one has not alread been created and updates it
     * with the details recieved from the server.
     * 
     * @param childUidl
     *            The UIDL
     */
    protected void updateDropHandler(UIDL childUidl) {
        if (dropHandler == null) {
            dropHandler = new VAbstractDropHandler() {

                public ApplicationConnection getApplicationConnection() {
                    return client;
                };

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
                 * #getPaintable()
                 */
                @Override
                public Paintable getPaintable() {
                    return VDDGridLayout.this;
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
                    // Nop
                }

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
                 * #dragEnter(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
                 */
                @Override
                public void dragEnter(VDragEvent drag) {
                    // Add the marker that shows the drop location while
                    // dragging
                    canvas.insert(dragShadow, 0);
                    postEnterHook(drag);
                };

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
                 * #drop(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
                 */
                @Override
                public boolean drop(VDragEvent drag) {

                    // Update the detail of the drop
                    updateDropDetails(drag);

                    // Remove emphasis
                    deEmphasis();

                    // Remove the drag shadow
                    canvas.remove(dragShadow);

                    return postDropHook(drag);
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

                    // Remove emphasis from previous selection
                    deEmphasis();

                    // Update the drop details so we can then validate them
                    updateDropDetails(drag);

                    postOverHook(drag);

                    // Emphasis drop location
                    validate(new VAcceptCallback() {
                        public void accepted(VDragEvent event) {
                            CellDetails cd = getCellDetails(event);
                            if (cd != null) {
                                dragShadow.setWidth(cd.width + "px");
                                dragShadow.setHeight(cd.height + "px");
                                canvas.setWidgetPosition(dragShadow, cd.x, cd.y);
                                emphasis(cd, event);
                            }
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
                    canvas.remove(dragShadow);
                    postLeaveHook(drag);
                    super.dragLeave(drag);
                };
            };
        }

        // Update the rules
        dropHandler.updateAcceptRules(childUidl);
    }

    /**
     * A helper class returned by getCellDetailsByCoordinates() which contains
     * positional and size data of the cell.
     */
    protected class CellDetails {
        public int row = -1;
        public int column = -1;
        public int x = -1;
        public int y = -1;
        public int width = -1;
        public int height = -1;
    }

    private CellDetails getCellDetails(VDragEvent event) {
        int x = Util.getTouchOrMouseClientX(event.getCurrentGwtEvent())
                - canvas.getAbsoluteLeft();
        int y = Util.getTouchOrMouseClientY(event.getCurrentGwtEvent())
                - canvas.getAbsoluteTop();
        return getCellDetailsByCoordinates(x, y);
    }

    /**
     * Returns details of the cell under the given position
     * 
     * @param x
     *            The x-coordinate
     * @param y
     *            The y-coordinate
     * @return The details of the cell under the coordinate
     */
    private CellDetails getCellDetailsByCoordinates(int x, int y) {

        CellDetails cd = new CellDetails();
        int[] columnWidths = getColumnWidths();
        int[] rowHeights = getRowHeights();

        // Get column and x coordinate
        int temp = 0;
        for (int col = 0; col < columnWidths.length; col++) {
            if (x >= temp && x <= temp + columnWidths[col]) {
                cd.column = col;
                cd.x = temp;
                cd.width = columnWidths[col];
                break;
            }
            temp += columnWidths[col] + getHorizontalSpacing();
        }

        // get row
        temp = 0;
        for (int row = 0; row < rowHeights.length; row++) {
            if (y >= temp && y <= temp + rowHeights[row]) {
                cd.row = row;
                cd.y = temp;
                cd.height = rowHeights[row];
                break;
            }
            temp += rowHeights[row] + getVerticalSpacing();
        }

        // Sanity check
        if (cd.row == -1 || cd.column == -1 || cd.width == -1
                || cd.height == -1) {
            return null;
        }

        return cd;
    }
}
