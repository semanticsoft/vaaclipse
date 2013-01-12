package fi.jasoft.dragdroplayouts.demo;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultAbsoluteLayoutDropHandler;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;

public class DragdropDragFilterDemo extends CustomComponent {
	
	public DragdropDragFilterDemo() {
		 setCaption("DragFilters");
	     setSizeFull();
	     
	     // Create an absolute layout
	     DDAbsoluteLayout layout = new DDAbsoluteLayout();
	     layout.setSizeFull();
	     setCompositionRoot(layout);
	     
	     // Enable dragging (of all) components
	     layout.setDragMode(LayoutDragMode.CLONE);
	     
	     // Limit dragging to only buttons
	     layout.setDragFilter(new DragFilter() {			
			public boolean isDraggable(Component component) {	
				return component instanceof Button;
			}
	     });

	     // Enable dropping components
	     layout.setDropHandler(new DefaultAbsoluteLayoutDropHandler());
	     	     
	     // Add some components to layout     
	     layout.addComponent(new Label("DragFilters allow you to control which components are draggable." +
	     		" All components in this example are in the same layout but only buttons are " +
	     		"draggable"));
	     layout.addComponent(new Button("Drag Me!"), "left:50px;top:100px");
	     layout.addComponent(new Button("Drag Me Too!"), "left:50px;top:150px");
	     layout.addComponent(new TextField(null, "You cannot drag me!"), "left:50px;top:200px");
	    
	}
}
