/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal.util;

import java.util.List;

import org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory;

/**
 * @author rushan
 *
 */
public class ModelHelper {

	public static void buildChildCategoryListIncludeThis(PreferencesCategory category, List<PreferencesCategory> childList) {
		childList.add(category);
		for (PreferencesCategory child : category.getChildCategories()) {
			buildChildCategoryListIncludeThis(child, childList);
		}
	}
	
	public static void buildChildCategoryListIncludeThisList(List<PreferencesCategory> list, List<PreferencesCategory> childList) {
		for (PreferencesCategory c : list) {
			buildChildCategoryListIncludeThis(c, childList);
		}
	}
	
}
