package com.gridsandcircles.domain.order.orderItems.service;

import com.gridsandcircles.domain.order.orderItems.entity.OrderItem;
import com.gridsandcircles.domain.order.orderItems.repository.OrderItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderItemsService {

    private final OrderItemsRepository orderItemsRepository;

    public long count() {
        return orderItemsRepository.count();
    }

    public void createOrderItem(OrderItem orderItem){
        orderItemsRepository.save(orderItem);
    }

    public void deleteOrder(Integer orderItemId){
        orderItemsRepository.deleteById(orderItemId);
    }
}
