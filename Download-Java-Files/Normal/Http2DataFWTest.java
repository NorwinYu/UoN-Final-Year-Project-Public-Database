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

import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.reaktivity.nukleus.http2.internal.types.stream.Http2FrameType.DATA;

public class Http2DataFWTest
{
    @Test
    public void encode()
    {
        DirectBuffer payload = new UnsafeBuffer(new byte[] {0, 1, 2, 3, 4, 5});
        byte[] bytes = new byte[1 + 9 + payload.capacity()];
        MutableDirectBuffer buf = new UnsafeBuffer(bytes);

        Http2DataFW fw = new Http2DataFW.Builder()
                .wrap(buf, 1, buf.capacity())   // non-zero offset
                .endStream()
                .streamId(3)
                .payload(payload)
                .build();

        assertEquals(6, fw.payloadLength());
        assertEquals(1, fw.offset());
        assertEquals(16, fw.limit());
        assertTrue(fw.endStream());
        assertEquals(DATA, fw.type());
        assertEquals(3, fw.streamId());
        assertEquals(payload, fw.data());
    }

    @Test
    public void encodeEmpty()
    {
        DirectBuffer payload = new UnsafeBuffer(new byte[0]);
        byte[] bytes = new byte[1 + 9 + payload.capacity()];
        MutableDirectBuffer buf = new UnsafeBuffer(bytes);

        Http2DataFW fw = new Http2DataFW.Builder()
                .wrap(buf, 1, buf.capacity())   // non-zero offset
                .endStream()
                .streamId(3)
                .payload(payload)
                .build();

        assertEquals(0, fw.payloadLength());
        assertEquals(1, fw.offset());
        assertEquals(10, fw.limit());
        assertTrue(fw.endStream());
        assertEquals(DATA, fw.type());
        assertEquals(3, fw.streamId());
        assertEquals(payload, fw.data());
    }
}
