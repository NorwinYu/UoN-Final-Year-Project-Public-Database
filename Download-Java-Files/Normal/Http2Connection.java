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

import static org.reaktivity.nukleus.buffer.BufferPool.NO_SLOT;
import static org.reaktivity.nukleus.http2.internal.Http2StreamState.CLOSED;
import static org.reaktivity.nukleus.http2.internal.Http2StreamState.HALF_CLOSED_REMOTE;
import static org.reaktivity.nukleus.http2.internal.Http2StreamState.OPEN;
import static org.reaktivity.nukleus.http2.internal.types.stream.HpackContext.CONNECTION;
import static org.reaktivity.nukleus.http2.internal.types.stream.HpackContext.DEFAULT_ACCESS_CONTROL_ALLOW_ORIGIN;
import static org.reaktivity.nukleus.http2.internal.types.stream.HpackContext.KEEP_ALIVE;
import static org.reaktivity.nukleus.http2.internal.types.stream.HpackContext.PROXY_CONNECTION;
import static org.reaktivity.nukleus.http2.internal.types.stream.HpackContext.TE;
import static org.reaktivity.nukleus.http2.internal.types.stream.HpackContext.TRAILERS;
import static org.reaktivity.nukleus.http2.internal.types.stream.HpackContext.UPGRADE;
import static org.reaktivity.nukleus.http2.internal.types.stream.HpackHeaderFieldFW.HeaderFieldType.UNKNOWN;
import static org.reaktivity.nukleus.http2.internal.types.stream.HpackLiteralHeaderFieldFW.LiteralType.INCREMENTAL_INDEXING;
import static org.reaktivity.nukleus.http2.internal.types.stream.HpackLiteralHeaderFieldFW.LiteralType.WITHOUT_INDEXING;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.agrona.collections.Int2ObjectHashMap;
import org.agrona.concurrent.UnsafeBuffer;
import org.reaktivity.nukleus.function.MessageConsumer;
import org.reaktivity.nukleus.function.MessageFunction;
import org.reaktivity.nukleus.function.MessagePredicate;
import org.reaktivity.nukleus.http2.internal.types.HttpHeaderFW;
import org.reaktivity.nukleus.http2.internal.types.ListFW;
import org.reaktivity.nukleus.http2.internal.types.OctetsFW;
import org.reaktivity.nukleus.http2.internal.types.String16FW;
import org.reaktivity.nukleus.http2.internal.types.StringFW;
import org.reaktivity.nukleus.http2.internal.types.control.HttpRouteExFW;
import org.reaktivity.nukleus.http2.internal.types.control.RouteFW;
import org.reaktivity.nukleus.http2.internal.types.stream.AbortFW;
import org.reaktivity.nukleus.http2.internal.types.stream.BeginFW;
import org.reaktivity.nukleus.http2.internal.types.stream.DataFW;
import org.reaktivity.nukleus.http2.internal.types.stream.EndFW;
import org.reaktivity.nukleus.http2.internal.types.stream.HpackContext;
import org.reaktivity.nukleus.http2.internal.types.stream.HpackHeaderBlockFW;
import org.reaktivity.nukleus.http2.internal.types.stream.HpackHeaderFieldFW;
import org.reaktivity.nukleus.http2.internal.types.stream.HpackHuffman;
import org.reaktivity.nukleus.http2.internal.types.stream.HpackLiteralHeaderFieldFW;
import org.reaktivity.nukleus.http2.internal.types.stream.HpackStringFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2ContinuationFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2DataExFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2DataFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2ErrorCode;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2Flags;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2FrameFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2FrameHeaderFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2FrameType;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2HeadersFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2PingFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2PrefaceFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2PriorityFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2RstStreamFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2SettingsFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2SettingsId;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2WindowUpdateFW;
import org.reaktivity.nukleus.http2.internal.types.stream.HttpBeginExFW;
import org.reaktivity.nukleus.http2.internal.types.stream.HttpEndExFW;
import org.reaktivity.nukleus.http2.internal.types.stream.ResetFW;
import org.reaktivity.nukleus.http2.internal.types.stream.WindowFW;
import org.reaktivity.nukleus.route.RouteManager;

final class Http2Connection
{
    private static final Map<String, String> EMPTY_HEADERS = Collections.emptyMap();

    ServerStreamFactory factory;
    private DecoderState decoderState;

    // slab to assemble a complete HTTP2 frame
    // no need for separate slab per HTTP2 stream as the frames are not fragmented
    private int frameSlot = NO_SLOT;
    int frameSlotLimit;

    // slab to assemble a complete HTTP2 headers frame(including its continuation frames)
    // no need for separate slab per HTTP2 stream as no interleaved frames of any other type
    // or from any other stream
    private int headersSlotIndex = NO_SLOT;
    private int headersSlotOffset;

    final long networkRouteId;
    final long networkId;
    long authorization;
    int lastStreamId;
    int networkReplyBudget;
    int networkReplyPadding;
    int outWindowThreshold = -1;

    final WriteScheduler writeScheduler;

    final MessageConsumer network;
    final long networkReplyId;
    private final HpackContext decodeContext;
    private final HpackContext encodeContext;
    private final MessageFunction<RouteFW> wrapRoute;

    final long networkReplyGroupId;

    final Int2ObjectHashMap<Http2Stream> http2Streams;      // HTTP2 stream-id --> Http2Stream

    private int clientStreamCount;
    private int promisedStreamCount;
    private int maxClientStreamId;
    private int maxPushPromiseStreamId;

    private boolean goaway;
    Settings initialSettings;
    Settings localSettings;
    Settings remoteSettings;
    private boolean expectContinuation;
    private int expectContinuationStreamId;
    private boolean expectDynamicTableSizeUpdate = true;
    long http2OutWindow;
    long http2InWindow;

    private final Consumer<HpackHeaderFieldFW> headerFieldConsumer;
    private final Consumer<HpackHeaderFieldFW> trailerFieldConsumer;
    private final HeadersContext headersContext = new HeadersContext();
    private final EncodeHeadersContext encodeHeadersContext = new EncodeHeadersContext();
    final Http2Writer http2Writer;
    final MessageConsumer networkReply;
    RouteManager router;
    long traceId;

    private Http2ErrorCode decodeError;

    Http2Connection(
        ServerStreamFactory factory,
        RouteManager router,
        MessageConsumer network,
        long networkRouteId,
        long networkId,
        MessageConsumer networkReply,
        long networkReplyId,
        MessageFunction<RouteFW> wrapRoute)
    {
        this.factory = factory;
        this.router = router;
        this.wrapRoute = wrapRoute;
        this.network = network;
        this.networkId = networkId;
        this.networkRouteId = networkRouteId;
        this.networkReplyId = networkReplyId;
        this.http2Streams = new Int2ObjectHashMap<>();
        this.localSettings = new Settings();
        this.remoteSettings = new Settings();
        this.decodeContext = new HpackContext(localSettings.headerTableSize, false);
        this.encodeContext = new HpackContext(remoteSettings.headerTableSize, true);
        this.http2Writer = factory.http2Writer;
        this.writeScheduler = new Http2WriteScheduler(this, networkReply, http2Writer, networkRouteId, networkReplyId);
        this.http2InWindow = localSettings.initialWindowSize;
        this.http2OutWindow = remoteSettings.initialWindowSize;
        this.networkReply = networkReply;
        this.networkReplyGroupId = factory.supplyGroupId.getAsLong();

        BiConsumer<DirectBuffer, DirectBuffer> nameValue =
                ((BiConsumer<DirectBuffer, DirectBuffer>)this::collectHeaders)
                        .andThen(this::validatePseudoHeaders)
                        .andThen(this::uppercaseHeaders)
                        .andThen(this::connectionHeaders)
                        .andThen(this::contentLengthHeader)
                        .andThen(this::teHeader)
                        .andThen(this::mapToHttp);

        Consumer<HpackHeaderFieldFW> consumer = this::validateHeaderFieldType;
        consumer = consumer.andThen(this::dynamicTableSizeUpdate);
        this.headerFieldConsumer = consumer.andThen(h -> decodeHeaderField(h, nameValue));
        this.trailerFieldConsumer = h -> decodeHeaderField(h, this::validateTrailerFieldName);
    }

