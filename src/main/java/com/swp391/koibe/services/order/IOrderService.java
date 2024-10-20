package com.swp391.koibe.services.order;

import com.swp391.koibe.dtos.OrderDTO;
import com.swp391.koibe.enums.OrderStatus;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.AuctionKoi;
import com.swp391.koibe.models.Order;
import java.util.List;

import com.swp391.koibe.models.User;
import com.swp391.koibe.responses.order.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;
    Order createOrder(Order order) throws Exception;
    Order getOrder(Long id);
    Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;
    Order updateOrderByUser(Long id, OrderDTO orderDTO) throws DataNotFoundException;
    Order updateOrderStatus(Long id, OrderStatus orderStatus) throws DataNotFoundException;
    void deleteOrder(Long id);
    List<OrderResponse> findByUserId(Long userId);
    Page<Order> getOrdersByKeyword(String keyword, Pageable pageable);
    List<Order> getOrdersByStatus(OrderStatus orderStatus) throws DataNotFoundException;
    void createOrderForAuctionKoi(AuctionKoi auctionKoi, User bidder) throws Exception;
    Page<Order> getOrdersByStatus(Long userId,String keyword, Pageable pageable);
}
