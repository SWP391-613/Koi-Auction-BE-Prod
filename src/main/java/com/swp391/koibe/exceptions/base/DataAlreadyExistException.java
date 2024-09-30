package com.swp391.koibe.exceptions.base;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DataAlreadyExistException extends RuntimeException{

    public DataAlreadyExistException(String message) {
        super(message);
    }

}
