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
package org.reaktivity.specification.http2.rfc7540;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.kaazing.k3po.junit.annotation.ScriptProperty;
import org.kaazing.k3po.junit.annotation.Specification;
import org.kaazing.k3po.junit.rules.K3poRule;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.rules.RuleChain.outerRule;

public class MessageFormatIT
{
    private final K3poRule k3po = new K3poRule()
        .addScriptRoot("spec", "org/reaktivity/specification/http2/rfc7540/message.format");

    private final TestRule timeout = new DisableOnDebug(new Timeout(10, SECONDS));

    @Rule
    public final TestRule chain = outerRule(k3po).around(timeout);

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/continuation.frames/client",
            "${spec}/continuation.frames/server",
    })
    public void continuationFrames() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/dynamic.table.requests/client",
            "${spec}/dynamic.table.requests/server",
    })
    public void dynamicTableRequests() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/max.frame.size/client",
            "${spec}/max.frame.size/server",
    })
    public void maxFrameSize() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/max.frame.size.error/client",
            "${spec}/max.frame.size.error/server",
    })
    public void maxFrameSizeError() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/ping.frame.size.error/client",
            "${spec}/ping.frame.size.error/server"
    })
    public void pingFrameSizeError() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/connection.window.frame.size.error/client",
            "${spec}/connection.window.frame.size.error/server"
    })
    public void connectionWindowFrameSizeError() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/window.frame.size.error/client",
            "${spec}/window.frame.size.error/server"
    })
    public void windowFrameSizeError() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/rst.stream.frame.size.error/client",
            "${spec}/rst.stream.frame.size.error/server"
    })
    public void rstStreanFrameSizeError() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/priority.frame.size.error/client",
            "${spec}/priority.frame.size.error/server"
    })
    public void priorityFrameSizeError() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/max.nukleus.data.frame.size/client",
            "${spec}/max.nukleus.data.frame.size/server",
    })
    public void maxNukleusDataFrameSize() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/connection.headers/client",
            "${spec}/connection.headers/server",
    })
    public void connectionHeaders() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/stream.id.order/client",
            "${spec}/stream.id.order/server",
    })
    public void streamIdOrder() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/invalid.hpack.index/client",
            "${spec}/invalid.hpack.index/server",
    })
    public void invalidHpackIndex() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

}
