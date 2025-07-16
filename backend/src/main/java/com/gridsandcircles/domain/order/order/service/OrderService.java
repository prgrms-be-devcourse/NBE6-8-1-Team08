package com.gridsandcircles.domain.order.order.service;

import com.gridsandcircles.domain.order.order.dto.OrderCreateReqBody;
import com.gridsandcircles.domain.order.order.dto.OrderDto;
import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.repository.OrderRepository;
import com.gridsandcircles.domain.order.orderItem.entity.OrderItem;
import com.gridsandcircles.domain.product.product.entity.Product;
import com.gridsandcircles.domain.product.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public long count() {
        return orderRepository.count();
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public void createOrder(Order order){
        orderRepository.save(order);
    }

    public void deleteOrder(Integer orderId){
        orderRepository.deleteById(orderId);
    }

    public OrderDto registerOrder(int num, OrderCreateReqBody reqBody) {
        String email = reqBody.email();
        String address = reqBody.address();
        int productId = reqBody.productId();
        int orderCount = reqBody.count();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        Order order = Order.builder()
                .email(email)
                .address(address)
                .orderStatus(true)
                .deliveryStatus(false)
                .build();

        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .orderCount(orderCount)
                .build();

        order.addOrderItem(orderItem);

        Order savedOrder = orderRepository.save(order);

        return OrderDto.from(savedOrder);
    }
}