    void handleBegin(
        BeginFW beginRO)
    {
        this.authorization = beginRO.authorization();
        this.decoderState = this::decodePreface;
        this.initialSettings = new Settings(factory.config.serverConcurrentStreams(), 0);
        writeScheduler.settings(initialSettings.maxConcurrentStreams, initialSettings.initialWindowSize);
        factory.counters.settingsFramesWritten.getAsLong();
    }

    void handleData(
        DataFW data)
    {
        final long traceId = data.trace();
        final OctetsFW payload = data.payload();
        final DirectBuffer buffer = payload.buffer();
        final int offset = payload.offset();
        final int limit = payload.limit();

        DirectBuffer decodeBuffer = buffer;
        int decodeOffset = offset;
        int decodeLimit = limit;

        if (frameSlot != NO_SLOT)
        {
            final MutableDirectBuffer frameBuffer = factory.framePool.buffer(frameSlot);
            frameBuffer.putBytes(frameSlotLimit, buffer, offset, limit - offset);
            frameSlotLimit += limit - offset;
            decodeBuffer = frameBuffer;
            decodeOffset = 0;
            decodeLimit = frameSlotLimit;
        }

        this.traceId = traceId;
        int decodeProgress = 0;
        while (decodeOffset < decodeLimit && decodeError == null)
        {
            decodeProgress = decoderState.decode(decodeBuffer, decodeOffset, decodeLimit);

            if (decodeProgress <= 0)
            {
                // incomplete frame
                break;
            }

            decodeOffset += decodeProgress;
        }
        this.traceId = 0;

        final int decodeRemaining = decodeLimit - decodeOffset;
        if (decodeProgress >= 0 && decodeRemaining > 0)
        {
            if (frameSlot == NO_SLOT)
            {
                assert frameSlotLimit == 0;

                final int newFrameSlot = factory.framePool.acquire(networkId);
                if (newFrameSlot != NO_SLOT)
                {
                    frameSlot = newFrameSlot;
                }
                else
                {
                    decodeProgress = -1; // error
                }
            }

            if (frameSlot != NO_SLOT)
            {
                final MutableDirectBuffer frameBuffer = factory.framePool.buffer(frameSlot);
                frameBuffer.putBytes(0, decodeBuffer, decodeOffset, decodeRemaining);
                frameSlotLimit = decodeRemaining;
            }
        }
        else
        {
            releaseFrameSlot();
        }

        if (decodeError != null)
        {
            error(decodeError);
        }

        if (decodeProgress < 0 ||
                decodeError != null && decodeError != Http2ErrorCode.NO_ERROR)
        {
            // TODO: use traceId ??
            http2Streams.forEach((i, s) -> s.onAbort(traceId));
            doResetNetworkAndCleanup();
        }
    }

    void handleAbort(
        long traceId)
    {
        http2Streams.forEach((i, s) -> s.onAbort(traceId));
        doCleanup();
    }

    void handleReset(
        ResetFW reset)
    {
        http2Streams.forEach((i, s) -> s.onReset(reset.trace()));
        doCleanup();
    }

    void handleEnd(
        EndFW end)
    {
        decoderState = (b, o, l) -> o;

        http2Streams.forEach((i, s) -> s.onEnd());
        writeScheduler.doEnd();
        doCleanup();
    }

    private int decodePreface(
        final DirectBuffer buffer,
        final int offset,
        final int limit)
    {
        final Http2PrefaceFW preface = factory.prefaceRO.tryWrap(buffer, offset, limit);

        int decodeProgress = 0;
        if (preface != null)
        {
            if (preface.error())
            {
                decodeProgress = -1;
                this.decodeError = Http2ErrorCode.PROTOCOL_ERROR;
            }
            else
            {
                decodeProgress = preface.sizeof();
                this.decoderState = this::decodeFrame;
            }
        }

        return decodeProgress;
    }

    private int decodeFrame(
        final DirectBuffer buffer,
        final int offset,
        final int limit)
    {
        final Http2FrameFW http2Frame = factory.http2RO.tryWrap(buffer, offset, limit);

        if (http2Frame != null)
        {
            if (http2Frame.payloadLength() > localSettings.maxFrameSize)
            {
                this.decodeError = Http2ErrorCode.FRAME_SIZE_ERROR;
            }
            else if (http2Frame.streamId() == 0)
            {
                onConnectionFrame(http2Frame);
            }
            else
            {
                onStreamFrame(http2Frame);
            }
        }
        else
        {
            final Http2FrameHeaderFW http2FrameHeader = factory.http2HeaderRO.tryWrap(buffer, offset, limit);

            if (http2FrameHeader != null)
            {
                if (http2FrameHeader.payloadLength() > localSettings.maxFrameSize)
                {
                    this.decodeError = Http2ErrorCode.FRAME_SIZE_ERROR;
                }
            }
        }

        return http2Frame != null ? http2Frame.sizeof() : 0;
    }

    private void onConnectionFrame(
        final Http2FrameFW http2Frame)
    {
        switch (http2Frame.type())
        {
        case SETTINGS:
            factory.counters.settingsFramesRead.getAsLong();
            onConnectionSettings(http2Frame);
            break;
        case PING:
            factory.counters.pingFramesRead.getAsLong();
            onConnectionPing(http2Frame);
            break;
        case GO_AWAY:
            factory.counters.goawayFramesRead.getAsLong();
            onConnectionGoAway(http2Frame);
            break;
        case WINDOW_UPDATE:
            factory.counters.windowUpdateFramesRead.getAsLong();
            onConnectionWindowUpdate(http2Frame);
            break;
        case UNKNOWN:
            break;
        default:
            this.decodeError = Http2ErrorCode.PROTOCOL_ERROR;
            break;
        }
    }

