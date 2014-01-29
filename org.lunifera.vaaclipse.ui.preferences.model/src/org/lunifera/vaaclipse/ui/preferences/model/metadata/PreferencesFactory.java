/**
 */
package org.lunifera.vaaclipse.ui.preferences.model.metadata;

import org.eclipse.emf.ecore.EFactory;

import org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.ComboFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.DirectoryFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.Entry;
import org.lunifera.vaaclipse.ui.preferences.model.FieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.FileFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.IntegerFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.ListEditor;
import org.lunifera.vaaclipse.ui.preferences.model.ListFold;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;
import org.lunifera.vaaclipse.ui.preferences.model.RadioGroupFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.StringFieldEditor;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage
 * @generated
 */
public interface PreferencesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PreferencesFactory eINSTANCE = org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Category</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Category</em>'.
	 * @generated
	 */
	PreferencesCategory createPreferencesCategory();

	/**
	 * Returns a new object of class '<em>Page</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Page</em>'.
	 * @generated
	 */
	PreferencesPage createPreferencesPage();

	/**
	 * Returns a new object of class '<em>Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Field Editor</em>'.
	 * @generated
	 */
	<T> FieldEditor<T> createFieldEditor();

	/**
	 * Returns a new object of class '<em>Boolean Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Boolean Field Editor</em>'.
	 * @generated
	 */
	BooleanFieldEditor createBooleanFieldEditor();

	/**
	 * Returns a new object of class '<em>Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Entry</em>'.
	 * @generated
	 */
	Entry createEntry();

	/**
	 * Returns a new object of class '<em>Combo Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Combo Field Editor</em>'.
	 * @generated
	 */
	ComboFieldEditor createComboFieldEditor();

	/**
	 * Returns a new object of class '<em>List Editor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>List Editor</em>'.
	 * @generated
	 */
	ListEditor createListEditor();

	/**
	 * Returns a new object of class '<em>Radio Group Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Radio Group Field Editor</em>'.
	 * @generated
	 */
	RadioGroupFieldEditor createRadioGroupFieldEditor();

	/**
	 * Returns a new object of class '<em>Scale Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Scale Field Editor</em>'.
	 * @generated
	 */
	ScaleFieldEditor createScaleFieldEditor();

	/**
	 * Returns a new object of class '<em>String Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String Field Editor</em>'.
	 * @generated
	 */
	StringFieldEditor createStringFieldEditor();

	/**
	 * Returns a new object of class '<em>Integer Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Integer Field Editor</em>'.
	 * @generated
	 */
	IntegerFieldEditor createIntegerFieldEditor();

	/**
	 * Returns a new object of class '<em>File Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>File Field Editor</em>'.
	 * @generated
	 */
	FileFieldEditor createFileFieldEditor();

	/**
	 * Returns a new object of class '<em>Directory Field Editor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Directory Field Editor</em>'.
	 * @generated
	 */
	DirectoryFieldEditor createDirectoryFieldEditor();

	/**
	 * Returns a new object of class '<em>List Fold</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>List Fold</em>'.
	 * @generated
	 */
	ListFold createListFold();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PreferencesPackage getPreferencesPackage();

} //PreferencesFactory
