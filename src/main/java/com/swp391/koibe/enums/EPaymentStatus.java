package com.swp391.koibe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EPaymentStatus {
    PENDING("PENDING"),
    SUCCESS("SUCCESS"),
    REFUNDED("REFUNDED");

    private final String status;
}
