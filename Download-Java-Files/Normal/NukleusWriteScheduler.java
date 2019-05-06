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
import org.reaktivity.nukleus.function.MessageConsumer;
import org.reaktivity.nukleus.http2.internal.types.Flyweight;
import org.reaktivity.nukleus.http2.internal.types.stream.DataFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2FrameType;

class NukleusWriteScheduler
{
    private final Http2Connection connection;
    private final Http2Writer http2Writer;
    private final long networkRouteId;
    private final long networkReplyId;
    private final MessageConsumer networkReply;
    private final MutableDirectBuffer writeBuffer;

    private long traceId;
    private int accumulatedLength;

    NukleusWriteScheduler(
        Http2Connection connection,
        MessageConsumer networkReply,
        Http2Writer http2Writer,
        long networkRouteId,
        long networkReplyId)
    {
        this.connection = connection;
        this.networkReply = networkReply;
        this.http2Writer = http2Writer;
        this.networkRouteId = networkRouteId;
        this.networkReplyId = networkReplyId;
        this.writeBuffer = http2Writer.writeBuffer;
    }

    int http2Frame(
        long traceId,
        int lengthGuess,
        Flyweight.Builder.Visitor visitor)
    {
        int length = visitor.visit(writeBuffer, DataFW.FIELD_OFFSET_PAYLOAD + accumulatedLength, lengthGuess);
        if (this.traceId == 0)
        {
            // pick one traceId as multiple reply data frames are assembled here
            this.traceId = traceId;
        }
        accumulatedLength += length;

        return length;
    }

    int offset()
    {
        return DataFW.FIELD_OFFSET_PAYLOAD + accumulatedLength;
    }

    void writtenHttp2Frame(
        Http2FrameType type,
        int length)
    {
        accumulatedLength += length;
    }

    void doEnd()
    {
        http2Writer.doEnd(networkReply, networkRouteId, networkReplyId);
    }

    void flush()
    {
        if (accumulatedLength > 0)
        {
            http2Writer.doData(networkReply, networkRouteId, networkReplyId, traceId, connection.networkReplyPadding,
                    writeBuffer, DataFW.FIELD_OFFSET_PAYLOAD, accumulatedLength);

            // Every nukleus DATA frame incurs padding overhead
            connection.networkReplyBudget -= accumulatedLength + connection.networkReplyPadding;
            assert connection.networkReplyBudget >= 0;

            traceId = 0;
            accumulatedLength = 0;
        }
    }

    boolean fits(
        int sizeof)
    {
        int candidateSizeof = accumulatedLength + sizeof;

        // Every nukleus DATA frame incurs padding overhead
        return candidateSizeof + connection.networkReplyPadding <= connection.networkReplyBudget;
    }

    int remaining()
    {
        // Every nukleus DATA frame incurs padding overhead
        int remaining = connection.networkReplyBudget - (accumulatedLength + connection.networkReplyPadding);
        return Math.max(remaining, 0);
    }

}
