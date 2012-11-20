package org.semanticsoft.vaaclipsedemo.mediaplayer.views;

import javax.annotation.PostConstruct;

import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class SamplePart {
	
	@PostConstruct
	public void pc(VerticalLayout ly){
		ly.addComponent(new TextField("This is a sample text field"));
		ly.addComponent(new Button("This is a sample button"));
	}

}
