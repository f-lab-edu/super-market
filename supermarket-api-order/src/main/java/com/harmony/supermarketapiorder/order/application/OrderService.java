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
    private final OrderItemRepository orderItemRepository;

    // TODO 1. 주문 정보 저장
    @Transactional
    public Order createOrder(OrderRequest request) {
        Order order = Order.from(request);
        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = request.getItems().stream()
                .map(itemRequest -> {
                    itemRequest.setOrderId(savedOrder.getOrderId());
                    return OrderItem.from(itemRequest);
                })
                .collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);
        orderItems.forEach(savedOrder::addItem);

        savedOrder.calculateTotalPrice();
        return orderRepository.save(savedOrder);
    }

    // TODO 2. 특정 주문의 정보를 조회하는 기능
    public void findOrderById(){

    }

    // TODO 3. 주문을 취소하는 기능
    public void cancelOrder(){

    }

}
