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
package fi.jasoft.dragdroplayouts;

import java.util.Map;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.TargetDetailsImpl;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.ClientWidget;

import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.VDDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;
import fi.jasoft.dragdroplayouts.interfaces.LayoutDragSource;
import fi.jasoft.dragdroplayouts.interfaces.ShimSupport;

/**
 * Absolute layout with drag and drop support
 * 
 * @author John Ahlroos / www.jasoft.fi
 */
@SuppressWarnings("serial")
@ClientWidget(VDDAbsoluteLayout.class)
public class DDAbsoluteLayout extends AbsoluteLayout implements
        LayoutDragSource, DropTarget, ShimSupport {

    // Drop handler which handles dd drop events
    private DropHandler dropHandler;

    // The current drag mode, default is dragging is not supported
    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    // Are the iframes shimmed
    private boolean iframeShims = true;
    
    /**
     * A filter for dragging components.
     */
    private DragFilter dragFilter = DragFilter.ALL;

    /**
     * Target details for dropping on a absolute layout. Contains the absolute
     * and relative coordinates for the drop.
     */
    public class AbsoluteLayoutTargetDetails extends TargetDetailsImpl {

    	/**
    	 * Constructor
    	 * 
    	 * @param rawDropData
    	 * 		Drop data
    	 */
        protected AbsoluteLayoutTargetDetails(Map<String, Object> rawDropData) {
            super(rawDropData, DDAbsoluteLayout.this);
        }

        /**
         * The absolute left coordinate in pixels measured from the windows left
         * edge
         * 
         * @return The amount of pixels from the left edge
         */
        public int getAbsoluteLeft() {
            return Integer.valueOf(getData(Constants.DROP_DETAIL_ABSOLUTE_LEFT).toString());
        }

        /**
         * The absolute top coordinate in pixels measured from the windows top
         * edge
         * 
         * @return The amount of pixels from the top edge
         */
        public int getAbsoluteTop() {
            return Integer.valueOf(getData(Constants.DROP_DETAIL_ABSOLUTE_TOP).toString());
        }

        /**
         * The relative left coordinate in pixels measured from the containers
         * left edge
         * 
         * @return The amount of pixels from the left edge
         */
        public int getRelativeLeft() {
            return Integer.valueOf(getData(Constants.DROP_DETAIL_RELATIVE_LEFT).toString());
        }

        /**
         * The relative top coordinate in pixels measured from the containers
         * top edge
         * 
         * @return The amount of pixels from the top edge
         */
        public int getRelativeTop() {
            return Integer.valueOf(getData(Constants.DROP_DETAIL_RELATIVE_TOP).toString());
        }

        /**
         * The width of the dragged component measured in pixels
         * 
         * @return The width in pixels
         */
        public int getComponentHeight() {
            return Integer.valueOf(getData(Constants.DROP_DETAIL_COMPONENT_HEIGHT).toString());
        }

        /**
         * The height of the dragged component measured in pixels
         * 
         * @return The height in pixels
         */
        public int getComponentWidth() {
            return Integer.valueOf(getData(Constants.DROP_DETAIL_COMPONENT_WIDTH).toString());
        }

        /**
         * Some details about the mouse event
         * 
         * @return details about the actual event that caused the event details.
         *         Practically mouse move or mouse up.
         */
        public MouseEventDetails getMouseEvent() {
            return MouseEventDetails
                    .deSerialize((String) getData(Constants.DROP_DETAIL_MOUSE_EVENT));
        }
    }

    /**
     * Creates an AbsoluteLayout with full size.
     */
    public DDAbsoluteLayout() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);

        // Paint the drop handler criterions
        if (dropHandler != null) {
            dropHandler.getAcceptCriterion().paint(target);
        }

        // Adds the drag mode (the default is none)
        target.addAttribute(VHasDragMode.DRAGMODE_ATTRIBUTE, dragMode.ordinal());

        // Should shims be used
        target.addAttribute(IframeCoverUtility.SHIM_ATTRIBUTE, iframeShims);
        
        // Paint the dragfilter into the paint target
        new DragFilterPaintable(this).paint(target);
    }

    /**
     * Get the drophandler which handles component drops on the layout
     */
    public DropHandler getDropHandler() {
        return dropHandler;
    }

    /**
     * Sets the drop handler which handles component drops on the layout
     * 
     * @param dropHandler
     *            The drop handler to set
     */
    public void setDropHandler(DropHandler dropHandler) {
    	if(this.dropHandler != dropHandler){
    		 this.dropHandler = dropHandler;
    	     requestRepaint();
    	}
    }

    /**
     * {@inheritDoc}
     */
    public TargetDetails translateDropTargetDetails(
            Map<String, Object> clientVariables) {
        return new AbsoluteLayoutTargetDetails(clientVariables);
    }

    /**
     * {@inheritDoc}
     */
    public Transferable getTransferable(Map<String, Object> rawVariables) {
        return new LayoutBoundTransferable(this, rawVariables);
    }

    /**
     * {@inheritDoc}
     */
    public LayoutDragMode getDragMode() {
        return dragMode;
    }

    /**
     * {@inheritDoc}
     */
    public void setDragMode(LayoutDragMode mode) {
    	if(dragMode != mode){
    		dragMode = mode;
            requestRepaint();
    	}
    }

    /**
     * {@inheritDoc}
     */
    public void setShim(boolean shim) {
    	if(iframeShims != shim){
    		 iframeShims = shim;
    	     requestRepaint();
    	}
    }

    /**
     * {@inheritDoc}
     */
    public boolean isShimmed() {
        return iframeShims;
    }

    /**
     * {@inheritDoc}
     */
	public DragFilter getDragFilter() {
		return dragFilter;
	}

	/**
     * {@inheritDoc}
     */
	public void setDragFilter(DragFilter dragFilter) {
		if(this.dragFilter != dragFilter){
			this.dragFilter = dragFilter;
			requestRepaint();
		}
	}
}
