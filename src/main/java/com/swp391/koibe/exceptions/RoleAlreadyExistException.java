package com.swp391.koibe.exceptions;

import com.swp391.koibe.exceptions.base.DataAlreadyExistException;

public class RoleAlreadyExistException extends DataAlreadyExistException {

    public RoleAlreadyExistException(String message) {
        super(message);
    }

}
