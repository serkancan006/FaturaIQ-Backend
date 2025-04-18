package com.example.FaturaIQ.entities;

import com.example.FaturaIQ.entities.enums.InvoiceStatus;
import com.example.FaturaIQ.entities.enums.InvoiceType;
import com.example.FaturaIQ.entities.enums.ScenarioType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Invoices")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String invoiceNumber;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoiceDate;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;
    @Enumerated(EnumType.STRING)
    private InvoiceType InvoiceType ;
    @Enumerated(EnumType.STRING)
    private ScenarioType ScenarioType ;
    @Column
    private Float totalAmount; //Toplam tutar (ürün/hizmet fiyatları + vergi ve diğer eklemeler).
    @Column
    private Float kdvRate; // KDV oranı (yüzde).
    @Column
    private Float kdvAmount; // KDV tutarı (toplam tutarın KDV'si).
    @Column
    private Float withholdingRate; //  Stopaj oranı (yüzde).
    @Column
    private Float discountRate; //  İndirim oranı (yüzde).
    @Column
    private Float netAmount; // İndirim, stopaj ve KDV'yi içeren son ödeme tutarı (net tutar).

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "sender_company_id")
    private Company sender; // Gönderen şirket

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "receiver_company_id")
    private Company receiver; // Alıcı şirket

    @JsonManagedReference
    @OneToMany(mappedBy = "invoice")
    private List<InvoiceItem> items;
}
