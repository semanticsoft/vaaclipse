package fi.jasoft.dragdroplayouts.demo;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Button.ClickEvent;

import fi.jasoft.dragdroplayouts.DDCssLayout;
import fi.jasoft.dragdroplayouts.DDCssLayout.CssLayoutTargetDetails;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultCssLayoutDropHandler;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;

public class DragdropCssLayoutDemo extends CustomComponent {

	public DragdropCssLayoutDemo() {
		 setCaption("Css Layout");
	     setSizeFull();
	     
	     DDCssLayout cssLayout = new DDCssLayout();
	     cssLayout.setSizeFull();
	     setCompositionRoot(cssLayout);
	     
	     // Enable dragging
	     cssLayout.setDragMode(LayoutDragMode.CLONE);
	     
	     // Enable dropping
	     cssLayout.setDropHandler(new DefaultCssLayoutDropHandler());
	     
	     // Only allow draggin buttons
	     cssLayout.setDragFilter(new DragFilter() {
			public boolean isDraggable(Component component) {
				return component instanceof Button;
			}
		});
	     
	     // Add some components
        Label lbl = new Label(
                "This is an CSS layout, the positions are defined by css rules. Try dragging the components around.");
        cssLayout.addComponent(lbl);
        Button btn = new Button("Button 1", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                getApplication().getMainWindow().showNotification("Click!");
            }
        });
        cssLayout.addComponent(btn);
        btn = new Button("Button 2", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                getApplication().getMainWindow().showNotification("Click!");
            }
        });
        cssLayout.addComponent(btn);
        btn = new Button("Button 3", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                getApplication().getMainWindow().showNotification("Click!");
            }
        });
        cssLayout.addComponent(btn);
	}
}
