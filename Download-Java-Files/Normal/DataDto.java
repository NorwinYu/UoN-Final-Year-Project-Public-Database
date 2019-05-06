package org.yesworkflow.save.data;

import com.google.gson.annotations.SerializedName;
import org.yesworkflow.model.Data;

public class DataDto {
    @SerializedName("dataId")
    public Long id;
    @SerializedName("inProgramBlock")
    public Long inProgramBlock;
    @SerializedName("name")
    public String name;
    @SerializedName("qualifiedName")
    public String qualifiedName;

    public DataDto(Long id, Long inProgramBlock, String name, String qualifiedName)
    {
        this.id = id;
        this.inProgramBlock = inProgramBlock;
        this.name = name;
        this.qualifiedName = qualifiedName;
    }

    public DataDto(Builder builder)
    {
        this.id = builder.id;
        this.inProgramBlock = builder.inProgramBlock;
        this.name = builder.name;
        this.qualifiedName = builder.qualifiedName;
    }

    public static class Builder
    {
        public Long id;
        public Long inProgramBlock;
        public String name;
        public String qualifiedName;


        public Builder(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Builder(Data data)
        {
            this.id = data.id;
            this.name = data.name;
            this.qualifiedName = data.qualifiedName == null ? data.name : data.qualifiedName;
        }

        public Builder setInProgramBlock(Long inProgramBlock)
        {
            this.inProgramBlock = inProgramBlock;
            return this;
        }

        public Builder setQualifiedName(String qualifiedName)
        {
            this.qualifiedName = qualifiedName;
            return this;
        }

        public DataDto build()
        {
            return new DataDto(this);
        }
    }

}
