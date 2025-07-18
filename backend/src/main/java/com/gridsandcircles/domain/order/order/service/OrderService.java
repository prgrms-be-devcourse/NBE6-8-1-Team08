package com.gridsandcircles.domain.order.order.service;

import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.repository.OrderRepository;
import com.gridsandcircles.domain.order.orderitem.entity.OrderItem;
import com.gridsandcircles.domain.order.orderitem.service.OrderItemService;
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
}