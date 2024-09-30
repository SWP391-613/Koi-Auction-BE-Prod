package com.swp391.koibe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    VERIFIED("VERIFIED"),
    UNVERIFIED("UNVERIFIED"),
    BANNED("BANNED");

    private final String status;

}
