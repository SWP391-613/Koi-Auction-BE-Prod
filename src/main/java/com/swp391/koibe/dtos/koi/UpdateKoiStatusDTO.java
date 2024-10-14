package com.swp391.koibe.dtos.koi;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateKoiStatusDTO(
    @NotBlank(message = "Koi status is required")
    @Pattern(regexp = "UNVERIFIED|VERIFIED|REJECTED|PENDING|SOLD", message = "Status must be either UNVERIFIED, VERIFIED, REJECTED, PENDING, SOLD")
    @JsonProperty("tracking_status") String trackingStatus
) {}
