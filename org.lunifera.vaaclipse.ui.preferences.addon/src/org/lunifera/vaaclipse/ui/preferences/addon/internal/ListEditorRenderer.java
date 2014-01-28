/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal;

import java.util.Iterator;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.emf.common.util.EList;
import org.lunifera.vaaclipse.ui.preferences.model.Entry;

import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

/**
 * @author rushan
 *
 */
public class ListEditorRenderer extends ListFieldEditorRenderer {

	private CssLayout buttonPanel;
	private Button addButton;
	private Button removeButton;
	private Button upButton;
	private Button downButton;
	
	@Inject
	IEclipseContext context;

	@Override
	public AbstractSelect createSelect() {
		return new ListSelect();
	}
	
	@Override
	public void render() {
		select = createSelect();
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

	private void createButtons() {
		addButton = addButton("Add", "add-button", new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Object contrib = editor.getObject();
				if (contrib != null) {
					ContextInjectionFactory.invoke(contrib, Execute.class, context);
					refreshSelect();
				}
			}
		});
		
		removeButton = addButton("Remove", "remove-button", new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if (select.getValue() != null) {
					
					Iterator<Entry> it = editor.getEntries().iterator();
					
					while (it.hasNext()) {
						Entry entry = it.next();
						if (select.getValue().equals(entry.getValue())) {
							it.remove();
							refreshSelect();
							break;
						}
					}
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
		
		int index = -1;
		EList<Entry> entries = editor.getEntries();
		for (int i = 0; i < entries.size(); i++) {
			Entry entry = entries.get(i);
			if (selectedValue.equals(entry.getValue())) {
				index = i;
				break;
			}
		}
		
		if (index < 0)
			return;
		
        int target = up ? index - 1 : index + 1;
        
        if (target < 0)
        	target = entries.size() - 1;
        else if (target >= entries.size())
        	target = 0;

        if (index >= 0) {
            Entry selected = entries.remove(index);
            entries.add(target, selected);
        }
        
        refreshSelect();
        select.select(selectedValue);
	}
}
