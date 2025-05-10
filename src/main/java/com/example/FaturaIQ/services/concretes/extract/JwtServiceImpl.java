package com.example.FaturaIQ.services.concretes.extract;

import com.example.FaturaIQ.dtos.auth.TokenDto;
import com.example.FaturaIQ.exceptions.JwtValidationException;
import com.example.FaturaIQ.services.abstracts.extract.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class JwtServiceImpl implements JwtService {

    @Value("${example.app.jwtSecret}")
    private String jwtSecret;

    @Value("${example.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    @Value("${example.app.jwtRefreshExpirationAddMs}")
    private int jwtRefreshExpirationAddMs;

    @Override
    public TokenDto generateJwtToken(String username, List<String> roles, String companyTaxNumber) {
        Date expirationDate = new Date((new Date()).getTime() + jwtExpirationMs);
        Date refreshTokenExpirationDate  = new Date((new Date()).getTime() + jwtExpirationMs + jwtRefreshExpirationAddMs);

        String token = Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .claim("company", companyTaxNumber)
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(getSigningKey(jwtSecret))
                .compact();

        String refreshToken = UUID.randomUUID().toString();

        return new TokenDto(token, expirationDate, refreshToken, refreshTokenExpirationDate);
    }


    private Claims getAllClaimsFromToken(String token, String secret) {
        return Jwts.parser()
                .verifyWith(getSigningKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public String getUserNameFromJwtToken(String token) {
        return getAllClaimsFromToken(token, jwtSecret).getSubject();
    }


    @Override
    public boolean validateJwtToken(
            String authToken) throws SignatureException, MalformedJwtException, ExpiredJwtException, UnsupportedJwtException, IllegalArgumentException, JwtValidationException {
        try {

            Jwts.parser().verifyWith(getSigningKey(jwtSecret)).build().parseSignedClaims(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new JwtValidationException("Invalid JWT signature", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new JwtValidationException("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new JwtValidationException("JWT token is expired", e);
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new JwtValidationException("JWT token is unsupported", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new JwtValidationException("JWT claims string is empty", e);
        }
    }

    private SecretKey getSigningKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
