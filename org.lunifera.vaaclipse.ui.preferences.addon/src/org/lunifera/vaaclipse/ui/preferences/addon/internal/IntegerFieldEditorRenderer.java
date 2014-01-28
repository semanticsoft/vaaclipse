/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal;

import javax.inject.Inject;

import org.lunifera.vaaclipse.ui.preferences.model.IntegerFieldEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
