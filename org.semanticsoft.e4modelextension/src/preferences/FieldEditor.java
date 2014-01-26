/**
 */
package preferences;

import org.eclipse.e4.ui.model.application.ui.MUIElement;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Field Editor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link preferences.FieldEditor#getLabel <em>Label</em>}</li>
 *   <li>{@link preferences.FieldEditor#getPreferenceName <em>Preference Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see preferences.PreferencesPackage#getFieldEditor()
 * @model
 * @generated
 */
public interface FieldEditor<T> extends EObject, MUIElement {
	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see preferences.PreferencesPackage#getFieldEditor_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link preferences.FieldEditor#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Preference Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Preference Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Preference Name</em>' attribute.
	 * @see #setPreferenceName(String)
	 * @see preferences.PreferencesPackage#getFieldEditor_PreferenceName()
	 * @model
	 * @generated
	 */
	String getPreferenceName();

	/**
	 * Sets the value of the '{@link preferences.FieldEditor#getPreferenceName <em>Preference Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Preference Name</em>' attribute.
	 * @see #getPreferenceName()
	 * @generated
	 */
	void setPreferenceName(String value);

} // FieldEditor
