package com.stream.transactionlog.usecase;

import com.stream.transactionlog.domain.model.Order;
import com.stream.transactionlog.domain.model.OrderDetail;
import com.stream.transactionlog.domain.model.OrderDetailList;
import com.stream.transactionlog.utils.SerdeUtil;
import io.debezium.serde.DebeziumSerdes;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import com.stream.transactionlog.infrastructure.config.AppConfig;
import com.stream.transactionlog.utils.LoggingHelper;
import org.slf4j.Logger;
import com.stream.transactionlog.domain.model.EnrichedOrder;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;


public class EnrichOrderUseCase {
    private static final Logger logger = LoggingHelper.getLogger(EnrichOrderUseCase.class);

    public Properties initKafkaStreams(AppConfig.UseCaseConfig useCaseConfig, String bootstrapServers) {
        Properties props = new Properties();
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, useCaseConfig.getApplicationID());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, useCaseConfig.getCommitTime());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

    public void buildTopology(StreamsBuilder builder, AppConfig.UseCaseConfig useCaseConfig) {
        String orderTopic = useCaseConfig.getTopics().get("orderTopic");
        String orderDetailTopic = useCaseConfig.getTopics().get("orderDetailTopic");
        String combinedOrderTopic = useCaseConfig.getTopics().get("aggregateTopic");

        // Init serde
        Serde<String> orderKey = DebeziumSerdes.payloadJson(String.class);
        orderKey.configure(Collections.emptyMap(), true);
        Serde<Order> orderSerde = DebeziumSerdes.payloadJson(Order.class);
        orderSerde.configure(Collections.singletonMap("from.field", "after"), false); // Cấu hình Serde

        Serde<String> orderDetailKey = DebeziumSerdes.payloadJson(String.class);
        orderDetailKey.configure(Collections.emptyMap(), true);
        Serde<OrderDetail> orderDetailSerde = DebeziumSerdes.payloadJson(OrderDetail.class);
        orderDetailSerde.configure(Collections.singletonMap("from.field", "after"), false);

        Serde<OrderDetailList> orderDetailListSerde = SerdeUtil.buildSerde(OrderDetailList.class);
        Serde<EnrichedOrder> enrichedOrderSerde = SerdeUtil.buildSerde(EnrichedOrder.class);

        // Load orders as a KTable
        KTable<String, Order> orderTable = builder.table(
                orderTopic, // Tên topic
                Consumed.with(orderKey, orderSerde)
        );

        KStream<String, OrderDetail> orderDetailTable = builder.stream(
                orderDetailTopic,
                Consumed.with(orderDetailKey, orderDetailSerde)
        );

        KTable<String, OrderDetailList> groupOrderDetail = orderDetailTable
                .map((k, v) -> KeyValue.pair(
                        v == null ? null : v.getOrderId(), v))
                .groupByKey(Grouped.with(Serdes.String(), SerdeUtil.buildSerde(OrderDetail.class)))
                .aggregate(
                        OrderDetailList::new,
                        (k, v, va) -> {
                            if (va == null) {
                                va = new OrderDetailList();
                            }
                           va.getDetails().removeIf(detail -> detail.getDetailId().equals(v.getDetailId()));
                            va.addOrderDetail(v);
                            return va;
                        },
                        Materialized.with(Serdes.String(), orderDetailListSerde)
                );

        KTable<String, EnrichedOrder> combinedOrderTable = orderTable
                .join(groupOrderDetail,
                        (order, orderDetailList) -> {
                            EnrichedOrder enrichedOrder = new EnrichedOrder(order);
                            enrichedOrder.setDetails(orderDetailList.getDetails());
                            return enrichedOrder;
                        },
                        Materialized.with(Serdes.String(), enrichedOrderSerde)
                );
        combinedOrderTable.toStream().foreach((k,v)->{
            System.out.println("Key: " + k + " OrderId: " + v.getOrderId() + " OrderDetailId: " + v.getDetails().get(0).getDetailId());
        });
        combinedOrderTable.toStream().to(combinedOrderTopic, Produced.with(Serdes.String(), enrichedOrderSerde));
    }

    public void run(AppConfig.UseCaseConfig useCaseConfig, String bootstrapServers) {
        Properties props = initKafkaStreams(useCaseConfig, bootstrapServers);
        StreamsBuilder builder = new StreamsBuilder();
        buildTopology(builder,useCaseConfig);
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }
}
