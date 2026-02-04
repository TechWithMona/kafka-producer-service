package com.techwithmona.kafkaproducerservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;


@Data
public class OrderEvent {

    @NotBlank(message = "orderId is required")
    private String orderId;

    private OrderEventType type;

    @Min(value = 1, message= "quantity must be >=1")
    private int quantity;

    @NotBlank
    private String timestamp;
}
