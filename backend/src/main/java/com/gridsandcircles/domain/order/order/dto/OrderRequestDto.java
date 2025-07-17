package com.gridsandcircles.domain.order.order.dto;

import java.util.List;

public record OrderRequestDto(
    String email,
    String address,
    List<OrderItemRequestDto> orderItems
) {
    public record OrderItemRequestDto(
        Long productId,
        int count
    ) {}
}