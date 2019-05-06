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

import java.util.function.BiConsumer;

import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.reaktivity.nukleus.function.MessageConsumer;
import org.reaktivity.nukleus.http2.internal.types.HttpHeaderFW;
import org.reaktivity.nukleus.http2.internal.types.ListFW;
import org.reaktivity.nukleus.http2.internal.types.stream.DataFW;
import org.reaktivity.nukleus.http2.internal.types.stream.EndFW;
import org.reaktivity.nukleus.http2.internal.types.stream.HpackHeaderBlockFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2DataFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2ErrorCode;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2Flags;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2GoawayFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2HeadersFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2PingFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2PushPromiseFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2RstStreamFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2SettingsFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2WindowUpdateFW;

class Http2Writer
{
    private final DataFW.Builder dataRW = new DataFW.Builder();
    private final EndFW.Builder endRW = new EndFW.Builder();

    private final Http2SettingsFW.Builder settingsRW = new Http2SettingsFW.Builder();
    private final Http2RstStreamFW.Builder http2ResetRW = new Http2RstStreamFW.Builder();
    private final Http2GoawayFW.Builder goawayRW = new Http2GoawayFW.Builder();
    private final Http2PingFW.Builder pingRW = new Http2PingFW.Builder();
    private final Http2WindowUpdateFW.Builder http2WindowRW = new Http2WindowUpdateFW.Builder();
    private final Http2DataFW.Builder http2DataRW = new Http2DataFW.Builder();
    private final Http2HeadersFW.Builder http2HeadersRW = new Http2HeadersFW.Builder();
    private final Http2PushPromiseFW.Builder pushPromiseRW = new Http2PushPromiseFW.Builder();

    final MutableDirectBuffer writeBuffer;

    Http2Writer(
        MutableDirectBuffer writeBuffer)
    {
        this.writeBuffer = writeBuffer;
    }

    void doData(
        MessageConsumer receiver,
        long routeId,
        long streamId,
        long traceId,
        int padding,
        MutableDirectBuffer payload,
        int offset,
        int length)
    {
        assert offset >= DataFW.FIELD_OFFSET_PAYLOAD;

        final DataFW data = dataRW.wrap(payload, offset - DataFW.FIELD_OFFSET_PAYLOAD, offset + length)
                .routeId(routeId)
                .streamId(streamId)
                .trace(traceId)
                .groupId(0)
                .padding(padding)
                .payload(p -> p.set((b, o, l) -> length))
                .build();

        receiver.accept(data.typeId(), data.buffer(), data.offset(), data.sizeof());
    }

    void doEnd(
        MessageConsumer receiver,
        long routeId,
        long streamId)
    {
        final EndFW end = endRW.wrap(writeBuffer, 0, writeBuffer.capacity())
                .routeId(routeId)
                .streamId(streamId)
                .build();

        receiver.accept(end.typeId(), end.buffer(), end.offset(), end.sizeof());
    }

    int settings(
        int offset,
        int length,
        int maxConcurrentStreams,
        int initialWindowSize)
    {
        int written = settingsRW.wrap(writeBuffer, offset, offset + length)
                         .maxConcurrentStreams(maxConcurrentStreams)
                         .initialWindowSize(initialWindowSize)
                         .build()
                         .sizeof();
        assert written == length;
        return written;
    }

    int settingsAck(
        int offset,
        int length)
    {
        int written = settingsRW.wrap(writeBuffer, offset, offset + length)
                         .ack()
                         .build()
                         .sizeof();
        assert written == length;
        return written;
    }

    int rst(
        int offset,
        int length,
        int streamId,
        Http2ErrorCode errorCode)
    {
        int written = http2ResetRW.wrap(writeBuffer, offset, offset + length)
                           .streamId(streamId)
                           .errorCode(errorCode)
                           .build()
                           .sizeof();
        assert written == length;
        return written;
    }

