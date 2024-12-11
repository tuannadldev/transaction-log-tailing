package com.stream.transactionlog.utils;

import org.apache.kafka.common.serialization.Serializer;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ListSerializer<T> implements Serializer<List<T>> {
    private final Gson gson = new Gson();

    @Override
    public byte[] serialize(String topic, List<T> data) {
        if (data == null) {
            return null;
        }
        return gson.toJson(data).getBytes(StandardCharsets.UTF_8);
    }
}