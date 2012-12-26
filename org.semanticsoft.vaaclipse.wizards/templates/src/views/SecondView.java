package @@viewsPackageName@@;

import javax.inject.Inject;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Label;

public class SecondView
{
	@Inject
	public SecondView(ComponentContainer parent)
	{
		parent.addComponent(new Label("Second view"));
	}
}
