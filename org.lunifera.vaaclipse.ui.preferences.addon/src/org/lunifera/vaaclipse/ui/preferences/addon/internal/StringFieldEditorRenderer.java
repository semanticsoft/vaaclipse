/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal;

import javax.inject.Inject;

import org.lunifera.vaaclipse.ui.preferences.addon.internal.exception.ValidationFailedException;
import org.lunifera.vaaclipse.ui.preferences.model.StringFieldEditor;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
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
		//field.setCaption(editor.getLabel());
		
		CssLayout layout = new CssLayout();
		Label label = new Label(editor.getLabel());
		layout.addComponent(label);
		layout.addComponent(field);
		
		component = layout;
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
	public void validate() throws ValidationFailedException {
		if (field.getValue() != null && editor.getMaxLength() != null) {
			if (field.getValue().length() > editor.getMaxLength()) {
				throw new ValidationFailedException(editor.getLabel(), "The length of text should not be greater than " + editor.getMaxLength());
			}
		}
	}

	@Override
	public void save() {
		setValue(field.getValue());
	}
}
