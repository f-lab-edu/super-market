package com.harmony.supermarketapiorder.order.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`order`")
@Getter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    // TODO 1. 요구사항 확인해서 주문 도메인에 들어갈 필드 정의하기


    // TODO 2. 주문 생성
    public void createOrder() {

    }

    // TODO 3. 주문 취소
    public void cancelOrder() {

    }

    // TODO 4. 특정 주문 정보 조회
    public void getOrderDetails() {

    }
}
