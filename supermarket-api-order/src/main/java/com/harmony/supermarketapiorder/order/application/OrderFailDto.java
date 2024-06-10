package com.harmony.supermarketapiorder.order.application;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class OrderFailDto extends OrderResponseDto {
    private String message;

    public OrderFailDto(String message) {
        this.message = message;
        super.setOrderResponseType(OrderResponseType.FAIL);
    }
}
