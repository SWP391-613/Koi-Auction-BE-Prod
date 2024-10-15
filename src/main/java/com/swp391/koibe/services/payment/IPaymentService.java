package com.swp391.koibe.services.payment;

import com.swp391.koibe.dtos.PaymentDTO;
import com.swp391.koibe.models.Payment;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface IPaymentService {
        Map<String, String> createDepositPayment(PaymentDTO paymentDTO, String ipAddress)
                        throws UnsupportedEncodingException;

        Map<String, String> createOrderPayment(PaymentDTO paymentDTO, String ipAddress)
                        throws UnsupportedEncodingException;

        Map<String, Object> handlePaymentReturn(Map<String, String> requestParams);

        Payment createPayment(PaymentDTO payment);

        Payment getPaymentByOrderID(Long id);

        Map<String, Object> createPaymentAndUpdateOrder(PaymentDTO paymentDTO) throws Exception;

}
