package com.gridsandcircles.domain.order.order.mapper;

import com.gridsandcircles.domain.order.order.dto.OrderDto;
import com.gridsandcircles.domain.order.order.entity.Order;

public class OrderMapper {

    public static OrderDto toDto(Order order) {
        return new OrderDto(
                order.getOrderId(),
                order.getEmail(),
                order.getAddress(),
                order.getCreatedAt(),
                order.isOrderStatus(),
                order.isDeliveryStatus(),
                order.getOrderItems()
        );
    }
}