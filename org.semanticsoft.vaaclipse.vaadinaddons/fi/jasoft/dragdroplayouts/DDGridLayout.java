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
import com.vaadin.terminal.gwt.client.ui.dd.HorizontalDropLocation;
import com.vaadin.terminal.gwt.client.ui.dd.VerticalDropLocation;
import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;

import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.VDDGridLayout;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;
import fi.jasoft.dragdroplayouts.interfaces.LayoutDragSource;
import fi.jasoft.dragdroplayouts.interfaces.ShimSupport;

/**
 * Grid layout with drag and drop support
 * 
 * @author John Ahlroos / www.jasoft.fi
 */
@SuppressWarnings("serial")
@ClientWidget(VDDGridLayout.class)
public class DDGridLayout extends GridLayout implements LayoutDragSource,
        DropTarget, ShimSupport {
	
	/**
	 * Target details for a drop event
	 */
    public class GridLayoutTargetDetails extends TargetDetailsImpl {

        private Component over;

        private int row = -1;

        private int column = -1;

        protected GridLayoutTargetDetails(Map<String, Object> rawDropData) {
            super(rawDropData, DDGridLayout.this);

            if (getData(Constants.DROP_DETAIL_ROW) != null) {
                row = Integer.valueOf(getData(Constants.DROP_DETAIL_ROW).toString());
            } else {
            	row = -1;
            }

            if (getData(Constants.DROP_DETAIL_COLUMN) != null) {
                column = Integer.valueOf(getData(Constants.DROP_DETAIL_COLUMN).toString());
            } else {
            	column = -1;
            }

            if (row != -1 && column != -1) {
                over = getComponent(column, row);
            }

            if (over == null) {
                over = DDGridLayout.this;
            }
        }

        /**
         * Returns the component over which the dragged component was dropped.
         * Returns NULL if no component was under the dragged component
         * 
         * @return
         */
        public Component getOverComponent() {
            return over;
        }

        /**
         * Over which row was the component dropped
         * 
         * @return The index of the row over which the component was dropped
         */
        public int getOverRow() {
            return row;
        }

        /**
         * Over which column was the component dropped
         * 
         * @return The index of the column over which the component was dropped
         */
        public int getOverColumn() {
            return column;
        }

        /**
         * Returns the horizontal location within the cell the component was
         * dropped
         * 
         * @return
         */
        public HorizontalDropLocation getHorizontalDropLocation() {
            return HorizontalDropLocation
                    .valueOf(getData(Constants.DROP_DETAIL_HORIZONTAL_DROP_LOCATION).toString());
        }

        /**
         * Returns the vertical location within the cell the component was
         * dropped
         * 
         * @return
         */
        public VerticalDropLocation getVerticalDropLocation() {
            return VerticalDropLocation
            		.valueOf(getData(Constants.DROP_DETAIL_VERTICAL_DROP_LOCATION).toString());
        }

        /**
         * Was the dropped component dropped in an empty cell
         * 
         * @return
         */
        public boolean overEmptyCell() {
            return Boolean.valueOf(getData(Constants.DROP_DETAIL_EMPTY_CELL).toString());
        }

        /**
         * Some details about the mouse event
         * 
         * @return details about the actual event that caused the event details.
         *         Practically mouse move or mouse up.
         */
        public MouseEventDetails getMouseEvent() {
            return MouseEventDetails
                    .deSerialize(getData(Constants.DROP_DETAIL_MOUSE_EVENT).toString());
        }
    }

    /**
     * Contains the transferable details when dragging from a GridLayout.
     */
    public class GridLayoutTransferable extends LayoutBoundTransferable {

        /**
         * Constructor
         * 
         * @param sourceComponent
         *            The source layout from where the component was dragged
         * @param rawVariables
         *            The drag details
         */
        public GridLayoutTransferable(Component sourceComponent,
                Map<String, Object> rawVariables) {
            super(sourceComponent, rawVariables);
        }

        /**
         * The row from where the component was dragged
         * 
         * @return The row index
         */
        public int getSourceRow() {
            return Integer.valueOf(getData(Constants.DROP_DETAIL_ROW).toString());
        }

        /**
         * The column from where the component was dragged
         * 
         * @return The column index
         */
        public int getSourceColumn() {
            return Integer.valueOf(getData(Constants.DROP_DETAIL_COLUMN).toString());
        }
    }

    private DropHandler dropHandler;

    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    private float horizontalDropRatio = VDDGridLayout.DEFAULT_HORIZONTAL_RATIO;

    private float verticalDropRatio = VDDGridLayout.DEFAULT_VERTICAL_RATIO;

    // Are the iframes shimmed
    private boolean iframeShims = true;
    
    /**
     * A filter for dragging components.
     */
    private DragFilter dragFilter = DragFilter.ALL;

    /**
     * Constructor for grid of given size (number of cells). Note that grid's
     * final size depends on the items that are added into the grid. Grid grows
     * if you add components outside the grid's area.
     * 
     * @param columns
     *            Number of columns in the grid.
     * @param rows
     *            Number of rows in the grid.
     */
    public DDGridLayout(int columns, int rows) {
        super(columns, rows);
    }

    /**
     * Constructs an empty grid layout that is extended as needed.
     */
    public DDGridLayout() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);

        if (dropHandler != null) {
            dropHandler.getAcceptCriterion().paint(target);
        }

        // Drop ratios
        target.addAttribute(Constants.ATTRIBUTE_HORIZONTAL_DROP_RATIO, horizontalDropRatio);
        target.addAttribute(Constants.ATTRIBUTE_VERTICAL_DROP_RATIO, verticalDropRatio);

        // Drag mode
        target.addAttribute(VHasDragMode.DRAGMODE_ATTRIBUTE, dragMode.ordinal());

        // Shims
        target.addAttribute(IframeCoverUtility.SHIM_ATTRIBUTE, iframeShims);
        
        // Paint the dragfilter into the paint target
        new DragFilterPaintable(this).paint(target);
    }

    /**
     * {@inheritDoc}
     */
    public DropHandler getDropHandler() {
        return dropHandler;
    }

    /**
     * {@inheritDoc}
     */
    public void setDropHandler(DropHandler dropHandler) {
    	if(dropHandler != this.dropHandler){
    		 this.dropHandler = dropHandler;
    	     requestRepaint();
    	}
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
    public TargetDetails translateDropTargetDetails(
            Map<String, Object> clientVariables) {
        return new GridLayoutTargetDetails(clientVariables);
    }

    /**
     * {@inheritDoc}
     */
    public Transferable getTransferable(Map<String, Object> rawVariables) {
        return new GridLayoutTransferable(this, rawVariables);
    }

    /**
     * Sets the ratio which determines how a cell is divided into drop zones.
     * The ratio is measured from the left and right borders. For example,
     * setting the ratio to 0.3 will divide the drop zone in three equal parts
     * (left,middle,right). Setting the ratio to 0.5 will disable dropping in
     * the middle and setting it to 0 will disable dropping at the sides.
     * 
     * @param ratio
     *            A ratio between 0 and 0.5. Default is 0.2
     */
    public void setComponentHorizontalDropRatio(float ratio) {
    	if(ratio != horizontalDropRatio){
    		 if (ratio >= 0 && ratio <= 0.5) {
	            horizontalDropRatio = ratio;
	            requestRepaint();
	        } else {
	            throw new IllegalArgumentException(
	                    "Ratio must be between 0 and 0.5");
	        }
    	}
    }

    /**
     * Sets the ratio which determines how a cell is divided into drop zones.
     * The ratio is measured from the top and bottom borders. For example,
     * setting the ratio to 0.3 will divide the drop zone in three equal parts
     * (top,center,bottom). Setting the ratio to 0.5 will disable dropping in
     * the center and setting it to 0 will disable dropping at the sides.
     * 
     * @param ratio
     *            A ratio between 0 and 0.5. Default is 0.2
     */
    public void setComponentVerticalDropRatio(float ratio) {
    	if(ratio != verticalDropRatio){
    		if (ratio >= 0 && ratio <= 0.5) {
                verticalDropRatio = ratio;
                requestRepaint();
            } else {
                throw new IllegalArgumentException(
                        "Ratio must be between 0 and 0.5");
            }	
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
