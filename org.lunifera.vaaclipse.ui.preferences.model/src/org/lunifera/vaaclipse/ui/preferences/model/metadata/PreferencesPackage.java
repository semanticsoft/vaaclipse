/**
 */
package org.lunifera.vaaclipse.ui.preferences.model.metadata;

import org.eclipse.e4.ui.model.application.impl.ApplicationPackageImpl;

import org.eclipse.e4.ui.model.application.ui.impl.UiPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesFactory
 * @model kind="package"
 * @generated
 */
public interface PreferencesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "preferences";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.lunifera.org/vaaclipse/ui/preferences";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "preferences";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PreferencesPackage eINSTANCE = org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesCategoryImpl <em>Category</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesCategoryImpl
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getPreferencesCategory()
	 * @generated
	 */
	int PREFERENCES_CATEGORY = 0;

	/**
	 * The feature id for the '<em><b>Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_CATEGORY__ELEMENT_ID = ApplicationPackageImpl.APPLICATION_ELEMENT__ELEMENT_ID;

	/**
	 * The feature id for the '<em><b>Persisted State</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_CATEGORY__PERSISTED_STATE = ApplicationPackageImpl.APPLICATION_ELEMENT__PERSISTED_STATE;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_CATEGORY__TAGS = ApplicationPackageImpl.APPLICATION_ELEMENT__TAGS;

	/**
	 * The feature id for the '<em><b>Contributor URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_CATEGORY__CONTRIBUTOR_URI = ApplicationPackageImpl.APPLICATION_ELEMENT__CONTRIBUTOR_URI;

	/**
	 * The feature id for the '<em><b>Transient Data</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_CATEGORY__TRANSIENT_DATA = ApplicationPackageImpl.APPLICATION_ELEMENT__TRANSIENT_DATA;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_CATEGORY__NAME = ApplicationPackageImpl.APPLICATION_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Child Categories</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_CATEGORY__CHILD_CATEGORIES = ApplicationPackageImpl.APPLICATION_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parent Category</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_CATEGORY__PARENT_CATEGORY = ApplicationPackageImpl.APPLICATION_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Page</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_CATEGORY__PAGE = ApplicationPackageImpl.APPLICATION_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Category</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_CATEGORY_FEATURE_COUNT = ApplicationPackageImpl.APPLICATION_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Category</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_CATEGORY_OPERATION_COUNT = ApplicationPackageImpl.APPLICATION_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPageImpl <em>Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPageImpl
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getPreferencesPage()
	 * @generated
	 */
	int PREFERENCES_PAGE = 1;

	/**
	 * The feature id for the '<em><b>Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__ELEMENT_ID = UiPackageImpl.ELEMENT_CONTAINER__ELEMENT_ID;

	/**
	 * The feature id for the '<em><b>Persisted State</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__PERSISTED_STATE = UiPackageImpl.ELEMENT_CONTAINER__PERSISTED_STATE;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__TAGS = UiPackageImpl.ELEMENT_CONTAINER__TAGS;

	/**
	 * The feature id for the '<em><b>Contributor URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__CONTRIBUTOR_URI = UiPackageImpl.ELEMENT_CONTAINER__CONTRIBUTOR_URI;

	/**
	 * The feature id for the '<em><b>Transient Data</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__TRANSIENT_DATA = UiPackageImpl.ELEMENT_CONTAINER__TRANSIENT_DATA;

	/**
	 * The feature id for the '<em><b>Widget</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__WIDGET = UiPackageImpl.ELEMENT_CONTAINER__WIDGET;

	/**
	 * The feature id for the '<em><b>Renderer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__RENDERER = UiPackageImpl.ELEMENT_CONTAINER__RENDERER;

	/**
	 * The feature id for the '<em><b>To Be Rendered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__TO_BE_RENDERED = UiPackageImpl.ELEMENT_CONTAINER__TO_BE_RENDERED;

	/**
	 * The feature id for the '<em><b>On Top</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__ON_TOP = UiPackageImpl.ELEMENT_CONTAINER__ON_TOP;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__VISIBLE = UiPackageImpl.ELEMENT_CONTAINER__VISIBLE;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__PARENT = UiPackageImpl.ELEMENT_CONTAINER__PARENT;

	/**
	 * The feature id for the '<em><b>Container Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__CONTAINER_DATA = UiPackageImpl.ELEMENT_CONTAINER__CONTAINER_DATA;

	/**
	 * The feature id for the '<em><b>Cur Shared Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__CUR_SHARED_REF = UiPackageImpl.ELEMENT_CONTAINER__CUR_SHARED_REF;

	/**
	 * The feature id for the '<em><b>Visible When</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__VISIBLE_WHEN = UiPackageImpl.ELEMENT_CONTAINER__VISIBLE_WHEN;

	/**
	 * The feature id for the '<em><b>Accessibility Phrase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__ACCESSIBILITY_PHRASE = UiPackageImpl.ELEMENT_CONTAINER__ACCESSIBILITY_PHRASE;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__CHILDREN = UiPackageImpl.ELEMENT_CONTAINER__CHILDREN;

	/**
	 * The feature id for the '<em><b>Selected Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__SELECTED_ELEMENT = UiPackageImpl.ELEMENT_CONTAINER__SELECTED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Category</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__CATEGORY = UiPackageImpl.ELEMENT_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Preferences Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE__PREFERENCES_SCOPE = UiPackageImpl.ELEMENT_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE_FEATURE_COUNT = UiPackageImpl.ELEMENT_CONTAINER_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Localized Accessibility Phrase</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE___GET_LOCALIZED_ACCESSIBILITY_PHRASE = UiPackageImpl.ELEMENT_CONTAINER___GET_LOCALIZED_ACCESSIBILITY_PHRASE;

	/**
	 * The number of operations of the '<em>Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCES_PAGE_OPERATION_COUNT = UiPackageImpl.ELEMENT_CONTAINER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.FieldEditorImpl <em>Field Editor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.FieldEditorImpl
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getFieldEditor()
	 * @generated
	 */
	int FIELD_EDITOR = 2;

	/**
	 * The feature id for the '<em><b>Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR__ELEMENT_ID = UiPackageImpl.UI_ELEMENT__ELEMENT_ID;

	/**
	 * The feature id for the '<em><b>Persisted State</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR__PERSISTED_STATE = UiPackageImpl.UI_ELEMENT__PERSISTED_STATE;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR__TAGS = UiPackageImpl.UI_ELEMENT__TAGS;

	/**
	 * The feature id for the '<em><b>Contributor URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR__CONTRIBUTOR_URI = UiPackageImpl.UI_ELEMENT__CONTRIBUTOR_URI;

	/**
	 * The feature id for the '<em><b>Transient Data</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR__TRANSIENT_DATA = UiPackageImpl.UI_ELEMENT__TRANSIENT_DATA;

	/**
	 * The feature id for the '<em><b>Widget</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR__WIDGET = UiPackageImpl.UI_ELEMENT__WIDGET;

	/**
	 * The feature id for the '<em><b>Renderer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR__RENDERER = UiPackageImpl.UI_ELEMENT__RENDERER;

	/**
	 * The feature id for the '<em><b>To Be Rendered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR__TO_BE_RENDERED = UiPackageImpl.UI_ELEMENT__TO_BE_RENDERED;

	/**
	 * The feature id for the '<em><b>On Top</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR__ON_TOP = UiPackageImpl.UI_ELEMENT__ON_TOP;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR__VISIBLE = UiPackageImpl.UI_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR__PARENT = UiPackageImpl.UI_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Container Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR__CONTAINER_DATA = UiPackageImpl.UI_ELEMENT__CONTAINER_DATA;

	/**
	 * The feature id for the '<em><b>Cur Shared Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR__CUR_SHARED_REF = UiPackageImpl.UI_ELEMENT__CUR_SHARED_REF;

	/**
	 * The feature id for the '<em><b>Visible When</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR__VISIBLE_WHEN = UiPackageImpl.UI_ELEMENT__VISIBLE_WHEN;

	/**
	 * The feature id for the '<em><b>Accessibility Phrase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR__ACCESSIBILITY_PHRASE = UiPackageImpl.UI_ELEMENT__ACCESSIBILITY_PHRASE;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR__LABEL = UiPackageImpl.UI_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Preference Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR__PREFERENCE_NAME = UiPackageImpl.UI_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR_FEATURE_COUNT = UiPackageImpl.UI_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Localized Accessibility Phrase</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE = UiPackageImpl.UI_ELEMENT___GET_LOCALIZED_ACCESSIBILITY_PHRASE;

	/**
	 * The number of operations of the '<em>Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_EDITOR_OPERATION_COUNT = UiPackageImpl.UI_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.BooleanFieldEditorImpl <em>Boolean Field Editor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.BooleanFieldEditorImpl
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getBooleanFieldEditor()
	 * @generated
	 */
	int BOOLEAN_FIELD_EDITOR = 3;

	/**
	 * The feature id for the '<em><b>Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR__ELEMENT_ID = FIELD_EDITOR__ELEMENT_ID;

	/**
	 * The feature id for the '<em><b>Persisted State</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR__PERSISTED_STATE = FIELD_EDITOR__PERSISTED_STATE;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR__TAGS = FIELD_EDITOR__TAGS;

	/**
	 * The feature id for the '<em><b>Contributor URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR__CONTRIBUTOR_URI = FIELD_EDITOR__CONTRIBUTOR_URI;

	/**
	 * The feature id for the '<em><b>Transient Data</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR__TRANSIENT_DATA = FIELD_EDITOR__TRANSIENT_DATA;

	/**
	 * The feature id for the '<em><b>Widget</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR__WIDGET = FIELD_EDITOR__WIDGET;

	/**
	 * The feature id for the '<em><b>Renderer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR__RENDERER = FIELD_EDITOR__RENDERER;

	/**
	 * The feature id for the '<em><b>To Be Rendered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR__TO_BE_RENDERED = FIELD_EDITOR__TO_BE_RENDERED;

	/**
	 * The feature id for the '<em><b>On Top</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR__ON_TOP = FIELD_EDITOR__ON_TOP;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR__VISIBLE = FIELD_EDITOR__VISIBLE;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR__PARENT = FIELD_EDITOR__PARENT;

	/**
	 * The feature id for the '<em><b>Container Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR__CONTAINER_DATA = FIELD_EDITOR__CONTAINER_DATA;

	/**
	 * The feature id for the '<em><b>Cur Shared Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR__CUR_SHARED_REF = FIELD_EDITOR__CUR_SHARED_REF;

	/**
	 * The feature id for the '<em><b>Visible When</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR__VISIBLE_WHEN = FIELD_EDITOR__VISIBLE_WHEN;

	/**
	 * The feature id for the '<em><b>Accessibility Phrase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR__ACCESSIBILITY_PHRASE = FIELD_EDITOR__ACCESSIBILITY_PHRASE;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR__LABEL = FIELD_EDITOR__LABEL;

	/**
	 * The feature id for the '<em><b>Preference Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR__PREFERENCE_NAME = FIELD_EDITOR__PREFERENCE_NAME;

	/**
	 * The feature id for the '<em><b>Style</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR__STYLE = FIELD_EDITOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Boolean Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR_FEATURE_COUNT = FIELD_EDITOR_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Localized Accessibility Phrase</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE = FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE;

	/**
	 * The number of operations of the '<em>Boolean Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_FIELD_EDITOR_OPERATION_COUNT = FIELD_EDITOR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.ListFieldEditorImpl <em>List Field Editor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.ListFieldEditorImpl
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getListFieldEditor()
	 * @generated
	 */
	int LIST_FIELD_EDITOR = 4;

	/**
	 * The feature id for the '<em><b>Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__ELEMENT_ID = FIELD_EDITOR__ELEMENT_ID;

	/**
	 * The feature id for the '<em><b>Persisted State</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__PERSISTED_STATE = FIELD_EDITOR__PERSISTED_STATE;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__TAGS = FIELD_EDITOR__TAGS;

	/**
	 * The feature id for the '<em><b>Contributor URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__CONTRIBUTOR_URI = FIELD_EDITOR__CONTRIBUTOR_URI;

	/**
	 * The feature id for the '<em><b>Transient Data</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__TRANSIENT_DATA = FIELD_EDITOR__TRANSIENT_DATA;

	/**
	 * The feature id for the '<em><b>Widget</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__WIDGET = FIELD_EDITOR__WIDGET;

	/**
	 * The feature id for the '<em><b>Renderer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__RENDERER = FIELD_EDITOR__RENDERER;

	/**
	 * The feature id for the '<em><b>To Be Rendered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__TO_BE_RENDERED = FIELD_EDITOR__TO_BE_RENDERED;

	/**
	 * The feature id for the '<em><b>On Top</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__ON_TOP = FIELD_EDITOR__ON_TOP;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__VISIBLE = FIELD_EDITOR__VISIBLE;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__PARENT = FIELD_EDITOR__PARENT;

	/**
	 * The feature id for the '<em><b>Container Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__CONTAINER_DATA = FIELD_EDITOR__CONTAINER_DATA;

	/**
	 * The feature id for the '<em><b>Cur Shared Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__CUR_SHARED_REF = FIELD_EDITOR__CUR_SHARED_REF;

	/**
	 * The feature id for the '<em><b>Visible When</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__VISIBLE_WHEN = FIELD_EDITOR__VISIBLE_WHEN;

	/**
	 * The feature id for the '<em><b>Accessibility Phrase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__ACCESSIBILITY_PHRASE = FIELD_EDITOR__ACCESSIBILITY_PHRASE;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__LABEL = FIELD_EDITOR__LABEL;

	/**
	 * The feature id for the '<em><b>Preference Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__PREFERENCE_NAME = FIELD_EDITOR__PREFERENCE_NAME;

	/**
	 * The feature id for the '<em><b>Contribution URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__CONTRIBUTION_URI = FIELD_EDITOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__OBJECT = FIELD_EDITOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR__ENTRIES = FIELD_EDITOR_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>List Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR_FEATURE_COUNT = FIELD_EDITOR_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Localized Accessibility Phrase</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE = FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE;

	/**
	 * The number of operations of the '<em>List Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FIELD_EDITOR_OPERATION_COUNT = FIELD_EDITOR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.ComboFieldEditorImpl <em>Combo Field Editor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.ComboFieldEditorImpl
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getComboFieldEditor()
	 * @generated
	 */
	int COMBO_FIELD_EDITOR = 6;

	/**
	 * The meta object id for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.ListEditorImpl <em>List Editor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.ListEditorImpl
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getListEditor()
	 * @generated
	 */
	int LIST_EDITOR = 7;

	/**
	 * The meta object id for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.RadioGroupFieldEditorImpl <em>Radio Group Field Editor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.RadioGroupFieldEditorImpl
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getRadioGroupFieldEditor()
	 * @generated
	 */
	int RADIO_GROUP_FIELD_EDITOR = 8;

	/**
	 * The meta object id for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.ScaleFieldEditorImpl <em>Scale Field Editor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.ScaleFieldEditorImpl
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getScaleFieldEditor()
	 * @generated
	 */
	int SCALE_FIELD_EDITOR = 9;

	/**
	 * The meta object id for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.StringFieldEditorImpl <em>String Field Editor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.StringFieldEditorImpl
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getStringFieldEditor()
	 * @generated
	 */
	int STRING_FIELD_EDITOR = 10;

	/**
	 * The meta object id for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.IntegerFieldEditorImpl <em>Integer Field Editor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.IntegerFieldEditorImpl
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getIntegerFieldEditor()
	 * @generated
	 */
	int INTEGER_FIELD_EDITOR = 11;

	/**
	 * The meta object id for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.FileFieldEditorImpl <em>File Field Editor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.FileFieldEditorImpl
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getFileFieldEditor()
	 * @generated
	 */
	int FILE_FIELD_EDITOR = 12;

	/**
	 * The meta object id for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.DirectoryFieldEditorImpl <em>Directory Field Editor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.DirectoryFieldEditorImpl
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getDirectoryFieldEditor()
	 * @generated
	 */
	int DIRECTORY_FIELD_EDITOR = 13;

	/**
	 * The meta object id for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.EntryImpl <em>Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.EntryImpl
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getEntry()
	 * @generated
	 */
	int ENTRY = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_OPERATION_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__ELEMENT_ID = LIST_FIELD_EDITOR__ELEMENT_ID;

	/**
	 * The feature id for the '<em><b>Persisted State</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__PERSISTED_STATE = LIST_FIELD_EDITOR__PERSISTED_STATE;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__TAGS = LIST_FIELD_EDITOR__TAGS;

	/**
	 * The feature id for the '<em><b>Contributor URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__CONTRIBUTOR_URI = LIST_FIELD_EDITOR__CONTRIBUTOR_URI;

	/**
	 * The feature id for the '<em><b>Transient Data</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__TRANSIENT_DATA = LIST_FIELD_EDITOR__TRANSIENT_DATA;

	/**
	 * The feature id for the '<em><b>Widget</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__WIDGET = LIST_FIELD_EDITOR__WIDGET;

	/**
	 * The feature id for the '<em><b>Renderer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__RENDERER = LIST_FIELD_EDITOR__RENDERER;

	/**
	 * The feature id for the '<em><b>To Be Rendered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__TO_BE_RENDERED = LIST_FIELD_EDITOR__TO_BE_RENDERED;

	/**
	 * The feature id for the '<em><b>On Top</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__ON_TOP = LIST_FIELD_EDITOR__ON_TOP;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__VISIBLE = LIST_FIELD_EDITOR__VISIBLE;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__PARENT = LIST_FIELD_EDITOR__PARENT;

	/**
	 * The feature id for the '<em><b>Container Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__CONTAINER_DATA = LIST_FIELD_EDITOR__CONTAINER_DATA;

	/**
	 * The feature id for the '<em><b>Cur Shared Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__CUR_SHARED_REF = LIST_FIELD_EDITOR__CUR_SHARED_REF;

	/**
	 * The feature id for the '<em><b>Visible When</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__VISIBLE_WHEN = LIST_FIELD_EDITOR__VISIBLE_WHEN;

	/**
	 * The feature id for the '<em><b>Accessibility Phrase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__ACCESSIBILITY_PHRASE = LIST_FIELD_EDITOR__ACCESSIBILITY_PHRASE;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__LABEL = LIST_FIELD_EDITOR__LABEL;

	/**
	 * The feature id for the '<em><b>Preference Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__PREFERENCE_NAME = LIST_FIELD_EDITOR__PREFERENCE_NAME;

	/**
	 * The feature id for the '<em><b>Contribution URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__CONTRIBUTION_URI = LIST_FIELD_EDITOR__CONTRIBUTION_URI;

	/**
	 * The feature id for the '<em><b>Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__OBJECT = LIST_FIELD_EDITOR__OBJECT;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR__ENTRIES = LIST_FIELD_EDITOR__ENTRIES;

	/**
	 * The number of structural features of the '<em>Combo Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR_FEATURE_COUNT = LIST_FIELD_EDITOR_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Localized Accessibility Phrase</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE = LIST_FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE;

	/**
	 * The number of operations of the '<em>Combo Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBO_FIELD_EDITOR_OPERATION_COUNT = LIST_FIELD_EDITOR_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__ELEMENT_ID = LIST_FIELD_EDITOR__ELEMENT_ID;

	/**
	 * The feature id for the '<em><b>Persisted State</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__PERSISTED_STATE = LIST_FIELD_EDITOR__PERSISTED_STATE;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__TAGS = LIST_FIELD_EDITOR__TAGS;

	/**
	 * The feature id for the '<em><b>Contributor URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__CONTRIBUTOR_URI = LIST_FIELD_EDITOR__CONTRIBUTOR_URI;

	/**
	 * The feature id for the '<em><b>Transient Data</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__TRANSIENT_DATA = LIST_FIELD_EDITOR__TRANSIENT_DATA;

	/**
	 * The feature id for the '<em><b>Widget</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__WIDGET = LIST_FIELD_EDITOR__WIDGET;

	/**
	 * The feature id for the '<em><b>Renderer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__RENDERER = LIST_FIELD_EDITOR__RENDERER;

	/**
	 * The feature id for the '<em><b>To Be Rendered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__TO_BE_RENDERED = LIST_FIELD_EDITOR__TO_BE_RENDERED;

	/**
	 * The feature id for the '<em><b>On Top</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__ON_TOP = LIST_FIELD_EDITOR__ON_TOP;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__VISIBLE = LIST_FIELD_EDITOR__VISIBLE;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__PARENT = LIST_FIELD_EDITOR__PARENT;

	/**
	 * The feature id for the '<em><b>Container Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__CONTAINER_DATA = LIST_FIELD_EDITOR__CONTAINER_DATA;

	/**
	 * The feature id for the '<em><b>Cur Shared Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__CUR_SHARED_REF = LIST_FIELD_EDITOR__CUR_SHARED_REF;

	/**
	 * The feature id for the '<em><b>Visible When</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__VISIBLE_WHEN = LIST_FIELD_EDITOR__VISIBLE_WHEN;

	/**
	 * The feature id for the '<em><b>Accessibility Phrase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__ACCESSIBILITY_PHRASE = LIST_FIELD_EDITOR__ACCESSIBILITY_PHRASE;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__LABEL = LIST_FIELD_EDITOR__LABEL;

	/**
	 * The feature id for the '<em><b>Preference Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__PREFERENCE_NAME = LIST_FIELD_EDITOR__PREFERENCE_NAME;

	/**
	 * The feature id for the '<em><b>Contribution URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__CONTRIBUTION_URI = LIST_FIELD_EDITOR__CONTRIBUTION_URI;

	/**
	 * The feature id for the '<em><b>Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__OBJECT = LIST_FIELD_EDITOR__OBJECT;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR__ENTRIES = LIST_FIELD_EDITOR__ENTRIES;

	/**
	 * The number of structural features of the '<em>List Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR_FEATURE_COUNT = LIST_FIELD_EDITOR_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Localized Accessibility Phrase</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE = LIST_FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE;

	/**
	 * The number of operations of the '<em>List Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_EDITOR_OPERATION_COUNT = LIST_FIELD_EDITOR_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__ELEMENT_ID = LIST_FIELD_EDITOR__ELEMENT_ID;

	/**
	 * The feature id for the '<em><b>Persisted State</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__PERSISTED_STATE = LIST_FIELD_EDITOR__PERSISTED_STATE;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__TAGS = LIST_FIELD_EDITOR__TAGS;

	/**
	 * The feature id for the '<em><b>Contributor URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__CONTRIBUTOR_URI = LIST_FIELD_EDITOR__CONTRIBUTOR_URI;

	/**
	 * The feature id for the '<em><b>Transient Data</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__TRANSIENT_DATA = LIST_FIELD_EDITOR__TRANSIENT_DATA;

	/**
	 * The feature id for the '<em><b>Widget</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__WIDGET = LIST_FIELD_EDITOR__WIDGET;

	/**
	 * The feature id for the '<em><b>Renderer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__RENDERER = LIST_FIELD_EDITOR__RENDERER;

	/**
	 * The feature id for the '<em><b>To Be Rendered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__TO_BE_RENDERED = LIST_FIELD_EDITOR__TO_BE_RENDERED;

	/**
	 * The feature id for the '<em><b>On Top</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__ON_TOP = LIST_FIELD_EDITOR__ON_TOP;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__VISIBLE = LIST_FIELD_EDITOR__VISIBLE;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__PARENT = LIST_FIELD_EDITOR__PARENT;

	/**
	 * The feature id for the '<em><b>Container Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__CONTAINER_DATA = LIST_FIELD_EDITOR__CONTAINER_DATA;

	/**
	 * The feature id for the '<em><b>Cur Shared Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__CUR_SHARED_REF = LIST_FIELD_EDITOR__CUR_SHARED_REF;

	/**
	 * The feature id for the '<em><b>Visible When</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__VISIBLE_WHEN = LIST_FIELD_EDITOR__VISIBLE_WHEN;

	/**
	 * The feature id for the '<em><b>Accessibility Phrase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__ACCESSIBILITY_PHRASE = LIST_FIELD_EDITOR__ACCESSIBILITY_PHRASE;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__LABEL = LIST_FIELD_EDITOR__LABEL;

	/**
	 * The feature id for the '<em><b>Preference Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__PREFERENCE_NAME = LIST_FIELD_EDITOR__PREFERENCE_NAME;

	/**
	 * The feature id for the '<em><b>Contribution URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__CONTRIBUTION_URI = LIST_FIELD_EDITOR__CONTRIBUTION_URI;

	/**
	 * The feature id for the '<em><b>Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__OBJECT = LIST_FIELD_EDITOR__OBJECT;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR__ENTRIES = LIST_FIELD_EDITOR__ENTRIES;

	/**
	 * The number of structural features of the '<em>Radio Group Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR_FEATURE_COUNT = LIST_FIELD_EDITOR_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Localized Accessibility Phrase</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE = LIST_FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE;

	/**
	 * The number of operations of the '<em>Radio Group Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RADIO_GROUP_FIELD_EDITOR_OPERATION_COUNT = LIST_FIELD_EDITOR_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__ELEMENT_ID = FIELD_EDITOR__ELEMENT_ID;

	/**
	 * The feature id for the '<em><b>Persisted State</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__PERSISTED_STATE = FIELD_EDITOR__PERSISTED_STATE;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__TAGS = FIELD_EDITOR__TAGS;

	/**
	 * The feature id for the '<em><b>Contributor URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__CONTRIBUTOR_URI = FIELD_EDITOR__CONTRIBUTOR_URI;

	/**
	 * The feature id for the '<em><b>Transient Data</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__TRANSIENT_DATA = FIELD_EDITOR__TRANSIENT_DATA;

	/**
	 * The feature id for the '<em><b>Widget</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__WIDGET = FIELD_EDITOR__WIDGET;

	/**
	 * The feature id for the '<em><b>Renderer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__RENDERER = FIELD_EDITOR__RENDERER;

	/**
	 * The feature id for the '<em><b>To Be Rendered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__TO_BE_RENDERED = FIELD_EDITOR__TO_BE_RENDERED;

	/**
	 * The feature id for the '<em><b>On Top</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__ON_TOP = FIELD_EDITOR__ON_TOP;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__VISIBLE = FIELD_EDITOR__VISIBLE;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__PARENT = FIELD_EDITOR__PARENT;

	/**
	 * The feature id for the '<em><b>Container Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__CONTAINER_DATA = FIELD_EDITOR__CONTAINER_DATA;

	/**
	 * The feature id for the '<em><b>Cur Shared Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__CUR_SHARED_REF = FIELD_EDITOR__CUR_SHARED_REF;

	/**
	 * The feature id for the '<em><b>Visible When</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__VISIBLE_WHEN = FIELD_EDITOR__VISIBLE_WHEN;

	/**
	 * The feature id for the '<em><b>Accessibility Phrase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__ACCESSIBILITY_PHRASE = FIELD_EDITOR__ACCESSIBILITY_PHRASE;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__LABEL = FIELD_EDITOR__LABEL;

	/**
	 * The feature id for the '<em><b>Preference Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__PREFERENCE_NAME = FIELD_EDITOR__PREFERENCE_NAME;

	/**
	 * The feature id for the '<em><b>Min Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__MIN_VALUE = FIELD_EDITOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Max Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__MAX_VALUE = FIELD_EDITOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Increment Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR__INCREMENT_VALUE = FIELD_EDITOR_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Scale Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR_FEATURE_COUNT = FIELD_EDITOR_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Localized Accessibility Phrase</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE = FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE;

	/**
	 * The number of operations of the '<em>Scale Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALE_FIELD_EDITOR_OPERATION_COUNT = FIELD_EDITOR_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR__ELEMENT_ID = FIELD_EDITOR__ELEMENT_ID;

	/**
	 * The feature id for the '<em><b>Persisted State</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR__PERSISTED_STATE = FIELD_EDITOR__PERSISTED_STATE;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR__TAGS = FIELD_EDITOR__TAGS;

	/**
	 * The feature id for the '<em><b>Contributor URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR__CONTRIBUTOR_URI = FIELD_EDITOR__CONTRIBUTOR_URI;

	/**
	 * The feature id for the '<em><b>Transient Data</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR__TRANSIENT_DATA = FIELD_EDITOR__TRANSIENT_DATA;

	/**
	 * The feature id for the '<em><b>Widget</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR__WIDGET = FIELD_EDITOR__WIDGET;

	/**
	 * The feature id for the '<em><b>Renderer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR__RENDERER = FIELD_EDITOR__RENDERER;

	/**
	 * The feature id for the '<em><b>To Be Rendered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR__TO_BE_RENDERED = FIELD_EDITOR__TO_BE_RENDERED;

	/**
	 * The feature id for the '<em><b>On Top</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR__ON_TOP = FIELD_EDITOR__ON_TOP;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR__VISIBLE = FIELD_EDITOR__VISIBLE;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR__PARENT = FIELD_EDITOR__PARENT;

	/**
	 * The feature id for the '<em><b>Container Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR__CONTAINER_DATA = FIELD_EDITOR__CONTAINER_DATA;

	/**
	 * The feature id for the '<em><b>Cur Shared Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR__CUR_SHARED_REF = FIELD_EDITOR__CUR_SHARED_REF;

	/**
	 * The feature id for the '<em><b>Visible When</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR__VISIBLE_WHEN = FIELD_EDITOR__VISIBLE_WHEN;

	/**
	 * The feature id for the '<em><b>Accessibility Phrase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR__ACCESSIBILITY_PHRASE = FIELD_EDITOR__ACCESSIBILITY_PHRASE;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR__LABEL = FIELD_EDITOR__LABEL;

	/**
	 * The feature id for the '<em><b>Preference Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR__PREFERENCE_NAME = FIELD_EDITOR__PREFERENCE_NAME;

	/**
	 * The number of structural features of the '<em>String Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR_FEATURE_COUNT = FIELD_EDITOR_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Localized Accessibility Phrase</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE = FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE;

	/**
	 * The number of operations of the '<em>String Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FIELD_EDITOR_OPERATION_COUNT = FIELD_EDITOR_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__ELEMENT_ID = FIELD_EDITOR__ELEMENT_ID;

	/**
	 * The feature id for the '<em><b>Persisted State</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__PERSISTED_STATE = FIELD_EDITOR__PERSISTED_STATE;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__TAGS = FIELD_EDITOR__TAGS;

	/**
	 * The feature id for the '<em><b>Contributor URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__CONTRIBUTOR_URI = FIELD_EDITOR__CONTRIBUTOR_URI;

	/**
	 * The feature id for the '<em><b>Transient Data</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__TRANSIENT_DATA = FIELD_EDITOR__TRANSIENT_DATA;

	/**
	 * The feature id for the '<em><b>Widget</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__WIDGET = FIELD_EDITOR__WIDGET;

	/**
	 * The feature id for the '<em><b>Renderer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__RENDERER = FIELD_EDITOR__RENDERER;

	/**
	 * The feature id for the '<em><b>To Be Rendered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__TO_BE_RENDERED = FIELD_EDITOR__TO_BE_RENDERED;

	/**
	 * The feature id for the '<em><b>On Top</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__ON_TOP = FIELD_EDITOR__ON_TOP;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__VISIBLE = FIELD_EDITOR__VISIBLE;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__PARENT = FIELD_EDITOR__PARENT;

	/**
	 * The feature id for the '<em><b>Container Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__CONTAINER_DATA = FIELD_EDITOR__CONTAINER_DATA;

	/**
	 * The feature id for the '<em><b>Cur Shared Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__CUR_SHARED_REF = FIELD_EDITOR__CUR_SHARED_REF;

	/**
	 * The feature id for the '<em><b>Visible When</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__VISIBLE_WHEN = FIELD_EDITOR__VISIBLE_WHEN;

	/**
	 * The feature id for the '<em><b>Accessibility Phrase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__ACCESSIBILITY_PHRASE = FIELD_EDITOR__ACCESSIBILITY_PHRASE;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__LABEL = FIELD_EDITOR__LABEL;

	/**
	 * The feature id for the '<em><b>Preference Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__PREFERENCE_NAME = FIELD_EDITOR__PREFERENCE_NAME;

	/**
	 * The feature id for the '<em><b>Min Valid Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__MIN_VALID_VALUE = FIELD_EDITOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Max Valid Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR__MAX_VALID_VALUE = FIELD_EDITOR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Integer Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR_FEATURE_COUNT = FIELD_EDITOR_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Localized Accessibility Phrase</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE = FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE;

	/**
	 * The number of operations of the '<em>Integer Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_FIELD_EDITOR_OPERATION_COUNT = FIELD_EDITOR_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__ELEMENT_ID = FIELD_EDITOR__ELEMENT_ID;

	/**
	 * The feature id for the '<em><b>Persisted State</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__PERSISTED_STATE = FIELD_EDITOR__PERSISTED_STATE;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__TAGS = FIELD_EDITOR__TAGS;

	/**
	 * The feature id for the '<em><b>Contributor URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__CONTRIBUTOR_URI = FIELD_EDITOR__CONTRIBUTOR_URI;

	/**
	 * The feature id for the '<em><b>Transient Data</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__TRANSIENT_DATA = FIELD_EDITOR__TRANSIENT_DATA;

	/**
	 * The feature id for the '<em><b>Widget</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__WIDGET = FIELD_EDITOR__WIDGET;

	/**
	 * The feature id for the '<em><b>Renderer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__RENDERER = FIELD_EDITOR__RENDERER;

	/**
	 * The feature id for the '<em><b>To Be Rendered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__TO_BE_RENDERED = FIELD_EDITOR__TO_BE_RENDERED;

	/**
	 * The feature id for the '<em><b>On Top</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__ON_TOP = FIELD_EDITOR__ON_TOP;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__VISIBLE = FIELD_EDITOR__VISIBLE;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__PARENT = FIELD_EDITOR__PARENT;

	/**
	 * The feature id for the '<em><b>Container Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__CONTAINER_DATA = FIELD_EDITOR__CONTAINER_DATA;

	/**
	 * The feature id for the '<em><b>Cur Shared Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__CUR_SHARED_REF = FIELD_EDITOR__CUR_SHARED_REF;

	/**
	 * The feature id for the '<em><b>Visible When</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__VISIBLE_WHEN = FIELD_EDITOR__VISIBLE_WHEN;

	/**
	 * The feature id for the '<em><b>Accessibility Phrase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__ACCESSIBILITY_PHRASE = FIELD_EDITOR__ACCESSIBILITY_PHRASE;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__LABEL = FIELD_EDITOR__LABEL;

	/**
	 * The feature id for the '<em><b>Preference Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__PREFERENCE_NAME = FIELD_EDITOR__PREFERENCE_NAME;

	/**
	 * The feature id for the '<em><b>Contribution URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__CONTRIBUTION_URI = FIELD_EDITOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR__OBJECT = FIELD_EDITOR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>File Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR_FEATURE_COUNT = FIELD_EDITOR_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Localized Accessibility Phrase</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE = FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE;

	/**
	 * The number of operations of the '<em>File Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FIELD_EDITOR_OPERATION_COUNT = FIELD_EDITOR_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__ELEMENT_ID = FIELD_EDITOR__ELEMENT_ID;

	/**
	 * The feature id for the '<em><b>Persisted State</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__PERSISTED_STATE = FIELD_EDITOR__PERSISTED_STATE;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__TAGS = FIELD_EDITOR__TAGS;

	/**
	 * The feature id for the '<em><b>Contributor URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__CONTRIBUTOR_URI = FIELD_EDITOR__CONTRIBUTOR_URI;

	/**
	 * The feature id for the '<em><b>Transient Data</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__TRANSIENT_DATA = FIELD_EDITOR__TRANSIENT_DATA;

	/**
	 * The feature id for the '<em><b>Widget</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__WIDGET = FIELD_EDITOR__WIDGET;

	/**
	 * The feature id for the '<em><b>Renderer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__RENDERER = FIELD_EDITOR__RENDERER;

	/**
	 * The feature id for the '<em><b>To Be Rendered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__TO_BE_RENDERED = FIELD_EDITOR__TO_BE_RENDERED;

	/**
	 * The feature id for the '<em><b>On Top</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__ON_TOP = FIELD_EDITOR__ON_TOP;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__VISIBLE = FIELD_EDITOR__VISIBLE;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__PARENT = FIELD_EDITOR__PARENT;

	/**
	 * The feature id for the '<em><b>Container Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__CONTAINER_DATA = FIELD_EDITOR__CONTAINER_DATA;

	/**
	 * The feature id for the '<em><b>Cur Shared Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__CUR_SHARED_REF = FIELD_EDITOR__CUR_SHARED_REF;

	/**
	 * The feature id for the '<em><b>Visible When</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__VISIBLE_WHEN = FIELD_EDITOR__VISIBLE_WHEN;

	/**
	 * The feature id for the '<em><b>Accessibility Phrase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__ACCESSIBILITY_PHRASE = FIELD_EDITOR__ACCESSIBILITY_PHRASE;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__LABEL = FIELD_EDITOR__LABEL;

	/**
	 * The feature id for the '<em><b>Preference Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__PREFERENCE_NAME = FIELD_EDITOR__PREFERENCE_NAME;

	/**
	 * The feature id for the '<em><b>Contribution URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__CONTRIBUTION_URI = FIELD_EDITOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR__OBJECT = FIELD_EDITOR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Directory Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR_FEATURE_COUNT = FIELD_EDITOR_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Localized Accessibility Phrase</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE = FIELD_EDITOR___GET_LOCALIZED_ACCESSIBILITY_PHRASE;

	/**
	 * The number of operations of the '<em>Directory Field Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTORY_FIELD_EDITOR_OPERATION_COUNT = FIELD_EDITOR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldStyle <em>Boolean Field Style</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldStyle
	 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getBooleanFieldStyle()
	 * @generated
	 */
	int BOOLEAN_FIELD_STYLE = 14;


	/**
	 * Returns the meta object for class '{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Category</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory
	 * @generated
	 */
	EClass getPreferencesCategory();

	/**
	 * Returns the meta object for the attribute '{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getName()
	 * @see #getPreferencesCategory()
	 * @generated
	 */
	EAttribute getPreferencesCategory_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getChildCategories <em>Child Categories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Child Categories</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getChildCategories()
	 * @see #getPreferencesCategory()
	 * @generated
	 */
	EReference getPreferencesCategory_ChildCategories();

	/**
	 * Returns the meta object for the container reference '{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getParentCategory <em>Parent Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent Category</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getParentCategory()
	 * @see #getPreferencesCategory()
	 * @generated
	 */
	EReference getPreferencesCategory_ParentCategory();

	/**
	 * Returns the meta object for the containment reference '{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getPage <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Page</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory#getPage()
	 * @see #getPreferencesCategory()
	 * @generated
	 */
	EReference getPreferencesCategory_Page();

	/**
	 * Returns the meta object for class '{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Page</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage
	 * @generated
	 */
	EClass getPreferencesPage();

	/**
	 * Returns the meta object for the container reference '{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage#getCategory <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Category</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage#getCategory()
	 * @see #getPreferencesPage()
	 * @generated
	 */
	EReference getPreferencesPage_Category();

	/**
	 * Returns the meta object for the attribute '{@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage#getPreferencesScope <em>Preferences Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Preferences Scope</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage#getPreferencesScope()
	 * @see #getPreferencesPage()
	 * @generated
	 */
	EAttribute getPreferencesPage_PreferencesScope();

	/**
	 * Returns the meta object for class '{@link org.lunifera.vaaclipse.ui.preferences.model.FieldEditor <em>Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Field Editor</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.FieldEditor
	 * @generated
	 */
	EClass getFieldEditor();

	/**
	 * Returns the meta object for the attribute '{@link org.lunifera.vaaclipse.ui.preferences.model.FieldEditor#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.FieldEditor#getLabel()
	 * @see #getFieldEditor()
	 * @generated
	 */
	EAttribute getFieldEditor_Label();

	/**
	 * Returns the meta object for the attribute '{@link org.lunifera.vaaclipse.ui.preferences.model.FieldEditor#getPreferenceName <em>Preference Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Preference Name</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.FieldEditor#getPreferenceName()
	 * @see #getFieldEditor()
	 * @generated
	 */
	EAttribute getFieldEditor_PreferenceName();

	/**
	 * Returns the meta object for class '{@link org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldEditor <em>Boolean Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Field Editor</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldEditor
	 * @generated
	 */
	EClass getBooleanFieldEditor();

	/**
	 * Returns the meta object for the attribute '{@link org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldEditor#getStyle <em>Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Style</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldEditor#getStyle()
	 * @see #getBooleanFieldEditor()
	 * @generated
	 */
	EAttribute getBooleanFieldEditor_Style();

	/**
	 * Returns the meta object for class '{@link org.lunifera.vaaclipse.ui.preferences.model.ListFieldEditor <em>List Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>List Field Editor</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.ListFieldEditor
	 * @generated
	 */
	EClass getListFieldEditor();

	/**
	 * Returns the meta object for the reference list '{@link org.lunifera.vaaclipse.ui.preferences.model.ListFieldEditor#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Entries</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.ListFieldEditor#getEntries()
	 * @see #getListFieldEditor()
	 * @generated
	 */
	EReference getListFieldEditor_Entries();

	/**
	 * Returns the meta object for class '{@link org.lunifera.vaaclipse.ui.preferences.model.ComboFieldEditor <em>Combo Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Combo Field Editor</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.ComboFieldEditor
	 * @generated
	 */
	EClass getComboFieldEditor();

	/**
	 * Returns the meta object for class '{@link org.lunifera.vaaclipse.ui.preferences.model.ListEditor <em>List Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>List Editor</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.ListEditor
	 * @generated
	 */
	EClass getListEditor();

	/**
	 * Returns the meta object for class '{@link org.lunifera.vaaclipse.ui.preferences.model.RadioGroupFieldEditor <em>Radio Group Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Radio Group Field Editor</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.RadioGroupFieldEditor
	 * @generated
	 */
	EClass getRadioGroupFieldEditor();

	/**
	 * Returns the meta object for class '{@link org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor <em>Scale Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Scale Field Editor</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor
	 * @generated
	 */
	EClass getScaleFieldEditor();

	/**
	 * Returns the meta object for the attribute '{@link org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor#getMinValue <em>Min Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Value</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor#getMinValue()
	 * @see #getScaleFieldEditor()
	 * @generated
	 */
	EAttribute getScaleFieldEditor_MinValue();

	/**
	 * Returns the meta object for the attribute '{@link org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor#getMaxValue <em>Max Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Value</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor#getMaxValue()
	 * @see #getScaleFieldEditor()
	 * @generated
	 */
	EAttribute getScaleFieldEditor_MaxValue();

	/**
	 * Returns the meta object for the attribute '{@link org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor#getIncrementValue <em>Increment Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Increment Value</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor#getIncrementValue()
	 * @see #getScaleFieldEditor()
	 * @generated
	 */
	EAttribute getScaleFieldEditor_IncrementValue();

	/**
	 * Returns the meta object for class '{@link org.lunifera.vaaclipse.ui.preferences.model.StringFieldEditor <em>String Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Field Editor</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.StringFieldEditor
	 * @generated
	 */
	EClass getStringFieldEditor();

	/**
	 * Returns the meta object for class '{@link org.lunifera.vaaclipse.ui.preferences.model.IntegerFieldEditor <em>Integer Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Field Editor</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.IntegerFieldEditor
	 * @generated
	 */
	EClass getIntegerFieldEditor();

	/**
	 * Returns the meta object for the attribute '{@link org.lunifera.vaaclipse.ui.preferences.model.IntegerFieldEditor#getMinValidValue <em>Min Valid Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Valid Value</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.IntegerFieldEditor#getMinValidValue()
	 * @see #getIntegerFieldEditor()
	 * @generated
	 */
	EAttribute getIntegerFieldEditor_MinValidValue();

	/**
	 * Returns the meta object for the attribute '{@link org.lunifera.vaaclipse.ui.preferences.model.IntegerFieldEditor#getMaxValidValue <em>Max Valid Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Valid Value</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.IntegerFieldEditor#getMaxValidValue()
	 * @see #getIntegerFieldEditor()
	 * @generated
	 */
	EAttribute getIntegerFieldEditor_MaxValidValue();

	/**
	 * Returns the meta object for class '{@link org.lunifera.vaaclipse.ui.preferences.model.FileFieldEditor <em>File Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>File Field Editor</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.FileFieldEditor
	 * @generated
	 */
	EClass getFileFieldEditor();

	/**
	 * Returns the meta object for class '{@link org.lunifera.vaaclipse.ui.preferences.model.DirectoryFieldEditor <em>Directory Field Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Directory Field Editor</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.DirectoryFieldEditor
	 * @generated
	 */
	EClass getDirectoryFieldEditor();

	/**
	 * Returns the meta object for class '{@link org.lunifera.vaaclipse.ui.preferences.model.Entry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entry</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.Entry
	 * @generated
	 */
	EClass getEntry();

	/**
	 * Returns the meta object for the attribute '{@link org.lunifera.vaaclipse.ui.preferences.model.Entry#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.Entry#getName()
	 * @see #getEntry()
	 * @generated
	 */
	EAttribute getEntry_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.lunifera.vaaclipse.ui.preferences.model.Entry#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.Entry#getValue()
	 * @see #getEntry()
	 * @generated
	 */
	EAttribute getEntry_Value();

	/**
	 * Returns the meta object for enum '{@link org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldStyle <em>Boolean Field Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Boolean Field Style</em>'.
	 * @see org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldStyle
	 * @generated
	 */
	EEnum getBooleanFieldStyle();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PreferencesFactory getPreferencesFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesCategoryImpl <em>Category</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesCategoryImpl
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getPreferencesCategory()
		 * @generated
		 */
		EClass PREFERENCES_CATEGORY = eINSTANCE.getPreferencesCategory();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFERENCES_CATEGORY__NAME = eINSTANCE.getPreferencesCategory_Name();

		/**
		 * The meta object literal for the '<em><b>Child Categories</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PREFERENCES_CATEGORY__CHILD_CATEGORIES = eINSTANCE.getPreferencesCategory_ChildCategories();

		/**
		 * The meta object literal for the '<em><b>Parent Category</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PREFERENCES_CATEGORY__PARENT_CATEGORY = eINSTANCE.getPreferencesCategory_ParentCategory();

		/**
		 * The meta object literal for the '<em><b>Page</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PREFERENCES_CATEGORY__PAGE = eINSTANCE.getPreferencesCategory_Page();

		/**
		 * The meta object literal for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPageImpl <em>Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPageImpl
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getPreferencesPage()
		 * @generated
		 */
		EClass PREFERENCES_PAGE = eINSTANCE.getPreferencesPage();

		/**
		 * The meta object literal for the '<em><b>Category</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PREFERENCES_PAGE__CATEGORY = eINSTANCE.getPreferencesPage_Category();

		/**
		 * The meta object literal for the '<em><b>Preferences Scope</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFERENCES_PAGE__PREFERENCES_SCOPE = eINSTANCE.getPreferencesPage_PreferencesScope();

		/**
		 * The meta object literal for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.FieldEditorImpl <em>Field Editor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.FieldEditorImpl
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getFieldEditor()
		 * @generated
		 */
		EClass FIELD_EDITOR = eINSTANCE.getFieldEditor();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_EDITOR__LABEL = eINSTANCE.getFieldEditor_Label();

		/**
		 * The meta object literal for the '<em><b>Preference Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_EDITOR__PREFERENCE_NAME = eINSTANCE.getFieldEditor_PreferenceName();

		/**
		 * The meta object literal for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.BooleanFieldEditorImpl <em>Boolean Field Editor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.BooleanFieldEditorImpl
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getBooleanFieldEditor()
		 * @generated
		 */
		EClass BOOLEAN_FIELD_EDITOR = eINSTANCE.getBooleanFieldEditor();

		/**
		 * The meta object literal for the '<em><b>Style</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOLEAN_FIELD_EDITOR__STYLE = eINSTANCE.getBooleanFieldEditor_Style();

		/**
		 * The meta object literal for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.ListFieldEditorImpl <em>List Field Editor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.ListFieldEditorImpl
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getListFieldEditor()
		 * @generated
		 */
		EClass LIST_FIELD_EDITOR = eINSTANCE.getListFieldEditor();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIST_FIELD_EDITOR__ENTRIES = eINSTANCE.getListFieldEditor_Entries();

		/**
		 * The meta object literal for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.ComboFieldEditorImpl <em>Combo Field Editor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.ComboFieldEditorImpl
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getComboFieldEditor()
		 * @generated
		 */
		EClass COMBO_FIELD_EDITOR = eINSTANCE.getComboFieldEditor();

		/**
		 * The meta object literal for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.ListEditorImpl <em>List Editor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.ListEditorImpl
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getListEditor()
		 * @generated
		 */
		EClass LIST_EDITOR = eINSTANCE.getListEditor();

		/**
		 * The meta object literal for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.RadioGroupFieldEditorImpl <em>Radio Group Field Editor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.RadioGroupFieldEditorImpl
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getRadioGroupFieldEditor()
		 * @generated
		 */
		EClass RADIO_GROUP_FIELD_EDITOR = eINSTANCE.getRadioGroupFieldEditor();

		/**
		 * The meta object literal for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.ScaleFieldEditorImpl <em>Scale Field Editor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.ScaleFieldEditorImpl
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getScaleFieldEditor()
		 * @generated
		 */
		EClass SCALE_FIELD_EDITOR = eINSTANCE.getScaleFieldEditor();

		/**
		 * The meta object literal for the '<em><b>Min Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCALE_FIELD_EDITOR__MIN_VALUE = eINSTANCE.getScaleFieldEditor_MinValue();

		/**
		 * The meta object literal for the '<em><b>Max Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCALE_FIELD_EDITOR__MAX_VALUE = eINSTANCE.getScaleFieldEditor_MaxValue();

		/**
		 * The meta object literal for the '<em><b>Increment Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCALE_FIELD_EDITOR__INCREMENT_VALUE = eINSTANCE.getScaleFieldEditor_IncrementValue();

		/**
		 * The meta object literal for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.StringFieldEditorImpl <em>String Field Editor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.StringFieldEditorImpl
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getStringFieldEditor()
		 * @generated
		 */
		EClass STRING_FIELD_EDITOR = eINSTANCE.getStringFieldEditor();

		/**
		 * The meta object literal for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.IntegerFieldEditorImpl <em>Integer Field Editor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.IntegerFieldEditorImpl
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getIntegerFieldEditor()
		 * @generated
		 */
		EClass INTEGER_FIELD_EDITOR = eINSTANCE.getIntegerFieldEditor();

		/**
		 * The meta object literal for the '<em><b>Min Valid Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTEGER_FIELD_EDITOR__MIN_VALID_VALUE = eINSTANCE.getIntegerFieldEditor_MinValidValue();

		/**
		 * The meta object literal for the '<em><b>Max Valid Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTEGER_FIELD_EDITOR__MAX_VALID_VALUE = eINSTANCE.getIntegerFieldEditor_MaxValidValue();

		/**
		 * The meta object literal for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.FileFieldEditorImpl <em>File Field Editor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.FileFieldEditorImpl
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getFileFieldEditor()
		 * @generated
		 */
		EClass FILE_FIELD_EDITOR = eINSTANCE.getFileFieldEditor();

		/**
		 * The meta object literal for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.DirectoryFieldEditorImpl <em>Directory Field Editor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.DirectoryFieldEditorImpl
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getDirectoryFieldEditor()
		 * @generated
		 */
		EClass DIRECTORY_FIELD_EDITOR = eINSTANCE.getDirectoryFieldEditor();

		/**
		 * The meta object literal for the '{@link org.lunifera.vaaclipse.ui.preferences.model.impl.EntryImpl <em>Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.EntryImpl
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getEntry()
		 * @generated
		 */
		EClass ENTRY = eINSTANCE.getEntry();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTRY__NAME = eINSTANCE.getEntry_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTRY__VALUE = eINSTANCE.getEntry_Value();

		/**
		 * The meta object literal for the '{@link org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldStyle <em>Boolean Field Style</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldStyle
		 * @see org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesPackageImpl#getBooleanFieldStyle()
		 * @generated
		 */
		EEnum BOOLEAN_FIELD_STYLE = eINSTANCE.getBooleanFieldStyle();

	}

} //PreferencesPackage
