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

    public OrderDto(Order order){
        this.orderId = order.getOrderId();
        this.customerId = order.getCustomerId();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
        this.deliveryAddress = order.getDeliveryAddress();
        this.deliveryMethod = order.getDeliveryMethod();
        this.expectedDeliveryDate = order.getExpectedDeliveryDate();
        this.paymentMethod = order.getPaymentMethod();
        this.specialRequest = order.getSpecialRequest();
    }

}