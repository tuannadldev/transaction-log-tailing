package com.stream.transactionlog.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class JsonDeserializer<T> implements Deserializer<T> {
    private final Class<T> type;
    private static final ObjectMapper mapper = new ObjectMapper();

    public JsonDeserializer(Class<T> type) {
        this.type = type;
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return mapper.readValue(data, type);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing JSON", e);
        }
    }

}