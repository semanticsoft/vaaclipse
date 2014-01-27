/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal;

import javax.inject.Inject;

import org.lunifera.vaaclipse.ui.preferences.model.FieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;
import org.osgi.service.prefs.Preferences;

import com.vaadin.ui.Component;

/**
 * @author rushan
 *
 */
public abstract class FieldEditorRenderer<T> {
	
	@Inject
	PreferencesPage page;
	
	Component component;
	
	public abstract void render();
	
	protected void renderInternal(FieldEditor<T> editor) {
		
		//layout.setWidth("100%");
		
		for (String style : editor.getTags()) {
			component.addStyleName(style);
		}
	}
	
	public Component getComponent() {
		return component;
	}
	
	public Preferences getPreferences() {
		return (Preferences) page.getPreferences();
	}
	
	public abstract T getValue();
	public abstract void setValue(T value);
	
	public abstract void save();
}