    private void onStreamFrame(
        final Http2FrameFW http2Frame)
    {
        int streamId = http2Frame.streamId();
        Http2FrameType type = http2Frame.type();

        if ((streamId & 0x01) != 0x01 &&
            type != Http2FrameType.WINDOW_UPDATE &&
            type != Http2FrameType.RST_STREAM &&
            type != Http2FrameType.PRIORITY)
        {
            decodeError = Http2ErrorCode.PROTOCOL_ERROR;
            return;
        }

        if (expectContinuation &&
                (type != Http2FrameType.CONTINUATION || streamId != expectContinuationStreamId))
        {
            this.decodeError = Http2ErrorCode.PROTOCOL_ERROR;
            return;
        }

        if ((streamId & 0x01) == 0x01 &&
            streamId > maxClientStreamId &&
            type != Http2FrameType.HEADERS &&
            type != Http2FrameType.PRIORITY)
        {
            decodeError = Http2ErrorCode.PROTOCOL_ERROR;
            return;
        }

        Http2Stream stream = http2Streams.get(streamId);
        if (stream == null)
        {
            switch (type)
            {
            case HEADERS:
                factory.counters.headersFramesRead.getAsLong();
                onStreamHeaders(http2Frame);
                break;
            case CONTINUATION:
                factory.counters.continuationFramesRead.getAsLong();
                onStreamContinuation(http2Frame);
                break;
            case WINDOW_UPDATE:
                // "half-closed (remote)" or "closed" stream MUST NOT be treated as error
                factory.counters.windowUpdateFramesRead.getAsLong();
                onStreamWindowUpdate(stream, http2Frame);
                break;
            case PRIORITY:
                // "half-closed (remote)" or "closed" stream MUST NOT be treated as error
                factory.counters.priorityFramesRead.getAsLong();
                onStreamPriority(stream, http2Frame);
                break;
            case RST_STREAM:
                onStreamRst(stream, http2Frame);
                break;
            case UNKNOWN:
                break;
            default:
                this.decodeError = Http2ErrorCode.PROTOCOL_ERROR;
                break;
            }
        }
        else
        {
            switch (type)
            {
            case DATA:
                factory.counters.dataFramesRead.getAsLong();
                onStreamData(stream, http2Frame);
                break;
            case HEADERS:
                factory.counters.headersFramesRead.getAsLong();
                onStreamHeaders(stream, http2Frame);
                break;
            case PRIORITY:
                factory.counters.priorityFramesRead.getAsLong();
                onStreamPriority(stream, http2Frame);
                break;
            case RST_STREAM:
                factory.counters.resetStreamFramesRead.getAsLong();
                onStreamRst(stream, http2Frame);
                break;
            case WINDOW_UPDATE:
                factory.counters.windowUpdateFramesRead.getAsLong();
                onStreamWindowUpdate(stream, http2Frame);
                break;
            case UNKNOWN:
                break;
            default:
                this.decodeError = Http2ErrorCode.PROTOCOL_ERROR;
                break;
            }
        }
    }

    private void onConnectionSettings(
        final Http2FrameFW http2Frame)
    {
        final Http2SettingsFW settings =
                factory.settingsRO.tryWrap(http2Frame.buffer(), http2Frame.offset(), http2Frame.limit());

        if (settings == null || settings.ack() && settings.payloadLength() != 0)
        {
            decodeError = Http2ErrorCode.FRAME_SIZE_ERROR;
        }
        else if (!settings.ack())
        {
            settings.forEach(this::applySetting);
            writeScheduler.settingsAck();
            factory.counters.settingsFramesWritten.getAsLong();
        }
        else
        {
            http2Streams.values().forEach(this::applyInitialWindowDelta);

            // now that peer acked our initial settings, can use them as our local settings
            localSettings = initialSettings;
        }
    }

    private void onConnectionPing(
        Http2FrameFW http2Frame)
    {
        final Http2PingFW ping = factory.pingRO.tryWrap(http2Frame.buffer(), http2Frame.offset(), http2Frame.limit());
        if (ping == null)
        {
            this.decodeError = Http2ErrorCode.FRAME_SIZE_ERROR;
            return;
        }

        if (!ping.ack())
        {
            writeScheduler.pingAck(ping.payload(), 0, ping.payload().capacity());

            factory.counters.pingFramesWritten.getAsLong();
        }
    }

    private void onConnectionGoAway(
        Http2FrameFW http2Frame)
    {
        // TODO: ConnectionState
        if (!goaway)
        {
            goaway = true;
            remoteSettings.enablePush = false;      // no new streams
            this.decodeError = Http2ErrorCode.NO_ERROR;
        }
    }

    private void onConnectionWindowUpdate(
        Http2FrameFW http2Frame)
    {
        final Http2WindowUpdateFW http2Window =
                factory.http2WindowRO.tryWrap(http2Frame.buffer(), http2Frame.offset(), http2Frame.limit());
        if (http2Window == null)
        {
            decodeError = Http2ErrorCode.FRAME_SIZE_ERROR;
            return;
        }

        // 6.9 WINDOW_UPDATE - legal range for flow-control window increment is 1 to 2^31-1 octets.
        if (http2Window.size() < 1)
        {
            decodeError = Http2ErrorCode.PROTOCOL_ERROR;
            return;
        }

        http2OutWindow += http2Window.size();

        // 6.9.1 A sender MUST NOT allow a flow-control window to exceed 2^31-1 octets.
        if (http2OutWindow > Integer.MAX_VALUE)
        {
            decodeError = Http2ErrorCode.FLOW_CONTROL_ERROR;
            return;
        }

        writeScheduler.onHttp2Window();
    }

    private void onStreamHeaders(
        Http2FrameFW http2Frame)
    {
        int streamId = http2Frame.streamId();

        Http2HeadersFW http2Headers = factory.headersRO.wrap(http2Frame.buffer(), http2Frame.offset(), http2Frame.limit());
        int parentStreamId = http2Headers.parentStream();

        if (parentStreamId == streamId)
        {
            // 5.3.1 A stream cannot depend on itself
            doRstStream(streamId, Http2ErrorCode.PROTOCOL_ERROR);
        }

        if (http2Headers.dataLength() < 0)
        {
            this.decodeError = Http2ErrorCode.PROTOCOL_ERROR;
            return;
        }

        if (streamId <= maxClientStreamId)
        {
            this.decodeError = Http2ErrorCode.PROTOCOL_ERROR;
            return;
        }

        maxClientStreamId = streamId;

        if (clientStreamCount >= localSettings.maxConcurrentStreams)
        {
            doRstStream(streamId, Http2ErrorCode.REFUSED_STREAM);
            return;
        }

        if (!http2Headers.endHeaders())
        {
            assert headersSlotIndex == NO_SLOT;
            assert headersSlotOffset == 0;

            headersSlotIndex = factory.headersPool.acquire(networkId);
            if (headersSlotIndex == NO_SLOT)
            {
                // all slots are in use, just reset the connection
                factory.doReset(network, networkRouteId, networkId, factory.supplyTrace.getAsLong());
                handleAbort(factory.supplyTrace.getAsLong());
                return;
            }

            MutableDirectBuffer headersBuffer = factory.headersPool.buffer(headersSlotIndex);
            headersBuffer.putBytes(headersSlotOffset, http2Headers.buffer(),
                    http2Headers.dataOffset(), http2Headers.dataLength());
            headersSlotOffset = http2Headers.dataLength();

            expectContinuation = true;
            expectContinuationStreamId = streamId;
            return;
        }

        onStreamHeadersEnd(http2Frame, http2Headers.buffer(), http2Headers.dataOffset(),
                http2Headers.dataOffset() + http2Headers.dataLength());
    }

    private void onStreamHeaders(
        Http2Stream stream,
        Http2FrameFW http2Frame)
    {
        if (stream.state == Http2StreamState.HALF_CLOSED_REMOTE)
        {
            decodeError = Http2ErrorCode.PROTOCOL_ERROR;
            return;
        }

        Http2HeadersFW http2Trailers = factory.headersRO.wrap(http2Frame.buffer(), http2Frame.offset(), http2Frame.limit());
        HpackHeaderBlockFW headerBlock = factory.blockRO.wrap(http2Trailers.buffer(), http2Trailers.dataOffset(),
                http2Trailers.dataOffset() + http2Trailers.dataLength());
        headerBlock.forEach(trailerFieldConsumer);

        if (headersContext.error())
        {
            if (headersContext.streamError != null)
            {
                doRstStream(stream.http2StreamId, headersContext.streamError);
                return;
            }

            if (headersContext.connectionError != null)
            {
                decodeError = headersContext.connectionError;
                return;
            }
        }
    }

