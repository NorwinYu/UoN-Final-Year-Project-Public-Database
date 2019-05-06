package org.yesworkflow.recon;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.yesworkflow.model.Model;

public class Run {

    public final Model model;
    public final Path runDirectoryBase;
    public List<Resource> resources;
    public List<UriVariable> uriVariables;
    public List<UriVariableValue> uriVariableValues;
    
    public Run(Model model, Path base) {
        this.model = model;
        this.runDirectoryBase = base;
        resources = new ArrayList<>();
        uriVariables = new ArrayList<>();
        uriVariableValues = new ArrayList<>();
    }

    public Run(Model model, String base) {
        this(model, new File(base).toPath());
    }

    public Run(Model model) {
        this(model, "");
    }
}
