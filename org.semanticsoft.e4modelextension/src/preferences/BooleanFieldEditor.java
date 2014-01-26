/**
 */
package preferences;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Boolean Field Editor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link preferences.BooleanFieldEditor#getStyle <em>Style</em>}</li>
 * </ul>
 * </p>
 *
 * @see preferences.PreferencesPackage#getBooleanFieldEditor()
 * @model superTypes="preferences.FieldEditor<org.eclipse.emf.ecore.EBooleanObject>"
 * @generated
 */
public interface BooleanFieldEditor extends FieldEditor<Boolean> {
	/**
	 * Returns the value of the '<em><b>Style</b></em>' attribute.
	 * The literals are from the enumeration {@link preferences.BooleanFieldStyle}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Style</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Style</em>' attribute.
	 * @see preferences.BooleanFieldStyle
	 * @see #setStyle(BooleanFieldStyle)
	 * @see preferences.PreferencesPackage#getBooleanFieldEditor_Style()
	 * @model
	 * @generated
	 */
	BooleanFieldStyle getStyle();

	/**
	 * Sets the value of the '{@link preferences.BooleanFieldEditor#getStyle <em>Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Style</em>' attribute.
	 * @see preferences.BooleanFieldStyle
	 * @see #getStyle()
	 * @generated
	 */
	void setStyle(BooleanFieldStyle value);

} // BooleanFieldEditor
