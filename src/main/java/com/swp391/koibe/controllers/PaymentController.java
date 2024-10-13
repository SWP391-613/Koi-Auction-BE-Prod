package com.swp391.koibe.controllers;

import com.swp391.koibe.configs.VNPayConfig;
import com.swp391.koibe.services.payment.IPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("vnpay/create_payment")
    public ResponseEntity<?> createPayment(@RequestBody Map<String, String> requestParams,
                                           HttpServletRequest request) throws UnsupportedEncodingException {
        String vnp_IpAddr = VNPayConfig.getIpAddress(request);
        Map<String, String> response = paymentService.createPayment(requestParams, vnp_IpAddr);
        return ResponseEntity.ok(response);
    }

    @GetMapping("vnpay/payment_return")
    public ResponseEntity<?> handleVNPayReturn(@RequestParam Map<String, String> requestParams) {
        Map<String, Object> result = paymentService.handlePaymentReturn(requestParams);

        String frontendUrl = "http://localhost:3000/payments/vnpay-payment-return"; // Update this to your frontend URL
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(frontendUrl);

        for (Map.Entry<String, Object> entry : result.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue().toString());
        }

        String redirectUrl = builder.toUriString();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUrl))
                .build();
    }
}
