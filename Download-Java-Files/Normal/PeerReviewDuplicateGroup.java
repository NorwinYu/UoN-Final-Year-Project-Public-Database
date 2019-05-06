package org.orcid.jaxb.model.v3.rc1.record.summary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.orcid.jaxb.model.v3.rc1.common.LastModifiedDate;
import org.orcid.jaxb.model.v3.rc1.record.ExternalIDs;
import org.orcid.jaxb.model.v3.rc1.record.Group;
import org.orcid.jaxb.model.v3.rc1.record.GroupableActivity;

import io.swagger.annotations.ApiModel;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "lastModifiedDate", "identifiers", "peerReviewSummary" })
@XmlRootElement(name = "peer-review-duplicate-group", namespace = "http://www.orcid.org/ns/activities")
@ApiModel(value = "PeerReviewDuplicateGroupV3_0_rc1")
public class PeerReviewDuplicateGroup implements Group, Serializable {
    private static final long serialVersionUID = 5941298350849708891L;
    @XmlElement(name = "last-modified-date", namespace = "http://www.orcid.org/ns/common")
    protected LastModifiedDate lastModifiedDate;
    @XmlElement(name = "external-ids", namespace = "http://www.orcid.org/ns/common")
    private ExternalIDs identifiers;
    @XmlElement(name = "peer-review-summary", namespace = "http://www.orcid.org/ns/peer-review")
    private List<PeerReviewSummary> peerReviewSummary;

    public ExternalIDs getIdentifiers() {
        if (identifiers == null)
            identifiers = new ExternalIDs();
        return identifiers;
    }

    public List<PeerReviewSummary> getPeerReviewSummary() {
        if (peerReviewSummary == null)
            peerReviewSummary = new ArrayList<PeerReviewSummary>();
        return peerReviewSummary;
    }

    @Override
    public Collection<? extends GroupableActivity> getActivities() {
        return getPeerReviewSummary();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((peerReviewSummary == null) ? 0 : peerReviewSummary.hashCode());
        result = prime * result + ((identifiers == null) ? 0 : identifiers.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PeerReviewDuplicateGroup other = (PeerReviewDuplicateGroup) obj;
        if (peerReviewSummary == null) {
            if (other.peerReviewSummary != null)
                return false;
        } else if (!peerReviewSummary.equals(other.peerReviewSummary))
            return false;
        if (identifiers == null) {
            if (other.identifiers != null)
                return false;
        } else if (!identifiers.equals(other.identifiers))
            return false;
        return true;
    }

    public LastModifiedDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LastModifiedDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
