package fi.jasoft.dragdroplayouts.interfaces;

/**
 * Adds Iframe shimming support for layout components. This means that when shimming is
 * turned on a element in placed on top of the component and acts as an delegate for the 
 * component. No clicks or other mouse events go through the shim. This allows dragging
 * an iframe based component. When shimming is turned off all mouse event passes into the
 * iframe and you cannot drag the component.
 * 
 * @author John Ahlroos
 * @since 0.6.3
 */
public interface ShimSupport {

	 /**
     * False to disable the iframe shim used to enable dragging iframe based
     * components (defaults to true).
     * 
     * @return Are the iframes shimmed
     */
    void setShim(boolean shim);

    /**
     * Are shims used over iframes so dragging is possible
     * 
     * @return
     */
    boolean isShimmed();
	
}
