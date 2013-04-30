package org.semanticsoft.vaaclipse.widgets.client.ui.stackwidget;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ui.dd.VAcceptCallback;
import com.vaadin.client.ui.dd.VDragEvent;

import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.tabsheet.VDDTabsheetDropHandler;

public class VStackWidgetDropHandler extends VDDTabsheetDropHandler {

	private final VStackWidget stackWidget;

	public VStackWidgetDropHandler(VStackWidget stackWidget,
			ApplicationConnection client) {
		super(stackWidget, client);
		this.stackWidget = stackWidget;
	}

	@Override
	public void dragOver(VDragEvent drag) {

		//VConsole.log("Drag Over");

		stackWidget.deEmphasis();

		stackWidget.updateDropDetails(drag);

		stackWidget.postOverHook(drag);

		// Check if we are dropping on our self
		if (stackWidget.equals(drag.getTransferable().getData(
				Constants.TRANSFERABLE_DETAIL_COMPONENT))) {
			return;
		}

		// Validate the drop
		validate(new VAcceptCallback() {
			public void accepted(VDragEvent event) {
				stackWidget.emphasis(event.getElementOver(), event);
			}
		}, drag);
	};

	@Override
	public void dragLeave(VDragEvent drag) {
		//VConsole.log("Drag Leave");
		stackWidget.deEmphasis();
		stackWidget.updateDropDetails(drag);
		stackWidget.postLeaveHook(drag);

		stackWidget.removeDockZone();
	};
	
	@Override
	public boolean drop(VDragEvent event) 
	{
		if (!stackWidget.updateRegion(event))
			return false;
		
		Object sourceWidget = event.getTransferable().getDragSource();
		if (sourceWidget instanceof StackWidgetConnector)
		{
			sourceWidget = ((StackWidgetConnector) sourceWidget).getWidget();
			VStackWidget stackWidget = (VStackWidget) sourceWidget;
			stackWidget.restoreLocationOfPartToolbar();
		}
		
		return super.drop(event);
	}
}
