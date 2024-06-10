package com.harmony.supermarketapiorder.order.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harmony.supermarketapiorder.order.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class OrderServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setup() {
        orderRepository.deleteAll();
    }

    @DisplayName("주문 생성 성공 통합 테스트")
    @Test
    void test1() throws Exception {
        // given
        OrderCreateRequest request = OrderCreateRequest.builder()
                .customerId("123456789")
                .deliveryAddress("풍세로 801-23")
                .deliveryMethod("NORMAL")
                .expectedDeliveryDate(LocalDate.now().plusDays(5))
                .paymentMethod("CREDIT_CARD")
                .specialRequest("내일 아침에 일찍 일어나야 해서 6시에 도착하셔서 벨 눌러주세요.")
                .items(Collections.singletonList(new OrderItemRequest(1L, new BigDecimal("19.99"), 2)))
                .build();

        // when & then
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("123456789"))
                .andExpect(jsonPath("$.deliveryAddress").value("풍세로 801-23"))
                .andExpect(jsonPath("$.deliveryMethod").value("NORMAL"))
                .andExpect(jsonPath("$.paymentMethod").value("CREDIT_CARD"))
                .andExpect(jsonPath("$.specialRequest").value("내일 아침에 일찍 일어나야 해서 6시에 도착하셔서 벨 눌러주세요."))
                .andDo(print());
    }

    @DisplayName("주문 생성 실패 테스트 - 최대 아이템 수 초과")
    @Test
    void test2() throws Exception {
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
        mockMvc.perform(post("/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(jsonPath("$.message").value("주문을 위한 상품 목록은 10개를 초과할 수 없습니다."))
                .andDo(print());
    }

    @DisplayName("주문 생성 실패 테스트 - 주문 아이템이 없는 경우")
    @Test
    void test3() throws Exception {
        // given
        OrderCreateRequest request = OrderCreateRequest.builder()
                .customerId("12345")
                .deliveryAddress("서울시 강남구")
                .deliveryMethod("FAST")
                .expectedDeliveryDate(LocalDate.now().plusDays(3))
                .paymentMethod("CREDIT_CARD")
                .items(new ArrayList<>())  // empty item list
                .build();

        // when & then
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(jsonPath("$.message").value("주문 생성을 위해 최소 1개 이상의 상품 정보가 필요합니다."))
                .andDo(print());
    }
}
