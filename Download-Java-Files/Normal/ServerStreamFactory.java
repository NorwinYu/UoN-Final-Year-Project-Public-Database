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

import static java.util.Objects.requireNonNull;

import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.LongFunction;
import java.util.function.LongSupplier;
import java.util.function.LongUnaryOperator;

import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.agrona.collections.Long2ObjectHashMap;
import org.agrona.concurrent.UnsafeBuffer;
import org.reaktivity.nukleus.buffer.BufferPool;
import org.reaktivity.nukleus.function.MessageConsumer;
import org.reaktivity.nukleus.function.MessageFunction;
import org.reaktivity.nukleus.function.MessagePredicate;
import org.reaktivity.nukleus.http2.internal.types.HttpHeaderFW;
import org.reaktivity.nukleus.http2.internal.types.ListFW;
import org.reaktivity.nukleus.http2.internal.types.control.HttpRouteExFW;
import org.reaktivity.nukleus.http2.internal.types.control.RouteFW;
import org.reaktivity.nukleus.http2.internal.types.stream.AbortFW;
import org.reaktivity.nukleus.http2.internal.types.stream.BeginFW;
import org.reaktivity.nukleus.http2.internal.types.stream.DataFW;
import org.reaktivity.nukleus.http2.internal.types.stream.EndFW;
import org.reaktivity.nukleus.http2.internal.types.stream.HpackHeaderBlockFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2ContinuationFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2DataExFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2DataFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2FrameFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2FrameHeaderFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2HeadersFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2PingFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2PrefaceFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2PriorityFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2RstStreamFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2SettingsFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2WindowUpdateFW;
import org.reaktivity.nukleus.http2.internal.types.stream.HttpBeginExFW;
import org.reaktivity.nukleus.http2.internal.types.stream.HttpEndExFW;
import org.reaktivity.nukleus.http2.internal.types.stream.ResetFW;
import org.reaktivity.nukleus.http2.internal.types.stream.WindowFW;
import org.reaktivity.nukleus.route.RouteManager;
import org.reaktivity.nukleus.stream.StreamFactory;

public final class ServerStreamFactory implements StreamFactory
{
    private static final double OUTWINDOW_LOW_THRESHOLD = 0.5;      // TODO configuration
    private static final double INWINDOW_THRESHOLD = 0.5;

    final RouteFW routeRO = new RouteFW();

    private final BeginFW beginRO = new BeginFW();
    private final DataFW dataRO = new DataFW();
    private final EndFW endRO = new EndFW();
    final AbortFW abortRO = new AbortFW();

    private final BeginFW.Builder beginRW = new BeginFW.Builder();
    private final AbortFW.Builder abortRW = new AbortFW.Builder();

    final WindowFW windowRO = new WindowFW();
    final ResetFW resetRO = new ResetFW();

    final HttpRouteExFW httpRouteExRO = new HttpRouteExFW();
    final Http2PrefaceFW prefaceRO = new Http2PrefaceFW();
    final Http2FrameFW http2RO = new Http2FrameFW();
    final Http2FrameHeaderFW http2HeaderRO = new Http2FrameHeaderFW();
    final Http2SettingsFW settingsRO = new Http2SettingsFW();
    final Http2DataFW http2DataRO = new Http2DataFW();
    final Http2HeadersFW headersRO = new Http2HeadersFW();
    final Http2ContinuationFW continationRO = new Http2ContinuationFW();
    final HpackHeaderBlockFW blockRO = new HpackHeaderBlockFW();
    final Http2WindowUpdateFW http2WindowRO = new Http2WindowUpdateFW();
    final Http2RstStreamFW http2RstStreamRO = new Http2RstStreamFW();
    final Http2PriorityFW priorityRO = new Http2PriorityFW();
    final UnsafeBuffer scratch = new UnsafeBuffer(new byte[8192]);  // TODO
    final HttpBeginExFW.Builder httpBeginExRW = new HttpBeginExFW.Builder();
    final ListFW.Builder<HttpHeaderFW.Builder, HttpHeaderFW> headersRW =
            new ListFW.Builder<>(new HttpHeaderFW.Builder(), new HttpHeaderFW());
    final DirectBuffer nameRO = new UnsafeBuffer(new byte[0]);
    final DirectBuffer valueRO = new UnsafeBuffer(new byte[0]);
    final HttpBeginExFW beginExRO = new HttpBeginExFW();
    final Http2DataExFW dataExRO = new Http2DataExFW();
    final HttpEndExFW httpEndExRO = new HttpEndExFW();
    final HpackHeaderBlockFW.Builder blockRW = new HpackHeaderBlockFW.Builder();

    final Http2PingFW pingRO = new Http2PingFW();

