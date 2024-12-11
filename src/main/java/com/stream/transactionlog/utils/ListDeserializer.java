package com.stream.transactionlog.utils;

import org.apache.kafka.common.serialization.Deserializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ListDeserializer<T> implements Deserializer<List<T>> {
    private final Class<T> type;
    private final Gson gson = new Gson();

    public ListDeserializer(Class<T> type) {
        this.type = type;
    }

    @Override
    public List<T> deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        // Chuyển đổi byte array thành danh sách
        return gson.fromJson(new String(data, StandardCharsets.UTF_8),
                TypeToken.getParameterized(List.class, type).getType());
    }
}