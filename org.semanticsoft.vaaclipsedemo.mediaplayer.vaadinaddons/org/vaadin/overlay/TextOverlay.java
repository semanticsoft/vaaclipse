package org.vaadin.overlay;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

/**
 * Server-side class for creating text overlays for other components.
 *
 * The overlays are strings rendered on the top of the specified component and
 * they can be aligned using the
 * {@link #setComponentAnchor(com.vaadin.ui.Alignment)},
 * {@link #setXOffset(int)} and {@link #setYOffset(int)} functions.
 *
 * @author Sami Ekblad
 *
 */

public class TextOverlay extends CustomClickableOverlay {
    private static final long serialVersionUID = -4604714578885982118L;

    public static final int CONTENT_DEFAULT = Label.CONTENT_DEFAULT;
    public static final int CONTENT_PREFORMATTED = Label.CONTENT_PREFORMATTED;
    public static final int CONTENT_RAW = Label.CONTENT_RAW;
    public static final int CONTENT_TEXT = Label.CONTENT_TEXT;
    public static final int CONTENT_XHTML = Label.CONTENT_XHTML;
    public static final int CONTENT_XML = Label.CONTENT_XML;

    private String text;
    private Label label;

    /**
     * Create new text overlay for a component.
     *
     * @param referenceComponent
     *            Align the overlay to this component.
     * @param text
     *            Text content of the overlay
     * @see #setComponentAnchor(com.vaadin.ui.Alignment)
     * @see #setXOffset(int)
     * @see #setYOffset(int)
     */
    public TextOverlay(Component referenceComponent, String text) {
        setComponent(referenceComponent);
        setText(text);
    }

    /**
     * Create new text empty overlay for a component.
     *
     * The text must be added later using the {@link #setText(String)}
     *
     * @param referenceComponent
     *            Align the overlay to this component.
     *
     * @see #setText(String)
     * @see #setComponentAnchor(com.vaadin.ui.Alignment)
     * @see #setXOffset(int)
     * @see #setYOffset(int)
     */
    public TextOverlay(Component referenceComponent) {
        setComponent(referenceComponent);
    }

    /**
     * Create new text empty overlay.
     *
     * The text must be added later using the {@link #setText(String)} and the
     * reference component using {@link #setComponent(Component)}.
     *
     * @param referenceComponent
     *            Align the overlay to this component.
     *
     * @see #setText(String)
     * @see #setComponentAnchor(com.vaadin.ui.Alignment)
     * @see #setXOffset(int)
     * @see #setYOffset(int)
     */
    public TextOverlay() {
    }

    /**
     * Set overlay text.
     *
     * Creates a {@link Label} for the text.
     *
     * @param text
     * @see #setContentMode(int)
     */
    public void setText(final String text) {
        this.text = text;
        if (label == null) {
            label = new Label();
            label.setSizeUndefined();
            setOverlay(label);
        }
        label.setValue(this.text);
    }

    /**
     * Set the content mode.
     *
     * This is a shortcut for ((Label)getOverlay()).setContentMode(int).
     *
     * @param contentMode
     *            One of the CONTENT_MODE_* constants. Same as
     *            Label.CONTENT_MODE_* constants.
     */
    public void setContentMode(int contentMode) {
        label.setContentMode(contentMode);
    }

    /**
     * Get the content mode.
     *
     * This is a shortcut for ((Label)getOverlay()).getContentMode().
     *
     * @return One of the CONTENT_MODE_* constants. Same as Label.CONTENT_MODE_*
     *         constants.
     */
    public int getContentMode() {
        if (label != null) {
            return label.getContentMode();
        }
        return CONTENT_DEFAULT;
    }

}
