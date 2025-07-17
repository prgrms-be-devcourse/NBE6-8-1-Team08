package com.gridsandcircles.domain.order.order.controller;

import com.gridsandcircles.domain.order.order.dto.OrderDto;
import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.service.OrderService;
import com.gridsandcircles.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    //브라우저로 작동확인
    @GetMapping("/{orderId}/items/{orderItemId}")
    @Transactional
    public ResponseEntity<RsData<Map<String, Integer>>> removeOrderItem(@PathVariable int orderId, @PathVariable int orderItemId) {
        orderService.removeOrderItem(orderId, orderItemId);
        return ResponseEntity.ok(
                RsData.success("주문 항목 삭제 성공", Map.of("orderId", orderId,"orderItemId", orderItemId))
        );
    }
}