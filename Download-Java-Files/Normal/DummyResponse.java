package org.yesworkflow.save.data;

import org.apache.http.HttpResponse;
import org.yesworkflow.exceptions.YwSaveException;
import org.yesworkflow.save.serialization.IYwSerializer;
import org.yesworkflow.save.response.YwResponse;

public class DummyResponse extends YwResponse<TestDto>
{
    public YwResponse<TestDto> Build(HttpResponse response, IYwSerializer serializer)
            throws YwSaveException
    {
        this.build(response, serializer);
        this.ResponseObject = DeserializeResponseContent();
        return this;
    }

    protected TestDto DeserializeResponseContent()
    {
        return serializer.Deserialize(this.ResponseBody, TestDto.class);
    }
}
