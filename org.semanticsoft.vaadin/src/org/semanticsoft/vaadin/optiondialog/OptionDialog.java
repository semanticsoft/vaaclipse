package org.semanticsoft.vaadin.optiondialog;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class OptionDialog extends Window
{
	public static interface OptionListener
	{
		void optionSelected(OptionDialog optionDialog, int optionId);
	}
	
	public static interface ComponentProvider extends OptionListener
	{
		Component getComponent(OptionDialog optionDialog);
		void setMessage(String message);
	}

	private OptionListener optionListener;
	private String msg;
	private boolean modal = true;
	
	public static final OptionListener CLOSE_LISTENER = new OptionListener() {
		
		@Override
		public void optionSelected(OptionDialog optionDialog, int optionId)
		{
			optionDialog.close();
		}
	};
	
	private ComponentProvider componentProvider = new ComponentProvider() {
		
		Label label = new Label();
		@Override
		public void setMessage(String message)
		{
			label.setPropertyDataSource(new ObjectProperty<String>(message, String.class));
		}
		
		@Override
		public Component getComponent(OptionDialog optionDialog)
		{
			return label;
		}

		@Override
		public void optionSelected(OptionDialog optionDialog, int optionId)
		{
			
		}
	};
	
	private VerticalLayout content;
	private HorizontalLayout buttons = new HorizontalLayout();
	private Map<Button, Integer> button2option = new HashMap<Button, Integer>();
	private Map<Integer, Button> option2button = new HashMap<>();

	public OptionDialog()
	{
		// msgLabel.setWidth("100%");
		// buttons.setWidth("100%");
		content = new VerticalLayout();
		content.setSizeFull();
		this.setContent(content);
		Component component = componentProvider.getComponent(this);
		content.addComponent(component);
		content.addComponent(buttons);
		content.setComponentAlignment(component, Alignment.TOP_CENTER);
		content.setComponentAlignment(buttons, Alignment.BOTTOM_CENTER);
		content.setExpandRatio(component, 1);

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
		componentProvider.setMessage(this.msg);
	}
	
	public ComponentProvider getComponentProvider()
	{
		return componentProvider;
	}
	
	public Component getComponent()
	{
		return this.componentProvider.getComponent(this);
	}
	
	public void setComponentProvider(ComponentProvider componentProvider)
	{
		if (componentProvider == null)
			return;
		Component newComponent = componentProvider.getComponent(this);
		if (newComponent != null)
		{
			Component oldComponent = this.componentProvider.getComponent(this);
			this.content.removeComponent(oldComponent);
			newComponent.setSizeFull();
			this.content.addComponent(newComponent, 0);
			this.content.setExpandRatio(newComponent, 1);
			this.content.setComponentAlignment(newComponent, Alignment.TOP_CENTER);
			this.componentProvider = componentProvider;
		}
	}
	
	public void open(Window parentWindow)
	{
		parentWindow.addWindow(parentWindow);
	}
	
	public void close()
	{
		if (getParent() != null)
			getParent().removeWindow(OptionDialog.this);
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
		option2button.put(optionId, button);
		button.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event)
			{
				Integer optionId = button2option.get(event.getButton());
				
				if (optionListener != null)
					optionListener.optionSelected(OptionDialog.this, optionId);
				
				getComponentProvider().optionSelected(OptionDialog.this, optionId);
			}
		});
	}
	
	public void setOptionEnabled(int optionId, boolean enabled)
	{
		Button button = option2button.get(optionId);
		button.setEnabled(enabled);
	}

	public static void show(Window parentWindow, String caption, String message, String[] options, int w, int h, int units,
			OptionListener optionListener)
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
		show(parentWindow, caption, message, options, -1, -1, -1, optionListener);
	}
}
