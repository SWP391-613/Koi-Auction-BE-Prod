package com.swp391.koibe.exceptions;

import com.swp391.koibe.exceptions.base.DataNotFoundException;

public class TokenNotFoundException extends DataNotFoundException {

    public TokenNotFoundException(String message) {
        super(message);
    }

}
