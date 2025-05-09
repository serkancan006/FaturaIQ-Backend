package com.example.FaturaIQ.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFilterCretionDto {
    private String username;
    private String email;
    private Integer page;
    private Integer size;
}
