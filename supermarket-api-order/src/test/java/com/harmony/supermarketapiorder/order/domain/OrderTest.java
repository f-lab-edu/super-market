package com.harmony.supermarketapiorder.order.domain;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @DisplayName("주문 생성 테스트")
    @Nested
    class OrderCreateTest {
        @DisplayName("주문 생성 성공 테스트")
        @Test
        void orderCreateSuccess() {
            // given
            OrderRequest request = OrderRequest.builder()
                    .customerId("123456789")
                    .deliveryAddress("풍세로 801-23")
                    .deliveryMethod(DeliveryMethod.SUPER_FAST)
                    .expectedDeliveryDate(LocalDate.now().plusDays(2))
                    .paymentMethod(PaymentMethod.PAYPAL)
                    .specialRequest("누구보다 빠르게 와주세요.")
                    .build();

            // when
            Order order = Order.from(request);

            // then
            assertEquals("123456789", order.getCustomerId());
            assertEquals("풍세로 801-23", order.getDeliveryAddress());
            assertEquals(DeliveryMethod.SUPER_FAST, order.getDeliveryMethod());
            assertEquals(PaymentMethod.PAYPAL, order.getPaymentMethod());
            assertEquals("누구보다 빠르게 와주세요.", order.getSpecialRequest());
            assertEquals(OrderStatus.PENDING, order.getStatus());
        }

        @DisplayName("고객 ID는 최대 10글자 까지 가능하다.")
        @Test
        void orderCreateFail1() {
            // given
            OrderRequest request = OrderRequest.builder()
                    .customerId("12345678901")  // 11자리 숫자, 유효하지 않음
                    .deliveryAddress("풍세로 801-23")
                    .deliveryMethod(DeliveryMethod.FAST)
                    .expectedDeliveryDate(LocalDate.now().plusDays(3))
                    .paymentMethod(PaymentMethod.PAYPAL)
                    .specialRequest("누구보다 빠르게 와주세요.")
                    .build();

            // when & then
            Exception exception = assertThrows(IllegalArgumentException.class, () -> Order.from(request));
            assertTrue(exception.getMessage().contains("고객 ID는 1~10 사이의 정수형으로 구성되야 합니다."));
        }
        @DisplayName("고객 ID는 최소 한글자 이상이어야 한다.")
        @Test
        void orderCreateFail3() {
            // given
            OrderRequest request = OrderRequest.builder()
                    .customerId("")  // 11자리 숫자, 유효하지 않음
                    .deliveryAddress("풍세로 801-23")
                    .deliveryMethod(DeliveryMethod.FAST)
                    .expectedDeliveryDate(LocalDate.now().plusDays(3))
                    .paymentMethod(PaymentMethod.PAYPAL)
                    .specialRequest("누구보다 빠르게 와주세요.")
                    .build();

            // when & then
            Exception exception = assertThrows(IllegalArgumentException.class, () -> Order.from(request));
            assertTrue(exception.getMessage().contains("고객 ID는 1~10 사이의 정수형으로 구성되야 합니다."));
        }


        @DisplayName("예상 배송일은 주문일로부터 7일 안으로만 입력되어야 한다.")
        @Test
        void orderCreateFail4() {
            // given
            OrderRequest request = OrderRequest.builder()
                    .customerId("1234567890")
                    .deliveryAddress("풍세로 801-23")
                    .deliveryMethod(DeliveryMethod.NORMAL)
                    .expectedDeliveryDate(LocalDate.now().plusDays(8))  // 8일 경과시 정책 벗어남 유효하지 않음
                    .paymentMethod(PaymentMethod.PAYPAL)
                    .specialRequest("누구보다 빠르게 와주세요.")
                    .build();

            // when & then
            Exception exception = assertThrows(IllegalArgumentException.class, () -> Order.from(request));
            assertTrue(exception.getMessage().contains("예상 배송일은 배송 요청 당일로부터 최소 1일에서 7일 사이로 입력되야 합니다."));
        }

        @DisplayName("예상 배송일은 주문일로부터 최소 하루가 지나야 한다.")
        @Test
        void orderCreateFail5() {
            // given
            OrderRequest request = OrderRequest.builder()
                    .customerId("1234567890")
                    .deliveryAddress("풍세로 801-23")
                    .deliveryMethod(DeliveryMethod.NORMAL)
                    .expectedDeliveryDate(LocalDate.now())  // 주문 당일은 배송 정책에 부합하지 않음
                    .paymentMethod(PaymentMethod.PAYPAL)
                    .specialRequest("누구보다 빠르게 와주세요.")
                    .build();

            // when & then
            Exception exception = assertThrows(IllegalArgumentException.class, () -> Order.from(request));
            assertTrue(exception.getMessage().contains("예상 배송일은 배송 요청 당일로부터 최소 1일에서 7일 사이로 입력되야 합니다."));
        }

        @DisplayName("주문 요청은 null이 들어오면 안된다.")
        @Test
        void orderCreateFailRequestNull() {
            // given
            OrderRequest request = null;

            // when & then
            Exception exception = assertThrows(IllegalArgumentException.class, () -> Order.from(request));
            assertTrue(exception.getMessage().contains("주문 요청이 빈 상태로 넘어왔습니다."));
        }

        @DisplayName("배송 주소는 비어있으면 안된다.")
        @Test
        void orderCreateFailEmptyDeliveryAddress() {
            // given
            OrderRequest request = OrderRequest.builder()
                    .customerId("123456789")
                    .deliveryAddress(" ")  // 비어있는 배송 주소
                    .deliveryMethod(DeliveryMethod.NORMAL)
                    .expectedDeliveryDate(LocalDate.now().plusDays(2))
                    .paymentMethod(PaymentMethod.PAYPAL)
                    .specialRequest("누구보다 빠르게 와주세요.")
                    .build();

            // when & then
            Exception exception = assertThrows(IllegalArgumentException.class, () -> Order.from(request));
            assertTrue(exception.getMessage().contains("배송주소, 배송 수단, 지불 수단중에 비어있는 값이 있습니다."));
        }

        @DisplayName("배송 수단이 비어있으면 안된다.")
        @Test
        void orderCreateFailNullDeliveryMethod() {
            // given
            OrderRequest request = OrderRequest.builder()
                    .customerId("123456789")
                    .deliveryAddress("풍세로 801-23")
                    .deliveryMethod(null)  // null인 배송 수단
                    .expectedDeliveryDate(LocalDate.now().plusDays(2))
                    .paymentMethod(PaymentMethod.PAYPAL)
                    .specialRequest("누구보다 빠르게 와주세요.")
                    .build();

            // when & then
            Exception exception = assertThrows(IllegalArgumentException.class, () -> Order.from(request));
            assertTrue(exception.getMessage().contains("배송주소, 배송 수단, 지불 수단중에 비어있는 값이 있습니다."));
        }

        @DisplayName("지불 수단이 비어 있으면 안된다.")
        @Test
        void orderCreateFailEmptyPaymentMethod() {
            // given
            OrderRequest request = OrderRequest.builder()
                    .customerId("123456789")
                    .deliveryAddress("풍세로 801-23")
                    .deliveryMethod(DeliveryMethod.NORMAL)
                    .expectedDeliveryDate(LocalDate.now().plusDays(2))
                    .paymentMethod(null)  // 비어있는 지불 수단
                    .specialRequest("누구보다 빠르게 와주세요.")
                    .build();

            // when & then
            Exception exception = assertThrows(IllegalArgumentException.class, () -> Order.from(request));
            assertTrue(exception.getMessage().contains("배송주소, 배송 수단, 지불 수단중에 비어있는 값이 있습니다."));
        }
    }

    @DisplayName("주문에 상품 추가 테스트")
    @Nested
    class OrderItemAddTest {
        @DisplayName("주문에 상품 추가 성공 테스트")
        @Test
        void orderAddItemSuccess() {
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

}
