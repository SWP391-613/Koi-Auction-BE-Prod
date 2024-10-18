package com.swp391.koibe.controllers;

import com.swp391.koibe.configs.VNPayConfig;
import com.swp391.koibe.dtos.PaymentDTO;
import com.swp391.koibe.responses.base.BaseResponse;
import com.swp391.koibe.services.payment.IPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
