package com.vaadin.incubator.dashlayout.client.util.css;

import com.google.gwt.user.client.Element;
import com.vaadin.terminal.gwt.client.RenderInformation.Size;

public class CSSUtil {

    /**
     * Get current margin values from the DOM.
     */
    public static int[] collectMargin(Element elem) {
        int[] margin = { 0, 0, 0, 0 };
        margin[0] = parsePixel(getComputedStyleValue(elem, "marginTop"));
        margin[1] = parsePixel(getComputedStyleValue(elem, "marginRight"));
        margin[2] = parsePixel(getComputedStyleValue(elem, "marginBottom"));
        margin[3] = parsePixel(getComputedStyleValue(elem, "marginLeft"));
        return margin;
    }

    /**
     * Get current padding values from the DOM.
     */
    public static int[] collectPadding(Element elem) {
        int[] padding = { 0, 0, 0, 0 };
        padding[0] = parsePixel(getStyleValue(elem, "paddingTop"));
        padding[1] = parsePixel(getStyleValue(elem, "paddingRight"));
        padding[2] = parsePixel(getStyleValue(elem, "paddingBottom"));
        padding[3] = parsePixel(getStyleValue(elem, "paddingLeft"));
        return padding;
    }

    /**
     * Get current border values from the DOM.
     */
    public static int[] collectBorder(Element elem) {
        int[] border = { 0, 0, 0, 0 };
        border[0] = parsePixel(getComputedStyleValue(elem, "borderTopWidth"));
        border[1] = parsePixel(getComputedStyleValue(elem, "borderRightWidth"));
        border[2] = parsePixel(getComputedStyleValue(elem, "borderBottomWidth"));
        border[3] = parsePixel(getComputedStyleValue(elem, "borderLeftWidth"));
        return border;
    }

    /**
     * Calculates the outer size of an element, i.e. size including margins.
     * 
     * @param elem
     * @return
     */
    public static Size getOuterSize(Element elem) {
        int[] margin = collectMargin(elem);
        int width = elem.getOffsetWidth() + margin[1] + margin[3];
        int height = elem.getOffsetHeight() + margin[0] + margin[2];
        return new Size(width, height);
    }

    public static native String getComputedStyleValue(Element elem, String name)
    /*-{
        // This function does not handle opacity well
        // Name is expected in camelCase format

        // Store reference of this function (used in recursion)
        var self = @com.vaadin.incubator.dashlayout.client.util.css.CSSUtil::getComputedStyleValue(Lcom/google/gwt/user/client/Element;Ljava/lang/String;);

        // Perform normalization and checks here (e.g. if margin has value "auto", return 0)
        if(name.indexOf("border") > -1 && name.indexOf("Width") > -1) {
                var borderStyle = self(elem, name.substring(0,name.length-5)+"Style");
                if(borderStyle=="none") {
                        return "0px";
                }
        }

        var ret, style = elem.style, force = false;
        var defaultView = $wnd.document.defaultView || {};


        if ( !force && style && style[ name ] ){

                // Fetch value from inline style
                // -------------------------------------
                ret = style[ name ];

        } else if ( defaultView.getComputedStyle ) {

                // Standards based browsers
                // ------------------------------------

                // Convert name to dashed format
                name = name.replace( /([A-Z])/g, "-$1" ).toLowerCase();
                var computedStyle = defaultView.getComputedStyle(elem, null);

                if ( computedStyle ) {
                        ret = computedStyle.getPropertyValue(name);
                }

        } else if ( elem.currentStyle ) {

                // IE
                // --------------------------------------

                var camelCase = name.replace(/\-(\w)/g, function(all, letter){
                        return letter.toUpperCase();
                });

                ret = elem.currentStyle[ name ];

                // From the awesome hack by Dean Edwards
                // http://erik.eae.net/archives/2007/07/27/18.54.15/#comment-102291

                // If we're not dealing with a regular pixel number
                // but a number that has a weird ending, we need to convert it to pixels
                if ( !/^\d+(px)?$/i.test( ret ) && /^\d/.test( ret ) ) {
                        // Remember the original values
                        var left = style.left, rsLeft = elem.runtimeStyle.left;

                        // Put in the new values to get a computed value out
                        elem.runtimeStyle.left = elem.currentStyle.left;
                        style.left = ret || 0;
                        ret = style.pixelLeft + "px";

                        // Revert the changed values
                        style.left = left;
                        elem.runtimeStyle.left = rsLeft;
                }
        }

        // Normalize margin values
        if(name.indexOf("margin") > -1 && ret == "auto") {
                return "0px";
        }

        return ret;
    }-*/;

    /**
     * Normalize computed style values
     */
    public static String getStyleValue(final Element elem, final String name) {
        String ret = getComputedStyleValue(elem, name);
        if (name.equals("width") && ret.equals("auto")) {
            ret = elem.getClientWidth() + "px";
        } else if (name.equals("height") && ret.equals("auto")) {
            ret = elem.getClientHeight() + "px";
        }
        return ret;
    }

    /**
     * Takes a String value e.g. "12px" and parses that to int 12
     * 
     * @param String
     *            value with "px" ending
     * @return int the value from the string before "px", converted to int
     */
    public static int parsePixel(String value) {
        if (value == "" || value == null) {
            return 0;
        }
        Float ret;
        if (value.length() > 2) {
            ret = Float.parseFloat(value.substring(0, value.length() - 2));
        } else {
            ret = Float.parseFloat(value);
        }
        return (int) Math.ceil(ret);
    }
}
