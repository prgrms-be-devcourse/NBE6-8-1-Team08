package com.gridsandcircles.domain.product.product.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.gridsandcircles.domain.product.product.dto.ProductResponseDto;
import com.gridsandcircles.domain.product.product.service.ProductService;
import com.gridsandcircles.global.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
@Tag(name = "ProductController", description = "제품 API")
public class ProductController {

  private final ProductService productService;

  @GetMapping("/list")
  @Operation(summary = "전체 제품 목록 조회")
  @ApiResponse(
      responseCode = "200",
      description = "제품 목록 조회 성공",
      content = @Content(mediaType = APPLICATION_JSON_VALUE)
  )
  public ResponseEntity<ResultResponse<List<ProductResponseDto>>> list() {
    return ResponseEntity.ok().body(new ResultResponse<>("Product list fetch successful",
        productService.getAllProducts()));
  }
}
