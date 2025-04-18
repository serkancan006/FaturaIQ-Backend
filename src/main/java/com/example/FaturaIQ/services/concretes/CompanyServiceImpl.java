package com.example.FaturaIQ.services.concretes;

import com.example.FaturaIQ.dtos.company.CreateCompany;
import com.example.FaturaIQ.entities.Company;
import com.example.FaturaIQ.repositories.CompanyRepository;
import com.example.FaturaIQ.services.abstracts.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    public void createCompany(CreateCompany createCompany){
        if (companyRepository.existsByTaxNumber(createCompany.getTaxNumber())) {
            throw new IllegalArgumentException("Bu vergi numarasına ait bir şirket zaten mevcut.");
        }

        Company company = Company.builder()
                .name(createCompany.getName())
                .address(createCompany.getAddress())
                .email(createCompany.getEmail())
                .phone(createCompany.getPhone())
                .taxNumber(createCompany.getTaxNumber())
                .build();

        companyRepository.save(company);
    }

    @Override
    public List<Company> getCompanies(){
        return companyRepository.findAll().stream().toList();
    }

}
