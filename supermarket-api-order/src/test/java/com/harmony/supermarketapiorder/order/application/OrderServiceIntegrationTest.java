package com.harmony.supermarketapiorder.order.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harmony.supermarketapiorder.order.domain.OrderItemRequest;
import com.harmony.supermarketapiorder.order.domain.OrderRepository;
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
import java.util.Collections;

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

}
