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

public class ConnectionManagementIT
{
    private final K3poRule k3po = new K3poRule()
        .addScriptRoot("spec", "org/reaktivity/specification/http2/rfc7540/connection.management");

    private final TestRule timeout = new DisableOnDebug(new Timeout(10, SECONDS));

    @Rule
    public final TestRule chain = outerRule(k3po).around(timeout);

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/connection.established/client",
            "${spec}/connection.established/server",
    })
    public void connectionEstablished() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/http.get.exchange/client",
            "${spec}/http.get.exchange/server",
    })
    public void httpGetExchange() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/http.unknown.authority/client",
            "${spec}/http.unknown.authority/server",
    })
    public void httpUnknownAuthority() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/http.post.exchange/client",
            "${spec}/http.post.exchange/server",
    })
    public void httpPostExchange() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/multiple.data.frames/client",
            "${spec}/multiple.data.frames/server",
    })
    public void multipleDataFrames() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/connection.has.two.streams/client",
            "${spec}/connection.has.two.streams/server",
    })
    public void connectionHasTwoStreams() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/http.push.promise/client",
            "${spec}/http.push.promise/server",
    })
    public void pushResources() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/push.promise.on.different.stream/client",
            "${spec}/push.promise.on.different.stream/server",
    })
    public void pushPromiseOnDifferentStream() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/reset.http2.stream/client",
            "${spec}/reset.http2.stream/server",
    })
    public void resetHttp2Stream() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/ignore.rst.stream/client",
            "${spec}/ignore.rst.stream/server",
    })
    public void ignoreRstStream() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/client.sent.read.abort.on.open.request/client",
            "${spec}/client.sent.read.abort.on.open.request/server"
    })
    public void clientSentReadAbortOnOpenRequest() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/rst.stream.last.frame/client",
            "${spec}/rst.stream.last.frame/server"
    })
    public void rstStreamLastFrame() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/client.sent.read.abort.on.closed.request/client",
            "${spec}/client.sent.read.abort.on.closed.request/server"
    })
    public void clientSentReadAbortOnClosedRequest() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/client.sent.write.abort.on.open.request/client",
            "${spec}/client.sent.write.abort.on.open.request/server"
    })
    public void clientSentWriteAbortOnOpenRequest() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/client.sent.write.abort.on.closed.request/client",
            "${spec}/client.sent.write.abort.on.closed.request/server"
    })
    public void clientSentWriteAbortOnClosedRequest() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/client.sent.write.close/client",
            "${spec}/client.sent.write.close/server"
    })
    public void clientSentWriteClose() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/server.sent.read.abort.on.open.request/client",
            "${spec}/server.sent.read.abort.on.open.request/server"
    })
    public void serverSentReadAbortOnOpenRequest() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/server.sent.read.abort.before.correlated/client",
            "${spec}/server.sent.read.abort.before.correlated/server"
    })
    public void serverSentReadAbortBeforeCorrelated() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/server.sent.write.abort.on.open.request/client",
            "${spec}/server.sent.write.abort.on.open.request/server"
    })
    public void serverSentWriteAbortOnOpenRequest() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/server.sent.write.abort.on.closed.request/client",
            "${spec}/server.sent.write.abort.on.closed.request/server"
    })
    public void serverSentWriteAbortOnClosedRequest() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/server.sent.write.close/client",
            "${spec}/server.sent.write.close/server"
    })
    public void serverSentWriteClose() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
            "${spec}/http.authority.default.port/client",
            "${spec}/http.authority.default.port/server",
    })
    public void authorityWithoutPort() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/http2#0\"")
    @Specification({
        "${spec}/http.response.trailer/client",
        "${spec}/http.response.trailer/server",
    })
    public void shouldProxyResponseTrailer() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }
}
