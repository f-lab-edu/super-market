package com.harmony.supermarketapiorder.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class OrderItemRequest {
    private Long productId;
    private BigDecimal price;
    private int quantity;
}