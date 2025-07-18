package com.gridsandcircles.domain.order.order.service;

import com.gridsandcircles.domain.order.order.dto.OrderRequestDto;
import com.gridsandcircles.domain.order.order.dto.OrderResponseDto;
import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.mapper.OrderMapper;
import com.gridsandcircles.domain.order.order.repository.OrderRepository;
import com.gridsandcircles.domain.order.orderitem.entity.OrderItem;
import com.gridsandcircles.domain.order.orderitem.service.OrderItemService;
import com.gridsandcircles.domain.product.product.entity.Product;
import com.gridsandcircles.domain.product.product.service.ProductService;
import com.gridsandcircles.global.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class OrderService{

  private final OrderRepository orderRepository;
  private final OrderItemService orderItemService;
  private final ProductService productService;

  @Transactional(readOnly = true)
  public long countOrders() {
    return orderRepository.count();
  }

  @Transactional(readOnly = true)
  public List<Order> getOrders() {
    return orderRepository.findAll();
  }

  @Transactional
  public OrderResponseDto createOrder(OrderRequestDto dto) {
    Order order = Order.builder()
            .email(dto.email())
            .address(dto.address())
            .orderStatus(true)
            .deliveryStatus(false)
            .build();

    for (OrderRequestDto.OrderItemRequestDto itemDto : dto.orderItems()) {
      Product product = productService.getProductById(itemDto.productId());
      OrderItem orderItem = OrderItem.builder()
              .product(product)
              .orderCount(itemDto.count())
              .orderItemStatus(true)
              .build();
      order.addOrderItem(orderItem);
    }

    orderRepository.save(order);
    return OrderMapper.toResponseDto(order);
  }

  @Transactional(readOnly = true)
  public java.util.List<Order> getOrdersByEmail(String email) {
    return orderRepository.findByEmail(email);
  }

  @Transactional
  public void deleteOrder(Order order) {
    orderRepository.delete(order);
  }

  @Transactional(readOnly = true)
  public Order getOrder(int id) {
    return orderRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Order not found"));
  }

  @Transactional
  protected void validateOrderItemDeletable(Order order, OrderItem orderItem) {
    if (!order.getOrderItems().contains(orderItem)) {
      throw new NoSuchElementException("Order item not found");
    }

    if (!(orderItem.isOrderItemStatus() && !order.isDeliveryStatus())) {//수정
      throw new ServiceException(HttpStatus.BAD_REQUEST, "Cannot delete item from active or incomplete order");
    }
  }

  @Transactional
  public void deleteOrderItem(Integer orderId, Integer orderItemId) {
    Order order = getOrder(orderId);

    OrderItem orderItem = orderItemService.getOrderItem(orderItemId);


    validateOrderItemDeletable(order, orderItem);

    order.removeOrderItem(orderItem);
    orderItemService.deleteOrderItem(orderItem.getOrderItemId());
  }

  @Transactional
  public List<Order> cancelAllOrdersByEmail(String email) {
    List<Order> orders = orderRepository.findByEmail(email);
    if (orders.isEmpty()) {
      throw new NoSuchElementException("No orders found for email: " + email);
    }
    for (Order order : orders) {
      order.cancel();
    }
    return orders;
  }

  @Transactional
  public List<Order> cancelOrderByEmailAndProductId(String email, Long productId) {
    List<Order> orders = orderRepository.findByEmail(email);
    if (orders.isEmpty()) {
      throw new NoSuchElementException("Order not found for email: " + email);
    }

    boolean itemCancelled = false;
    for (Order order : orders) {
      for (OrderItem orderItem : order.getOrderItems()) {
        if (orderItem.getProduct().getProductId().equals(productId.intValue())) {
          orderItem.cancel();
          itemCancelled = true;
        }
      }
    }

    if (!itemCancelled) {
      throw new NoSuchElementException("Product not found in any order for the given email.");
    }
    return orders;
  }
}