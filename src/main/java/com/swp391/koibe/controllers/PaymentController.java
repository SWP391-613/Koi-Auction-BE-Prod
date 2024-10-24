package com.swp391.koibe.controllers;

import com.swp391.koibe.configs.VNPayConfig;
import com.swp391.koibe.dtos.payment.PaymentDTO;
import com.swp391.koibe.enums.EPaymentStatus;
import com.swp391.koibe.models.Payment;
import com.swp391.koibe.responses.base.BaseResponse;
import com.swp391.koibe.responses.pagination.PaymentPaginationResponse;
import com.swp391.koibe.services.payment.IPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("${api.prefix}/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final IPaymentService paymentService;

    @PostMapping("/create_deposit_payment")
    public ResponseEntity<?> createDepositPayment(
            @Valid @RequestBody PaymentDTO paymentDTO,
            HttpServletRequest request) throws UnsupportedEncodingException {
        try {
            String vnp_IpAddr = VNPayConfig.getIpAddress(request);
            Map<String, String> response = paymentService.createDepositPayment(paymentDTO, vnp_IpAddr);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            BaseResponse<?> response = new BaseResponse<>();
            response.setMessage("Failed to create payment");
            response.setReason(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("vnpay/payment_return")
    public ResponseEntity<?> handleVNPayReturn(@RequestParam Map<String, String> requestParams) {
        Map<String, Object> result = paymentService.handlePaymentReturn(requestParams);

        String frontendUrl = "http://localhost:3000/payments/vnpay-payment-return";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(frontendUrl);

        for (Map.Entry<String, Object> entry : result.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue().toString());
        }

        String redirectUrl = builder.toUriString();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUrl))
                .build();
    }

    @PostMapping("/create_order_payment")
    public ResponseEntity<?> createOrderPayment(
            @Valid @RequestBody PaymentDTO paymentDTO,
            HttpServletRequest request) {
        try {
            if ("Cash".equals(paymentDTO.getPaymentMethod())) {
                Map<String, Object> response = paymentService.createPaymentAndUpdateOrder(paymentDTO);
                return ResponseEntity.ok(response);
            } else {
                String vnp_IpAddr = VNPayConfig.getIpAddress(request);
                Map<String, String> response = paymentService.createOrderPayment(paymentDTO, vnp_IpAddr);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            BaseResponse<?> response = new BaseResponse<>();
            response.setMessage("Failed to create payment");
            response.setReason(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/create_drawout_request")
    public ResponseEntity<?> createDrawOutRequest(
            @Valid @RequestBody PaymentDTO paymentDTO) {
        try {
            Map<String, Object> response = paymentService.createDrawOutRequest(paymentDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            BaseResponse<?> response = new BaseResponse<>();
            response.setMessage("Failed to create draw out request");
            response.setReason(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    public ResponseEntity<?> getPaymentByUserId(
            @PathVariable Long userId,
            @RequestParam EPaymentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            PageRequest pageRequest = PageRequest.of(page, limit);

            Page<Payment> payments;
            PaymentPaginationResponse response = new PaymentPaginationResponse();
            if (String.valueOf(status).equals("ALL")) {
                payments = paymentService.getPaymentsByUserId(userId, pageRequest);
            } else {
                payments = paymentService.getPaymentsByUserIdAndStatus(userId, status, pageRequest);
            }
            response.setItem(payments.getContent());
            response.setTotalItem(payments.getTotalElements());
            response.setTotalPage(payments.getTotalPages());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            BaseResponse<?> response = new BaseResponse<>();
            response.setMessage("Failed to get payments");
            response.setReason(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/get_payments_by_status_and_keyword")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_STAFF')")
    public ResponseEntity<?> getPaymentsByStatus(
            @RequestParam(defaultValue = "", required = false) String keyword,
            @RequestParam EPaymentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            PageRequest pageRequest = PageRequest.of(page, limit);
            Page<Payment> payments = paymentService.getPaymentsByKeywordAndStatus(keyword, status, pageRequest);
            PaymentPaginationResponse response = new PaymentPaginationResponse();
            response.setItem(payments.getContent());
            response.setTotalItem(payments.getTotalElements());
            response.setTotalPage(payments.getTotalPages());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            BaseResponse<?> response = new BaseResponse<>();
            response.setMessage("Failed to get payments");
            response.setReason(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_STAFF')")
    public ResponseEntity<?> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam EPaymentStatus status) {
        try {
            Payment payment = paymentService.updatePaymentStatus(id, status);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            BaseResponse<?> response = new BaseResponse<>();
            response.setMessage("Failed to update payment status");
            response.setReason(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


}
