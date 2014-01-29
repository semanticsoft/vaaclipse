/**
 */
package org.lunifera.vaaclipse.ui.preferences.model.impl;

import org.eclipse.e4.ui.model.application.ui.impl.UIElementImpl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.lunifera.vaaclipse.ui.preferences.model.FieldEditor;

import org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage;

import org.osgi.service.prefs.Preferences;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Field Editor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.FieldEditorImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.FieldEditorImpl#getPreferenceName <em>Preference Name</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.FieldEditorImpl#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.FieldEditorImpl#getDefaultValueTyped <em>Default Value Typed</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.FieldEditorImpl#getBundle <em>Bundle</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.FieldEditorImpl#getPreferences <em>Preferences</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.FieldEditorImpl#getEquinoxPath <em>Equinox Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FieldEditorImpl<T> extends UIElementImpl implements FieldEditor<T> {
	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getPreferenceName() <em>Preference Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreferenceName()
	 * @generated
	 * @ordered
	 */
	protected static final String PREFERENCE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPreferenceName() <em>Preference Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreferenceName()
	 * @generated
	 * @ordered
	 */
	protected String preferenceName = PREFERENCE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValue()
	 * @generated
	 * @ordered
	 */
	protected String defaultValue = DEFAULT_VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDefaultValueTyped() <em>Default Value Typed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValueTyped()
	 * @generated
	 * @ordered
	 */
	protected T defaultValueTyped;

	/**
	 * The default value of the '{@link #getBundle() <em>Bundle</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBundle()
	 * @generated
	 * @ordered
	 */
	protected static final String BUNDLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBundle() <em>Bundle</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBundle()
	 * @generated
	 * @ordered
	 */
	protected String bundle = BUNDLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPreferences() <em>Preferences</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreferences()
	 * @generated
	 * @ordered
	 */
	protected static final Preferences PREFERENCES_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPreferences() <em>Preferences</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreferences()
	 * @generated
	 * @ordered
	 */
	protected Preferences preferences = PREFERENCES_EDEFAULT;

	/**
	 * The default value of the '{@link #getEquinoxPath() <em>Equinox Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEquinoxPath()
	 * @generated
	 * @ordered
	 */
	protected static final String EQUINOX_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEquinoxPath() <em>Equinox Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEquinoxPath()
	 * @generated
	 * @ordered
	 */
	protected String equinoxPath = EQUINOX_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FieldEditorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PreferencesPackage.Literals.FIELD_EDITOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.FIELD_EDITOR__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPreferenceName() {
		return preferenceName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreferenceName(String newPreferenceName) {
		String oldPreferenceName = preferenceName;
		preferenceName = newPreferenceName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.FIELD_EDITOR__PREFERENCE_NAME, oldPreferenceName, preferenceName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultValue(String newDefaultValue) {
		String oldDefaultValue = defaultValue;
		defaultValue = newDefaultValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.FIELD_EDITOR__DEFAULT_VALUE, oldDefaultValue, defaultValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public T getDefaultValueTyped() {
		return defaultValueTyped;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultValueTyped(T newDefaultValueTyped) {
		T oldDefaultValueTyped = defaultValueTyped;
		defaultValueTyped = newDefaultValueTyped;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.FIELD_EDITOR__DEFAULT_VALUE_TYPED, oldDefaultValueTyped, defaultValueTyped));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBundle() {
		return bundle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBundle(String newBundle) {
		String oldBundle = bundle;
		bundle = newBundle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.FIELD_EDITOR__BUNDLE, oldBundle, bundle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Preferences getPreferences() {
		return preferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreferences(Preferences newPreferences) {
		Preferences oldPreferences = preferences;
		preferences = newPreferences;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.FIELD_EDITOR__PREFERENCES, oldPreferences, preferences));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEquinoxPath() {
		return equinoxPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEquinoxPath(String newEquinoxPath) {
		String oldEquinoxPath = equinoxPath;
		equinoxPath = newEquinoxPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.FIELD_EDITOR__EQUINOX_PATH, oldEquinoxPath, equinoxPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PreferencesPackage.FIELD_EDITOR__LABEL:
				return getLabel();
			case PreferencesPackage.FIELD_EDITOR__PREFERENCE_NAME:
				return getPreferenceName();
			case PreferencesPackage.FIELD_EDITOR__DEFAULT_VALUE:
				return getDefaultValue();
			case PreferencesPackage.FIELD_EDITOR__DEFAULT_VALUE_TYPED:
				return getDefaultValueTyped();
			case PreferencesPackage.FIELD_EDITOR__BUNDLE:
				return getBundle();
			case PreferencesPackage.FIELD_EDITOR__PREFERENCES:
				return getPreferences();
			case PreferencesPackage.FIELD_EDITOR__EQUINOX_PATH:
				return getEquinoxPath();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PreferencesPackage.FIELD_EDITOR__LABEL:
				setLabel((String)newValue);
				return;
			case PreferencesPackage.FIELD_EDITOR__PREFERENCE_NAME:
				setPreferenceName((String)newValue);
				return;
			case PreferencesPackage.FIELD_EDITOR__DEFAULT_VALUE:
				setDefaultValue((String)newValue);
				return;
			case PreferencesPackage.FIELD_EDITOR__DEFAULT_VALUE_TYPED:
				setDefaultValueTyped((T)newValue);
				return;
			case PreferencesPackage.FIELD_EDITOR__BUNDLE:
				setBundle((String)newValue);
				return;
			case PreferencesPackage.FIELD_EDITOR__PREFERENCES:
				setPreferences((Preferences)newValue);
				return;
			case PreferencesPackage.FIELD_EDITOR__EQUINOX_PATH:
				setEquinoxPath((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case PreferencesPackage.FIELD_EDITOR__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case PreferencesPackage.FIELD_EDITOR__PREFERENCE_NAME:
				setPreferenceName(PREFERENCE_NAME_EDEFAULT);
				return;
			case PreferencesPackage.FIELD_EDITOR__DEFAULT_VALUE:
				setDefaultValue(DEFAULT_VALUE_EDEFAULT);
				return;
			case PreferencesPackage.FIELD_EDITOR__DEFAULT_VALUE_TYPED:
				setDefaultValueTyped((T)null);
				return;
			case PreferencesPackage.FIELD_EDITOR__BUNDLE:
				setBundle(BUNDLE_EDEFAULT);
				return;
			case PreferencesPackage.FIELD_EDITOR__PREFERENCES:
				setPreferences(PREFERENCES_EDEFAULT);
				return;
			case PreferencesPackage.FIELD_EDITOR__EQUINOX_PATH:
				setEquinoxPath(EQUINOX_PATH_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PreferencesPackage.FIELD_EDITOR__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case PreferencesPackage.FIELD_EDITOR__PREFERENCE_NAME:
				return PREFERENCE_NAME_EDEFAULT == null ? preferenceName != null : !PREFERENCE_NAME_EDEFAULT.equals(preferenceName);
			case PreferencesPackage.FIELD_EDITOR__DEFAULT_VALUE:
				return DEFAULT_VALUE_EDEFAULT == null ? defaultValue != null : !DEFAULT_VALUE_EDEFAULT.equals(defaultValue);
			case PreferencesPackage.FIELD_EDITOR__DEFAULT_VALUE_TYPED:
				return defaultValueTyped != null;
			case PreferencesPackage.FIELD_EDITOR__BUNDLE:
				return BUNDLE_EDEFAULT == null ? bundle != null : !BUNDLE_EDEFAULT.equals(bundle);
			case PreferencesPackage.FIELD_EDITOR__PREFERENCES:
				return PREFERENCES_EDEFAULT == null ? preferences != null : !PREFERENCES_EDEFAULT.equals(preferences);
			case PreferencesPackage.FIELD_EDITOR__EQUINOX_PATH:
				return EQUINOX_PATH_EDEFAULT == null ? equinoxPath != null : !EQUINOX_PATH_EDEFAULT.equals(equinoxPath);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (label: ");
		result.append(label);
		result.append(", preferenceName: ");
		result.append(preferenceName);
		result.append(", defaultValue: ");
		result.append(defaultValue);
		result.append(", defaultValueTyped: ");
		result.append(defaultValueTyped);
		result.append(", bundle: ");
		result.append(bundle);
		result.append(", preferences: ");
		result.append(preferences);
		result.append(", equinoxPath: ");
		result.append(equinoxPath);
		result.append(')');
		return result.toString();
	}

} //FieldEditorImpl
