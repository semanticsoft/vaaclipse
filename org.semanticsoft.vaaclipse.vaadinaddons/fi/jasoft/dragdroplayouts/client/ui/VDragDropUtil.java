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

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.Container;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;
import com.vaadin.terminal.gwt.client.VCaption;
import com.vaadin.terminal.gwt.client.VConsole;
import com.vaadin.terminal.gwt.client.ui.VScrollTable;
import com.vaadin.terminal.gwt.client.ui.VTwinColSelect;
import com.vaadin.terminal.gwt.client.ui.dd.HorizontalDropLocation;
import com.vaadin.terminal.gwt.client.ui.dd.VTransferable;
import com.vaadin.terminal.gwt.client.ui.dd.VerticalDropLocation;

import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;

public final class VDragDropUtil {

    private VDragDropUtil() {
        // Prevent instantiation
    }

    /**
     * Get the vertical drop location in a ordered layout
     * 
     * @param element
     *            The target element or cell
     * @param clientY
     *            The client y-coordinate
     * @param topBottomRatio
     *            The ratio how the cell has been divided
     * @return The drop location
     */
    public static VerticalDropLocation getVerticalDropLocation(Element element,
            int clientY, double topBottomRatio) {
        int offsetHeight = element.getOffsetHeight();
        return getVerticalDropLocation(element, offsetHeight, clientY,
                topBottomRatio);
    }

    /**
     * Get the vertical drop location in a ordered layout
     * 
     * @param element
     *            The target element or cell
     * @param offsetHeight
     *            The height of the cell
     * @param clientY
     *            The width of the cell
     * @param topBottomRatio
     *            The ratio of the cell
     * @return The location of the drop
     */
    public static VerticalDropLocation getVerticalDropLocation(Element element,
            int offsetHeight, int clientY, double topBottomRatio) {

        int absoluteTop = element.getAbsoluteTop();
        int fromTop = clientY - absoluteTop;

        float percentageFromTop = (fromTop / (float) offsetHeight);
        if (percentageFromTop < topBottomRatio) {
            return VerticalDropLocation.TOP;
        } else if (percentageFromTop > 1 - topBottomRatio) {
            return VerticalDropLocation.BOTTOM;
        } else {
            return VerticalDropLocation.MIDDLE;
        }
    }

    /**
     * Get the horizontal drop location in an ordered layout
     * 
     * @param element
     *            The target element or cell
     * @param clientX
     *            The x-coordinate of the drop
     * @param leftRightRatio
     *            The ratio of how the cell has been divided
     * @return the drop location relative to the cell
     */
    public static HorizontalDropLocation getHorizontalDropLocation(
            Element element, int clientX, double leftRightRatio) {

        int absoluteLeft = element.getAbsoluteLeft();
        int offsetWidth = element.getOffsetWidth();
        int fromTop = clientX - absoluteLeft;

        float percentageFromTop = (fromTop / (float) offsetWidth);
        if (percentageFromTop < leftRightRatio) {
            return HorizontalDropLocation.LEFT;
        } else if (percentageFromTop > 1 - leftRightRatio) {
            return HorizontalDropLocation.RIGHT;
        } else {
            return HorizontalDropLocation.CENTER;
        }
    }

    /**
     * Creates a transferable for the tabsheet
     * 
     * @param tabsheet
     *            The tabsheet the event occurred
     * @param tab
     *            The tab on which the event occurred
     * @param event
     *            The event
     * @param root
     *            The root widget
     * @return
     */
    private static VTransferable createTabsheetTransferableFromMouseDown(
            VDDTabSheet tabsheet, Widget tab, NativeEvent event) {

        // Create transferable
        VTransferable transferable = new VTransferable();
        transferable.setDragSource(tabsheet);
        if (tabsheet != tab) {
            transferable.setData(Constants.TRANSFERABLE_DETAIL_COMPONENT, tab);
            transferable.setData(Constants.TRANSFERABLE_DETAIL_INDEX,
                    tabsheet.getTabPosition(tab));
			transferable.setData("tabSheetAbsoluteTop", tabsheet.getAbsoluteTop());
			transferable.setData("tabSheetAbsoluteLeft", tabsheet.getAbsoluteLeft());
        }
        transferable.setData(Constants.TRANSFERABLE_DETAIL_MOUSEDOWN,
                new MouseEventDetails(event).serialize());

        return transferable;
    }

    /**
     * Creates a transferable for the Accordion
     * 
     * @param accordion
     *            The Accordion where the event occurred
     * @param tab
     *            The tab on which the event occurred
     * @param event
     *            The event
     * @param root
     *            The root widget
     * @return
     */
    private static VTransferable createAccordionTransferableFromMouseDown(
            VDDAccordion accordion, VCaption tabCaption, NativeEvent event) {

        // Create transferable
        VTransferable transferable = new VTransferable();
        transferable.setDragSource(accordion);
        transferable.setData(Constants.TRANSFERABLE_DETAIL_CAPTION, tabCaption);
        transferable.setData(Constants.TRANSFERABLE_DETAIL_COMPONENT,
                tabCaption.getParent());
        transferable.setData(Constants.TRANSFERABLE_DETAIL_INDEX,
                accordion.getWidgetIndex(tabCaption.getParent()));
        transferable.setData(Constants.TRANSFERABLE_DETAIL_MOUSEDOWN,
                new MouseEventDetails(event).serialize());

        return transferable;
    }

