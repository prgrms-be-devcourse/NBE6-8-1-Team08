package com.gridsandcircles.domain.order.order.controller;

import com.gridsandcircles.domain.order.order.dto.OrderCreateReqBody;
import com.gridsandcircles.domain.order.order.dto.OrderDto;
import com.gridsandcircles.domain.order.order.service.OrderService;
import com.gridsandcircles.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{num}")
    public RsData<OrderDto> createOrder(
            @PathVariable int num,
            @Valid @RequestBody OrderCreateReqBody reqBody
    ){
        OrderDto orderDto = orderService.registerOrder(num, reqBody);
        return new RsData<>("200-1", "주문이 성공적으로 등록되었습니다.", orderDto);
    }
}