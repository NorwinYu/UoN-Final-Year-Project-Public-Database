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
package org.reaktivity.nukleus.http2.internal;

import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CircularDirectBufferTest
{

    @Test
    public void add()
    {
        int capacity = 100;
        MutableDirectBuffer src = new UnsafeBuffer(new byte[capacity]);
        MutableDirectBuffer dst = new UnsafeBuffer(new byte[capacity]);

        // will test all boundaries with start of the buffer at i
        for(int i=0; i < 100; i++)
        {
            CircularDirectBuffer cb = new CircularDirectBuffer(capacity);
            assertTrue(cb.write(dst, src, 0, i));
            assertEquals(i, read(cb, i));

            // now start is at i
            assertTrue(cb.write(dst, src, 0, 20));
            assertTrue(cb.write(dst, src, 0, 30));
            assertTrue(cb.write(dst, src, 0, 40));
            assertTrue(cb.write(dst, src, 0, 10));
            assertFalse(cb.write(dst, src, 0, 20));

            assertEquals(5, read(cb, 5));
            assertEquals(20, read(cb, 20));
            assertEquals(15, read(cb, 15));
            assertEquals(30, read(cb, 30));
            assertEquals(30, read(cb, 30));
        }
    }

    private int read(CircularDirectBuffer cb, int length)
    {
        int part1 = cb.read(length);
        int part2 = 0;

        if (part1 != length)
        {
            part2 = cb.read(length-part1);
        }
        return part1 + part2;
    }

    @Test
    public void writeContiguous()
    {
        int capacity = 100;
        MutableDirectBuffer src = new UnsafeBuffer(new byte[capacity]);
        MutableDirectBuffer dst = new UnsafeBuffer(new byte[capacity]);

        // will test all boundaries with start of the buffer at i
        for(int i=0; i < 100; i++)
        {
            CircularDirectBuffer cb = new CircularDirectBuffer(capacity);
            assertEquals(i, cb.writeContiguous(dst, src, 0, i));
            assertEquals(i, read(cb, i));

            // now start is at i
            assertEquals(20, write(cb, dst, src, 0, 20));
            assertEquals(30, write(cb, dst, src, 0, 30));
            assertEquals(40, write(cb, dst, src, 0, 40));
            assertEquals(10, write(cb, dst, src, 0, 10));

            assertEquals(5, read(cb, 5));
            assertEquals(20, read(cb, 20));
            assertEquals(15, read(cb, 15));
            assertEquals(30, read(cb, 30));
            assertEquals(30, read(cb, 30));
        }
    }

    private int write(CircularDirectBuffer cb, MutableDirectBuffer dst, MutableDirectBuffer src, int index, int length)
    {
        int part1 = cb.writeContiguous(dst, src, index, length);
        int part2 = 0;

        if (part1 != length)
        {
            part2 = cb.writeContiguous(dst, src, index, length-part1);
        }

        return part1 + part2;
    }

}
