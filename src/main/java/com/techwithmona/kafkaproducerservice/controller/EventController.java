package com.techwithmona.kafkaproducerservice.controller;


import com.techwithmona.kafkaproducerservice.dto.OrderEvent;
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

    @PostMapping
    public String event(@Valid @RequestBody OrderEvent orderEvent) {
        orderEventPublisher.publish(orderEvent);
        return "published";
    }



}