    private final WindowFW.Builder windowRW = new WindowFW.Builder();
    private final ResetFW.Builder resetRW = new ResetFW.Builder();

    final Http2Configuration config;
    private final RouteManager router;
    private final MutableDirectBuffer writeBuffer;
    final BufferPool bufferPool;
    final BufferPool framePool;
    final BufferPool headersPool;
    final BufferPool httpWriterPool;
    final BufferPool http2ReplyPool;
    final LongUnaryOperator supplyInitialId;
    final LongUnaryOperator supplyReplyId;
    final LongSupplier supplyTrace;
    final HttpWriter httpWriter;
    final Http2Writer http2Writer;

    // Buf to build HTTP error status code header
    final MutableDirectBuffer errorBuf = new UnsafeBuffer(new byte[64]);

    final Long2ObjectHashMap<Correlation> correlations;
    private final MessageFunction<RouteFW> wrapRoute;
    final LongSupplier supplyGroupId;
    final LongFunction<IntUnaryOperator> groupBudgetClaimer;
    final LongFunction<IntUnaryOperator> groupBudgetReleaser;
    final Http2Counters counters;

    ServerStreamFactory(
        Http2Configuration config,
        RouteManager router,
        MutableDirectBuffer writeBuffer,
        BufferPool bufferPool,
        LongUnaryOperator supplyInitialId,
        LongUnaryOperator supplyReplyId,
        Long2ObjectHashMap<Correlation> correlations,
        LongSupplier supplyGroupId,
        LongSupplier supplyTrace,
        LongFunction<IntUnaryOperator> groupBudgetClaimer,
        LongFunction<IntUnaryOperator> groupBudgetReleaser,
        Function<String, LongSupplier> supplyCounter)
    {
        this.config = config;
        this.router = requireNonNull(router);
        this.writeBuffer = requireNonNull(writeBuffer);
        this.bufferPool = requireNonNull(bufferPool);
        this.framePool = bufferPool.duplicate();
        this.headersPool = bufferPool.duplicate();
        this.httpWriterPool = bufferPool.duplicate();
        this.http2ReplyPool = bufferPool.duplicate();
        this.supplyInitialId = requireNonNull(supplyInitialId);
        this.supplyReplyId = requireNonNull(supplyReplyId);
        this.correlations = requireNonNull(correlations);
        this.supplyGroupId = requireNonNull(supplyGroupId);
        this.supplyTrace = requireNonNull(supplyTrace);
        this.groupBudgetClaimer = requireNonNull(groupBudgetClaimer);
        this.groupBudgetReleaser = requireNonNull(groupBudgetReleaser);

        this.httpWriter = new HttpWriter(writeBuffer);
        this.http2Writer = new Http2Writer(writeBuffer);
        this.counters = new Http2Counters(supplyCounter);

        this.wrapRoute = this::wrapRoute;
    }

    @Override
    public MessageConsumer newStream(
            int msgTypeId,
            DirectBuffer buffer,
            int index,
            int length,
            MessageConsumer throttle)
    {
        final BeginFW begin = beginRO.wrap(buffer, index, index + length);
        final long streamId = begin.streamId();

        MessageConsumer newStream;

        if ((streamId & 0x0000_0000_0000_0001L) != 0L)
        {
            newStream = newAcceptStream(begin, throttle);
        }
        else
        {
            newStream = newConnectReplyStream(begin, throttle);
        }

        return newStream;
    }

    private MessageConsumer newAcceptStream(
        final BeginFW begin,
        final MessageConsumer networkReply)
    {
        final long networkRouteId = begin.routeId();

        final MessagePredicate filter = (t, b, o, l) -> true;

        final RouteFW route = router.resolve(networkRouteId, begin.authorization(), filter, this::wrapRoute);

        MessageConsumer newStream = null;

        if (route != null)
        {
            final long networkId = begin.streamId();

            newStream = new ServerAcceptStream(networkReply, networkRouteId, networkId)::handleStream;
        }

        return newStream;
    }

    private MessageConsumer newConnectReplyStream(
        final BeginFW begin,
        final MessageConsumer sender)
    {
        final long routeId = begin.routeId();
        final long streamId = begin.streamId();

        return new ServerConnectReplyStream(sender, routeId, streamId)::handleStream;
    }

    private RouteFW wrapRoute(
        int msgTypeId,
        DirectBuffer buffer,
        int index,
        int length)
    {
        return routeRO.wrap(buffer, index, index + length);
    }

    private final class ServerAcceptStream
    {
        private final MessageConsumer networkReply;
        private final long networkRouteId;
        private final long networkInitialId;
        private final long networkReplyId;

        private MessageConsumer streamState;
        private Http2Connection http2Connection;
        private int initialWindow = bufferPool.slotCapacity();
        private int window;

