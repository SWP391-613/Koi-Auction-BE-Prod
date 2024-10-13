package com.swp391.koibe.services.payment;

import com.swp391.koibe.configs.VNPayConfig;
import com.swp391.koibe.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {
    private final IUserService userService;

    @Override
    public Map<String, String> createPayment(Map<String, String> requestParams, String ipAddress) throws UnsupportedEncodingException {
        long amount = Long.parseLong(requestParams.get("amount")) * 100;
        String userId = requestParams.get("userId");
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = ipAddress;
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        // ... (copy the vnp_Params setup from the original controller)
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Deposit to account:" + userId);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;

        Map<String, String> response = new HashMap<>();
        response.put("paymentUrl", paymentUrl);
        return response;
    }

    @Override
    public Map<String, Object> handlePaymentReturn(Map<String, String> requestParams) {
        String vnp_ResponseCode = requestParams.get("vnp_ResponseCode");
        String vnp_TxnRef = requestParams.get("vnp_TxnRef");
        String vnp_Amount = requestParams.get("vnp_Amount");
        String vnp_OrderInfo = requestParams.get("vnp_OrderInfo");

        Map<String, Object> result = new HashMap<>();
        result.put("success", "00".equals(vnp_ResponseCode));

        if ("00".equals(vnp_ResponseCode)) {
            long amount = Long.parseLong(vnp_Amount) / 100;
            if (vnp_OrderInfo.startsWith("Deposit to account:")) {
                String userId = vnp_OrderInfo.split(":")[1].trim();
                try {
                    userService.updateAccountBalance(Long.parseLong(userId), amount);
                    result.put("message", "Deposit successful");
                    result.put("userId", userId);
                    result.put("amount", amount);
                } catch (Exception e) {
                    result.put("success", false);
                    result.put("message", "Error updating balance");
                }
            } else {
                result.put("message", "Order payment successful");
                result.put("amount", amount);
            }
        } else {
            result.put("message", "Payment failed");
            result.put("responseCode", vnp_ResponseCode);
        }

        return result;
    }
}
