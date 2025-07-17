package com.gridsandcircles.domain.order.order.mapper;

import com.gridsandcircles.domain.order.order.dto.OrderResponseDto;
import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.orderitem.mapper.OrderItemMapper;

import java.sql.Timestamp;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponseDto toResponseDto(Order order) {
        return new OrderResponseDto(
                order.getOrderId(),
                order.getEmail(),
                order.getAddress(),
                // 기존 코드 -> incompatible types: LocalDateTime cannot be converted to Timestamp
                // order.getCreatedAt(),
                Timestamp.valueOf(order.getCreatedAt()),
                order.isOrderStatus(),
                order.isDeliveryStatus(),
                order.getOrderItems().stream()
                        .map(OrderItemMapper::toResponseDto)
                        .collect(Collectors.toList())
        );
    }
}