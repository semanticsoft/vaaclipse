/**
 */
package org.lunifera.vaaclipse.ui.preferences.model;

import org.eclipse.e4.ui.model.application.MContribution;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Page</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage#getCategory <em>Category</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getPreferencesPage()
 * @model
 * @generated
 */
public interface PreferencesPage extends EObject, MElementContainer<FieldEditor<?>>, MContribution {

	/**
	 * Returns the value of the '<em><b>Category</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getPage <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Category</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Category</em>' container reference.
	 * @see #setCategory(PreferencesCategory)
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getPreferencesPage_Category()
	 * @see org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getPage
	 * @model opposite="page" transient="false"
	 * @generated
	 */
	PreferencesCategory getCategory();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage#getCategory <em>Category</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Category</em>' container reference.
	 * @see #getCategory()
	 * @generated
	 */
	void setCategory(PreferencesCategory value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getPreferencesPage_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);
} // PreferencesPage
