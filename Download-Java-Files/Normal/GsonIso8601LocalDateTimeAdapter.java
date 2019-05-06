package org.yesworkflow.save.serialization;

import com.google.gson.*;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class GsonIso8601LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime>
{
    private final DateTimeFormatter iso8601DateTimeFormat;
    private final ZoneOffset zoneOffset;

    public GsonIso8601LocalDateTimeAdapter()
    {
        this(ZoneOffset.UTC);
    }

    public GsonIso8601LocalDateTimeAdapter(ZoneOffset zoneOffset)
    {
        this.iso8601DateTimeFormat = DateTimeFormatter.ISO_DATE_TIME;
        this.zoneOffset = zoneOffset;
    }

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {

        String dateFormatString = localDateTime.atZone(zoneOffset).format(iso8601DateTimeFormat);
        return new JsonPrimitive(dateFormatString);
    }

    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        // Unnecessary to implement, but the method is required for Gson adapters
        return null;
    }
}
