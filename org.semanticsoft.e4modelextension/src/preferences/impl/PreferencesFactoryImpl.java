/**
 */
package preferences.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import preferences.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PreferencesFactoryImpl extends EFactoryImpl implements PreferencesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PreferencesFactory init() {
		try {
			PreferencesFactory thePreferencesFactory = (PreferencesFactory)EPackage.Registry.INSTANCE.getEFactory(PreferencesPackage.eNS_URI);
			if (thePreferencesFactory != null) {
				return thePreferencesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PreferencesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreferencesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case PreferencesPackage.PREFERENCES_CATEGORY: return createPreferencesCategory();
			case PreferencesPackage.PREFERENCES_PAGE: return createPreferencesPage();
			case PreferencesPackage.FIELD_EDITOR: return createFieldEditor();
			case PreferencesPackage.BOOLEAN_FIELD_EDITOR: return createBooleanFieldEditor();
			case PreferencesPackage.ENTRY: return createEntry();
			case PreferencesPackage.COMBO_FIELD_EDITOR: return createComboFieldEditor();
			case PreferencesPackage.LIST_EDITOR: return createListEditor();
			case PreferencesPackage.RADIO_GROUP_FIELD_EDITOR: return createRadioGroupFieldEditor();
			case PreferencesPackage.SCALE_FIELD_EDITOR: return createScaleFieldEditor();
			case PreferencesPackage.STRING_FIELD_EDITOR: return createStringFieldEditor();
			case PreferencesPackage.INTEGER_FIELD_EDITOR: return createIntegerFieldEditor();
			case PreferencesPackage.FILE_FIELD_EDITOR: return createFileFieldEditor();
			case PreferencesPackage.DIRECTORY_FIELD_EDITOR: return createDirectoryFieldEditor();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case PreferencesPackage.BOOLEAN_FIELD_STYLE:
				return createBooleanFieldStyleFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case PreferencesPackage.BOOLEAN_FIELD_STYLE:
				return convertBooleanFieldStyleToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreferencesCategory createPreferencesCategory() {
		PreferencesCategoryImpl preferencesCategory = new PreferencesCategoryImpl();
		return preferencesCategory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreferencesPage createPreferencesPage() {
		PreferencesPageImpl preferencesPage = new PreferencesPageImpl();
		return preferencesPage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <T> FieldEditor<T> createFieldEditor() {
		FieldEditorImpl<T> fieldEditor = new FieldEditorImpl<T>();
		return fieldEditor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanFieldEditor createBooleanFieldEditor() {
		BooleanFieldEditorImpl booleanFieldEditor = new BooleanFieldEditorImpl();
		return booleanFieldEditor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Entry createEntry() {
		EntryImpl entry = new EntryImpl();
		return entry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComboFieldEditor createComboFieldEditor() {
		ComboFieldEditorImpl comboFieldEditor = new ComboFieldEditorImpl();
		return comboFieldEditor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ListEditor createListEditor() {
		ListEditorImpl listEditor = new ListEditorImpl();
		return listEditor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RadioGroupFieldEditor createRadioGroupFieldEditor() {
		RadioGroupFieldEditorImpl radioGroupFieldEditor = new RadioGroupFieldEditorImpl();
		return radioGroupFieldEditor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScaleFieldEditor createScaleFieldEditor() {
		ScaleFieldEditorImpl scaleFieldEditor = new ScaleFieldEditorImpl();
		return scaleFieldEditor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringFieldEditor createStringFieldEditor() {
		StringFieldEditorImpl stringFieldEditor = new StringFieldEditorImpl();
		return stringFieldEditor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerFieldEditor createIntegerFieldEditor() {
		IntegerFieldEditorImpl integerFieldEditor = new IntegerFieldEditorImpl();
		return integerFieldEditor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FileFieldEditor createFileFieldEditor() {
		FileFieldEditorImpl fileFieldEditor = new FileFieldEditorImpl();
		return fileFieldEditor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DirectoryFieldEditor createDirectoryFieldEditor() {
		DirectoryFieldEditorImpl directoryFieldEditor = new DirectoryFieldEditorImpl();
		return directoryFieldEditor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanFieldStyle createBooleanFieldStyleFromString(EDataType eDataType, String initialValue) {
		BooleanFieldStyle result = BooleanFieldStyle.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBooleanFieldStyleToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreferencesPackage getPreferencesPackage() {
		return (PreferencesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PreferencesPackage getPackage() {
		return PreferencesPackage.eINSTANCE;
	}

} //PreferencesFactoryImpl
