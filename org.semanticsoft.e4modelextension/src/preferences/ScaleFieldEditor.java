/**
 */
package preferences;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Scale Field Editor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link preferences.ScaleFieldEditor#getMinValue <em>Min Value</em>}</li>
 *   <li>{@link preferences.ScaleFieldEditor#getMaxValue <em>Max Value</em>}</li>
 *   <li>{@link preferences.ScaleFieldEditor#getIncrementValue <em>Increment Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see preferences.PreferencesPackage#getScaleFieldEditor()
 * @model superTypes="preferences.FieldEditor<org.eclipse.emf.ecore.EIntegerObject>"
 * @generated
 */
public interface ScaleFieldEditor extends FieldEditor<Integer> {
	/**
	 * Returns the value of the '<em><b>Min Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Value</em>' attribute.
	 * @see #setMinValue(Integer)
	 * @see preferences.PreferencesPackage#getScaleFieldEditor_MinValue()
	 * @model
	 * @generated
	 */
	Integer getMinValue();

	/**
	 * Sets the value of the '{@link preferences.ScaleFieldEditor#getMinValue <em>Min Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Value</em>' attribute.
	 * @see #getMinValue()
	 * @generated
	 */
	void setMinValue(Integer value);

	/**
	 * Returns the value of the '<em><b>Max Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Value</em>' attribute.
	 * @see #setMaxValue(Integer)
	 * @see preferences.PreferencesPackage#getScaleFieldEditor_MaxValue()
	 * @model
	 * @generated
	 */
	Integer getMaxValue();

	/**
	 * Sets the value of the '{@link preferences.ScaleFieldEditor#getMaxValue <em>Max Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Value</em>' attribute.
	 * @see #getMaxValue()
	 * @generated
	 */
	void setMaxValue(Integer value);

	/**
	 * Returns the value of the '<em><b>Increment Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Increment Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Increment Value</em>' attribute.
	 * @see #setIncrementValue(Integer)
	 * @see preferences.PreferencesPackage#getScaleFieldEditor_IncrementValue()
	 * @model
	 * @generated
	 */
	Integer getIncrementValue();

	/**
	 * Sets the value of the '{@link preferences.ScaleFieldEditor#getIncrementValue <em>Increment Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Increment Value</em>' attribute.
	 * @see #getIncrementValue()
	 * @generated
	 */
	void setIncrementValue(Integer value);

} // ScaleFieldEditor
