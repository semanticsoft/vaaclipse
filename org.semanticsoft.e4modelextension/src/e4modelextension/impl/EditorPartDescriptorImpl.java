/**
 */
package e4modelextension.impl;

import e4modelextension.E4modelextensionPackage;
import e4modelextension.EditorPartDescriptor;

import org.eclipse.e4.ui.model.application.descriptor.basic.impl.PartDescriptorImpl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Editor Part Descriptor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link e4modelextension.impl.EditorPartDescriptorImpl#getUriFilter <em>Uri Filter</em>}</li>
 *   <li>{@link e4modelextension.impl.EditorPartDescriptorImpl#getPartAddingLogicUri <em>Part Adding Logic Uri</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EditorPartDescriptorImpl extends PartDescriptorImpl implements EditorPartDescriptor
{
	/**
	 * The default value of the '{@link #getUriFilter() <em>Uri Filter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUriFilter()
	 * @generated
	 * @ordered
	 */
	protected static final String URI_FILTER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUriFilter() <em>Uri Filter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUriFilter()
	 * @generated
	 * @ordered
	 */
	protected String uriFilter = URI_FILTER_EDEFAULT;

	/**
	 * The default value of the '{@link #getPartAddingLogicUri() <em>Part Adding Logic Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPartAddingLogicUri()
	 * @generated
	 * @ordered
	 */
	protected static final String PART_ADDING_LOGIC_URI_EDEFAULT = "bundleclass://org.semanticsoft.e4extension/org.semanticsoft.e4extension.shared.DefaultPartAddingLogic";

	/**
	 * The cached value of the '{@link #getPartAddingLogicUri() <em>Part Adding Logic Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPartAddingLogicUri()
	 * @generated
	 * @ordered
	 */
	protected String partAddingLogicUri = PART_ADDING_LOGIC_URI_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EditorPartDescriptorImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return E4modelextensionPackage.Literals.EDITOR_PART_DESCRIPTOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUriFilter()
	{
		return uriFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUriFilter(String newUriFilter)
	{
		String oldUriFilter = uriFilter;
		uriFilter = newUriFilter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.EDITOR_PART_DESCRIPTOR__URI_FILTER, oldUriFilter, uriFilter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPartAddingLogicUri()
	{
		return partAddingLogicUri;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPartAddingLogicUri(String newPartAddingLogicUri)
	{
		String oldPartAddingLogicUri = partAddingLogicUri;
		partAddingLogicUri = newPartAddingLogicUri;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, E4modelextensionPackage.EDITOR_PART_DESCRIPTOR__PART_ADDING_LOGIC_URI, oldPartAddingLogicUri, partAddingLogicUri));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID) {
			case E4modelextensionPackage.EDITOR_PART_DESCRIPTOR__URI_FILTER:
				return getUriFilter();
			case E4modelextensionPackage.EDITOR_PART_DESCRIPTOR__PART_ADDING_LOGIC_URI:
				return getPartAddingLogicUri();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID) {
			case E4modelextensionPackage.EDITOR_PART_DESCRIPTOR__URI_FILTER:
				setUriFilter((String)newValue);
				return;
			case E4modelextensionPackage.EDITOR_PART_DESCRIPTOR__PART_ADDING_LOGIC_URI:
				setPartAddingLogicUri((String)newValue);
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
	public void eUnset(int featureID)
	{
		switch (featureID) {
			case E4modelextensionPackage.EDITOR_PART_DESCRIPTOR__URI_FILTER:
				setUriFilter(URI_FILTER_EDEFAULT);
				return;
			case E4modelextensionPackage.EDITOR_PART_DESCRIPTOR__PART_ADDING_LOGIC_URI:
				setPartAddingLogicUri(PART_ADDING_LOGIC_URI_EDEFAULT);
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
	public boolean eIsSet(int featureID)
	{
		switch (featureID) {
			case E4modelextensionPackage.EDITOR_PART_DESCRIPTOR__URI_FILTER:
				return URI_FILTER_EDEFAULT == null ? uriFilter != null : !URI_FILTER_EDEFAULT.equals(uriFilter);
			case E4modelextensionPackage.EDITOR_PART_DESCRIPTOR__PART_ADDING_LOGIC_URI:
				return PART_ADDING_LOGIC_URI_EDEFAULT == null ? partAddingLogicUri != null : !PART_ADDING_LOGIC_URI_EDEFAULT.equals(partAddingLogicUri);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (uriFilter: ");
		result.append(uriFilter);
		result.append(", partAddingLogicUri: ");
		result.append(partAddingLogicUri);
		result.append(')');
		return result.toString();
	}

} //EditorPartDescriptorImpl
