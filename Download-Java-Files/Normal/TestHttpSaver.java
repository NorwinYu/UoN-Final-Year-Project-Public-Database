package org.yesworkflow.save;

import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.yesworkflow.YesWorkflowTestCase;
import org.yesworkflow.db.YesWorkflowDB;
import org.yesworkflow.extract.DefaultExtractor;
import org.yesworkflow.extract.Extractor;
import org.yesworkflow.model.DefaultModeler;
import org.yesworkflow.model.Modeler;
import org.yesworkflow.recon.DefaultReconstructor;
import org.yesworkflow.recon.Reconstructor;
import org.yesworkflow.save.serialization.IYwSerializer;
import org.yesworkflow.save.serialization.JsonSerializer;

import java.io.InputStream;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestHttpSaver extends YesWorkflowTestCase
{
    private YesWorkflowDB ywdb = null;
    private Extractor extractor = null;
    private Modeler modeler = null;
    private Reconstructor reconstructor = null;
    private CloseableHttpClient httpClient = null;

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        this.ywdb = YesWorkflowDB.createInMemoryDB();
        this.extractor = new DefaultExtractor(this.ywdb, super.stdoutStream, super.stderrStream);
        this.modeler = new DefaultModeler(this.ywdb, super.stdoutStream, super.stderrStream);
        this.reconstructor = new DefaultReconstructor(super.stdoutStream, super.stderrStream);
        this.httpClient = mock(CloseableHttpClient.class);
    }

    @Test
    public void testSaver_TagParse() throws Exception
    {
        IYwSerializer serializer = new JsonSerializer();
        HttpSaver saver = new HttpSaver(serializer, null, null, mock(InputStream.class));
        saver.configure("tags", "a, b, c, d, e");
        ArrayList<String> x = new ArrayList<String>();
        x.add("a");
        x.add("b");
        x.add("c");
        x.add("d");
        x.add("e");
        Assert.assertEquals(x, saver.tags);
    }

    @Test
    public void testSaver_WorkflowParse() throws Exception
    {
        IYwSerializer serializer = new JsonSerializer();
        HttpSaver saver = new HttpSaver(serializer, null, null, mock(InputStream.class));
        Integer expected = 1;

        saver.configure("workflow", "1");
        Assert.assertEquals(expected, saver.workflowId);
    }

    @Test
    public void testSaver_FormatUrl() throws Exception
    {
        IYwSerializer serializer = new JsonSerializer();
        HttpSaver saver = new HttpSaver(serializer, null, null, mock(InputStream.class));

        String[][] testData = new String[][]{
                {"url", "http://url/"},
                {"url/", "http://url/"},
                {"http://url", "http://url/"},
                {"http://url/", "http://url/"},
                {"https://url", "https://url/"},
                {"https://url/", "https://url/"},
                {"", "http://"},
        };

        for(String[] dataPoint : testData)
        {
            saver.configure("serveraddress", dataPoint[0]);
            assertEquals(String.format("Saver transformed '%s' to  '%s'", dataPoint[0], saver.baseUrl),
                         dataPoint[1],
                         saver.baseUrl);
        }
    }
}
