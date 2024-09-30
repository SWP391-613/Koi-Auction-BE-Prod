package com.swp391.koibe.exceptions;

import com.swp391.koibe.exceptions.base.DataNotFoundException;

public class MemberNotFoundException extends DataNotFoundException {

    public MemberNotFoundException(String message) {
        super(message);
    }

}
