/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal;

import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.OptionGroup;


/**
 * @author rushan
 *
 */
public class RadioGroupFieldEditorRenderer extends ListFieldEditorRenderer {

	@Override
	public AbstractSelect createSelect() {
		return new OptionGroup();
	}
}
