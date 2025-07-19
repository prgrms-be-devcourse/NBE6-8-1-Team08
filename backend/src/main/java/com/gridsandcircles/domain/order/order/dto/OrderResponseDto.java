package com.gridsandcircles.domain.order.order.dto;

import com.gridsandcircles.domain.order.orderitem.dto.OrderItemResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
    Integer orderId,
    String email,
    String address,
    LocalDateTime createdAt,
    boolean orderStatus,
    boolean deliveryStatus,
    List<OrderItemResponseDto> orderItems
) {
}
