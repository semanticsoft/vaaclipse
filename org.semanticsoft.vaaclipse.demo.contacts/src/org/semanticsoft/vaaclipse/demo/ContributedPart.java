 
package org.semanticsoft.vaaclipse.demo;

import javax.annotation.PostConstruct;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public class ContributedPart {
	
	@PostConstruct
	public void postConstruct(VerticalLayout vl) {
		vl.addComponent(new Button("Test button from contributed part"));
	}
	
	
	
	
}