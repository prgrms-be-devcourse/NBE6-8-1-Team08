package com.gridsandcircles.domain.order.orderItems.repository;

import com.gridsandcircles.domain.order.orderItems.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
}
