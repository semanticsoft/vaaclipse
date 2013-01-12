package fi.jasoft.dragdroplayouts.demo;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.And;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import fi.jasoft.dragdroplayouts.DDGridLayout;
import fi.jasoft.dragdroplayouts.DDGridLayout.GridLayoutTargetDetails;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.events.HorizontalLocationIs;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;
import fi.jasoft.dragdroplayouts.events.VerticalLocationIs;

@SuppressWarnings("serial")
public class DragdropGridLayoutDemo extends CustomComponent {
	
	private static final int ROWS = 4;
	private static final int COLUMNS = 4;
	
    public DragdropGridLayoutDemo() {
        setCaption("Grid layout");
        setSizeFull();

        VerticalLayout outer = new VerticalLayout();
        outer.setSizeFull();
        Label lbl = new Label(
                "This is a grid layout with 16 cells, try dragging the buttons into an empty cell");
        outer.addComponent(lbl);
        setCompositionRoot(outer);

        final DDGridLayout layout = new DDGridLayout(COLUMNS, ROWS);
        layout.setWidth("400px");
        layout.setHeight("100%");
        layout.setComponentHorizontalDropRatio(0);
        layout.setComponentVerticalDropRatio(0);

        outer.addComponent(layout);
        outer.setExpandRatio(layout, 1);

        layout.setDragMode(LayoutDragMode.CLONE);

        layout.setDropHandler(new DropHandler() {

            public AcceptCriterion getAcceptCriterion() {
                return new And(VerticalLocationIs.MIDDLE,
                        HorizontalLocationIs.CENTER);
            }

            public void drop(DragAndDropEvent event) {
                GridLayoutTargetDetails details = (GridLayoutTargetDetails) event
                        .getTargetDetails();
                LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
                        .getTransferable();

                int column = details.getOverColumn();
                int row = details.getOverRow();

                Component c = transferable.getComponent();

                if (layout.getComponent(column, row) == null) {
                    layout.removeComponent(c);
                    layout.addComponent(c, column, row);
                    layout.setComponentAlignment(c, Alignment.MIDDLE_CENTER);
                }
            }
        });

        
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (row == 0 || row == ROWS-1 || col == 0 || col == COLUMNS-1) {
                    Button btn = new Button("Button");
                    layout.addComponent(btn, col, row);
                    layout.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);
                }
            }
        }
    }
}
