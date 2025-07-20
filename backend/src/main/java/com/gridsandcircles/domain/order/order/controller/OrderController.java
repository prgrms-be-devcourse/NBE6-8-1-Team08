package com.gridsandcircles.domain.order.order.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.gridsandcircles.domain.order.order.dto.OrderCancelRequestDto;
import com.gridsandcircles.domain.order.order.dto.OrderCancelResponseDto;
import com.gridsandcircles.domain.order.order.dto.OrderRequestDto;
import com.gridsandcircles.domain.order.order.dto.OrderResponseDto;
import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.mapper.OrderMapper;
import com.gridsandcircles.domain.order.order.service.OrderService;
import com.gridsandcircles.global.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
@Tag(name = "OrderController", description = "고객 API")
public class OrderController {

  private final OrderService orderService;

  @GetMapping
  @Operation(summary = "전체 주문 목록 조회")
  public ResponseEntity<ResultResponse<List<OrderResponseDto>>> getOrders() {
    List<OrderResponseDto> items = orderService.getOrders()
        .stream()
        .map(OrderMapper::toResponseDto)
        .toList();

    return ResponseEntity.ok().body(new ResultResponse<>("Get order successful", items));
  }

  @PostMapping("/user/order")
  @Operation(summary = "고객 주문 등록")
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "주문 등록 성공",
          content = @Content(
              mediaType = "application/json",
              examples = @ExampleObject(value = """
                  {
                    "msg": "Order created successfully",
                    "data": {
                      "orderId": 10,
                      "email": "user@example.com",
                      "address": "서울시 강남구 테헤란로 123",
                      "createdAt": "2024-07-19T17:01:12",
                      "orderStatus": true,
                      "deliveryStatus": false,
                      "orderItems": [
                        {
                          "orderItemId": 20,
                          "productId": 1,
                          "productName": "아메리카노",
                          "orderCount": 2,
                          "orderItemStatus": true
                        },
                        {
                          "orderItemId": 21,
                          "productId": 3,
                          "productName": "카페라떼",
                          "orderCount": 1,
                          "orderItemStatus": true
                        }
                      ]
                    }
                  }
                  """)
          )
      ),
      @ApiResponse(
          responseCode = "400",
          description = "입력값 오류",
          content = @Content(
              mediaType = "application/json",
              examples = {
                  @ExampleObject(name = "이메일 누락", value = "{\"msg\": \"이메일은 필수입니다.\"}"),
                  @ExampleObject(name = "주소 누락", value = "{\"msg\": \"주소는 필수입니다.\"}"),
                  @ExampleObject(name = "주문수량 0 이하", value = "{\"msg\": \"주문 수량은 1개 이상이어야 합니다.\"}")
              }
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "상품 ID가 존재하지 않음",
          content = @Content(
              mediaType = "application/json",
              examples = @ExampleObject(value = "{\"msg\": \"상품을 찾을 수 없습니다.\"}")
          )
      )
  })
  public ResponseEntity<ResultResponse<OrderResponseDto>> createOrder(
      @Valid @RequestBody OrderRequestDto requestDto
  ) {
    OrderResponseDto responseDto = orderService.createOrder(requestDto);
    return ResponseEntity.ok(new ResultResponse<>("Order created successfully", responseDto));
  }

  @GetMapping("/user/findorder")
  @Operation(
      summary = "고객 주문 조회"
  )
  @Parameter(
      name = "email",
      description = "고객 이메일",
      required = true,
      example = "user@example.com"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "주문 조회 성공",
          content = @Content(
              mediaType = "application/json",
              examples = @ExampleObject(value = """
                  {
                    "msg": "Get orders by email successful",
                    "data": [
                      {
                        "orderId": 10,
                        "email": "user@example.com",
                        "address": "서울시 강남구 테헤란로 123",
                        "createdAt": "2024-07-19T17:01:12",
                        "orderStatus": true,
                        "deliveryStatus": false,
                        "orderItems": [
                          {
                            "orderItemId": 20,
                            "productId": 1,
                            "productName": "아메리카노",
                            "orderCount": 2,
                            "orderItemStatus": true
                          }
                        ]
                      }
                    ]
                  }
                  """)
          )
      ),
      @ApiResponse(
          responseCode = "400",
          description = "입력값 오류",
          content = @Content(
              mediaType = "application/json",
              examples = @ExampleObject(value = "{\"msg\": \"이메일은 필수입니다.\"}")
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "주문 내역에 해당 이메일이 없음",
          content = @Content(
              mediaType = "application/json",
              examples = @ExampleObject(value = "{\"msg\": \"해당 이메일로 주문 내역을 찾을 수 없습니다.\"}")
          )
      )
  })
  public ResponseEntity<ResultResponse<List<OrderResponseDto>>> getOrdersByEmail(
      @NotBlank(message = "이메일은 필수입니다.") @RequestParam String email) {
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
      @Valid @RequestBody OrderCancelRequestDto orderCancelRequestDto
  ) {
    List<String> productNames = orderCancelRequestDto.products().stream()
        .map(OrderCancelRequestDto.ProductRequestDto::productName)
        .toList();

    List<Order> orders = orderService.cancelOrderItemsByEmailAndProductNames(
        orderCancelRequestDto.email(), productNames);

    OrderCancelResponseDto responseDto = OrderMapper.toCancelResponseDto(orders.get(0));

    return ResponseEntity.ok()
        .body(new ResultResponse<>("Cancel product successful", responseDto));
  }
}