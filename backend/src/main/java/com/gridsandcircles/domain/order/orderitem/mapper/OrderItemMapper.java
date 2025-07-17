package com.gridsandcircles.domain.order.orderitem.mapper;

import com.gridsandcircles.domain.order.orderitem.dto.OrderItemResponseDto;
import com.gridsandcircles.domain.order.orderitem.entity.OrderItem;

public class OrderItemMapper {

    public static OrderItemResponseDto toResponseDto(OrderItem orderItem) {
        return new OrderItemResponseDto(
                orderItem.getOrderItemId(),
                orderItem.getProduct().getName(),
                orderItem.getOrderCount(),
                orderItem.getProduct().getPrice()
        );
    }
}