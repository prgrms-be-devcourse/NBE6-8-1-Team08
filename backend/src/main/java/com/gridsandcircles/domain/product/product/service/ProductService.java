package com.gridsandcircles.domain.product.product.service;

import com.gridsandcircles.domain.product.product.entity.Product;
import com.gridsandcircles.domain.product.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public long count() {
        return productRepository.count();
    }

    public void createOrder(Product product){
        productRepository.save(product);
    }

    public void deleteOrder(Integer productId){
        productRepository.deleteById(productId);
    }
}
