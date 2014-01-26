/**
 */
package preferences.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import preferences.PreferencesPackage;
import preferences.ScaleFieldEditor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Scale Field Editor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link preferences.impl.ScaleFieldEditorImpl#getMinValue <em>Min Value</em>}</li>
 *   <li>{@link preferences.impl.ScaleFieldEditorImpl#getMaxValue <em>Max Value</em>}</li>
 *   <li>{@link preferences.impl.ScaleFieldEditorImpl#getIncrementValue <em>Increment Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ScaleFieldEditorImpl extends FieldEditorImpl<Integer> implements ScaleFieldEditor {
	/**
	 * The default value of the '{@link #getMinValue() <em>Min Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinValue()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MIN_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMinValue() <em>Min Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinValue()
	 * @generated
	 * @ordered
	 */
	protected Integer minValue = MIN_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaxValue() <em>Max Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxValue()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MAX_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMaxValue() <em>Max Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxValue()
	 * @generated
	 * @ordered
	 */
	protected Integer maxValue = MAX_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getIncrementValue() <em>Increment Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncrementValue()
	 * @generated
	 * @ordered
	 */
	protected static final Integer INCREMENT_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIncrementValue() <em>Increment Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncrementValue()
	 * @generated
	 * @ordered
	 */
	protected Integer incrementValue = INCREMENT_VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ScaleFieldEditorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PreferencesPackage.Literals.SCALE_FIELD_EDITOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getMinValue() {
		return minValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinValue(Integer newMinValue) {
		Integer oldMinValue = minValue;
		minValue = newMinValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.SCALE_FIELD_EDITOR__MIN_VALUE, oldMinValue, minValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getMaxValue() {
		return maxValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxValue(Integer newMaxValue) {
		Integer oldMaxValue = maxValue;
		maxValue = newMaxValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.SCALE_FIELD_EDITOR__MAX_VALUE, oldMaxValue, maxValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getIncrementValue() {
		return incrementValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIncrementValue(Integer newIncrementValue) {
		Integer oldIncrementValue = incrementValue;
		incrementValue = newIncrementValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.SCALE_FIELD_EDITOR__INCREMENT_VALUE, oldIncrementValue, incrementValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PreferencesPackage.SCALE_FIELD_EDITOR__MIN_VALUE:
				return getMinValue();
			case PreferencesPackage.SCALE_FIELD_EDITOR__MAX_VALUE:
				return getMaxValue();
			case PreferencesPackage.SCALE_FIELD_EDITOR__INCREMENT_VALUE:
				return getIncrementValue();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PreferencesPackage.SCALE_FIELD_EDITOR__MIN_VALUE:
				setMinValue((Integer)newValue);
				return;
			case PreferencesPackage.SCALE_FIELD_EDITOR__MAX_VALUE:
				setMaxValue((Integer)newValue);
				return;
			case PreferencesPackage.SCALE_FIELD_EDITOR__INCREMENT_VALUE:
				setIncrementValue((Integer)newValue);
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
			case PreferencesPackage.SCALE_FIELD_EDITOR__MIN_VALUE:
				setMinValue(MIN_VALUE_EDEFAULT);
				return;
			case PreferencesPackage.SCALE_FIELD_EDITOR__MAX_VALUE:
				setMaxValue(MAX_VALUE_EDEFAULT);
				return;
			case PreferencesPackage.SCALE_FIELD_EDITOR__INCREMENT_VALUE:
				setIncrementValue(INCREMENT_VALUE_EDEFAULT);
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
			case PreferencesPackage.SCALE_FIELD_EDITOR__MIN_VALUE:
				return MIN_VALUE_EDEFAULT == null ? minValue != null : !MIN_VALUE_EDEFAULT.equals(minValue);
			case PreferencesPackage.SCALE_FIELD_EDITOR__MAX_VALUE:
				return MAX_VALUE_EDEFAULT == null ? maxValue != null : !MAX_VALUE_EDEFAULT.equals(maxValue);
			case PreferencesPackage.SCALE_FIELD_EDITOR__INCREMENT_VALUE:
				return INCREMENT_VALUE_EDEFAULT == null ? incrementValue != null : !INCREMENT_VALUE_EDEFAULT.equals(incrementValue);
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
		result.append(" (minValue: ");
		result.append(minValue);
		result.append(", maxValue: ");
		result.append(maxValue);
		result.append(", incrementValue: ");
		result.append(incrementValue);
		result.append(')');
		return result.toString();
	}

} //ScaleFieldEditorImpl
