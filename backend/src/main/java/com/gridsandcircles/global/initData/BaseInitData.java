package com.gridsandcircles.global.initData;

import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.service.OrderService;
import com.gridsandcircles.domain.order.orderItems.entity.OrderItems;
import com.gridsandcircles.domain.order.orderItems.service.OrderItemsService;
import com.gridsandcircles.domain.product.product.entity.Product;
import com.gridsandcircles.domain.product.product.service.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
//@Profile("!test") //테스트 환경

public class BaseInitData {
    private final OrderService orderService;
    private final OrderItemsService orderItemsService;
    private final ProductService productService;

    @PostConstruct
    public void init() {
        System.out.println("BaseInitData 초기 데이터 입력");

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

        Product product = Product.builder()
                .name("아메리카노")
                .price(3000)
                .description("진한 커피")
                .productImage("image.jpg")
                .build();


        OrderItems orderItem = OrderItems.builder()
                .order(order1)
                .product(product)
                .orderCount(10)
                .build();

        orderService.createOrder(order1);
        orderService.createOrder(order2);
        productService .createProduct(product);
        orderItemsService.createOrder(orderItem);
        System.out.println("주문개수:"+orderService.count());
        orderService.deleteOrder(1);
        System.out.println("주문개수:"+orderService.count());
        System.out.println("Order 엔티티 데이터 초기화");
    }
}