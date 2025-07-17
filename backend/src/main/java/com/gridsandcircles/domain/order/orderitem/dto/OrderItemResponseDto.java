package com.gridsandcircles.domain.order.orderitem.dto;

import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.product.product.entity.Product;

public record OrderItemResponseDto(
        Integer orderItemId,
        String productName,
        int orderCount,
        int productPrice
) {}