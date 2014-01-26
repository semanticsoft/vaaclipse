/**
 */
package preferences;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Integer Field Editor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link preferences.IntegerFieldEditor#getMinValidValue <em>Min Valid Value</em>}</li>
 *   <li>{@link preferences.IntegerFieldEditor#getMaxValidValue <em>Max Valid Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see preferences.PreferencesPackage#getIntegerFieldEditor()
 * @model superTypes="preferences.FieldEditor<org.eclipse.emf.ecore.EIntegerObject>"
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
	 * @see preferences.PreferencesPackage#getIntegerFieldEditor_MinValidValue()
	 * @model
	 * @generated
	 */
	Integer getMinValidValue();

	/**
	 * Sets the value of the '{@link preferences.IntegerFieldEditor#getMinValidValue <em>Min Valid Value</em>}' attribute.
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
	 * @see preferences.PreferencesPackage#getIntegerFieldEditor_MaxValidValue()
	 * @model
	 * @generated
	 */
	Integer getMaxValidValue();

	/**
	 * Sets the value of the '{@link preferences.IntegerFieldEditor#getMaxValidValue <em>Max Valid Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Valid Value</em>' attribute.
	 * @see #getMaxValidValue()
	 * @generated
	 */
	void setMaxValidValue(Integer value);

} // IntegerFieldEditor
