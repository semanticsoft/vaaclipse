/**
 */
package preferences.impl;

import java.util.Collection;

import org.eclipse.e4.ui.model.application.impl.ApplicationElementImpl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import preferences.PreferencesCategory;
import preferences.PreferencesPackage;
import preferences.PreferencesPage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Category</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link preferences.impl.PreferencesCategoryImpl#getName <em>Name</em>}</li>
 *   <li>{@link preferences.impl.PreferencesCategoryImpl#getChildCategories <em>Child Categories</em>}</li>
 *   <li>{@link preferences.impl.PreferencesCategoryImpl#getParentCategory <em>Parent Category</em>}</li>
 *   <li>{@link preferences.impl.PreferencesCategoryImpl#getPage <em>Page</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PreferencesCategoryImpl extends ApplicationElementImpl implements PreferencesCategory {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = "No Name";

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getChildCategories() <em>Child Categories</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildCategories()
	 * @generated
	 * @ordered
	 */
	protected EList<PreferencesCategory> childCategories;

	/**
	 * The cached value of the '{@link #getParentCategory() <em>Parent Category</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentCategory()
	 * @generated
	 * @ordered
	 */
	protected PreferencesCategory parentCategory;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PreferencesCategoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PreferencesPackage.Literals.PREFERENCES_CATEGORY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.PREFERENCES_CATEGORY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PreferencesCategory> getChildCategories() {
		if (childCategories == null) {
			childCategories = new EObjectContainmentEList<PreferencesCategory>(PreferencesCategory.class, this, PreferencesPackage.PREFERENCES_CATEGORY__CHILD_CATEGORIES);
		}
		return childCategories;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreferencesCategory getParentCategory() {
		if (parentCategory != null && parentCategory.eIsProxy()) {
			InternalEObject oldParentCategory = (InternalEObject)parentCategory;
			parentCategory = (PreferencesCategory)eResolveProxy(oldParentCategory);
			if (parentCategory != oldParentCategory) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PreferencesPackage.PREFERENCES_CATEGORY__PARENT_CATEGORY, oldParentCategory, parentCategory));
			}
		}
		return parentCategory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreferencesCategory basicGetParentCategory() {
		return parentCategory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentCategory(PreferencesCategory newParentCategory) {
		PreferencesCategory oldParentCategory = parentCategory;
		parentCategory = newParentCategory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.PREFERENCES_CATEGORY__PARENT_CATEGORY, oldParentCategory, parentCategory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreferencesPage getPage() {
		if (eContainerFeatureID() != PreferencesPackage.PREFERENCES_CATEGORY__PAGE) return null;
		return (PreferencesPage)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPage(PreferencesPage newPage, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newPage, PreferencesPackage.PREFERENCES_CATEGORY__PAGE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPage(PreferencesPage newPage) {
		if (newPage != eInternalContainer() || (eContainerFeatureID() != PreferencesPackage.PREFERENCES_CATEGORY__PAGE && newPage != null)) {
			if (EcoreUtil.isAncestor(this, newPage))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newPage != null)
				msgs = ((InternalEObject)newPage).eInverseAdd(this, PreferencesPackage.PREFERENCES_PAGE__CATEGORY, PreferencesPage.class, msgs);
			msgs = basicSetPage(newPage, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PreferencesPackage.PREFERENCES_CATEGORY__PAGE, newPage, newPage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PreferencesPackage.PREFERENCES_CATEGORY__PAGE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetPage((PreferencesPage)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PreferencesPackage.PREFERENCES_CATEGORY__CHILD_CATEGORIES:
				return ((InternalEList<?>)getChildCategories()).basicRemove(otherEnd, msgs);
			case PreferencesPackage.PREFERENCES_CATEGORY__PAGE:
				return basicSetPage(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case PreferencesPackage.PREFERENCES_CATEGORY__PAGE:
				return eInternalContainer().eInverseRemove(this, PreferencesPackage.PREFERENCES_PAGE__CATEGORY, PreferencesPage.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PreferencesPackage.PREFERENCES_CATEGORY__NAME:
				return getName();
			case PreferencesPackage.PREFERENCES_CATEGORY__CHILD_CATEGORIES:
				return getChildCategories();
			case PreferencesPackage.PREFERENCES_CATEGORY__PARENT_CATEGORY:
				if (resolve) return getParentCategory();
				return basicGetParentCategory();
			case PreferencesPackage.PREFERENCES_CATEGORY__PAGE:
				return getPage();
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
			case PreferencesPackage.PREFERENCES_CATEGORY__NAME:
				setName((String)newValue);
				return;
			case PreferencesPackage.PREFERENCES_CATEGORY__CHILD_CATEGORIES:
				getChildCategories().clear();
				getChildCategories().addAll((Collection<? extends PreferencesCategory>)newValue);
				return;
			case PreferencesPackage.PREFERENCES_CATEGORY__PARENT_CATEGORY:
				setParentCategory((PreferencesCategory)newValue);
				return;
			case PreferencesPackage.PREFERENCES_CATEGORY__PAGE:
				setPage((PreferencesPage)newValue);
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
			case PreferencesPackage.PREFERENCES_CATEGORY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PreferencesPackage.PREFERENCES_CATEGORY__CHILD_CATEGORIES:
				getChildCategories().clear();
				return;
			case PreferencesPackage.PREFERENCES_CATEGORY__PARENT_CATEGORY:
				setParentCategory((PreferencesCategory)null);
				return;
			case PreferencesPackage.PREFERENCES_CATEGORY__PAGE:
				setPage((PreferencesPage)null);
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
			case PreferencesPackage.PREFERENCES_CATEGORY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PreferencesPackage.PREFERENCES_CATEGORY__CHILD_CATEGORIES:
				return childCategories != null && !childCategories.isEmpty();
			case PreferencesPackage.PREFERENCES_CATEGORY__PARENT_CATEGORY:
				return parentCategory != null;
			case PreferencesPackage.PREFERENCES_CATEGORY__PAGE:
				return getPage() != null;
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //PreferencesCategoryImpl
