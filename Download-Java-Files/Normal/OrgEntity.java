package org.orcid.persistence.jpa.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author Will Simpson
 * 
 */

@Entity
@Table(name = "org")
public class OrgEntity extends BaseEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String city;
    private String region;
    private String country;
    private String url;
    private SourceEntity source;
    private OrgDisambiguatedEntity orgDisambiguated;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "org_seq")
    @SequenceGenerator(name = "org_seq", sequenceName = "org_seq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String resolveName() {
        if (orgDisambiguated == null) {
            return name;
        }
        return orgDisambiguated.getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public String resolveCity() {
        if (orgDisambiguated == null) {
            return city;
        }
        return orgDisambiguated.getCity();
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public String resolveRegion() {
        if (orgDisambiguated == null) {
            return region;
        }
        return orgDisambiguated.getRegion();
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public String resolveCountry() {
        if (orgDisambiguated == null) {
            return country;
        }
        return orgDisambiguated.getCountry();
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SourceEntity getSource() {
        return source;
    }

    public void setSource(SourceEntity source) {
        this.source = source;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "org_disambiguated_id")
    public OrgDisambiguatedEntity getOrgDisambiguated() {
        return orgDisambiguated;
    }

    public void setOrgDisambiguated(OrgDisambiguatedEntity orgDisambiguated) {
        this.orgDisambiguated = orgDisambiguated;
    }

}