        private ServerAcceptStream(
            MessageConsumer networkReply,
            long networkRouteId,
            long networkInitialId)
        {
            this.networkReply = networkReply;
            this.networkRouteId = networkRouteId;
            this.networkInitialId = networkInitialId;
            this.networkReplyId = supplyReplyId.applyAsLong(networkInitialId);
            this.streamState = this::beforeBegin;
        }

        private void handleStream(
            int msgTypeId,
            DirectBuffer buffer,
            int index,
            int length)
        {
            streamState.accept(msgTypeId, buffer, index, length);
        }

        private void beforeBegin(
            int msgTypeId,
            DirectBuffer buffer,
            int index,
            int length)
        {
            if (msgTypeId == BeginFW.TYPE_ID)
            {
                final BeginFW begin = beginRO.wrap(buffer, index, index + length);
                handleBegin(begin);
            }
            else
            {
                doReset(networkReply, networkRouteId, networkInitialId, supplyTrace.getAsLong());
            }
        }

        private void afterBegin(
            int msgTypeId,
            DirectBuffer buffer,
            int index,
            int length)
        {
            switch (msgTypeId)
            {
                case DataFW.TYPE_ID:
                    final DataFW data = dataRO.wrap(buffer, index, index + length);
                    handleData(data);
                    break;
                case EndFW.TYPE_ID:
                    final EndFW end = endRO.wrap(buffer, index, index + length);
                    handleEnd(end);
                    break;
                case AbortFW.TYPE_ID:
                    final AbortFW abort = abortRO.wrap(buffer, index, index + length);
                    handleAbort(abort);
                    break;
                default:
                    doReset(networkReply, networkRouteId, networkInitialId, supplyTrace.getAsLong());
                    break;
            }
        }

        private void handleBegin(
            BeginFW begin)
        {
            initialWindow = bufferPool.slotCapacity();
            doWindow(networkReply, networkRouteId, networkInitialId, initialWindow, 0, 0, supplyTrace.getAsLong());
            window = initialWindow;

            doBegin(networkReply, networkRouteId, networkReplyId, supplyTrace.getAsLong());
            router.setThrottle(networkReplyId, this::handleThrottle);

            this.streamState = this::afterBegin;
            http2Connection = new Http2Connection(ServerStreamFactory.this, router,
                    networkReply, networkRouteId, networkInitialId,
                    networkReply, networkReplyId, wrapRoute);
            http2Connection.handleBegin(begin);
        }

        private void handleData(
            DataFW data)
        {
            window -= dataRO.length() + dataRO.padding();
            if (window < 0)
            {
                doReset(networkReply, networkRouteId, networkInitialId, supplyTrace.getAsLong());
                //http2Connection.handleReset();
            }
            else
            {
                http2Connection.handleData(data);

                if (window < initialWindow * INWINDOW_THRESHOLD)
                {
                    int windowPending = initialWindow - window - http2Connection.frameSlotLimit;
                    if (windowPending > 0)
                    {
                        window += windowPending;
                        doWindow(networkReply, networkRouteId, networkInitialId, windowPending, 0, 0,
                                supplyTrace.getAsLong());
                    }
                }
            }
        }

        private void handleEnd(
            EndFW end)
        {
            http2Connection.handleEnd(end);
        }

        private void handleAbort(
            AbortFW abort)
        {
            correlations.remove(networkReplyId);

            // aborts reply stream
            doAbort(networkReply, networkRouteId, networkReplyId, supplyTrace.getAsLong());

            // aborts http request stream, resets http response stream
            http2Connection.handleAbort(abort.trace());
        }

        private void handleThrottle(
            int msgTypeId,
            DirectBuffer buffer,
            int index,
            int length)
        {
            switch (msgTypeId)
            {
                case WindowFW.TYPE_ID:
                    final WindowFW window = windowRO.wrap(buffer, index, index + length);
                    handleWindow(window);
                    break;
                case ResetFW.TYPE_ID:
                    final ResetFW reset = resetRO.wrap(buffer, index, index + length);
                    handleReset(reset);
                    break;
                default:
                    // ignore
                    break;
            }
        }

        private void handleWindow(
            WindowFW window)
        {
            int credit = windowRO.credit();
            int padding = windowRO.padding();
            if (http2Connection.outWindowThreshold == -1)
            {
                http2Connection.outWindowThreshold = (int) (OUTWINDOW_LOW_THRESHOLD * credit);
            }
            http2Connection.networkReplyBudget += credit;
            http2Connection.networkReplyPadding = padding;
            http2Connection.handleWindow(window);
        }

        private void handleReset(
            ResetFW reset)
        {
            http2Connection.handleReset(reset);
            doReset(networkReply, networkRouteId, networkInitialId, supplyTrace.getAsLong());
        }
    }


