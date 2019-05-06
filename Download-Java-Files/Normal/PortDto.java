package org.yesworkflow.save.data;

import com.google.gson.annotations.SerializedName;
import org.yesworkflow.model.Port;

public class PortDto
{
    @SerializedName("portId")
    public Integer id;
    @SerializedName("onProgramBlock")
    public Long onProgramBlock;
    @SerializedName("data")
    public Long data;
    @SerializedName("name")
    public String name;
    @SerializedName("qualifiedName")
    public String qualifiedName;
    @SerializedName("alias")
    public String alias;
    @SerializedName("uriTemplate")
    public String uriTemplate;
    @SerializedName("inPort")
    public Boolean isInPort;
    @SerializedName("outPort")
    public Boolean isOutPort;

    public PortDto(Integer id,
                   Long onProgramBlock,
                   Long data,
                   String name,
                   String qualifiedName,
                   String alias,
                   String uriTemplate,
                   Boolean isInPort,
                   Boolean isOutPort) {
        this.id = id;
        this.onProgramBlock = onProgramBlock;
        this.data = data;
        this.name = name;
        this.qualifiedName = qualifiedName;
        this.alias = alias;
        this.uriTemplate = uriTemplate;
        this.isInPort = isInPort;
        this.isOutPort = isOutPort;
    }

    public PortDto(Builder builder) {
        this.id = builder.id;
        this.onProgramBlock = builder.onProgramBlock;
        this.data = builder.data;
        this.name = builder.name;
        this.qualifiedName = builder.qualifiedName;
        this.alias = builder.alias;
        this.uriTemplate = builder.uriTemplate;
        this.isInPort = builder.isInPort;
        this.isOutPort = builder.isOutPort;
    }

    public static class Builder
    {
        public Integer id;
        public Long onProgramBlock;
        public Long data;
        public String name;
        public String qualifiedName;
        public String alias;
        public String uriTemplate;
        public Boolean isInPort;
        public Boolean isOutPort;

        public Builder(Integer id,
                       Long onProgramBlock,
                       Long data,
                       String name,
                       String qualifiedName,
                       Boolean isInPort,
                       Boolean isOutPort)
        {
            this.id = id;
            this.onProgramBlock = onProgramBlock;
            this.data = data;
            this.name = name;
            this.qualifiedName = qualifiedName;
            this.isInPort = isInPort;
            this.isOutPort = isOutPort;
        }

        public Builder(Port port, Long programId, Boolean isInPort, Boolean isOutPort)
        {
            this.id = port.id;
            this.onProgramBlock = programId;
            this.data = port.data.id;
            this.name = port.data.name;
            this.qualifiedName = port.toString();
            this.isInPort = isInPort;
            this.isOutPort = isOutPort;
        }

        public Builder setAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public Builder setUriTemplate(String uriTemplate)
        {
            this.uriTemplate = uriTemplate;
            return this;
        }

        public PortDto build()
        {
            return new PortDto(this);
        }
    }
}
