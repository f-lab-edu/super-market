package com.harmony.supermarketapiorder.order.application;

import com.harmony.supermarketapiorder.order.domain.Order;
import com.harmony.supermarketapiorder.order.domain.OrderItem;
import com.harmony.supermarketapiorder.order.domain.OrderRepository;
import com.harmony.supermarketapiorder.order.domain.OrderRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    // private final RestTemplate restTemplate; // 별도 모듈 관련 도커 환경 구축 이후 활성화 계획


    // TODO 1. 주문 정보 저장
    @Transactional
    public OrderResponseDto createOrder(OrderRequest request) {
        // TODO 1. 각 요청 실패시 처리 구현 필요

        // restTemplate.postForObject(productServiceUrl, request, Void.class); // TODO. 각 서비스 호출을 위한 DTO로 변경
        // restTemplate.postForObject(paymentServiceUrl, request, Void.class); // TODO. 공통 모듈을 통한 응답 처리

        String validateResult = validateOrderRequest(request);
        if(validateOrderRequest(request) != "isOk"){
            log.error("createOrder request exception, validateOrderRequest: " + validateResult);
            return new OrderFailDto(validateResult);
        }

        Order newOrder = new Order(request);

        request.getItems().stream()
                .map(OrderItem::from)
                .forEach(newOrder::addItem);

        Order createdOrder = orderRepository.save(newOrder);

        // TODO. 레빗 엠큐를 이용한 배송 등록 요청

        return new OrderSuccessDto(createdOrder);

    }

    public OrderResponseDto findOrderById(Long orderId){
        Order order = null;

        try {
            order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
            log.error("findOrderById exception, orderRepository.findById: " + validateResult);
        } catch (IllegalArgumentException e) {
            return new OrderFailDto(e.getMessage());
        }

        return new OrderSuccessDto(order);
    }

    @Transactional
    public void cancelOrder(Long orderId){
        // TODO 1. findOrderById()를 여기서 재사용하기 위해선 findOrderById에서 Dto가 아닌 order 객체를 내려줘야함
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        order.cancel();
        orderRepository.save(order);

    }

    private String validateOrderRequest(OrderRequest request) {
        if (request == null) {
            return "request는 null이 될 수 없습니다.";
        }
        if (request.getItems() == null || request.getItems().isEmpty()) {
            return "주문 생성을 위해 최소 1개 이상의 상품 정보가 필요합니다.";
        }
        if (request.getItems().size() > Order.MAX_ITEMS) {
            return "주문을 위한 상품 목록은 " + Order.MAX_ITEMS + "개를 초과할 수 없습니다.";
        }

        return "isOk";
    }

}
