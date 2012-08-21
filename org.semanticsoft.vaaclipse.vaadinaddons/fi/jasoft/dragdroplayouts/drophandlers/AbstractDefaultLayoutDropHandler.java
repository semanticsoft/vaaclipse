package fi.jasoft.dragdroplayouts.drophandlers;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;
import com.vaadin.ui.Component;

import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;

public abstract class AbstractDefaultLayoutDropHandler implements DropHandler {

    protected abstract void handleComponentReordering(DragAndDropEvent event);

    protected abstract void handleDropFromLayout(DragAndDropEvent event);

    /**
     * Handles a drop by a component which has an absolute layout as parent. In
     * this case the component is moved.
     * 
     * @param event
     *            The drag and drop event
     */
    protected void handleDropFromAbsoluteParentLayout(DragAndDropEvent event) {
        LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
                .getTransferable();
        TargetDetails details = event.getTargetDetails();
        MouseEventDetails mouseDown = transferable.getMouseDownEvent();
        MouseEventDetails mouseUp = MouseEventDetails
                .deSerialize((String) details
                        .getData(Constants.DROP_DETAIL_MOUSE_EVENT));
        int movex = mouseUp.getClientX() - mouseDown.getClientX();
        int movey = mouseUp.getClientY() - mouseDown.getClientY();
        Component comp = transferable.getComponent();

        DDAbsoluteLayout parent = (DDAbsoluteLayout) comp.getParent();
        ComponentPosition position = parent.getPosition(comp);

        float x = position.getLeftValue() + movex;
        float y = position.getTopValue() + movey;
        position.setLeft(x, Sizeable.UNITS_PIXELS);
        position.setTop(y, Sizeable.UNITS_PIXELS);
    }

    public void drop(DragAndDropEvent event) {
        // Get information about the drop
        TargetDetails details = event.getTargetDetails();
        DropTarget layout = details.getTarget();
        Component source = event.getTransferable().getSourceComponent();

        if (layout == source) {
            handleComponentReordering(event);
        } else if (event.getTransferable() instanceof LayoutBoundTransferable) {
            LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
                    .getTransferable();
            Component comp = transferable.getComponent();
            if (comp == layout) {
                if (comp.getParent() instanceof DDAbsoluteLayout) {
                    handleDropFromAbsoluteParentLayout(event);
                }
            } else {
                handleDropFromLayout(event);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.event.dd.DropHandler#getAcceptCriterion()
     */
    public AcceptCriterion getAcceptCriterion() {
        return AcceptAll.get();
    }

}
