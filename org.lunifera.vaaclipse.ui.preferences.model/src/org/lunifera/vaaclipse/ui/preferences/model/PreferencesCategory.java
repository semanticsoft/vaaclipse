/**
 */
package org.lunifera.vaaclipse.ui.preferences.model;

import org.eclipse.e4.ui.model.application.MApplicationElement;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Category</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getName <em>Name</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getChildCategories <em>Child Categories</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getParentCategory <em>Parent Category</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getPage <em>Page</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getPreferencesCategory()
 * @model
 * @generated
 */
public interface PreferencesCategory extends EObject, MApplicationElement {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>"No Name"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getPreferencesCategory_Name()
	 * @model default="No Name"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Child Categories</b></em>' containment reference list.
	 * The list contents are of type {@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory}.
	 * It is bidirectional and its opposite is '{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getParentCategory <em>Parent Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Child Categories</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child Categories</em>' containment reference list.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getPreferencesCategory_ChildCategories()
	 * @see org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getParentCategory
	 * @model opposite="parentCategory" containment="true"
	 * @generated
	 */
	EList<PreferencesCategory> getChildCategories();

	/**
	 * Returns the value of the '<em><b>Parent Category</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getChildCategories <em>Child Categories</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Category</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Category</em>' container reference.
	 * @see #setParentCategory(PreferencesCategory)
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getPreferencesCategory_ParentCategory()
	 * @see org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getChildCategories
	 * @model opposite="childCategories" transient="false"
	 * @generated
	 */
	PreferencesCategory getParentCategory();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getParentCategory <em>Parent Category</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Category</em>' container reference.
	 * @see #getParentCategory()
	 * @generated
	 */
	void setParentCategory(PreferencesCategory value);

	/**
	 * Returns the value of the '<em><b>Page</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage#getCategory <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Page</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Page</em>' containment reference.
	 * @see #setPage(PreferencesPage)
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getPreferencesCategory_Page()
	 * @see org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage#getCategory
	 * @model opposite="category" containment="true"
	 * @generated
	 */
	PreferencesPage getPage();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getPage <em>Page</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Page</em>' containment reference.
	 * @see #getPage()
	 * @generated
	 */
	void setPage(PreferencesPage value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#getPreferencesCategory_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

} // PreferencesCategory
