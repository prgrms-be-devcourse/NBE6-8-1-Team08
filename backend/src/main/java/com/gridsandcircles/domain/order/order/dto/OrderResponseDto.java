package com.gridsandcircles.domain.order.order.dto;

import com.gridsandcircles.domain.order.orderitem.dto.OrderItemResponseDto;

import java.sql.Timestamp;
import java.util.List;

public record OrderResponseDto(
    Integer orderId,
    String email,
    String address,
    Timestamp createdAt,
    boolean orderStatus,
    boolean deliveryStatus,
    List<OrderItemResponseDto> orderItems
) {
}
