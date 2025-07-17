package com.gridsandcircles.domain.order.order.service;

import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.repository.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

  private final OrderRepository orderRepository;

  public long count() {
    return orderRepository.count();
  }

  public List<Order> findAll() {
    return orderRepository.findAll();
  }

  public void createOrder(Order order) {
    orderRepository.save(order);
  }

  public void deleteOrder(Integer orderId) {
    orderRepository.deleteById(orderId);
  }
}
