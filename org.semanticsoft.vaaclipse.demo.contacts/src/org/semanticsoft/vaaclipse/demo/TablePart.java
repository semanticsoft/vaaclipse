 
package org.semanticsoft.vaaclipse.demo;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;

import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class TablePart {
	
	@PostConstruct
	public void postConstruct(VerticalLayout vl) {
		vl.addComponent(new TextArea("I am a new text Arela"));
	}
	
	
	
	@Focus
	public void onFocus() {
		//TODO Your code here
	}
	
	
}