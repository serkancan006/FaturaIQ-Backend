package com.example.FaturaIQ.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUser {
    @NotBlank(message = "Kullanıcı adı boş geçilemez")
    private String username;
    @NotBlank(message = "Şifre boş geçilemez")
    private String password;
    @NotBlank(message = "Mail boş geçilemez")
    private String email;
    @NotBlank(message = "Ad boş geçilemez")
    private String firstName;
    @NotBlank(message = "Soyad boş geçilemez")
    private String lastName;
    @NotNull(message = "Şirket boş geçilemez")
    private Integer companyId;
}
