package org.orcid.pojo.ajaxForm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.orcid.jaxb.model.common.Iso3166Country;
import org.orcid.jaxb.model.v3.release.common.Country;
import org.orcid.jaxb.model.v3.release.common.Source;
import org.orcid.jaxb.model.v3.release.record.Address;

public class AddressForm extends VisibilityForm implements ErrorsInterface, Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> errors = new ArrayList<String>();
    private Iso2Country iso2Country;
    private String countryName;
    private String putCode;    
    private Long displayIndex;
    private Date createdDate;
    private Date lastModified;
    private String source;
    private String sourceName;
    private String assertionOriginOrcid;
    private String assertionOriginClientId;
    private String assertionOriginName;

    public static AddressForm valueOf(Address address) {
        AddressForm form = new AddressForm();

        if (address == null) return form;

        if (address.getCountry() != null && address.getCountry().getValue() != null) {
            form.setIso2Country(Iso2Country.valueOf(address.getCountry().getValue()));
        }

        if (address.getVisibility() != null) {
            form.setVisibility(Visibility.valueOf(address.getVisibility()));
        }

        if (address.getPutCode() != null) {
            form.setPutCode(String.valueOf(address.getPutCode()));
        }

        if (address.getCreatedDate() != null) {
            Date createdDate = new Date();
            createdDate.setYear(String.valueOf(address.getCreatedDate().getValue().getYear()));
            createdDate.setMonth(String.valueOf(address.getCreatedDate().getValue().getMonth()));
            createdDate.setDay(String.valueOf(address.getCreatedDate().getValue().getDay()));
            form.setCreatedDate(createdDate);
        }

        if (address.getLastModifiedDate() != null) {
            Date lastModifiedDate = new Date();
            lastModifiedDate.setYear(String.valueOf(address.getLastModifiedDate().getValue().getYear()));
            lastModifiedDate.setMonth(String.valueOf(address.getLastModifiedDate().getValue().getMonth()));
            lastModifiedDate.setDay(String.valueOf(address.getLastModifiedDate().getValue().getDay()));
            form.setLastModified(lastModifiedDate);
        }

        if (address.getSource() != null) {
            // Set source
            form.setSource(address.getSource().retrieveSourcePath());
            if (address.getSource().getSourceName() != null) {
                form.setSourceName(address.getSource().getSourceName().getContent());
            }
            
            if (address.getSource().getAssertionOriginClientId() != null) {
                form.setAssertionOriginClientId(address.getSource().getAssertionOriginClientId().getPath());
            }
            
            if (address.getSource().getAssertionOriginOrcid() != null) {
                form.setAssertionOriginOrcid(address.getSource().getAssertionOriginOrcid().getPath());
            }
            
            if (address.getSource().getAssertionOriginName() != null) {
                form.setAssertionOriginName(address.getSource().getAssertionOriginName().getContent());
            }
        }
        if (address.getDisplayIndex() != null) {
            form.setDisplayIndex(address.getDisplayIndex());
        } else {
            form.setDisplayIndex(0L);
        }

        return form;
    }

    public Address toAddress() {
        Address address = new Address();

        if (this.iso2Country != null && this.iso2Country.getValue() != null) {
            Country country = new Country();
            country.setValue(Iso3166Country.fromValue(this.iso2Country.getValue().value()));
            address.setCountry(country);
        }
       
        if (this.visibility != null && this.visibility.getVisibility() != null) {
            address.setVisibility(org.orcid.jaxb.model.v3.release.common.Visibility.fromValue(this.getVisibility().getVisibility().value()));
        }

        if (!PojoUtil.isEmpty(this.getPutCode())) {
            address.setPutCode(Long.valueOf(this.getPutCode()));
        }

        if (displayIndex != null) {
            address.setDisplayIndex(displayIndex);
        } else {
            address.setDisplayIndex(0L);
        }

        address.setSource(new Source(source));

        return address;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Iso2Country getIso2Country() {
        return iso2Country;
    }

    public void setIso2Country(Iso2Country iso2Country) {
        this.iso2Country = iso2Country;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getPutCode() {
        return putCode;
    }

    public void setPutCode(String putCode) {
        this.putCode = putCode;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Long getDisplayIndex() {
        return displayIndex;
    }

    public void setDisplayIndex(Long displayIndex) {
        this.displayIndex = displayIndex;
    }

    public String getAssertionOriginOrcid() {
        return assertionOriginOrcid;
    }

    public void setAssertionOriginOrcid(String assertionOriginOrcid) {
        this.assertionOriginOrcid = assertionOriginOrcid;
    }

    public String getAssertionOriginClientId() {
        return assertionOriginClientId;
    }

    public void setAssertionOriginClientId(String assertionOriginClientId) {
        this.assertionOriginClientId = assertionOriginClientId;
    }

    public String getAssertionOriginName() {
        return assertionOriginName;
    }

    public void setAssertionOriginName(String assertionOriginName) {
        this.assertionOriginName = assertionOriginName;
    }
    
}
