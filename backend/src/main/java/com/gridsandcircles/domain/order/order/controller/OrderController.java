package com.gridsandcircles.domain.order.order.controller;

import com.gridsandcircles.domain.order.order.dto.OrderResponseDto;
import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.mapper.OrderMapper;
import com.gridsandcircles.domain.order.order.service.OrderService;
import com.gridsandcircles.global.ResultResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
@Tag(name = "OrderController", description = "고객 API")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ResultResponse<List<OrderResponseDto>>> getOrders() {
        List<OrderResponseDto> items = orderService.getOrders()
                .stream()
                .map(OrderMapper::toResponseDto)
                .toList();

        return ResponseEntity.ok().body(new ResultResponse<>("Get order successful", items));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ResultResponse<Void>> delete(@PathVariable int id) {
        Order order = orderService.getOrder(id);

        orderService.deleteOrder(order);

        ResultResponse<Void> response = new ResultResponse<>(
                "Order #%d has been deleted".formatted(id)
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{orderId}/items/{orderItemId}")
    public ResponseEntity<ResultResponse<Map<String, Integer>>> removeOrderItem(
            @PathVariable int orderId,
            @PathVariable int orderItemId
    ) {
        orderService.deleteOrderItem(orderId, orderItemId);

        return ResponseEntity.ok().body(
                new ResultResponse<>(
                        "Delete orderItem successful",
                        Map.of("orderId", orderId,"orderItemId", orderItemId)
                )
        );
    }
}