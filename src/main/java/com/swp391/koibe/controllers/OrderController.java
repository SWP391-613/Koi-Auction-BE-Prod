package com.swp391.koibe.controllers;

import com.swp391.koibe.components.LocalizationUtils;
import com.swp391.koibe.dtos.OrderDTO;
import com.swp391.koibe.dtos.PaymentDTO;
import com.swp391.koibe.exceptions.InvalidApiPathVariableException;
import com.swp391.koibe.exceptions.MethodArgumentNotValidException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.models.Order;
import com.swp391.koibe.models.Payment;
import com.swp391.koibe.responses.base.BaseResponse;
import com.swp391.koibe.responses.order.OrderListResponse;
import com.swp391.koibe.responses.order.OrderResponse;
import com.swp391.koibe.services.order.IOrderService;
import com.swp391.koibe.utils.DTOConverter;
import com.swp391.koibe.utils.MessageKeys;
import jakarta.validation.Valid;

import java.util.List;

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
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId) {
        try {
            List<OrderResponse> orders = orderService.findByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            BaseResponse response = new BaseResponse();
            response.setMessage("Get orders failed");
            response.setReason(e.toString());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // GET http://localhost:8088/api/v1/orders/2
    @GetMapping("/{id}")
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
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER')")
    // PUT http://localhost:8088/api/v1/orders/2
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable long id,
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult result) {

        if (id <= 0)
            throw new InvalidApiPathVariableException("Order id must be greater than 0");

        if (result.hasErrors())
            throw new MethodArgumentNotValidException(result);

        try {
            Order order;
            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_MANAGER"))) {
                order = orderService.updateOrder(id, orderDTO);
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
        if (id <= 0)
            throw new InvalidApiPathVariableException("Order id must be greater than 0");
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

    @GetMapping("/get-orders-by-keyword")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_MEMBER')")
    public ResponseEntity<OrderListResponse> getOrdersByKeyword(
            @RequestParam(defaultValue = "", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        // Tạo Pageable từ thông tin trang và giới hạn
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                // Sort.by("createdAt").descending()
                Sort.by("id").ascending());
        Page<OrderResponse> orderPage = orderService
                .getOrdersByKeyword(keyword, pageRequest)
                .map(DTOConverter::fromOrder);
        // Lấy tổng số trang
        int totalPages = orderPage.getTotalPages();
        List<OrderResponse> orderResponses = orderPage.getContent();
        return ResponseEntity.ok(OrderListResponse
                .builder()
                .orders(orderResponses)
                .totalPages(totalPages)
                .build());
    }

    @GetMapping("/user/{user_id}/get-sorted-orders")
    public ResponseEntity<OrderListResponse> getSortedOrder(
            @Valid @PathVariable("user_id") Long userId,
            @Valid @RequestParam("keyword") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {

        PageRequest pageRequest = PageRequest.of(
                page, limit,
                // Sort.by("createdAt").descending()
                Sort.by("id").ascending());
        Page<OrderResponse> orderPage = orderService
                .getOrdersByStatus(userId, keyword, pageRequest)
                .map(DTOConverter::fromOrder);
        int totalPages = orderPage.getTotalPages();
        List<OrderResponse> orderResponses = orderPage.getContent();
        return ResponseEntity.ok(OrderListResponse
                .builder()
                .orders(orderResponses)
                .totalPages(totalPages)
                .build());
    }

}
