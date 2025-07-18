package com.gridsandcircles.domain.order.order.dto;

public record OrderCancelRequestDto(
        String email,
        Long productId
) {
}
