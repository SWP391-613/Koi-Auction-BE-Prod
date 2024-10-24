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
public class PaymentDrawOutDTO {
    @JsonProperty("payment_amount")
    @Min(value = 1, message = "Payment amount must be greater than 0")
    private Float paymentAmount;

    @JsonProperty("user_id")
    @NotNull(message = "User id is required")
    @Min(value = 1, message = "User id must be greater than 0")
    private Long userId;
}
