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
package org.reaktivity.nukleus.http2.internal.types.stream;

import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.reaktivity.nukleus.http2.internal.types.stream.Http2FrameType.PUSH_PROMISE;
import static org.reaktivity.nukleus.http2.internal.types.stream.HpackLiteralHeaderFieldFW.LiteralType.INCREMENTAL_INDEXING;

public class Http2PushPromiseFWTest
{

    @Test
    public void encode()
    {
        byte[] bytes = new byte[100];
        MutableDirectBuffer buf = new UnsafeBuffer(bytes);

        Http2PushPromiseFW push = new Http2PushPromiseFW.Builder()
                .wrap(buf, 1, buf.capacity())   // non-zero offset
                .streamId(1)
                .promisedStreamId(2)
                .header(h -> h.indexed(2))      // :method: GET
                .header(h -> h.indexed(6))      // :scheme: http
                .header(h -> h.literal(l -> l.type(INCREMENTAL_INDEXING).name(4).value("/resource1")))
                .header(h -> h.literal(l -> l.type(INCREMENTAL_INDEXING).name(1).value("www.example.com")))
                .endHeaders()
                .build();

        assertEquals(35, push.payloadLength());
        assertEquals(1, push.offset());
        assertEquals(45, push.limit());
        assertTrue(push.endHeaders());
        assertEquals(PUSH_PROMISE, push.type());
        assertEquals(1, push.streamId());
        assertEquals(2, push.promisedStreamId());


        Map<String, String> headers = new LinkedHashMap<>();
        HpackContext context = new HpackContext();
        push.forEach(HpackHeaderBlockFWTest.getHeaders(context, headers));

        assertEquals(4, headers.size());
        assertEquals("GET", headers.get(":method"));
        assertEquals("http", headers.get(":scheme"));
        assertEquals("/resource1", headers.get(":path"));
        assertEquals("www.example.com", headers.get(":authority"));
    }

}
