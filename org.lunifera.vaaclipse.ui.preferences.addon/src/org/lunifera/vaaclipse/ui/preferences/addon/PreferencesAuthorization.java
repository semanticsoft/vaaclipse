package org.lunifera.vaaclipse.ui.preferences.addon;

import org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory;

public interface PreferencesAuthorization {

    /**
     * Returns true if the preference page may be showed for the current user
     * @param pageId
     * @param user
     * @return
     **/
    boolean isAllowed(PreferencesCategory category);
    
    boolean exportAllowed();
    boolean importAllowed();
}
