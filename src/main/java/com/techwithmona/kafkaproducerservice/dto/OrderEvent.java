package com.techwithmona.kafkaproducerservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public class OrderEvent {

    @NotBlank(message = "orderId is required")
    private String orderId;

    @NotBlank(message = "type is required")
    private String type;

    @Min(value = 1, message= "quantity must be >=1")
    private int quantity;

    private Instant timestamp = Instant.now();


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
