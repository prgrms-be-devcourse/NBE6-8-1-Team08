package com.gridsandcircles.domain.order.orderItem.dto;

import com.gridsandcircles.domain.order.orderItem.entity.OrderItem;
import lombok.Data;

@Data
public class OrderItemDto {
    private Integer orderItemId;
    private Integer productId;
    private int orderCount;

    public OrderItemDto(OrderItem orderItem) {
        this.orderItemId = orderItem.getOrderItemId();
        this.productId = orderItem.getProduct().getProductId();
        this.orderCount = orderItem.getOrderCount();
    }
}