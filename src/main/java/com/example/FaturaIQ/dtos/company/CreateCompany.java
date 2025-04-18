package com.example.FaturaIQ.dtos.company;

import lombok.Data;

@Data
public class CreateCompany {
    private String name;
    private String taxNumber;
    private String email;
    private String address;
    private String phone;
}
