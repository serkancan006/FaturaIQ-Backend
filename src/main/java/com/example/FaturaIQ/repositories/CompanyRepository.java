package com.example.FaturaIQ.repositories;

import com.example.FaturaIQ.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findByTaxNumber(String taxNumber);
    boolean existsByTaxNumber(String taxNumber);
    // Native Query ile şirket adı ve vergi numarasını al
    @Query(value = "SELECT name, tax_number FROM Companies WHERE id = :companyId", nativeQuery = true)
    List<Object[]> findCompanyNameAndTaxNumberById(@Param("companyId") Integer companyId);
}
