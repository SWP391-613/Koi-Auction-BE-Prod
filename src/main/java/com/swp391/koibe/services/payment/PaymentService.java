package com.swp391.koibe.services.payment;

import com.swp391.koibe.configs.VNPayConfig;
import com.swp391.koibe.dtos.PaymentDTO;
import com.swp391.koibe.enums.EPaymentStatus;
import com.swp391.koibe.enums.EPaymentType;
import com.swp391.koibe.enums.OrderStatus;
import com.swp391.koibe.exceptions.MalformDataException;
import com.swp391.koibe.models.Order;
import com.swp391.koibe.models.Payment;
import com.swp391.koibe.repositories.OrderRepository;
import com.swp391.koibe.repositories.PaymentRepository;
import com.swp391.koibe.repositories.UserRepository;
import com.swp391.koibe.services.order.IOrderService;
import com.swp391.koibe.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final IUserService userService;
    private final IOrderService orderService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public Map<String, String> createDepositPayment(PaymentDTO paymentDTO, String ipAddress)
            throws UnsupportedEncodingException {
        return createVNPayPayment(paymentDTO, ipAddress, "Deposit to account:");
    }

    @Override
    public Map<String, String> createOrderPayment(PaymentDTO paymentDTO, String ipAddress)
            throws UnsupportedEncodingException {
        return createVNPayPayment(paymentDTO, ipAddress, "Payment for order:");
    }

    private Map<String, String> createVNPayPayment(PaymentDTO paymentDTO, String ipAddress, String orderInfoPrefix)
            throws UnsupportedEncodingException {
        long amount = (long) (paymentDTO.getPaymentAmount() * 100);
        String id = orderInfoPrefix.startsWith("Deposit") ? paymentDTO.getUserId().toString()
                : paymentDTO.getOrderId().toString();
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);

        Map<String, String> vnp_Params = createBaseVnpParams(amount, vnp_TxnRef, ipAddress);
        vnp_Params.put("vnp_OrderInfo", orderInfoPrefix + id);

        String paymentUrl = createPaymentUrl(vnp_Params);

        Map<String, String> response = new HashMap<>();
        response.put("paymentUrl", paymentUrl);
        return response;
    }

    private Map<String, String> createBaseVnpParams(long amount, String vnp_TxnRef, String vnp_IpAddr) {
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
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

        return vnp_Params;
    }

    private String createPaymentUrl(Map<String, String> vnp_Params) throws UnsupportedEncodingException {
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
        return VNPayConfig.vnp_PayUrl + "?" + queryUrl;
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
            processSuccessfulPayment(result, vnp_Amount, vnp_OrderInfo);
        } else {
            result.put("message", "Payment failed");
            result.put("responseCode", vnp_ResponseCode);
        }

        return result;
    }

    private void processSuccessfulPayment(Map<String, Object> result, String vnp_Amount, String vnp_OrderInfo) {
        long amount = Long.parseLong(vnp_Amount) / 100;
        float amountFloat = Float.parseFloat(vnp_Amount) / 100;
        Payment payment = createPaymentObject(amountFloat, vnp_OrderInfo);

        if (vnp_OrderInfo.startsWith("Deposit to account:")) {
            processDepositPayment(result, amount, payment);
        } else {
            processOrderPayment(result, amount, payment);
        }
    }

    private Payment createPaymentObject(float amount, String orderInfo) {
        return Payment.builder()
                .paymentAmount(amount)
                .paymentMethod("VNPAY")
                .paymentDate(LocalDateTime.now())
                .paymentType(orderInfo.startsWith("Deposit") ? EPaymentType.DEPOSIT : EPaymentType.ORDER)
                .paymentStatus(EPaymentStatus.SUCCESS)
                .user(orderInfo.startsWith("Deposit")
                        ? userRepository.findById(Long.parseLong(orderInfo.split(":")[1])).orElse(null)
                        : orderRepository.findById(Long.parseLong(orderInfo.split(":")[1]))
                        .orElseThrow(() -> new MalformDataException("Order not found")).getUser())
                .order(orderInfo.startsWith("Payment for order")
                        ? orderRepository.findById(Long.parseLong(orderInfo.split(":")[1]))
                        .orElseThrow(() -> new MalformDataException("Order not found"))
                        : null)
                .build();
    }

    private void processDepositPayment(Map<String, Object> result, long amount, Payment payment) {
        String userId = payment.getUser().getId().toString();
        try {
            payment.setUser(userRepository.findById(Long.parseLong(userId)).orElse(null));
            paymentRepository.save(payment);
            userService.updateAccountBalance(Long.parseLong(userId), amount);
            result.put("message", "Deposit successful");
            result.put("userId", userId);
            result.put("amount", amount);
            result.put("paymentType", "deposit");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error updating balance");
        }
    }

   private void processOrderPayment(Map<String, Object> result, long amount, Payment payment) {
    result.put("message", "Order payment successful");
    result.put("amount", amount);
    result.put("paymentType", "order");
    String orderId = payment.getOrder().getId().toString();
    orderRepository.findById(Long.parseLong(orderId)).ifPresent(order -> {
        orderService.updateOrderStatus(order.getId(), OrderStatus.PROCESSING);
        orderRepository.save(order);
        payment.setOrder(order);
        paymentRepository.save(payment);
    });
}

    @Override
    public Payment createPayment(PaymentDTO paymentDTO) {
        Payment payment = Payment.builder()
                .paymentAmount(paymentDTO.getPaymentAmount())
                .paymentMethod(paymentDTO.getPaymentMethod())
                .paymentType(EPaymentType.valueOf(paymentDTO.getPaymentType()))
                .order(paymentDTO.getOrderId() != null ?
                        orderRepository.findById(paymentDTO.getOrderId())
                                .orElseThrow(() -> new MalformDataException("Order not found")) : null)
                .user(userRepository.findById(paymentDTO.getUserId()).orElse(null))
                .paymentStatus(EPaymentStatus.valueOf(paymentDTO.getPaymentStatus()))
                .paymentDate(LocalDateTime.now())
                .build();
        return paymentRepository.save(payment);
    }

    @Override
    public Optional<Payment> getPaymentByOrderID(Long id) {
        Order existingOrder = orderRepository.findById(id).orElse(null);
        return paymentRepository.findByOrder(existingOrder);
    }

    @Override
    @Transactional
    public Map<String, Object> createPaymentAndUpdateOrder(PaymentDTO paymentDTO) throws Exception {
        if (getPaymentByOrderID(paymentDTO.getOrderId()).isPresent()) {
            throw new Exception("Payment already exists for order");
        }

        paymentDTO.setPaymentStatus(EPaymentStatus.PENDING.toString());
        Payment payment = createPayment(paymentDTO);
        if (payment == null) {
            throw new Exception("Failed to create payment");
        }
        orderService.updateOrderStatus(paymentDTO.getOrderId(), OrderStatus.PROCESSING);
        Map<String, Object> response = new HashMap<>();
        response.put("payment", payment);
        response.put("orderStatus", OrderStatus.PROCESSING);
        return response;
    }
}
