package com.techwithmona.kafkaproducerservice.controller;


import com.techwithmona.kafkaproducerservice.dto.OrderEvent;
import com.techwithmona.kafkaproducerservice.dto.OrderEventType;
import com.techwithmona.kafkaproducerservice.publisher.OrderEventPublisher;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventController {

    private final OrderEventPublisher orderEventPublisher;

    public EventController(OrderEventPublisher orderEventPublisher) {
        this.orderEventPublisher = orderEventPublisher;
    }

    @PostMapping("/created")
    public String created(@Valid @RequestBody OrderEvent orderEvent) {
        orderEvent.setType(OrderEventType.CREATED);
        orderEventPublisher.publish(orderEvent);
        return "CREATED published";
    }

    @PostMapping("/confirmed")
    public String confirmed(@Valid @RequestBody OrderEvent orderEvent) {
        orderEvent.setType(OrderEventType.CONFIRMED);
        orderEventPublisher.publish(orderEvent);
        return "CONFIRMED published";
    }

    @PostMapping("/shipped")
    public String shipped(@Valid @RequestBody OrderEvent orderEvent) {
        orderEvent.setType(OrderEventType.SHIPPED);
        orderEventPublisher.publish(orderEvent);
        return "SHIPPED published";
    }

}
