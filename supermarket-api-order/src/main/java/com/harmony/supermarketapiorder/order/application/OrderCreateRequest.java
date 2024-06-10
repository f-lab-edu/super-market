package com.harmony.supermarketapiorder.order.application;

import com.harmony.supermarketapiorder.order.domain.DeliveryMethod;
import com.harmony.supermarketapiorder.order.domain.OrderItemRequest;
import com.harmony.supermarketapiorder.order.domain.OrderRequest;
import com.harmony.supermarketapiorder.order.domain.PaymentMethod;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderCreateRequest {
    private String customerId;
    private String deliveryAddress;
    private String deliveryMethod;
    private LocalDate expectedDeliveryDate;
    private String paymentMethod;
    private String specialRequest;
    private List<OrderItemRequest> items;

    OrderCreateRequest() {}

    @Builder
    public OrderCreateRequest(String customerId, String deliveryAddress, String deliveryMethod, LocalDate expectedDeliveryDate, String paymentMethod, String specialRequest, List<OrderItemRequest> items) {
        this.customerId = customerId;
        this.deliveryAddress = deliveryAddress;
        this.deliveryMethod = deliveryMethod;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.paymentMethod = paymentMethod;
        this.specialRequest = specialRequest;
        this.items = items;
    }

    public OrderRequest toOrderRequest() {
        return OrderRequest.builder()
                .customerId(this.customerId)
                .deliveryAddress(this.deliveryAddress)
                .deliveryMethod(DeliveryMethod.valueOf(this.deliveryMethod))
                .expectedDeliveryDate(this.expectedDeliveryDate)
                .paymentMethod(PaymentMethod.valueOf(this.paymentMethod))
                .specialRequest(this.specialRequest)
                .items(this.items)
                .build();
    }
}