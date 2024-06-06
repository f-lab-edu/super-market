package com.harmony.supermarketapiorder.order.controller;


import com.harmony.supermarketapiorder.order.application.OrderCreateRequest;
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
    public ResponseEntity<Void> order(@RequestBody OrderCreateRequest orderCreateRequest){
        orderService.createOrder(orderCreateRequest.toOrderRequest());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Void> getOrder(@PathVariable Long orderId){
        orderService.findOrderById(orderId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }
}