package com.swp391.koibe.services.payment;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface IPaymentService {
    Map<String, String> createPayment(Map<String, String> requestParams, String ipAddress) throws UnsupportedEncodingException;
    Map<String, Object> handlePaymentReturn(Map<String, String> requestParams);
}
