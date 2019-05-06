package org.yesworkflow.save;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import org.junit.Test;
import org.junit.Assert;
import org.yesworkflow.YesWorkflowTestCase;
import org.yesworkflow.save.data.*;
import org.yesworkflow.save.data.TestDto;
import org.yesworkflow.save.serialization.GsonIso8601LocalDateTimeAdapter;
import org.yesworkflow.save.serialization.IYwSerializer;
import org.yesworkflow.save.serialization.JsonSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;

public class TestSaveUtility extends YesWorkflowTestCase
{
    @Test
    public void testJsonSerializer_serialize()
    {
        IYwSerializer serializer = new JsonSerializer();

        TestDto testDto = new TestDto("first", "second", "third");
        String expectedOutput = TestData.testDtoJson;
        String actualOutput = serializer.Serialize(testDto);

        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testJsonSerializer_serializeNestedDto()
    {
        IYwSerializer serializer = new JsonSerializer();
        ScriptDto scriptDto = new ScriptDto("n", "c", "cs");
        String scriptJson = serializer.Serialize(scriptDto);

        String expected = String.format("{\"model\":\"m\",\"modelChecksum\":\"mc\",\"graph\":\"g\",\"scripts\":[%s]}", scriptJson);

        ArrayList<ScriptDto> s = new ArrayList<>();
        s.add(scriptDto);
        RunDto testRunDto = new RunDto.Builder("m", "mc", "g", s)
                                                .build();

        String actual = serializer.Serialize(testRunDto);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testJsonSerializer_serializeLocalDateTime()
    {
        ZoneOffset zoneOffset = ZoneOffset.UTC;
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(1553474986, 0, zoneOffset);
        String expected = "{\"timestamp\":\"2019-03-25T00:49:46Z\"}";

        TestTimeStampObject testTimeStampObject = new TestTimeStampObject(localDateTime);

        IYwSerializer serializer = new JsonSerializer();
        String actual = serializer.Serialize(testTimeStampObject);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testJsonSerializer_deserializeLocalDateTime()
    {
        String timestamp = "{\"timestamp\":\"2019-03-25T00:49:46Z\"}";

        IYwSerializer serializer = new JsonSerializer();
        TestTimeStampObject testTimeStampObject = serializer.Deserialize(timestamp, TestTimeStampObject.class);

        Assert.assertNull(testTimeStampObject.timestamp);
    }

    @Test
    public void testJSONSerializer_deserialize()
    {
        IYwSerializer serializer = new JsonSerializer();

        TestDto actual = serializer.Deserialize(TestData.testDtoJson, TestDto.class);
        TestDto expected = new TestDto("first", "second", "third");

        Assert.assertEquals(expected.one, actual.one);
        Assert.assertEquals(expected.two, actual.two);
        Assert.assertEquals(expected.three, actual.three);
    }

    @Test
    public void testGsonIso8601DateAdapter_serialize()
    {
        ZoneOffset zoneOffset = ZoneOffset.UTC;
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(1553474986, 0, zoneOffset);
        String expectedIso8601String = "2019-03-25T00:49:46Z";

        GsonIso8601LocalDateTimeAdapter gsonIso8601LocalDateTimeAdapter = new GsonIso8601LocalDateTimeAdapter();


        JsonElement actualTimeStamp = gsonIso8601LocalDateTimeAdapter.serialize(localDateTime,
                                                                                mock(Type.class),
                                                                                mock(JsonSerializationContext.class));

        Assert.assertEquals(expectedIso8601String, actualTimeStamp.getAsString());
    }

    @Test
    public void testGsonIso8601DateAdapter_deserialize()
    {
        GsonIso8601LocalDateTimeAdapter gsonIso8601LocalDateTimeAdapter = new GsonIso8601LocalDateTimeAdapter();
        LocalDateTime result = gsonIso8601LocalDateTimeAdapter.deserialize(mock(JsonElement.class),
                                                                           mock(Type.class),
                                                                           mock(JsonDeserializationContext.class));

        Assert.assertNull(result);
    }

    @Test
    public void testURIHash() throws NoSuchAlgorithmException, IOException {
        URI uri =  URI.create("examples/clean_name_date/date_val_log.txt");

        Hash hash = new Hash("md5");
        String test = hash.getHash(uri);
        assertEquals(test, "9e1de71fbdbb8c680f5729360cf220c7");
    }

    @Test
    public void testPathHash() throws NoSuchAlgorithmException, IOException {
        Path path = Paths.get("." ,"examples/clean_name_date/date_val_log.txt");

        Hash hash = new Hash("md5");
        String test = hash.getHash(path);
        assertEquals(test, "9e1de71fbdbb8c680f5729360cf220c7");
    }

    @Test
    public void testStringPathHash() throws NoSuchAlgorithmException, IOException {
        String path = "examples/clean_name_date/date_val_log.txt";

        Hash hash = new Hash("md5");
        String test = hash.getHash(path);
        assertEquals(test, "9e1de71fbdbb8c680f5729360cf220c7");
    }

    @Test
    public void testStringHash() {
        String testString = "This is a string used to test String Hashing.";

        String hashedString = Hash.getStringHash(testString);
        assertEquals(hashedString, "1213258105");
    }
}
