package com.example.FaturaIQ.controller;

import com.example.FaturaIQ.entities.User;
import com.example.FaturaIQ.repositories.CompanyRepository;
import com.example.FaturaIQ.utils.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
@AllArgsConstructor
public class TestController {
    public SecurityUtil securityUtil;
    public CompanyRepository companyRepository;

    @GetMapping
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Bu bir Test endpointidir");
    }

    @GetMapping("get-users-test")
    public ResponseEntity<User> getUsersTest(){
        return ResponseEntity.ok(securityUtil.getCurrentUser());
    }

    @GetMapping("get-compant-test")
    public ResponseEntity<Object> getCompantTest(){
        return ResponseEntity.ok(companyRepository.findCompanyNameAndTaxNumberById(1));
    }
}
