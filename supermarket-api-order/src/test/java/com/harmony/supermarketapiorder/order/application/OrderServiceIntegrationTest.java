package com.harmony.supermarketapiorder.order.application;

import com.harmony.supermarketapiorder.order.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;


    @DisplayName("주문 생성 성공 통합 테스트")
    @Test
    void test1() {
        // given
        List<OrderItemRequest> items = new ArrayList<>();
        items.add(new OrderItemRequest(1L, new BigDecimal("19.99"), 2));

        OrderRequest request = OrderRequest.builder()
                .customerId("123456789")
                .deliveryAddress("풍세로 801-23")
                .deliveryMethod(DeliveryMethod.NORMAL)
                .expectedDeliveryDate(LocalDate.now().plusDays(5))
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .specialRequest("내일 아침에 일찍 일어나야 해서 6시에 도착하셔서 벨 눌러주세요.")
                .items(items)
                .build();

        int initialCount = orderRepository.findAll().size();

        // when
        Order order = orderService.createOrder(request);

        // then
        assertNotNull(order.getOrderId());
        assertEquals(initialCount + 1, orderRepository.findAll().size());
        assertEquals("123456789", order.getCustomerId());
        assertEquals("풍세로 801-23", order.getDeliveryAddress());
        assertEquals(DeliveryMethod.NORMAL, order.getDeliveryMethod());
        assertEquals(PaymentMethod.CREDIT_CARD, order.getPaymentMethod());
        assertEquals("내일 아침에 일찍 일어나야 해서 6시에 도착하셔서 벨 눌러주세요.", order.getSpecialRequest());
        assertEquals(1L, order.getItems().size());

    }

    @DisplayName("주문 생성 실패 테스트 - 주문 아이템이 없는 경우")
    @Test
    void test2() {
        // given
        OrderRequest request = OrderRequest.builder()
                .customerId("12345")
                .deliveryAddress("서울시 강남구")
                .deliveryMethod(DeliveryMethod.FAST)
                .expectedDeliveryDate(LocalDate.now().plusDays(3))
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .items(new ArrayList<>())  // 빈 아이템 리스트
                .build();

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(request));
        assertTrue(exception.getMessage().contains("주문 생성을 위해 최소 1개 이상의 상품 정보가 필요합니다."));
    }

    @DisplayName("주문 생성 실패 테스트 - 최대 아이템 수 초과")
    @Test
    void test3() {
        // given
        List<OrderItemRequest> items = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            items.add(new OrderItemRequest((long) i, new BigDecimal("15.00"), 1));
        }

        OrderRequest request = OrderRequest.builder()
                .customerId("67890")
                .deliveryAddress("서울시 마포구")
                .deliveryMethod(DeliveryMethod.SUPER_FAST)
                .expectedDeliveryDate(LocalDate.now().plusDays(1))
                .paymentMethod(PaymentMethod.DEBIT_CARD)
                .items(items)
                .build();

        // when & then
        Exception exception = assertThrows(IllegalStateException.class, () -> orderService.createOrder(request));
        assertTrue(exception.getMessage().contains("주문을 위한 상품 목록은 10개를 초과할 수 없습니다."));
    }


}
