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

import static org.junit.Assert.assertEquals;
import static org.reaktivity.nukleus.http2.internal.Http2Configuration.HTTP2_ACCESS_CONTROL_ALLOW_ORIGIN;
import static org.reaktivity.nukleus.http2.internal.Http2Configuration.HTTP2_SERVER_HEADER;

import org.junit.Test;

public class Http2ConfigurationTest
{
    // needed by test annotations
    public static final String HTTP2_ACCESS_CONTROL_ALLOW_ORIGIN_NAME = "nukleus.http2.server.access.control.allow.origin";
    public static final String HTTP2_SERVER_HEADER_NAME = "nukleus.http2.server.header";

    @Test
    public void shouldVerifyConstants() throws Exception
    {
        assertEquals(HTTP2_ACCESS_CONTROL_ALLOW_ORIGIN.name(), HTTP2_ACCESS_CONTROL_ALLOW_ORIGIN_NAME);
        assertEquals(HTTP2_SERVER_HEADER.name(), HTTP2_SERVER_HEADER_NAME);
    }
}
