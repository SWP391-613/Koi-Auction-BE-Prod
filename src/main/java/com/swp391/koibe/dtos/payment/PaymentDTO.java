package com.swp391.koibe.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    @JsonProperty("payment_amount")
    @Min(value = 1, message = "Payment amount must be greater than 0")
    private Float paymentAmount;

    @JsonProperty("payment_method")
    @NotNull(message = "Payment method is required")
    private String paymentMethod;

    @JsonProperty("payment_type")
    @NotNull(message = "Payment type is required")
    private String paymentType;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("payment_status")
    private String paymentStatus;

    @JsonProperty("user_id")
    @NotNull(message = "User id is required")
    @Min(value = 1, message = "User id must be greater than 0")
    private Long userId;

}