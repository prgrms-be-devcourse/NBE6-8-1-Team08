package com.gridsandcircles.domain.order.orderItems.repository;

import com.gridsandcircles.domain.order.orderItems.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItem, Integer> {
}
