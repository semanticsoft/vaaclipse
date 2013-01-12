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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.BrowserInfo;
import com.vaadin.client.Util;
import com.vaadin.client.VConsole;
import com.vaadin.client.ui.VButton;
import com.vaadin.client.ui.VCssLayout;
import com.vaadin.client.ui.VSlider;
import com.vaadin.client.ui.VTextField;
import com.vaadin.client.ui.dd.VDragAndDropManager;
import com.vaadin.client.ui.dd.VDragEvent;
import com.vaadin.client.ui.dd.VTransferable;

/**
 * Mouse handler for starting component drag operations
 * 
 * @author John Ahlroos
 */
public class VLayoutDragDropMouseHandler implements MouseDownHandler,
        TouchStartHandler {

    public static final String ACTIVE_DRAG_SOURCE_STYLENAME = "v-dd-active-drag-source";

    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    private final Widget root;

    private Widget currentDraggedWidget;

    private HandlerRegistration mouseUpHandlerReg;

    private final List<HandlerRegistration> handlers = new LinkedList<HandlerRegistration>();

    private final List<DragStartListener> dragStartListeners = new ArrayList<VLayoutDragDropMouseHandler.DragStartListener>();

    /**
     * A listener to listen for drag start events
     */
    public interface DragStartListener {
        /**
         * Called when a drag is about to begin
         * 
         * @param widget
         *            The widget which is about to be dragged
         * @param mode
         *            The draggin mode
         * @return Should the dragging be commenced.
         */
        boolean dragStart(Widget widget, LayoutDragMode mode);
    }

    /**
     * Constructor
     * 
     * @param root
     *            The root element
     * @param dragMode
     *            The drag mode of the layout
     */
    public VLayoutDragDropMouseHandler(Widget root, LayoutDragMode dragMode) {
        this.dragMode = dragMode;
        this.root = root;
    }

    /**
     * Is the mouse down event a valid mouse drag event, i.e. left mouse button
     * is pressed without any modifier keys
     * 
     * @param event
     *            The mouse event
     * @return Is the mouse event a valid drag event
     */
    private boolean isMouseDragEvent(NativeEvent event) {
        boolean hasModifierKey = event.getAltKey() || event.getCtrlKey()
                || event.getMetaKey() || event.getShiftKey();
        return !(hasModifierKey || event.getButton() > NativeEvent.BUTTON_LEFT);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.gwt.event.dom.client.TouchStartHandler#onTouchStart(com.google
     * .gwt.event.dom.client.TouchStartEvent)
     */
    public void onTouchStart(TouchStartEvent event) {
        initiateDrag(event.getNativeEvent());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.gwt.event.dom.client.MouseDownHandler#onMouseDown(com.google
     * .gwt.event.dom.client.MouseDownEvent)
     */
    public void onMouseDown(MouseDownEvent event) {
        initiateDrag(event.getNativeEvent());
        event.preventDefault();
        event.stopPropagation();
    }

    /**
     * Called when the dragging a component should be initiated by both a mouse
     * down event as well as a touch start event
     * 
     * @param event
     */
    protected void initiateDrag(NativeEvent event) {
        // Check that dragging is enabled
        if (dragMode == LayoutDragMode.NONE) {
            return;
        }

        // Dragging can only be done with left mouse button and no modifier keys
        if (!isMouseDragEvent(event) && !Util.isTouchEvent(event)) {
            return;
        }

        // Create the transfarable
        VTransferable transferable = VDragDropUtil
                .createLayoutTransferableFromMouseDown(event, root);

        // Are we trying to drag the root layout
        if (transferable == null) {
            VConsole.error("Creating transferable on mouse down returned null");
            return;
        }

        // Resolve the component
        final Widget w;
        if (root instanceof VDDAccordion
                && transferable.getData(Constants.TRANSFERABLE_DETAIL_CAPTION) != null) {
            w = (Widget) transferable
                    .getData(Constants.TRANSFERABLE_DETAIL_CAPTION);
        } else if (transferable
                .getData(Constants.TRANSFERABLE_DETAIL_COMPONENT) != null) {
            w = (Widget) transferable
                    .getData(Constants.TRANSFERABLE_DETAIL_COMPONENT);
        } else {
            // Failsafe if no widget was found
            w = root;
            VConsole.log("Could not resolve component, using root as component");
        }

        // Announce drag start to listeners
        for (DragStartListener dl : dragStartListeners) {
            if (!dl.dragStart(w, dragMode)) {
                VDragAndDropManager.get().interruptDrag();
                return;
            }
        }

        /*
         * A hack to remove slider popup when dragging. This is done by first
         * focusing the slider and then unfocusing so we get a blur event which
         * will remove the popup.
         */
        if (w instanceof VSlider) {
            VSlider slider = (VSlider) w;
            slider.setFocus(true);
            slider.setFocus(false);
        }

        currentDraggedWidget = w;

        // Announce to handler that we are starting a drag operation
        VDragEvent currentDragEvent = VDragAndDropManager.get().startDrag(
                transferable, event, true);

        if (w instanceof VButton && BrowserInfo.get().isIE()) {
            /*
             * Due to Buttons crazy implementation we need to simulate a mouse
             * move to get the drag going in IE without first focusing the
             * button. Crazy shit.
             */
            VButton button = (VButton) w;
            NativeEvent evt = Document.get().createMouseMoveEvent(1,
                    event.getScreenX(), event.getScreenY(),
                    Util.getTouchOrMouseClientX(event),
                    Util.getTouchOrMouseClientY(event), false, false, false,
                    false, 0);
            button.getElement().dispatchEvent(evt);
        }

        // Create the drag image
        if (root instanceof VCssLayout) {
            /*
             * CSS Layout does not have an enclosing div so we just use the
             * component dov
             */
            currentDragEvent.createDragImage((Element) w.getElement().cast(),
                    true);

        } else {
            /*
             * Other layouts uses a enclosing div so we use it.
             */
            currentDragEvent.createDragImage((Element) w.getElement()
                    .getParentNode().cast(), true);
        }

        if (BrowserInfo.get().isIE7() && w instanceof VTextField) {
            currentDragEvent.createDragImage(w.getElement(), true);
            // Capture something so focus is not applied to elements
            Event.setCapture(RootPanel.getBodyElement());
        }

        Element clone = currentDragEvent.getDragImage();
        Style cloneStyle = clone.getStyle();
        if (BrowserInfo.get().isIE()) {
            // Fix IE not aligning the drag image correctly when dragging
            // layouts
            cloneStyle.setPosition(Position.ABSOLUTE);
        }

        currentDraggedWidget.addStyleName(ACTIVE_DRAG_SOURCE_STYLENAME);

        // Listen to mouse up for cleanup
        mouseUpHandlerReg = Event
                .addNativePreviewHandler(new Event.NativePreviewHandler() {
                    public void onPreviewNativeEvent(NativePreviewEvent event) {
                        if (event.getTypeInt() == Event.ONMOUSEUP
                                || event.getTypeInt() == Event.ONTOUCHEND
                                || event.getTypeInt() == Event.ONTOUCHCANCEL) {
                            if (mouseUpHandlerReg != null) {
                                mouseUpHandlerReg.removeHandler();
                                if (currentDraggedWidget != null) {
                                    currentDraggedWidget
                                            .removeStyleName(ACTIVE_DRAG_SOURCE_STYLENAME);
                                    currentDraggedWidget = null;
                                }
                            }

                            // Ensure capturing is turned off at mouse up
                            Event.releaseCapture(RootPanel.getBodyElement());
                        }
                    }
                });

    }

    /**
     * Set the current drag mode
     * 
     * @param dragMode
     *            The drag mode to use
     */
    public void updateDragMode(LayoutDragMode dragMode) {
        this.dragMode = dragMode;
    }

    /**
     * Add a drag start listener to monitor drag starts
     * 
     * @param listener
     */
    public void addDragStartListener(DragStartListener listener) {
        dragStartListeners.add(listener);
    }

    /**
     * Remove a drag start listener
     * 
     * @param listener
     */
    public void removeDragStartListener(DragStartListener listener) {
        dragStartListeners.remove(listener);
    }

    /**
     * Start listening to events
     */
    public void attach() {
        if (handlers.isEmpty()) {
            handlers.add(root.addDomHandler(this, MouseDownEvent.getType()));
            handlers.add(root.addDomHandler(this, TouchStartEvent.getType()));
        }
    }

    /**
     * Attach to another widget than root
     * 
     * @param widget
     *            The widget to attach to
     */
    public void attachTo(Widget widget) {
        if (handlers.isEmpty()) {
            handlers.add(widget.addDomHandler(this, MouseDownEvent.getType()));
            handlers.add(widget.addDomHandler(this, TouchStartEvent.getType()));
        }
    }

    /**
     * Stop listening to events
     */
    public void detach() {
        for (HandlerRegistration reg : handlers) {
            reg.removeHandler();
        }
        handlers.clear();
    }
}
