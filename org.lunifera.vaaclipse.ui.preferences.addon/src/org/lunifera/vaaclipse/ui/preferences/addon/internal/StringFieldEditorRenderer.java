/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal;

import javax.inject.Inject;

import org.lunifera.vaaclipse.ui.preferences.model.StringFieldEditor;

import com.vaadin.ui.TextField;

/**
 * @author rushan
 *
 */
public class StringFieldEditorRenderer extends FieldEditorRenderer<String> {

	@Inject
	StringFieldEditor editor;
	private TextField field;
	
	@Override
	public void render() {
		
		field = new TextField();
		field.setValue(getValue());
		field.setCaption(editor.getLabel());
		component = field;
	}

	@Override
	public String getValue() {
		return getPreferences().get(editor.getPreferenceName(), editor.getDefaultValueTyped());
	}

	@Override
	public void setValue(String value) {
		if (value != null)
			getPreferences().put(editor.getPreferenceName(), value);		
	}

	@Override
	public void save() {
		setValue(field.getValue());
	}
}
