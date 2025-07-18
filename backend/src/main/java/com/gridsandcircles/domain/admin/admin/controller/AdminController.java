package com.gridsandcircles.domain.admin.admin.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.gridsandcircles.domain.admin.admin.dto.AdminSignupRequestDto;
import com.gridsandcircles.domain.admin.admin.dto.AdminSignupResponseDto;
import com.gridsandcircles.domain.admin.admin.mapper.AdminMapper;
import com.gridsandcircles.domain.admin.admin.service.AdminService;
import com.gridsandcircles.domain.order.order.dto.OrderCancelRequestDto;
import com.gridsandcircles.domain.order.order.dto.OrderCancelResponseDto;
import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.mapper.OrderMapper;
import com.gridsandcircles.domain.order.order.dto.OrderResponseDto;
import com.gridsandcircles.domain.order.order.service.OrderService;
import com.gridsandcircles.global.ResultResponse;
import com.gridsandcircles.global.ServiceException;
import com.gridsandcircles.global.swagger.BadRequestApiResponse;
import com.gridsandcircles.global.swagger.ConflictApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@Tag(name = "AdminController", description = "관리자 API")
public class AdminController {

  private final OrderService orderService;

  private final AdminService adminService;

  @PostMapping("/signup")
  @Operation(summary = "회원 가입")
  @ApiResponse(
      responseCode = "201",
      description = "회원 가입 성공",
      content = @Content(
          mediaType = APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = ResultResponse.class),
          examples = @ExampleObject(value = """
              {
                "msg": "Sign up successful",
                "data": {
                  "adminId": "test"
                }
              }
              """
          )
      )
  )
  @BadRequestApiResponse
  @ConflictApiResponse
  public ResponseEntity<ResultResponse<AdminSignupResponseDto>> signup(
      @Valid @RequestBody AdminSignupRequestDto adminSignupRequestDto
  ) {
    if (!adminSignupRequestDto.inputPassword().equals(adminSignupRequestDto.confirmPassword())) {
      throw new ServiceException(BAD_REQUEST, "Password does not match");
    }

    AdminSignupResponseDto adminSignupResponseDto = AdminMapper.toDto(
        adminService.createAdmin(adminSignupRequestDto.adminId(),
            adminSignupRequestDto.inputPassword()));

    return ResponseEntity.status(CREATED)
        .body(new ResultResponse<>("Sign up successful", adminSignupResponseDto));
  }

  @GetMapping("/orders")
  @Operation(summary = "전체 주문 내역 조회")
  @ApiResponse(
      responseCode = "200",
      description = "전체 주문 내역 조회 성공",
      content = @Content(
          mediaType = APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = ResultResponse.class)
      )
  )
  public ResponseEntity<ResultResponse<List<OrderResponseDto>>> getOrders() {
    List<OrderResponseDto> orderDtos = orderService.getOrders()
        .stream()
        .map(OrderMapper::toResponseDto)
        .toList();

    return ResponseEntity.ok()
        .body(new ResultResponse<>("Get all orders successful", orderDtos));
  }

  @PatchMapping("/orders/cancel")
  @Operation(summary = "주문 취소")
  @ApiResponse(
          responseCode = "200",
          description = "email 단위로 주문 취소 성공",
          content = @Content(
                  mediaType = APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ResultResponse.class),
                  examples = @ExampleObject(value = """
              {
                "msg": "Cancel order successful",
                "email" : "test"
              }
              """
                  )
          )
  )
  public ResponseEntity<ResultResponse<OrderCancelResponseDto>> cancelOrder(
          @RequestBody OrderCancelRequestDto orderCancelRequestDto
  ) {
    List<Order> orders = orderService.cancelAllOrdersByEmail(orderCancelRequestDto.email());

    OrderCancelResponseDto responseDto = OrderMapper.toCancelResponseDto(orders.get(0));

    return ResponseEntity.ok()
            .body(new ResultResponse<>("Cancel order successful", responseDto));
  }

  @PatchMapping("/orders/cancel-detail")
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