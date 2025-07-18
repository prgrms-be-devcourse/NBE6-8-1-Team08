package com.gridsandcircles.domain.order.orderitem.service;

import com.gridsandcircles.domain.order.orderitem.entity.OrderItem;
import com.gridsandcircles.domain.order.orderitem.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public void deleteOrderItem(Integer orderItemId){
        orderItemRepository.deleteById(orderItemId);
    }

    public Optional<OrderItem> getOrderItem(int id) {
        return orderItemRepository.findById(id);
    }
}
