package com.rezolve.test.exceptions;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String msg) {
        super(msg);
    }
}
