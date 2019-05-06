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
package org.reaktivity.nukleus.http2.internal.bench;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import static java.util.concurrent.TimeUnit.SECONDS;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@Fork(3)
@Warmup(iterations = 5, time = 1, timeUnit = SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = SECONDS)
@OutputTimeUnit(SECONDS)
public class Http2ServerBM
{
    /*
    private final Reaktor reaktor;
    private final Http2Controller controller;
    private final Configuration configuration;

    {
        Properties properties = new Properties();
        properties.setProperty(DIRECTORY_PROPERTY_NAME, "target/nukleus-benchmarks");
        properties.setProperty(STREAMS_BUFFER_CAPACITY_PROPERTY_NAME, Long.toString(1024L * 1024L * 16L));

        this.configuration = new Configuration(properties);

        try
        {
            Files.walk(configuration.directory(), FOLLOW_LINKS)
                 .map(Path::toFile)
                 .forEach(File::delete);
        }
        catch (IOException ex)
        {
            LangUtil.rethrowUnchecked(ex);
        }

        this.reaktor = Reaktor.builder()
                              .config(configuration)
                              .nukleus("http2"::equals)
                              .controller(Http2Controller.class::isAssignableFrom)
                              .errorHandler(ex -> ex.printStackTrace(System.err))
                              .build();

        this.controller = reaktor.controller(Http2Controller.class);
    }

    private final BeginFW beginRO = new BeginFW();
    private final DataFW dataRO = new DataFW();
    private final WindowFW.Builder windowRW = new WindowFW.Builder();
    private DataFW requestDataRO;

    private final Map<String, String> headers = new HashMap<>();
    {
        headers.put(":authority", "localhost:8080");
    }

    private final Random random = new Random();

    private HttpStreams sourceInputStreams;
    private HttpStreams sourceOutputEstStreams;

    private MutableDirectBuffer throttleBuffer;

    private long sourceInputRef;

    private long sourceInputId;

    private MessageHandler sourceOutputEstHandler;

    @Setup(Level.Trial)
    public void reinit() throws Exception
    {
        reaktor.start();
        this.sourceInputRef = controller.routeServer("source", 0L, "http2", 0L, headers).get();

        //this.sourceInputStreams = controller.streams("source");
        // Handshake streams (not used yet)
        //this.sourceOutputEstStreams = controller.streams("source", "source");
        this.sourceInputId = random.nextLong();
        this.throttleBuffer = new UnsafeBuffer(allocateDirect(SIZE_OF_LONG + SIZE_OF_INT));

        writeBegin();
        writePreface();
        writeSettings();
        writeSettingsAck();
        writeRequestHeaders();

        String str = "source/streams/http2#http2";
        Path path = configuration.directory().resolve(str);
        while (!Files.exists(path))
        {
            Thread.yield();
        }
        //this.sourceOutputEstStreams = controller.streams("http2", "source");
        this.sourceOutputEstHandler = this::processBegin;
        createRequestData();
    }

    private void writeBegin()
    {
        final AtomicBuffer writeBuffer = new UnsafeBuffer(new byte[256]);

        BeginFW begin = new BeginFW.Builder()
                .wrap(writeBuffer, 0, writeBuffer.capacity())
                .streamId(sourceInputId)
                .source("source")
                .sourceRef(sourceInputRef)
                .correlationId(random.nextLong())
                .build();
        sourceInputStreams.writeStreams(begin.typeId(), begin.buffer(), begin.offset(), begin.sizeof());
    }

    private void writePreface()
    {
        String preface =
                "PRI * HTTP/2.0\r\n" +
                "\r\n" +
                "SM\r\n" +
                "\r\n";
        byte[] prefaceBytes = preface.getBytes(UTF_8);
        AtomicBuffer writeBuffer = new UnsafeBuffer(new byte[256]);
        DataFW prefaceData = new DataFW.Builder()
                .wrap(writeBuffer, 0, writeBuffer.capacity())
                .streamId(sourceInputId)
                .payload(p -> p.set(prefaceBytes))
                .build();
        sourceInputStreams.writeStreams(prefaceData.typeId(),
                prefaceData.buffer(), prefaceData.offset(), prefaceData.sizeof());
    }

    private void writeSettings()
    {
        AtomicBuffer buf = new UnsafeBuffer(new byte[256]);
        Http2SettingsFW settings = new Http2SettingsFW.Builder()
                .wrap(buf, 0, buf.capacity())
                .maxConcurrentStreams(100)
                .build();

        AtomicBuffer writeBuffer = new UnsafeBuffer(new byte[256]);
        DataFW settingsData = new DataFW.Builder()
                .wrap(writeBuffer, 0, writeBuffer.capacity())
                .streamId(sourceInputId)
                .payload(p -> p.set(settings.buffer(), settings.offset(), settings.sizeof()))
                .build();
        sourceInputStreams.writeStreams(settingsData.typeId(),
                settingsData.buffer(), settingsData.offset(), settingsData.sizeof());
    }

    private void writeSettingsAck()
    {
        AtomicBuffer buf = new UnsafeBuffer(new byte[256]);
        Http2SettingsFW settings = new Http2SettingsFW.Builder()
                .wrap(buf, 0, buf.capacity())
                .ack()
                .build();

        AtomicBuffer writeBuffer = new UnsafeBuffer(new byte[256]);
        DataFW settingsAckData = new DataFW.Builder()
                .wrap(writeBuffer, 0, writeBuffer.capacity())
                .streamId(sourceInputId)
                .payload(p -> p.set(settings.buffer(), settings.offset(), settings.sizeof()))
                .build();
        sourceInputStreams.writeStreams(settingsAckData.typeId(),
                settingsAckData.buffer(), settingsAckData.offset(), settingsAckData.sizeof());
    }

    private boolean writeRequestHeaders()
    {
        AtomicBuffer buf = new UnsafeBuffer(new byte[256]);
        Http2HeadersFW headers = new Http2HeadersFW.Builder()
                .wrap(buf, 0, buf.capacity())
                .header(h -> h.indexed(2))      // :method: GET
                .header(h -> h.indexed(6))      // :scheme: http
                .header(h -> h.indexed(4))      // :path: /
                .header(h -> h.literal(l -> l.type(WITHOUT_INDEXING).name(1).value("localhost:8080")))
                .endHeaders()
                .streamId(3)
                .build();

        AtomicBuffer writeBuffer = new UnsafeBuffer(new byte[256]);
        DataFW headersData = new DataFW.Builder()
                .wrap(writeBuffer, 0, writeBuffer.capacity())
                .streamId(sourceInputId)
                .payload(p -> p.set(buf, 0, headers.sizeof()))
                .build();
        return sourceInputStreams.writeStreams(headersData.typeId(),
                headersData.buffer(), headersData.offset(), headersData.sizeof());
    }

    private void createRequestData()
    {
        AtomicBuffer buf = new UnsafeBuffer(new byte[256]);

        String str = "Hello, world!";
        DirectBuffer strBuf = new UnsafeBuffer(str.getBytes(UTF_8));
        Http2DataFW http2Data = new Http2DataFW.Builder()
                .wrap(buf, 0, buf.capacity())
                .streamId(3)
                .payload(strBuf)
                //.endStream()      since sending multiple HTTP2 DATA frames
                .build();

        AtomicBuffer writeBuffer = new UnsafeBuffer(new byte[256]);
        requestDataRO = new DataFW.Builder()
                .wrap(writeBuffer, 0, writeBuffer.capacity())
                .streamId(sourceInputId)
                .payload(p -> p.set(buf, 0, http2Data.sizeof()))
                .build();
    }

    private boolean writeRequestData()
    {
        return sourceInputStreams.writeStreams(requestDataRO.typeId(),
                requestDataRO.buffer(), requestDataRO.offset(), requestDataRO.sizeof());
    }

    @TearDown(Level.Trial)
    public void reset() throws Exception
    {
        Http2Controller controller = reaktor.controller(Http2Controller.class);

        controller.unrouteServer("source", sourceInputRef, "http2", 0L, headers).get();

        this.sourceInputStreams.close();
        this.sourceInputStreams = null;

        this.sourceOutputEstStreams.close();
        this.sourceOutputEstStreams = null;

        reaktor.close();
    }

    @Benchmark
    @Group("throughput")
    @GroupThreads
    public void writer(Control control) throws Exception
    {
        while (!control.stopMeasurement && !writeRequestData())
        {
            Thread.yield();
        }

        while (!control.stopMeasurement &&
                sourceInputStreams.readThrottle((t, b, o, l) -> {}) == 0)
        {
            Thread.yield();
        }
    }

    @Benchmark
    @Group("throughput")
    @GroupThreads
    public void reader(Control control) throws Exception
    {
        while (!control.stopMeasurement &&
               sourceOutputEstStreams.readStreams(this::handleSourceOutputEst) == 0)
        {
            Thread.yield();
        }
    }

    private void handleSourceOutputEst(int msgTypeId, MutableDirectBuffer buffer, int index, int length)
    {
        sourceOutputEstHandler.onMessage(msgTypeId, buffer, index, length);
    }

    private void processBegin(int msgTypeId, MutableDirectBuffer buffer, int index, int length)
    {
        beginRO.wrap(buffer, index, index + length);
        final long streamId = beginRO.streamId();
        doWindow(streamId, 8192);

        this.sourceOutputEstHandler = this::processData;
    }

    private void processData(int msgTypeId, MutableDirectBuffer buffer, int index, int length)
    {
        dataRO.wrap(buffer, index, index + length);
        long streamId = dataRO.streamId();
        OctetsFW payload = dataRO.payload();

        int update = payload.sizeof();
        doWindow(streamId, update);
    }

    private void doWindow(long streamId, int update)
    {
        WindowFW window = windowRW
                .wrap(throttleBuffer, 0, throttleBuffer.capacity())
                .streamId(streamId)
                .update(update)
                .build();

        sourceOutputEstStreams.writeThrottle(window.typeId(), window.buffer(), window.offset(), window.sizeof());
    }*/

    public static void main(String[] args) throws RunnerException
    {
        Options opt = new OptionsBuilder()
                .include(Http2ServerBM.class.getSimpleName())
                .forks(0)
                .build();

        new Runner(opt).run();
    }
}
