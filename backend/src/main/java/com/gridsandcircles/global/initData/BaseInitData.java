package com.gridsandcircles.global.initData;

import com.gridsandcircles.domain.order.order.dto.OrderRequestDto;
import com.gridsandcircles.domain.order.order.service.OrderService;
import com.gridsandcircles.domain.product.product.entity.Product;
import com.gridsandcircles.domain.product.product.service.ProductService;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

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
          .name("Ethiopia Yirgacheffe")
          .price(4500)
          .description("플로럴, 시트러스, 상쾌한 산미와 깔끔한 마무리를 자아내는 원두")
          .productImage("https://naver.me/xmBffsW3")
          .build());

      Product product2 = productService.createProduct(Product.builder()
          .name("Columbia Bean")
          .price(5000)
          .description("균형 잡힌 바디, 은은한 산미, 견과류·초콜릿 향미가 조화로운 원두")
          .productImage("https://naver.me/50JttVK7")
          .build());

      Product product3 = productService.createProduct(Product.builder()
          .name("Guatemala Antigua")
          .price(5200)
          .description("깊고 진한 바디, 카카오, 향신료, 약간의 꽃향이 나는 원두")
          .productImage("https://naver.me/GWiKKVRB")
          .build());

      Product product4 = productService.createProduct(Product.builder()
          .name("Serra Do Caparao")
          .price(5300)
          .description("달콤한 과일향, 부드러운 바디, 견과류 및 초콜릿 뉘앙스의 원두")
          .productImage("https://naver.me/xv6uuvzW")
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