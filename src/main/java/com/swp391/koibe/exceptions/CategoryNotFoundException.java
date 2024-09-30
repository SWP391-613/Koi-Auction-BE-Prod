package com.swp391.koibe.exceptions;

import com.swp391.koibe.exceptions.base.DataNotFoundException;

public class CategoryNotFoundException extends DataNotFoundException {

    public CategoryNotFoundException(String message) {
        super(message);
    }

}
