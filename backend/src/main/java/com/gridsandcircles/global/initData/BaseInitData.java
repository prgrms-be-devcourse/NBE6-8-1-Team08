package com.gridsandcircles.global.initData;

import com.gridsandcircles.domain.order.order.dto.OrderRequestDto;
import com.gridsandcircles.domain.order.order.service.OrderService;
import com.gridsandcircles.domain.product.product.entity.Product;
import com.gridsandcircles.domain.product.product.service.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Configuration
public class BaseInitData {

  private final OrderService orderService;
  private final ProductService productService;

  @PostConstruct
  public void init() {
    System.out.println("BaseInitData 초기 데이터 입력");

    if (orderService.countOrders() == 0) {
      Product product1 = productService.createProduct(Product.builder()
              .name("아메리카노")
              .price(3000)
              .description("진한 커피")
              .productImage("image.jpg1")
              .build());

      Product product2 = productService.createProduct(Product.builder()
              .name("초코라떼")
              .price(5000)
              .description("진한 라떼")
              .productImage("image.jpg2")
              .build());

      OrderRequestDto orderRequest1 = new OrderRequestDto(
              "order1@example.com",
              "서울",
              List.of(
                      new OrderRequestDto.OrderItemRequestDto(product1.getProductId().longValue(), 10),
                      new OrderRequestDto.OrderItemRequestDto(product2.getProductId().longValue(), 20)
              )
      );

      OrderRequestDto orderRequest2 = new OrderRequestDto(
              "order2@example.com",
              "부산",
              List.of(
                      new OrderRequestDto.OrderItemRequestDto(product2.getProductId().longValue(), 30)
              )
      );

      orderService.createOrder(orderRequest1);
      orderService.createOrder(orderRequest2);

      System.out.println("주문개수:" + orderService.countOrders());
      System.out.println("Order 엔티티 데이터 초기화");
    } else {
      System.out.println("초기 데이터가 이미 존재합니다.");
    }
  }
}