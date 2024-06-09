package com.harmony.supermarketapiorder.order.application;

import com.harmony.supermarketapiorder.order.domain.DeliveryMethod;
import com.harmony.supermarketapiorder.order.domain.Order;
import com.harmony.supermarketapiorder.order.domain.OrderStatus;
import com.harmony.supermarketapiorder.order.domain.PaymentMethod;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDto {
    private Long orderId;
    private String customerId;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private String deliveryAddress;
    private DeliveryMethod deliveryMethod;
    private LocalDate expectedDeliveryDate;
    private PaymentMethod paymentMethod;
    private String specialRequest;

    public static OrderDto from(Order order) {
        return new OrderDto(
                order.getOrderId(),
                order.getCustomerId(),
                order.getOrderDate(),
                order.getStatus(),
                order.getDeliveryAddress(),
                order.getDeliveryMethod(),
                order.getExpectedDeliveryDate(),
                order.getPaymentMethod(),
                order.getSpecialRequest()
        );
    }
}