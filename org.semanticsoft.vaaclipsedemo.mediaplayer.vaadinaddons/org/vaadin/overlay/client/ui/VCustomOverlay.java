package org.vaadin.overlay.client.ui;

import java.util.Set;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Container;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.RenderSpace;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;
import com.vaadin.terminal.gwt.client.ui.AlignmentInfo;

/**
 * Client side widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VCustomOverlay extends SimplePanel implements Paintable, Container {


    public static final int ORIGO_TOP_LEFT = 0;
    public static final int ORIGO_TOP_RIGHT = 1;
    public static final int ORIGO_BOTTOM_LEFT = 2;
    public static final int ORIGO_BOTTOM_RIGHT = 3;

    public static final String CLASSNAME = "v-customoverlay";

    protected static final int Z_INDEX = 19900;

    private int x;

    private int y;

    private Element refCompEl;

    private RenderSpace renderSpace = new RenderSpace(0, 0);

    private AlignmentInfo align = new AlignmentInfo(AlignmentInfo.LEFT,
            AlignmentInfo.TOP);

    private AlignmentInfo overlayAlign = new AlignmentInfo(AlignmentInfo.LEFT,
            AlignmentInfo.TOP);

    private PopupPanel overlay;
    private int refY;
    private int refX;

    /**
     * The constructor should first call super() to initialize the component and
     * then handle any initialization relevant to Vaadin.
     */
    public VCustomOverlay() {
        super();
        setWidget(new HTML()); // Seems that we need this one
        overlay = new PopupPanel();
        overlay.addStyleName(CLASSNAME);
        overlay.setAutoHideEnabled(false);
        overlay.setAnimationEnabled(false);
        overlay.setModal(false);

        Event.addNativePreviewHandler(new NativePreviewHandler() {

            public void onPreviewNativeEvent(NativePreviewEvent event) {
                int typeInt = event.getTypeInt();
                // We're only listening for these
                if (typeInt == Event.ONSCROLL) {
                    VCustomOverlay.this.updateOverlayPosition();
                }
            }
        });
    }

    /**
     * Called whenever an update is received from the server
     */
    public void updateFromUIDL(UIDL uidl, final ApplicationConnection client) {

        // Custom visibility handling
        if (uidl.getBooleanAttribute("invisible") && overlay != null) {
            overlay.hide();
        }
        if (client.updateComponent(this, uidl, false)) {
            return;
        }

        // Find the reference component
        if (uidl.hasAttribute("comp")) {
            Paintable refComp = client.getPaintable(uidl
                    .getStringAttribute("comp"));
            if (refComp != null) {
                Widget w = (Widget) refComp;
                if (w instanceof VCustomOverlay) {
                    w = ((VCustomOverlay) w).getOverlayWidget();
                }
                refCompEl = w.getElement();
            }
        }


        // Render the component
        final UIDL child = uidl.getChildUIDL(0);
        if (child != null) {
            Paintable p = client.getPaintable(child);
            Widget w = overlay.getWidget();
            if (p != w && w != null) {
                client.unregisterPaintable((Paintable) w);
                overlay.clear();
            }
            overlay.setWidget((Widget) p);
            overlay.show();
            p.updateFromUIDL(child, client);
        } else {
            overlay.hide();
        }

        Widget wgt = getOverlayWidget();
        int w = Util.getRequiredWidth(wgt);
        int h = Util.getRequiredHeight(wgt);
        ApplicationConnection.getConsole().log("PAINT: w=" + w + "h=" + h);

        // Position the component
        x = uidl.getIntAttribute("x");
        y = uidl.getIntAttribute("y");
        align = new AlignmentInfo(uidl.getIntAttribute("align"));
        overlayAlign = new AlignmentInfo(uidl.getIntAttribute("overlayAlign"));

        deferredUpdatePosition();
    }

    private Widget getOverlayWidget() {
        return overlay.getWidget();
    }

    protected void updateOverlayPosition() {
        if (refCompEl != null) {

            // Calculate the position based on reference component size and the
            // align point.
            refY = refCompEl.getAbsoluteTop();
            refX = refCompEl.getAbsoluteLeft();

            if (align.isBottom()) {
                refY += refCompEl.getOffsetHeight();
            } else if (align.isVerticalCenter()) {
                refY += refCompEl.getOffsetHeight() / 2;
            }
            if (align.isRight()) {
                refX += refCompEl.getOffsetWidth();
            } else if (align.isHorizontalCenter()) {
                refX += refCompEl.getOffsetWidth() / 2;
            }
            // Show popup
            overlay.setPopupPositionAndShow(new PositionCallback() {

                public void setPosition(int offsetWidth, int offsetHeight) {
                    // Calculate the position based on over component size and
                    // the alignment point.
                    Widget wgt = getOverlayWidget();
                    int w = Util.getRequiredWidth(wgt);
                    int h = Util.getRequiredHeight(wgt);

                    ApplicationConnection.getConsole().log(
                            "POSITION: w=" + w + "h=" + h);

                    int top = refY + y;
                    int left = refX + x;
                    if (overlayAlign.isBottom()) {
                        top -= h;
                    } else if (overlayAlign.isVerticalCenter()) {
                        top -= h / 2;
                    }

                    if (overlayAlign.isRight()) {
                        left -= w;
                    } else if (overlayAlign.isHorizontalCenter()) {
                        left -= w / 2;
                    }
                    ApplicationConnection.getConsole().log(
                            "top=" + top + "left=" + left);

                    overlay.setPopupPosition(left, top);
                }
            });
        }
    }

    public RenderSpace getAllocatedSpace(Widget child) {
        return renderSpace;
    }

    public boolean hasChildComponent(Widget component) {
        if (overlay.getWidget() == component) {
            return true;
        } else {
            return false;
        }
    }

    public void replaceChildComponent(Widget oldComponent, Widget newComponent) {
        if (hasChildComponent(oldComponent)) {
            overlay.clear();
            overlay.setWidget(newComponent);
            deferredUpdatePosition();
        } else {
            throw new IllegalStateException();
        }
    }

    private void deferredUpdatePosition() {
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                updateOverlayPosition();
            }
        });
    }

    public boolean requestLayout(Set<Paintable> children) {
        deferredUpdatePosition();
        return true;
    }

    public void updateCaption(Paintable component, UIDL uidl) {
        // No captions for overlays
    }

    @Override
    protected void onDetach() {
        if (overlay != null) {
            overlay.hide();
        }
        super.onDetach();
    }

}
