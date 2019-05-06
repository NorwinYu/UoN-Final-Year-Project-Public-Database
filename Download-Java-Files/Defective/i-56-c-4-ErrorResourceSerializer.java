package io.spring.api.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.Opt;
import static org.checkerframework.checker.nullness.Opt.*;
import java.util.Optional;

public class ErrorResourceSerializer extends JsonSerializer<ErrorResource> {
    // Error generated even when at line 37
    // ErrorResourceSerializer.java:33: error: [dereference.of.nullable] dereference of possibly-null reference json.get(fieldErrorResource.getField())
    //         json.get(fieldErrorResource.getField()).add(fieldErrorResource.getMessage());
    //                 ^
    @SuppressWarnings("nullness") // False Positive in no circumstance json.get(fieldErrorResource.getField()) will become null
    @Override
    public void serialize(ErrorResource value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        Map<String, List<String>> json = new HashMap<>();
        gen.writeStartObject();
        gen.writeObjectFieldStart("errors");
        for (FieldErrorResource fieldErrorResource : value.getFieldErrors()) {
            if (!json.containsKey(fieldErrorResource.getField())) {
                json.put(fieldErrorResource.getField(), new ArrayList<String>());
            }

            json.get(fieldErrorResource.getField()).add(fieldErrorResource.getMessage());
        }
        for (Map.Entry<String, List<String>> pair : json.entrySet()) {
            gen.writeArrayFieldStart(pair.getKey());
            pair.getValue().forEach(content -> {
                try {
                    gen.writeString(content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            gen.writeEndArray();
        }
        gen.writeEndObject();
        gen.writeEndObject();
    }
}
