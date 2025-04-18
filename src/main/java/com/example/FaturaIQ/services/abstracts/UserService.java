package com.example.FaturaIQ.services.abstracts;

import com.example.FaturaIQ.dtos.user.CreateUser;
import com.example.FaturaIQ.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void createUser(CreateUser createUser);

    List<User> getAllUser();

    User getUserByUsername(String userName);
}
