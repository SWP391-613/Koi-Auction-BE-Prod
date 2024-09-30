package com.swp391.koibe.services.order;

import com.swp391.koibe.models.Order;

public interface IOrderService {

    Order createOrder(Order order) throws Exception;

}
