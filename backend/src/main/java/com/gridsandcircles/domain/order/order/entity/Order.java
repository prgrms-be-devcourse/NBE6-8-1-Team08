package com.gridsandcircles.domain.order.order.entity;

import com.gridsandcircles.domain.order.orderItems.entity.OrderItems;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer orderId;

    private String email;

    private String address;

    @CreationTimestamp
    private Timestamp createAt;

    private boolean deliveryStatus;
    private boolean orderStatus;

    @OneToMany(mappedBy="order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<OrderItems> orderItems = new ArrayList<>();

    public void addOrderItems(OrderItems items) {
        orderItems.add(items);
        items.setOrder(this);
    }

}
