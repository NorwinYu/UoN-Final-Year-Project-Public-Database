/**
 * =============================================================================
 *
 * ORCID (R) Open Source
 * http://orcid.org
 *
 * Copyright (c) 2012-2014 ORCID, Inc.
 * Licensed under an MIT-Style License (MIT)
 * http://orcid.org/open-source-license
 *
 * This copyright and license information (including a link to the full license)
 * shall be included in its entirety in all copies or substantial portion of
 * the software.
 *
 * =============================================================================
 */
package org.orcid.activitiesindexer.orcid;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;

import org.orcid.activitiesindexer.exception.DeprecatedRecordException;
import org.orcid.activitiesindexer.exception.LockedRecordException;
import org.orcid.jaxb.model.error_v2.OrcidError;
import org.orcid.jaxb.model.record.summary_v2.ActivitiesSummary;
import org.orcid.jaxb.model.record_v2.Affiliation;
import org.orcid.jaxb.model.record_v2.AffiliationType;
import org.orcid.jaxb.model.record_v2.Education;
import org.orcid.jaxb.model.record_v2.Employment;
import org.orcid.jaxb.model.record_v2.Funding;
import org.orcid.jaxb.model.record_v2.PeerReview;
import org.orcid.jaxb.model.record_v2.Record;
import org.orcid.jaxb.model.record_v2.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;

@Component
public class Orcid20APIClient {

    Logger LOG = LoggerFactory.getLogger(Orcid20APIClient.class);
    @Resource
    protected Client jerseyClient;
    protected final URI baseUri;
    protected final String accessToken;
    
    @Autowired
    public Orcid20APIClient(@Value("${org.orcid.message-listener.api20BaseURI}") String baseUri,
            @Value("${org.orcid.message-listener.api.read_public_access_token}") String accessToken) throws URISyntaxException {
        LOG.info("Creating Orcid20APIClient with baseUri = " + baseUri);
        this.baseUri = new URI(baseUri);
        this.accessToken = accessToken;
    }

    /**
     * Fetches the public activities from the ORCID API v2.0
     * 
     * Caches based on message.
     * 
     * @param orcid
     * @return Activities
     */
    public Record fetchPublicRecord(String orcid) throws LockedRecordException, DeprecatedRecordException {
        WebResource webResource = jerseyClient.resource(baseUri).path(orcid + "/record");
        webResource.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, false);
        Builder builder = webResource.accept(MediaType.APPLICATION_XML).header("Authorization", "Bearer " + accessToken);
        ClientResponse response = builder.get(ClientResponse.class);
        if (response.getStatus() != 200) {
            OrcidError orcidError = null;
            switch (response.getStatus()) {
            case 301:
                orcidError = response.getEntity(OrcidError.class);
                throw new DeprecatedRecordException(orcidError);
            case 409:
                orcidError = response.getEntity(OrcidError.class);
                throw new LockedRecordException(orcidError);
            default:
                LOG.error("Unable to fetch public activities for " + orcid + " on API 2.0 HTTP error code: " + response.getStatus());
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        }
        return response.getEntity(Record.class);        
    }
    
    public Affiliation fetchAffiliation(String orcid, Long putCode, AffiliationType type){
        WebResource webResource = jerseyClient.resource(baseUri).path(orcid + "/" + type.value() + "/" + putCode);
        webResource.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, false);
        Builder builder = webResource.accept(MediaType.APPLICATION_XML).header("Authorization", "Bearer " + accessToken);
        ClientResponse response = builder.get(ClientResponse.class);
        if (response.getStatus() != 200) {
            switch (response.getStatus()) {
                default:
                LOG.error("Unable to fetch affiliation from record " + orcid + "/" + type.value() + "/" + putCode+" on API 2.0 HTTP error code: " + response.getStatus());
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        }
        
        Affiliation aff;
        
        if(AffiliationType.EDUCATION.equals(type)) {
        	aff = response.getEntity(Education.class);
        } else {
        	aff = response.getEntity(Employment.class);
        }
        
        return aff;
    }
    
    public Funding fetchFunding(String orcid, Long putCode){
        WebResource webResource = jerseyClient.resource(baseUri).path(orcid + "/funding/"+ putCode);
        webResource.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, false);
        Builder builder = webResource.accept(MediaType.APPLICATION_XML).header("Authorization", "Bearer " + accessToken);
        ClientResponse response = builder.get(ClientResponse.class);
        if (response.getStatus() != 200) {
            switch (response.getStatus()) {
                default:
                LOG.error("Unable to fetch funding record " + orcid + "/" + putCode+" on API 2.0 HTTP error code: " + response.getStatus());
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        }
        return response.getEntity(Funding.class);
    }
    
    public Work fetchWork(String orcid, Long putCode){
        WebResource webResource = jerseyClient.resource(baseUri).path(orcid + "/work/"+ putCode);
        webResource.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, false);
        Builder builder = webResource.accept(MediaType.APPLICATION_XML).header("Authorization", "Bearer " + accessToken);
        ClientResponse response = builder.get(ClientResponse.class);
        if (response.getStatus() != 200) {
            switch (response.getStatus()) {
                default:
                LOG.error("Unable to fetch work from record " + orcid + "/" + putCode+" on API 2.0 HTTP error code: " + response.getStatus());
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        }
        return response.getEntity(Work.class);
    }
    
    public PeerReview fetchPeerReview(String orcid, Long putCode){
        WebResource webResource = jerseyClient.resource(baseUri).path(orcid + "/peer-review/"+ putCode);
        webResource.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, false);
        Builder builder = webResource.accept(MediaType.APPLICATION_XML).header("Authorization", "Bearer " + accessToken);
        ClientResponse response = builder.get(ClientResponse.class);
        if (response.getStatus() != 200) {
            switch (response.getStatus()) {
                default:
                LOG.error("Unable to fetch peer review from record " + orcid + "/" + putCode+" on API 2.0 HTTP error code: " + response.getStatus());
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        }
        return response.getEntity(PeerReview.class);
    }
}
