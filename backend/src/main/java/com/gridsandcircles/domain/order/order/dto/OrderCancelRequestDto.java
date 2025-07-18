package com.gridsandcircles.domain.order.order.dto;

import java.util.List;

public record OrderCancelRequestDto(
        String email,
        List<ProductRequestDto> products
) {
    public record ProductRequestDto(int productId){}
}
