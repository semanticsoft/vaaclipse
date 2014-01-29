/**
 */
package org.lunifera.vaaclipse.ui.preferences.model;

import org.eclipse.e4.ui.model.application.MContribution;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>List Editor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.ListEditor#getListCrud <em>List Crud</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.ListEditor#getListFold <em>List Fold</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getListEditor()
 * @model superTypes="org.lunifera.vaaclipse.ui.preferences.model.FieldEditor<org.eclipse.emf.ecore.EString> org.eclipse.e4.ui.model.application.Contribution"
 * @generated
 */
public interface ListEditor extends FieldEditor<String>, MContribution {
	/**
	 * Returns the value of the '<em><b>List Crud</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>List Crud</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>List Crud</em>' reference.
	 * @see #setListCrud(ListCrud)
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getListEditor_ListCrud()
	 * @model
	 * @generated
	 */
	ListCrud getListCrud();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.ListEditor#getListCrud <em>List Crud</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>List Crud</em>' reference.
	 * @see #getListCrud()
	 * @generated
	 */
	void setListCrud(ListCrud value);

	/**
	 * Returns the value of the '<em><b>List Fold</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>List Fold</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>List Fold</em>' reference.
	 * @see #setListFold(ListFold)
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getListEditor_ListFold()
	 * @model
	 * @generated
	 */
	ListFold getListFold();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.ListEditor#getListFold <em>List Fold</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>List Fold</em>' reference.
	 * @see #getListFold()
	 * @generated
	 */
	void setListFold(ListFold value);

} // ListEditor
