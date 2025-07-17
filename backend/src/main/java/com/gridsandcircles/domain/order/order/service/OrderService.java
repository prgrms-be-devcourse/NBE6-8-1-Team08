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

  public long count() {
    return orderRepository.count();
  }

  public List<Order> findAll() {
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

  public Optional<Order> findById(int id) {
    return orderRepository.findById(id);
  }

  private void validateOrderItemDeletable(Order order, OrderItem orderItem) {
    if (!order.getOrderItems().contains(orderItem)) {
      throw new IllegalArgumentException("이 주문에 포함되지 않은 항목입니다.");
    }

    if (!(order.isOrderStatus() && !order.isDeliveryStatus())) {
      throw new IllegalStateException("배송 중이거나 미완료 주문에서는 항목을 삭제할 수 없습니다.");
    }
  }

  @Transactional
  public void deleteOrderItem(Integer orderId, Integer orderItemId) {
    Order order = findById(orderId)
            .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));
    OrderItem orderItem = orderItemService.findById(orderItemId)
            .orElseThrow(() -> new RuntimeException("주문 항목을 찾을 수 없습니다."));

    validateOrderItemDeletable(order, orderItem);

    order.removeOrderItem(orderItem);
    orderItemService.deleteOrderItem(orderItem.getOrderItemId());
  }
}