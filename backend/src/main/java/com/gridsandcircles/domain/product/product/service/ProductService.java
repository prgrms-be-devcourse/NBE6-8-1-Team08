package com.gridsandcircles.domain.product.product.service;

import com.gridsandcircles.domain.product.product.dto.ProductResponseDto;
import com.gridsandcircles.domain.product.product.entity.Product;
import com.gridsandcircles.domain.product.product.mapper.ProductMapper;
import com.gridsandcircles.domain.product.product.repository.ProductRepository;
import com.gridsandcircles.global.ServiceException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

  private final ProductRepository productRepository;

  public Product createProduct(Product product) {
    productRepository.save(product);
    return product;
  }

  public Product getProductById(Long id) {
    return productRepository.findById(id.intValue())
        .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."));
  }

  public List<ProductResponseDto> getAllProducts() {
    return productRepository.findAll()
        .stream()
        .map(ProductMapper::toDto)
        .toList();
  }
}
