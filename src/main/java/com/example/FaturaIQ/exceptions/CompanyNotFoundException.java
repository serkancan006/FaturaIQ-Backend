package com.example.FaturaIQ.exceptions;

public class CompanyNotFoundException extends RuntimeException{
    public CompanyNotFoundException(){
        super("Firma Bulunamadı");
    }
}
