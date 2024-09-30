package com.swp391.koibe.exceptions;

import com.swp391.koibe.exceptions.base.DataAlreadyExistException;

public class CategoryAlreadyExistException extends DataAlreadyExistException {

    public CategoryAlreadyExistException(String message) {
        super(message);
    }

}
