package fi.jasoft.dragdroplayouts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;

import fi.jasoft.dragdroplayouts.client.ui.VDragFilter;
import fi.jasoft.dragdroplayouts.interfaces.LayoutDragSource;

/**
 * A DragFilter wrapper to add common painting operations. Only used internally.
 * 
 * @author John Ahlroos
 * @since 0.6.3
 */
class DragFilterPaintable {
	
	private final LayoutDragSource source;
	
	/**
	 * Constructor
	 * 
	 * @param source
	 * 		The drag source
	 * 
	 */
	public DragFilterPaintable(LayoutDragSource source){
		this.source = source;
	}
	
	/**
	 * Paint the drag filter into the target
	 * 
	 * @param target
	 * 		The paint target
	 * @throws PaintException
	 * 		Thrown is painting failed
	 */
	public void paint(PaintTarget target) throws PaintException{
		if(source instanceof TabSheet){
			paintWithIndexes(target);
		} else if(source instanceof Layout){
			paintWithPids(target);
		} else {
			throw new UnsupportedOperationException("Cannot paint filter for paint target");
		}
	}
	
	private void paintWithPids(PaintTarget target) throws PaintException{
		Layout layout = (Layout) source;
		if(source.getDragFilter() != null){
        	// Get components with dragging disabled
        	Map<Component, Boolean> dragmap = new HashMap<Component, Boolean>();
        	Iterator<Component> iter = layout.getComponentIterator();
        	while(iter.hasNext()){
        		Component c = iter.next();
        		boolean draggable = source.getDragFilter().isDraggable(c);
        		dragmap.put(c, draggable);
        	}
        	target.addAttribute(VDragFilter.DRAGMAP_ATTRIBUTE, dragmap);
        }
	}
	
	private void paintWithIndexes(PaintTarget target) throws PaintException{
		TabSheet tabsheet = (TabSheet) source;
		if (source.getDragFilter() != null) {
			// Get components with dragging disabled
			Map<Integer, Boolean> dragmap = new HashMap<Integer, Boolean>();
			Iterator<Component> iter = tabsheet.getComponentIterator();
			while (iter.hasNext()) {
				Component c = iter.next();
				if (tabsheet.getTab(c).isVisible()) {
					boolean draggable = source.getDragFilter().isDraggable(c);
					int index = tabsheet.getTabPosition(tabsheet.getTab(c));
					dragmap.put(index, draggable);
				}
			}
			target.addAttribute(VDragFilter.DRAGMAP_ATTRIBUTE, dragmap);
		}
	}
}
