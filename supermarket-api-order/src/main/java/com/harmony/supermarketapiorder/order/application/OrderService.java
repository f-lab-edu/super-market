package com.harmony.supermarketapiorder.order.application;

import com.harmony.supermarketapiorder.order.domain.Order;
import com.harmony.supermarketapiorder.order.domain.OrderItem;
import com.harmony.supermarketapiorder.order.domain.OrderRepository;
import com.harmony.supermarketapiorder.order.domain.OrderRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    @Value("${external-api.product-service}")
    private String productServiceUrl;

    @Value("${external-api.payment-service}")
    private String paymentServiceUrl;

    @Value("${external-api.delivery-service}")
    private String deliveryServiceUrl;

    // TODO 1. 주문 정보 저장
    @Transactional
    public Order createOrder(OrderRequest request) {
        // TODO 1. 각 요청 실패시 처리 구현 필요

        // restTemplate.postForObject(productServiceUrl, request, Void.class); // TODO. 각 서비스 호출을 위한 DTO로 변경
        // restTemplate.postForObject(paymentServiceUrl, request, Void.class); // TODO. 공통 모듈을 통한 응답 처리

        validateOrderRequest(request);
        Order order = Order.from(request);

        request.getItems().stream()
                .map(OrderItem::from)
                .forEach(order::addItem);

        // TODO. 레빗 엠큐를 이용한 배송 등록 처리

        return orderRepository.save(order);
    }

    public OrderDto findOrderById(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        return new OrderDto(order.getOrderId(), order.getCustomerId(), order.getOrderDate(), order.getStatus(), order.getDeliveryAddress(), order.getDeliveryMethod(), order.getExpectedDeliveryDate(), order.getPaymentMethod(), order.getSpecialRequest());
    }

    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
        
        order.cancel();
        orderRepository.save(order);

    }

    private void validateOrderRequest(OrderRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request는 null이 될 수 없습니다.");
        }
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("주문 생성을 위해 최소 1개 이상의 상품 정보가 필요합니다.");
        }
        if (request.getItems().size() > Order.MAX_ITEMS) {
            throw new IllegalStateException("주문을 위한 상품 목록은 " + Order.MAX_ITEMS + "개를 초과할 수 없습니다.");
        }
    }

}
