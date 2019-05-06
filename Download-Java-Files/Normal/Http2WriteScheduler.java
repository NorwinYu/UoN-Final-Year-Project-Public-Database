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

import static org.reaktivity.nukleus.http2.internal.types.stream.Http2FrameType.DATA;
import static org.reaktivity.nukleus.http2.internal.types.stream.Http2FrameType.GO_AWAY;
import static org.reaktivity.nukleus.http2.internal.types.stream.Http2FrameType.HEADERS;
import static org.reaktivity.nukleus.http2.internal.types.stream.Http2FrameType.PING;
import static org.reaktivity.nukleus.http2.internal.types.stream.Http2FrameType.PUSH_PROMISE;
import static org.reaktivity.nukleus.http2.internal.types.stream.Http2FrameType.RST_STREAM;
import static org.reaktivity.nukleus.http2.internal.types.stream.Http2FrameType.SETTINGS;
import static org.reaktivity.nukleus.http2.internal.types.stream.Http2FrameType.WINDOW_UPDATE;

import java.util.Deque;
import java.util.LinkedList;

import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;
import org.reaktivity.nukleus.function.MessageConsumer;
import org.reaktivity.nukleus.http2.internal.types.HttpHeaderFW;
import org.reaktivity.nukleus.http2.internal.types.ListFW;
import org.reaktivity.nukleus.http2.internal.types.stream.HpackHeaderBlockFW;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2ErrorCode;
import org.reaktivity.nukleus.http2.internal.types.stream.Http2FrameType;

public class Http2WriteScheduler implements WriteScheduler
{
    private final Http2Connection connection;
    private final Http2Writer http2Writer;
    private final NukleusWriteScheduler writer;
    private final Deque<WriteScheduler.Entry> replyQueue;

    private boolean end;
    private boolean endSent;
    private int entryCount;
    private long windowTraceId;

    Http2WriteScheduler(
        Http2Connection connection,
        MessageConsumer networkReply,
        Http2Writer http2Writer,
        long networkRouteId,
        long networkReplyId)
    {
        this.connection = connection;
        this.http2Writer = http2Writer;
        this.writer = new NukleusWriteScheduler(connection, networkReply, http2Writer, networkRouteId, networkReplyId);
        this.replyQueue = new LinkedList<>();
    }

    @Override
    public boolean windowUpdate(int streamId, int update)
    {
        long traceId = connection.factory.supplyTrace.getAsLong();
        int length = 4;                     // 4 window size increment
        int sizeof = length + 9;            // +9 for HTTP2 framing
        Http2FrameType type = WINDOW_UPDATE;
        Http2Stream stream = stream(streamId);

        if (!buffered() && hasNukleusBudget(length))
        {
            int written = http2Writer.windowUpdate(writer.offset(), sizeof, streamId, update);
            postWrite(stream, type, written);
            writer.flush();
        }
        else
        {
            Entry entry = new WindowUpdateEntry(stream, streamId, traceId, length, type, update);
            addEntry(entry);
        }
        return true;
    }

    @Override
    public boolean pingAck(DirectBuffer buffer, int offset, int length)
    {
        assert length == 8;

        long traceId = connection.factory.supplyTrace.getAsLong();
        int streamId = 0;
        int sizeof = 9 + length;             // +9 for HTTP2 framing, +8 for a ping
        Http2FrameType type = PING;

        if (!buffered() && hasNukleusBudget(length))
        {
            int written = http2Writer.pingAck(writer.offset(), sizeof, buffer, offset, length);
            postWrite(null, type, written);
            writer.flush();
        }
        else
        {
            MutableDirectBuffer copy = new UnsafeBuffer(new byte[8]);
            copy.putBytes(0, buffer, offset, length);
            Entry entry = new PingAckEntry(null, streamId, traceId, length, type, copy, 0, length);
            addEntry(entry);
        }

        return true;
    }

    @Override
    public boolean goaway(int lastStreamId, Http2ErrorCode errorCode)
    {
        int streamId = 0;
        long traceId = connection.factory.supplyTrace.getAsLong();
        int length = 8;                     // 8 for goaway payload
        int sizeof = length + 9;            // +9 for HTTP2 framing
        Http2FrameType type = GO_AWAY;

        if (!buffered() && hasNukleusBudget(length))
        {
            int written = http2Writer.goaway(writer.offset(), sizeof, lastStreamId, errorCode);
            postWrite(null, type, written);
            writer.flush();
        }
        else
        {
            Entry entry = new GoawayEntry(null, streamId, traceId, length, type, lastStreamId, errorCode);
            addEntry(entry);
        }

        return true;
    }

