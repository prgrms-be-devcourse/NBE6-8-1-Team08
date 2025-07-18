package com.gridsandcircles.domain.order.orderitem.entity;

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
public class OrderItem {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer orderItemId;

    @ManyToOne
    @JoinColumn(name = "product_Id")
    private Product product;

    private int orderCount;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "order_item_status")
    private boolean orderItemStatus;

    public void cancel() {
        this.orderItemStatus = false;
    }
}