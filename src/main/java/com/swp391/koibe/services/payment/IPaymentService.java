package com.swp391.koibe.services.payment;

import com.swp391.koibe.dtos.payment.PaymentDTO;
import com.swp391.koibe.enums.EPaymentStatus;
import com.swp391.koibe.models.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Optional;

public interface IPaymentService {
        Map<String, String> createDepositPayment(PaymentDTO paymentDTO, String ipAddress)
                        throws UnsupportedEncodingException;

        Map<String, String> createOrderPayment(PaymentDTO paymentDTO, String ipAddress)
                        throws UnsupportedEncodingException;

        Map<String, Object> handlePaymentReturn(Map<String, String> requestParams);

        Payment createPayment(PaymentDTO payment);

        Optional<Payment> getPaymentByOrderID(Long id);

        Map<String, Object> createPaymentAndUpdateOrder(PaymentDTO paymentDTO) throws Exception;

        Map<String, Object> createDrawOutRequest(PaymentDTO paymentDrawOutDTO) throws Exception;

        Page<Payment> getPaymentsByUserId(Long userId, Pageable pageable);
        Page<Payment> getPaymentsByUserIdAndStatus(Long userId, EPaymentStatus status, Pageable pageable);
        Page<Payment> getPaymentsByKeywordAndStatus(String keyword, EPaymentStatus status, Pageable pageable);
        Page<Payment> getPaymentsByKeyword(String keyword, Pageable pageable);
        Payment updatePaymentStatus(Long id, EPaymentStatus status) throws Exception;
}
