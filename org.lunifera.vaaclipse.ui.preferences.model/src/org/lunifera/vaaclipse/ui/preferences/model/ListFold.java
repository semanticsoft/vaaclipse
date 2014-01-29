/**
 */
package org.lunifera.vaaclipse.ui.preferences.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>List Fold</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getListFold()
 * @model
 * @generated
 */
public interface ListFold extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model prevDataType="org.lunifera.vaaclipse.ui.preferences.model.StringBuffer"
	 * @generated
	 */
	void apply(String value, StringBuffer prev);

} // ListFold
