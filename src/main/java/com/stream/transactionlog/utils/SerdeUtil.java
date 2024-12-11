package com.stream.transactionlog.utils;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import java.util.List;

public class SerdeUtil {
    public static <T> Serde<T> buildSerde(Class<T> type) {
        return Serdes.serdeFrom(new JsonSerializer<>(), new JsonDeserializer<>(type));
    }

    public static <T> Serde<List<T>> buildListSerde(Class<T> type) {
        return Serdes.serdeFrom(new ListSerializer<>(), new ListDeserializer<>(type));
    }

}