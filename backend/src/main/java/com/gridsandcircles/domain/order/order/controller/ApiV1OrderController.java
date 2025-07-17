package com.gridsandcircles.domain.order.order.controller;

import com.gridsandcircles.domain.order.order.dto.OrderCreateReqBody;
import com.gridsandcircles.domain.order.order.dto.OrderDto;
import com.gridsandcircles.domain.order.order.dto.OrderResponseDto;
import com.gridsandcircles.domain.order.order.mapper.OrderMapper;
import com.gridsandcircles.domain.order.order.service.OrderService;
import com.gridsandcircles.global.ResultResponse;
import com.gridsandcircles.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    // 유석님 코드
    @PostMapping("/{num}")
    public RsData<OrderDto> createOrder(
            @PathVariable int num,
            @Valid @RequestBody OrderCreateReqBody reqBody
    ){
        OrderDto orderDto = orderService.registerOrder(num, reqBody);
        return new RsData<>("201-1", "주문이 성공적으로 등록되었습니다.", orderDto);
    }
}

