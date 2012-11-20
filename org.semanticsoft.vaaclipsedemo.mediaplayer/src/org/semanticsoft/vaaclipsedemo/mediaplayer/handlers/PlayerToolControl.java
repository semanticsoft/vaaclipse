package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.TextField;

public class PlayerToolControl
{
	private Button button;
	private TextField textField;

	@PostConstruct
	public void postConstruct(ComponentContainer cc, final IEventBroker broker)
	{
		button = new Button("Load video from YouTube");
		textField = new TextField();
		textField.setWidth("20em");
		textField.setValue("http://www.youtube.com/v/0417pQz7iIk");

		button.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event)
			{

				Object value = textField.getValue();

				if (value == null)
					return;

				String string = value.toString();
				if ((string.isEmpty()))
					return;
				else
				{
					Media media = new Media();
					media.setName("Tool control media");
					media.setUri(string);
					media.setDescription("Tool control media description");
					broker.send(org.semanticsoft.vaaclipsedemo.mediaplayer.constants.IMediaConstants.mediaSelected,
							media);

				}
			}

		});
		cc.addComponent(button);
		cc.addComponent(textField);
	}// pretty easy eh :)

}
