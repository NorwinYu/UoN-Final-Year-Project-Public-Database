package org.orcid.core.adapter.jsonidentifier;

public class JSONWorkPutCodes {
    
    private Long[] workPutCodes;
    
    public JSONWorkPutCodes() {
        
    }
    
    public JSONWorkPutCodes(Long[] workPutCodes) {
        this.workPutCodes = workPutCodes;
    }

    public Long[] getWorkPutCodes() {
        return workPutCodes;
    }

    public void setWorkPutCodes(Long[] workPutCodes) {
        this.workPutCodes = workPutCodes;
    }
    
}
