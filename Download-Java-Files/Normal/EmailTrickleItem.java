package org.orcid.core.email.trickle.producer;

import java.io.Serializable;

import org.orcid.core.manager.v3.EmailMessage;
import org.orcid.persistence.jpa.entities.ProfileEventType;

public class EmailTrickleItem implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private EmailMessage emailMessage;
    
    private String orcid;
    
    private ProfileEventType successType;
    
    private ProfileEventType failureType;
    
    private ProfileEventType skippedType;
    
    private boolean marketingMail;
    
    public EmailMessage getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(EmailMessage emailMessage) {
        this.emailMessage = emailMessage;
    }

    public String getOrcid() {
        return orcid;
    }

    public void setOrcid(String orcid) {
        this.orcid = orcid;
    }

    public ProfileEventType getSuccessType() {
        return successType;
    }

    public void setSuccessType(ProfileEventType successType) {
        this.successType = successType;
    }

    public ProfileEventType getFailureType() {
        return failureType;
    }

    public void setFailureType(ProfileEventType failureType) {
        this.failureType = failureType;
    }

    public ProfileEventType getSkippedType() {
        return skippedType;
    }

    public void setSkippedType(ProfileEventType skippedType) {
        this.skippedType = skippedType;
    }

    public boolean isMarketingMail() {
        return marketingMail;
    }

    public void setMarketingMail(boolean marketingMail) {
        this.marketingMail = marketingMail;
    }
    
}
