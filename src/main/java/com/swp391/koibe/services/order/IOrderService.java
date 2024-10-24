package com.swp391.koibe.services.order;

import com.swp391.koibe.dtos.order.OrderDTO;
import com.swp391.koibe.enums.OrderStatus;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.AuctionKoi;
import com.swp391.koibe.models.Order;
import java.util.List;

import com.swp391.koibe.models.User;
import com.swp391.koibe.responses.order.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;
    Order createOrder(Order order) throws Exception;
    Order getOrder(Long id);
    Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;
    Order updateOrderByUser(Long id, OrderDTO orderDTO) throws DataNotFoundException;
    Order updateOrderStatus(Long id, OrderStatus orderStatus) throws DataNotFoundException;
    void deleteOrder(Long id);
    Page<Order> findByUserId(Long userId, Pageable pageable);
    List<Order> getOrdersByKeyword(String keyword, String status);
    List<Order> getOrdersByStatus(OrderStatus orderStatus) throws DataNotFoundException;
    void createOrderForAuctionKoi(AuctionKoi auctionKoi, User bidder) throws Exception;
    Page<Order> getOrdersByStatus(Long userId, OrderStatus keyword, Pageable pageable);
    Order updateOrderStatusAndShipDate(Long id, OrderStatus orderStatus) throws Exception;
    void cancelOrder(Long id) throws DataNotFoundException;
}
