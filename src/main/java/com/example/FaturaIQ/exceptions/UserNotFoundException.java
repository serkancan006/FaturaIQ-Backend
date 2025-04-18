package com.example.FaturaIQ.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(){
        super("Kullanıcı Bulunamadı");
    }
}