    private void onStreamContinuation(
        Http2FrameFW http2Frame)
    {
        if (!expectContinuation)
        {
            decodeError = Http2ErrorCode.PROTOCOL_ERROR;
            return;
        }

        assert headersSlotIndex != NO_SLOT;
        assert headersSlotOffset != 0;

        Http2ContinuationFW http2Continuation =
                factory.continationRO.wrap(http2Frame.buffer(), http2Frame.offset(), http2Frame.limit());
        DirectBuffer payload = http2Continuation.payload();

        MutableDirectBuffer headersBuffer = factory.headersPool.buffer(headersSlotIndex);
        headersBuffer.putBytes(headersSlotOffset, payload, 0, payload.capacity());
        headersSlotOffset += payload.capacity();

        if (!http2Continuation.endHeaders())
        {
            assert expectContinuation;
            assert expectContinuationStreamId == http2Continuation.streamId();
            return;
        }

        onStreamHeadersEnd(http2Frame, headersBuffer, 0, headersSlotOffset);

        releaseHeadersSlot();

        expectContinuation = false;
        expectContinuationStreamId = 0;
    }

    private void onStreamHeadersEnd(
        Http2FrameFW http2Frame,
        DirectBuffer headersBuffer,
        int headersOffset,
        int headersLimit)
    {
        int streamId = http2Frame.streamId();

        headersContext.reset();

        factory.httpBeginExRW.wrap(factory.scratch, 0, factory.scratch.capacity());
        HpackHeaderBlockFW headerBlock = factory.blockRO.wrap(headersBuffer, headersOffset, headersLimit);
        headerBlock.forEach(headerFieldConsumer);

        // All HTTP/2 requests MUST include exactly one valid value for the
        // ":method", ":scheme", and ":path" pseudo-header fields, unless it is
        // a CONNECT request (Section 8.3).  An HTTP request that omits
        // mandatory pseudo-header fields is malformed
        if (!headersContext.error() &&
                (headersContext.method != 1 || headersContext.scheme != 1 || headersContext.path != 1))
        {
            headersContext.streamError = Http2ErrorCode.PROTOCOL_ERROR;
        }

        if (headersContext.error())
        {
            if (headersContext.streamError != null)
            {
                doRstStream(streamId, headersContext.streamError);
                return;
            }

            if (headersContext.connectionError != null)
            {
                decodeError = headersContext.connectionError;
                return;
            }
        }

        addDefaultPortToAuthorityIfNeeded();

        RouteFW route = resolveTarget(headersContext.headers);
        if (route == null)
        {
            noRoute(streamId);
        }
        else
        {
            Http2StreamState nextState = http2Frame.endStream() ? HALF_CLOSED_REMOTE : OPEN;
            followRoute(streamId, nextState, route);
        }
    }

    private void onStreamData(
        Http2Stream stream,
        Http2FrameFW http2Frame)
    {
        if (stream.state == HALF_CLOSED_REMOTE)
        {
            decodeError = Http2ErrorCode.STREAM_CLOSED;
            closeStream(stream);
            return;
        }

        // handle invalid padding length
        Http2DataFW http2Data = factory.http2DataRO.wrap(http2Frame.buffer(), http2Frame.offset(), http2Frame.limit());
        if (http2Data.dataLength() < 0)
        {
            decodeError = Http2ErrorCode.STREAM_CLOSED;
            closeStream(stream);
            return;
        }

        final int payloadLength = http2Frame.payloadLength();
        if (stream.http2InWindow < payloadLength || http2InWindow < payloadLength)
        {
            doRstByUs(stream, Http2ErrorCode.FLOW_CONTROL_ERROR);
            return;
        }

        http2InWindow -= payloadLength;
        stream.http2InWindow -= payloadLength;

        stream.totalData += payloadLength;

        if (http2Data.endStream())
        {
            // 8.1.2.6 A request is malformed if the value of a content-length header field does
            // not equal the sum of the DATA frame payload lengths
            if (stream.contentLength != -1 && stream.totalData != stream.contentLength)
            {
                doRstByUs(stream, Http2ErrorCode.PROTOCOL_ERROR);
                //stream.httpWriteScheduler.doEnd(stream.targetId);
                return;
            }

            stream.state = Http2StreamState.HALF_CLOSED_REMOTE;
        }

        stream.onData(traceId, http2Data);
    }

    private void onStreamPriority(
        Http2Stream stream,
        Http2FrameFW http2Frame)
    {
        Http2PriorityFW http2Priority = factory.priorityRO.tryWrap(http2Frame.buffer(), http2Frame.offset(), http2Frame.limit());
        if (http2Priority == null)
        {
            decodeError = Http2ErrorCode.FRAME_SIZE_ERROR;
            return;
        }

        if (stream != null)
        {
            if (http2Priority.parentStream() == stream.http2StreamId)
            {
                // 5.3.1 A stream cannot depend on itself
                doRstByUs(stream, Http2ErrorCode.PROTOCOL_ERROR);
                return;
            }
        }
    }

    private void onStreamWindowUpdate(
        Http2Stream stream,
        Http2FrameFW http2Frame)
    {
        Http2WindowUpdateFW http2Window =
                factory.http2WindowRO.tryWrap(http2Frame.buffer(), http2Frame.offset(), http2Frame.limit());
        if (http2Window == null)
        {
            decodeError = Http2ErrorCode.FRAME_SIZE_ERROR;
            return;
        }

        // 6.9 WINDOW_UPDATE - legal range for flow-control window increment is 1 to 2^31-1 octets.
        if (http2Window.size() < 1)
        {
            doRstByUs(stream, Http2ErrorCode.PROTOCOL_ERROR);
            return;
        }

        if (stream != null)
        {
            // 6.9.1 A sender MUST NOT allow a flow-control window to exceed 2^31-1 octets.
            stream.http2OutWindow += http2Window.size();
            if (stream.http2OutWindow > Integer.MAX_VALUE)
            {
                doRstByUs(stream, Http2ErrorCode.FLOW_CONTROL_ERROR);
                return;
            }

            writeScheduler.onHttp2Window(stream.http2StreamId);
        }
    }

    private void onStreamRst(
        Http2Stream stream,
        Http2FrameFW http2Frame)
    {
        Http2RstStreamFW http2RstStream =
                factory.http2RstStreamRO.tryWrap(http2Frame.buffer(), http2Frame.offset(), http2Frame.limit());
        if (http2RstStream == null)
        {
            decodeError = Http2ErrorCode.FRAME_SIZE_ERROR;
            return;
        }

        if (stream != null)
        {
            stream.onReset(factory.supplyTrace.getAsLong());
            closeStream(stream);
        }
    }

    private void applySetting(
        Http2SettingsId id,
        Long value)
    {
        switch (id)
        {
            case HEADER_TABLE_SIZE:
                remoteSettings.headerTableSize = value.intValue();
                break;
            case ENABLE_PUSH:
                if (!(value == 0L || value == 1L))
                {
                    decodeError = Http2ErrorCode.PROTOCOL_ERROR;
                    return;
                }
                remoteSettings.enablePush = (value == 1L);
                break;
            case MAX_CONCURRENT_STREAMS:
                remoteSettings.maxConcurrentStreams = value.intValue();
                break;
            case INITIAL_WINDOW_SIZE:
                if (value > Integer.MAX_VALUE)
                {
                    decodeError = Http2ErrorCode.FLOW_CONTROL_ERROR;
                    return;
                }
                int old = remoteSettings.initialWindowSize;
                remoteSettings.initialWindowSize = value.intValue();
                int update = value.intValue() - old;

                // 6.9.2. Initial Flow-Control Window Size
                // SETTINGS frame can alter the initial flow-control
                // window size for streams with active flow-control windows
                for(Http2Stream http2Stream: http2Streams.values())
                {
                    http2Stream.http2OutWindow += update;           // http2OutWindow can become negative
                    if (http2Stream.http2OutWindow > Integer.MAX_VALUE)
                    {
                        // 6.9.2. Initial Flow-Control Window Size
                        // An endpoint MUST treat a change to SETTINGS_INITIAL_WINDOW_SIZE that
                        // causes any flow-control window to exceed the maximum size as a
                        // connection error of type FLOW_CONTROL_ERROR.
                        decodeError = Http2ErrorCode.FLOW_CONTROL_ERROR;
                        return;
                    }
                }
                break;
            case MAX_FRAME_SIZE:
                if (value < Math.pow(2, 14) || value > Math.pow(2, 24) -1)
                {
                    decodeError = Http2ErrorCode.PROTOCOL_ERROR;
                    return;
                }
                remoteSettings.maxFrameSize = value.intValue();
                break;
            case MAX_HEADER_LIST_SIZE:
                remoteSettings.maxHeaderListSize = value.intValue();
                break;
            default:
                // Ignore the unkonwn setting
                break;
        }
    }

