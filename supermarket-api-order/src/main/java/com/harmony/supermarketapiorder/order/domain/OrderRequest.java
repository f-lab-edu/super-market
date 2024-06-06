package com.harmony.supermarketapiorder.order.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderRequest {
    private String customerId;
    private String deliveryAddress;
    private DeliveryMethod deliveryMethod;
    private LocalDate expectedDeliveryDate;
    private PaymentMethod paymentMethod;
    private String specialRequest;
    private List<OrderItemRequest> items;

    @Builder
    public OrderRequest(String customerId, String deliveryAddress, DeliveryMethod deliveryMethod, LocalDate expectedDeliveryDate, PaymentMethod paymentMethod, String specialRequest, List<OrderItemRequest> items) {
        this.customerId = customerId;
        this.deliveryAddress = deliveryAddress;
        this.deliveryMethod = deliveryMethod;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.paymentMethod = paymentMethod;
        this.specialRequest = specialRequest;
        this.items = items;
    }
}
