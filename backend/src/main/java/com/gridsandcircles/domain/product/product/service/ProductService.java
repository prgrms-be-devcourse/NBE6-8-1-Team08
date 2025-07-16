package com.gridsandcircles.domain.product.product.service;

import com.gridsandcircles.domain.product.product.entity.Product;
import com.gridsandcircles.domain.product.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public long count() {
        return productRepository.count();
    }
    public Product createProduct(Product product){
        productRepository.save(product);
        return product;
    }
    public void deleteProduct(Integer productId){
        productRepository.deleteById(productId);
    }
}
