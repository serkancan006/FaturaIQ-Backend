package com.example.FaturaIQ.services.abstracts.extract;

import com.example.FaturaIQ.dtos.auth.TokenDto;
import com.example.FaturaIQ.exceptions.JwtValidationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

import java.util.List;

public interface JwtService {

    TokenDto generateJwtToken(String username, List<String> roles, String companyTaxNumber);

    TokenDto generateJwtRefreshToken(String username);

    String getUserNameFromJwtToken(String token);

    String getUserNameFromJwtRefreshToken(String token);

    boolean validateJwtToken(
            String authToken) throws SignatureException, MalformedJwtException, ExpiredJwtException, UnsupportedJwtException, IllegalArgumentException, JwtValidationException;

    boolean validateJwtRefreshToken(
            String authToken) throws SignatureException, MalformedJwtException, ExpiredJwtException, UnsupportedJwtException, IllegalArgumentException, JwtValidationException;
}
