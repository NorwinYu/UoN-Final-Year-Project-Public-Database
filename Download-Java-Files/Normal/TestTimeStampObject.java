package org.yesworkflow.save.data;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class TestTimeStampObject
{
    @SerializedName("timestamp")
    public LocalDateTime timestamp;

    public TestTimeStampObject(LocalDateTime timestamp)
    {
        this.timestamp = timestamp;
    }
}
