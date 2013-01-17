package org.semanticsoft.vaaclipse.widgets.client.ui.stackwidget;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.VConsole;
import com.vaadin.client.ui.dd.VAcceptCallback;
import com.vaadin.client.ui.dd.VDragEvent;

import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.tabsheet.VDDTabsheetDropHandler;

public class VStackWidgetDropHandler extends VDDTabsheetDropHandler {

    private final VStackWidget layout;

    private final ApplicationConnection client;

    public VStackWidgetDropHandler(VStackWidget layout,
            ApplicationConnection client) {
    	super(layout, client);
        this.layout = layout;
        this.client = client;
    }
    
    @Override
    public boolean drop(VDragEvent drag) {
    	
    	VConsole.log("Drop");
    	
        layout.deEmphasis();
        
        // Update the details
        layout.updateDropDetails(drag);
        
        if (layout.dockZone1 != null && layout.dockZoneContainer != null)
        {
        	layout.removeDockZone();
        }
        
        return layout.postDropHook(drag) && super.drop(drag);
    };
    
    @Override
    public void dragOver(VDragEvent drag) {
    	
    	VConsole.log("Drag Over");
    	
        layout.deEmphasis();

        layout.updateDropDetails(drag);

        layout.postOverHook(drag);

     // Check if we are dropping on our self
        if (layout.equals(drag.getTransferable().getData(
                Constants.TRANSFERABLE_DETAIL_COMPONENT))) {
            return;
        }
        
        // Validate the drop
        validate(new VAcceptCallback() {
            public void accepted(VDragEvent event) {
                layout.emphasis(event.getElementOver(), event);
            }
        }, drag);
    };
    
    @Override
    public void dragLeave(VDragEvent drag) {
    	VConsole.log("Drag Leave");
        layout.deEmphasis();
        layout.updateDropDetails(drag);
        layout.postLeaveHook(drag);
        
        if (layout.dockZone1 != null && layout.dockZoneContainer != null)
        {
        	layout.removeDockZone();
        }
    };
}
