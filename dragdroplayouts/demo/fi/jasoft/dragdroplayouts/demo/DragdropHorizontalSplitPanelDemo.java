package fi.jasoft.dragdroplayouts.demo;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.And;
import com.vaadin.event.dd.acceptcriteria.Not;
import com.vaadin.event.dd.acceptcriteria.Or;
import com.vaadin.event.dd.acceptcriteria.SourceIs;
import com.vaadin.event.dd.acceptcriteria.SourceIsTarget;
import com.vaadin.terminal.gwt.client.ui.dd.HorizontalDropLocation;
import com.vaadin.terminal.gwt.client.ui.dd.VerticalDropLocation;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import fi.jasoft.dragdroplayouts.DDHorizontalSplitPanel;
import fi.jasoft.dragdroplayouts.DDVerticalLayout;
import fi.jasoft.dragdroplayouts.DDHorizontalSplitPanel.HorizontalSplitPanelTargetDetails;
import fi.jasoft.dragdroplayouts.DDVerticalLayout.VerticalLayoutTargetDetails;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.events.HorizontalLocationIs;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;
import fi.jasoft.dragdroplayouts.events.VerticalLocationIs;

@SuppressWarnings("serial")
public class DragdropHorizontalSplitPanelDemo extends CustomComponent {

    private int buttonCount = 1;

    public DragdropHorizontalSplitPanelDemo() {
        setCaption("Horizontal SplitPanel");
        setSizeFull();

        CssLayout root = new CssLayout();
        root.setSizeFull();
        setCompositionRoot(root);

        Label lbl = new Label(
                "To the left are some buttons, and to the right is a horizontal split panel. "
                        + "Try dragging the buttons on to the splitpanel. If a component already exists in the SplitPanel it is replaced with the dragged one.");
        root.addComponent(lbl);

        // Wrapping components in a horizontal layout
        HorizontalLayout inner = new HorizontalLayout();
        inner.setMargin(true);
        inner.setSizeFull();
        inner.setSpacing(true);
        root.addComponent(inner);

        // Add some buttons to a vertical layout with dragging enabled
        final DDVerticalLayout btns = new DDVerticalLayout();
        btns.setDragMode(LayoutDragMode.CLONE);
        btns.setSizeUndefined();
        btns.setSpacing(true);
        String caption = "Button ";
        btns.addComponent(new Button(caption + buttonCount++));
        btns.addComponent(new Button(caption + buttonCount++));
        btns.addComponent(new Button(caption + buttonCount++));
        btns.addComponent(new Button(caption + buttonCount++));
        btns.addComponent(new Button(caption + buttonCount++));
        inner.addComponent(btns);

        // Create a drag & drop horizontal split panel
        final DDHorizontalSplitPanel panel = new DDHorizontalSplitPanel();
        panel.setSizeFull();

        // Enable dragging
        panel.setDragMode(LayoutDragMode.CLONE);

        // Enable dropping
        panel.setDropHandler(new DropHandler() {

            public AcceptCriterion getAcceptCriterion() {
                // Only accept drags from the vertical button layout or self
                // and
                // do not allow dropping on the spacer
                return new And(
                        new Or(new SourceIs(btns), SourceIsTarget.get()),
                        new Not(HorizontalLocationIs.CENTER));
            }

            public void drop(DragAndDropEvent event) {
                // Casting to a layout bound transferable since we know it
                // comes from the vertical layout
                LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
                        .getTransferable();

                Component component = transferable.getComponent();

                ComponentContainer source = (ComponentContainer) transferable
                        .getSourceComponent();

                // Get the drop details
                HorizontalSplitPanelTargetDetails details = (HorizontalSplitPanelTargetDetails) event
                        .getTargetDetails();

                // Remove component from vertical layout
                source.removeComponent(component);

                // Add a new button to the vertical layout
                source.addComponent(new Button("Button " + buttonCount++));

                if (details.getDropLocation() == HorizontalDropLocation.LEFT) {
                    // Dropped in the left area
                    panel.setFirstComponent(component);

                } else if (details.getDropLocation() == HorizontalDropLocation.RIGHT) {
                    // Dropped in the right area
                    panel.setSecondComponent(component);
                }
            }
        });
        inner.addComponent(panel);
        inner.setExpandRatio(panel, 1);

        // Add drophandler to button layout
        btns.setDropHandler(new DropHandler() {
            public AcceptCriterion getAcceptCriterion() {
                return new And(new SourceIs(panel), new Not(
                        VerticalLocationIs.MIDDLE));
            }

            public void drop(DragAndDropEvent event) {
                LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
                        .getTransferable();
                VerticalLayoutTargetDetails details = (VerticalLayoutTargetDetails) event
                        .getTargetDetails();
                Component comp = transferable.getComponent();
                int newIndex = details.getOverIndex();
                btns.removeComponent(comp);
                if (details.getDropLocation() == VerticalDropLocation.BOTTOM) {
                    newIndex++;
                }

                if (btns.getComponentCount() == 0) {
                    btns.addComponent(comp);
                } else {
                    btns.addComponent(comp, newIndex);
                }
            }
        });
    }
}
