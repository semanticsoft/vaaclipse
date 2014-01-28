/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.lunifera.vaaclipse.ui.preferences.model.FieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;
import org.osgi.framework.Bundle;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

/**
 * @author rushan
 *
 */
public class PrefHelper {
	public static void populateInterfaces(FieldEditor<?> editor, IEclipseContext rendererContext, Class<?>[] interfaces) {
		for (Class<?> i : interfaces) {
			if (FieldEditor.class.isAssignableFrom(i)) {
				Class<FieldEditor<?>> editorInterface = (Class<FieldEditor<?>>) i;
				rendererContext.set(editorInterface, editor);
				populateInterfaces(editor, rendererContext, i.getInterfaces());
			}
		}
	}
	
	public static String toEquinoxPath(Bundle bundle, PreferencesCategory category) {
		return toEquinoxPreferencePath(bundle, getAbsolutePath(category));
	}
	
	public static String toEquinoxPreferencePath(Bundle bundle, String catPath) {
		return "/configuration/org.eclipse.core.runtime.preferences.OSGiPreferences." + bundle.getBundleId()
		+ catPath;
	}
	
	public static String getAbsolutePath(PreferencesCategory cat) {
		if (cat.getParentCategory() == null)
			return "";
		return getAbsolutePath(cat.getParentCategory()) + "/" + cat.getId();
	}
	
	public static void flush(PreferencesPage page) throws BackingStoreException {
		Set<Preferences> list = getPreferencesSet(page);
		
		for (Preferences p : list) {
			p.flush();
		}
	}

	public static Set<Preferences> getPreferencesSet(PreferencesPage page) {
		Set<Preferences> list = new HashSet<>();
		for (FieldEditor<?> editor : page.getChildren()) {
			Preferences pref = (Preferences) editor.getPreferences();
			list.add(pref);
		}
		return list;
	}
}
