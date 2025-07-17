package com.gridsandcircles.domain.order.orderitem.dto;

import com.gridsandcircles.domain.order.orderitem.entity.OrderItem;
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