    private void applyInitialWindowDelta(
        Http2Stream http2Stream)
    {
        final int initialWindowDelta = initialSettings.initialWindowSize - localSettings.initialWindowSize;
        http2Stream.http2InWindow += initialWindowDelta;           // http2InWindow can become negative
    }

    private void releaseFrameSlot()
    {
        if (frameSlot != NO_SLOT)
        {
            factory.framePool.release(frameSlot);
            frameSlot = NO_SLOT;
            frameSlotLimit = 0;
        }
    }

    private void releaseHeadersSlot()
    {
        if (headersSlotIndex != NO_SLOT)
        {
            factory.headersPool.release(headersSlotIndex);
            headersSlotIndex = NO_SLOT;
            headersSlotOffset = 0;
        }
    }

    private void doResetNetworkAndCleanup()
    {
        factory.doReset(networkReply, networkRouteId, networkId, factory.supplyTrace.getAsLong());
        doCleanup();
    }

    private void doCleanup()
    {
        releaseFrameSlot();
        releaseHeadersSlot();
        http2Streams.values().forEach(this::closeStream);
        http2Streams.clear();
    }

    private void followRoute(
        int streamId,
        Http2StreamState state,
        RouteFW route)
    {
        final long applicationRouteId = route.correlationId();
        HttpWriter httpWriter = factory.httpWriter;
        Http2Stream stream = newStream(streamId, state, applicationRouteId, httpWriter);

        stream.contentLength = headersContext.contentLength;

        HttpBeginExFW beginEx = factory.httpBeginExRW.build();
        httpWriter.doHttpBegin(stream.applicationInitial, applicationRouteId,
                stream.applicationInitialId, traceId, beginEx.buffer(),
                beginEx.offset(), beginEx.sizeof());
        router.setThrottle(stream.applicationInitialId, stream::onThrottle);

        if (state == HALF_CLOSED_REMOTE)
        {
            // Deferring until WINDOW is received
            stream.endDeferred = true;
        }
    }

    // No route for the HTTP2 request, send 404 on the corresponding HTTP2 stream
    private void noRoute(
        int streamId)
    {
        send404(streamId);
    }

    // No route for the HTTP2 request, send 404 on the corresponding HTTP2 stream
    void send404(
        int streamId)
    {
        ListFW<HttpHeaderFW> headers =
                factory.headersRW.wrap(factory.errorBuf, 0, factory.errorBuf.capacity())
                                 .item(b -> b.name(":status").value("404"))
                                 .build();

        writeScheduler.headers(0, streamId, Http2Flags.END_STREAM, headers);

        if ((streamId & 0x01L) == 0x00L)
        {
            factory.counters.pushHeadersFramesWritten.getAsLong();
        }
        else
        {
            factory.counters.headersFramesWritten.getAsLong();
        }
    }

    void closeStream(
        Http2Stream stream)
    {
        if (stream.state != CLOSED)
        {
            stream.state = CLOSED;

            if (stream.isClientInitiated())
            {
                clientStreamCount--;
            }
            else
            {
                promisedStreamCount--;
            }
            factory.correlations.remove(stream.applicationReplyId);
            http2Streams.remove(stream.http2StreamId);
            stream.close();
        }
    }

    private RouteFW resolveTarget(
        Map<String, String> headers)
    {
        MessagePredicate filter = (t, b, o, l) ->
        {
            RouteFW route = factory.routeRO.wrap(b, o, o + l);
            OctetsFW extension = route.extension();
            Map<String, String> routeHeaders;
            if (extension.sizeof() == 0)
            {
                routeHeaders = EMPTY_HEADERS;
            }
            else
            {
                final HttpRouteExFW routeEx = extension.get(factory.httpRouteExRO::wrap);
                routeHeaders = new LinkedHashMap<>();
                routeEx.headers().forEach(h -> routeHeaders.put(h.name().asString(), h.value().asString()));
            }

            return headers.entrySet().containsAll(routeHeaders.entrySet());
        };

        return router.resolve(networkRouteId, authorization, filter, wrapRoute);
    }

    void handleWindow(
        WindowFW windowRO)
    {
        writeScheduler.onWindow(windowRO.trace());
    }

    void error(
        Http2ErrorCode errorCode)
    {
        writeScheduler.goaway(lastStreamId, errorCode);

        factory.counters.goawayFramesWritten.getAsLong();

        factory.doReset(network, networkRouteId, networkId, factory.supplyTrace.getAsLong());
        factory.doAbort(networkReply, networkRouteId, networkReplyId, factory.supplyTrace.getAsLong());
        http2Streams.forEach((i, s) -> s.onError(traceId));
        doCleanup();
    }

    void doRstByUs(
        Http2Stream stream,
        Http2ErrorCode errorCode)
    {
        stream.onReset(factory.supplyTrace.getAsLong());
        doRstStream(stream.http2StreamId, errorCode);
        closeStream(stream);
    }

    private void doRstStream(
        int streamId,
        Http2ErrorCode errorCode)
    {
        writeScheduler.rst(streamId, errorCode);
        factory.counters.resetStreamFramesWritten.getAsLong();
    }

    private int nextPromisedId()
    {
        maxPushPromiseStreamId += 2;
        return maxPushPromiseStreamId;
    }

    /*
     * @param streamId corresponding http2 stream-id on which service response
     *                 will be sent
     * @return a stream id on which PUSH_PROMISE can be sent
     *         -1 otherwise
     */
    private int findPushId(
        int streamId)
    {
        if (remoteSettings.enablePush && promisedStreamCount +1 < remoteSettings.maxConcurrentStreams)
        {
            // PUSH_PROMISE frames MUST only be sent on a peer-initiated stream
            if (streamId%2 == 0)
            {
                // Find a stream on which PUSH_PROMISE can be sent
                return http2Streams.entrySet()
                                   .stream()
                                   .map(Map.Entry::getValue)
                                   .filter(s -> (s.http2StreamId & 0x01) == 1)     // client-initiated stream
                                   .filter(s -> s.state == OPEN || s.state == HALF_CLOSED_REMOTE)
                                   .mapToInt(s -> s.http2StreamId)
                                   .findAny()
                                   .orElse(-1);
            }
            else
            {
                return streamId;        // client-initiated stream
            }
        }
        return -1;
    }

