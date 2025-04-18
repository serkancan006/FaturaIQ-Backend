package com.example.FaturaIQ.services.abstracts;

import com.example.FaturaIQ.dtos.auth.AuthenticationRequest;
import com.example.FaturaIQ.dtos.auth.TokenDto;

public interface AuthService {
    TokenDto signIn(AuthenticationRequest authenticationRequest);
}
