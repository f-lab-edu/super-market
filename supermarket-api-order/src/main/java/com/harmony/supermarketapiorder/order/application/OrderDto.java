package com.harmony.supermarketapiorder.order.application;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderDto {
    private Long orderId;
    private String customerId;
    private LocalDateTime orderDate;
    private String status;
    private String deliveryAddress;
    private String deliveryMethod;
    private LocalDate expectedDeliveryDate;
    private String paymentMethod;
    private String specialRequest;

}
