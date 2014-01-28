/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal;

import javax.inject.Inject;

import org.lunifera.vaaclipse.ui.preferences.addon.internal.exception.ValidationFailedException;
import org.lunifera.vaaclipse.ui.preferences.model.IntegerFieldEditor;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;

/**
 * @author rushan
 *
 */
public class IntegerFieldEditorRenderer extends FieldEditorRenderer<Integer> {

	@Inject
	IntegerFieldEditor editor;
	
	TextField textField;
	
	@Override
	public void render() {
		CssLayout layout = createCssLayoutWithCaption();
		textField = new TextField();
		textField.setWidth("100%");
		layout.addComponent(textField);
	}

	@Override
	public Integer getValue() {
		return getPreferences().getInt(editor.getPreferenceName(), editor.getDefaultValueTyped());
	}

	@Override
	public void setValue(Integer value) {
		this.getPreferences().putInt(editor.getPreferenceName(), value);
	}
	
	@Override
	public void validate() throws ValidationFailedException {
		
		if (textField.getValue() != null) {
			int value;
			try {
				value = Integer.parseInt(textField.getValue());
			}
			catch (NumberFormatException e) {
				throw new ValidationFailedException(editor.getLabel(), "The value can not be parsed");
			}
			
			if ((editor.getMinValidValue() != null && value < editor.getMinValidValue()) || (editor.getMaxValidValue() != null && value > editor.getMaxValidValue()))
				throw new ValidationFailedException(editor.getLabel(), String.format("Value should be in range (%s, %s)", 
						editor.getMinValidValue() != null ? editor.getMinValidValue().toString() : "?", 
								editor.getMaxValidValue() != null ? editor.getMaxValidValue().toString() : "?"));
		}
	}

	@Override
	public void save() {
		String value = textField.getValue();
		if (value != null) {
			try {
				int intValue = Integer.parseInt(value);
				setValue(intValue);
			}
			catch (NumberFormatException e) {
				
			}
		}
	}
}
