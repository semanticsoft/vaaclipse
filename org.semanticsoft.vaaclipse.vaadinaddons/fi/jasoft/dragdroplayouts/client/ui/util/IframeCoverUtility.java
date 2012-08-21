package fi.jasoft.dragdroplayouts.client.ui.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class IframeCoverUtility {

	private static Map<Element, Element> iframeCoverMap = new HashMap<Element, Element>();

	public static final String SHIM_ATTRIBUTE = "shims";
	
	/**
     * Adds an iframe cover over an Embedded component
     * 
     * @param iframe
     * 		The iframe element
     * @return
     * 		The element which covers the iframe
     */
    private static Element addIframeCover(Element iframe) {
        if (iframeCoverMap.containsKey(iframe)) {
            return iframeCoverMap.get(iframe);
        }

        // Get dimensions
        String iframeWidth = iframe.getAttribute("width");
        String iframeHeight = iframe.getAttribute("height");

        Style iframeStyle = iframe.getStyle();

        if (!iframeWidth.equals("") && !iframeHeight.equals("")) {
            iframeStyle.setPosition(Position.ABSOLUTE);
            iframeStyle.setTop(0, Unit.PX);
            iframeStyle.setLeft(0, Unit.PX);
        }

        // Create the cover element
        Element coverContainer = DOM.createDiv();
        DOM.setStyleAttribute(coverContainer, "width", iframeWidth);
        DOM.setStyleAttribute(coverContainer, "height", iframeHeight);

        coverContainer.setClassName("v-dragdrop-iframe-container");
        coverContainer.getStyle().setPosition(Position.RELATIVE);
        iframe.getParentElement().appendChild(coverContainer);

        // Move iframe to cover container
        iframe.getParentElement().replaceChild(coverContainer, iframe);
        coverContainer.appendChild(iframe);

        // Style the cover
        Element cover = DOM.createDiv();
        cover.setClassName("v-dragdrop-iframe-cover");
        Style coverStyle = cover.getStyle();
        coverStyle.setPosition(Position.ABSOLUTE);
        coverStyle.setWidth(100, Unit.PCT);
        coverStyle.setHeight(100, Unit.PCT);
        coverStyle.setTop(0, Unit.PX);
        coverStyle.setLeft(0, Unit.PX);

        coverContainer.appendChild(cover);

        iframeCoverMap.put(iframe, coverContainer);

        return coverContainer;
    }

    /**
     * Removes a iframe cover
     * 
     * @param iframe
     * 		The iframe element which has been covered
     */
    private static void removeIframeCover(Element iframe) {
        Element coverContainer = iframeCoverMap.get(iframe);
        if (coverContainer != null) {
            Element parent = coverContainer.getParentElement().cast();
            parent.replaceChild(iframe, coverContainer);
            iframe.getStyle().setPosition(null);
            iframeCoverMap.remove(iframe);
        }
    }

    /**
     * Adds iframe covers for all child iframe elements
     * 
     * @param rootElement
     * 		The parent element
     * @return
     * 		A set of elements with the iframe covers
     */
    private static Set<Element> addIframeCovers(Element rootElement) {
        Set<Element> coveredIframes = new HashSet<Element>();
        NodeList<com.google.gwt.dom.client.Element> iframes = rootElement
                .getElementsByTagName("iframe");
        for (int i = 0; i < iframes.getLength(); i++) {
            Element iframe = (Element) iframes.getItem(i);
            addIframeCover(iframe);
            coveredIframes.add(iframe);
        }
        return coveredIframes;
    }

    /**
     * Removes iframe covers from a set of iframes
     * 
     * @param iframes
     * 		The iframes to remove the covers from
     */
    private static void removeIframeCovers(Set<Element> iframes) {
        if (iframes != null) {
            for (Element iframe : iframes) {
                removeIframeCover(iframe);
            }
        }
    }
    
    // The covered ifream covers
    private Set<Element> coveredIframes = new HashSet<Element>();
    
    /**
     * Enable IFrame covers for a element
     * 
     * @param enabled
     * 		Enable/Disable Iframe covers
     * @param root
     * 		The root element where to put the IFrame covers
     */
    public void setIframeCoversEnabled(boolean enabled, Element root) {
        if (enabled) {
            coveredIframes = addIframeCovers(root);
        } else if (coveredIframes != null) {
            removeIframeCovers(coveredIframes);
            coveredIframes = null;
        }
    }
}
