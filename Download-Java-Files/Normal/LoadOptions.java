package org.orcid.core.manager;

public class LoadOptions {

    public static final LoadOptions ALL = new LoadOptions(true, true, true);
    public static final LoadOptions BIO_ONLY = new LoadOptions(true, false, false);
    public static final LoadOptions BIO_AND_INTERNAL_ONLY = new LoadOptions(true, false, true);
    public static final LoadOptions INTERNAL_ONLY = new LoadOptions(false, false, true);
    public static final LoadOptions ALL_WITH_NEW_AFFILIATION_TYPES = new LoadOptions(true, true, true, true);
    
    private boolean loadBio;
    private boolean loadActivities;
    private boolean loadInternal;
    private boolean loadNewAffiliationTypes;

    public LoadOptions(boolean loadBio, boolean loadActivities, boolean loadInternal) {
        super();
        this.loadBio = loadBio;
        this.loadActivities = loadActivities;
        this.loadInternal = loadInternal;
        this.loadNewAffiliationTypes = false;
    }
    
    private LoadOptions(boolean loadBio, boolean loadActivities, boolean loadInternal, boolean loadNewAffiliationTypes) {
        super();
        this.loadBio = loadBio;
        this.loadActivities = loadActivities;
        this.loadInternal = loadInternal;
        this.loadNewAffiliationTypes = loadNewAffiliationTypes;
    }

    public boolean isLoadBio() {
        return loadBio;
    }

    public void setLoadBio(boolean loadBio) {
        this.loadBio = loadBio;
    }

    public boolean isLoadActivities() {
        return loadActivities;
    }

    public void setLoadActivities(boolean loadActivities) {
        this.loadActivities = loadActivities;
    }

    public boolean isLoadInternal() {
        return loadInternal;
    }

    public void setLoadInternal(boolean loadInternal) {
        this.loadInternal = loadInternal;
    }
    
    public boolean isLoadNewAffiliationTypes() {
        return loadNewAffiliationTypes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (loadActivities ? 1231 : 1237);
        result = prime * result + (loadBio ? 1231 : 1237);
        result = prime * result + (loadInternal ? 1231 : 1237);
        result = prime * result + (loadNewAffiliationTypes ? 1231 : 1237);
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
        LoadOptions other = (LoadOptions) obj;
        if (loadActivities != other.loadActivities)
            return false;
        if (loadBio != other.loadBio)
            return false;
        if (loadInternal != other.loadInternal)
            return false;
        if (loadNewAffiliationTypes != other.loadNewAffiliationTypes)
            return false;        
        return true;
    }

}
