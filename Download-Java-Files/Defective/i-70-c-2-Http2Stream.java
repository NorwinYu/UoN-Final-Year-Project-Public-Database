/**
 * Copyright 2016-2018 The Reaktivity Project
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
import org.reaktivity.nukleus.function.MessageConsumer;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2DataFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2ErrorCode;
import org.reaktivity.nukleus.http2.internal.types.stream.ResetFW;
import org.reaktivity.nukleus.http2.internal.types.stream.WindowFW;

import java.util.Deque;
import java.util.LinkedList;

import static org.reaktivity.nukleus.buffer.BufferPool.NO_SLOT;

class Http2Stream
{
    final Http2Connection connection;
    final HttpWriteScheduler httpWriteScheduler;
    final int http2StreamId;
    final int maxHeaderSize;
    final long applicationRouteId;
    final long applicationId;
    final long correlationId;
    final MessageConsumer applicationTarget;

    boolean endDeferred;
    Http2StreamState state;
    long http2OutWindow;
    long applicationReplyBudget;
    long http2InWindow;

    long contentLength;
    long totalData;

    private int replySlot = NO_SLOT;
    CircularDirectBuffer replyBuffer;
    Deque<WriteScheduler.Entry> replyQueue = new LinkedList<>();
    boolean endStream;

    long totalOutData;
    private ServerStreamFactory factory;

    MessageConsumer applicationReplyThrottle;
    long applicationReplyId;

    Http2Stream(
        ServerStreamFactory factory,
        Http2Connection connection,
        int http2StreamId,
        Http2StreamState state,
        long applicationRouteId,
        HttpWriter httpWriter)
    {
        this.factory = factory;
        this.connection = connection;
        this.http2StreamId = http2StreamId;
        this.applicationRouteId = applicationRouteId;
        this.applicationId = factory.supplyInitialId.applyAsLong(applicationRouteId);
        this.applicationTarget = connection.router.supplyReceiver(applicationId);
        this.correlationId = factory.supplyCorrelationId.getAsLong();
        this.http2InWindow = connection.localSettings.initialWindowSize;

        this.http2OutWindow = connection.remoteSettings.initialWindowSize;
        this.state = state;
        this.httpWriteScheduler = new HttpWriteScheduler(factory, applicationTarget, httpWriter,
                applicationRouteId, applicationId, this);
        // Setting the overhead to zero for now. Doesn't help when multiple streams are in picture
        this.maxHeaderSize = 0;     // maxHeaderSize();
    }

    // Estimate only - no of DATA frames + WINDOW frames
    private int maxHeaderSize()
    {
        int frameCount = (int) Math.ceil(factory.bufferPool.slotCapacity()/connection.remoteSettings.maxFrameSize) + 10;
        return frameCount * 9;
    }

    boolean isClientInitiated()
    {
        return http2StreamId%2 == 1;
    }

    void onHttpEnd(long traceId)
    {
        connection.writeScheduler.dataEos(traceId, http2StreamId);
        applicationReplyThrottle = null;

        factory.counters.dataFramesWritten.getAsLong();
   }

    void onHttpAbort()
    {
        // more request data to be sent, so send ABORT
        if (state != Http2StreamState.HALF_CLOSED_REMOTE)
        {
            httpWriteScheduler.doAbort(factory.supplyTrace.getAsLong());
        }

        connection.writeScheduler.rst(http2StreamId, Http2ErrorCode.NO_ERROR);

        factory.counters.resetStreamFramesWritten.getAsLong();

        connection.closeStream(this);
    }

    void onHttpReset()
    {
        // reset the response stream
        if (applicationReplyThrottle != null)
        {
            factory.doReset(applicationReplyThrottle, applicationRouteId, applicationReplyId, factory.supplyTrace.getAsLong());
        }

        if (factory.correlations.containsKey(correlationId))
        {
            connection.send404(http2StreamId);
        }
        else
        {
            connection.writeScheduler.rst(http2StreamId, Http2ErrorCode.NO_ERROR);
            factory.counters.resetStreamFramesWritten.getAsLong();
        }

        connection.closeStream(this);
    }

    void onData(
        long traceId,
        Http2DataFW http2Data)
    {
        boolean written = httpWriteScheduler.onData(traceId, http2Data);
        if (!written)
        {
            connection.writeScheduler.rst(http2StreamId, Http2ErrorCode.ENHANCE_YOUR_CALM);
            onAbort(traceId);

            factory.counters.resetStreamFramesWritten.getAsLong();
        }
    }

    void onError(long traceId)
    {
        // more request data to be sent, so send ABORT
        if (state != Http2StreamState.HALF_CLOSED_REMOTE)
        {
            httpWriteScheduler.doAbort(traceId);
        }

        // reset the response stream
        if (applicationReplyThrottle != null)
        {
            factory.doReset(applicationReplyThrottle, applicationRouteId, applicationReplyId, factory.supplyTrace.getAsLong());
        }

        close();
    }

    void onAbort(long traceId)
    {
        // more request data to be sent, so send ABORT
        if (state != Http2StreamState.HALF_CLOSED_REMOTE)
        {
            httpWriteScheduler.doAbort(traceId);
        }

        // reset the response stream
        if (applicationReplyThrottle != null)
        {
            factory.doReset(applicationReplyThrottle, applicationRouteId, applicationReplyId, factory.supplyTrace.getAsLong());
        }

        close();
    }

    void onReset(long networkReplyTraceId)
    {
        // reset the response stream
        if (applicationReplyThrottle != null)
        {
            factory.doReset(applicationReplyThrottle, applicationRouteId, applicationReplyId, networkReplyTraceId);
        }

        // more request data to be sent, so send ABORT
        if (state != Http2StreamState.HALF_CLOSED_REMOTE)
        {
            httpWriteScheduler.doAbort(factory.supplyTrace.getAsLong());
        }

        close();
    }

    void onEnd()
    {
        // more request data to be sent, so send ABORT
        if (state != Http2StreamState.HALF_CLOSED_REMOTE)
        {
            httpWriteScheduler.doAbort(0);
        }

        if (applicationReplyThrottle != null)
        {
            factory.doReset(applicationReplyThrottle, applicationRouteId, applicationReplyId, factory.supplyTrace.getAsLong());
        }

        close();
    }

    void onThrottle(
            int msgTypeId,
            DirectBuffer buffer,
            int index,
            int length)
    {
        switch (msgTypeId)
        {
            case WindowFW.TYPE_ID:
                factory.windowRO.wrap(buffer, index, index + length);
                int credit = factory.windowRO.credit();
                int padding = factory.windowRO.padding();
                long groupId = factory.windowRO.groupId();
                httpWriteScheduler.onWindow(credit, padding, groupId);
                break;
            case ResetFW.TYPE_ID:
                onHttpReset();
                break;
            default:
                // ignore
                break;
        }
    }

    /*
     * @return true if there is a buffer
     *         false if all slots are taken
     */
    MutableDirectBuffer acquireReplyBuffer()
    {
        if (replySlot == NO_SLOT)
        {
            replySlot = factory.http2ReplyPool.acquire(connection.networkReplyId);
            if (replySlot != NO_SLOT)
            {
                int capacity = factory.http2ReplyPool.buffer(replySlot).capacity();
                replyBuffer = new CircularDirectBuffer(capacity);
            }
        }
        return replySlot != NO_SLOT ? factory.http2ReplyPool.buffer(replySlot) : null;
    }

    void releaseReplyBuffer()
    {
        if (replySlot != NO_SLOT)
        {
            factory.http2ReplyPool.release(replySlot);
            replySlot = NO_SLOT;
            replyBuffer = null;
        }
    }

    void close()
    {
        httpWriteScheduler.onReset();
        releaseReplyBuffer();
    }

    void sendHttpWindow(long traceId)
    {
        // buffer may already have some data, so can only send window for remaining
        int occupied = replyBuffer == null ? 0 : replyBuffer.size();
        long maxWindow = Math.min(http2OutWindow, connection.factory.bufferPool.slotCapacity() - occupied);
        long applicationReplyCredit = maxWindow - applicationReplyBudget;
        if (applicationReplyCredit > 0)
        {
            applicationReplyBudget += applicationReplyCredit;
            int applicationReplyPadding = connection.networkReplyPadding + maxHeaderSize;
            connection.factory.doWindow(applicationReplyThrottle, applicationRouteId, applicationReplyId,
                    (int) applicationReplyCredit, applicationReplyPadding, connection.networkReplyGroupId, traceId);
        }
    }
}
