package com.gridsandcircles.domain.order.order.service;

import com.gridsandcircles.domain.order.order.entity.Order;
import com.gridsandcircles.domain.order.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public long count() {
        return orderRepository.count();
    }

    public List<Order> findAll() {return orderRepository.findAll();}

    public void createOrder(Order order){
        orderRepository.save(order);
    }

    public void deleteOrder(Order order){orderRepository.delete(order);}

    public Optional<Order> findById(int id) {return orderRepository.findById(id);}

    @Transactional
    public void removeOrderItem(Integer orderId, Integer orderItemId){
        Order order = findById(orderId).orElseThrow(()->new RuntimeException("주문을 찾을 수 없습니다."));

        boolean removed = order.getOrderItems().removeIf(item ->item.getOrderItemId().equals(orderItemId));

        if(!removed){
            throw new RuntimeException("해당 주문 항목을 찾을 수 없습니다.");
        }
    }
}
