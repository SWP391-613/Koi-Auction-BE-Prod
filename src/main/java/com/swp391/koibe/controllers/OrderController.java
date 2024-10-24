package com.swp391.koibe.controllers;

import com.swp391.koibe.components.LocalizationUtils;
import com.swp391.koibe.dtos.order.OrderDTO;
import com.swp391.koibe.dtos.order.UpdateOrderStatusDTO;
import com.swp391.koibe.enums.OrderStatus;
import com.swp391.koibe.exceptions.InvalidApiPathVariableException;
import com.swp391.koibe.exceptions.MethodArgumentNotValidException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Order;
import com.swp391.koibe.responses.base.BaseResponse;
import com.swp391.koibe.responses.order.OrderResponse;
import com.swp391.koibe.responses.pagination.OrderPaginationResponse;
import com.swp391.koibe.services.order.IOrderService;
import com.swp391.koibe.utils.DTOConverter;
import com.swp391.koibe.utils.MessageKeys;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;
    private final LocalizationUtils localizationUtils;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    public ResponseEntity<?> createOrder(
        @Valid @RequestBody OrderDTO orderDTO,
        BindingResult result) {
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }
        try {
            Order newOrder = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(DTOConverter.fromOrder(newOrder));
        } catch (Exception e) {
            BaseResponse response = new BaseResponse();
            response.setMessage(e.getMessage());
            response.setMessage("Create order failed");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/user/{user_id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER')")
    public ResponseEntity<?> getOrders(
        @PathVariable("user_id") Long userId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<OrderResponse> orders =
            orderService.findByUserId(userId, pageRequest).map(DTOConverter::fromOrder);
        OrderPaginationResponse response = new OrderPaginationResponse();
        response.setItem(orders.getContent());
        response.setTotalItem(orders.getTotalElements());
        response.setTotalPage(orders.getTotalPages());
        return ResponseEntity.ok(response);
    }

    // GET http://localhost:8088/api/v1/orders/2
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_BREEDER')")
    public ResponseEntity<?> getOrder(
        @Valid @PathVariable("id") Long orderId) {
        try {
            Order existingOrder = orderService.getOrder(orderId);
            OrderResponse orderResponse = DTOConverter.fromOrder(existingOrder);
            return ResponseEntity.ok(orderResponse);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse();
            response.setMessage("Get orders failed");
            response.setReason(e.toString());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF')")
    // PUT http://localhost:8088/api/v1/orders/2
    public ResponseEntity<?> updateOrder(
        @Valid @PathVariable long id,
        @Valid @RequestBody OrderDTO orderDTO,
        @Valid @RequestBody UpdateOrderStatusDTO updateOrderStatusDTO,
        BindingResult result) {

        if (id <= 0) {
            throw new InvalidApiPathVariableException("Order id must be greater than 0");
        }

        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        try {
            Order order;
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(
                    grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_STAFF"))) {
                order = orderService.updateOrderStatus(id, OrderStatus.valueOf(
                    updateOrderStatusDTO.getStatus()));
            } else {
                order = orderService.updateOrderByUser(id, orderDTO);
            }
            return ResponseEntity.ok(DTOConverter.fromOrder(order));
        } catch (Exception e) {

            if (e instanceof DataNotFoundException) {
                throw e;
            }
            BaseResponse response = new BaseResponse();
            response.setReason(e.getMessage());
            response.setMessage("Update order failed");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable Long id) {
        // xóa mềm => cập nhật trường active = false
        if (id <= 0) {
            throw new InvalidApiPathVariableException("Order id must be greater than 0");
        }
        try {
            orderService.deleteOrder(id);
            String result = localizationUtils.getLocalizedMessage(
                MessageKeys.DELETE_ORDER_SUCCESSFULLY, id);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            if (e instanceof DataNotFoundException) {
                throw e;
            }
            BaseResponse response = new BaseResponse();
            response.setReason(e.getMessage());
            response.setMessage("Delete order failed");
            return ResponseEntity.badRequest().body(response);
        }

    }

    @GetMapping("/get-orders-by-keyword-and-status")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_STAFF')")
    public ResponseEntity<OrderPaginationResponse> getOrdersByKeyword(
        @RequestParam(defaultValue = "", required = false) String keyword,
        @RequestParam String status,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit) {

        List<OrderResponse> orderResponses = orderService
            .getOrdersByKeyword(keyword, status)
            .stream()
            .map(DTOConverter::fromOrder)
            .collect(Collectors.toList());
        OrderPaginationResponse orderPaginationResponse = new OrderPaginationResponse();
        orderPaginationResponse.setItem(orderResponses);
        orderPaginationResponse.setTotalPage(Math.floorDiv(orderResponses.size(), limit) + 1);
        orderPaginationResponse.setTotalItem(orderResponses.size());
        return ResponseEntity.ok(orderPaginationResponse);
    }

    @GetMapping("/user/{user_id}/get-active-sorted-orders")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_BREEDER')")
    public ResponseEntity<OrderPaginationResponse> getSortedOrder(
        @PathVariable("user_id") Long userId,
        @RequestParam("keyword") OrderStatus keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit) {

        PageRequest pageRequest = PageRequest.of(
            page, limit,
            // Sort.by("createdAt").descending()
            Sort.by("id").ascending());

        Page<OrderResponse> orders;
        OrderPaginationResponse response = new OrderPaginationResponse();

        if(String.valueOf(keyword).equals("ALL")){
            orders =
                orderService.findByUserId(userId, pageRequest).map(DTOConverter::fromOrder);

        }else{
            orders = orderService
                .getOrdersByStatus(userId, keyword, pageRequest)
                .map(DTOConverter::fromOrder);
        }

        response.setItem(orders.getContent());
        response.setTotalItem(orders.getTotalElements());
        response.setTotalPage(orders.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_STAFF')")
    public ResponseEntity<?> updateOrderStatus(
        @Valid @PathVariable long id,
        @Valid @RequestBody UpdateOrderStatusDTO updateOrderStatusDTO) {
        try {
            Order updatedOrder = orderService.updateOrderStatusAndShipDate(id,
                                                                           OrderStatus.valueOf(
                                                                               updateOrderStatusDTO.getStatus()));
            return ResponseEntity.ok(DTOConverter.fromOrder(updatedOrder));
        } catch (Exception e) {
            BaseResponse response = new BaseResponse();
            response.setReason(e.getMessage());
            response.setMessage("Update order status failed");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}/confirm-delivery")
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    public ResponseEntity<?> confirmDelivery(@Valid @PathVariable long id,
                                             @Valid @RequestBody UpdateOrderStatusDTO updateOrderStatusDTO) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(id,
                                                                OrderStatus.valueOf(
                                                                    updateOrderStatusDTO.getStatus()));
            return ResponseEntity.ok(DTOConverter.fromOrder(updatedOrder));
        } catch (Exception e) {
            BaseResponse response = new BaseResponse();
            response.setReason(e.getMessage());
            response.setMessage("Failed to confirm delivery");
            return ResponseEntity.badRequest().body(response);
        }
    }

}