    @Override
    public boolean rst(int streamId, Http2ErrorCode errorCode)
    {
        long traceId = connection.factory.supplyTrace.getAsLong();
        int length = 4;                     // 4 for RST_STREAM payload
        int sizeof = length + 9;            // +9 for HTTP2 framing
        Http2Stream stream = stream(streamId);
        Http2FrameType type = RST_STREAM;

        if (!buffered() && hasNukleusBudget(length))
        {
            int written = http2Writer.rst(writer.offset(), sizeof, streamId, errorCode);
            postWrite(stream, type, written);
            writer.flush();
        }
        else
        {
            Entry entry = new RstEntry(stream, streamId, traceId, length, type, errorCode);
            addEntry(entry);
        }

        return true;
    }

    @Override
    public boolean settings(int maxConcurrentStreams, int initialWindowSize)
    {
        long traceId = connection.factory.supplyTrace.getAsLong();
        int streamId = 0;
        int length = 12;                     // 6 for a setting
        int sizeof = length + 9;            // +9 for HTTP2 framing
        Http2FrameType type = SETTINGS;

        if (!buffered() && hasNukleusBudget(length))
        {
            int written = http2Writer.settings(writer.offset(), sizeof, maxConcurrentStreams, initialWindowSize);
            postWrite(null, type, written);
            writer.flush();
        }
        else
        {
            Entry entry = new SettingsEntry(null, streamId, traceId, length, type, maxConcurrentStreams, initialWindowSize);
            addEntry(entry);
        }

        return true;
    }

    @Override
    public boolean settingsAck()
    {
        long traceId = connection.factory.supplyTrace.getAsLong();
        int streamId = 0;
        int length = 0;
        int sizeof = length + 9;                 // +9 for HTTP2 framing
        Http2FrameType type = SETTINGS;

        if (!buffered() && hasNukleusBudget(length))
        {
            int written = http2Writer.settingsAck(writer.offset(), sizeof);
            postWrite(null, type, written);
            writer.flush();
        }
        else
        {
            Entry entry = new SettingsAckEntry(null, streamId, traceId, length, type);
            addEntry(entry);
        }

        return true;
    }

    @Override
    public boolean trailers(long traceId, int streamId, byte flags, ListFW<HttpHeaderFW> headers)
    {
        MutableDirectBuffer copy = null;
        int length = headersLength(headers);        // estimate only
        int sizeof = 9 + headersLength(headers);    // +9 for HTTP2 framing
        Http2FrameType type = HEADERS;
        Http2Stream stream = stream(streamId);

        if (stream == null)
        {
            return true;
        }

        if (buffered() || !hasNukleusBudget(length))
        {
            copy = new UnsafeBuffer(new byte[8192]);
            connection.factory.blockRW.wrap(copy, 0, copy.capacity());
            connection.mapHeaders(headers, connection.factory.blockRW);
            HpackHeaderBlockFW block = connection.factory.blockRW.build();
            length = block.sizeof();
            sizeof = 9 + length;
        }

        stream.endStream = true;

        if (buffered() || !hasNukleusBudget(length))
        {
            Entry entry = new TrailersEntry(stream, streamId, traceId, length, type, flags, copy, 0, length);
            addEntry(entry);
        }
        else
        {
            int written = http2Writer.headers(writer.offset(), sizeof, streamId, flags, headers, connection::mapTrailers);
            postWrite(stream, type, written);
            writer.flush();

            connection.closeStream(stream);
        }
        return true;
    }

    @Override
    public boolean headers(long traceId, int streamId, byte flags, ListFW<HttpHeaderFW> headers)
    {
        MutableDirectBuffer copy = null;
        int length = headersLength(headers);        // estimate only
        int sizeof = 9 + headersLength(headers);    // +9 for HTTP2 framing
        Http2FrameType type = HEADERS;
        Http2Stream stream = stream(streamId);

        if (buffered() || !hasNukleusBudget(length))
        {
            copy = new UnsafeBuffer(new byte[8192]);
            connection.factory.blockRW.wrap(copy, 0, copy.capacity());
            connection.mapHeaders(headers, connection.factory.blockRW);
            HpackHeaderBlockFW block = connection.factory.blockRW.build();
            length = block.sizeof();
            sizeof = 9 + length;
        }

        if (buffered() || !hasNukleusBudget(length))
        {
            Entry entry = new HeadersEntry(null, streamId, traceId, length, type, flags, copy, 0, length);
            addEntry(entry);
        }
        else
        {
            int written = http2Writer.headers(writer.offset(), sizeof, streamId, flags, headers, connection::mapHeaders);
            postWrite(stream, type, written);
            writer.flush();
        }

        return true;
    }

