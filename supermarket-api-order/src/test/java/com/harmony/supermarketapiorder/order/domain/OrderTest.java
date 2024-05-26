package com.harmony.supermarketapiorder.order.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderTest {

    @DisplayName("주문 생성 성공 테스트")
    @Test
    void test1() {
        // given
        OrderRequest request = OrderRequest.builder()
                .customerId("최찬혁")
                .deliveryAddress("풍세로 801-23")
                .deliveryMethod("빛보다 빠른 배달")
                .expectedDeliveryDate(LocalDate.now())
                .paymentMethod("card")
                .specialRequest("누구보다 빠르게 와주세요.")
                .build();

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


    @DisplayName("주문에 상품 추가 성공 테스트")
    @Test
    void test2() {
        // given
        Order order = new Order();
        OrderItem item = new OrderItem();
        item.setProductId(1L);
        item.setPrice(new BigDecimal("29.99"));
        item.setQuantity(2);

        // when
        order.addItem(item);

        // then
        assertNotNull(order.getItems());
        assertEquals(1, order.getItems().size());
        assertEquals(item, order.getItems().get(0));
        assertEquals(order, item.getOrder());

    }
}
