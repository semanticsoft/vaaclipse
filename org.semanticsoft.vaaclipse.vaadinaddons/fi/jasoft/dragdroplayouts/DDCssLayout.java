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
import com.vaadin.ui.CssLayout;

import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.VDDCssLayout;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;
import fi.jasoft.dragdroplayouts.interfaces.LayoutDragSource;
import fi.jasoft.dragdroplayouts.interfaces.ShimSupport;

/**
 * CssLayout with drag and drop support
 * 
 * @author John Ahlroos / www.jasoft.fi
 * @since 0.7.0
 *
 */
@SuppressWarnings("serial")
@ClientWidget(VDDCssLayout.class)
public class DDCssLayout extends CssLayout implements
LayoutDragSource, DropTarget, ShimSupport{
	
	/**
     * A filter for dragging components.
     */
    private DragFilter dragFilter = DragFilter.ALL;

    // The current drag mode, default is dragging is not supported
    private LayoutDragMode dragMode = LayoutDragMode.NONE;
    
    // Are the iframes shimmed
    private boolean iframeShims = true;
    
    // Drop handler which handles dd drop events
    private DropHandler dropHandler;
    
    /**
     * Target details for dropping on a absolute layout. 
     */
    public class CssLayoutTargetDetails extends TargetDetailsImpl{

    	private int index = -1;
    	
    	private Component over;

    	
    	/**
    	 * Constructor
    	 * 
    	 * @param rawDropData
    	 * 		The drop data
    	 */
		protected CssLayoutTargetDetails(Map<String, Object> rawDropData) {
			 super(rawDropData, DDCssLayout.this);
			
			// Get over which component (if any) the drop was made and the
            // index of it
            if (getData(Constants.DROP_DETAIL_TO) != null) {
                index = Integer.valueOf(getData(Constants.DROP_DETAIL_TO).toString());
                if(index >= 0 && index < components.size()) {
                    over = components.get(index);
                }
            } else {
            	index = components.size();
            }

            // Was the drop over no specific cell
            if (over == null) {
                over = DDCssLayout.this;
            }
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
        
        /**
         * Get the horizontal position of the dropped component within the
         * underlying cell.
         * 
         * @return The drop location
         */
        public HorizontalDropLocation getHorizontalDropLocation() {
        	 return HorizontalDropLocation.valueOf((String) getData(Constants.DROP_DETAIL_HORIZONTAL_DROP_LOCATION));
        }
           
        
        /**
         * Get the horizontal position of the dropped component within the
         * underlying cell.
         * 
         * @return The drop location
         */
        public VerticalDropLocation getVerticalDropLocation() {
            return VerticalDropLocation.valueOf((String) getData(Constants.DROP_DETAIL_VERTICAL_DROP_LOCATION));
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
         * The component over which the drop was made.
         * 
         * @return Null if the drop was not over a component, else the component
         */
        public Component getOverComponent() {
            return over;
        }
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
	 * gets the drop handler which handles component drops on the layout
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
		return new CssLayoutTargetDetails(clientVariables);
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
	public DragFilter getDragFilter() {
		return this.dragFilter;
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
	
	/*
	 * (non-Javadoc)
	 * @see com.vaadin.ui.CssLayout#paintContent(com.vaadin.terminal.PaintTarget)
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
}