    @Override
    public boolean pushPromise(long traceId, int streamId, int promisedStreamId, ListFW<HttpHeaderFW> headers)
    {
        MutableDirectBuffer copy = null;
        int length = headersLength(headers);            // estimate only
        int sizeof = 9 + 4 + length;                    // +9 for HTTP2 framing, +4 for promised stream id
        Http2FrameType type = PUSH_PROMISE;
        Http2Stream stream = stream(streamId);

        if (buffered() || !hasNukleusBudget(length))
        {
            copy = new UnsafeBuffer(new byte[8192]);
            connection.factory.blockRW.wrap(copy, 0, copy.capacity());
            connection.mapPushPromise(headers, connection.factory.blockRW);
            HpackHeaderBlockFW block = connection.factory.blockRW.build();
            length = block.sizeof() + 4;            // +4 for promised stream id
            sizeof = 9 + length;                    // +9 for HTTP2 framing
        }

        if (buffered() || !hasNukleusBudget(length))
        {
            Entry entry = new PushPromiseEntry(null, streamId, traceId, length, type, promisedStreamId, copy, 0, length - 4);
            addEntry(entry);
        }
        else
        {
            int written = http2Writer.pushPromise(writer.offset(), sizeof, streamId, promisedStreamId, headers,
                    connection::mapPushPromise);
            postWrite(stream, type, written);
            writer.flush();
        }

        return true;
    }

    @Override
    public boolean data(long traceId, int streamId, DirectBuffer buffer, int offset, int length)
    {
        assert length > 0;
        assert streamId != 0;

        Http2FrameType type = DATA;
        Http2Stream stream = stream(streamId);
        if (stream == null)
        {
            return true;
        }


        if (!buffered() && !buffered(streamId) && hasNukleusBudget(length) && length <= connection.http2OutWindow &&
                length <= stream.http2OutWindow)
        {
            // Send multiple DATA frames (because of max frame size)
            while (length > 0)
            {
                int chunk = Math.min(length, connection.remoteSettings.maxFrameSize);
                int sizeof = chunk + 9;
                int written = http2Writer.data(writer.offset(), sizeof, streamId, buffer, offset, chunk);
                postWrite(stream, type, written);

                offset += chunk;
                length -= chunk;
            }
            writer.flush();
        }
        else
        {
            // Buffer the data as there is no window
            MutableDirectBuffer replyBuffer = stream.acquireReplyBuffer();
            if (replyBuffer == null)
            {
                connection.doRstByUs(stream, Http2ErrorCode.INTERNAL_ERROR);
                return false;
            }

            CircularDirectBuffer cdb = stream.replyBuffer;

            // Store as two contiguous parts (as it is circular buffer)
            int part1 = cdb.writeContiguous(replyBuffer, buffer, offset, length);
            assert part1 > 0;
            DataEntry entry1 = new DataEntry(stream, streamId, traceId, type, part1);
            addEntry(entry1);

            int part2 = length - part1;
            if (part2 > 0)
            {
                part2 = cdb.writeContiguous(replyBuffer, buffer, offset + part1, part2);
                assert part2 > 0;
                if (part1 + part2 != length)
                {
                    String msg = String.format("Internal Error: not enough space: length=%d part1=%d part2=%d buffered=%d",
                            length, part1, part2, cdb.size());
                    throw new RuntimeException(msg);
                }
                DataEntry entry2 = new DataEntry(stream, streamId, traceId, type, part2);
                addEntry(entry2);
            }
            flush();
        }
        return true;
    }

    @Override
    public boolean dataEos(long traceId, int streamId)
    {
        int length = 0;
        int sizeof = length + 9;    // +9 for HTTP2 framing
        Http2FrameType type = DATA;

        Http2Stream stream = connection.http2Streams.get(streamId);
        if (stream == null)
        {
            return true;
        }
        stream.endStream = true;

        if (!buffered() && !buffered(streamId) && hasNukleusBudget(length) && 0 <= connection.http2OutWindow &&
                0 <= stream.http2OutWindow)
        {
            int written = http2Writer.dataEos(writer.offset(), sizeof, streamId);
            postWrite(stream, type, written);
            writer.flush();

            connection.closeStream(stream);
        }
        else
        {
            DataEosEntry entry = new DataEosEntry(stream, streamId, traceId, length, type);
            addEntry(entry);
        }

        return true;
    }

