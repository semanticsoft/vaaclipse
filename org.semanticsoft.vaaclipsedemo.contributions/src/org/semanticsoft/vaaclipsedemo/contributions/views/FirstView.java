package org.semanticsoft.vaaclipsedemo.contributions.views;

import javax.inject.Inject;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Label;

public class FirstView
{
	@Inject
	public FirstView(ComponentContainer parent)
	{
        parent.addComponent(new Label("First View"));
	}
}
