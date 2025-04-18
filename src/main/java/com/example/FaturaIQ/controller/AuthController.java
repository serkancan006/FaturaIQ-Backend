package com.example.FaturaIQ.controller;

import com.example.FaturaIQ.dtos.auth.AuthenticationRequest;
import com.example.FaturaIQ.dtos.auth.TokenDto;
import com.example.FaturaIQ.services.abstracts.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<TokenDto> signIn(@RequestBody AuthenticationRequest authenticationRequest){
        TokenDto result = authService.signIn(authenticationRequest);
        return ResponseEntity.ok(result);
    }

}
