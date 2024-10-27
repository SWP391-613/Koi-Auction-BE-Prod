package com.swp391.koibe.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusUpdateDTO {
    @NotBlank(message = "Payment status is required")
    @Pattern(regexp = "SUCCESS|REFUNDED|PENDING", message = "Status must be either SUCCESS, REFUNDED or PENDING")
    @JsonProperty("status")
    String status;
}