    private boolean hasNukleusBudget(int length)
    {
        int frameCount = length == 0 ? 1 : (int) Math.ceil((double) length/connection.remoteSettings.maxFrameSize);
        int sizeof = length + frameCount * 9;
        return writer.fits(sizeof);
    }

    private void addEntry(Entry entry)
    {
        if (entry.type == DATA)
        {
            Deque<WriteScheduler.Entry> queue = queue(entry.stream);
            if (queue != null)
            {
                queue.add(entry);
            }
        }
        else
        {
            replyQueue.add(entry);
        }
    }

    // Since it is not encoding, this gives an approximate length of header block
    private int headersLength(ListFW<HttpHeaderFW> headers)
    {
        int[] length = new int[1];
        headers.forEach(x -> length[0] += x.name().sizeof() + x.value().sizeof() + 4);

        // may inject server header, so add it into calculations
        DirectBuffer serverHeader = connection.factory.config.serverHeader();
        length[0] += serverHeader == null ? 0 : serverHeader.capacity() + 4;

        // may inject access-control-allow-origin: *, so add it into calculations
        length[0] += 4;

        return length[0];
    }

    @Override
    public void doEnd()
    {
        end = true;

        assert entryCount >= 0;
        if (entryCount == 0 && !endSent)
        {
            endSent = true;
            writer.doEnd();
        }
    }

    private void flush()
    {
        if (connection.networkReplyBudget < connection.outWindowThreshold)
        {
            // Instead of sending small updates, wait until a bigger window accumulates
            return;
        }

        Entry entry;
        while ((entry = pop()) != null)
        {
            entry.write();

            if (!buffered(entry.stream) && entry.stream != null)
            {
                entry.stream.releaseReplyBuffer();
            }
        }
        writer.flush();

        for(Http2Stream stream : connection.http2Streams.values())
        {
            if (stream.applicationReplyThrottle != null)
            {
                stream.sendHttpWindow(windowTraceId);
            }
        }

        if (entryCount == 0 && end && !endSent)
        {
            endSent = true;
            writer.doEnd();
        }
    }

    @Override
    public void onHttp2Window()
    {
        flush();
    }

    @Override
    public void onHttp2Window(int streamId)
    {
        flush();
    }

    @Override
    public void onWindow(long traceId)
    {
        windowTraceId = traceId;
        flush();
    }

    private Entry pop()
    {
        if (buffered())
        {
            // There are entries on connection queue but cannot make progress, so
            // not make any progress on other streams
            return pop(null);
        }

        // TODO Map#values may not iterate randomly, randomly pick a stream ??
        // Select a frame on a HTTP2 stream that can be written
        for(Http2Stream stream : connection.http2Streams.values())
        {
            Entry entry = pop(stream);
            if (entry != null)
            {
                return entry;
            }
        }

        return null;
    }

    private Entry pop(Http2Stream stream)
    {
        if (buffered(stream))
        {
            Deque replyQueue = queue(stream);
            Entry entry = (Entry) replyQueue.peek();
            if (entry.fits())
            {
                entryCount--;
                entry =  (Entry) replyQueue.poll();
                return entry;
            }
        }

        return null;
    }

    private Deque<WriteScheduler.Entry> queue(Http2Stream stream)
    {
        return (stream == null) ? replyQueue : stream.replyQueue;
    }

    private boolean buffered(Http2Stream stream)
    {
        Deque queue = (stream == null) ? replyQueue : stream.replyQueue;
        return buffered(queue);
    }

    private boolean buffered(Deque queue)
    {
        return !(queue == null || queue.isEmpty());
    }

    CircularDirectBuffer buffer(int streamId)
    {
        assert streamId != 0;

        Http2Stream stream = connection.http2Streams.get(streamId);
        return stream.replyBuffer;
    }

    private boolean buffered()
    {
        return !replyQueue.isEmpty();
    }

    private boolean buffered(int streamId)
    {
        assert streamId != 0;

        Http2Stream stream = connection.http2Streams.get(streamId);
        return !(stream == null || stream.replyQueue.isEmpty());
    }

    Http2Stream stream(int streamId)
    {
        return streamId == 0 ? null : connection.http2Streams.get(streamId);
    }

