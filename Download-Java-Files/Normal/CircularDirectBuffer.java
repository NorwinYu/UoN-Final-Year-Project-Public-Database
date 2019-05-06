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

import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;

class CircularDirectBuffer
{
    private final int capacity;
    /*
     *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     *  |   |x x x x x x x x x x|         |
     *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     *       ^                   ^
     *       start               end
     *
     *
     *
     *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     *  |x x x|     |x x x x x x x|x|
     *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     *         ^     ^
     *       end     start
     */

    private int start;
    private int end;
    private int count;

    CircularDirectBuffer(int capacity)
    {
        this.capacity = capacity;
    }

    boolean write(MutableDirectBuffer dstBuffer, DirectBuffer srcBuffer, int srcIndex, int length)
    {
        if (count + length > capacity)
        {
            return false;
        }

        if (end + length > capacity)
        {
            int firstPart = capacity - end;
            int secondPart = length - firstPart;
            dstBuffer.putBytes(end, srcBuffer, srcIndex, firstPart);
            dstBuffer.putBytes(0, srcBuffer, srcIndex + firstPart, secondPart);
        }
        else
        {
            dstBuffer.putBytes(end, srcBuffer, srcIndex, length);
        }

        count += length;
        end = (end + length) % capacity;
        return true;
    }

    int writeContiguous(MutableDirectBuffer dstBuffer, DirectBuffer srcBuffer, int srcIndex, int length)
    {
        int part = (start <= end) ? Math.min(length, capacity - end) : Math.min(length, start - end);
        dstBuffer.putBytes(end, srcBuffer, srcIndex, part);
        count += part;
        end = (end + part) % capacity;
        return part;
    }

    int read(int length)
    {
        if (length > count)
        {
            throw new IllegalArgumentException();
        }

        int read = (start + length > capacity) ? capacity - start : length;
        count -= read;
        start = (start + read) % capacity;

        assert  read <= length;
        return read;
    }

    int readOffset()
    {
        return start;
    }

    int size()
    {
        return count;
    }

    public String toString()
    {
        return "[capacity = " + capacity + " (start = " + start + " end = " + end + ")]";
    }

}
