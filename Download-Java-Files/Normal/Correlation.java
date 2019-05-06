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

import java.util.Objects;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;

import org.reaktivity.nukleus.http2.internal.types.stream.HpackContext;

public class Correlation
{
    final PromisedRequestHandler pushHandler;
    final long id;
    final int http2StreamId;
    final IntSupplier promisedStreamIds;
    final long sourceOutputEstId;
    final HpackContext encodeContext;
    final IntUnaryOperator pushStreamIds;
    final WriteScheduler writeScheduler;
    final Http2Connection http2Connection;

    public Correlation(
        long id,
        long sourceOutputEstId,
        WriteScheduler writeScheduler,
        PromisedRequestHandler pushHandler,
        Http2Connection http2Connection,
        int http2StreamId,
        HpackContext encodeContext,
        IntSupplier promisedStreamIds,
        IntUnaryOperator pushStreamIds)
    {
        this.id = id;
        this.sourceOutputEstId = sourceOutputEstId;
        this.writeScheduler = writeScheduler;
        this.pushHandler = pushHandler;
        this.http2Connection = http2Connection;
        this.http2StreamId = http2StreamId;
        this.encodeContext = encodeContext;
        this.promisedStreamIds = promisedStreamIds;
        this.pushStreamIds = pushStreamIds;
    }

    public long id()
    {
        return id;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, sourceOutputEstId, http2StreamId);
    }

    @Override
    public boolean equals(
            Object obj)
    {
        if (!(obj instanceof Correlation))
        {
            return false;
        }

        Correlation that = (Correlation) obj;
        return this.id == that.id &&
                this.sourceOutputEstId == that.sourceOutputEstId &&
                this.http2StreamId == that.http2StreamId;
    }

    @Override
    public String toString()
    {
        return String.format("[id=%s, sourceOutputEstId=%s http2StreamId=%s]",
                id, sourceOutputEstId, http2StreamId);
    }

}
