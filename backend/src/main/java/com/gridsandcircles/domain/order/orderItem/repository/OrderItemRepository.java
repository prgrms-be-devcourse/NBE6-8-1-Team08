package com.gridsandcircles.domain.order.orderItem.repository;

import com.gridsandcircles.domain.order.orderItem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
