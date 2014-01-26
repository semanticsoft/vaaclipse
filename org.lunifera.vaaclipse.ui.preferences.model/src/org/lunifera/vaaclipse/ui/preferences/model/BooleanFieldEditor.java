/**
 */
package org.lunifera.vaaclipse.ui.preferences.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Boolean Field Editor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldEditor#getStyle <em>Style</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getBooleanFieldEditor()
 * @model superTypes="org.lunifera.vaaclipse.ui.preferences.model.FieldEditor<org.eclipse.emf.ecore.EBooleanObject>"
 * @generated
 */
public interface BooleanFieldEditor extends FieldEditor<Boolean> {
	/**
	 * Returns the value of the '<em><b>Style</b></em>' attribute.
	 * The literals are from the enumeration {@link org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldStyle}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Style</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Style</em>' attribute.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldStyle
	 * @see #setStyle(BooleanFieldStyle)
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getBooleanFieldEditor_Style()
	 * @model
	 * @generated
	 */
	BooleanFieldStyle getStyle();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldEditor#getStyle <em>Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Style</em>' attribute.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldStyle
	 * @see #getStyle()
	 * @generated
	 */
	void setStyle(BooleanFieldStyle value);

} // BooleanFieldEditor
