package com.gridsandcircles.domain.order.order.dto;

import com.gridsandcircles.domain.order.order.entity.Order;
import lombok.Data;

@Data
public class OrderDto {
    private Integer orderId;
    private String email;
    private String address;


    public OrderDto(Order order) {
        this.orderId = order.getOrderId();
        this.email = order.getEmail();
        this.address = order.getAddress();

    }
}
