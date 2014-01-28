/**
 */
package org.lunifera.vaaclipse.ui.preferences.model.impl;

import org.eclipse.e4.ui.model.application.impl.ApplicationPackageImpl;

import org.eclipse.e4.ui.model.application.ui.impl.UiPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.BooleanFieldStyle;
import org.lunifera.vaaclipse.ui.preferences.model.ComboFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.DirectoryFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.Entry;
import org.lunifera.vaaclipse.ui.preferences.model.FieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.FileFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.IntegerFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.ListCrud;
import org.lunifera.vaaclipse.ui.preferences.model.ListEditor;
import org.lunifera.vaaclipse.ui.preferences.model.ListFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.ListFold;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;
import org.lunifera.vaaclipse.ui.preferences.model.RadioGroupFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.StringFieldEditor;

import org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesFactory;
import org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage;

import org.osgi.service.prefs.Preferences;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PreferencesPackageImpl extends EPackageImpl implements PreferencesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass preferencesCategoryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass preferencesPageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fieldEditorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanFieldEditorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass listFieldEditorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass comboFieldEditorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass listEditorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass radioGroupFieldEditorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass scaleFieldEditorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringFieldEditorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integerFieldEditorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fileFieldEditorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass directoryFieldEditorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass listCrudEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass listFoldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum booleanFieldStyleEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType stringBufferEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType preferencesEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PreferencesPackageImpl() {
		super(eNS_URI, PreferencesFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link PreferencesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PreferencesPackage init() {
		if (isInited) return (PreferencesPackage)EPackage.Registry.INSTANCE.getEPackage(PreferencesPackage.eNS_URI);

		// Obtain or create and register package
		PreferencesPackageImpl thePreferencesPackage = (PreferencesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof PreferencesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new PreferencesPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ApplicationPackageImpl.eINSTANCE.eClass();

		// Create package meta-data objects
		thePreferencesPackage.createPackageContents();

		// Initialize created meta-data
		thePreferencesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePreferencesPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PreferencesPackage.eNS_URI, thePreferencesPackage);
		return thePreferencesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPreferencesCategory() {
		return preferencesCategoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPreferencesCategory_Name() {
		return (EAttribute)preferencesCategoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPreferencesCategory_ChildCategories() {
		return (EReference)preferencesCategoryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPreferencesCategory_ParentCategory() {
		return (EReference)preferencesCategoryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPreferencesCategory_Page() {
		return (EReference)preferencesCategoryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPreferencesCategory_Id() {
		return (EAttribute)preferencesCategoryEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPreferencesPage() {
		return preferencesPageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPreferencesPage_Category() {
		return (EReference)preferencesPageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPreferencesPage_Description() {
		return (EAttribute)preferencesPageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFieldEditor() {
		return fieldEditorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFieldEditor_Label() {
		return (EAttribute)fieldEditorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFieldEditor_PreferenceName() {
		return (EAttribute)fieldEditorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFieldEditor_DefaultValue() {
		return (EAttribute)fieldEditorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFieldEditor_DefaultValueTyped() {
		return (EAttribute)fieldEditorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFieldEditor_Bundle() {
		return (EAttribute)fieldEditorEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFieldEditor_Preferences() {
		return (EAttribute)fieldEditorEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanFieldEditor() {
		return booleanFieldEditorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBooleanFieldEditor_Style() {
		return (EAttribute)booleanFieldEditorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getListFieldEditor() {
		return listFieldEditorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getListFieldEditor_Entries() {
		return (EReference)listFieldEditorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEntry() {
		return entryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntry_Name() {
		return (EAttribute)entryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntry_Value() {
		return (EAttribute)entryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComboFieldEditor() {
		return comboFieldEditorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getListEditor() {
		return listEditorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getListEditor_ListCrud() {
		return (EReference)listEditorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getListEditor_ListFold() {
		return (EReference)listEditorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRadioGroupFieldEditor() {
		return radioGroupFieldEditorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getScaleFieldEditor() {
		return scaleFieldEditorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScaleFieldEditor_MinValue() {
		return (EAttribute)scaleFieldEditorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScaleFieldEditor_MaxValue() {
		return (EAttribute)scaleFieldEditorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScaleFieldEditor_IncrementValue() {
		return (EAttribute)scaleFieldEditorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringFieldEditor() {
		return stringFieldEditorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringFieldEditor_MaxLength() {
		return (EAttribute)stringFieldEditorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntegerFieldEditor() {
		return integerFieldEditorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntegerFieldEditor_MinValidValue() {
		return (EAttribute)integerFieldEditorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntegerFieldEditor_MaxValidValue() {
		return (EAttribute)integerFieldEditorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFileFieldEditor() {
		return fileFieldEditorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDirectoryFieldEditor() {
		return directoryFieldEditorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getListCrud() {
		return listCrudEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getListCrud__AddNewValue__String() {
		return listCrudEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getListFold() {
		return listFoldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getListFold__Apply__String_StringBuffer() {
		return listFoldEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getBooleanFieldStyle() {
		return booleanFieldStyleEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getStringBuffer() {
		return stringBufferEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPreferences() {
		return preferencesEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreferencesFactory getPreferencesFactory() {
		return (PreferencesFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		preferencesCategoryEClass = createEClass(PREFERENCES_CATEGORY);
		createEAttribute(preferencesCategoryEClass, PREFERENCES_CATEGORY__NAME);
		createEReference(preferencesCategoryEClass, PREFERENCES_CATEGORY__CHILD_CATEGORIES);
		createEReference(preferencesCategoryEClass, PREFERENCES_CATEGORY__PARENT_CATEGORY);
		createEReference(preferencesCategoryEClass, PREFERENCES_CATEGORY__PAGE);
		createEAttribute(preferencesCategoryEClass, PREFERENCES_CATEGORY__ID);

		preferencesPageEClass = createEClass(PREFERENCES_PAGE);
		createEReference(preferencesPageEClass, PREFERENCES_PAGE__CATEGORY);
		createEAttribute(preferencesPageEClass, PREFERENCES_PAGE__DESCRIPTION);

		fieldEditorEClass = createEClass(FIELD_EDITOR);
		createEAttribute(fieldEditorEClass, FIELD_EDITOR__LABEL);
		createEAttribute(fieldEditorEClass, FIELD_EDITOR__PREFERENCE_NAME);
		createEAttribute(fieldEditorEClass, FIELD_EDITOR__DEFAULT_VALUE);
		createEAttribute(fieldEditorEClass, FIELD_EDITOR__DEFAULT_VALUE_TYPED);
		createEAttribute(fieldEditorEClass, FIELD_EDITOR__BUNDLE);
		createEAttribute(fieldEditorEClass, FIELD_EDITOR__PREFERENCES);

		booleanFieldEditorEClass = createEClass(BOOLEAN_FIELD_EDITOR);
		createEAttribute(booleanFieldEditorEClass, BOOLEAN_FIELD_EDITOR__STYLE);

		listFieldEditorEClass = createEClass(LIST_FIELD_EDITOR);
		createEReference(listFieldEditorEClass, LIST_FIELD_EDITOR__ENTRIES);

		entryEClass = createEClass(ENTRY);
		createEAttribute(entryEClass, ENTRY__NAME);
		createEAttribute(entryEClass, ENTRY__VALUE);

		comboFieldEditorEClass = createEClass(COMBO_FIELD_EDITOR);

		listEditorEClass = createEClass(LIST_EDITOR);
		createEReference(listEditorEClass, LIST_EDITOR__LIST_CRUD);
		createEReference(listEditorEClass, LIST_EDITOR__LIST_FOLD);

		radioGroupFieldEditorEClass = createEClass(RADIO_GROUP_FIELD_EDITOR);

		scaleFieldEditorEClass = createEClass(SCALE_FIELD_EDITOR);
		createEAttribute(scaleFieldEditorEClass, SCALE_FIELD_EDITOR__MIN_VALUE);
		createEAttribute(scaleFieldEditorEClass, SCALE_FIELD_EDITOR__MAX_VALUE);
		createEAttribute(scaleFieldEditorEClass, SCALE_FIELD_EDITOR__INCREMENT_VALUE);

		stringFieldEditorEClass = createEClass(STRING_FIELD_EDITOR);
		createEAttribute(stringFieldEditorEClass, STRING_FIELD_EDITOR__MAX_LENGTH);

		integerFieldEditorEClass = createEClass(INTEGER_FIELD_EDITOR);
		createEAttribute(integerFieldEditorEClass, INTEGER_FIELD_EDITOR__MIN_VALID_VALUE);
		createEAttribute(integerFieldEditorEClass, INTEGER_FIELD_EDITOR__MAX_VALID_VALUE);

		fileFieldEditorEClass = createEClass(FILE_FIELD_EDITOR);

		directoryFieldEditorEClass = createEClass(DIRECTORY_FIELD_EDITOR);

		listCrudEClass = createEClass(LIST_CRUD);
		createEOperation(listCrudEClass, LIST_CRUD___ADD_NEW_VALUE__STRING);

		listFoldEClass = createEClass(LIST_FOLD);
		createEOperation(listFoldEClass, LIST_FOLD___APPLY__STRING_STRINGBUFFER);

		// Create enums
		booleanFieldStyleEEnum = createEEnum(BOOLEAN_FIELD_STYLE);

		// Create data types
		stringBufferEDataType = createEDataType(STRING_BUFFER);
		preferencesEDataType = createEDataType(PREFERENCES);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		ApplicationPackageImpl theApplicationPackage = (ApplicationPackageImpl)EPackage.Registry.INSTANCE.getEPackage(ApplicationPackageImpl.eNS_URI);
		UiPackageImpl theUiPackage = (UiPackageImpl)EPackage.Registry.INSTANCE.getEPackage(UiPackageImpl.eNS_URI);

		// Create type parameters
		ETypeParameter fieldEditorEClass_T = addETypeParameter(fieldEditorEClass, "T");

		// Set bounds for type parameters

		// Add supertypes to classes
		preferencesCategoryEClass.getESuperTypes().add(theApplicationPackage.getApplicationElement());
		EGenericType g1 = createEGenericType(theUiPackage.getElementContainer());
		EGenericType g2 = createEGenericType(this.getFieldEditor());
		g1.getETypeArguments().add(g2);
		EGenericType g3 = createEGenericType();
		g2.getETypeArguments().add(g3);
		preferencesPageEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theApplicationPackage.getContribution());
		preferencesPageEClass.getEGenericSuperTypes().add(g1);
		fieldEditorEClass.getESuperTypes().add(theUiPackage.getUIElement());
		g1 = createEGenericType(this.getFieldEditor());
		g2 = createEGenericType(ecorePackage.getEBooleanObject());
		g1.getETypeArguments().add(g2);
		booleanFieldEditorEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getFieldEditor());
		g2 = createEGenericType(ecorePackage.getEString());
		g1.getETypeArguments().add(g2);
		listFieldEditorEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theApplicationPackage.getContribution());
		listFieldEditorEClass.getEGenericSuperTypes().add(g1);
		comboFieldEditorEClass.getESuperTypes().add(this.getListFieldEditor());
		g1 = createEGenericType(this.getFieldEditor());
		g2 = createEGenericType(ecorePackage.getEString());
		g1.getETypeArguments().add(g2);
		listEditorEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theApplicationPackage.getContribution());
		listEditorEClass.getEGenericSuperTypes().add(g1);
		radioGroupFieldEditorEClass.getESuperTypes().add(this.getListFieldEditor());
		g1 = createEGenericType(this.getFieldEditor());
		g2 = createEGenericType(ecorePackage.getEIntegerObject());
		g1.getETypeArguments().add(g2);
		scaleFieldEditorEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getFieldEditor());
		g2 = createEGenericType(ecorePackage.getEString());
		g1.getETypeArguments().add(g2);
		stringFieldEditorEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getFieldEditor());
		g2 = createEGenericType(ecorePackage.getEIntegerObject());
		g1.getETypeArguments().add(g2);
		integerFieldEditorEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getFieldEditor());
		g2 = createEGenericType(ecorePackage.getEString());
		g1.getETypeArguments().add(g2);
		fileFieldEditorEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theApplicationPackage.getContribution());
		fileFieldEditorEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getFieldEditor());
		g2 = createEGenericType(ecorePackage.getEString());
		g1.getETypeArguments().add(g2);
		directoryFieldEditorEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theApplicationPackage.getContribution());
		directoryFieldEditorEClass.getEGenericSuperTypes().add(g1);

		// Initialize classes, features, and operations; add parameters
		initEClass(preferencesCategoryEClass, PreferencesCategory.class, "PreferencesCategory", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPreferencesCategory_Name(), ecorePackage.getEString(), "name", "No Name", 0, 1, PreferencesCategory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPreferencesCategory_ChildCategories(), this.getPreferencesCategory(), this.getPreferencesCategory_ParentCategory(), "childCategories", null, 0, -1, PreferencesCategory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPreferencesCategory_ParentCategory(), this.getPreferencesCategory(), this.getPreferencesCategory_ChildCategories(), "parentCategory", null, 0, 1, PreferencesCategory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPreferencesCategory_Page(), this.getPreferencesPage(), this.getPreferencesPage_Category(), "page", null, 0, 1, PreferencesCategory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPreferencesCategory_Id(), ecorePackage.getEString(), "id", null, 0, 1, PreferencesCategory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(preferencesPageEClass, PreferencesPage.class, "PreferencesPage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPreferencesPage_Category(), this.getPreferencesCategory(), this.getPreferencesCategory_Page(), "category", null, 0, 1, PreferencesPage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPreferencesPage_Description(), ecorePackage.getEString(), "description", null, 0, 1, PreferencesPage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fieldEditorEClass, FieldEditor.class, "FieldEditor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFieldEditor_Label(), ecorePackage.getEString(), "label", "", 0, 1, FieldEditor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldEditor_PreferenceName(), ecorePackage.getEString(), "preferenceName", null, 0, 1, FieldEditor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldEditor_DefaultValue(), ecorePackage.getEString(), "defaultValue", null, 0, 1, FieldEditor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(fieldEditorEClass_T);
		initEAttribute(getFieldEditor_DefaultValueTyped(), g1, "defaultValueTyped", null, 0, 1, FieldEditor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldEditor_Bundle(), ecorePackage.getEString(), "bundle", null, 0, 1, FieldEditor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFieldEditor_Preferences(), this.getPreferences(), "preferences", null, 0, 1, FieldEditor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(booleanFieldEditorEClass, BooleanFieldEditor.class, "BooleanFieldEditor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBooleanFieldEditor_Style(), this.getBooleanFieldStyle(), "style", null, 0, 1, BooleanFieldEditor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(listFieldEditorEClass, ListFieldEditor.class, "ListFieldEditor", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getListFieldEditor_Entries(), this.getEntry(), null, "entries", null, 0, -1, ListFieldEditor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entryEClass, Entry.class, "Entry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEntry_Name(), ecorePackage.getEString(), "name", null, 0, 1, Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntry_Value(), ecorePackage.getEString(), "value", null, 0, 1, Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(comboFieldEditorEClass, ComboFieldEditor.class, "ComboFieldEditor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(listEditorEClass, ListEditor.class, "ListEditor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getListEditor_ListCrud(), this.getListCrud(), null, "listCrud", null, 0, 1, ListEditor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getListEditor_ListFold(), this.getListFold(), null, "listFold", null, 0, 1, ListEditor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(radioGroupFieldEditorEClass, RadioGroupFieldEditor.class, "RadioGroupFieldEditor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(scaleFieldEditorEClass, ScaleFieldEditor.class, "ScaleFieldEditor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getScaleFieldEditor_MinValue(), ecorePackage.getEIntegerObject(), "minValue", "0", 0, 1, ScaleFieldEditor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScaleFieldEditor_MaxValue(), ecorePackage.getEIntegerObject(), "maxValue", "100", 0, 1, ScaleFieldEditor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScaleFieldEditor_IncrementValue(), ecorePackage.getEIntegerObject(), "incrementValue", "1", 0, 1, ScaleFieldEditor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringFieldEditorEClass, StringFieldEditor.class, "StringFieldEditor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringFieldEditor_MaxLength(), ecorePackage.getEIntegerObject(), "maxLength", null, 0, 1, StringFieldEditor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(integerFieldEditorEClass, IntegerFieldEditor.class, "IntegerFieldEditor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntegerFieldEditor_MinValidValue(), ecorePackage.getEIntegerObject(), "minValidValue", null, 0, 1, IntegerFieldEditor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIntegerFieldEditor_MaxValidValue(), ecorePackage.getEIntegerObject(), "maxValidValue", null, 0, 1, IntegerFieldEditor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fileFieldEditorEClass, FileFieldEditor.class, "FileFieldEditor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(directoryFieldEditorEClass, DirectoryFieldEditor.class, "DirectoryFieldEditor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(listCrudEClass, ListCrud.class, "ListCrud", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		EOperation op = initEOperation(getListCrud__AddNewValue__String(), ecorePackage.getEString(), "addNewValue", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "values", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(listFoldEClass, ListFold.class, "ListFold", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = initEOperation(getListFold__Apply__String_StringBuffer(), null, "apply", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "value", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getStringBuffer(), "prev", 0, 1, IS_UNIQUE, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(booleanFieldStyleEEnum, BooleanFieldStyle.class, "BooleanFieldStyle");
		addEEnumLiteral(booleanFieldStyleEEnum, BooleanFieldStyle.DEFAULT);
		addEEnumLiteral(booleanFieldStyleEEnum, BooleanFieldStyle.SEPARATE_LABEL);

		// Initialize data types
		initEDataType(stringBufferEDataType, StringBuffer.class, "StringBuffer", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(preferencesEDataType, Preferences.class, "Preferences", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //PreferencesPackageImpl
