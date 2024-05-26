package com.harmony.supermarketapiorder.order.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long productId;
    private BigDecimal price;
    private int quantity;

    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public static OrderItem from(OrderItemRequest itemRequest) {
        return OrderItem.builder()
                .productId(itemRequest.getProductId())
                .price(itemRequest.getPrice())
                .quantity(itemRequest.getQuantity())
                .build();
    }
}