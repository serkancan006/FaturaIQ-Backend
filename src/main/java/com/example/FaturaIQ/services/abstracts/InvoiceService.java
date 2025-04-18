package com.example.FaturaIQ.services.abstracts;

import com.example.FaturaIQ.dtos.invoice.CreateInvoice;
import com.example.FaturaIQ.entities.Invoice;

import java.util.List;

public interface InvoiceService {
    List<Invoice> getInvoices();


    List<Invoice> sentInvoices(String taxNumber);

    List<Invoice> receiverInvoices(String taxNumber);

    void createInvoice(CreateInvoice createInvoice, String senderTaxNumber);
}
