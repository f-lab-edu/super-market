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

}
