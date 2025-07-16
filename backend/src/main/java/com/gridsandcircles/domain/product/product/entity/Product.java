package com.gridsandcircles.domain.product.product.entity;

import com.gridsandcircles.domain.order.orderItems.entity.OrderItems;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="product")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String name; // 제품명 필드
    private int price;
    private String description;
    private String productImage;

    @OneToMany(mappedBy = "product")
    private List<OrderItems> orderItemsList = new ArrayList<>(); // product로 OrderItems
}
