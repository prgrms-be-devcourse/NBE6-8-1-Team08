package com.gridsandcircles.domain.order.order.dto;

import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.orderItem.entity.OrderItem;
import com.gridsandcircles.domain.product.product.entity.Product;

import java.util.List;

public record OrderDto(
        Integer orderId,
        String email,
        String address,
        String createdAt,
        boolean orderStatus,
        boolean deliveryStatus,
        List<OrderItemDto> orderItems
) {
    public static OrderDto from(Order order) {
        return new OrderDto(
                order.getOrderId(),
                order.getEmail(),
                order.getAddress(),
                order.getCreatedAt() != null ? order.getCreatedAt().toString() : null,
                order.isOrderStatus(),
                order.isDeliveryStatus(),
                order.getOrderItems().stream().map(OrderItemDto::from).toList()
        );
    }

    public record OrderItemDto(
            Integer orderItemId,
            Integer productId,
            String productName,
            int productPrice,
            String productImage,
            int orderCount
    ) {
        public static OrderItemDto from(OrderItem item) {
            Product product = item.getProduct();
            return new OrderItemDto(
                    item.getOrderItemId(),
                    product.getProductId(),
                    product.getName(),
                    product.getPrice(),
                    product.getProductImage(),
                    item.getOrderCount()
            );
        }
    }
}