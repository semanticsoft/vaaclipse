/**
 */
package org.lunifera.vaaclipse.ui.preferences.model;

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
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.FieldEditor#getLabel <em>Label</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.FieldEditor#getPreferenceName <em>Preference Name</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.FieldEditor#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.FieldEditor#getDefaultValueTyped <em>Default Value Typed</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getFieldEditor()
 * @model
 * @generated
 */
public interface FieldEditor<T> extends EObject, MUIElement {
	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getFieldEditor_Label()
	 * @model default=""
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.FieldEditor#getLabel <em>Label</em>}' attribute.
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
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getFieldEditor_PreferenceName()
	 * @model
	 * @generated
	 */
	String getPreferenceName();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.FieldEditor#getPreferenceName <em>Preference Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Preference Name</em>' attribute.
	 * @see #getPreferenceName()
	 * @generated
	 */
	void setPreferenceName(String value);

	/**
	 * Returns the value of the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Value</em>' attribute.
	 * @see #setDefaultValue(String)
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getFieldEditor_DefaultValue()
	 * @model
	 * @generated
	 */
	String getDefaultValue();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.FieldEditor#getDefaultValue <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Value</em>' attribute.
	 * @see #getDefaultValue()
	 * @generated
	 */
	void setDefaultValue(String value);

	/**
	 * Returns the value of the '<em><b>Default Value Typed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Value Typed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Value Typed</em>' attribute.
	 * @see #setDefaultValueTyped(Object)
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getFieldEditor_DefaultValueTyped()
	 * @model
	 * @generated
	 */
	T getDefaultValueTyped();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.FieldEditor#getDefaultValueTyped <em>Default Value Typed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Value Typed</em>' attribute.
	 * @see #getDefaultValueTyped()
	 * @generated
	 */
	void setDefaultValueTyped(T value);

} // FieldEditor
