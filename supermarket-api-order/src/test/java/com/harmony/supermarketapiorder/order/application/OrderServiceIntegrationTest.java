package com.harmony.supermarketapiorder.order.application;

import com.harmony.supermarketapiorder.order.domain.Order;
import com.harmony.supermarketapiorder.order.domain.OrderItemRequest;
import com.harmony.supermarketapiorder.order.domain.OrderRepository;
import com.harmony.supermarketapiorder.order.domain.OrderRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;



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
                .customerId("최찬혁")
                .deliveryAddress("풍세로 801-23")
                .deliveryMethod("로켓배송")
                .expectedDeliveryDate(LocalDate.now().plusDays(5))
                .paymentMethod("신용카드")
                .specialRequest("내일 아침에 일찍 일어나야 해서 6시에 도착하셔서 벨 눌러주세요.")
                .items(items)
                .build();

        int initialCount = orderRepository.findAll().size();

        // when
        Order order = orderService.createOrder(request);

        // then
        assertNotNull(order.getOrderId());
        assertEquals(initialCount + 1, orderRepository.findAll().size());
        assertEquals("최찬혁", order.getCustomerId());
        assertEquals("풍세로 801-23", order.getDeliveryAddress());
        assertEquals("로켓배송", order.getDeliveryMethod());
        assertEquals("신용카드", order.getPaymentMethod());
        assertEquals("내일 아침에 일찍 일어나야 해서 6시에 도착하셔서 벨 눌러주세요.", order.getSpecialRequest());
        assertEquals(1L, order.getItems().size());

    }
}
