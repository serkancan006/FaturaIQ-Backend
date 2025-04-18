package com.example.FaturaIQ.dtos.invoice;

import com.example.FaturaIQ.entities.enums.InvoiceType;
import com.example.FaturaIQ.entities.enums.ScenarioType;
import lombok.Data;
import java.util.List;

@Data
public class CreateInvoice {
    private InvoiceType InvoiceType ;
    private ScenarioType ScenarioType ;
    private Float kdvRate;
    private Float withholdingRate;
    private Float discountRate;

    private String receiverTaxNumber; // Alıcı şirket


    private List<CreateInvoiceItem> items;

    @Data
    public static class CreateInvoiceItem{
        private String name;
        private String description;
        private Float quantity;
        private Float unitPrice;
    }
}


