package com.techwithmona.kafkaproducerservice.publisher;

import com.techwithmona.kafkaproducerservice.dto.OrderEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {


    private static final String topic = "order-event";

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderEventPublisher(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(OrderEvent orderEvent) {
        kafkaTemplate.send(topic, orderEvent.getOrderId(), orderEvent);
    }


}
