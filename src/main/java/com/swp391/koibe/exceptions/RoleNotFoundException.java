package com.swp391.koibe.exceptions;

import com.swp391.koibe.exceptions.base.DataNotFoundException;

public class RoleNotFoundException extends DataNotFoundException {

    public RoleNotFoundException(String message) {
        super(message);
    }

}