    /**
     * Creates a transferable from a mouse down event. Returns null if creation
     * was not successful.
     * 
     * @param event
     *            The mouse down event
     * @param root
     *            The root layout from where the component is dragged
     * @return A transferable or NULL if something failed
     */
    public static VTransferable createLayoutTransferableFromMouseDown(
            NativeEvent event, Widget root) {

        Widget w = Util.findWidget((Element) event.getEventTarget().cast(),
                null);

        // NPE check
        if (w == null) {
            VConsole.error("Could not find widget");
            return null;
        }

        // Special treatment for Tabsheet
        if (root instanceof VDDTabSheet) {
            if (w instanceof VCaption || w == root) {
                // We are dragging a tabsheet tab, handle it
                return createTabsheetTransferableFromMouseDown(
                        (VDDTabSheet) root, w, event);
            } else {
                return null;
            }
        }

        // Special treatment for Accordion
        if (root instanceof VDDAccordion) {
            if (w instanceof VCaption || w == root) {
                // We are dragging a Accordion tab, handle it
                return createAccordionTransferableFromMouseDown(
                        (VDDAccordion) root, (VCaption) w, event);
            } else {
                // Do not allow dragging content, only the "tab"
                return null;
            }
        }

        // Ensure we have the right widget
        w = getTransferableWidget(w);

        // Find the containing layout of the component
        Container layout = Util.getLayout(w);

        // Iterate until parent either is the root or a layout with drag and
        // drop enabled
        while (layout != root && layout != null) {
            if (layout instanceof VHasDragMode
                    && ((VHasDragMode) layout).getDragMode() != LayoutDragMode.NONE) {
                // Found parent layout with support for drag and drop
                break;
            }
            w = (Widget) layout;
            layout = Util.getLayout(w);
        }

        // Consistency check
        if (w == null || root == w || layout == null) {
            VConsole.error("Consistency check failed");
            return null;
        }

        // Ensure layout allows dragging
        if (!isDraggingEnabled(layout)) {
            return null;
        }

        return createTransferable(layout, w, event);
    }

    private static VTransferable createTransferable(Container layout,
            Widget widget, NativeEvent event) {
        VTransferable transferable = new VTransferable();
        transferable.setDragSource(layout);
        transferable.setData(Constants.TRANSFERABLE_DETAIL_COMPONENT, widget);
        transferable.setData(Constants.TRANSFERABLE_DETAIL_MOUSEDOWN,
                new MouseEventDetails(event).serialize());
        return transferable;
    }

    private static Widget getTransferableWidget(Widget w) {
        // Ensure w is Paintable
        while (!(w instanceof Paintable) && w.getParent() != null) {
            w = w.getParent();
        }

        // Are we grabbing the caption of a component
        boolean isCaption = w instanceof VCaption;

        if (isCaption) {
            // Dragging caption means dragging component the caption belongs to
            Widget owner = (Widget) ((VCaption) w).getOwner();
            if (owner != null) {
                w = owner;
            }
        } else if (w instanceof VScrollTable.VScrollTableBody.VScrollTableRow) {
            // Table rows are paintable but we do not want to drag them so
            // search for next paintable which should be the table itself
            w = w.getParent();
            while (!(w instanceof Paintable)) {
                w = w.getParent();
            }
        } else if (w.getParent().getParent().getParent() instanceof VTwinColSelect) {
            // TwinColSelect has paintable buttons..
            w = w.getParent().getParent().getParent();
        }

        return w;
    }

    /**
     * Is dragging enabled for a component container
     * 
     * @param layout
     *            The component container to check
     * @return
     */
    private static boolean isDraggingEnabled(Container layout) {
        if (layout instanceof VHasDragMode) {
            LayoutDragMode dm = ((VHasDragMode) layout).getDragMode();
            return dm != LayoutDragMode.NONE;
        }
        return false;
    }

    /**
     * Removes the Drag and drop fake paintable from an UIDL
     * 
     * @param uidl
     *            The uidl which contains a dragdrop paintable (-ac)
     * @return UIDL stripped of the paintable
     */
    public static native UIDL removeDragDropCriteraFromUIDL(UIDL uidl)
    /*-{
        var obj = new Array();
        for(key in uidl){
            if(uidl[key][0] != "-ac"){
               obj[key] = uidl[key];
            }
        }
        return obj;
    }-*/;

    /**
     * Measures the left margin of an element
     * 
     * @param element
     *            The element to measure
     * @return Left margin in pixels
     */
    public static int measureMarginLeft(Element element) {
        return element.getAbsoluteLeft()
                - element.getParentElement().getAbsoluteLeft();
    }

    /**
     * Measures the top margin of an element
     * 
     * @param element
     *            The element to measure
     * @return Top margin in pixels
     */
    public static int measureMarginTop(Element element) {
        return element.getAbsoluteTop()
                - element.getParentElement().getAbsoluteTop();
    }
}
