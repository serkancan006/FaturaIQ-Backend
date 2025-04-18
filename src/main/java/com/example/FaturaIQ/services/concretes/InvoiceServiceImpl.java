package com.example.FaturaIQ.services.concretes;

import com.example.FaturaIQ.dtos.invoice.CreateInvoice;
import com.example.FaturaIQ.entities.Company;
import com.example.FaturaIQ.entities.Invoice;
import com.example.FaturaIQ.entities.InvoiceItem;
import com.example.FaturaIQ.entities.enums.InvoiceStatus;
import com.example.FaturaIQ.exceptions.CompanyNotFoundException;
import com.example.FaturaIQ.repositories.CompanyRepository;
import com.example.FaturaIQ.repositories.InvoiceItemRepository;
import com.example.FaturaIQ.repositories.InvoiceRepository;
import com.example.FaturaIQ.services.abstracts.InvoiceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final CompanyRepository companyRepository;
    private final InvoiceItemRepository invoiceItemRepository;


    @Override
    public List<Invoice> getInvoices(){
        return invoiceRepository.findAll().stream().toList();
    }

    @Override
    public List<Invoice> sentInvoices(String taxNumber){
        Company company = companyRepository.findByTaxNumber(taxNumber).orElseThrow(CompanyNotFoundException::new);
        return invoiceRepository.findBySender(company);
    }

    @Override
    public List<Invoice> receiverInvoices(String taxNumber){
        Company company = companyRepository.findByTaxNumber(taxNumber).orElseThrow(CompanyNotFoundException::new);
        return invoiceRepository.findByReceiver(company);
    }

    @Override
    public void createInvoice(CreateInvoice createInvoice, String senderTaxNumber) {
        // Gönderen ve alıcıyı al
        Company sender = companyRepository.findByTaxNumber(senderTaxNumber)
                .orElseThrow(CompanyNotFoundException::new);
        Company receiver = companyRepository.findByTaxNumber(createInvoice.getReceiverTaxNumber())
                .orElseThrow(CompanyNotFoundException::new);

        // 1. Invoice item listesini oluştur
        List<InvoiceItem> invoiceItemList = createInvoice.getItems().stream()
                .map(item -> InvoiceItem.builder()
                        .name(item.getName())
                        .description(item.getDescription())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .totalPrice(item.getUnitPrice() * item.getQuantity())
                        .invoice(null)
                        .build()
                )
                .collect(Collectors.toList());

        // 2. Total amount hesapla
        float totalAmount = (float) invoiceItemList.stream()
                .mapToDouble(InvoiceItem::getTotalPrice)
                .sum();

        // 3. KDV amount hesapla
        float kdvAmount = totalAmount * createInvoice.getKdvRate();

        // 4. Net amount hesapla
        float discountAmount = totalAmount * createInvoice.getDiscountRate();
        float withholdingAmount = (totalAmount + kdvAmount) * createInvoice.getWithholdingRate();
        float netAmount = (totalAmount + kdvAmount) - discountAmount - withholdingAmount;

        // 5. Invoice nesnesini oluştur
        Invoice invoice = Invoice.builder()
                .invoiceNumber(UUID.randomUUID().toString())
                .invoiceStatus(InvoiceStatus.BEKLIYOR)
                .invoiceDate(Date.from(Instant.now()))
                .ScenarioType(createInvoice.getScenarioType())
                .InvoiceType(createInvoice.getInvoiceType())
                .discountRate(createInvoice.getDiscountRate())
                .kdvRate(createInvoice.getKdvRate())
                .withholdingRate(createInvoice.getWithholdingRate())
                .sender(sender)
                .receiver(receiver)
                .totalAmount(totalAmount)
                .netAmount(netAmount)
                .kdvAmount(kdvAmount)
                .items(invoiceItemList)
                .build();

        // 6. Invoice item'larındaki invoice ilişkisini güncelle
        invoiceItemList.forEach(item -> item.setInvoice(invoice));

        // 7. Invoice'ı kaydet
        invoiceRepository.save(invoice);
        invoiceItemRepository.saveAll(invoiceItemList);
    }


}
