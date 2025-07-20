package com.gridsandcircles.domain.product.product.dto;

public record ProductResponseDto(
    Integer productId,
    String name,
    int price,
    String description,
    String productImage
) {

}
