/**
 */
package org.lunifera.vaaclipse.ui.preferences.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Scale Field Editor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor#getMinValue <em>Min Value</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor#getMaxValue <em>Max Value</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor#getIncrementValue <em>Increment Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getScaleFieldEditor()
 * @model superTypes="org.lunifera.vaaclipse.ui.preferences.model.FieldEditor<org.eclipse.emf.ecore.EIntegerObject>"
 * @generated
 */
public interface ScaleFieldEditor extends FieldEditor<Integer> {
	/**
	 * Returns the value of the '<em><b>Min Value</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Value</em>' attribute.
	 * @see #setMinValue(Integer)
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getScaleFieldEditor_MinValue()
	 * @model default="0"
	 * @generated
	 */
	Integer getMinValue();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor#getMinValue <em>Min Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Value</em>' attribute.
	 * @see #getMinValue()
	 * @generated
	 */
	void setMinValue(Integer value);

	/**
	 * Returns the value of the '<em><b>Max Value</b></em>' attribute.
	 * The default value is <code>"100"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Value</em>' attribute.
	 * @see #setMaxValue(Integer)
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getScaleFieldEditor_MaxValue()
	 * @model default="100"
	 * @generated
	 */
	Integer getMaxValue();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor#getMaxValue <em>Max Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Value</em>' attribute.
	 * @see #getMaxValue()
	 * @generated
	 */
	void setMaxValue(Integer value);

	/**
	 * Returns the value of the '<em><b>Increment Value</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Increment Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Increment Value</em>' attribute.
	 * @see #setIncrementValue(Integer)
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getScaleFieldEditor_IncrementValue()
	 * @model default="1"
	 * @generated
	 */
	Integer getIncrementValue();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor#getIncrementValue <em>Increment Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Increment Value</em>' attribute.
	 * @see #getIncrementValue()
	 * @generated
	 */
	void setIncrementValue(Integer value);

} // ScaleFieldEditor
