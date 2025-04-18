package com.example.FaturaIQ.exceptions;

public class JwtValidationException extends Exception {
    public JwtValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
