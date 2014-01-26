/**
 */
package org.lunifera.vaaclipse.ui.preferences.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Integer Field Editor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.IntegerFieldEditor#getMinValidValue <em>Min Valid Value</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.IntegerFieldEditor#getMaxValidValue <em>Max Valid Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getIntegerFieldEditor()
 * @model superTypes="org.lunifera.vaaclipse.ui.preferences.model.FieldEditor<org.eclipse.emf.ecore.EIntegerObject>"
 * @generated
 */
public interface IntegerFieldEditor extends FieldEditor<Integer> {
	/**
	 * Returns the value of the '<em><b>Min Valid Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Valid Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Valid Value</em>' attribute.
	 * @see #setMinValidValue(Integer)
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getIntegerFieldEditor_MinValidValue()
	 * @model
	 * @generated
	 */
	Integer getMinValidValue();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.IntegerFieldEditor#getMinValidValue <em>Min Valid Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Valid Value</em>' attribute.
	 * @see #getMinValidValue()
	 * @generated
	 */
	void setMinValidValue(Integer value);

	/**
	 * Returns the value of the '<em><b>Max Valid Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Valid Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Valid Value</em>' attribute.
	 * @see #setMaxValidValue(Integer)
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getIntegerFieldEditor_MaxValidValue()
	 * @model
	 * @generated
	 */
	Integer getMaxValidValue();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.IntegerFieldEditor#getMaxValidValue <em>Max Valid Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Valid Value</em>' attribute.
	 * @see #getMaxValidValue()
	 * @generated
	 */
	void setMaxValidValue(Integer value);

} // IntegerFieldEditor
