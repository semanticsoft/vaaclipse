package org.semanticsoft.vaaclipsedemo.mediaplayer.views;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.commands.ECommandService;

import org.eclipse.e4.core.commands.EHandlerService;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;

import e4modelextension.Dialog;

public class FooterLayout {
	
	@PostConstruct
	public void pc(HorizontalLayout hl, final Dialog dialog, final EHandlerService handlerService, final ECommandService commandService){
		hl.addComponent(new Button("Test"));
		hl.addComponent(new Button("Another Test"));
		hl.addComponent(new Button("Another other Test"));
		Button finish = new Button("Finish everything");
		hl.addComponent(finish);
		finish.addListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				handlerService.executeHandler(commandService.createCommand(dialog.getCommand().getElementId(), null));
			}
		});
		
	}

}
