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

import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.StatusException;
import no.nb.nna.veidemann.api.config.v1.Collection.SubCollectionType;
import no.nb.nna.veidemann.api.config.v1.ConfigObject;
import no.nb.nna.veidemann.api.contentwriter.v1.CrawledContent;
import no.nb.nna.veidemann.api.contentwriter.v1.RecordType;
import no.nb.nna.veidemann.api.contentwriter.v1.WriteRequestMeta;
import no.nb.nna.veidemann.commons.db.ConfigAdapter;
import no.nb.nna.veidemann.commons.db.DbAdapter;
import no.nb.nna.veidemann.commons.db.DbException;
import no.nb.nna.veidemann.commons.db.DbService;
import no.nb.nna.veidemann.contentwriter.warc.WarcCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class WriteSessionContext {
    private static final Logger LOG = LoggerFactory.getLogger(WriteSessionContext.class);
    private static final ConfigAdapter config = DbService.getInstance().getConfigAdapter();
    private static final DbAdapter dbAdapter = DbService.getInstance().getDbAdapter();

    //    private final Map<Integer, ContentBuffer> contentBuffers = new HashMap<>();
    private final Map<Integer, RecordData> recordDataMap = new HashMap<>();

    private WriteRequestMeta.Builder writeRequestMeta;
    private ConfigObject collectionConfig;
    private boolean canceled = false;

    public RecordData getRecordData(Integer recordNum) {
        return recordDataMap.computeIfAbsent(recordNum, n -> new RecordData(n));
    }

    public void initMDC() {
        if (writeRequestMeta != null) {
            MDC.put("eid", writeRequestMeta.getExecutionId());
            MDC.put("uri", writeRequestMeta.getTargetUri());
        }
    }

    public void setWriteRequestMeta(WriteRequestMeta writeRequestMeta) throws StatusException {
        this.writeRequestMeta = writeRequestMeta.toBuilder();
        initMDC();

        try {
            if (!writeRequestMeta.hasCollectionRef()) {
                String msg = "No collection id in request";
                LOG.error(msg);
                Status status = Status.INVALID_ARGUMENT.withDescription(msg);
                throw status.asException();
            } else {
                collectionConfig = config.getConfigObject(writeRequestMeta.getCollectionRef());
                if (collectionConfig == null || !collectionConfig.hasMeta() || !collectionConfig.hasCollection()) {
                    String msg = "Collection with id '" + writeRequestMeta.getCollectionRef() + "' is missing or insufficient: " + collectionConfig;
                    LOG.error(msg);
                    Status status = Status.UNKNOWN.withDescription(msg);
                    throw status.asException();
                }
            }
        } catch (Exception e) {
            String msg = "Error getting collection config " + writeRequestMeta.getCollectionRef();
            LOG.error(msg, e);
            Status status = Status.UNKNOWN.withDescription(msg);
            throw status.asException();
        }
    }

    public boolean hasWriteRequestMeta() {
        return writeRequestMeta != null;
    }

    public ConfigObject getCollectionConfig() {
        return collectionConfig;
    }

    public void validateSession() throws StatusException {
        for (Entry<Integer, RecordData> recordEntry : recordDataMap.entrySet()) {
            ContentBuffer contentBuffer = recordEntry.getValue().getContentBuffer();
            WriteRequestMeta.RecordMeta recordMeta = writeRequestMeta.getRecordMetaOrDefault(recordEntry.getKey(), null);
            if (recordMeta == null) {
                throw Status.INVALID_ARGUMENT.withDescription("Missing metadata for record num: " + recordEntry.getKey()).asException();
            }

            if (contentBuffer.getTotalSize() == 0L) {
                LOG.error("Nothing to store");
                throw Status.INVALID_ARGUMENT.withDescription("Nothing to store").asException();
            }

            if (contentBuffer.getTotalSize() != recordMeta.getSize()) {
                LOG.error("Size mismatch. Expected {}, but was {}",
                        recordMeta.getSize(), contentBuffer.getTotalSize());
                throw Status.INVALID_ARGUMENT.withDescription("Size mismatch").asException();
            }

            if (!contentBuffer.getBlockDigest().equals(recordMeta.getBlockDigest())) {
                LOG.error("Block digest mismatch. Expected {}, but was {}",
                        recordMeta.getBlockDigest(), contentBuffer.getBlockDigest());
                throw Status.INVALID_ARGUMENT.withDescription("Block digest mismatch").asException();
            }

            if (writeRequestMeta.getIpAddress().isEmpty()) {
                LOG.error("Missing IP-address");
                throw Status.INVALID_ARGUMENT.withDescription("Missing IP-address").asException();
            }
        }
    }

    public Set<Integer> getRecordNums() {
        return writeRequestMeta.getRecordMetaMap().keySet();
    }

    public void detectRevisit(final Integer recordNum, final WarcCollection collection) {
        RecordData rd = getRecordData(recordNum);
        if (rd.getRecordType() == RecordType.RESPONSE || rd.getRecordType() == RecordType.RESOURCE) {
            Optional<CrawledContent> isDuplicate = null;
            try {
                isDuplicate = dbAdapter
                        .hasCrawledContent(CrawledContent.newBuilder()
                                .setDigest(rd.getContentBuffer().getPayloadDigest() + ":" + collection.getCollectionName(rd.getSubCollectionType()))
                                .setWarcId(rd.getWarcId())
                                .setTargetUri(writeRequestMeta.getTargetUri())
                                .setDate(writeRequestMeta.getFetchTimeStamp())
                                .build());
            } catch (DbException e) {
                LOG.error("Failed checking for revisit, treating as new object", e);
            }

            if (isDuplicate.isPresent()) {
                CrawledContent cc = isDuplicate.get();
                LOG.debug("Detected {} as a revisit of {}",
                        MDC.get("uri"), cc.getWarcId());

                WriteRequestMeta.RecordMeta newRecordMeta = rd.getRecordMeta().toBuilder()
                        .setType(RecordType.REVISIT)
                        .setBlockDigest(rd.getContentBuffer().getHeaderDigest())
                        .setPayloadDigest(rd.getContentBuffer().getPayloadDigest())
                        .setSize(rd.getContentBuffer().getHeaderSize())
                        .build();
                writeRequestMeta.putRecordMeta(recordNum, newRecordMeta);

                rd.getContentBuffer().removePayload();

                rd.revisitRef = cc;
            }
        }

        if (rd.revisitRef == null) {
            WriteRequestMeta.RecordMeta newRecordMeta = rd.getRecordMeta().toBuilder()
                    .setBlockDigest(rd.getContentBuffer().getBlockDigest())
                    .setPayloadDigest(rd.getContentBuffer().getPayloadDigest())
                    .build();
            writeRequestMeta.putRecordMeta(recordNum, newRecordMeta);
        }
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void cancelSession(String cancelReason) {
        canceled = true;
        LOG.debug("Request cancelled before WARC record written. Reason {}", cancelReason);
        for (RecordData cb : recordDataMap.values()) {
            cb.close();
        }
    }

    public class RecordData implements AutoCloseable {
        private final Integer recordNum;
        private final ContentBuffer contentBuffer = new ContentBuffer();
        private CrawledContent revisitRef;

        public RecordData(Integer recordNum) {
            this.recordNum = recordNum;
        }

        public ContentBuffer getContentBuffer() {
            return contentBuffer;
        }

        public WriteRequestMeta.RecordMeta getRecordMeta() {
            return writeRequestMeta.getRecordMetaOrThrow(recordNum);
        }

        public CrawledContent getRevisitRef() {
            return revisitRef;
        }

        public String getWarcId() {
            return contentBuffer.getWarcId();
        }

        public RecordType getRecordType() {
            return getRecordMeta().getType();
        }

        public SubCollectionType getSubCollectionType() {
            return getRecordMeta().getSubCollection();
        }

        public String getTargetUri() {
            return writeRequestMeta.getTargetUri();
        }

        public Timestamp getFetchTimeStamp() {
            return writeRequestMeta.getFetchTimeStamp();
        }

        public String getIpAddress() {
            return writeRequestMeta.getIpAddress();
        }

        public List<String> getWarcConcurrentToIds() {
            List<String> ids = recordDataMap.values().stream()
                    .map(cb -> cb.getContentBuffer().getWarcId())
                    .collect(Collectors.toList());
            ids.addAll(getRecordData(recordNum).getRecordMeta().getWarcConcurrentToList());
            return ids;
        }

        public void close() {
            contentBuffer.close();
        }
    }
}
