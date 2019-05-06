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

import static org.reaktivity.nukleus.http2.internal.types.stream.Http2Flags.ACK;
import static org.reaktivity.nukleus.http2.internal.types.stream.Http2FrameType.SETTINGS;
import static org.reaktivity.nukleus.http2.internal.types.stream.Http2SettingsId.ENABLE_PUSH;
import static org.reaktivity.nukleus.http2.internal.types.stream.Http2SettingsId.HEADER_TABLE_SIZE;
import static org.reaktivity.nukleus.http2.internal.types.stream.Http2SettingsId.INITIAL_WINDOW_SIZE;
import static org.reaktivity.nukleus.http2.internal.types.stream.Http2SettingsId.MAX_CONCURRENT_STREAMS;
import static org.reaktivity.nukleus.http2.internal.types.stream.Http2SettingsId.MAX_FRAME_SIZE;
import static org.reaktivity.nukleus.http2.internal.types.stream.Http2SettingsId.MAX_HEADER_LIST_SIZE;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;

/*
    Flyweight for HTTP2 SETTINGS frame

    +-----------------------------------------------+
    |                 Length (24)                   |
    +---------------+---------------+---------------+
    |   Type (8)    |   Flags (8)   |
    +-+-------------+---------------+-------------------------------+
    |R|                 Stream Identifier (31)                      |
    +=+=============================+===============================+
    |       Identifier (16)         |
    +-------------------------------+-------------------------------+
    |                        Value (32)                             |
    +---------------------------------------------------------------+
    |       ...                     |
    +-------------------------------+-------------------------------+
    |                        ...                                    |
    +---------------------------------------------------------------+

 */
public class Http2SettingsFW extends Http2FrameFW
{
    private static final int FLAGS_OFFSET = 4;
    private static final int PAYLOAD_OFFSET = 9;

    private final UnboundedListFW<Http2SettingFW> listFW = new UnboundedListFW<>(new Http2SettingFW());

    @Override
    public Http2FrameType type()
    {
        return SETTINGS;
    }

    public boolean ack()
    {
        return Http2Flags.ack(flags());
    }

    public void forEach(BiConsumer<Http2SettingsId, Long> consumer)
    {
        listFW.forEach(s -> consumer.accept(Http2SettingsId.get(s.id()), s.value()));
    }

    public long headerTableSize()
    {
        return settings(HEADER_TABLE_SIZE.id());
    }

    public long enablePush()
    {
        return settings(ENABLE_PUSH.id());
    }

    public long maxConcurrentStreams()
    {
        return settings(MAX_CONCURRENT_STREAMS.id());
    }

    public long initialWindowSize()
    {
        return settings(INITIAL_WINDOW_SIZE.id());
    }

    public long maxFrameSize()
    {
        return settings(MAX_FRAME_SIZE.id());
    }

    public long maxHeaderListSize()
    {
        return settings(MAX_HEADER_LIST_SIZE.id());
    }

    public long settings(int key)
    {
        long[] value = new long[] { -1L };

        // TODO https://github.com/reaktivity/nukleus-maven-plugin/issues/16
        listFW.forEach(x ->
        {
            if (x.id() == key)
            {
                value[0] = x.value();
            }
        });
        return value[0];
    }

    public Http2SettingsFW tryWrap(
        DirectBuffer buffer,
        int offset,
        int maxLimit)
    {
        boolean wrappable = super.wrap(buffer, offset, maxLimit) != null;

        wrappable &= super.streamId() == 0;
        wrappable &= super.type() == SETTINGS;
        wrappable &= super.payloadLength() % 6 == 0;
        wrappable &= limit() <= maxLimit;

        if (wrappable)
        {
            listFW.wrap(buffer, offset + PAYLOAD_OFFSET, limit());
        }

        return wrappable ? this : null;
    }

    @Override
    public Http2SettingsFW wrap(
        DirectBuffer buffer,
        int offset,
        int maxLimit)
    {
        super.wrap(buffer, offset, maxLimit);

        int streamId = super.streamId();
        if (streamId != 0)
        {
            throw new IllegalArgumentException(String.format("Invalid SETTINGS frame stream-id=%d", streamId));
        }

        Http2FrameType type = super.type();
        if (type != SETTINGS)
        {
            throw new IllegalArgumentException(String.format("Invalid SETTINGS frame type=%s", type));
        }

        int payloadLength = super.payloadLength();
        if (payloadLength % 6 != 0)
        {
            throw new IllegalArgumentException(String.format("Invalid SETTINGS frame length=%d", payloadLength));
        }

        listFW.wrap(buffer, offset + PAYLOAD_OFFSET, limit());
        checkLimit(limit(), maxLimit);
        return this;
    }

    @Override
    public String toString()
    {
        return String.format("%s frame <length=%s, type=%s, flags=%s, id=%s>",
                type(), payloadLength(), type(), flags(), streamId());
    }

    public static final class Builder extends Http2FrameFW.Builder<Builder, Http2SettingsFW>
    {
        private final UnboundedListFW.Builder<Http2SettingFW.Builder, Http2SettingFW> settingsRW =
                new UnboundedListFW.Builder<>(new Http2SettingFW.Builder(), new Http2SettingFW());

        public Builder()
        {
            super(new Http2SettingsFW());
        }

        @Override
        public Builder wrap(MutableDirectBuffer buffer, int offset, int maxLimit)
        {
            super.wrap(buffer, offset, maxLimit);
            settingsRW.wrap(buffer, offset + PAYLOAD_OFFSET, maxLimit);
            return this;
        }

        public Builder ack()
        {
            buffer().putByte(offset() + FLAGS_OFFSET, ACK);
            return this;
        }

        public Builder headerTableSize(long size)
        {
            addSetting(x -> x.setting(HEADER_TABLE_SIZE.id(), size));
            return this;
        }

        public Builder enablePush()
        {
            addSetting(x -> x.setting(ENABLE_PUSH.id(), 1L));
            return this;
        }

        public Builder maxConcurrentStreams(long streams)
        {
            addSetting(x -> x.setting(MAX_CONCURRENT_STREAMS.id(), streams));
            return this;
        }

        public Builder initialWindowSize(long size)
        {
            addSetting(x -> x.setting(INITIAL_WINDOW_SIZE.id(), size));
            return this;
        }

        public Builder maxFrameSize(long size)
        {
            addSetting(x -> x.setting(MAX_FRAME_SIZE.id(), size));
            return this;
        }

        public Builder maxHeaderListSize(long size)
        {
            addSetting(x -> x.setting(MAX_HEADER_LIST_SIZE.id(), size));
            return this;
        }

        private Builder addSetting(Consumer<Http2SettingFW.Builder> mutator)
        {
            settingsRW.item(mutator);
            int length = settingsRW.limit() - offset() - PAYLOAD_OFFSET;
            payloadLength(length);
            return this;
        }

    }
}

