package com.swp391.koibe.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

    private Long id;

    @JsonProperty("payment_amount")
    private Float paymentAmount;

    @JsonProperty("payment_date")
    private LocalDateTime paymentDate;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("payment_status")
    private String paymentStatus; // e.g., SUCCESS, PENDING, REFUNDED

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("user")
    private UserResponse user;

    @JsonProperty("payment_type")
    private String paymentType; // e.g., 'DEPOSIT', 'ORDER', 'DRAW_OUT'

    @JsonProperty("bank_number")
    private String bankNumber;

}
