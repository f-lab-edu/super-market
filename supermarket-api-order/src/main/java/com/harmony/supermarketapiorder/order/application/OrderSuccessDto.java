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
public class OrderSuccessDto extends OrderResponseDto {
    Long orderId;
    String customerId;
    LocalDateTime orderDate;
    OrderStatus status;
    String deliveryAddress;
    DeliveryMethod deliveryMethod;
    LocalDate expectedDeliveryDate;
    PaymentMethod paymentMethod;
    String specialRequest;

    public OrderSuccessDto(Order order){
        this.orderId = order.getOrderId();
        this.customerId = order.getCustomerId();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
        this.deliveryAddress = order.getDeliveryAddress();
        this.deliveryMethod = order.getDeliveryMethod();
        this.expectedDeliveryDate = order.getExpectedDeliveryDate();
        this.paymentMethod = order.getPaymentMethod();
        this.specialRequest = order.getSpecialRequest();
        super.setOrderResponseType(OrderResponseType.SUCCESS);
    }

}