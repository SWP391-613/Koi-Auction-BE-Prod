package com.swp391.koibe.exceptions;

import com.swp391.koibe.exceptions.base.DataNotFoundException;

public class BreederNotFoundException extends DataNotFoundException {

    public BreederNotFoundException(String message) {
        super(message);
    }

}
