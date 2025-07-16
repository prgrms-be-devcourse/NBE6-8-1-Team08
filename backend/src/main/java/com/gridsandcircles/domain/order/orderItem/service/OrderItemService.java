package com.gridsandcircles.domain.order.orderItem.service;

import com.gridsandcircles.domain.order.orderItem.entity.OrderItem;
import com.gridsandcircles.domain.order.orderItem.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public long count() {
        return orderItemRepository.count();
    }

    public void createOrderItem(OrderItem orderItem){
        orderItemRepository.save(orderItem);
    }

    public void deleteOrder(Integer orderItemId){
        orderItemRepository.deleteById(orderItemId);
    }
}