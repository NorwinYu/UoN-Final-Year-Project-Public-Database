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

public enum Http2SettingsId
{
    UNKNOWN(-1),
    HEADER_TABLE_SIZE(1),
    ENABLE_PUSH(2),
    MAX_CONCURRENT_STREAMS(3),
    INITIAL_WINDOW_SIZE(4),
    MAX_FRAME_SIZE(5),
    MAX_HEADER_LIST_SIZE(6);

    private final int id;

    Http2SettingsId(int id)
    {
        this.id = id;
    }

    int id()
    {
        return id;
    }

    static Http2SettingsId get(int id)
    {
        switch (id)
        {
            case 1: return HEADER_TABLE_SIZE;
            case 2: return ENABLE_PUSH;
            case 3: return MAX_CONCURRENT_STREAMS;
            case 4: return INITIAL_WINDOW_SIZE;
            case 5: return MAX_FRAME_SIZE;
            case 6: return MAX_HEADER_LIST_SIZE;
            default:return UNKNOWN;
        }
    }
}
