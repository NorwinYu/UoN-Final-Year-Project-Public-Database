package org.yesworkflow.save;

import org.yesworkflow.YWStage;
import org.yesworkflow.config.Configurable;
import org.yesworkflow.recon.Run;

import java.util.List;
import java.util.Map;

public interface Saver extends YWStage, Configurable
{
    Saver configure(Map<String, Object> config) throws Exception;
    Saver configure(String key, Object value) throws Exception;
    Saver build(Run run, String graph, List<String> sourceCodeList, List<String> sourcePaths);
    Saver save() throws Exception;
    Saver login() throws Exception;
}


