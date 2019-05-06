package org.yesworkflow.save.response;

import org.apache.http.HttpResponse;
import org.yesworkflow.exceptions.YwSaveException;
import org.yesworkflow.save.IYwSerializer;
import org.yesworkflow.save.data.LogoutDto;

public class LogoutResponse extends YwResponse<LogoutDto>
{
    @Override
    protected LogoutDto DeserializeResponseContent()
    {
        return this.serializer.Deserialize(this.ResponseBody, LogoutDto.class);
    }

    @Override
    public YwResponse<LogoutDto> Build(HttpResponse response, IYwSerializer serializer)
            throws YwSaveException
    {
        super.build(response, serializer);
        this.ResponseObject = DeserializeResponseContent();
        return this;
    }
}
