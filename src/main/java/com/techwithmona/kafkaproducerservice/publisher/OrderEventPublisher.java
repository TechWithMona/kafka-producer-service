package com.techwithmona.kafkaproducerservice.publisher;

import com.techwithmona.kafkaproducerservice.dto.OrderEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {

    @Value("${app.kafka.topic.order-events}")
    private String topic;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderEventPublisher(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(OrderEvent orderEvent) {
        kafkaTemplate.send(topic, orderEvent.getOrderId(), orderEvent);
    }

}
