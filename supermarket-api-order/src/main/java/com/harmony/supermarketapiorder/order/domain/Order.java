package com.harmony.supermarketapiorder.order.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`orders`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String customerId;
    private LocalDateTime orderDate;
    private String status;
    private BigDecimal totalPrice;
    private String deliveryAddress;
    private String deliveryMethod;
    private LocalDate expectedDeliveryDate;
    private String paymentMethod;
    private String specialRequest;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> items = new ArrayList<>();

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrderId(this.orderId);
    }

    public void calculateTotalPrice() {
        totalPrice = items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static Order from(OrderRequest request) {
        return Order.builder()
                .customerId(request.getCustomerId())
                .orderDate(LocalDateTime.now())
                .status("pending")
                .deliveryAddress(request.getDeliveryAddress())
                .deliveryMethod(request.getDeliveryMethod())
                .expectedDeliveryDate(request.getExpectedDeliveryDate())
                .paymentMethod(request.getPaymentMethod())
                .specialRequest(request.getSpecialRequest())
                .build();
    }

    // TODO 3. 주문 취소
    public void cancelOrder() {

    }

    // TODO 4. 특정 주문 정보 조회
    public void getOrderDetails() {

    }
}