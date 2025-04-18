package com.example.FaturaIQ.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Kullanıcı adı veya parola hatalı!"
        );
    }
}
