/**
 */
package preferences;

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
 *   <li>{@link preferences.PreferencesCategory#getName <em>Name</em>}</li>
 *   <li>{@link preferences.PreferencesCategory#getChildCategories <em>Child Categories</em>}</li>
 *   <li>{@link preferences.PreferencesCategory#getParentCategory <em>Parent Category</em>}</li>
 *   <li>{@link preferences.PreferencesCategory#getPage <em>Page</em>}</li>
 * </ul>
 * </p>
 *
 * @see preferences.PreferencesPackage#getPreferencesCategory()
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
	 * @see preferences.PreferencesPackage#getPreferencesCategory_Name()
	 * @model default="No Name"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link preferences.PreferencesCategory#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Child Categories</b></em>' containment reference list.
	 * The list contents are of type {@link preferences.PreferencesCategory}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Child Categories</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child Categories</em>' containment reference list.
	 * @see preferences.PreferencesPackage#getPreferencesCategory_ChildCategories()
	 * @model containment="true"
	 * @generated
	 */
	EList<PreferencesCategory> getChildCategories();

	/**
	 * Returns the value of the '<em><b>Parent Category</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Category</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Category</em>' reference.
	 * @see #setParentCategory(PreferencesCategory)
	 * @see preferences.PreferencesPackage#getPreferencesCategory_ParentCategory()
	 * @model
	 * @generated
	 */
	PreferencesCategory getParentCategory();

	/**
	 * Sets the value of the '{@link preferences.PreferencesCategory#getParentCategory <em>Parent Category</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Category</em>' reference.
	 * @see #getParentCategory()
	 * @generated
	 */
	void setParentCategory(PreferencesCategory value);

	/**
	 * Returns the value of the '<em><b>Page</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link preferences.PreferencesPage#getCategory <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Page</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Page</em>' container reference.
	 * @see #setPage(PreferencesPage)
	 * @see preferences.PreferencesPackage#getPreferencesCategory_Page()
	 * @see preferences.PreferencesPage#getCategory
	 * @model opposite="category" transient="false"
	 * @generated
	 */
	PreferencesPage getPage();

	/**
	 * Sets the value of the '{@link preferences.PreferencesCategory#getPage <em>Page</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Page</em>' container reference.
	 * @see #getPage()
	 * @generated
	 */
	void setPage(PreferencesPage value);

} // PreferencesCategory
