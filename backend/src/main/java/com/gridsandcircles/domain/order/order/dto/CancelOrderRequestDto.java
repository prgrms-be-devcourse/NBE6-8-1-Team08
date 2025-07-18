package com.gridsandcircles.domain.order.order.dto;

import java.util.List;

public record CancelOrderRequestDto(
        String userId,
        List<ProductRequestDto> products
) {
    public record ProductRequestDto(
            String productId
    ) {}
}