    private final class ServerConnectReplyStream
    {
        private final MessageConsumer applicationReplyThrottle;
        private final long applicationRouteId;
        private final long applicationReplyId;

        private MessageConsumer streamState;

        private Http2Connection http2Connection;
        private Correlation correlation;

        private ServerConnectReplyStream(
            MessageConsumer applicationReplyThrottle,
            long applicationRouteId,
            long applicationReplyId)
        {
            this.applicationReplyThrottle = applicationReplyThrottle;
            this.applicationRouteId = applicationRouteId;
            this.applicationReplyId = applicationReplyId;
            this.streamState = this::beforeBegin;
        }

        private void handleStream(
                int msgTypeId,
                DirectBuffer buffer,
                int index,
                int length)
        {
            streamState.accept(msgTypeId, buffer, index, length);
        }

        private void beforeBegin(
            int msgTypeId,
            DirectBuffer buffer,
            int index,
            int length)
        {
            if (msgTypeId == BeginFW.TYPE_ID)
            {
                final BeginFW begin = beginRO.wrap(buffer, index, index + length);
                handleBegin(begin);
            }
            else
            {
                doReset(applicationReplyThrottle, applicationRouteId, applicationReplyId, supplyTrace.getAsLong());
            }
        }

        private void afterBegin(
            int msgTypeId,
            DirectBuffer buffer,
            int index,
            int length)
        {
            switch (msgTypeId)
            {
                case DataFW.TYPE_ID:
                    final DataFW data = dataRO.wrap(buffer, index, index + length);
                    handleData(data);
                    break;
                case EndFW.TYPE_ID:
                    final EndFW end = endRO.wrap(buffer, index, index + length);
                    handleEnd(end);
                    break;
                case AbortFW.TYPE_ID:
                    final AbortFW abort = abortRO.wrap(buffer, index, index + length);
                    handleAbort(abort);
                    break;
                default:
                    doReset(applicationReplyThrottle, applicationRouteId, applicationReplyId, supplyTrace.getAsLong());
                    break;
            }
        }

        private void handleBegin(
            BeginFW begin)
        {
            final long replyId = begin.streamId();
            correlation = correlations.remove(replyId);
            if (correlation != null)
            {
                http2Connection = correlation.http2Connection;

                http2Connection.handleHttpBegin(begin, applicationReplyThrottle, applicationRouteId,
                        applicationReplyId, correlation);

                this.streamState = this::afterBegin;
            }
            else
            {
                doReset(applicationReplyThrottle, applicationRouteId, applicationReplyId, supplyTrace.getAsLong());
            }
        }

        private void handleData(
            DataFW data)
        {
            http2Connection.handleHttpData(data, correlation);
        }

        private void handleEnd(
            EndFW end)
        {
            http2Connection.handleHttpEnd(end, correlation);
        }

        private void handleAbort(
            AbortFW abort)
        {
            http2Connection.handleHttpAbort(abort, correlation);
        }

    }

    private void doBegin(
        final MessageConsumer receiver,
        final long routeId,
        final long streamId,
        final long traceId)
    {
        final BeginFW begin = beginRW.wrap(writeBuffer, 0, writeBuffer.capacity())
                .routeId(routeId)
                .streamId(streamId)
                .trace(traceId)
                .build();

        receiver.accept(begin.typeId(), begin.buffer(), begin.offset(), begin.sizeof());
    }

    void doAbort(
        final MessageConsumer receiver,
        final long routeId,
        final long streamId,
        final long traceId)
    {
        final AbortFW abort = abortRW.wrap(writeBuffer, 0, writeBuffer.capacity())
                .routeId(routeId)
                .streamId(streamId)
                .trace(traceId)
                .build();

        receiver.accept(abort.typeId(), abort.buffer(), abort.offset(), abort.sizeof());
    }

    void doWindow(
        final MessageConsumer sender,
        final long routeId,
        final long streamId,
        final int credit,
        final int padding,
        final long groupId,
        final long traceId)
    {
        final WindowFW window = windowRW.wrap(writeBuffer, 0, writeBuffer.capacity())
                .routeId(routeId)
                .streamId(streamId)
                .trace(traceId)
                .credit(credit)
                .padding(padding)
                .groupId(groupId)
                .build();

        sender.accept(window.typeId(), window.buffer(), window.offset(), window.sizeof());
    }

    void doReset(
        final MessageConsumer sender,
        final long routeId,
        final long streamId,
        final long traceId)
    {
        final ResetFW reset = resetRW.wrap(writeBuffer, 0, writeBuffer.capacity())
                .routeId(routeId)
                .streamId(streamId)
                .trace(traceId)
                .build();

        sender.accept(reset.typeId(), reset.buffer(), reset.offset(), reset.sizeof());
    }
}