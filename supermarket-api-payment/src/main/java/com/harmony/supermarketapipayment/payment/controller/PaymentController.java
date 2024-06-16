package com.harmony.supermarketapipayment.payment.controller;


import common.payment.request.PaymentRequest;
import common.payment.response.PaymentResponse;
import common.payment.response.ResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @PostMapping("/payment")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest request) {

        return ResponseEntity.ok(new PaymentResponse(ResponseStatus.SUCCESS.name(), "Payment processed successfully"));
    }

}