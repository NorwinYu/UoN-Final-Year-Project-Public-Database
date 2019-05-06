/**
 * Copyright 2016-2019 The Reaktivity Project
 *
 * The Reaktivity Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.reaktivity.specification.http2.internal;

import static org.kaazing.k3po.lang.internal.el.ExpressionFactoryUtils.newExpressionFactory;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import org.junit.Before;
import org.junit.Test;
import org.kaazing.k3po.lang.internal.el.ExpressionContext;

public class FunctionsTest
{

    private ExpressionFactory factory;
    private ELContext ctx;

    @Before
    public void setUp() throws Exception
    {

        factory = newExpressionFactory();
        ctx = new ExpressionContext();
    }

    @Test
    public void shouldInvokeFunctionRandomBytes() throws Exception
    {
        String expressionText = "${http2:randomBytes(42)}";
        ValueExpression expression = factory.createValueExpression(ctx, expressionText, String.class);
        expression.getValue(ctx);
    }
}
