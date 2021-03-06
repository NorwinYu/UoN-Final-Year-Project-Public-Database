/**
 */
package org.yakindu.base.types;

import org.eclipse.emf.common.util.EList;
import org.yakindu.base.base.DomainElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Package</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.yakindu.base.types.Package#getMember <em>Member</em>}</li>
 *   <li>{@link org.yakindu.base.types.Package#getImports <em>Imports</em>}</li>
 * </ul>
 *
 * @see org.yakindu.base.types.TypesPackage#getPackage()
 * @model
 * @generated
 */
public interface Package extends Declaration, DomainElement {
	/**
	 * Returns the value of the '<em><b>Member</b></em>' containment reference list.
	 * The list contents are of type {@link org.yakindu.base.types.Declaration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Member</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Member</em>' containment reference list.
	 * @see org.yakindu.base.types.TypesPackage#getPackage_Member()
	 * @model containment="true"
	 * @generated
	 */
	EList<Declaration> getMember();

	/**
	 * Returns the value of the '<em><b>Imports</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Imports</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Imports</em>' attribute list.
	 * @see org.yakindu.base.types.TypesPackage#getPackage_Imports()
	 * @model
	 * @generated
	 */
	EList<String> getImports();

} // Package
