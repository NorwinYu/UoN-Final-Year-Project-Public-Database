/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ******************************************************************************/
package com.pogeyan.cmis.impl.uri.expression;

import java.util.ArrayList;
import java.util.List;

import com.pogeyan.cmis.api.uri.expression.CommonExpression;
import com.pogeyan.cmis.api.uri.expression.ExceptionVisitExpression;
import com.pogeyan.cmis.api.uri.expression.ExpressionKind;
import com.pogeyan.cmis.api.uri.expression.ExpressionVisitor;
import com.pogeyan.cmis.api.uri.expression.MethodExpression;
import com.pogeyan.cmis.api.uri.expression.MethodOperator;

/**
 *  
 */
public class MethodExpressionImpl implements MethodExpression {

	private InfoMethod infoMethod;
	private List<CommonExpression> actualParameters;

	public MethodExpressionImpl(final InfoMethod infoMethod) {
		this.infoMethod = infoMethod;
		actualParameters = new ArrayList<CommonExpression>();
	}

	@Override
	public MethodOperator getMethod() {
		return infoMethod.getMethod();
	}

	public InfoMethod getMethodInfo() {
		return infoMethod;
	}

	@Override
	public List<CommonExpression> getParameters() {
		return actualParameters;
	}

	@Override
	public int getParameterCount() {
		return actualParameters.size();
	}

	/**
	 * @param expression
	 * @return A self reference for method chaining"
	 */
	public MethodExpressionImpl appendParameter(final CommonExpression expression) {
		actualParameters.add(expression);
		return this;
	}

	@Override
	public ExpressionKind getKind() {
		return ExpressionKind.METHOD;
	}

	@Override
	public String getUriLiteral() {
		return infoMethod.getSyntax();
	}

	@Override
	public Object accept(final ExpressionVisitor visitor) throws ExceptionVisitExpression {
		ArrayList<Object> retParameters = new ArrayList<Object>();
		for (CommonExpression parameter : actualParameters) {
			Object retParameter = parameter.accept(visitor);
			retParameters.add(retParameter);
		}

		Object ret = visitor.visitMethod(this, getMethod(), retParameters);
		return ret;
	}

}
