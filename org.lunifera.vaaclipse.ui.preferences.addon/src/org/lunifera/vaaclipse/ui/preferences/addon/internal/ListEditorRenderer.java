/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.lunifera.vaaclipse.ui.preferences.addon.internal.util.EmfHelper;
import org.lunifera.vaaclipse.ui.preferences.model.ListCrud;
import org.lunifera.vaaclipse.ui.preferences.model.ListEditor;
import org.lunifera.vaaclipse.ui.preferences.model.ListFold;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;

/**
 * @author rushan
 *
 */
public class ListEditorRenderer extends FieldEditorRenderer<String> {

	private CssLayout buttonPanel;
	private Button addButton;
	private Button removeButton;
	private Button upButton;
	private Button downButton;
	
	@Inject
	ListEditor editor;
	
	ListSelect select;
	
	@Inject
	IEclipseContext context;

	@Override
	public void render() {
		select = new ListSelect();
		refreshSelect();
		
		CssLayout layout = new CssLayout();
		layout.setWidth("100%");
		
		layout.addComponent(new Label(editor.getLabel()));
		
		HorizontalLayout row = new HorizontalLayout();
		row.setWidth("100%");
		layout.addComponent(row);
		
		row.addComponent(select);
		select.setWidth("100%");
		
		buttonPanel = new CssLayout();
		buttonPanel.setSizeFull();
		row.addComponent(buttonPanel);
		
		row.setExpandRatio(select, 8);
		row.setExpandRatio(buttonPanel, 2);
		
		createButtons();
		component = layout;
	}
	
	private void refreshSelect() {
		
		String value = getValue();
		if (value != null) {
			String[] values = value.split(";");
			select.removeAllItems();
			for (String v : values) {
				select.addItem(v);
			}
		}
		
	}

	@Override
	public void save() {
		
		String value = "";
		for (Object v : select.getItemIds()) {
			value += v.toString() + ";";
		}
		
		setValue(value);
	}
	
	public static class DefaultListFold extends EmfHelper.EInterface implements ListFold {

		@Override
		public void apply(String value, StringBuffer prev) {
			prev.append(";" + value);
		}		
	}
	
	public String buildValue() {
		
		ListFold op = editor.getListFold();
		if (op == null) {
			op = new DefaultListFold();
		}
		
		StringBuffer str = new StringBuffer();
		for (Object s : select.getItemIds()) {
			op.apply(s.toString(), str);
		}
		
		return str.toString();
	}

	private void createButtons() {
		addButton = addButton("Add", "add-button", new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				ListCrud crud = editor.getListCrud();
				if (crud != null) {
					String newValue = crud.addNewValue(buildValue());
					select.addItem(newValue);
				}
			}
		});
		
		removeButton = addButton("Remove", "remove-button", new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if (select.getValue() != null) {
					select.removeItem(select.getValue());
				}
			}
		});
		upButton = addButton("Up", "up-button", new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				swap(true);
			}
		});
		downButton = addButton("Down", "down-button", new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				swap(false);
			}
		});
		
//		buttonPanel.setExpandRatio(addButton, 0);
//		buttonPanel.setExpandRatio(removeButton, 0);
//		buttonPanel.setExpandRatio(upButton, 0);
//		buttonPanel.setExpandRatio(downButton, 1);
	}
	
	private Button addButton(String name, String style, ClickListener listener) {
		Button button = new Button(name);
		button.addStyleName(style);
		button.setWidth("80px");
		buttonPanel.addComponent(button);
		button.addClickListener(listener);
		return button;
	}
	
	private void swap(boolean up) {
		Object selectedValue = select.getValue();
		if (selectedValue == null)
			return;
		
		int index = -1;
		List<Object> list = new ArrayList<>(select.getItemIds());
		for (int i = 0; i < list.size(); i++) {
			if (selectedValue.equals(list.get(i))) {
				index = i;
				break;
			}
		}
		
		if (index < 0)
			return;
		
        int target = up ? index - 1 : index + 1;
        
        if (target < 0)
        	target = list.size() - 1;
        else if (target >= list.size())
        	target = 0;

        if (index >= 0) {
            Object selected = list.remove(index);
            list.add(target, selected);
        }
        
        select.removeAllItems();
        
        for (Object o : list) {
        	select.addItem(o);
        }
        
        select.select(selectedValue);
	}

	@Override
	public String getValue() {
		return getPreferences().get(editor.getPreferenceName(), editor.getDefaultValue());
	}

	@Override
	public void setValue(String value) {
		getPreferences().put(editor.getPreferenceName(), value);
	}
}
