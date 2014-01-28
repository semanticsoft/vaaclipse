/**
 */
package org.lunifera.vaaclipse.ui.preferences.model.impl;

import org.eclipse.e4.ui.model.application.MContribution;

import org.eclipse.e4.ui.model.application.impl.ApplicationPackageImpl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.lunifera.vaaclipse.ui.preferences.model.ListCrud;
import org.lunifera.vaaclipse.ui.preferences.model.ListEditor;
import org.lunifera.vaaclipse.ui.preferences.model.ListFold;

import org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>List Editor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.ListEditorImpl#getContributionURI <em>Contribution URI</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.ListEditorImpl#getObject <em>Object</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.ListEditorImpl#getListCrud <em>List Crud</em>}</li>
 *   <li>{@link org.lunifera.vaaclipse.ui.preferences.model.impl.ListEditorImpl#getListFold <em>List Fold</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ListEditorImpl extends FieldEditorImpl<String> implements ListEditor {
	/**
	 * The default value of the '{@link #getContributionURI() <em>Contribution URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContributionURI()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTRIBUTION_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContributionURI() <em>Contribution URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContributionURI()
	 * @generated
	 * @ordered
	 */
	protected String contributionURI = CONTRIBUTION_URI_EDEFAULT;

	/**
	 * The default value of the '{@link #getObject() <em>Object</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObject()
	 * @generated
	 * @ordered
	 */
	protected static final Object OBJECT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getObject() <em>Object</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObject()
	 * @generated
	 * @ordered
	 */
	protected Object object = OBJECT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getListCrud() <em>List Crud</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getListCrud()
	 * @generated
	 * @ordered
	 */
	protected ListCrud listCrud;

	/**
	 * The cached value of the '{@link #getListFold() <em>List Fold</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getListFold()
	 * @generated
	 * @ordered
	 */
	protected ListFold listFold;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ListEditorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PreferencesPackage.Literals.LIST_EDITOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public void setDefaultValueTyped(String newDefaultValueTyped) {
		super.setDefaultValueTyped(newDefaultValueTyped);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getContributionURI() {
		return contributionURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContributionURI(String newContributionURI) {
		String oldContributionURI = contributionURI;
		contributionURI = newContributionURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.LIST_EDITOR__CONTRIBUTION_URI, oldContributionURI, contributionURI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObject(Object newObject) {
		Object oldObject = object;
		object = newObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.LIST_EDITOR__OBJECT, oldObject, object));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ListCrud getListCrud() {
		if (listCrud != null && listCrud.eIsProxy()) {
			InternalEObject oldListCrud = (InternalEObject)listCrud;
			listCrud = (ListCrud)eResolveProxy(oldListCrud);
			if (listCrud != oldListCrud) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PreferencesPackage.LIST_EDITOR__LIST_CRUD, oldListCrud, listCrud));
			}
		}
		return listCrud;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ListCrud basicGetListCrud() {
		return listCrud;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setListCrud(ListCrud newListCrud) {
		ListCrud oldListCrud = listCrud;
		listCrud = newListCrud;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.LIST_EDITOR__LIST_CRUD, oldListCrud, listCrud));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ListFold getListFold() {
		if (listFold != null && listFold.eIsProxy()) {
			InternalEObject oldListFold = (InternalEObject)listFold;
			listFold = (ListFold)eResolveProxy(oldListFold);
			if (listFold != oldListFold) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PreferencesPackage.LIST_EDITOR__LIST_FOLD, oldListFold, listFold));
			}
		}
		return listFold;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ListFold basicGetListFold() {
		return listFold;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setListFold(ListFold newListFold) {
		ListFold oldListFold = listFold;
		listFold = newListFold;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.LIST_EDITOR__LIST_FOLD, oldListFold, listFold));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PreferencesPackage.LIST_EDITOR__CONTRIBUTION_URI:
				return getContributionURI();
			case PreferencesPackage.LIST_EDITOR__OBJECT:
				return getObject();
			case PreferencesPackage.LIST_EDITOR__LIST_CRUD:
				if (resolve) return getListCrud();
				return basicGetListCrud();
			case PreferencesPackage.LIST_EDITOR__LIST_FOLD:
				if (resolve) return getListFold();
				return basicGetListFold();
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
			case PreferencesPackage.LIST_EDITOR__CONTRIBUTION_URI:
				setContributionURI((String)newValue);
				return;
			case PreferencesPackage.LIST_EDITOR__OBJECT:
				setObject(newValue);
				return;
			case PreferencesPackage.LIST_EDITOR__LIST_CRUD:
				setListCrud((ListCrud)newValue);
				return;
			case PreferencesPackage.LIST_EDITOR__LIST_FOLD:
				setListFold((ListFold)newValue);
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
			case PreferencesPackage.LIST_EDITOR__CONTRIBUTION_URI:
				setContributionURI(CONTRIBUTION_URI_EDEFAULT);
				return;
			case PreferencesPackage.LIST_EDITOR__OBJECT:
				setObject(OBJECT_EDEFAULT);
				return;
			case PreferencesPackage.LIST_EDITOR__LIST_CRUD:
				setListCrud((ListCrud)null);
				return;
			case PreferencesPackage.LIST_EDITOR__LIST_FOLD:
				setListFold((ListFold)null);
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
			case PreferencesPackage.LIST_EDITOR__CONTRIBUTION_URI:
				return CONTRIBUTION_URI_EDEFAULT == null ? contributionURI != null : !CONTRIBUTION_URI_EDEFAULT.equals(contributionURI);
			case PreferencesPackage.LIST_EDITOR__OBJECT:
				return OBJECT_EDEFAULT == null ? object != null : !OBJECT_EDEFAULT.equals(object);
			case PreferencesPackage.LIST_EDITOR__LIST_CRUD:
				return listCrud != null;
			case PreferencesPackage.LIST_EDITOR__LIST_FOLD:
				return listFold != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == MContribution.class) {
			switch (derivedFeatureID) {
				case PreferencesPackage.LIST_EDITOR__CONTRIBUTION_URI: return ApplicationPackageImpl.CONTRIBUTION__CONTRIBUTION_URI;
				case PreferencesPackage.LIST_EDITOR__OBJECT: return ApplicationPackageImpl.CONTRIBUTION__OBJECT;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == MContribution.class) {
			switch (baseFeatureID) {
				case ApplicationPackageImpl.CONTRIBUTION__CONTRIBUTION_URI: return PreferencesPackage.LIST_EDITOR__CONTRIBUTION_URI;
				case ApplicationPackageImpl.CONTRIBUTION__OBJECT: return PreferencesPackage.LIST_EDITOR__OBJECT;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (contributionURI: ");
		result.append(contributionURI);
		result.append(", object: ");
		result.append(object);
		result.append(')');
		return result.toString();
	}

} //ListEditorImpl
