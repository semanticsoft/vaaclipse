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

import java.util.Iterator;
import java.util.Map;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.TargetDetailsImpl;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.terminal.gwt.client.ui.dd.VerticalDropLocation;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.Component;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.VDDAccordion;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;
import fi.jasoft.dragdroplayouts.interfaces.LayoutDragSource;
import fi.jasoft.dragdroplayouts.interfaces.ShimSupport;

@SuppressWarnings("serial")
@ClientWidget(VDDAccordion.class)
public class DDAccordion extends Accordion implements LayoutDragSource,
        DropTarget, ShimSupport {

    /**
     * Specifies if dragging components is allowed and if so how it should be
     * visualized
     */
    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    /**
     * The drop handler which handles dropped components in the layout.
     */
    private DropHandler dropHandler;

    // The ratio how drop c
    private float verticalDropRatio = VDDAccordion.DEFAULT_VERTICAL_RATIO;

    // Are the iframes shimmed
    private boolean iframeShims = true;
    
    /**
     * A filter for dragging components.
     */
    private DragFilter dragFilter = DragFilter.ALL;

    public class AccordionTargetDetails extends TargetDetailsImpl {

        private Component over;

        private int index = -1;

        protected AccordionTargetDetails(Map<String, Object> rawDropData) {
            super(rawDropData, DDAccordion.this);

            // Get over which component (if any) the drop was made and the
            // index of it
            Object to = rawDropData.get("to");
            if (to != null) {
                index = Integer.valueOf(to.toString());

                if (index < getComponentCount()) {
                    Iterator<Component> iter = getComponentIterator();
                    int counter = 0;
                    while (iter.hasNext()) {
                        over = iter.next();
                        if (counter == index) {
                            break;
                        }
                        counter++;
                    }
                } else {
                    over = DDAccordion.this;
                }
            } else {
                over = DDAccordion.this;
            }
        }

        /**
         * The component over which the drop was made.
         * 
         * @return Null if the drop was not over a component, else the component
         */
        public Component getOverComponent() {
            return over;
        }

        /**
         * The index over which the drop was made. If the drop was not made over
         * any component then it returns -1.
         * 
         * @return The index of the component or -1 if over no component.
         */
        public int getOverIndex() {
            return index;
        }

        /**
         * Some details about the mouse event
         * 
         * @return details about the actual event that caused the event details.
         *         Practically mouse move or mouse up.
         */
        public MouseEventDetails getMouseEvent() {
            return MouseEventDetails
                    .deSerialize((String) getData("mouseEvent"));
        }

        /**
         * Get the horizontal position of the dropped component within the
         * underlying cell.
         * 
         * @return The drop location
         */
        public VerticalDropLocation getDropLocation() {
            if (getData("vdetail") != null) {
                return VerticalDropLocation
                        .valueOf((String) getData("vdetail"));
            } else {
                return null;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public Transferable getTransferable(Map<String, Object> rawVariables) {
        if (rawVariables.get("index") != null) {
            int index = Integer.parseInt(rawVariables.get("index").toString());
            Iterator<Component> iter = getComponentIterator();
            int counter = 0;
            Component c = null;
            while (iter.hasNext()) {
                c = iter.next();
                if (counter == index) {
                    break;
                }
                counter++;
            }

            rawVariables.put("component", c);
        } else if (rawVariables.get("component") == null) {
            rawVariables.put("component", DDAccordion.this);
        }

        return new LayoutBoundTransferable(this, rawVariables);
    }

    /**
     * Sets the current handler which handles dropped components on the layout.
     * By setting a drop handler dropping components on the layout is enabled.
     * By setting the dropHandler to null dropping is disabled.
     * 
     * @param dropHandler
     *            The drop handler to handle drop events or null to disable
     *            dropping
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
    public DropHandler getDropHandler() {
        return dropHandler;
    }

    /**
     * {@inheritDoc}
     */
    public TargetDetails translateDropTargetDetails(
            Map<String, Object> clientVariables) {
        return new AccordionTargetDetails(clientVariables);
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
        if (dragMode != mode) {
            dragMode = mode;
            requestRepaint();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);

        // Add drop handler
        if (dropHandler != null) {
            dropHandler.getAcceptCriterion().paint(target);
        }

        // Adds the drag mode (the default is none)
        target.addAttribute("dragMode", dragMode.ordinal());

        // Set the drop ratio
        target.addAttribute("vDropRatio", verticalDropRatio);

        // Should shims be used
        target.addAttribute(IframeCoverUtility.SHIM_ATTRIBUTE, iframeShims);
        
        // Paint the dragfilter into the paint target
        new DragFilterPaintable(this).paint(target);
    }

    /**
     * Sets the ratio which determines how a tab is divided into drop zones. The
     * ratio is measured from the left and right borders. For example, setting
     * the ratio to 0.3 will divide the drop zone in three equal parts
     * (left,middle,right). Setting the ratio to 0.5 will disable dropping in
     * the middle and setting it to 0 will disable dropping at the sides.
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
