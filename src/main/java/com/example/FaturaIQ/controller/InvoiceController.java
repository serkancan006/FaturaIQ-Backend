package com.example.FaturaIQ.controller;

import com.example.FaturaIQ.dtos.invoice.CreateInvoice;
import com.example.FaturaIQ.entities.Invoice;
import com.example.FaturaIQ.entities.User;
import com.example.FaturaIQ.services.abstracts.InvoiceService;
import com.example.FaturaIQ.services.abstracts.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Invoices")
@AllArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-invoices")
    public ResponseEntity<List<Invoice>> getInvoices(){
        return ResponseEntity.ok(invoiceService.getInvoices());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/get-sent-invoices")
    public ResponseEntity<List<Invoice>> getSentInvoices(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userAuth = (User) authentication.getPrincipal();

        return ResponseEntity.ok(invoiceService.sentInvoices(userAuth.getCompany().getTaxNumber()));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/get-receiver-invoices")
    public ResponseEntity<List<Invoice>> getReceiverInvoices(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.getUserByUsername(userName);

        return ResponseEntity.ok(invoiceService.receiverInvoices(user.getCompany().getTaxNumber()));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/create-invoice")
    public ResponseEntity<Void> createInvoice(@RequestBody CreateInvoice createInvoice){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userAuth = (User) authentication.getPrincipal();

        invoiceService.createInvoice(createInvoice, userAuth.getCompany().getTaxNumber());
        return ResponseEntity.ok().build();
    }
}
