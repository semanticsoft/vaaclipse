/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal;

import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.ComboBox;

/**
 * @author rushan
 *
 */
public class ComboFieldEditorRenderer extends ListFieldEditorRenderer {

	@Override
	public AbstractSelect createSelect() {
		return new ComboBox();
	}
}
