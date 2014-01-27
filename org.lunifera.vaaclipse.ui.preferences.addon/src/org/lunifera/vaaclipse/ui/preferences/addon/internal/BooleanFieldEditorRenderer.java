/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal;

import javax.inject.Inject;

import org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldStyle;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

/**
 * @author rushan
 *
 */
public class BooleanFieldEditorRenderer extends FieldEditorRenderer<Boolean> {
	
	@Inject
	BooleanFieldEditor booleanFieldEditor;
	private CheckBox cb;
	
	@Override
	public Boolean getValue() {
		return getPreferences().getBoolean(booleanFieldEditor.getPreferenceName(), booleanFieldEditor.getDefaultValueTyped());
	}
	
	@Override
	public void setValue(Boolean value) {
		getPreferences().putBoolean(booleanFieldEditor.getPreferenceName(), value);
	}
	
	@Override
	public void render() {
		renderInternal(booleanFieldEditor);
		
		cb = new CheckBox();
		cb.setValue(getValue());
		
		if (booleanFieldEditor.getStyle() == BooleanFieldStyle.DEFAULT) {
			cb.setCaption(booleanFieldEditor.getLabel());
			component = cb;
		}
		else if (booleanFieldEditor.getStyle() == BooleanFieldStyle.SEPARATE_LABEL) {
			
			HorizontalLayout hl = new HorizontalLayout();
			
			Label separateLabel = new Label(booleanFieldEditor.getLabel());
			separateLabel.addStyleName("boolean-separate-label");
			cb.addStyleName("boolean-separate-checkbox");
			hl.addComponent(separateLabel);
			hl.addComponent(cb);
			
			component = hl;
		}
	}

	@Override
	public void save() {
		setValue(cb.getValue());
	}
	
}
