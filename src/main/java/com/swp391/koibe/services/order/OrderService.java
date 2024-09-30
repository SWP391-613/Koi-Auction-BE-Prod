package com.swp391.koibe.services.order;

import com.swp391.koibe.models.Order;
import com.swp391.koibe.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) throws Exception {
        return null;
    }
}
