package com.gridsandcircles.domain.order.order.repository;

import com.gridsandcircles.domain.order.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
