package com.harmony.supermarketapiorder.order.domain;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`orders`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String customerId;
    private LocalDateTime orderDate;
    private String status;
    private String deliveryAddress;
    private String deliveryMethod;
    private LocalDate expectedDeliveryDate;
    private String paymentMethod;
    private String specialRequest;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    public static final int MAX_ITEMS = 10;

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public static Order from(OrderRequest request) {
        validateRequest(request);

        return Order.builder()
                .customerId(request.getCustomerId())
                .orderDate(LocalDateTime.now())
                .status("pending")
                .deliveryAddress(request.getDeliveryAddress())
                .deliveryMethod(request.getDeliveryMethod())
                .expectedDeliveryDate(request.getExpectedDeliveryDate())
                .paymentMethod(request.getPaymentMethod())
                .specialRequest(request.getSpecialRequest())
                .build();
    }

    // TODO 3. 주문 취소
    public void cancel() {
        this.status = "cancelled";
    }

    private static void validateRequest(OrderRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("주문 요청이 빈 상태로 넘어왔습니다.");
        }
        if (StringUtils.isBlank(request.getCustomerId()) || !request.getCustomerId().matches("\\d{1,10}")) {
            throw new IllegalArgumentException("고객 ID는 1~10 사이의 정수형으로 구성되야 합니다.");
        }
        if (StringUtils.isBlank(request.getDeliveryAddress()) || StringUtils.isBlank(request.getDeliveryMethod()) || StringUtils.isBlank(request.getPaymentMethod())) {
            throw new IllegalArgumentException("배송주소, 배송 수단, 지불 수단중에 비어있는 값이 있습니다.");
        }
        if (request.getExpectedDeliveryDate() == null || !request.getExpectedDeliveryDate().isAfter(LocalDate.now().plusDays(1)) || !request.getExpectedDeliveryDate().isBefore(LocalDate.now().plusDays(7))) {
            throw new IllegalArgumentException("예상 배송일은 배송 요청 당일로부터 최소 1일에서 7일 사이로 입력되야 합니다.");
        }
    }

}