package com.swp391.koibe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EBidMethod {

    FIXED_PRICE("1"),
    SEALED_BID("2"),
    ASCENDING_BID("3"),
    DESCENDING_BID("4");

    private final String type;

}
