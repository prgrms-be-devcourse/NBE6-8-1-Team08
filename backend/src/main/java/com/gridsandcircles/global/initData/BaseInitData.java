package com.gridsandcircles.global.initData;

import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.service.OrderService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
@Profile("!test") //테스트 환경

public class BaseInitData {
    private final OrderService orderService;

    @PostConstruct
    public void init() {
        System.out.println("BaseInitData 초기 데이터 입력");

        Order order1 = Order.builder()
                .email("order1@example.com")
                .address("서울")
                .orderStatus(true)
                .deliveryStatus(false)
                .build();

        Order order2 = Order.builder()
                .email("order2@example.com")
                .address("부산")
                .orderStatus(false)
                .deliveryStatus(false)
                .build();

        orderService.createOrder(order1);
        orderService.createOrder(order2);

        System.out.println("Order 엔티티 데이터 초기화");
    }
}





