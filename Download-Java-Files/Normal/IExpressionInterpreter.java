/**
 * Copyright (c) 2011 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     committers of YAKINDU - initial API and implementation
 */
package org.yakindu.base.expressions.interpreter;

import org.yakindu.base.types.Expression;
import org.yakindu.sct.model.sruntime.ExecutionContext;

import com.google.inject.ImplementedBy;

/**
 * 
 * @author andreas muelder - Initial contribution and API
 * 
 */
@ImplementedBy(DefaultExpressionInterpreter.class)
public interface IExpressionInterpreter {
	
	public Object evaluate(Expression expression, ExecutionContext context);

}
