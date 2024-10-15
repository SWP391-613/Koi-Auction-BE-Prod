package com.swp391.koibe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EPaymentType {
    DEPOSIT("DEPOSIT"),
    ORDER("ORDER"),
    DRAW_OUT("DRAW_OUT");
    private final String type;
}
