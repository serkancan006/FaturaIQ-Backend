package com.example.FaturaIQ.controller;

import com.example.FaturaIQ.dtos.user.CreateUser;
import com.example.FaturaIQ.dtos.user.UserFilterCretionDto;
import com.example.FaturaIQ.entities.User;
import com.example.FaturaIQ.services.abstracts.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUser createUser){
        userService.createUser(createUser);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-users")
    public ResponseEntity<List<User>> gelAllUsers(@RequestParam(required = false) String username,
                                                  @RequestParam(required = false) String email,
                                                  @RequestParam(required = false) Integer page,
                                                  @RequestParam(required = false) Integer size){
        UserFilterCretionDto request = new UserFilterCretionDto(username, email, page, size);
        return ResponseEntity.ok(userService.getAllUserByCretion(request));
    }

}
