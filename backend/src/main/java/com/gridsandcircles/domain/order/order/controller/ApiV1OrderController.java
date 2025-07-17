package com.gridsandcircles.domain.order.order.controller;

import com.gridsandcircles.domain.order.order.dto.OrderResponseDto;
import com.gridsandcircles.domain.order.order.mapper.OrderMapper;
import com.gridsandcircles.domain.order.order.service.OrderService;
import com.gridsandcircles.global.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class ApiV1OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ResultResponse<List<OrderResponseDto>>> getOrders() {
        List<OrderResponseDto> items = orderService.findAll()
                .stream()
                .map(OrderMapper::toResponseDto)
                .toList();

        return ResponseEntity.ok().body(new ResultResponse<>("Get order successful", items));
    }
}