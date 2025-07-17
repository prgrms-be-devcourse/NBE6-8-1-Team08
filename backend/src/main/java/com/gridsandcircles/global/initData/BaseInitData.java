package com.gridsandcircles.global.initData;

import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.service.OrderService;
import com.gridsandcircles.domain.order.orderitem.entity.OrderItem;
import com.gridsandcircles.domain.order.orderitem.service.OrderItemService;
import com.gridsandcircles.domain.product.product.entity.Product;
import com.gridsandcircles.domain.product.product.service.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Configuration
public class BaseInitData {

  private final OrderService orderService;
  private final OrderItemService orderItemService;
  private final ProductService productService;

  @PostConstruct
  public void init() {
    System.out.println("BaseInitData 초기 데이터 입력");

    if (orderService.count() == 0) {
      Order order1 = Order.builder()
          .email("order1@example.com")
          .address("서울")
          .orderStatus(true)
          .deliveryStatus(false)
          .build();

      Order order2 = Order.builder()
          .email("order2@example.com")
          .address("부산")
          .orderStatus(false)
          .deliveryStatus(false)
          .build();

      Product product1 = Product.builder()
          .name("아메리카노")
          .price(3000)
          .description("진한 커피")
          .productImage("image.jpg1")
          .build();

      Product product2 = Product.builder()
          .name("초코라떼")
          .price(5000)
          .description("진한 라떼")
          .productImage("image.jpg2")
          .build();

      OrderItem orderItem1 = OrderItem.builder()
          .order(order1)
          .product(product1)
          .orderCount(10)
          .build();

      OrderItem orderItem2 = OrderItem.builder()
          .order(order1)
          .product(product2)
          .orderCount(20)
          .build();

      OrderItem orderItem3 = OrderItem.builder()
          .order(order2)
          .product(product2)
          .orderCount(30)
          .build();

      orderService.createOrder(order1);
      orderService.createOrder(order2);
      productService.createProduct(product1);
      productService.createProduct(product2);
      orderItemService.createOrderItem(orderItem1);
      orderItemService.createOrderItem(orderItem2);
      orderItemService.createOrderItem(orderItem3);

      System.out.println("주문개수:" + orderService.count());

//        Order orderToDelete = orderService.findById(1).orElseThrow(() -> new RuntimeException("초기화 중 주문을 찾을 수 없습니다. ID: 1"));;
//        orderService.deleteOrder(orderToDelete);
//        System.out.println("주문개수:"+orderService.count());
//        System.out.println("주문개수:"+orderItemService.count());
//        orderItemService.deleteOrderItem(1);
//        System.out.println("주문개수:"+orderItemService.count());

      System.out.println("Order 엔티티 데이터 초기화");
    } else {
      System.out.println("초기 데이터가 이미 존재합니다.");
    }
  }
}