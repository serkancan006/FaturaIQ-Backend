package com.example.FaturaIQ.controller;

import com.example.FaturaIQ.dtos.user.CreateUser;
import com.example.FaturaIQ.entities.User;
import com.example.FaturaIQ.services.abstracts.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create-user")
    public ResponseEntity<Void> createUser(@RequestBody CreateUser createUser){
        userService.createUser(createUser);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-users")
    public ResponseEntity<List<User>> gelAllUsers(){
        return ResponseEntity.ok(userService.getAllUser());
    }

}
