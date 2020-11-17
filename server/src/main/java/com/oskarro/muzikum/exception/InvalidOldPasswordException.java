package com.oskarro.muzikum.exception;

public class InvalidOldPasswordException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidOldPasswordException() {
        super("Incorrect old password");
    }
}

