package com.swp391.koibe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EAuctionStatus {

    ONGOING("ONGOING"),
    ACTIVE("ACTIVE"),
    ENDED("ENDED");

    private final String status;

}
