/**
 */
package org.lunifera.vaaclipse.ui.preferences.model;

import org.eclipse.e4.ui.model.application.MContribution;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>List Field Editor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.ListFieldEditor#getEntries <em>Entries</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getListFieldEditor()
 * @model abstract="true" superTypes="org.lunifera.vaaclipse.ui.preferences.model.FieldEditor<org.eclipse.emf.ecore.EString> org.eclipse.e4.ui.model.application.Contribution"
 * @generated
 */
public interface ListFieldEditor extends FieldEditor<String>, MContribution {
	/**
	 * Returns the value of the '<em><b>Entries</b></em>' reference list.
	 * The list contents are of type {@link org.lunifera.vaaclipse.ui.preferences.model.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entries</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entries</em>' reference list.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getListFieldEditor_Entries()
	 * @model
	 * @generated
	 */
	EList<Entry> getEntries();

} // ListFieldEditor
