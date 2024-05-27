package com.harmony.supermarketapiorder.order.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderRequest {
    private String customerId;
    private String deliveryAddress;
    private String deliveryMethod;
    private LocalDate expectedDeliveryDate;
    private String paymentMethod;
    private String specialRequest;
    private List<OrderItemRequest> items;

    @Builder
    public OrderRequest(String customerId, String deliveryAddress, String deliveryMethod, LocalDate expectedDeliveryDate, String paymentMethod, String specialRequest, List<OrderItemRequest> items) {
        this.customerId = customerId;
        this.deliveryAddress = deliveryAddress;
        this.deliveryMethod = deliveryMethod;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.paymentMethod = paymentMethod;
        this.specialRequest = specialRequest;
        this.items = items;
    }
}
