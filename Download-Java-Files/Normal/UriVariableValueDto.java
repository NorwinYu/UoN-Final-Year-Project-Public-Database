package org.yesworkflow.save.data;

import com.google.gson.annotations.SerializedName;
import org.yesworkflow.recon.UriVariableValue;

public class UriVariableValueDto
{
    @SerializedName("uriVariableId")
    public Long uriVariable;
    @SerializedName("resource")
    public Integer resource;
    @SerializedName("value")
    public String value;

    public UriVariableValueDto(Long uriVariable, Integer resource, String value)
    {
        this.uriVariable = uriVariable;
        this.resource = resource;
        this.value = value;
    }

    public UriVariableValueDto(UriVariableValue uriVariableValue)
    {
        this.uriVariable = uriVariableValue.uriVariable;
        this.resource = uriVariableValue.resource;
        this.value = uriVariableValue.value;
    }
}
