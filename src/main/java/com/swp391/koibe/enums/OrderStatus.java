package com.swp391.koibe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

       PENDING("PENDING"),
       PROCESSING("PROCESSING"),
       SHIPPED("SHIPPED"),
       DELIVERED("DELIVERED"),
       CANCELLED("CANCELLED");

         private final String status;
}
