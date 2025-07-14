package com.gridsandcircles.domain.order.order.entity;

import java.sql.Timestamp;

public class Order {
    private Integer orderId;
    private String email;
    private String address;
    private Timestamp createAt;
    private boolean deliveryStatus;
    private boolean orderStatus;
}
