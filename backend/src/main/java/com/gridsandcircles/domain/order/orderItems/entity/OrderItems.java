package com.gridsandcircles.domain.order.orderItems.entity;

import com.gridsandcircles.domain.order.order.entity.Order;
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

    private int orderCount;

    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
