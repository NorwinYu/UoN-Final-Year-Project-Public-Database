/*
 * Copyright 2019 National Library of Norway.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package no.nb.nna.veidemann.contentwriter;

import com.google.protobuf.ByteString;
import com.google.protobuf.util.Timestamps;
import com.rethinkdb.RethinkDB;
import io.grpc.StatusException;
import no.nb.nna.veidemann.api.config.v1.Collection.SubCollectionType;
import no.nb.nna.veidemann.api.config.v1.ConfigRef;
import no.nb.nna.veidemann.api.config.v1.Kind;
import no.nb.nna.veidemann.api.contentwriter.v1.Data;
import no.nb.nna.veidemann.api.contentwriter.v1.RecordType;
import no.nb.nna.veidemann.api.contentwriter.v1.WriteRequestMeta;
import no.nb.nna.veidemann.api.contentwriter.v1.WriteRequestMeta.RecordMeta;
import no.nb.nna.veidemann.api.contentwriter.v1.WriteResponseMeta;
import no.nb.nna.veidemann.commons.client.ContentWriterClient;
import no.nb.nna.veidemann.commons.client.ContentWriterClient.ContentWriterSession;
import no.nb.nna.veidemann.commons.db.ConfigAdapter;
import no.nb.nna.veidemann.commons.db.DbConnectionException;
import no.nb.nna.veidemann.commons.db.DbException;
import no.nb.nna.veidemann.commons.db.DbService;
import no.nb.nna.veidemann.commons.settings.CommonSettings;
import no.nb.nna.veidemann.commons.util.Sha1Digest;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.jwat.warc.WarcRecord;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.jwat.warc.WarcConstants.*;

public class ContentWriterServiceTestIT {
    static ContentWriterClient contentWriterClient;

    static ConfigAdapter db;

    static RethinkDB r = RethinkDB.r;

    @BeforeClass
    public static void init() throws DbConnectionException {
        String contentWriterHost = System.getProperty("contentwriter.host");
        int contentWriterPort = Integer.parseInt(System.getProperty("contentwriter.port"));
        String dbHost = System.getProperty("db.host");
        int dbPort = Integer.parseInt(System.getProperty("db.port"));
        System.out.println("Database address: " + dbHost + ":" + dbPort);

        contentWriterClient = new ContentWriterClient(contentWriterHost, contentWriterPort);

        if (!DbService.isConfigured()) {
            CommonSettings dbSettings = new CommonSettings()
                    .withDbHost(dbHost)
                    .withDbPort(dbPort)
                    .withDbName("veidemann")
                    .withDbUser("admin")
                    .withDbPassword("");
            DbService.configure(dbSettings);
        }
        db = DbService.getInstance().getConfigAdapter();
    }

    @AfterClass
    public static void shutdown() {
        contentWriterClient.close();
    }

    @Test
    public void write() throws StatusException, InterruptedException, DbException, ParseException {
        Map<String, WriteResponseMeta> responses = new HashMap<>();
        assertThatCode(() -> {
            responses.put("http", writeHttpRecords());
        }).doesNotThrowAnyException();
        WriteResponseMeta httpResponseMeta = responses.get("http");
        String httpResponseWarcId = httpResponseMeta.getRecordMetaOrThrow(1).getWarcId();

        assertThatCode(() -> {
            responses.put("screenshot", writeScreenshotRecord(httpResponseWarcId));
        }).doesNotThrowAnyException();
        WriteResponseMeta screenshotResponseMeta = responses.get("screenshot");

        writeHttpRecords();
        writeHttpRecords();
        writeDnsRecord();

        WarcFileSet wfs = WarcInspector.getWarcFiles();
        wfs.listFiles().forEach(wf -> {
            System.out.println(wf.getName());
            try (Stream<WarcRecord> stream = wf.getContent()) {
                stream.forEach(r -> {
                    System.out.println("  - " + r.header.warcRecordIdStr + " " + r.header.versionStr + " " + r.header.warcTypeStr);
                });
            }
        });

        wfs.listFiles().forEach(wf -> {
            assertThat(wf.getContent()).allSatisfy(r -> {
                MyProjectAssertions.assertThat(r)
                        .hasVersion(1, 0)
                        .hasValidHeaders();
            });
        });
    }

    private WriteResponseMeta writeHttpRecords() throws ParseException, StatusException, InterruptedException {
        ContentWriterSession session = contentWriterClient.createSession();

        Sha1Digest requestBlockDigest = new Sha1Digest();
        Sha1Digest responseBlockDigest = new Sha1Digest();
        Sha1Digest responsePayloadDigest = new Sha1Digest();

        ByteString requestHeaderData = ByteString.copyFromUtf8("GET /images/logoc.jpg HTTP/1.0\n" +
                "User-Agent: Mozilla/5.0 (compatible; heritrix/1.10.0)\n" +
                "From: stack@example.org\n" +
                "Connection: close\n" +
                "Referer: http://www.archive.org/\n" +
                "Host: www.archive.org\n" +
                "Cookie: PHPSESSID=009d7bb11022f80605aa87e18224d824\n");

        requestBlockDigest.update(requestHeaderData);

        ByteString responseHeaderData = ByteString.copyFromUtf8("HTTP/1.1 200 OK\n" +
                "Date: Tue, 19 Sep 2016 17:18:40 GMT\n" +
                "Server: Apache/2.0.54 (Ubuntu)\n" +
                "Last-Modified: Mon, 16 Jun 2013 22:28:51 GMT\n" +
                "ETag: \"3e45-67e-2ed02ec0\"\n" +
                "Accept-Ranges: bytes\n" +
                "Content-Length: 37\n" +
                "Connection: close\n" +
                "Content-Type: text/html\n");

        ByteString responsePayloadData = ByteString.copyFromUtf8("<html><body><p>test</p></body></html>");

        responseBlockDigest.update(responseHeaderData);
        responseBlockDigest.update('\r', '\n');
        responsePayloadDigest.update(responsePayloadData);
        responseBlockDigest.update(responsePayloadData);

        RecordMeta requestMeta = RecordMeta.newBuilder()
                .setRecordNum(0)
                .setSize(requestHeaderData.size())
                .setBlockDigest(requestBlockDigest.getPrefixedDigestString())
                .setPayloadDigest("sha1:da39a3ee5e6b4b0d3255bfef95601890afd80709")
                .setType(RecordType.REQUEST)
                .setRecordContentType("application/http; msgtype=request")
                .build();
        RecordMeta responseMeta = RecordMeta.newBuilder()
                .setRecordNum(1)
                .setSize(responseHeaderData.size() + responsePayloadData.size() + 2)
                .setBlockDigest(responseBlockDigest.getPrefixedDigestString())
                .setPayloadDigest(responsePayloadDigest.getPrefixedDigestString())
                .setType(RecordType.RESPONSE)
                .setRecordContentType("application/http; msgtype=response")
                .build();
        WriteRequestMeta meta = WriteRequestMeta.newBuilder()
                .setCollectionRef(ConfigRef.newBuilder().setKind(Kind.collection).setId("2fa23773-d7e1-4748-8ab6-9253e470a3f5"))
                .setIpAddress("127.0.0.1")
                .setTargetUri("http://www.example.com/index.html")
                .setFetchTimeStamp(Timestamps.parse("2016-09-19T17:20:24Z"))
                .putRecordMeta(0, requestMeta)
                .putRecordMeta(1, responseMeta)
                .build();
        session.sendMetadata(meta);

        session.sendHeader(Data.newBuilder()
                .setRecordNum(0)
                .setData(requestHeaderData)
                .build());

        session.sendHeader(Data.newBuilder()
                .setRecordNum(1)
                .setData(responseHeaderData)
                .build());

        session.sendPayload(Data.newBuilder()
                .setRecordNum(1)
                .setData(responsePayloadData)
                .build());

        WriteResponseMeta res = null;
        try {
            res = session.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(session.isOpen()).isFalse();
        return res;
    }

    private WriteResponseMeta writeScreenshotRecord(String warcId) throws ParseException, StatusException, InterruptedException {
        ContentWriterSession session = contentWriterClient.createSession();
        assertThat(session.isOpen()).isTrue();

        Sha1Digest blockDigest = new Sha1Digest();
        ByteString payloadData = ByteString.copyFromUtf8("binary png");
        blockDigest.update(payloadData);

        RecordMeta screenshotMeta = RecordMeta.newBuilder()
                .setRecordNum(0)
                .setSize(payloadData.size())
                .setBlockDigest(blockDigest.getPrefixedDigestString())
                .setType(RecordType.RESOURCE)
                .setRecordContentType("image/png")
                .setSubCollection(SubCollectionType.SCREENSHOT)
                .addWarcConcurrentTo(warcId)
                .build();
        WriteRequestMeta meta = WriteRequestMeta.newBuilder()
                .setCollectionRef(ConfigRef.newBuilder().setKind(Kind.collection).setId("2fa23773-d7e1-4748-8ab6-9253e470a3f5"))
                .setIpAddress("127.0.0.1")
                .setTargetUri("http://www.example.com/index.html")
                .setFetchTimeStamp(Timestamps.parse("2016-09-19T17:20:24Z"))
                .putRecordMeta(0, screenshotMeta)
                .build();
        session.sendMetadata(meta);

        Data screenshot = Data.newBuilder()
                .setRecordNum(0)
                .setData(payloadData)
                .build();
        session.sendPayload(screenshot);

        WriteResponseMeta res = session.finish();
        assertThat(session.isOpen()).isFalse();
        return res;
    }

    private WriteResponseMeta writeDnsRecord() throws ParseException, StatusException, InterruptedException {
        ContentWriterSession session = contentWriterClient.createSession();
        assertThat(session.isOpen()).isTrue();

        Sha1Digest blockDigest = new Sha1Digest();
        ByteString payloadData = ByteString.copyFromUtf8("dns record");
        blockDigest.update(payloadData);

        RecordMeta dnsMeta = RecordMeta.newBuilder()
                .setRecordNum(0)
                .setType(RecordType.RESOURCE)
                .setRecordContentType("text/dns")
                .setSize(payloadData.size())
                .setBlockDigest(blockDigest.getPrefixedDigestString())
                .setSubCollection(SubCollectionType.DNS)
                .build();
        WriteRequestMeta meta = WriteRequestMeta.newBuilder()
                .setTargetUri("dns:www.example.com")
                .setFetchTimeStamp(Timestamps.parse("2016-09-19T17:20:24Z"))
                .setIpAddress("127.0.0.1")
                .setCollectionRef(ConfigRef.newBuilder().setKind(Kind.collection).setId("2fa23773-d7e1-4748-8ab6-9253e470a3f5"))
                .putRecordMeta(0, dnsMeta)
                .build();
        session.sendMetadata(meta);

        Data dns = Data.newBuilder()
                .setRecordNum(0)
                .setData(payloadData)
                .build();
        session.sendPayload(dns);

        WriteResponseMeta res = session.finish();
        assertThat(session.isOpen()).isFalse();
        return res;
    }

    public static class MyProjectAssertions extends Assertions {
        public static WarcRecordAssert assertThat(WarcRecord actual) {
            return new WarcRecordAssert(actual);
        }
    }

    public static class WarcRecordAssert extends AbstractAssert<WarcRecordAssert, WarcRecord> {
        public WarcRecordAssert(WarcRecord actual) {
            super(actual, WarcRecordAssert.class);
        }

        public WarcRecordAssert hasVersion(int major, int minor) {
            isNotNull();
            if (actual.header.major != major || actual.header.minor != minor) {
                failWithMessage("Expected WARC version to be <%d.%d> but was <%d.%d>", major, minor, actual.header.major, actual.header.minor);
            }
            return this;
        }

        public WarcRecordAssert hasValidHeaders() {
            isNotNull();
            assertThat(actual.header.warcRecordIdStr).as("%s should not be null", FN_WARC_RECORD_ID).isNotEmpty();
            assertThat(actual.header.warcTypeStr).as("%s should not be null", FN_WARC_TYPE).isNotEmpty();
            assertThat(actual.header.warcDateStr).as("%s should not be null", FN_WARC_DATE).isNotEmpty();
            assertThat(actual.header.contentLengthStr).as("%s should not be null", FN_CONTENT_LENGTH).isNotEmpty();
            assertThat(actual.header.contentTypeStr).as("%s should not be null", FN_CONTENT_TYPE).isNotEmpty();

            if (!actual.diagnostics.getErrors().isEmpty()) {
                System.out.println("ERRORS: " + actual.diagnostics.getErrors()
                        .stream()
                        .map(d -> "\n   " + d.type.toString() + ":" + d.entity + ":" + Arrays.toString(d.getMessageArgs()))
                        .collect(Collectors.joining()));
                actual.getHeaderList().forEach(h -> System.out.print(" W: " + new String(h.raw)));
            }
            if (!actual.diagnostics.getWarnings().isEmpty()) {
                System.out.println("WARNINGS: " + actual.diagnostics.getWarnings()
                        .stream()
                        .map(d -> "\n   " + d.type.toString() + ":" + d.entity + ":" + Arrays.toString(d.getMessageArgs()))
                        .collect(Collectors.joining()));
                actual.getHeaderList().forEach(h -> System.out.print(" W: " + new String(h.raw)));
            }
            assertThat(actual.isCompliant()).as("Record of type '%s' is not compliant", actual.header.warcTypeStr).isTrue();

            switch (actual.header.warcTypeStr) {
                case RT_CONTINUATION:
                    break;
                case RT_CONVERSION:
                    break;
                case RT_METADATA:
                    break;
                case RT_REQUEST:
                    assertThat(actual.header.warcTargetUriStr)
                            .as("%s for record type '%s' should not be null", FN_WARC_TARGET_URI, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.header.warcConcurrentToList)
                            .as("%s for record type '%s' should not be empty", FN_WARC_CONCURRENT_TO, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.header.warcBlockDigestStr)
                            .as("%s for record type '%s' should not be empty", FN_WARC_BLOCK_DIGEST, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.header.warcPayloadDigestStr)
                            .as("%s for record type '%s' should not be empty", FN_WARC_PAYLOAD_DIGEST, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.header.warcIpAddress)
                            .as("%s for record type '%s' should not be empty", FN_WARC_IP_ADDRESS, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.header.warcWarcinfoIdStr)
                            .as("%s for record type '%s' should not be empty", FN_WARC_WARCINFO_ID, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.isValidBlockDigest)
                            .as("%s for record type '%s' doesn't validate", FN_WARC_BLOCK_DIGEST, actual.header.warcTypeStr)
                            .isTrue();
                    assertThat(actual.isValidPayloadDigest)
                            .as("%s for record type '%s' doesn't validate", FN_WARC_PAYLOAD_DIGEST, actual.header.warcTypeStr)
                            .isTrue();
                    break;
                case RT_RESOURCE:
                    assertThat(actual.header.warcTargetUriStr)
                            .as("%s for record type '%s' should not be null", FN_WARC_TARGET_URI, actual.header.warcTypeStr)
                            .isNotEmpty();
                    if ("text/dns".equals(actual.header.contentTypeStr)) {
                        assertThat(actual.header.warcConcurrentToList)
                                .as("%s for record type '%s' should be empty", FN_WARC_CONCURRENT_TO, actual.header.warcTypeStr)
                                .isEmpty();
                    } else {
                        assertThat(actual.header.warcConcurrentToList)
                                .as("%s for record type '%s' should not be empty", FN_WARC_CONCURRENT_TO, actual.header.warcTypeStr)
                                .isNotEmpty();
                    }
                    assertThat(actual.header.warcBlockDigestStr)
                            .as("%s for record type '%s' should not be empty", FN_WARC_BLOCK_DIGEST, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.header.warcIpAddress)
                            .as("%s for record type '%s' should not be empty", FN_WARC_IP_ADDRESS, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.header.warcWarcinfoIdStr)
                            .as("%s for record type '%s' should not be empty", FN_WARC_WARCINFO_ID, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.isValidBlockDigest)
                            .as("%s for record type '%s' doesn't validate", FN_WARC_BLOCK_DIGEST, actual.header.warcTypeStr)
                            .isTrue();
                    assertThat(actual.header.warcPayloadDigestStr)
                            .as("%s for record type '%s' should be empty", FN_WARC_PAYLOAD_DIGEST, actual.header.warcTypeStr)
                            .isNullOrEmpty();
                    break;
                case RT_RESPONSE:
                    assertThat(actual.header.warcTargetUriStr)
                            .as("%s for record type '%s' should not be null", FN_WARC_TARGET_URI, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.header.warcConcurrentToList)
                            .as("%s for record type '%s' should not be empty", FN_WARC_CONCURRENT_TO, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.header.warcBlockDigestStr)
                            .as("%s for record type '%s' should not be empty", FN_WARC_BLOCK_DIGEST, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.header.warcPayloadDigestStr)
                            .as("%s for record type '%s' should not be empty", FN_WARC_PAYLOAD_DIGEST, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.header.warcIpAddress)
                            .as("%s for record type '%s' should not be empty", FN_WARC_IP_ADDRESS, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.header.warcWarcinfoIdStr)
                            .as("%s for record type '%s' should not be empty", FN_WARC_WARCINFO_ID, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.isValidBlockDigest)
                            .as("%s for record type '%s' doesn't validate", FN_WARC_BLOCK_DIGEST, actual.header.warcTypeStr)
                            .isTrue();
                    assertThat(actual.isValidPayloadDigest)
                            .as("%s for record type '%s' doesn't validate", FN_WARC_PAYLOAD_DIGEST, actual.header.warcTypeStr)
                            .isTrue();
                    break;
                case RT_REVISIT:
                    assertThat(actual.header.warcTargetUriStr)
                            .as("%s for record type '%s' should not be null", FN_WARC_TARGET_URI, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.header.warcRefersToStr)
                            .as("%s for record type '%s' should not be empty", FN_WARC_REFERS_TO, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.header.warcConcurrentToList)
                            .as("%s for record type '%s' should not be empty", FN_WARC_CONCURRENT_TO, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.header.warcBlockDigestStr)
                            .as("%s for record type '%s' should not be empty", FN_WARC_BLOCK_DIGEST, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.header.warcPayloadDigestStr)
                            .as("%s for record type '%s' should not be empty", FN_WARC_PAYLOAD_DIGEST, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.header.warcIpAddress)
                            .as("%s for record type '%s' should not be empty", FN_WARC_IP_ADDRESS, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.header.warcWarcinfoIdStr)
                            .as("%s for record type '%s' should not be empty", FN_WARC_WARCINFO_ID, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.isValidBlockDigest)
                            .as("%s for record type '%s' doesn't validate", FN_WARC_BLOCK_DIGEST, actual.header.warcTypeStr)
                            .isTrue();
                    break;
                case RT_WARCINFO:
                    assertThat(actual.header.warcPayloadDigestStr)
                            .as("%s for record type '%s' should be empty", FN_WARC_PAYLOAD_DIGEST, actual.header.warcTypeStr)
                            .isNullOrEmpty();
                    assertThat(actual.header.warcFilename)
                            .as("%s for record type '%s' should not be null", FN_WARC_FILENAME, actual.header.warcTypeStr)
                            .isNotEmpty();
                    assertThat(actual.isValidBlockDigest)
                            .as("%s for record type '%s' doesn't validate", FN_WARC_BLOCK_DIGEST, actual.header.warcTypeStr)
                            .isTrue();
                    break;
                default:
                    failWithMessage("Illegal WARC-Type <%s>", actual.header.warcTypeStr);
            }
            return this;
        }
    }
}