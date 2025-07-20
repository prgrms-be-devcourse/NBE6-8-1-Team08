package com.gridsandcircles.domain.product.product.controller;

import com.gridsandcircles.domain.product.product.dto.ProductResponseDto;
import com.gridsandcircles.domain.product.product.service.ProductService;
import com.gridsandcircles.global.ResultResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

  private final ProductService productService;

  @GetMapping("/list")
  public ResponseEntity<ResultResponse<List<ProductResponseDto>>> list() {
    return ResponseEntity.ok().body(new ResultResponse<>("Product list fetch successful",
        productService.getAllProducts()));
  }
}
