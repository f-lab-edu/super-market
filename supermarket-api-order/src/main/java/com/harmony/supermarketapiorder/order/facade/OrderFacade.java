package com.harmony.supermarketapiorder.order.facade;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderFacade {


    public void processOrder(){
        // TODO 1. 재고 감소 (상품 도메인에 요청)

        // TODO 2. 결제 요청 (결제 도메인에 요청)

        // TODO 3. 주문 저장

        // TODO 4. 배송 요청

    }

}
