package com.harmony.supermarketapiorder.order.controller;


import com.harmony.supermarketapiorder.order.application.OrderCreateRequest;
import com.harmony.supermarketapiorder.order.application.OrderResponseDto;
import com.harmony.supermarketapiorder.order.application.OrderSuccessDto;
import com.harmony.supermarketapiorder.order.application.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> order(@RequestBody OrderCreateRequest orderCreateRequest){
        OrderResponseDto response = orderService.createOrder(orderCreateRequest.toOrderRequest());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long orderId){
        OrderResponseDto response = orderService.findOrderById(orderId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }
}