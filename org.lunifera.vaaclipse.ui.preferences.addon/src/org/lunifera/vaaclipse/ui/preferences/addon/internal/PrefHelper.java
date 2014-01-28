/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal;

import java.util.Map;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.lunifera.vaaclipse.ui.preferences.model.FieldEditor;
import org.osgi.framework.Bundle;

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
	
	public static String toEquinoxPreferencePath(Map<String, Bundle> bundlesByName,
			String scope) {
		int searchFrom = 0;
		if (scope.startsWith("/"))
			searchFrom = 1;
		int endOfBundleName = scope.indexOf("/", searchFrom);
		String absolutePreferencePath = scope;
		if (endOfBundleName > 0 && endOfBundleName < scope.length() - 1) {
			String bundleName = scope.substring(searchFrom, endOfBundleName);
			Bundle bundle = bundlesByName.get(bundleName);
			if (bundle != null) {
				String bundleRelativePath = scope.substring(endOfBundleName + 1);
				absolutePreferencePath = "/configuration/org.eclipse.core.runtime.preferences.OSGiPreferences." + bundle.getBundleId()
						+ "/" + bundleRelativePath;	
			}
		}
		return absolutePreferencePath;
	}
}
