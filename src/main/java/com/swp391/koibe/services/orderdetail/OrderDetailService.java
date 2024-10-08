package com.swp391.koibe.services.orderdetail;

import com.swp391.koibe.dtos.OrderDetailDTO;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Koi;
import com.swp391.koibe.models.Order;
import com.swp391.koibe.models.OrderDetail;
import com.swp391.koibe.repositories.KoiRepository;
import com.swp391.koibe.repositories.OrderDetailRepository;
import com.swp391.koibe.repositories.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderDetailService implements IOrderDetailService{
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final KoiRepository koiRepository;
    @Override
    @Transactional
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        Order order = orderRepository.findById(orderDetailDTO.orderId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find Order with id : "+orderDetailDTO.orderId()));
        Koi product = koiRepository.findById(orderDetailDTO.koiId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id: " + orderDetailDTO.koiId()));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .koi(product)
                .numberOfProducts(orderDetailDTO.numberOfProducts())
                .price(orderDetailDTO.price())
                .totalMoney(orderDetailDTO.totalMoney())
                .build();
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail createOrderDetail(OrderDetail orderDetail) throws Exception {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Cannot find OrderDetail with id: "+id));
    }

    @Override
    @Transactional
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO)
            throws DataNotFoundException {
        //tìm xem order detail có tồn tại ko đã
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order detail with id: "+id));
        Order existingOrder = orderRepository.findById(orderDetailDTO.orderId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: "+id));
        Koi existingKoi = koiRepository.findById(orderDetailDTO.koiId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id: " + orderDetailDTO.koiId()));
        existingOrderDetail.setPrice(orderDetailDTO.price());
        existingOrderDetail.setNumberOfProducts(orderDetailDTO.numberOfProducts());
        existingOrderDetail.setTotalMoney(orderDetailDTO.totalMoney());
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setKoi(existingKoi);
        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
