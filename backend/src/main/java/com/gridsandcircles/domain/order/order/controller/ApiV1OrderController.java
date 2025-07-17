package com.gridsandcircles.domain.order.order.controller;

import com.gridsandcircles.domain.order.order.dto.OrderDto;
import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.service.OrderService;
import com.gridsandcircles.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class ApiV1OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getOrders() {
        List<Order> items = orderService.findAll();
        return items
                .stream()
                .map(OrderDto::new)
                .toList();
    }
//브라우저로 작동확인
    @DeleteMapping("/{id}")
    @Transactional
    public RsData<Void> delete(@PathVariable int id) {
        Order order = orderService.findById(id).get();

        orderService.deleteOrder(order);

        return new RsData<>(
                "%d번 주문이 삭제되었습니다.".formatted(id),
                null
        );
    }
}