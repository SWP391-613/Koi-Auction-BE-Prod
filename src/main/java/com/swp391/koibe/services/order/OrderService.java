package com.swp391.koibe.services.order;

import com.swp391.koibe.dtos.CartItemDTO;
import com.swp391.koibe.dtos.OrderDTO;
import com.swp391.koibe.dtos.OrderDetailDTO;
import com.swp391.koibe.dtos.OrderWithDetailsDTO;
import com.swp391.koibe.enums.OrderStatus;
import com.swp391.koibe.exceptions.MalformDataException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.*;
import com.swp391.koibe.repositories.KoiRepository;
import com.swp391.koibe.repositories.OrderDetailRepository;
import com.swp391.koibe.repositories.OrderRepository;
import com.swp391.koibe.repositories.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.swp391.koibe.responses.order.OrderResponse;
import com.swp391.koibe.services.orderdetail.IOrderDetailService;
import com.swp391.koibe.utils.DTOConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final KoiRepository koiRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;
    private final IOrderMailService orderMailService;
    private final IOrderDetailService orderDetailService;

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        // tìm xem user'id có tồn tại ko
        User user = userRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + orderDTO.getUserId()));
        // convert orderDTO => Order
        // dùng thư viện Model Mapper
        // Tạo một luồng bảng ánh xạ riêng để kiểm soát việc ánh xạ
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));

        // Cập nhật các trường của đơn hàng từ orderDTO
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(LocalDate.now());// lấy thời điểm hiện tại
        order.setStatus(OrderStatus.PENDING);

        // Kiểm tra shipping date phải >= ngày hôm nay
        LocalDate shippingDate = orderDTO.getShippingDate() == null
                ? LocalDate.now()
                : orderDTO.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new MalformDataException("Shipping Date must be at least today !");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);// đoạn này nên set sẵn trong sql
        // EAV-Entity-Attribute-Value model
        order.setTotalMoney(orderDTO.getTotalMoney());
        // orderRepository.save(order);
        // Tạo danh sách các đối tượng OrderDetail từ cartItems
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItemDTO cartItemDTO : orderDTO.getCartItems()) {
            // Tạo một đối tượng OrderDetail từ CartItemDTO
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);

            // Lấy thông tin sản phẩm từ cartItemDTO
            Long koiId = cartItemDTO.koiId();
            int quantity = cartItemDTO.quantity();

            // Tìm thông tin sản phẩm t�� cơ sở dữ liệu (hoặc sử dụng cache nếu cần)
            Koi koi = koiRepository.findById(koiId)
                    .orElseThrow(() -> new DataNotFoundException("Koi not found with id: " + koiId));

            // Đặt thông tin cho OrderDetail
            orderDetail.setKoi(koi);
            orderDetail.setNumberOfProducts(quantity);
            // Các trường khác của OrderDetail nếu cần
            orderDetail.setPrice((float) koi.getPrice());
            orderDetail.setTotalMoney((float) koi.getPrice() * quantity); // Tính tổng tiền

            // Thêm OrderDetail vào danh sách
            orderDetails.add(orderDetail);
        }

        order.setOrderDetails(orderDetails);

        // Lưu danh sách OrderDetail vào cơ sở dữ liệu
        // Save the order (cascade will handle saving orderDetails)
        orderRepository.save(order);

        log.info("Order created with ID: {}", order.getId());
        log.info("Number of OrderDetails: {}", order.getOrderDetails().size());

        return order;
    }

    @Override
    public Order createOrder(Order order) throws Exception {
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrderWithDetails(OrderWithDetailsDTO orderWithDetailsDTO) {
        modelMapper.typeMap(OrderWithDetailsDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderWithDetailsDTO, order);
        Order savedOrder = orderRepository.save(order);

        // Set the order for each order detail
        for (OrderDetailDTO orderDetailDTO : orderWithDetailsDTO.orderDetailDTOS()) {
            // orderDetail.setOrder(OrderDetail);
        }

        // Save or update the order details
        List<OrderDetail> savedOrderDetails = orderDetailRepository.saveAll(order.getOrderDetails());

        // Set the updated order details for the order
        savedOrder.setOrderDetails(savedOrderDetails);

        return savedOrder;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));
        User existingUser = userRepository.findById(
                orderDTO.getUserId()).orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + id));

        // Tạo một luồng bảng ánh xạ riêng để kiểm soát việc ánh xạ
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        // Cập nhật các trường của đơn hàng từ orderDTO
        modelMapper.map(orderDTO, order);
        order.setUser(existingUser);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order updateOrderByUser(Long id, OrderDTO orderDTO)
            throws DataNotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));
        User existingUser = userRepository.findById(
                orderDTO.getUserId()).orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + id));

        // Tạo một luồng bảng ánh xạ riêng để kiểm soát việc ánh xạ
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        // Cập nhật các trường của đơn hàng từ orderDTO
        modelMapper.map(orderDTO, order);
        order.setUser(existingUser);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));

        // no hard-delete, => please soft-delete
        order.setActive(false);
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public List<OrderResponse> findByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderResponse> list = new ArrayList<>();
        if (orders.isEmpty())
            return list;
        for (Order order : orders) {
            list.add(DTOConverter.fromOrder(order));
        }
        return list;
    }

    @Override
    public Page<Order> getOrdersByKeyword(String keyword, Pageable pageable) {
        return orderRepository.findByKeyword(keyword, pageable);
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus orderStatus) throws DataNotFoundException {
        return orderRepository.findByStatus(orderStatus);
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus orderStatus) throws DataNotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));
        order.setStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void createOrderForAuctionKoi(AuctionKoi auctionKoi, User bidder) throws Exception {
        Order order = Order.builder()
                .user(bidder)
                .firstName(bidder.getFirstName())
                .lastName(bidder.getLastName())
                .email(bidder.getEmail())
                .status(OrderStatus.PENDING)
                .address(bidder.getAddress())
                .phoneNumber(bidder.getPhoneNumber() == null ? "" : bidder.getPhoneNumber())
                .totalMoney(500F)
                .shippingMethod("Standard")
                .shippingAddress(bidder.getAddress())
                .shippingDate(LocalDate.now())
                .paymentMethod("Cash")
                .orderDate(LocalDate.now())
                .active(true)
                .build();

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .koi(auctionKoi.getKoi())
                .numberOfProducts(1)
                .price(0F)
                .totalMoney(0F)
                .build();

        order.setOrderDetails(List.of(orderDetail));
        createOrder(order);
        orderDetailService.createOrderDetail(orderDetail);
        // send email to bidder
        Context context = new Context();
        orderMailService.sendOrderCreatedEmailToUser(order, "Order Created Successfully", "orderCreated", context);
    }

    @Override
    public Page<Order> getOrdersByStatus(Long userId, String keyword, Pageable pageable) {
        return orderRepository.findByUserIdAndStatus(userId, OrderStatus.valueOf(keyword), pageable);
    }
}
