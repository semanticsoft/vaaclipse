 
package org.semanticsoft.vaaclipse.demo;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public class ContactsList {
	
	@PostConstruct
	public void postConstruct(VerticalLayout layout) {
		System.err.println("In postccccconstruct");
		layout.addComponent(new Button("I am button 1"));
	}
	
	
	
	@Focus
	public void onFocus() {
		//TODO Your code here
	}
	
	
}