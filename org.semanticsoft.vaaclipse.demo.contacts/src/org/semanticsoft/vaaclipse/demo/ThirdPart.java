 
package org.semanticsoft.vaaclipse.demo;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;

import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class ThirdPart {
	@PostConstruct
	public void postConstruct(VerticalLayout vl) {
		vl.addComponent(new Table("I am a new text Arela"));
	}
	
	
	
	@Focus
	public void onFocus() {
		//TODO Your code here
	}
}