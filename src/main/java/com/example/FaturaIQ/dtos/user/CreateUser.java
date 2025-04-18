package com.example.FaturaIQ.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUser {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Integer companyId;
}
