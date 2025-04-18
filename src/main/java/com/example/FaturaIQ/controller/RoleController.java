package com.example.FaturaIQ.controller;

import com.example.FaturaIQ.dtos.role.CreateRole;
import com.example.FaturaIQ.services.abstracts.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create-role")
    public ResponseEntity<Void> createRole(@RequestBody CreateRole createRole){
        roleService.createRole(createRole);
        return ResponseEntity.ok().build();
    }



}
