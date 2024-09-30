package com.swp391.koibe.exceptions;

import com.swp391.koibe.exceptions.base.DataNotFoundException;

public class StaffNotFoundException extends DataNotFoundException {

    public StaffNotFoundException(String message) {
        super(message);
    }

}