    private void doPromisedRequest(
        int http2StreamId,
        long authorization,
        ListFW<HttpHeaderFW> headers)
    {
        Map<String, String> headersMap = new HashMap<>();
        headers.forEach(
                httpHeader -> headersMap.put(httpHeader.name().asString(), httpHeader.value().asString()));
        RouteFW route = resolveTarget(headersMap);
        final long applicationRouteId = route.correlationId();
        HttpWriter httpWriter = factory.httpWriter;
        Http2Stream http2Stream = newStream(http2StreamId, HALF_CLOSED_REMOTE, applicationRouteId, httpWriter);
        final MessageConsumer applicationTarget = http2Stream.applicationInitial;
        long targetId = http2Stream.applicationInitialId;

        httpWriter.doHttpBegin(applicationTarget, applicationRouteId, targetId, factory.supplyTrace.getAsLong(), authorization,
                hs -> headers.forEach(h -> hs.item(b -> b.name(h.name()).value(h.value()))));
        router.setThrottle(targetId, http2Stream::onThrottle);
        http2Stream.endDeferred = true;
    }

    private Http2Stream newStream(
        int http2StreamId,
        Http2StreamState state,
        long applicationRouteId,
        HttpWriter httpWriter)
    {
        assert http2StreamId != 0;

        Http2Stream http2Stream = new Http2Stream(factory, this, http2StreamId, state, applicationRouteId, httpWriter);
        http2Streams.put(http2StreamId, http2Stream);

        Correlation correlation = new Correlation(http2Stream.applicationReplyId, networkReplyId, writeScheduler,
                this::doPromisedRequest, this, http2StreamId, encodeContext, this::nextPromisedId, this::findPushId);

        factory.correlations.put(http2Stream.applicationReplyId, correlation);
        if (http2Stream.isClientInitiated())
        {
            clientStreamCount++;
        }
        else
        {
            promisedStreamCount++;
        }
        return http2Stream;
    }

    private void validateHeaderFieldType(
        HpackHeaderFieldFW hf)
    {
        if (!headersContext.error() && hf.type() == UNKNOWN)
        {
            headersContext.connectionError = Http2ErrorCode.COMPRESSION_ERROR;
        }
    }

    private void dynamicTableSizeUpdate(
        HpackHeaderFieldFW hf)
    {
        if (!headersContext.error())
        {
            switch (hf.type())
            {
                case INDEXED:
                case LITERAL:
                    expectDynamicTableSizeUpdate = false;
                    break;
                case UPDATE:
                    if (!expectDynamicTableSizeUpdate)
                    {
                        // dynamic table size update MUST occur at the beginning of the first header block
                        headersContext.connectionError = Http2ErrorCode.COMPRESSION_ERROR;
                        return;
                    }
                    int maxTableSize = hf.tableSize();
                    if (maxTableSize > localSettings.headerTableSize)
                    {
                        headersContext.connectionError = Http2ErrorCode.COMPRESSION_ERROR;
                        return;
                    }
                    decodeContext.updateSize(hf.tableSize());
                    break;
                default:
                    break;
            }
        }
    }

    private void validatePseudoHeaders(
        DirectBuffer name,
        DirectBuffer value)
    {
        if (!headersContext.error())
        {
            if (name.capacity() > 0 && name.getByte(0) == ':')
            {
                // All pseudo-header fields MUST appear in the header block before regular header fields
                if (headersContext.regularHeader)
                {
                    headersContext.streamError = Http2ErrorCode.PROTOCOL_ERROR;
                    return;
                }
                // request pseudo-header fields MUST be one of :authority, :method, :path, :scheme,
                int index = decodeContext.index(name);
                switch (index)
                {
                    case 1:             // :authority
                        break;
                    case 2:             // :method
                        headersContext.method++;
                        break;
                    case 4:             // :path
                        if (value.capacity() > 0)       // :path MUST not be empty
                        {
                            headersContext.path++;
                        }
                        break;
                    case 6:             // :scheme
                        headersContext.scheme++;
                        break;
                    default:
                        headersContext.streamError = Http2ErrorCode.PROTOCOL_ERROR;
                        return;
                }
            }
            else
            {
                headersContext.regularHeader = true;
            }
        }
    }

    private void validateTrailerFieldName(
        DirectBuffer name,
        DirectBuffer value)
    {
        if (!headersContext.error())
        {
            if (name.capacity() > 0 && name.getByte(0) == ':')
            {
                headersContext.streamError = Http2ErrorCode.PROTOCOL_ERROR;
                return;
            }
        }
    }


    private void connectionHeaders(
        DirectBuffer name,
        DirectBuffer value)
    {

        if (!headersContext.error() && name.equals(HpackContext.CONNECTION))
        {
            headersContext.streamError = Http2ErrorCode.PROTOCOL_ERROR;
        }
    }

    private void contentLengthHeader(
        DirectBuffer name,
        DirectBuffer value)
    {
        if (!headersContext.error() && name.equals(decodeContext.nameBuffer(28)))
        {
            String contentLength = value.getStringWithoutLengthUtf8(0, value.capacity());
            headersContext.contentLength = Long.parseLong(contentLength);
        }
    }

    // 8.1.2.2 TE header MUST NOT contain any value other than "trailers".
    private void teHeader(
        DirectBuffer name,
        DirectBuffer value)
    {
        if (!headersContext.error() && name.equals(TE) && !value.equals(TRAILERS))
        {
            headersContext.streamError = Http2ErrorCode.PROTOCOL_ERROR;
        }
    }

    private void uppercaseHeaders(
        DirectBuffer name,
        DirectBuffer value)
    {
        if (!headersContext.error())
        {
            for(int i=0; i < name.capacity(); i++)
            {
                if (name.getByte(i) >= 'A' && name.getByte(i) <= 'Z')
                {
                    headersContext.streamError = Http2ErrorCode.PROTOCOL_ERROR;
                }
            }
        }
    }

    // Collect headers into map to resolve target
    // TODO avoid this
    private void collectHeaders(
        DirectBuffer name,
        DirectBuffer value)
    {
        if (!headersContext.error())
        {
            String nameStr = name.getStringWithoutLengthUtf8(0, name.capacity());
            String valueStr = value.getStringWithoutLengthUtf8(0, value.capacity());
            // TODO cookie needs to be appended with ';'
            headersContext.headers.merge(nameStr, valueStr, (o, n) -> String.format("%s, %s", o, n));
        }
    }

    // Writes HPACK header field to http representation in a buffer
    private void mapToHttp(
        DirectBuffer name,
        DirectBuffer value)
    {
        if (!headersContext.error())
        {
            factory.httpBeginExRW.headersItem(item -> item.name(name, 0, name.capacity())
                                                          .value(value, 0, value.capacity()));
        }
    }

    private void addDefaultPortToAuthorityIfNeeded()
    {
        String authority = headersContext.headers.get(":authority");
        if (authority.indexOf(':') == -1)
        {
            String scheme = headersContext.headers.get(":scheme");
            String defaultPort = scheme.equals("https") ? ":443" : ":80";
            headersContext.headers.put(":authority", authority + defaultPort);

            // rebuild http request as :authority header is modified
            factory.httpBeginExRW.wrap(factory.scratch, 0, factory.scratch.capacity());
            for(Map.Entry<String, String> e : headersContext.headers.entrySet())
            {
                factory.httpBeginExRW.headersItem(item -> item.name(e.getKey())
                                                              .value(e.getValue()));
            }
        }
    }

    private void decodeHeaderField(
        HpackHeaderFieldFW hf,
        BiConsumer<DirectBuffer, DirectBuffer> nameValue)
    {
        if (!headersContext.error())
        {
            decodeHF(hf, nameValue);
        }
    }

