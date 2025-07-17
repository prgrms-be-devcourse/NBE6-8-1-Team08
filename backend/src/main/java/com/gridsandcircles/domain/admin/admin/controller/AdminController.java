package com.gridsandcircles.domain.admin.admin.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.gridsandcircles.domain.admin.admin.mapper.AdminMapper;
import com.gridsandcircles.domain.admin.admin.dto.AdminRequestDto;
import com.gridsandcircles.domain.admin.admin.dto.AdminResponseDto;
import com.gridsandcircles.domain.admin.admin.service.AdminService;
import com.gridsandcircles.domain.order.order.dto.OrderDto;
import com.gridsandcircles.domain.order.order.mapper.OrderMapper;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@Tag(name = "AdminController", description = "관리자 API")
public class AdminController {

  private final AdminService adminService;
  private final OrderService orderService;

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
  public ResponseEntity<ResultResponse<AdminResponseDto>> signup(
      @Valid @RequestBody AdminRequestDto adminRequestDto
  ) {
    if (!adminRequestDto.inputPassword().equals(adminRequestDto.confirmPassword())) {
      throw new ServiceException(BAD_REQUEST, "Password does not match");
    }

    AdminResponseDto adminResponseDto = AdminMapper.toDto(
        adminService.createAdmin(adminRequestDto.adminId(), adminRequestDto.inputPassword()));

    return ResponseEntity.status(CREATED)
        .body(new ResultResponse<>("Sign up successful", adminResponseDto));
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
  public ResponseEntity<ResultResponse<List<OrderDto>>> getOrders() {
    List<OrderDto> orderDtos = orderService.findAll()
        .stream()
        .map(OrderMapper::toDto)
        .toList();

    return ResponseEntity.ok()
        .body(new ResultResponse<>("전체 주문 내역 조회 성공", orderDtos));
  }
}