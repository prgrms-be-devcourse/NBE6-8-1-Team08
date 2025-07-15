package com.gridsandcircles.domain.order.order.entity;

import com.gridsandcircles.domain.order.orderItems.entity.OrderItems;
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

    private Timestamp createdAt; // 오후 2시기준 배송일결정.

    private boolean orderStatus;

    private boolean deliveryStatus;

    @OneToMany(mappedBy="order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @Builder.Default //필드의 기본값(default value)을 지정
    private List<OrderItems> orderItems = new ArrayList<>();

    public void addOrderItems(OrderItems items) {
        orderItems.add(items);
        items.setOrder(this);
    }
}