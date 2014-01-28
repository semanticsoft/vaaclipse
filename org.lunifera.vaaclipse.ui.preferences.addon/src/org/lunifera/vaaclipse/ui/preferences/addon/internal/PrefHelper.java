/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.lunifera.vaaclipse.ui.preferences.model.FieldEditor;

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
}
