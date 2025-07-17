package com.gridsandcircles.domain.order.order.entity;

import com.gridsandcircles.domain.order.orderitem.entity.OrderItem;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
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
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer orderId;

    private String email;

    private String address;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder.Default
    private boolean orderStatus = true;

    @Builder.Default
    private boolean deliveryStatus = false;

    @OneToMany(mappedBy="order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
    }
}