    private void decodeHF(
        HpackHeaderFieldFW hf,
        BiConsumer<DirectBuffer, DirectBuffer> nameValue)
    {
        int index;
        DirectBuffer name = null;
        DirectBuffer value = null;

        switch (hf.type())
        {
            case INDEXED :
                index = hf.index();
                if (!decodeContext.valid(index))
                {
                    headersContext.connectionError = Http2ErrorCode.COMPRESSION_ERROR;
                    return;
                }
                name = decodeContext.nameBuffer(index);
                value = decodeContext.valueBuffer(index);
                nameValue.accept(name, value);
                break;

            case LITERAL :
                HpackLiteralHeaderFieldFW literalRO = hf.literal();
                if (literalRO.error())
                {
                    headersContext.connectionError = Http2ErrorCode.COMPRESSION_ERROR;
                    return;
                }
                switch (literalRO.nameType())
                {
                    case INDEXED:
                    {
                        index = literalRO.nameIndex();
                        if (!decodeContext.valid(index))
                        {
                            headersContext.connectionError = Http2ErrorCode.COMPRESSION_ERROR;
                            return;
                        }
                        name = decodeContext.nameBuffer(index);

                        HpackStringFW valueRO = literalRO.valueLiteral();
                        value = valueRO.payload();
                        if (valueRO.huffman())
                        {
                            MutableDirectBuffer dst = new UnsafeBuffer(new byte[4096]); // TODO
                            int length = HpackHuffman.decode(value, dst);
                            if (length == -1)
                            {
                                headersContext.connectionError = Http2ErrorCode.COMPRESSION_ERROR;
                                return;
                            }
                            value = new UnsafeBuffer(dst, 0, length);
                        }
                        nameValue.accept(name, value);
                    }
                    break;
                    case NEW:
                    {
                        HpackStringFW nameRO = literalRO.nameLiteral();
                        name = nameRO.payload();
                        if (nameRO.huffman())
                        {
                            MutableDirectBuffer dst = new UnsafeBuffer(new byte[4096]); // TODO
                            int length = HpackHuffman.decode(name, dst);
                            if (length == -1)
                            {
                                headersContext.connectionError = Http2ErrorCode.COMPRESSION_ERROR;
                                return;
                            }
                            name = new UnsafeBuffer(dst, 0, length);
                        }

                        HpackStringFW valueRO = literalRO.valueLiteral();
                        value = valueRO.payload();
                        if (valueRO.huffman())
                        {
                            MutableDirectBuffer dst = new UnsafeBuffer(new byte[4096]); // TODO
                            int length = HpackHuffman.decode(value, dst);
                            if (length == -1)
                            {
                                headersContext.connectionError = Http2ErrorCode.COMPRESSION_ERROR;
                                return;
                            }
                            value = new UnsafeBuffer(dst, 0, length);
                        }
                        nameValue.accept(name, value);
                    }
                    break;
                }
                if (literalRO.literalType() == INCREMENTAL_INDEXING)
                {
                    // make a copy for name and value as they go into dynamic table (outlives current frame)
                    MutableDirectBuffer nameCopy = new UnsafeBuffer(new byte[name.capacity()]);
                    nameCopy.putBytes(0, name, 0, name.capacity());
                    MutableDirectBuffer valueCopy = new UnsafeBuffer(new byte[value.capacity()]);
                    valueCopy.putBytes(0, value, 0, value.capacity());
                    decodeContext.add(nameCopy, valueCopy);
                }
                break;
            default:
                break;
        }
    }


    void mapPushPromise(
        ListFW<HttpHeaderFW> httpHeaders,
        HpackHeaderBlockFW.Builder builder)
    {
        httpHeaders.forEach(h -> builder.header(b -> mapHeader(h, b)));
    }

    void mapTrailers(
        ListFW<HttpHeaderFW> httpHeaders,
        HpackHeaderBlockFW.Builder builder)
    {
        httpHeaders.forEach(h ->
        {
            if (validHeader(h))
            {
                builder.header(b -> mapHeader(h, b));
            }
        });
    }

    void mapHeaders(
        ListFW<HttpHeaderFW> httpHeaders,
        HpackHeaderBlockFW.Builder builder)
    {
        encodeHeadersContext.reset();

        httpHeaders.forEach(this::status)                       // checks if there is :status
                   .forEach(this::accessControlAllowOrigin)     // checks if there is access-control-allow-origin
                   .forEach(this::serverHeader)                 // checks if there is server
                   .forEach(this::connectionHeaders);           // collects all connection headers
        if (!encodeHeadersContext.status)
        {
            builder.header(b -> b.indexed(8));          // no mandatory :status header, add :status: 200
        }

        httpHeaders.forEach(h ->
        {
            if (validHeader(h))
            {
                builder.header(b -> mapHeader(h, b));
            }
        });

        if (factory.config.accessControlAllowOrigin() && !encodeHeadersContext.accessControlAllowOrigin)
        {
            builder.header(b -> b.literal(l -> l.type(WITHOUT_INDEXING).name(20).value(DEFAULT_ACCESS_CONTROL_ALLOW_ORIGIN)));
        }

        // add configured Server header if there is no Server header in response
        if (factory.config.serverHeader() != null && !encodeHeadersContext.serverHeader)
        {
            DirectBuffer server = factory.config.serverHeader();
            builder.header(b -> b.literal(l -> l.type(WITHOUT_INDEXING).name(54).value(server)));
        }
    }

    private void status(
        HttpHeaderFW httpHeader)
    {
        if (!encodeHeadersContext.status)
        {
            StringFW name = httpHeader.name();
            String16FW value = httpHeader.value();
            factory.nameRO.wrap(name.buffer(), name.offset() + 1, name.sizeof() - 1); // +1, -1 for length-prefixed buffer
            factory.valueRO.wrap(value.buffer(), value.offset() + 2, value.sizeof() - 2);

            if (factory.nameRO.equals(encodeContext.nameBuffer(8)))
            {
                encodeHeadersContext.status = true;
            }
        }
    }

    // Checks if response has access-control-allow-origin header
    private void accessControlAllowOrigin(
        HttpHeaderFW httpHeader)
    {
        if (factory.config.accessControlAllowOrigin() && !encodeHeadersContext.accessControlAllowOrigin)
        {
            StringFW name = httpHeader.name();
            String16FW value = httpHeader.value();
            factory.nameRO.wrap(name.buffer(), name.offset() + 1, name.sizeof() - 1); // +1, -1 for length-prefixed buffer
            factory.valueRO.wrap(value.buffer(), value.offset() + 2, value.sizeof() - 2);

            if (factory.nameRO.equals(encodeContext.nameBuffer(20)))
            {
                encodeHeadersContext.accessControlAllowOrigin = true;
            }
        }
    }

    // Checks if response has server header
    private void serverHeader(
        HttpHeaderFW httpHeader)
    {
        if (factory.config.serverHeader() != null && !encodeHeadersContext.serverHeader)
        {
            StringFW name = httpHeader.name();
            String16FW value = httpHeader.value();
            factory.nameRO.wrap(name.buffer(), name.offset() + 1, name.sizeof() - 1); // +1, -1 for length-prefixed buffer
            factory.valueRO.wrap(value.buffer(), value.offset() + 2, value.sizeof() - 2);

            if (factory.nameRO.equals(encodeContext.nameBuffer(54)))
            {
                encodeHeadersContext.serverHeader = true;
            }
        }
    }

    private void connectionHeaders(
        HttpHeaderFW httpHeader)
    {
        StringFW name = httpHeader.name();
        String16FW value = httpHeader.value();
        factory.nameRO.wrap(name.buffer(), name.offset() + 1, name.sizeof() - 1); // +1, -1 for length-prefixed buffer

        if (factory.nameRO.equals(CONNECTION))
        {
            String[] headers = value.asString().split(",");
            for (String header : headers)
            {
                encodeHeadersContext.connectionHeaders.add(header.trim());
            }
        }
    }

