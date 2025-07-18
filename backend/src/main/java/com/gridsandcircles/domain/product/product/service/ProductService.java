package com.gridsandcircles.domain.product.product.service;

import com.gridsandcircles.domain.product.product.entity.Product;
import com.gridsandcircles.domain.product.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

        public Product createProduct(Product product){
        productRepository.save(product);
        return product;
    }
}