    int goaway(
        int offset,
        int length,
        int lastStreamId,
        Http2ErrorCode errorCode)
    {
        int written = goawayRW.wrap(writeBuffer, offset, offset + length)
                       .lastStreamId(lastStreamId)
                       .errorCode(errorCode)
                       .build()
                       .sizeof();
        assert written == length;
        return written;
    }

    int pingAck(
        int offset,
        int length,
        DirectBuffer payloadBuffer,
        int payloadOffset,
        int payloadLength)
    {
        int written = pingRW.wrap(writeBuffer, offset, offset + length)
                      .ack()
                      .payload(payloadBuffer, payloadOffset, payloadLength)
                      .build()
                      .sizeof();
        assert written == length;
        return written;
    }

    int windowUpdate(
        int offset,
        int length,
        int streamId,
        int update)
    {
        int written = http2WindowRW.wrap(writeBuffer, offset, offset + length)
                            .streamId(streamId)
                            .size(update)
                            .build()
                            .sizeof();
        assert written == length;
        return written;
    }

    int data(
        int offset,
        int length,
        int streamId,
        DirectBuffer payloadBuffer,
        int payloadOffset,
        int payloadLength)
    {
        int written = http2DataRW.wrap(writeBuffer, offset, offset + length)
                           .streamId(streamId)
                           .payload(payloadBuffer, payloadOffset, payloadLength)
                           .build()
                           .sizeof();
        assert written == length;
        return written;
    }

    int dataEos(
        int offset,
        int length,
        int streamId)
    {
        assert streamId != 0;

        int written = http2DataRW.wrap(writeBuffer, offset, offset + length)
                           .streamId(streamId)
                           .endStream()
                           .build()
                           .sizeof();
        assert written == length;
        return written;
    }

    int headers(
        int offset,
        int lengthGuess,
        int streamId,
        byte flags,
        ListFW<HttpHeaderFW> headers,
        BiConsumer<ListFW<HttpHeaderFW>, HpackHeaderBlockFW.Builder> builder)
    {
        byte headersFlags = (byte) (flags | Http2Flags.END_HEADERS);

        int written = http2HeadersRW.wrap(writeBuffer, offset, offset + lengthGuess)
                             .streamId(streamId)
                             .flags(headersFlags)
                             .headers(b -> builder.accept(headers, b))
                             .build()
                             .sizeof();
        assert written <= lengthGuess;
        return written;
    }

    int headers(
        int offset,
        int length,
        int streamId,
        byte flags,
        DirectBuffer blockBuffer,
        int blockOffset,
        int blockLength)
    {
        assert streamId != 0;

        byte headersFlags = (byte) (flags | Http2Flags.END_HEADERS);
        int written = http2HeadersRW.wrap(writeBuffer, offset, offset + length)
                             .streamId(streamId)
                             .flags(headersFlags)
                             .payload(blockBuffer, blockOffset, blockLength)
                             .build()
                             .sizeof();
        assert written == length;
        return written;
    }

    int pushPromise(
        int offset,
        int lengthGuess,
        int streamId,
        int promisedStreamId,
        ListFW<HttpHeaderFW> headers,
        BiConsumer<ListFW<HttpHeaderFW>, HpackHeaderBlockFW.Builder> builder)
    {
        int written = pushPromiseRW.wrap(writeBuffer, offset, offset + lengthGuess)
                            .streamId(streamId)
                            .promisedStreamId(promisedStreamId)
                            .endHeaders()
                            .headers(b -> builder.accept(headers, b))
                            .build()
                            .sizeof();
        assert written <= lengthGuess;
        return written;
    }

    int pushPromise(
        int offset,
        int length,
        int streamId,
        int promisedStreamId,
        DirectBuffer blockBuffer,
        int blockOffset,
        int blockLength)
    {
        assert streamId != 0;

        int written = pushPromiseRW.wrap(writeBuffer, offset, offset + length)
                            .streamId(streamId)
                            .promisedStreamId(promisedStreamId)
                            .endHeaders()
                            .headers(blockBuffer, blockOffset, blockLength)
                            .build()
                            .sizeof();
        assert written == length;
        return written;
    }

}
