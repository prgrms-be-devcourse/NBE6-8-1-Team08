package com.gridsandcircles.domain.order.orderItems.entity;

import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.product.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OrderItems {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer orderItemId;

    @ManyToOne
    @JoinColumn(name = "product_Id")
    private Product product;//Integer productId에서 변경

    private int orderCount;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}