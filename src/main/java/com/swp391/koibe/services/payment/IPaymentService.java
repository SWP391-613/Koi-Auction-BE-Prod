package com.swp391.koibe.services.payment;

import com.swp391.koibe.models.Payment;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface IPaymentService {
    Map<String, String> createDepositPayment(Map<String, String> requestParams, String ipAddress)
            throws UnsupportedEncodingException;

    Map<String, String> createOrderPayment(Map<String, String> requestParams, String ipAddress)
            throws UnsupportedEncodingException;

    Map<String, Object> handlePaymentReturn(Map<String, String> requestParams);

//    Payment createPayment(Payment payment);
//    Payment getPaymentByOrderID(Long id);

}
