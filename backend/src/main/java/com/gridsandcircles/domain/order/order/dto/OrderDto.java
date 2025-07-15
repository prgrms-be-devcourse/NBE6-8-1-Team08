package com.gridsandcircles.domain.order.order.dto;

import com.gridsandcircles.domain.order.orderItems.entity.OrderItems;

import java.util.List;

public class OrderDto {
    private String email;
    private String address;
    private List<OrderItems> orderItems;
}
