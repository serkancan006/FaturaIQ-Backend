package com.example.FaturaIQ.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tests")
@AllArgsConstructor
public class TestController {
    @GetMapping
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Bu bir Test endpointidir");
    }
}
