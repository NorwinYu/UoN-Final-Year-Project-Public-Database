/**
 */
package org.yakindu.base.expressions.expressions;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.yakindu.base.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.yakindu.base.expressions.expressions.ExpressionsFactory
 * @model kind="package"
 * @generated
 */
public interface ExpressionsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "expressions";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.yakindu.org/base/expressions/Expressions";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "expressions";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ExpressionsPackage eINSTANCE = org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.LiteralImpl <em>Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.LiteralImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getLiteral()
	 * @generated
	 */
	int LITERAL = 3;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.BoolLiteralImpl <em>Bool Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.BoolLiteralImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getBoolLiteral()
	 * @generated
	 */
	int BOOL_LITERAL = 4;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.IntLiteralImpl <em>Int Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.IntLiteralImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getIntLiteral()
	 * @generated
	 */
	int INT_LITERAL = 5;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.DoubleLiteralImpl <em>Double Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.DoubleLiteralImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getDoubleLiteral()
	 * @generated
	 */
	int DOUBLE_LITERAL = 6;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.FloatLiteralImpl <em>Float Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.FloatLiteralImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getFloatLiteral()
	 * @generated
	 */
	int FLOAT_LITERAL = 7;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.HexLiteralImpl <em>Hex Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.HexLiteralImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getHexLiteral()
	 * @generated
	 */
	int HEX_LITERAL = 8;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.StringLiteralImpl <em>String Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.StringLiteralImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getStringLiteral()
	 * @generated
	 */
	int STRING_LITERAL = 10;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.NullLiteralImpl <em>Null Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.NullLiteralImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getNullLiteral()
	 * @generated
	 */
	int NULL_LITERAL = 11;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.AssignmentExpressionImpl <em>Assignment Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.AssignmentExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getAssignmentExpression()
	 * @generated
	 */
	int ASSIGNMENT_EXPRESSION = 12;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.ConditionalExpressionImpl <em>Conditional Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.ConditionalExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getConditionalExpression()
	 * @generated
	 */
	int CONDITIONAL_EXPRESSION = 13;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.BinaryExpressionImpl <em>Binary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.BinaryExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getBinaryExpression()
	 * @generated
	 */
	int BINARY_EXPRESSION = 0;

	/**
	 * The feature id for the '<em><b>Left Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPRESSION__LEFT_OPERAND = TypesPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Right Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPRESSION__RIGHT_OPERAND = TypesPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Binary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_EXPRESSION_FEATURE_COUNT = TypesPackage.EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.UnaryExpressionImpl <em>Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.UnaryExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getUnaryExpression()
	 * @generated
	 */
	int UNARY_EXPRESSION = 1;

	/**
	 * The feature id for the '<em><b>Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION__OPERAND = TypesPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION_FEATURE_COUNT = TypesPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.LogicalOrExpressionImpl <em>Logical Or Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.LogicalOrExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getLogicalOrExpression()
	 * @generated
	 */
	int LOGICAL_OR_EXPRESSION = 14;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.LogicalAndExpressionImpl <em>Logical And Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.LogicalAndExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getLogicalAndExpression()
	 * @generated
	 */
	int LOGICAL_AND_EXPRESSION = 15;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.LogicalNotExpressionImpl <em>Logical Not Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.LogicalNotExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getLogicalNotExpression()
	 * @generated
	 */
	int LOGICAL_NOT_EXPRESSION = 16;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.BitwiseXorExpressionImpl <em>Bitwise Xor Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.BitwiseXorExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getBitwiseXorExpression()
	 * @generated
	 */
	int BITWISE_XOR_EXPRESSION = 17;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.BitwiseOrExpressionImpl <em>Bitwise Or Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.BitwiseOrExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getBitwiseOrExpression()
	 * @generated
	 */
	int BITWISE_OR_EXPRESSION = 18;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.BitwiseAndExpressionImpl <em>Bitwise And Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.BitwiseAndExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getBitwiseAndExpression()
	 * @generated
	 */
	int BITWISE_AND_EXPRESSION = 19;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.LogicalRelationExpressionImpl <em>Logical Relation Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.LogicalRelationExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getLogicalRelationExpression()
	 * @generated
	 */
	int LOGICAL_RELATION_EXPRESSION = 20;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.ShiftExpressionImpl <em>Shift Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.ShiftExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getShiftExpression()
	 * @generated
	 */
	int SHIFT_EXPRESSION = 21;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.NumericalAddSubtractExpressionImpl <em>Numerical Add Subtract Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.NumericalAddSubtractExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getNumericalAddSubtractExpression()
	 * @generated
	 */
	int NUMERICAL_ADD_SUBTRACT_EXPRESSION = 22;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.NumericalMultiplyDivideExpressionImpl <em>Numerical Multiply Divide Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.NumericalMultiplyDivideExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getNumericalMultiplyDivideExpression()
	 * @generated
	 */
	int NUMERICAL_MULTIPLY_DIVIDE_EXPRESSION = 23;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.NumericalUnaryExpressionImpl <em>Numerical Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.NumericalUnaryExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getNumericalUnaryExpression()
	 * @generated
	 */
	int NUMERICAL_UNARY_EXPRESSION = 24;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.PrimitiveValueExpressionImpl <em>Primitive Value Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.PrimitiveValueExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getPrimitiveValueExpression()
	 * @generated
	 */
	int PRIMITIVE_VALUE_EXPRESSION = 26;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.ArgumentExpressionImpl <em>Argument Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.ArgumentExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getArgumentExpression()
	 * @generated
	 */
	int ARGUMENT_EXPRESSION = 2;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_EXPRESSION__ARGUMENTS = TypesPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Argument Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_EXPRESSION_FEATURE_COUNT = TypesPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_FEATURE_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_LITERAL__VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Bool Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_LITERAL__VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Int Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL__VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Double Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_LITERAL__VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Float Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEX_LITERAL__VALUE = INT_LITERAL__VALUE;

	/**
	 * The number of structural features of the '<em>Hex Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEX_LITERAL_FEATURE_COUNT = INT_LITERAL_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.BinaryLiteralImpl <em>Binary Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.BinaryLiteralImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getBinaryLiteral()
	 * @generated
	 */
	int BINARY_LITERAL = 9;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_LITERAL__VALUE = HEX_LITERAL__VALUE;

	/**
	 * The number of structural features of the '<em>Binary Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_LITERAL_FEATURE_COUNT = HEX_LITERAL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL__VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Null Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NULL_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Var Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_EXPRESSION__VAR_REF = TypesPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_EXPRESSION__OPERATOR = TypesPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_EXPRESSION__EXPRESSION = TypesPackage.EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Assignment Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_EXPRESSION_FEATURE_COUNT = TypesPackage.EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_EXPRESSION__CONDITION = TypesPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>True Case</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_EXPRESSION__TRUE_CASE = TypesPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>False Case</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_EXPRESSION__FALSE_CASE = TypesPackage.EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Conditional Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_EXPRESSION_FEATURE_COUNT = TypesPackage.EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Left Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGICAL_OR_EXPRESSION__LEFT_OPERAND = BINARY_EXPRESSION__LEFT_OPERAND;

	/**
	 * The feature id for the '<em><b>Right Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGICAL_OR_EXPRESSION__RIGHT_OPERAND = BINARY_EXPRESSION__RIGHT_OPERAND;

	/**
	 * The number of structural features of the '<em>Logical Or Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGICAL_OR_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Left Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGICAL_AND_EXPRESSION__LEFT_OPERAND = BINARY_EXPRESSION__LEFT_OPERAND;

	/**
	 * The feature id for the '<em><b>Right Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGICAL_AND_EXPRESSION__RIGHT_OPERAND = BINARY_EXPRESSION__RIGHT_OPERAND;

	/**
	 * The number of structural features of the '<em>Logical And Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGICAL_AND_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGICAL_NOT_EXPRESSION__OPERAND = UNARY_EXPRESSION__OPERAND;

	/**
	 * The number of structural features of the '<em>Logical Not Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGICAL_NOT_EXPRESSION_FEATURE_COUNT = UNARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Left Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BITWISE_XOR_EXPRESSION__LEFT_OPERAND = BINARY_EXPRESSION__LEFT_OPERAND;

	/**
	 * The feature id for the '<em><b>Right Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BITWISE_XOR_EXPRESSION__RIGHT_OPERAND = BINARY_EXPRESSION__RIGHT_OPERAND;

	/**
	 * The number of structural features of the '<em>Bitwise Xor Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BITWISE_XOR_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Left Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BITWISE_OR_EXPRESSION__LEFT_OPERAND = BINARY_EXPRESSION__LEFT_OPERAND;

	/**
	 * The feature id for the '<em><b>Right Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BITWISE_OR_EXPRESSION__RIGHT_OPERAND = BINARY_EXPRESSION__RIGHT_OPERAND;

	/**
	 * The number of structural features of the '<em>Bitwise Or Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BITWISE_OR_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Left Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BITWISE_AND_EXPRESSION__LEFT_OPERAND = BINARY_EXPRESSION__LEFT_OPERAND;

	/**
	 * The feature id for the '<em><b>Right Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BITWISE_AND_EXPRESSION__RIGHT_OPERAND = BINARY_EXPRESSION__RIGHT_OPERAND;

	/**
	 * The number of structural features of the '<em>Bitwise And Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BITWISE_AND_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Left Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGICAL_RELATION_EXPRESSION__LEFT_OPERAND = BINARY_EXPRESSION__LEFT_OPERAND;

	/**
	 * The feature id for the '<em><b>Right Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGICAL_RELATION_EXPRESSION__RIGHT_OPERAND = BINARY_EXPRESSION__RIGHT_OPERAND;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGICAL_RELATION_EXPRESSION__OPERATOR = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Logical Relation Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOGICAL_RELATION_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Left Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHIFT_EXPRESSION__LEFT_OPERAND = BINARY_EXPRESSION__LEFT_OPERAND;

	/**
	 * The feature id for the '<em><b>Right Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHIFT_EXPRESSION__RIGHT_OPERAND = BINARY_EXPRESSION__RIGHT_OPERAND;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHIFT_EXPRESSION__OPERATOR = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Shift Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHIFT_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Left Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERICAL_ADD_SUBTRACT_EXPRESSION__LEFT_OPERAND = BINARY_EXPRESSION__LEFT_OPERAND;

	/**
	 * The feature id for the '<em><b>Right Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERICAL_ADD_SUBTRACT_EXPRESSION__RIGHT_OPERAND = BINARY_EXPRESSION__RIGHT_OPERAND;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERICAL_ADD_SUBTRACT_EXPRESSION__OPERATOR = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Numerical Add Subtract Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERICAL_ADD_SUBTRACT_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Left Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERICAL_MULTIPLY_DIVIDE_EXPRESSION__LEFT_OPERAND = BINARY_EXPRESSION__LEFT_OPERAND;

	/**
	 * The feature id for the '<em><b>Right Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERICAL_MULTIPLY_DIVIDE_EXPRESSION__RIGHT_OPERAND = BINARY_EXPRESSION__RIGHT_OPERAND;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERICAL_MULTIPLY_DIVIDE_EXPRESSION__OPERATOR = BINARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Numerical Multiply Divide Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERICAL_MULTIPLY_DIVIDE_EXPRESSION_FEATURE_COUNT = BINARY_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERICAL_UNARY_EXPRESSION__OPERAND = UNARY_EXPRESSION__OPERAND;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERICAL_UNARY_EXPRESSION__OPERATOR = UNARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Numerical Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERICAL_UNARY_EXPRESSION_FEATURE_COUNT = UNARY_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.FeatureCallImpl <em>Feature Call</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.FeatureCallImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getFeatureCall()
	 * @generated
	 */
	int FEATURE_CALL = 27;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.ElementReferenceExpressionImpl <em>Element Reference Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.ElementReferenceExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getElementReferenceExpression()
	 * @generated
	 */
	int ELEMENT_REFERENCE_EXPRESSION = 29;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.ParenthesizedExpressionImpl <em>Parenthesized Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.ParenthesizedExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getParenthesizedExpression()
	 * @generated
	 */
	int PARENTHESIZED_EXPRESSION = 30;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.TypeCastExpressionImpl <em>Type Cast Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.TypeCastExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getTypeCastExpression()
	 * @generated
	 */
	int TYPE_CAST_EXPRESSION = 31;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.ArgumentImpl <em>Argument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.ArgumentImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getArgument()
	 * @generated
	 */
	int ARGUMENT = 32;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.PostFixUnaryExpressionImpl <em>Post Fix Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.PostFixUnaryExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getPostFixUnaryExpression()
	 * @generated
	 */
	int POST_FIX_UNARY_EXPRESSION = 25;

	/**
	 * The feature id for the '<em><b>Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_FIX_UNARY_EXPRESSION__OPERAND = UNARY_EXPRESSION__OPERAND;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_FIX_UNARY_EXPRESSION__OPERATOR = UNARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Post Fix Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_FIX_UNARY_EXPRESSION_FEATURE_COUNT = UNARY_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_VALUE_EXPRESSION__VALUE = TypesPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Primitive Value Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_VALUE_EXPRESSION_FEATURE_COUNT = TypesPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CALL__ARGUMENTS = ARGUMENT_EXPRESSION__ARGUMENTS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CALL__OWNER = ARGUMENT_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CALL__FEATURE = ARGUMENT_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Operation Call</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CALL__OPERATION_CALL = ARGUMENT_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Array Selector</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CALL__ARRAY_SELECTOR = ARGUMENT_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Array Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CALL__ARRAY_ACCESS = ARGUMENT_EXPRESSION_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Feature Call</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CALL_FEATURE_COUNT = ARGUMENT_EXPRESSION_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.MetaCallImpl <em>Meta Call</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.MetaCallImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getMetaCall()
	 * @generated
	 */
	int META_CALL = 28;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_CALL__ARGUMENTS = FEATURE_CALL__ARGUMENTS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_CALL__OWNER = FEATURE_CALL__OWNER;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_CALL__FEATURE = FEATURE_CALL__FEATURE;

	/**
	 * The feature id for the '<em><b>Operation Call</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_CALL__OPERATION_CALL = FEATURE_CALL__OPERATION_CALL;

	/**
	 * The feature id for the '<em><b>Array Selector</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_CALL__ARRAY_SELECTOR = FEATURE_CALL__ARRAY_SELECTOR;

	/**
	 * The feature id for the '<em><b>Array Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_CALL__ARRAY_ACCESS = FEATURE_CALL__ARRAY_ACCESS;

	/**
	 * The number of structural features of the '<em>Meta Call</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_CALL_FEATURE_COUNT = FEATURE_CALL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_REFERENCE_EXPRESSION__ARGUMENTS = ARGUMENT_EXPRESSION__ARGUMENTS;

	/**
	 * The feature id for the '<em><b>Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_REFERENCE_EXPRESSION__REFERENCE = ARGUMENT_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operation Call</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_REFERENCE_EXPRESSION__OPERATION_CALL = ARGUMENT_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Array Selector</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_REFERENCE_EXPRESSION__ARRAY_SELECTOR = ARGUMENT_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Array Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_REFERENCE_EXPRESSION__ARRAY_ACCESS = ARGUMENT_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Element Reference Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_REFERENCE_EXPRESSION_FEATURE_COUNT = ARGUMENT_EXPRESSION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENTHESIZED_EXPRESSION__EXPRESSION = TypesPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Parenthesized Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARENTHESIZED_EXPRESSION_FEATURE_COUNT = TypesPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_CAST_EXPRESSION__OPERAND = TypesPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_CAST_EXPRESSION__TYPE = TypesPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Type Cast Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_CAST_EXPRESSION_FEATURE_COUNT = TypesPackage.EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT__PARAMETER = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.IfExpressionImpl <em>If Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.IfExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getIfExpression()
	 * @generated
	 */
	int IF_EXPRESSION = 33;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_EXPRESSION__CONDITION = TypesPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Then</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_EXPRESSION__THEN = TypesPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Else</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_EXPRESSION__ELSE = TypesPackage.EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>If Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_EXPRESSION_FEATURE_COUNT = TypesPackage.EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.BlockExpressionImpl <em>Block Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.BlockExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getBlockExpression()
	 * @generated
	 */
	int BLOCK_EXPRESSION = 34;

	/**
	 * The feature id for the '<em><b>Expressions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_EXPRESSION__EXPRESSIONS = TypesPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Block Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_EXPRESSION_FEATURE_COUNT = TypesPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.WhileExpressionImpl <em>While Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.WhileExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getWhileExpression()
	 * @generated
	 */
	int WHILE_EXPRESSION = 35;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WHILE_EXPRESSION__BODY = TypesPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WHILE_EXPRESSION__CONDITION = TypesPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>While Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WHILE_EXPRESSION_FEATURE_COUNT = TypesPackage.EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.ReturnExpressionImpl <em>Return Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.ReturnExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getReturnExpression()
	 * @generated
	 */
	int RETURN_EXPRESSION = 36;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_EXPRESSION__EXPRESSION = TypesPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Return Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_EXPRESSION_FEATURE_COUNT = TypesPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.SwitchExpressionImpl <em>Switch Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.SwitchExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getSwitchExpression()
	 * @generated
	 */
	int SWITCH_EXPRESSION = 37;

	/**
	 * The feature id for the '<em><b>Switch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_EXPRESSION__SWITCH = TypesPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Cases</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_EXPRESSION__CASES = TypesPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_EXPRESSION__DEFAULT = TypesPackage.EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Switch Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_EXPRESSION_FEATURE_COUNT = TypesPackage.EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.SwitchCaseImpl <em>Switch Case</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.SwitchCaseImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getSwitchCase()
	 * @generated
	 */
	int SWITCH_CASE = 38;

	/**
	 * The feature id for the '<em><b>Case</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_CASE__CASE = 0;

	/**
	 * The feature id for the '<em><b>Then</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_CASE__THEN = 1;

	/**
	 * The number of structural features of the '<em>Switch Case</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_CASE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.EventRaisingExpressionImpl <em>Event Raising Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.EventRaisingExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getEventRaisingExpression()
	 * @generated
	 */
	int EVENT_RAISING_EXPRESSION = 39;

	/**
	 * The feature id for the '<em><b>Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_RAISING_EXPRESSION__EVENT = TypesPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_RAISING_EXPRESSION__VALUE = TypesPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Event Raising Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_RAISING_EXPRESSION_FEATURE_COUNT = TypesPackage.EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.EventValueReferenceExpressionImpl <em>Event Value Reference Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.EventValueReferenceExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getEventValueReferenceExpression()
	 * @generated
	 */
	int EVENT_VALUE_REFERENCE_EXPRESSION = 40;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_VALUE_REFERENCE_EXPRESSION__VALUE = TypesPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Event Value Reference Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_VALUE_REFERENCE_EXPRESSION_FEATURE_COUNT = TypesPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.ForExpressionImpl <em>For Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.ForExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getForExpression()
	 * @generated
	 */
	int FOR_EXPRESSION = 41;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_EXPRESSION__BODY = TypesPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_EXPRESSION__CONDITION = TypesPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Var Decls</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_EXPRESSION__VAR_DECLS = TypesPackage.EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Var Updates</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_EXPRESSION__VAR_UPDATES = TypesPackage.EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>For Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_EXPRESSION_FEATURE_COUNT = TypesPackage.EXPRESSION_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.ForVarDeclImpl <em>For Var Decl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.ForVarDeclImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getForVarDecl()
	 * @generated
	 */
	int FOR_VAR_DECL = 42;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_VAR_DECL__NAME = TypesPackage.PROPERTY__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_VAR_DECL__ANNOTATIONS = TypesPackage.PROPERTY__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Annotation Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_VAR_DECL__ANNOTATION_INFO = TypesPackage.PROPERTY__ANNOTATION_INFO;

	/**
	 * The feature id for the '<em><b>Meta Features</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_VAR_DECL__META_FEATURES = TypesPackage.PROPERTY__META_FEATURES;

	/**
	 * The feature id for the '<em><b>Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_VAR_DECL__STATIC = TypesPackage.PROPERTY__STATIC;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_VAR_DECL__ID = TypesPackage.PROPERTY__ID;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_VAR_DECL__TYPE = TypesPackage.PROPERTY__TYPE;

	/**
	 * The feature id for the '<em><b>Type Specifier</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_VAR_DECL__TYPE_SPECIFIER = TypesPackage.PROPERTY__TYPE_SPECIFIER;

	/**
	 * The feature id for the '<em><b>Const</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_VAR_DECL__CONST = TypesPackage.PROPERTY__CONST;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_VAR_DECL__READONLY = TypesPackage.PROPERTY__READONLY;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_VAR_DECL__INITIAL_VALUE = TypesPackage.PROPERTY__INITIAL_VALUE;

	/**
	 * The number of structural features of the '<em>For Var Decl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_VAR_DECL_FEATURE_COUNT = TypesPackage.PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.impl.ThrowExpressionImpl <em>Throw Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.impl.ThrowExpressionImpl
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getThrowExpression()
	 * @generated
	 */
	int THROW_EXPRESSION = 43;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THROW_EXPRESSION__EXPRESSION = TypesPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Throw Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THROW_EXPRESSION_FEATURE_COUNT = TypesPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.AssignmentOperator <em>Assignment Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.AssignmentOperator
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getAssignmentOperator()
	 * @generated
	 */
	int ASSIGNMENT_OPERATOR = 44;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.ShiftOperator <em>Shift Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.ShiftOperator
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getShiftOperator()
	 * @generated
	 */
	int SHIFT_OPERATOR = 45;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.AdditiveOperator <em>Additive Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.AdditiveOperator
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getAdditiveOperator()
	 * @generated
	 */
	int ADDITIVE_OPERATOR = 46;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.MultiplicativeOperator <em>Multiplicative Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.MultiplicativeOperator
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getMultiplicativeOperator()
	 * @generated
	 */
	int MULTIPLICATIVE_OPERATOR = 47;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.UnaryOperator <em>Unary Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.UnaryOperator
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getUnaryOperator()
	 * @generated
	 */
	int UNARY_OPERATOR = 48;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.RelationalOperator <em>Relational Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.RelationalOperator
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getRelationalOperator()
	 * @generated
	 */
	int RELATIONAL_OPERATOR = 49;


	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.LogicalOperator <em>Logical Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.LogicalOperator
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getLogicalOperator()
	 * @generated
	 */
	int LOGICAL_OPERATOR = 50;

	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.BitwiseOperator <em>Bitwise Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.BitwiseOperator
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getBitwiseOperator()
	 * @generated
	 */
	int BITWISE_OPERATOR = 51;


	/**
	 * The meta object id for the '{@link org.yakindu.base.expressions.expressions.PostFixOperator <em>Post Fix Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.yakindu.base.expressions.expressions.PostFixOperator
	 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getPostFixOperator()
	 * @generated
	 */
	int POST_FIX_OPERATOR = 52;

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.Literal <em>Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Literal</em>'.
	 * @see org.yakindu.base.expressions.expressions.Literal
	 * @generated
	 */
	EClass getLiteral();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.BoolLiteral <em>Bool Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bool Literal</em>'.
	 * @see org.yakindu.base.expressions.expressions.BoolLiteral
	 * @generated
	 */
	EClass getBoolLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.yakindu.base.expressions.expressions.BoolLiteral#isValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.yakindu.base.expressions.expressions.BoolLiteral#isValue()
	 * @see #getBoolLiteral()
	 * @generated
	 */
	EAttribute getBoolLiteral_Value();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.IntLiteral <em>Int Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Int Literal</em>'.
	 * @see org.yakindu.base.expressions.expressions.IntLiteral
	 * @generated
	 */
	EClass getIntLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.yakindu.base.expressions.expressions.IntLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.yakindu.base.expressions.expressions.IntLiteral#getValue()
	 * @see #getIntLiteral()
	 * @generated
	 */
	EAttribute getIntLiteral_Value();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.DoubleLiteral <em>Double Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Double Literal</em>'.
	 * @see org.yakindu.base.expressions.expressions.DoubleLiteral
	 * @generated
	 */
	EClass getDoubleLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.yakindu.base.expressions.expressions.DoubleLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.yakindu.base.expressions.expressions.DoubleLiteral#getValue()
	 * @see #getDoubleLiteral()
	 * @generated
	 */
	EAttribute getDoubleLiteral_Value();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.FloatLiteral <em>Float Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Float Literal</em>'.
	 * @see org.yakindu.base.expressions.expressions.FloatLiteral
	 * @generated
	 */
	EClass getFloatLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.yakindu.base.expressions.expressions.FloatLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.yakindu.base.expressions.expressions.FloatLiteral#getValue()
	 * @see #getFloatLiteral()
	 * @generated
	 */
	EAttribute getFloatLiteral_Value();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.HexLiteral <em>Hex Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Hex Literal</em>'.
	 * @see org.yakindu.base.expressions.expressions.HexLiteral
	 * @generated
	 */
	EClass getHexLiteral();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.BinaryLiteral <em>Binary Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binary Literal</em>'.
	 * @see org.yakindu.base.expressions.expressions.BinaryLiteral
	 * @generated
	 */
	EClass getBinaryLiteral();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.StringLiteral <em>String Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Literal</em>'.
	 * @see org.yakindu.base.expressions.expressions.StringLiteral
	 * @generated
	 */
	EClass getStringLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.yakindu.base.expressions.expressions.StringLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.yakindu.base.expressions.expressions.StringLiteral#getValue()
	 * @see #getStringLiteral()
	 * @generated
	 */
	EAttribute getStringLiteral_Value();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.NullLiteral <em>Null Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Null Literal</em>'.
	 * @see org.yakindu.base.expressions.expressions.NullLiteral
	 * @generated
	 */
	EClass getNullLiteral();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.AssignmentExpression <em>Assignment Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Assignment Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.AssignmentExpression
	 * @generated
	 */
	EClass getAssignmentExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.AssignmentExpression#getVarRef <em>Var Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Var Ref</em>'.
	 * @see org.yakindu.base.expressions.expressions.AssignmentExpression#getVarRef()
	 * @see #getAssignmentExpression()
	 * @generated
	 */
	EReference getAssignmentExpression_VarRef();

	/**
	 * Returns the meta object for the attribute '{@link org.yakindu.base.expressions.expressions.AssignmentExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.yakindu.base.expressions.expressions.AssignmentExpression#getOperator()
	 * @see #getAssignmentExpression()
	 * @generated
	 */
	EAttribute getAssignmentExpression_Operator();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.AssignmentExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.AssignmentExpression#getExpression()
	 * @see #getAssignmentExpression()
	 * @generated
	 */
	EReference getAssignmentExpression_Expression();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.ConditionalExpression <em>Conditional Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Conditional Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.ConditionalExpression
	 * @generated
	 */
	EClass getConditionalExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.ConditionalExpression#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see org.yakindu.base.expressions.expressions.ConditionalExpression#getCondition()
	 * @see #getConditionalExpression()
	 * @generated
	 */
	EReference getConditionalExpression_Condition();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.ConditionalExpression#getTrueCase <em>True Case</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>True Case</em>'.
	 * @see org.yakindu.base.expressions.expressions.ConditionalExpression#getTrueCase()
	 * @see #getConditionalExpression()
	 * @generated
	 */
	EReference getConditionalExpression_TrueCase();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.ConditionalExpression#getFalseCase <em>False Case</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>False Case</em>'.
	 * @see org.yakindu.base.expressions.expressions.ConditionalExpression#getFalseCase()
	 * @see #getConditionalExpression()
	 * @generated
	 */
	EReference getConditionalExpression_FalseCase();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.LogicalOrExpression <em>Logical Or Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Logical Or Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.LogicalOrExpression
	 * @generated
	 */
	EClass getLogicalOrExpression();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.LogicalAndExpression <em>Logical And Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Logical And Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.LogicalAndExpression
	 * @generated
	 */
	EClass getLogicalAndExpression();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.LogicalNotExpression <em>Logical Not Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Logical Not Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.LogicalNotExpression
	 * @generated
	 */
	EClass getLogicalNotExpression();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.BitwiseXorExpression <em>Bitwise Xor Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bitwise Xor Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.BitwiseXorExpression
	 * @generated
	 */
	EClass getBitwiseXorExpression();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.BitwiseOrExpression <em>Bitwise Or Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bitwise Or Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.BitwiseOrExpression
	 * @generated
	 */
	EClass getBitwiseOrExpression();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.BitwiseAndExpression <em>Bitwise And Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bitwise And Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.BitwiseAndExpression
	 * @generated
	 */
	EClass getBitwiseAndExpression();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.LogicalRelationExpression <em>Logical Relation Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Logical Relation Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.LogicalRelationExpression
	 * @generated
	 */
	EClass getLogicalRelationExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.yakindu.base.expressions.expressions.LogicalRelationExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.yakindu.base.expressions.expressions.LogicalRelationExpression#getOperator()
	 * @see #getLogicalRelationExpression()
	 * @generated
	 */
	EAttribute getLogicalRelationExpression_Operator();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.ShiftExpression <em>Shift Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shift Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.ShiftExpression
	 * @generated
	 */
	EClass getShiftExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.yakindu.base.expressions.expressions.ShiftExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.yakindu.base.expressions.expressions.ShiftExpression#getOperator()
	 * @see #getShiftExpression()
	 * @generated
	 */
	EAttribute getShiftExpression_Operator();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.NumericalAddSubtractExpression <em>Numerical Add Subtract Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numerical Add Subtract Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.NumericalAddSubtractExpression
	 * @generated
	 */
	EClass getNumericalAddSubtractExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.yakindu.base.expressions.expressions.NumericalAddSubtractExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.yakindu.base.expressions.expressions.NumericalAddSubtractExpression#getOperator()
	 * @see #getNumericalAddSubtractExpression()
	 * @generated
	 */
	EAttribute getNumericalAddSubtractExpression_Operator();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.NumericalMultiplyDivideExpression <em>Numerical Multiply Divide Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numerical Multiply Divide Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.NumericalMultiplyDivideExpression
	 * @generated
	 */
	EClass getNumericalMultiplyDivideExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.yakindu.base.expressions.expressions.NumericalMultiplyDivideExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.yakindu.base.expressions.expressions.NumericalMultiplyDivideExpression#getOperator()
	 * @see #getNumericalMultiplyDivideExpression()
	 * @generated
	 */
	EAttribute getNumericalMultiplyDivideExpression_Operator();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.NumericalUnaryExpression <em>Numerical Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numerical Unary Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.NumericalUnaryExpression
	 * @generated
	 */
	EClass getNumericalUnaryExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.yakindu.base.expressions.expressions.NumericalUnaryExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.yakindu.base.expressions.expressions.NumericalUnaryExpression#getOperator()
	 * @see #getNumericalUnaryExpression()
	 * @generated
	 */
	EAttribute getNumericalUnaryExpression_Operator();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.PrimitiveValueExpression <em>Primitive Value Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Primitive Value Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.PrimitiveValueExpression
	 * @generated
	 */
	EClass getPrimitiveValueExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.PrimitiveValueExpression#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.yakindu.base.expressions.expressions.PrimitiveValueExpression#getValue()
	 * @see #getPrimitiveValueExpression()
	 * @generated
	 */
	EReference getPrimitiveValueExpression_Value();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.FeatureCall <em>Feature Call</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Call</em>'.
	 * @see org.yakindu.base.expressions.expressions.FeatureCall
	 * @generated
	 */
	EClass getFeatureCall();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.FeatureCall#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Owner</em>'.
	 * @see org.yakindu.base.expressions.expressions.FeatureCall#getOwner()
	 * @see #getFeatureCall()
	 * @generated
	 */
	EReference getFeatureCall_Owner();

	/**
	 * Returns the meta object for the reference '{@link org.yakindu.base.expressions.expressions.FeatureCall#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature</em>'.
	 * @see org.yakindu.base.expressions.expressions.FeatureCall#getFeature()
	 * @see #getFeatureCall()
	 * @generated
	 */
	EReference getFeatureCall_Feature();

	/**
	 * Returns the meta object for the attribute '{@link org.yakindu.base.expressions.expressions.FeatureCall#isOperationCall <em>Operation Call</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operation Call</em>'.
	 * @see org.yakindu.base.expressions.expressions.FeatureCall#isOperationCall()
	 * @see #getFeatureCall()
	 * @generated
	 */
	EAttribute getFeatureCall_OperationCall();

	/**
	 * Returns the meta object for the containment reference list '{@link org.yakindu.base.expressions.expressions.FeatureCall#getArraySelector <em>Array Selector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Array Selector</em>'.
	 * @see org.yakindu.base.expressions.expressions.FeatureCall#getArraySelector()
	 * @see #getFeatureCall()
	 * @generated
	 */
	EReference getFeatureCall_ArraySelector();

	/**
	 * Returns the meta object for the attribute '{@link org.yakindu.base.expressions.expressions.FeatureCall#isArrayAccess <em>Array Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Array Access</em>'.
	 * @see org.yakindu.base.expressions.expressions.FeatureCall#isArrayAccess()
	 * @see #getFeatureCall()
	 * @generated
	 */
	EAttribute getFeatureCall_ArrayAccess();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.MetaCall <em>Meta Call</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Meta Call</em>'.
	 * @see org.yakindu.base.expressions.expressions.MetaCall
	 * @generated
	 */
	EClass getMetaCall();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.ElementReferenceExpression <em>Element Reference Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element Reference Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.ElementReferenceExpression
	 * @generated
	 */
	EClass getElementReferenceExpression();

	/**
	 * Returns the meta object for the reference '{@link org.yakindu.base.expressions.expressions.ElementReferenceExpression#getReference <em>Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Reference</em>'.
	 * @see org.yakindu.base.expressions.expressions.ElementReferenceExpression#getReference()
	 * @see #getElementReferenceExpression()
	 * @generated
	 */
	EReference getElementReferenceExpression_Reference();

	/**
	 * Returns the meta object for the attribute '{@link org.yakindu.base.expressions.expressions.ElementReferenceExpression#isOperationCall <em>Operation Call</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operation Call</em>'.
	 * @see org.yakindu.base.expressions.expressions.ElementReferenceExpression#isOperationCall()
	 * @see #getElementReferenceExpression()
	 * @generated
	 */
	EAttribute getElementReferenceExpression_OperationCall();

	/**
	 * Returns the meta object for the containment reference list '{@link org.yakindu.base.expressions.expressions.ElementReferenceExpression#getArraySelector <em>Array Selector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Array Selector</em>'.
	 * @see org.yakindu.base.expressions.expressions.ElementReferenceExpression#getArraySelector()
	 * @see #getElementReferenceExpression()
	 * @generated
	 */
	EReference getElementReferenceExpression_ArraySelector();

	/**
	 * Returns the meta object for the attribute '{@link org.yakindu.base.expressions.expressions.ElementReferenceExpression#isArrayAccess <em>Array Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Array Access</em>'.
	 * @see org.yakindu.base.expressions.expressions.ElementReferenceExpression#isArrayAccess()
	 * @see #getElementReferenceExpression()
	 * @generated
	 */
	EAttribute getElementReferenceExpression_ArrayAccess();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.ParenthesizedExpression <em>Parenthesized Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parenthesized Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.ParenthesizedExpression
	 * @generated
	 */
	EClass getParenthesizedExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.ParenthesizedExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.ParenthesizedExpression#getExpression()
	 * @see #getParenthesizedExpression()
	 * @generated
	 */
	EReference getParenthesizedExpression_Expression();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.TypeCastExpression <em>Type Cast Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Cast Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.TypeCastExpression
	 * @generated
	 */
	EClass getTypeCastExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.TypeCastExpression#getOperand <em>Operand</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Operand</em>'.
	 * @see org.yakindu.base.expressions.expressions.TypeCastExpression#getOperand()
	 * @see #getTypeCastExpression()
	 * @generated
	 */
	EReference getTypeCastExpression_Operand();

	/**
	 * Returns the meta object for the reference '{@link org.yakindu.base.expressions.expressions.TypeCastExpression#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.yakindu.base.expressions.expressions.TypeCastExpression#getType()
	 * @see #getTypeCastExpression()
	 * @generated
	 */
	EReference getTypeCastExpression_Type();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.Argument <em>Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Argument</em>'.
	 * @see org.yakindu.base.expressions.expressions.Argument
	 * @generated
	 */
	EClass getArgument();

	/**
	 * Returns the meta object for the reference '{@link org.yakindu.base.expressions.expressions.Argument#getParameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parameter</em>'.
	 * @see org.yakindu.base.expressions.expressions.Argument#getParameter()
	 * @see #getArgument()
	 * @generated
	 */
	EReference getArgument_Parameter();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.Argument#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.yakindu.base.expressions.expressions.Argument#getValue()
	 * @see #getArgument()
	 * @generated
	 */
	EReference getArgument_Value();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.IfExpression <em>If Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>If Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.IfExpression
	 * @generated
	 */
	EClass getIfExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.IfExpression#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see org.yakindu.base.expressions.expressions.IfExpression#getCondition()
	 * @see #getIfExpression()
	 * @generated
	 */
	EReference getIfExpression_Condition();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.IfExpression#getThen <em>Then</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Then</em>'.
	 * @see org.yakindu.base.expressions.expressions.IfExpression#getThen()
	 * @see #getIfExpression()
	 * @generated
	 */
	EReference getIfExpression_Then();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.IfExpression#getElse <em>Else</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Else</em>'.
	 * @see org.yakindu.base.expressions.expressions.IfExpression#getElse()
	 * @see #getIfExpression()
	 * @generated
	 */
	EReference getIfExpression_Else();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.BlockExpression <em>Block Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Block Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.BlockExpression
	 * @generated
	 */
	EClass getBlockExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link org.yakindu.base.expressions.expressions.BlockExpression#getExpressions <em>Expressions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Expressions</em>'.
	 * @see org.yakindu.base.expressions.expressions.BlockExpression#getExpressions()
	 * @see #getBlockExpression()
	 * @generated
	 */
	EReference getBlockExpression_Expressions();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.WhileExpression <em>While Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>While Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.WhileExpression
	 * @generated
	 */
	EClass getWhileExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.WhileExpression#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see org.yakindu.base.expressions.expressions.WhileExpression#getBody()
	 * @see #getWhileExpression()
	 * @generated
	 */
	EReference getWhileExpression_Body();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.WhileExpression#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see org.yakindu.base.expressions.expressions.WhileExpression#getCondition()
	 * @see #getWhileExpression()
	 * @generated
	 */
	EReference getWhileExpression_Condition();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.ReturnExpression <em>Return Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Return Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.ReturnExpression
	 * @generated
	 */
	EClass getReturnExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.ReturnExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.ReturnExpression#getExpression()
	 * @see #getReturnExpression()
	 * @generated
	 */
	EReference getReturnExpression_Expression();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.SwitchExpression <em>Switch Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Switch Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.SwitchExpression
	 * @generated
	 */
	EClass getSwitchExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.SwitchExpression#getSwitch <em>Switch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Switch</em>'.
	 * @see org.yakindu.base.expressions.expressions.SwitchExpression#getSwitch()
	 * @see #getSwitchExpression()
	 * @generated
	 */
	EReference getSwitchExpression_Switch();

	/**
	 * Returns the meta object for the containment reference list '{@link org.yakindu.base.expressions.expressions.SwitchExpression#getCases <em>Cases</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cases</em>'.
	 * @see org.yakindu.base.expressions.expressions.SwitchExpression#getCases()
	 * @see #getSwitchExpression()
	 * @generated
	 */
	EReference getSwitchExpression_Cases();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.SwitchExpression#getDefault <em>Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Default</em>'.
	 * @see org.yakindu.base.expressions.expressions.SwitchExpression#getDefault()
	 * @see #getSwitchExpression()
	 * @generated
	 */
	EReference getSwitchExpression_Default();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.SwitchCase <em>Switch Case</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Switch Case</em>'.
	 * @see org.yakindu.base.expressions.expressions.SwitchCase
	 * @generated
	 */
	EClass getSwitchCase();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.SwitchCase#getCase <em>Case</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Case</em>'.
	 * @see org.yakindu.base.expressions.expressions.SwitchCase#getCase()
	 * @see #getSwitchCase()
	 * @generated
	 */
	EReference getSwitchCase_Case();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.SwitchCase#getThen <em>Then</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Then</em>'.
	 * @see org.yakindu.base.expressions.expressions.SwitchCase#getThen()
	 * @see #getSwitchCase()
	 * @generated
	 */
	EReference getSwitchCase_Then();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.EventRaisingExpression <em>Event Raising Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Raising Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.EventRaisingExpression
	 * @generated
	 */
	EClass getEventRaisingExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.EventRaisingExpression#getEvent <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Event</em>'.
	 * @see org.yakindu.base.expressions.expressions.EventRaisingExpression#getEvent()
	 * @see #getEventRaisingExpression()
	 * @generated
	 */
	EReference getEventRaisingExpression_Event();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.EventRaisingExpression#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.yakindu.base.expressions.expressions.EventRaisingExpression#getValue()
	 * @see #getEventRaisingExpression()
	 * @generated
	 */
	EReference getEventRaisingExpression_Value();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.EventValueReferenceExpression <em>Event Value Reference Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Value Reference Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.EventValueReferenceExpression
	 * @generated
	 */
	EClass getEventValueReferenceExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.EventValueReferenceExpression#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.yakindu.base.expressions.expressions.EventValueReferenceExpression#getValue()
	 * @see #getEventValueReferenceExpression()
	 * @generated
	 */
	EReference getEventValueReferenceExpression_Value();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.ForExpression <em>For Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>For Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.ForExpression
	 * @generated
	 */
	EClass getForExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.ForExpression#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see org.yakindu.base.expressions.expressions.ForExpression#getBody()
	 * @see #getForExpression()
	 * @generated
	 */
	EReference getForExpression_Body();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.ForExpression#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see org.yakindu.base.expressions.expressions.ForExpression#getCondition()
	 * @see #getForExpression()
	 * @generated
	 */
	EReference getForExpression_Condition();

	/**
	 * Returns the meta object for the containment reference list '{@link org.yakindu.base.expressions.expressions.ForExpression#getVarDecls <em>Var Decls</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Var Decls</em>'.
	 * @see org.yakindu.base.expressions.expressions.ForExpression#getVarDecls()
	 * @see #getForExpression()
	 * @generated
	 */
	EReference getForExpression_VarDecls();

	/**
	 * Returns the meta object for the containment reference list '{@link org.yakindu.base.expressions.expressions.ForExpression#getVarUpdates <em>Var Updates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Var Updates</em>'.
	 * @see org.yakindu.base.expressions.expressions.ForExpression#getVarUpdates()
	 * @see #getForExpression()
	 * @generated
	 */
	EReference getForExpression_VarUpdates();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.ForVarDecl <em>For Var Decl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>For Var Decl</em>'.
	 * @see org.yakindu.base.expressions.expressions.ForVarDecl
	 * @generated
	 */
	EClass getForVarDecl();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.ThrowExpression <em>Throw Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Throw Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.ThrowExpression
	 * @generated
	 */
	EClass getThrowExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.ThrowExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.ThrowExpression#getExpression()
	 * @see #getThrowExpression()
	 * @generated
	 */
	EReference getThrowExpression_Expression();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.PostFixUnaryExpression <em>Post Fix Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Post Fix Unary Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.PostFixUnaryExpression
	 * @generated
	 */
	EClass getPostFixUnaryExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.yakindu.base.expressions.expressions.PostFixUnaryExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.yakindu.base.expressions.expressions.PostFixUnaryExpression#getOperator()
	 * @see #getPostFixUnaryExpression()
	 * @generated
	 */
	EAttribute getPostFixUnaryExpression_Operator();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.ArgumentExpression <em>Argument Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Argument Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.ArgumentExpression
	 * @generated
	 */
	EClass getArgumentExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link org.yakindu.base.expressions.expressions.ArgumentExpression#getArguments <em>Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Arguments</em>'.
	 * @see org.yakindu.base.expressions.expressions.ArgumentExpression#getArguments()
	 * @see #getArgumentExpression()
	 * @generated
	 */
	EReference getArgumentExpression_Arguments();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.BinaryExpression <em>Binary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binary Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.BinaryExpression
	 * @generated
	 */
	EClass getBinaryExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.BinaryExpression#getLeftOperand <em>Left Operand</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left Operand</em>'.
	 * @see org.yakindu.base.expressions.expressions.BinaryExpression#getLeftOperand()
	 * @see #getBinaryExpression()
	 * @generated
	 */
	EReference getBinaryExpression_LeftOperand();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.BinaryExpression#getRightOperand <em>Right Operand</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right Operand</em>'.
	 * @see org.yakindu.base.expressions.expressions.BinaryExpression#getRightOperand()
	 * @see #getBinaryExpression()
	 * @generated
	 */
	EReference getBinaryExpression_RightOperand();

	/**
	 * Returns the meta object for class '{@link org.yakindu.base.expressions.expressions.UnaryExpression <em>Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unary Expression</em>'.
	 * @see org.yakindu.base.expressions.expressions.UnaryExpression
	 * @generated
	 */
	EClass getUnaryExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.yakindu.base.expressions.expressions.UnaryExpression#getOperand <em>Operand</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Operand</em>'.
	 * @see org.yakindu.base.expressions.expressions.UnaryExpression#getOperand()
	 * @see #getUnaryExpression()
	 * @generated
	 */
	EReference getUnaryExpression_Operand();

	/**
	 * Returns the meta object for enum '{@link org.yakindu.base.expressions.expressions.AssignmentOperator <em>Assignment Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Assignment Operator</em>'.
	 * @see org.yakindu.base.expressions.expressions.AssignmentOperator
	 * @generated
	 */
	EEnum getAssignmentOperator();

	/**
	 * Returns the meta object for enum '{@link org.yakindu.base.expressions.expressions.ShiftOperator <em>Shift Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Shift Operator</em>'.
	 * @see org.yakindu.base.expressions.expressions.ShiftOperator
	 * @generated
	 */
	EEnum getShiftOperator();

	/**
	 * Returns the meta object for enum '{@link org.yakindu.base.expressions.expressions.AdditiveOperator <em>Additive Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Additive Operator</em>'.
	 * @see org.yakindu.base.expressions.expressions.AdditiveOperator
	 * @generated
	 */
	EEnum getAdditiveOperator();

	/**
	 * Returns the meta object for enum '{@link org.yakindu.base.expressions.expressions.MultiplicativeOperator <em>Multiplicative Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Multiplicative Operator</em>'.
	 * @see org.yakindu.base.expressions.expressions.MultiplicativeOperator
	 * @generated
	 */
	EEnum getMultiplicativeOperator();

	/**
	 * Returns the meta object for enum '{@link org.yakindu.base.expressions.expressions.UnaryOperator <em>Unary Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Unary Operator</em>'.
	 * @see org.yakindu.base.expressions.expressions.UnaryOperator
	 * @generated
	 */
	EEnum getUnaryOperator();

	/**
	 * Returns the meta object for enum '{@link org.yakindu.base.expressions.expressions.RelationalOperator <em>Relational Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Relational Operator</em>'.
	 * @see org.yakindu.base.expressions.expressions.RelationalOperator
	 * @generated
	 */
	EEnum getRelationalOperator();

	/**
	 * Returns the meta object for enum '{@link org.yakindu.base.expressions.expressions.LogicalOperator <em>Logical Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Logical Operator</em>'.
	 * @see org.yakindu.base.expressions.expressions.LogicalOperator
	 * @generated
	 */
	EEnum getLogicalOperator();

	/**
	 * Returns the meta object for enum '{@link org.yakindu.base.expressions.expressions.BitwiseOperator <em>Bitwise Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Bitwise Operator</em>'.
	 * @see org.yakindu.base.expressions.expressions.BitwiseOperator
	 * @generated
	 */
	EEnum getBitwiseOperator();

	/**
	 * Returns the meta object for enum '{@link org.yakindu.base.expressions.expressions.PostFixOperator <em>Post Fix Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Post Fix Operator</em>'.
	 * @see org.yakindu.base.expressions.expressions.PostFixOperator
	 * @generated
	 */
	EEnum getPostFixOperator();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ExpressionsFactory getExpressionsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.LiteralImpl <em>Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.LiteralImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getLiteral()
		 * @generated
		 */
		EClass LITERAL = eINSTANCE.getLiteral();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.BoolLiteralImpl <em>Bool Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.BoolLiteralImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getBoolLiteral()
		 * @generated
		 */
		EClass BOOL_LITERAL = eINSTANCE.getBoolLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOL_LITERAL__VALUE = eINSTANCE.getBoolLiteral_Value();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.IntLiteralImpl <em>Int Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.IntLiteralImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getIntLiteral()
		 * @generated
		 */
		EClass INT_LITERAL = eINSTANCE.getIntLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INT_LITERAL__VALUE = eINSTANCE.getIntLiteral_Value();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.DoubleLiteralImpl <em>Double Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.DoubleLiteralImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getDoubleLiteral()
		 * @generated
		 */
		EClass DOUBLE_LITERAL = eINSTANCE.getDoubleLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOUBLE_LITERAL__VALUE = eINSTANCE.getDoubleLiteral_Value();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.FloatLiteralImpl <em>Float Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.FloatLiteralImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getFloatLiteral()
		 * @generated
		 */
		EClass FLOAT_LITERAL = eINSTANCE.getFloatLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOAT_LITERAL__VALUE = eINSTANCE.getFloatLiteral_Value();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.HexLiteralImpl <em>Hex Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.HexLiteralImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getHexLiteral()
		 * @generated
		 */
		EClass HEX_LITERAL = eINSTANCE.getHexLiteral();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.BinaryLiteralImpl <em>Binary Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.BinaryLiteralImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getBinaryLiteral()
		 * @generated
		 */
		EClass BINARY_LITERAL = eINSTANCE.getBinaryLiteral();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.StringLiteralImpl <em>String Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.StringLiteralImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getStringLiteral()
		 * @generated
		 */
		EClass STRING_LITERAL = eINSTANCE.getStringLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_LITERAL__VALUE = eINSTANCE.getStringLiteral_Value();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.NullLiteralImpl <em>Null Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.NullLiteralImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getNullLiteral()
		 * @generated
		 */
		EClass NULL_LITERAL = eINSTANCE.getNullLiteral();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.AssignmentExpressionImpl <em>Assignment Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.AssignmentExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getAssignmentExpression()
		 * @generated
		 */
		EClass ASSIGNMENT_EXPRESSION = eINSTANCE.getAssignmentExpression();

		/**
		 * The meta object literal for the '<em><b>Var Ref</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSIGNMENT_EXPRESSION__VAR_REF = eINSTANCE.getAssignmentExpression_VarRef();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSIGNMENT_EXPRESSION__OPERATOR = eINSTANCE.getAssignmentExpression_Operator();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSIGNMENT_EXPRESSION__EXPRESSION = eINSTANCE.getAssignmentExpression_Expression();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.ConditionalExpressionImpl <em>Conditional Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.ConditionalExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getConditionalExpression()
		 * @generated
		 */
		EClass CONDITIONAL_EXPRESSION = eINSTANCE.getConditionalExpression();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_EXPRESSION__CONDITION = eINSTANCE.getConditionalExpression_Condition();

		/**
		 * The meta object literal for the '<em><b>True Case</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_EXPRESSION__TRUE_CASE = eINSTANCE.getConditionalExpression_TrueCase();

		/**
		 * The meta object literal for the '<em><b>False Case</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_EXPRESSION__FALSE_CASE = eINSTANCE.getConditionalExpression_FalseCase();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.LogicalOrExpressionImpl <em>Logical Or Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.LogicalOrExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getLogicalOrExpression()
		 * @generated
		 */
		EClass LOGICAL_OR_EXPRESSION = eINSTANCE.getLogicalOrExpression();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.LogicalAndExpressionImpl <em>Logical And Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.LogicalAndExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getLogicalAndExpression()
		 * @generated
		 */
		EClass LOGICAL_AND_EXPRESSION = eINSTANCE.getLogicalAndExpression();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.LogicalNotExpressionImpl <em>Logical Not Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.LogicalNotExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getLogicalNotExpression()
		 * @generated
		 */
		EClass LOGICAL_NOT_EXPRESSION = eINSTANCE.getLogicalNotExpression();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.BitwiseXorExpressionImpl <em>Bitwise Xor Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.BitwiseXorExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getBitwiseXorExpression()
		 * @generated
		 */
		EClass BITWISE_XOR_EXPRESSION = eINSTANCE.getBitwiseXorExpression();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.BitwiseOrExpressionImpl <em>Bitwise Or Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.BitwiseOrExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getBitwiseOrExpression()
		 * @generated
		 */
		EClass BITWISE_OR_EXPRESSION = eINSTANCE.getBitwiseOrExpression();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.BitwiseAndExpressionImpl <em>Bitwise And Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.BitwiseAndExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getBitwiseAndExpression()
		 * @generated
		 */
		EClass BITWISE_AND_EXPRESSION = eINSTANCE.getBitwiseAndExpression();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.LogicalRelationExpressionImpl <em>Logical Relation Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.LogicalRelationExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getLogicalRelationExpression()
		 * @generated
		 */
		EClass LOGICAL_RELATION_EXPRESSION = eINSTANCE.getLogicalRelationExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOGICAL_RELATION_EXPRESSION__OPERATOR = eINSTANCE.getLogicalRelationExpression_Operator();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.ShiftExpressionImpl <em>Shift Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.ShiftExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getShiftExpression()
		 * @generated
		 */
		EClass SHIFT_EXPRESSION = eINSTANCE.getShiftExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHIFT_EXPRESSION__OPERATOR = eINSTANCE.getShiftExpression_Operator();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.NumericalAddSubtractExpressionImpl <em>Numerical Add Subtract Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.NumericalAddSubtractExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getNumericalAddSubtractExpression()
		 * @generated
		 */
		EClass NUMERICAL_ADD_SUBTRACT_EXPRESSION = eINSTANCE.getNumericalAddSubtractExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NUMERICAL_ADD_SUBTRACT_EXPRESSION__OPERATOR = eINSTANCE.getNumericalAddSubtractExpression_Operator();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.NumericalMultiplyDivideExpressionImpl <em>Numerical Multiply Divide Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.NumericalMultiplyDivideExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getNumericalMultiplyDivideExpression()
		 * @generated
		 */
		EClass NUMERICAL_MULTIPLY_DIVIDE_EXPRESSION = eINSTANCE.getNumericalMultiplyDivideExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NUMERICAL_MULTIPLY_DIVIDE_EXPRESSION__OPERATOR = eINSTANCE.getNumericalMultiplyDivideExpression_Operator();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.NumericalUnaryExpressionImpl <em>Numerical Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.NumericalUnaryExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getNumericalUnaryExpression()
		 * @generated
		 */
		EClass NUMERICAL_UNARY_EXPRESSION = eINSTANCE.getNumericalUnaryExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NUMERICAL_UNARY_EXPRESSION__OPERATOR = eINSTANCE.getNumericalUnaryExpression_Operator();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.PrimitiveValueExpressionImpl <em>Primitive Value Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.PrimitiveValueExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getPrimitiveValueExpression()
		 * @generated
		 */
		EClass PRIMITIVE_VALUE_EXPRESSION = eINSTANCE.getPrimitiveValueExpression();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIMITIVE_VALUE_EXPRESSION__VALUE = eINSTANCE.getPrimitiveValueExpression_Value();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.FeatureCallImpl <em>Feature Call</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.FeatureCallImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getFeatureCall()
		 * @generated
		 */
		EClass FEATURE_CALL = eINSTANCE.getFeatureCall();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_CALL__OWNER = eINSTANCE.getFeatureCall_Owner();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_CALL__FEATURE = eINSTANCE.getFeatureCall_Feature();

		/**
		 * The meta object literal for the '<em><b>Operation Call</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_CALL__OPERATION_CALL = eINSTANCE.getFeatureCall_OperationCall();

		/**
		 * The meta object literal for the '<em><b>Array Selector</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_CALL__ARRAY_SELECTOR = eINSTANCE.getFeatureCall_ArraySelector();

		/**
		 * The meta object literal for the '<em><b>Array Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_CALL__ARRAY_ACCESS = eINSTANCE.getFeatureCall_ArrayAccess();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.MetaCallImpl <em>Meta Call</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.MetaCallImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getMetaCall()
		 * @generated
		 */
		EClass META_CALL = eINSTANCE.getMetaCall();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.ElementReferenceExpressionImpl <em>Element Reference Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.ElementReferenceExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getElementReferenceExpression()
		 * @generated
		 */
		EClass ELEMENT_REFERENCE_EXPRESSION = eINSTANCE.getElementReferenceExpression();

		/**
		 * The meta object literal for the '<em><b>Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_REFERENCE_EXPRESSION__REFERENCE = eINSTANCE.getElementReferenceExpression_Reference();

		/**
		 * The meta object literal for the '<em><b>Operation Call</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_REFERENCE_EXPRESSION__OPERATION_CALL = eINSTANCE.getElementReferenceExpression_OperationCall();

		/**
		 * The meta object literal for the '<em><b>Array Selector</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_REFERENCE_EXPRESSION__ARRAY_SELECTOR = eINSTANCE.getElementReferenceExpression_ArraySelector();

		/**
		 * The meta object literal for the '<em><b>Array Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_REFERENCE_EXPRESSION__ARRAY_ACCESS = eINSTANCE.getElementReferenceExpression_ArrayAccess();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.ParenthesizedExpressionImpl <em>Parenthesized Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.ParenthesizedExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getParenthesizedExpression()
		 * @generated
		 */
		EClass PARENTHESIZED_EXPRESSION = eINSTANCE.getParenthesizedExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARENTHESIZED_EXPRESSION__EXPRESSION = eINSTANCE.getParenthesizedExpression_Expression();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.TypeCastExpressionImpl <em>Type Cast Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.TypeCastExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getTypeCastExpression()
		 * @generated
		 */
		EClass TYPE_CAST_EXPRESSION = eINSTANCE.getTypeCastExpression();

		/**
		 * The meta object literal for the '<em><b>Operand</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_CAST_EXPRESSION__OPERAND = eINSTANCE.getTypeCastExpression_Operand();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_CAST_EXPRESSION__TYPE = eINSTANCE.getTypeCastExpression_Type();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.ArgumentImpl <em>Argument</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.ArgumentImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getArgument()
		 * @generated
		 */
		EClass ARGUMENT = eINSTANCE.getArgument();

		/**
		 * The meta object literal for the '<em><b>Parameter</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARGUMENT__PARAMETER = eINSTANCE.getArgument_Parameter();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARGUMENT__VALUE = eINSTANCE.getArgument_Value();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.IfExpressionImpl <em>If Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.IfExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getIfExpression()
		 * @generated
		 */
		EClass IF_EXPRESSION = eINSTANCE.getIfExpression();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IF_EXPRESSION__CONDITION = eINSTANCE.getIfExpression_Condition();

		/**
		 * The meta object literal for the '<em><b>Then</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IF_EXPRESSION__THEN = eINSTANCE.getIfExpression_Then();

		/**
		 * The meta object literal for the '<em><b>Else</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IF_EXPRESSION__ELSE = eINSTANCE.getIfExpression_Else();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.BlockExpressionImpl <em>Block Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.BlockExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getBlockExpression()
		 * @generated
		 */
		EClass BLOCK_EXPRESSION = eINSTANCE.getBlockExpression();

		/**
		 * The meta object literal for the '<em><b>Expressions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK_EXPRESSION__EXPRESSIONS = eINSTANCE.getBlockExpression_Expressions();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.WhileExpressionImpl <em>While Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.WhileExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getWhileExpression()
		 * @generated
		 */
		EClass WHILE_EXPRESSION = eINSTANCE.getWhileExpression();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WHILE_EXPRESSION__BODY = eINSTANCE.getWhileExpression_Body();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WHILE_EXPRESSION__CONDITION = eINSTANCE.getWhileExpression_Condition();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.ReturnExpressionImpl <em>Return Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.ReturnExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getReturnExpression()
		 * @generated
		 */
		EClass RETURN_EXPRESSION = eINSTANCE.getReturnExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RETURN_EXPRESSION__EXPRESSION = eINSTANCE.getReturnExpression_Expression();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.SwitchExpressionImpl <em>Switch Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.SwitchExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getSwitchExpression()
		 * @generated
		 */
		EClass SWITCH_EXPRESSION = eINSTANCE.getSwitchExpression();

		/**
		 * The meta object literal for the '<em><b>Switch</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SWITCH_EXPRESSION__SWITCH = eINSTANCE.getSwitchExpression_Switch();

		/**
		 * The meta object literal for the '<em><b>Cases</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SWITCH_EXPRESSION__CASES = eINSTANCE.getSwitchExpression_Cases();

		/**
		 * The meta object literal for the '<em><b>Default</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SWITCH_EXPRESSION__DEFAULT = eINSTANCE.getSwitchExpression_Default();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.SwitchCaseImpl <em>Switch Case</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.SwitchCaseImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getSwitchCase()
		 * @generated
		 */
		EClass SWITCH_CASE = eINSTANCE.getSwitchCase();

		/**
		 * The meta object literal for the '<em><b>Case</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SWITCH_CASE__CASE = eINSTANCE.getSwitchCase_Case();

		/**
		 * The meta object literal for the '<em><b>Then</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SWITCH_CASE__THEN = eINSTANCE.getSwitchCase_Then();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.EventRaisingExpressionImpl <em>Event Raising Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.EventRaisingExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getEventRaisingExpression()
		 * @generated
		 */
		EClass EVENT_RAISING_EXPRESSION = eINSTANCE.getEventRaisingExpression();

		/**
		 * The meta object literal for the '<em><b>Event</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_RAISING_EXPRESSION__EVENT = eINSTANCE.getEventRaisingExpression_Event();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_RAISING_EXPRESSION__VALUE = eINSTANCE.getEventRaisingExpression_Value();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.EventValueReferenceExpressionImpl <em>Event Value Reference Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.EventValueReferenceExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getEventValueReferenceExpression()
		 * @generated
		 */
		EClass EVENT_VALUE_REFERENCE_EXPRESSION = eINSTANCE.getEventValueReferenceExpression();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_VALUE_REFERENCE_EXPRESSION__VALUE = eINSTANCE.getEventValueReferenceExpression_Value();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.ForExpressionImpl <em>For Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.ForExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getForExpression()
		 * @generated
		 */
		EClass FOR_EXPRESSION = eINSTANCE.getForExpression();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FOR_EXPRESSION__BODY = eINSTANCE.getForExpression_Body();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FOR_EXPRESSION__CONDITION = eINSTANCE.getForExpression_Condition();

		/**
		 * The meta object literal for the '<em><b>Var Decls</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FOR_EXPRESSION__VAR_DECLS = eINSTANCE.getForExpression_VarDecls();

		/**
		 * The meta object literal for the '<em><b>Var Updates</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FOR_EXPRESSION__VAR_UPDATES = eINSTANCE.getForExpression_VarUpdates();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.ForVarDeclImpl <em>For Var Decl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.ForVarDeclImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getForVarDecl()
		 * @generated
		 */
		EClass FOR_VAR_DECL = eINSTANCE.getForVarDecl();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.ThrowExpressionImpl <em>Throw Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.ThrowExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getThrowExpression()
		 * @generated
		 */
		EClass THROW_EXPRESSION = eINSTANCE.getThrowExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THROW_EXPRESSION__EXPRESSION = eINSTANCE.getThrowExpression_Expression();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.PostFixUnaryExpressionImpl <em>Post Fix Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.PostFixUnaryExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getPostFixUnaryExpression()
		 * @generated
		 */
		EClass POST_FIX_UNARY_EXPRESSION = eINSTANCE.getPostFixUnaryExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POST_FIX_UNARY_EXPRESSION__OPERATOR = eINSTANCE.getPostFixUnaryExpression_Operator();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.ArgumentExpressionImpl <em>Argument Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.ArgumentExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getArgumentExpression()
		 * @generated
		 */
		EClass ARGUMENT_EXPRESSION = eINSTANCE.getArgumentExpression();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARGUMENT_EXPRESSION__ARGUMENTS = eINSTANCE.getArgumentExpression_Arguments();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.BinaryExpressionImpl <em>Binary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.BinaryExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getBinaryExpression()
		 * @generated
		 */
		EClass BINARY_EXPRESSION = eINSTANCE.getBinaryExpression();

		/**
		 * The meta object literal for the '<em><b>Left Operand</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_EXPRESSION__LEFT_OPERAND = eINSTANCE.getBinaryExpression_LeftOperand();

		/**
		 * The meta object literal for the '<em><b>Right Operand</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_EXPRESSION__RIGHT_OPERAND = eINSTANCE.getBinaryExpression_RightOperand();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.impl.UnaryExpressionImpl <em>Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.impl.UnaryExpressionImpl
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getUnaryExpression()
		 * @generated
		 */
		EClass UNARY_EXPRESSION = eINSTANCE.getUnaryExpression();

		/**
		 * The meta object literal for the '<em><b>Operand</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNARY_EXPRESSION__OPERAND = eINSTANCE.getUnaryExpression_Operand();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.AssignmentOperator <em>Assignment Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.AssignmentOperator
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getAssignmentOperator()
		 * @generated
		 */
		EEnum ASSIGNMENT_OPERATOR = eINSTANCE.getAssignmentOperator();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.ShiftOperator <em>Shift Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.ShiftOperator
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getShiftOperator()
		 * @generated
		 */
		EEnum SHIFT_OPERATOR = eINSTANCE.getShiftOperator();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.AdditiveOperator <em>Additive Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.AdditiveOperator
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getAdditiveOperator()
		 * @generated
		 */
		EEnum ADDITIVE_OPERATOR = eINSTANCE.getAdditiveOperator();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.MultiplicativeOperator <em>Multiplicative Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.MultiplicativeOperator
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getMultiplicativeOperator()
		 * @generated
		 */
		EEnum MULTIPLICATIVE_OPERATOR = eINSTANCE.getMultiplicativeOperator();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.UnaryOperator <em>Unary Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.UnaryOperator
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getUnaryOperator()
		 * @generated
		 */
		EEnum UNARY_OPERATOR = eINSTANCE.getUnaryOperator();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.RelationalOperator <em>Relational Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.RelationalOperator
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getRelationalOperator()
		 * @generated
		 */
		EEnum RELATIONAL_OPERATOR = eINSTANCE.getRelationalOperator();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.LogicalOperator <em>Logical Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.LogicalOperator
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getLogicalOperator()
		 * @generated
		 */
		EEnum LOGICAL_OPERATOR = eINSTANCE.getLogicalOperator();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.BitwiseOperator <em>Bitwise Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.BitwiseOperator
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getBitwiseOperator()
		 * @generated
		 */
		EEnum BITWISE_OPERATOR = eINSTANCE.getBitwiseOperator();

		/**
		 * The meta object literal for the '{@link org.yakindu.base.expressions.expressions.PostFixOperator <em>Post Fix Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.yakindu.base.expressions.expressions.PostFixOperator
		 * @see org.yakindu.base.expressions.expressions.impl.ExpressionsPackageImpl#getPostFixOperator()
		 * @generated
		 */
		EEnum POST_FIX_OPERATOR = eINSTANCE.getPostFixOperator();

	}

} //ExpressionsPackage
