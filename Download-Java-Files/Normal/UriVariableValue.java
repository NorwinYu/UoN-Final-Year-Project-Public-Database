package org.yesworkflow.recon;

public class UriVariableValue
{
    public Long uriVariable;
    public Integer resource;
    public String value;

    public UriVariableValue(Long uriVariable, Integer resource, String value)
    {
        this.uriVariable = uriVariable;
        this.resource = resource;
        this.value = value;
    }
}
