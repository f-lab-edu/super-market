package com.harmony.supermarketapiorder.order.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    // TODO 1. 전달받은 상품 내역을 가지고 실제로 주문하는 기능
    @PostMapping
    public void order(){

    }


    // TODO 2. 특정 주문의 정보를 조회하는 기능
    @GetMapping("/{orderId}")
    public void getOrder(){

    }

    // TODO 3. 주문을 취소하는 기능
    @DeleteMapping("/{orderId}")
    public void cancelOrder(){

    }


}
