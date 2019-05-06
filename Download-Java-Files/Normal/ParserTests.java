package org.aaroncoplan.waterfall;

import org.aaroncoplan.waterfall.parsing.FileParser;
import org.aaroncoplan.waterfall.parsing.ParseResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

public class ParserTests {

    @Test
    public void testParserResults() {
        final File baseDirectory = new File("");
        Assert.assertTrue(baseDirectory.getAbsolutePath().endsWith("/waterfall"));

        final File shouldPassParsingTestDir = new File(baseDirectory.getAbsolutePath() + "/src/main/resources/test-resources/parsing/should-pass");
        Assert.assertTrue(shouldPassParsingTestDir.exists());
        Assert.assertTrue(shouldPassParsingTestDir.isDirectory());

        final File[] shouldPassFiles = shouldPassParsingTestDir.listFiles((file, name) -> name.endsWith(".wf"));
        Assert.assertTrue(shouldPassFiles.length > 0);

        for(File f : shouldPassFiles) {
            final ParseResult parseResult = FileParser.parseFile(f.getPath());
            Assert.assertFalse(parseResult.hasErrors());
            Assert.assertNotNull(parseResult.getFilePath());
            Assert.assertNotNull(parseResult.getProgramAST());
        }

        final File shouldFailParsingTestDir = new File(baseDirectory.getAbsolutePath() + "/src/main/resources/test-resources/parsing/should-fail");
        Assert.assertTrue(shouldFailParsingTestDir.exists());
        Assert.assertTrue(shouldFailParsingTestDir.isDirectory());

        final File[] shouldFailFiles = shouldFailParsingTestDir.listFiles((file, name) -> name.endsWith(".wf"));
        Assert.assertTrue(shouldFailFiles.length > 0);

        for(File f : shouldFailFiles) {
            final ParseResult parseResult = FileParser.parseFile(f.getPath());
            Assert.assertTrue(parseResult.hasErrors());
            Assert.assertNotNull(parseResult.getFilePath());
            Assert.assertNotNull(parseResult.getProgramAST());
        }
    }
}
