package com.example.FaturaIQ.services.abstracts;

import com.example.FaturaIQ.dtos.company.CreateCompany;
import com.example.FaturaIQ.entities.Company;

import java.util.List;

public interface CompanyService {
    void createCompany(CreateCompany createCompany);

    List<Company> getCompanies();
}
