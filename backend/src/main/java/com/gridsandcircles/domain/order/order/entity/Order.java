package com.gridsandcircles.domain.order.order.entity;

import com.gridsandcircles.domain.order.orderItem.entity.OrderItem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer orderId;

    private String email;

    private String address;

    @CreationTimestamp
    private Timestamp createdAt;

    private boolean orderStatus;

    private boolean deliveryStatus;

    @OneToMany(mappedBy="order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
    }
}