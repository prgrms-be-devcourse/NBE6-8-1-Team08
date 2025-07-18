package com.gridsandcircles.domain.order.order.controller;

import com.gridsandcircles.domain.order.order.dto.OrderCancelRequestDto;
import com.gridsandcircles.domain.order.order.dto.OrderCancelResponseDto;
import com.gridsandcircles.domain.order.order.dto.OrderRequestDto;
import com.gridsandcircles.domain.order.order.dto.OrderResponseDto;
import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.mapper.OrderMapper;
import com.gridsandcircles.domain.order.order.service.OrderService;
import com.gridsandcircles.global.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    @PostMapping("/{num}")
    public ResponseEntity<ResultResponse<OrderResponseDto>> createOrder(
            @PathVariable int num,
            @RequestBody OrderRequestDto requestDto
    ) {
        OrderResponseDto responseDto = orderService.createOrder(requestDto);
        return ResponseEntity.ok(new ResultResponse<>("Order created successfully", responseDto));
    }

    @GetMapping(params = "email")
    public ResponseEntity<ResultResponse<List<OrderResponseDto>>> getOrdersByEmail(@RequestParam String email) {
        List<OrderResponseDto> orders = orderService.getOrdersByEmail(email)
                .stream()
                .map(OrderMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(new ResultResponse<>("Get orders by email successful", orders));
    }

    @PatchMapping("/cancel-detail")
    @Operation(summary = "상품 취소")
    @ApiResponse(
            responseCode = "200",
            description = "product 단위로 주문 취소 성공",
            content = @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ResultResponse.class),
                    examples = @ExampleObject(value = """
              {
                "msg": "Cancel orderItem successful",
                "email" : "test"
              }
              """
                    )
            )
    )
    public ResponseEntity<ResultResponse<OrderCancelResponseDto>> cancelOrderDetail(
            @RequestBody OrderCancelRequestDto orderCancelRequestDto
    ) {
        List<Integer> productIds = orderCancelRequestDto.products().stream()
                .map(OrderCancelRequestDto.ProductRequestDto::productId)
                .toList();

        List<Order> orders = orderService.cancelOrderItemsByEmailAndProductIds(orderCancelRequestDto.email(), productIds);

        OrderCancelResponseDto responseDto = OrderMapper.toCancelResponseDto(orders.get(0));

        return ResponseEntity.ok()
                .body(new ResultResponse<>("Cancel product successful", responseDto));
    }
}