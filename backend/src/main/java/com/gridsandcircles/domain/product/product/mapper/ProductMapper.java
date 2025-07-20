package com.gridsandcircles.domain.product.product.mapper;

import com.gridsandcircles.domain.product.product.dto.ProductResponseDto;
import com.gridsandcircles.domain.product.product.entity.Product;

public class ProductMapper {

  public static ProductResponseDto toDto(Product product) {
    return new ProductResponseDto(
        product.getProductId(),
        product.getName(),
        product.getPrice(),
        product.getDescription(),
        product.getProductImage()
    );
  }
}
