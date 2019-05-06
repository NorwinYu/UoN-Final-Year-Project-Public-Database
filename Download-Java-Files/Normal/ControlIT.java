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
package org.reaktivity.specification.nukleus.http2.control;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.rules.RuleChain.outerRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.kaazing.k3po.junit.annotation.Specification;
import org.kaazing.k3po.junit.rules.K3poRule;

public class ControlIT
{
    private final K3poRule k3po = new K3poRule();

    private final TestRule timeout = new DisableOnDebug(new Timeout(5, SECONDS));

    @Rule
    public final TestRule chain = outerRule(k3po).around(timeout);

    @Test
    @Specification({
        "route/server/nukleus",
        "route/server/controller"
    })
    public void shouldRouteServer() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "route/client/nukleus",
        "route/client/controller"
    })
    public void shouldRouteClient() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "unroute/server/nukleus",
        "unroute/server/controller"
    })
    public void shouldUnrouteServer() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "unroute/client/nukleus",
        "unroute/client/controller"
    })
    public void shouldUnrouteClient() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @Specification({
        "route/server.authority/nukleus",
        "route/server.authority/controller"
    })
    public void shouldRouteServerUsingAuthorityWithDefaultPort() throws Exception
    {
        k3po.finish();
    }
}
