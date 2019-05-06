package org.yesworkflow.save.response;

import org.apache.http.HttpResponse;
import org.yesworkflow.exceptions.YwSaveException;
import org.yesworkflow.save.serialization.IYwSerializer;
import org.yesworkflow.save.data.CreatedDto;

public class SaveResponse extends YwResponse<CreatedDto>
{

    @Override
    public YwResponse<CreatedDto> Build(HttpResponse response, IYwSerializer serializer)
            throws YwSaveException
    {
        build(response, serializer);
        this.ResponseObject = DeserializeResponseContent();
        return this;
    }

    @Override
    protected CreatedDto DeserializeResponseContent()
    {
        return serializer.Deserialize(this.ResponseBody, CreatedDto.class);
    }
}