    private void postWrite(Http2Stream stream, Http2FrameType type, int written)
    {
        assert written >= 9;

        if (canStreamWrite(stream, type))
        {
            if (type == DATA)
            {
                int length = written - 9;
                stream.http2OutWindow -= length;
                connection.http2OutWindow -= length;
                stream.totalOutData += length;
            }
            writer.writtenHttp2Frame(type, written);
        }
    }

    private static boolean canStreamWrite(Http2Stream stream, Http2FrameType type)
    {
        // After RST_STREAM is written, don't write any frame in the stream
        return stream == null || type == RST_STREAM || stream.state != Http2StreamState.CLOSED;
    }

    private abstract class Entry implements WriteScheduler.Entry
    {
        final int streamId;
        final long traceId;
        final int length;
        final int sizeof;
        final Http2FrameType type;
        final Http2Stream stream;

        Entry(Http2Stream stream, int streamId, long traceId, int length, Http2FrameType type)
        {
            this.stream = stream;
            this.streamId = streamId;
            this.traceId = traceId;
            this.length = length;
            this.sizeof = length + 9;
            this.type = type;

            entryCount++;
        }

        boolean fits()
        {
            return hasNukleusBudget(length);
        }

        abstract void write();

    }

    private class DataEosEntry extends Entry
    {
        DataEosEntry(Http2Stream stream, int streamId, long traceId, int length, Http2FrameType type)
        {
            super(stream, streamId, traceId, length, type);
        }

        @Override
        void write()
        {
            int written = http2Writer.dataEos(writer.offset(), sizeof, streamId);
            postWrite(stream, type, written);

            connection.closeStream(stream);
        }
    }

    private class TrailersEntry extends Entry
    {
        private byte flags;
        private DirectBuffer payloadBuffer;
        private int payloadOffset;
        private int payloadLength;

        TrailersEntry(Http2Stream stream, int streamId, long traceId, int length, Http2FrameType type, byte flags,
                     DirectBuffer payloadBuffer, int payloadOffset, int payloadLength)
        {
            super(stream, streamId, traceId, length, type);
            this.flags = flags;
            this.payloadBuffer = payloadBuffer;
            this.payloadOffset = payloadOffset;
            this.payloadLength = payloadLength;
        }

        @Override
        void write()
        {
            int written = http2Writer.headers(writer.offset(), sizeof, streamId, flags,
                    payloadBuffer, payloadOffset, payloadLength);
            postWrite(stream, type, written);

            connection.closeStream(stream);
        }
    }

    private class RstEntry extends Entry
    {
        final Http2ErrorCode errorCode;

        RstEntry(Http2Stream stream, int streamId, long traceId, int length, Http2FrameType type, Http2ErrorCode errorCode)
        {
            super(stream, streamId, traceId, length, type);
            this.errorCode = errorCode;
        }

        @Override
        void write()
        {
            int written = http2Writer.rst(writer.offset(), sizeof, streamId, errorCode);
            postWrite(stream, type, written);
        }
    }

    private class WindowUpdateEntry extends Entry
    {
        private final int update;

        WindowUpdateEntry(Http2Stream stream, int streamId, long traceId, int length, Http2FrameType type,
                          int update)
        {
            super(stream, streamId, traceId, length, type);
            this.update = update;
        }

        @Override
        void write()
        {
            int written = http2Writer.windowUpdate(writer.offset(), sizeof, streamId, update);
            postWrite(stream, type, written);
        }
    }

    private class PingAckEntry extends Entry
    {
        private final DirectBuffer payloadBuffer;
        private final int payloadOffset;
        private final int payloadLength;

        PingAckEntry(Http2Stream stream, int streamId, long traceId, int length, Http2FrameType type,
                     DirectBuffer payloadBuffer, int payloadOffset, int payloadLength)
        {
            super(stream, streamId, traceId, length, type);
            this.payloadBuffer = payloadBuffer;
            this.payloadOffset = payloadOffset;
            this.payloadLength = payloadLength;
        }

        @Override
        void write()
        {
            int written = http2Writer.pingAck(writer.offset(), sizeof, payloadBuffer, payloadOffset, payloadLength);
            postWrite(stream, type, written);
        }
    }

    private class GoawayEntry extends Entry
    {
        final int lastStreamId;
        final Http2ErrorCode errorCode;

        GoawayEntry(Http2Stream stream, int streamId, long traceId, int length, Http2FrameType type, int lastStreamId,
                    Http2ErrorCode errorCode)
        {
            super(stream, streamId, traceId, length, type);
            this.lastStreamId = lastStreamId;
            this.errorCode = errorCode;
        }

