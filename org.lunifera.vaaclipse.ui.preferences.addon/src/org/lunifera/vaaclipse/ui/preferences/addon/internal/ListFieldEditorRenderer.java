/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal;

import javax.inject.Inject;

import org.lunifera.vaaclipse.ui.preferences.model.Entry;
import org.lunifera.vaaclipse.ui.preferences.model.ListFieldEditor;

import com.vaadin.data.Item;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

/**
 * @author rushan
 *
 */
public abstract class ListFieldEditorRenderer extends FieldEditorRenderer<String> {
	@Inject
	ListFieldEditor editor;
	
	AbstractSelect select;
	
	public abstract AbstractSelect createSelect();
	
	@Override
	public void render() {
		select = createSelect();
		
		CssLayout layout = new CssLayout();
		layout.addComponent(new Label(editor.getLabel()));
		layout.addComponent(select);
		
		refreshSelect();
		component = layout;
	}

	protected void refreshSelect() {
		select.removeAllItems();
		for (Entry entry : editor.getEntries()) {
			Item item = select.addItem(entry.getValue());
			select.setItemCaption(item, entry.getName());
		}
		select.select(getValue());
	}

	@Override
	public String getValue() {
		return getPreferences().get(editor.getPreferenceName(), editor.getDefaultValue());
	}

	@Override
	public void setValue(String value) {
		getPreferences().put(editor.getPreferenceName(), value);
	}

	@Override
	public void save() {
		if (select.getValue() != null)
			setValue(select.getValue().toString());
	}
}
