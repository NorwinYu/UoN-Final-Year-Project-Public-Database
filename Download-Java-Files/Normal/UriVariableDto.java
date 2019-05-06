package org.yesworkflow.save.data;

import com.google.gson.annotations.SerializedName;
import org.yesworkflow.recon.UriVariable;

public class UriVariableDto
{
    @SerializedName("uriVariableId")
    public Long id;
    @SerializedName("port")
    public Integer port;
    @SerializedName("name")
    public String name;

    public UriVariableDto(Long id, Integer port, String name)
    {
        this.id = id;
        this.port = port;
        this.name = name;
    }

    public UriVariableDto(UriVariable uriVariable)
    {
        this.id = uriVariable.id;
        this.port = uriVariable.port;
        this.name = uriVariable.name;
    }
}
