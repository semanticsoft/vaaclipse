/**
 */
package org.lunifera.vaaclipse.ui.preferences.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>String Field Editor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.StringFieldEditor#getMaxLength <em>Max Length</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getStringFieldEditor()
 * @model superTypes="org.lunifera.vaaclipse.ui.preferences.model.FieldEditor<org.eclipse.emf.ecore.EString>"
 * @generated
 */
public interface StringFieldEditor extends FieldEditor<String> {
	/**
	 * Returns the value of the '<em><b>Max Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Length</em>' attribute.
	 * @see #setMaxLength(Integer)
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getStringFieldEditor_MaxLength()
	 * @model
	 * @generated
	 */
	Integer getMaxLength();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.StringFieldEditor#getMaxLength <em>Max Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Length</em>' attribute.
	 * @see #getMaxLength()
	 * @generated
	 */
	void setMaxLength(Integer value);

} // StringFieldEditor