        @Override
        void write()
        {
            int written = http2Writer.goaway(writer.offset(), sizeof, lastStreamId, errorCode);
            postWrite(stream, type, written);
        }
    }

    private class SettingsEntry extends Entry
    {
        final int maxConcurrentStreams;
        final int initialWindowSize;

        SettingsEntry(Http2Stream stream, int streamId, long traceId, int length, Http2FrameType type,
                      int maxConcurrentStreams, int initialWindowSize)
        {
            super(stream, streamId, traceId, length, type);
            this.maxConcurrentStreams = maxConcurrentStreams;
            this.initialWindowSize = initialWindowSize;
        }

        @Override
        void write()
        {
            int written = http2Writer.settings(writer.offset(), sizeof, maxConcurrentStreams, initialWindowSize);
            postWrite(stream, type, written);
        }
    }

    private class SettingsAckEntry extends Entry
    {
        SettingsAckEntry(Http2Stream stream, int streamId, long traceId, int length, Http2FrameType type)
        {
            super(stream, streamId, traceId, length, type);
        }

        @Override
        void write()
        {
            int written = http2Writer.settingsAck(writer.offset(), sizeof);
            postWrite(stream, type, written);
        }
    }

    private class HeadersEntry extends Entry
    {
        private byte flags;
        private DirectBuffer payloadBuffer;
        private int payloadOffset;
        private int payloadLength;

        HeadersEntry(Http2Stream stream, int streamId, long traceId, int length, Http2FrameType type, byte flags,
                     DirectBuffer payloadBuffer, int payloadOffset, int payloadLength)
        {
            super(stream, streamId, traceId, length, type);
            this.flags = flags;
            this.payloadBuffer = payloadBuffer;
            this.payloadOffset = payloadOffset;
            this.payloadLength = payloadLength;
        }

        @Override
        void write()
        {
            int written = http2Writer.headers(writer.offset(), sizeof, streamId, flags,
                    payloadBuffer, payloadOffset, payloadLength);
            postWrite(stream, type, written);
        }
    }

    private class PushPromiseEntry extends Entry
    {
        private int promisedStreamId;
        private DirectBuffer blockBuffer;
        private int blockOffset;
        private int blockLength;

        PushPromiseEntry(Http2Stream stream, int streamId, long traceId, int length, Http2FrameType type, int promisedStreamId,
                     DirectBuffer blockBuffer, int blockOffset, int blockLength)
        {
            super(stream, streamId, traceId, length, type);
            this.promisedStreamId = promisedStreamId;
            this.blockBuffer = blockBuffer;
            this.blockOffset = blockOffset;
            this.blockLength = blockLength;
        }

        @Override
        void write()
        {
            int written = http2Writer.pushPromise(writer.offset(), sizeof, streamId, promisedStreamId,
                    blockBuffer, blockOffset, blockLength);
            postWrite(stream, type, written);
        }
    }

    private class DataEntry extends Entry
    {
        DataEntry(
                Http2Stream stream,
                int streamId,
                long traceId,
                Http2FrameType type,
                int length)
        {
            super(stream, streamId, traceId, length, type);

            assert streamId != 0;
        }

        boolean fits()
        {
            // limit by nuklei window, http2 windows, peer's max frame size
            int min = Math.min((int) connection.http2OutWindow, (int) stream.http2OutWindow);
            min = Math.min(min, length);
            min = Math.min(min, connection.remoteSettings.maxFrameSize);
            min = Math.min(min, writer.remaining() - 9);

            if (min > 0)
            {
                int remaining = length - min;
                if (remaining > 0)
                {
                    entryCount--;
                    stream.replyQueue.poll();
                    DataEntry entry1 = new DataEntry(stream, streamId, traceId, type, min);
                    DataEntry entry2 = new DataEntry(stream, streamId, traceId, type, remaining);

                    stream.replyQueue.addFirst(entry2);
                    stream.replyQueue.addFirst(entry1);
                }
            }

            return min > 0;
        }

        @Override
        void write()
        {
            DirectBuffer read = stream.acquireReplyBuffer();
            assert read != null;
            int offset = stream.replyBuffer.readOffset();
            int readLength = stream.replyBuffer.read(length);
            assert readLength == length;

            int written = http2Writer.data(writer.offset(), sizeof, streamId, read, offset, readLength);
            postWrite(stream, type, written);
        }

        public String toString()
        {
            return String.format("length=%d", length);
        }

    }

}
