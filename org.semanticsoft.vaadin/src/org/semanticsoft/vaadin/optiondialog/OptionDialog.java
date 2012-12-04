package org.semanticsoft.vaadin.optiondialog;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class OptionDialog extends Window
{
	public static interface OptionListener
	{
		void optionSelected(int optionId);
	}

	private OptionListener optionListener;
	private String msg;
	private boolean modal = true;

	private Label msgLabel = new Label();
	private HorizontalLayout buttons = new HorizontalLayout();
	private Map<Button, Integer> button2option = new HashMap<Button, Integer>();

	public OptionDialog()
	{
		// msgLabel.setWidth("100%");
		// buttons.setWidth("100%");
		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		this.setContent(content);
		content.addComponent(msgLabel);
		content.addComponent(buttons);
		content.setComponentAlignment(msgLabel, Alignment.TOP_CENTER);
		content.setComponentAlignment(buttons, Alignment.BOTTOM_CENTER);

		this.center();
		this.setWidth("500px");
		this.setHeight("100px");
	}

	public String getMessage()
	{
		return msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
		this.msgLabel.setPropertyDataSource(new ObjectProperty<String>(this.msg, String.class));
	}

	public boolean isModal()
	{
		return this.modal;
	}

	public void setModal(boolean modality)
	{
		this.modal = modality;
	}

	public OptionListener getOptionListener()
	{
		return optionListener;
	}

	public void setOptionListener(OptionListener optionListener)
	{
		this.optionListener = optionListener;
	}

	public void addOption(int optionId, String optionText)
	{
		Button button = new Button();
		button.setCaption(optionText);
		buttons.addComponent(button);
		button2option.put(button, optionId);
		button.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event)
			{
				if (optionListener != null)
				{
					Integer optionId = button2option.get(event.getButton());
					if (getParent() != null)
						getParent().removeWindow(OptionDialog.this);
					optionListener.optionSelected(optionId);
				}
			}
		});
	}

	public static void show(Window parentWindow, String caption, String message, String[] options,
			OptionListener optionListener, int w, int h, int units)
	{
		OptionDialog optionDialog = new OptionDialog();
		if (w > 0 && h > 0)
		{
			optionDialog.setWidth(w, units);
			optionDialog.setHeight(h, units);
		}

		optionDialog.setModal(optionDialog.modal);
		optionDialog.setCaption(caption);
		optionDialog.setMessage(message);
		optionDialog.setOptionListener(optionListener);

		for (int i = 0; i < options.length; i++)
		{
			optionDialog.addOption(i, options[i]);
		}

		parentWindow.addWindow(optionDialog);
	}

	public static void show(Window parentWindow, String caption, String message, String[] options,
			OptionListener optionListener)
	{
		show(parentWindow, caption, message, options, optionListener, -1, -1, -1);
	}
}
