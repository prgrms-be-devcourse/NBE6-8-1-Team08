package com.gridsandcircles.domain.order.orderitem.repository;

import com.gridsandcircles.domain.order.orderitem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
