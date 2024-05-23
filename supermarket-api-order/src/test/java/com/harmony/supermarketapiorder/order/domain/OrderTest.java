package com.harmony.supermarketapiorder.order.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {

    @DisplayName("주문 생성 성공 테스트")
    @Test
    void test1() {
        // given
        OrderRequest request = new OrderRequest(
                "최찬혁",
                "풍세로 801-23",
                "빛보다 빠른 배달",
                LocalDate.now(),
                "card",
                "누구보다 빠르게 와주세요."
        );

        // when
        Order order = Order.from(request);

        // then
        assertEquals("최찬혁", order.getCustomerId());
        assertEquals("풍세로 801-23", order.getDeliveryAddress());
        assertEquals("빛보다 빠른 배달", order.getDeliveryMethod());
        assertEquals("card", order.getPaymentMethod());
        assertEquals("누구보다 빠르게 와주세요.", order.getSpecialRequest());
        assertEquals("pending", order.getStatus());
    }
}
