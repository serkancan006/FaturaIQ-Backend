package com.example.FaturaIQ.repositories;

import com.example.FaturaIQ.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findByTaxNumber(String taxNumber);
    boolean existsByTaxNumber(String taxNumber);
}
