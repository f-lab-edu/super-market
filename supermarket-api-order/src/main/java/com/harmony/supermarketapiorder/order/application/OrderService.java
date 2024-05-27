package com.harmony.supermarketapiorder.order.application;

import com.harmony.supermarketapiorder.order.domain.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    // TODO 1. 주문 정보 저장
    @Transactional
    public Order createOrder(OrderRequest request) {
        validateOrderRequest(request);
        Order order = Order.from(request);

        request.getItems().stream()
                .map(OrderItem::from)
                .forEach(order::addItem);

        return orderRepository.save(order);
    }

    // TODO 2. 특정 주문의 정보를 조회하는 기능
    public void findOrderById(){

    }

    // TODO 3. 주문을 취소하는 기능
    public void cancelOrder(){

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
