package com.gridsandcircles.domain.order.orderitem.service;

import com.gridsandcircles.domain.order.orderitem.entity.OrderItem;
import com.gridsandcircles.domain.order.orderitem.repository.OrderItemRepository;
import com.gridsandcircles.global.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    public void deleteOrderItem(Integer orderItemId){
        orderItemRepository.deleteById(orderItemId);
    }

    public OrderItem getOrderItem(int id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "Order item not found"));
    }
}
