package com.gridsandcircles.domain.order.orderItems.service;

import com.gridsandcircles.domain.order.order.repository.OrderRepository;
import com.gridsandcircles.domain.order.orderItems.entity.OrderItems;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemsService {
    private final OrderRepository orderItemsRepository;

    public long count() {
        return orderItemsRepository.count();
    }

    public void createOrder(OrderItems orderItems){
        orderItemsRepository.save(orderItems);
    }

    public void deleteOrder(OrderItems orderItemId){
        orderItemsRepository.deleteById(orderItemId);
    }
}
