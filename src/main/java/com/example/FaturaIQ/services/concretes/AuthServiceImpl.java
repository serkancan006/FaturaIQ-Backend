package com.example.FaturaIQ.services.concretes;

import com.example.FaturaIQ.dtos.auth.AuthenticationRequest;
import com.example.FaturaIQ.dtos.auth.TokenDto;
import com.example.FaturaIQ.entities.Role;
import com.example.FaturaIQ.entities.User;
import com.example.FaturaIQ.exceptions.InvalidCredentialsException;
import com.example.FaturaIQ.exceptions.UserNotFoundException;
import com.example.FaturaIQ.repositories.UserRepository;
import com.example.FaturaIQ.services.abstracts.AuthService;
import com.example.FaturaIQ.services.abstracts.extract.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public TokenDto signIn(AuthenticationRequest authenticationRequest){
        User user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(UserNotFoundException::new);
        if(passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword()) &&
                user.getUsername().equals(authenticationRequest.getUsername())){

            TokenDto tokenDto = jwtService.generateJwtToken(
                    user.getUsername(),
                    user.getRoles().stream().map(Role::getName).toList(),
                    user.getCompany().getTaxNumber()
            );

            // refresh token veritabanına kaydedilir
            user.setRefreshToken(tokenDto.getRefreshToken());
            user.setRefreshTokenExpiry(tokenDto.getRefreshTokenExpiry());
            userRepository.save(user);

            return tokenDto;
        }
        throw new InvalidCredentialsException();
    }

    @Override
    public TokenDto refreshToken(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Geçersiz refresh token"));

        if (user.getRefreshTokenExpiry().before(new Date())) {
            throw new IllegalArgumentException("Refresh token süresi dolmuş");
        }

        TokenDto newToken = jwtService.generateJwtToken(
                user.getUsername(),
                user.getRoles().stream().map(Role::getName).toList(),
                user.getCompany().getTaxNumber()
        );

        // Yeni refresh token'ı kullanıcıya ata ve kaydet
        user.setRefreshToken(newToken.getRefreshToken());
        user.setRefreshTokenExpiry(newToken.getRefreshTokenExpiry());
        userRepository.save(user);

        return newToken;
    }
}
