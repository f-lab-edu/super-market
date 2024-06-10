package com.harmony.supermarketapiorder.order.application;

public abstract class OrderResponseDto {

    private OrderResponseType orderResponseType;

    public void setOrderResponseType(OrderResponseType orderResponseType) {
        this.orderResponseType = orderResponseType;
    }
}
