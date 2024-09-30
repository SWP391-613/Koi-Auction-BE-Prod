package com.swp391.koibe.services.orderdetail;

public interface IOrderDetailService {

    void createOrderDetail(int orderId, int productId, int quantity) throws Exception;

    void updateOrderDetail(int id, int orderId, int productId, int quantity) throws Exception;

    void deleteOrderDetail(int id) throws Exception;

    void getOrderDetail(int id) throws Exception;

    void getOrderDetails() throws Exception;

}