    private boolean validHeader(
        HttpHeaderFW httpHeader)
    {
        StringFW name = httpHeader.name();
        String16FW value = httpHeader.value();
        factory.nameRO.wrap(name.buffer(), name.offset() + 1, name.sizeof() - 1); // +1, -1 for length-prefixed buffer
        factory.valueRO.wrap(value.buffer(), value.offset() + 2, value.sizeof() - 2);

        // Removing 8.1.2.1 Pseudo-Header Fields
        // Not sending error as it will allow requests to loop back
        if (factory.nameRO.equals(encodeContext.nameBuffer(1)) ||          // :authority
                factory.nameRO.equals(encodeContext.nameBuffer(2)) ||      // :method
                factory.nameRO.equals(encodeContext.nameBuffer(4)) ||      // :path
                factory.nameRO.equals(encodeContext.nameBuffer(6)))        // :scheme
        {
            return false;
        }

        // Removing 8.1.2.2 connection-specific header fields from response
        if (factory.nameRO.equals(encodeContext.nameBuffer(57)) ||         // transfer-encoding
                factory.nameRO.equals(CONNECTION) ||
                factory.nameRO.equals(KEEP_ALIVE) ||
                factory.nameRO.equals(PROXY_CONNECTION) ||
                factory.nameRO.equals(UPGRADE))
        {
            return false;
        }

        // Removing any header that is nominated by Connection header field
        for(String connectionHeader: encodeHeadersContext.connectionHeaders)
        {
            if (name.asString().equals(connectionHeader))
            {
                return false;
            }
        }

        return true;
    }

    // Map http1.1 header to http2 header field in HEADERS, PUSH_PROMISE request
    private void mapHeader(
        HttpHeaderFW httpHeader,
        HpackHeaderFieldFW.Builder builder)
    {
        StringFW name = httpHeader.name();
        String16FW value = httpHeader.value();
        factory.nameRO.wrap(name.buffer(), name.offset() + 1, name.sizeof() - 1); // +1, -1 for length-prefixed buffer
        factory.valueRO.wrap(value.buffer(), value.offset() + 2, value.sizeof() - 2);

        int index = encodeContext.index(factory.nameRO, factory.valueRO);
        if (index != -1)
        {
            // Indexed
            builder.indexed(index);
        }
        else
        {
            // Literal
            builder.literal(literalBuilder -> buildLiteral(literalBuilder, encodeContext));
        }
    }

    // Building Literal representation of header field
    // TODO dynamic table, huffman, never indexed
    private void buildLiteral(
        HpackLiteralHeaderFieldFW.Builder builder,
        HpackContext hpackContext)
    {
        int nameIndex = hpackContext.index(factory.nameRO);
        builder.type(WITHOUT_INDEXING);
        if (nameIndex != -1)
        {
            builder.name(nameIndex);
        }
        else
        {
            builder.name(factory.nameRO, 0, factory.nameRO.capacity());
        }
        builder.value(factory.valueRO, 0, factory.valueRO.capacity());
    }


    void handleHttpBegin(
        BeginFW begin,
        MessageConsumer applicationReplyThrottle,
        long applicationRouteId,
        long applicationReplyId,
        Correlation correlation)
    {
        OctetsFW extension = begin.extension();
        Http2Stream stream = http2Streams.get(correlation.http2StreamId);
        if (stream == null)
        {
            factory.doReset(applicationReplyThrottle, applicationRouteId, applicationReplyId, factory.supplyTrace.getAsLong());
        }
        else
        {
            stream.applicationReplyThrottle = applicationReplyThrottle;

            stream.sendHttpWindow(factory.supplyTrace.getAsLong());

            if (extension.sizeof() > 0)
            {
                HttpBeginExFW beginEx = extension.get(factory.beginExRO::wrap);
                writeScheduler.headers(begin.trace(), correlation.http2StreamId, Http2Flags.NONE, beginEx.headers());

                if ((correlation.http2StreamId & 0x01L) == 0x00L)
                {
                    factory.counters.pushHeadersFramesWritten.getAsLong();
                }
                else
                {
                    factory.counters.headersFramesWritten.getAsLong();
                }
            }
        }
    }

    void handleHttpData(
        DataFW dataRO,
        Correlation correlation)
    {
        OctetsFW extension = dataRO.extension();
        OctetsFW payload = dataRO.payload();
        long traceId = dataRO.trace();

        if (extension.sizeof() > 0)
        {

            int pushStreamId = correlation.pushStreamIds.applyAsInt(correlation.http2StreamId);
            if (pushStreamId != -1)
            {
                int promisedStreamId = correlation.promisedStreamIds.getAsInt();
                Http2DataExFW dataEx = extension.get(factory.dataExRO::wrap);
                writeScheduler.pushPromise(traceId, pushStreamId, promisedStreamId, dataEx.headers());
                correlation.pushHandler.accept(promisedStreamId, dataRO.authorization(), dataEx.headers());

                factory.counters.pushPromiseFramesWritten.getAsLong();
            }
            else
            {
                factory.counters.pushPromiseFramesSkipped.getAsLong();
            }
        }

        if (payload != null)
        {
            Http2Stream stream = http2Streams.get(correlation.http2StreamId);
            if (stream != null)
            {
                stream.applicationReplyBudget -= dataRO.length() + dataRO.padding();
                if (stream.applicationReplyBudget < 0)
                {
                    doRstByUs(stream, Http2ErrorCode.INTERNAL_ERROR);
                    return;
                }
            }

            writeScheduler.data(traceId, correlation.http2StreamId, payload.buffer(), payload.offset(), payload.sizeof());

            factory.counters.dataFramesWritten.getAsLong();
        }
    }

    void handleHttpEnd(
        EndFW end,
        Correlation correlation)
    {
        Http2Stream stream = http2Streams.get(correlation.http2StreamId);
        if (stream != null)
        {
            final OctetsFW extension = end.extension();
            if (extension.sizeof() != 0)
            {
                final HttpEndExFW httpEndEx = extension.get(factory.httpEndExRO::wrap);
                ListFW<HttpHeaderFW> trailers = httpEndEx.trailers();
                writeScheduler.trailers(end.trace(), stream.http2StreamId, Http2Flags.END_STREAM, trailers);
            }
            else
            {
                stream.onHttpEnd(end.trace());
            }
        }
    }

    void handleHttpAbort(
        AbortFW abort,
        Correlation correlation)
    {
        Http2Stream stream = http2Streams.get(correlation.http2StreamId);

        if (stream != null)
        {
            stream.onHttpAbort();
        }
    }

    private static final class HeadersContext
    {
        Http2ErrorCode connectionError;
        Map<String, String> headers = new LinkedHashMap<>();
        int method;
        int scheme;
        int path;
        boolean regularHeader;
        Http2ErrorCode streamError;
        long contentLength = -1;

        void reset()
        {
            connectionError = null;
            headers.clear();
            method = 0;
            scheme = 0;
            path = 0;
            regularHeader = false;
            streamError = null;
            contentLength = -1;
        }

        boolean error()
        {
            return streamError != null || connectionError != null;
        }
    }

    private static final class EncodeHeadersContext
    {
        boolean status;
        boolean accessControlAllowOrigin;
        boolean serverHeader;
        final List<String> connectionHeaders = new ArrayList<>();

        void reset()
        {
            status = false;
            accessControlAllowOrigin = false;
            serverHeader = false;
            connectionHeaders.clear();
        }

    }

    @FunctionalInterface
    private interface DecoderState
    {
        int decode(DirectBuffer buffer, int offset, int length);
    }
}
