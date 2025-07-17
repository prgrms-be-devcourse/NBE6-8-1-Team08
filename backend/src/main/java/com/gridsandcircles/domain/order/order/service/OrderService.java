package com.gridsandcircles.domain.order.order.service;

import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.repository.OrderRepository;
import com.gridsandcircles.domain.order.orderitem.entity.OrderItem;
import com.gridsandcircles.domain.order.orderitem.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService{

  private final OrderRepository orderRepository;
  private final OrderItemService orderItemService;

  @Transactional(readOnly = true)
  public long countOrders() {
    return orderRepository.count();
  }

  @Transactional(readOnly = true)
  public List<Order> getOrders() {
    return orderRepository.findAll();
  }

  @Transactional
  public void createOrder(Order order) {
    orderRepository.save(order);
  }

  @Transactional
  public void deleteOrder(Order order) {
    orderRepository.delete(order);
  }

  @Transactional(readOnly = true)
  public Optional<Order> findById(int id) {
    return orderRepository.findById(id);
  }

  @Transactional
  private void validateOrderItemDeletable(Order order, OrderItem orderItem) {
    if (!order.getOrderItems().contains(orderItem)) {
      throw new IllegalArgumentException("Order item not found");
    }

    if (!(order.isOrderStatus() && !order.isDeliveryStatus())) {
      throw new IllegalStateException("Cannot delete item from active or incomplete order");
    }
  }

  @Transactional
  public void deleteOrderItem(Integer orderId, Integer orderItemId) {
    Order order = findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    OrderItem orderItem = orderItemService.findById(orderItemId)
            .orElseThrow(() -> new RuntimeException("Order item not found"));

    validateOrderItemDeletable(order, orderItem);

    order.removeOrderItem(orderItem);
    orderItemService.deleteOrderItem(orderItem.getOrderItemId());
  }
}