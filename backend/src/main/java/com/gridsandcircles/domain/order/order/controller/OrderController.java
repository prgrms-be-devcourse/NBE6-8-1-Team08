package com.gridsandcircles.domain.order.order.controller;

import com.gridsandcircles.domain.order.order.dto.CancelOrderRequestDto;
import com.gridsandcircles.domain.order.order.dto.CancelOrderResponseDto;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

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
                        Map.of("orderId", orderId, "orderItemId", orderItemId)
                )
        );
    }

    @PatchMapping("/cancel")
    @Operation(summary = "주문 취소, by orderItem")
    @ApiResponse(
            responseCode = "200",
            description = "product 단위로 주문 취소 성공",
            content = @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ResultResponse.class),
                    examples = @ExampleObject(value = """
              {
                "msg": "Cancel orderItem successful"
              }
              """
                    )
            )
    )
    public ResponseEntity<ResultResponse<List<CancelOrderResponseDto>>> cancelOrderDetail(
            @RequestBody CancelOrderRequestDto cancelOrderRequestDto
    ) {
        List<Order> orders = orderService.getOrders();

        Integer orderId = orderService.findOrderIdByEmail(cancelOrderRequestDto.userId(), orders)
                .orElseThrow(() -> new RuntimeException("주문이 존재하지 않습니다."));;

        List<Integer> orderItemIds = cancelOrderRequestDto.products().stream()
                .map(CancelOrderRequestDto.ProductRequestDto::productId)
                .map(Integer::valueOf)
                .toList();

        List<CancelOrderResponseDto> cancelResults = orderService.cancelDetails(orderId, orderItemIds);

        ResultResponse<List<CancelOrderResponseDto>> result =
                new ResultResponse<>("Cancel orderItem successful", cancelResults);

        return ResponseEntity.ok().body(result);
    }
}
