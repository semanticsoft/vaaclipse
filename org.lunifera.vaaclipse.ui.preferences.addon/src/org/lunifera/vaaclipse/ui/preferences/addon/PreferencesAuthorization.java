package org.lunifera.vaaclipse.ui.preferences.addon;

import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;

public interface PreferencesAuthorization {

    /**
     * Returns true if the preference page may be showed for the given user
     * @param pageId
     * @param user
     * @return
     **/
    boolean isAllowed(PreferencesPage page, String user);
    
    boolean exportAllowed(String user);
    boolean importAllowed(String user);
}
