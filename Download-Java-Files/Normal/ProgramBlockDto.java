package org.yesworkflow.save.data;

import com.google.gson.annotations.SerializedName;
import org.yesworkflow.model.Program;

public class ProgramBlockDto
{
    @SerializedName("programBlockId")
    public Long id;
    @SerializedName("inProgramBlock")
    public Long inProgramBlock;
    @SerializedName("name")
    public String name;
    @SerializedName("qualifiedName")
    public String qualifiedName;

    public ProgramBlockDto(Long id, Long inProgramBlock, String name, String qualifiedName)
    {
        this.id = id;
        this.inProgramBlock = inProgramBlock;
        this.name = name;
        this.qualifiedName = qualifiedName;
    }

    public ProgramBlockDto(Builder builder)
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

        public Builder(Long id, String name, String qualifiedName)
        {
            this.id = id;
            this.name = name;
            this.qualifiedName = qualifiedName;
        }

        public Builder(Program program)
        {
            this.id = program.id;
            this.name = program.name;
            this.qualifiedName = program.toString();
        }

        public Builder setInProgramBlock(Long inProgramBlock)
        {
            this.inProgramBlock = inProgramBlock;
            return this;
        }

        public ProgramBlockDto build()
        {
            return new ProgramBlockDto(this);
        }
    }
}
