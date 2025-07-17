package com.gridsandcircles.domain.order.order.dto;

import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.orderItem.dto.OrderItemDto;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDto {

    private Integer orderId;
    private String email;
    private String address;
    private Timestamp createdAt;
    private boolean orderStatus;
    private boolean deliveryStatus;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
        this.orderId = order.getOrderId();
        this.email = order.getEmail();
        this.address = order.getAddress();
        this.createdAt = order.getCreatedAt();
        this.orderStatus = order.isOrderStatus();
        this.deliveryStatus = order.isDeliveryStatus();
        this.orderItems = order.getOrderItems().stream()
                .map(OrderItemDto::new)
                .collect(Collectors.toList());
    }
}
