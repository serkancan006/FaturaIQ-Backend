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
            return jwtService.generateJwtToken(user.getUsername(), user.getRoles().stream().map(Role::getName).toList(), user.getCompany().getTaxNumber());
        }
        throw new InvalidCredentialsException();
    }
}
