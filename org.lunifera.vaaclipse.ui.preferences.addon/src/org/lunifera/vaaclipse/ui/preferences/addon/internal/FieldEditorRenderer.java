/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal;

import javax.inject.Inject;

import org.lunifera.vaaclipse.ui.preferences.addon.internal.exception.ValidationFailedException;
import org.lunifera.vaaclipse.ui.preferences.model.FieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;
import org.osgi.service.prefs.Preferences;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

/**
 * @author rushan
 *
 */
public abstract class FieldEditorRenderer<T> {
	
	@Inject
	PreferencesPage page;
	
	Component component;
	
	@Inject
	FieldEditor<?> basicInterfaceToEditor;
	
	public abstract void render();
	
	protected void renderInternal(FieldEditor<T> editor) {
		
		//layout.setWidth("100%");
		
		for (String style : editor.getTags()) {
			component.addStyleName(style);
		}
	}
	
	protected CssLayout createCssLayoutWithCaption() {
		CssLayout layout = new CssLayout();
		layout.addComponent(new Label(basicInterfaceToEditor.getLabel()));
		this.component = layout;
		return layout;
	}
	
	public Component getComponent() {
		return component;
	}
	
	public Preferences getPreferences() {
		return (Preferences) basicInterfaceToEditor.getPreferences();
	}
	
	public abstract T getValue();
	public abstract void setValue(T value);
	
	public abstract void save();
	
	public void validate() throws ValidationFailedException {
		
	}
}
