package com.example.FaturaIQ.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Companies")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String taxNumber;
    @Column
    private String email;
    @Column
    private String address;
    @Column
    private String phone;

    @JsonManagedReference
    @OneToMany(mappedBy = "sender")
    private List<Invoice> sentInvoices; // Şirketin gönderdiği faturalar
    
    @JsonManagedReference
    @OneToMany(mappedBy = "receiver")
    private List<Invoice> receivedInvoices; // Şirketin aldığı faturalar

    @JsonManagedReference
    @OneToMany(mappedBy = "company")
    private List<User> users;
}
