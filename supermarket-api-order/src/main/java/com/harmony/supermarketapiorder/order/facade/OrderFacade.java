package com.harmony.supermarketapiorder.order.facade;

import com.harmony.supermarketapiorder.order.application.OrderService;
import com.harmony.supermarketapiorder.order.domain.OrderRequest;
import com.harmony.supermarketapiorder.order.facade.dtos.StockDecreaseDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderFacade {
    private final OrderService orderService;
    private final RestTemplate restTemplate;

    public void processOrder(OrderRequest orderRequest, List<StockDecreaseDto> stockDecreaseDtos){
        // TODO 1. 재고 감소 (상품 도메인에 요청)
        String url = "JNDI 이용해 적용토록";
        restTemplate.patchForObject(url, stockDecreaseDtos, List.class);

        // -> 재고 감소 실패시 실패처리토록

        // TODO 2. 결제 요청 (결제 도메인에 요청)
        url = "JNDI 이용해 적용토록";
        restTemplate.patchForObject(url, stockDecreaseDtos, List.class);

        // TODO 3. 주문 저장
        orderService.createOrder(orderRequest);


        // TODO 4. 배송 요청 (배송 도메인에 요청)
        // RabbitMQ 사용해 비동기 처리 계획

    }

    public void processOrderCancel(Long orderId) {
        // TODO 1. 주문 상태 취소로 변경
        orderService.cancelOrder(orderId);

        // TODO 2. 재고 서비스에 재고 복원 요청

        // TODO 3. 결제 서비스에 결제 취소 요청

        // TODO 4. 배송 서비스에 배송 취소 요청
    }
}
