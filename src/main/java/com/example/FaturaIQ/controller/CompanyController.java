package com.example.FaturaIQ.controller;

import com.example.FaturaIQ.dtos.company.CreateCompany;
import com.example.FaturaIQ.entities.Company;
import com.example.FaturaIQ.services.abstracts.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@AllArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create-company")
    public ResponseEntity<Void> createCompany(@RequestBody CreateCompany createCompany){
        companyService.createCompany(createCompany);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/get-companies")
    public ResponseEntity<List<Company>> getCompanies(){
        return ResponseEntity.ok(companyService.getCompanies());
    }

}
