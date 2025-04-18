package com.example.FaturaIQ.repositories;

import com.example.FaturaIQ.entities.Company;
import com.example.FaturaIQ.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findBySender(Company sender);
    List<Invoice> findByReceiver(Company receiver);
}
