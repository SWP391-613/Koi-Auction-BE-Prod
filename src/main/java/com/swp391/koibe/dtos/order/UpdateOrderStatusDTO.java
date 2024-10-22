package com.swp391.koibe.dtos.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateOrderStatusDTO {
    @NotBlank(message = "Order status is required")
    @Pattern(regexp = "PROCESSING|SHIPPED|DELIVERED|PENDING|CANCELLED", message = "Status must be either PROCESSING,SHIPPED,DELIVERED,PENDING,CANCELLED")
    @JsonProperty("status")
    String status;
}
