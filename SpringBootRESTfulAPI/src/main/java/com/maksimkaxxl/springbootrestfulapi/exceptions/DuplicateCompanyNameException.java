package com.maksimkaxxl.springbootrestfulapi.exceptions;

public class DuplicateCompanyNameException extends RuntimeException {
    public DuplicateCompanyNameException(String message) {
        super(message);
    }
}