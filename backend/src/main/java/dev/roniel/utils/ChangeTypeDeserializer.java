package dev.roniel.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class ChangeTypeDeserializer extends JsonDeserializer<ChangeType> {

    @Override
    public ChangeType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getValueAsString();
        return ChangeType.fromString(value);
    }
}

