package org.semanticsoft.vaaclipsedemo.mediaplayer.views;

import java.awt.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

public class FooterLayout {
	
	@PostConstruct
	public void pc(HorizontalLayout hl){
		hl.addComponent(new Button("Test"));
		hl.addComponent(new Button("Another Test"));
		hl.addComponent(new Button("Another other Test"));
		
	}

}
