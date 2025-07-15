package com.gridsandcircles.domain.order.orderItems.service;

import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemsService {
    private final OrderRepository orderItemsRepository;

    public long count() {
        return orderItemsRepository.count();
    }

    public void createOrder(Order order){
        orderItemsRepository.save(order);
    }

    public void deleteOrder(Integer orderId){
        orderItemsRepository.deleteById(orderId);
    }
}
