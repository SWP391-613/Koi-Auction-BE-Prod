package com.swp391.koibe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EAuctionStatus {

    UPCOMING("UPCOMING"),
    ONGOING("ONGOING"),
    ENDED("ENDED");

    private final String status;

}
