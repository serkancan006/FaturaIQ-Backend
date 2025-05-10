package com.example.FaturaIQ.controller;

import com.example.FaturaIQ.entities.*;
import com.example.FaturaIQ.entities.enums.InvoiceStatus;
import com.example.FaturaIQ.entities.enums.InvoiceType;
import com.example.FaturaIQ.entities.enums.ScenarioType;
import com.example.FaturaIQ.services.abstracts.InvoiceService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InvoiceService invoiceService;

    @Test
    @WithMockUser(username = "serkancan", roles = {"ADMIN"})
    void testGetInvoicesReturn200() throws Exception {
        // Mock veri oluşturuluyor
        Invoice invoice = Invoice.builder()
                .id(1)
                .InvoiceType(InvoiceType.SATIS)
                .ScenarioType(ScenarioType.Ticari)
                .discountRate(0.05f)
                .invoiceDate(new Date())
                .invoiceNumber("ca597ced-d574-43a3-b9d2-304c89904ba6")
                .invoiceStatus(InvoiceStatus.BEKLIYOR)
                .kdvAmount(4000f)
                .kdvRate(0.2f)
                .netAmount(21800f)
                .totalAmount(20000f)
                .withholdingRate(0.05f)
                .build();

        // Mock servis çağrısı ayarlanıyor
        when(invoiceService.getInvoices()).thenReturn(Arrays.asList(invoice));

        // HTTP GET isteği simüle ediliyor ve sonuç kontrol ediliyor
        mockMvc.perform(get("/api/Invoices/get-invoices")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 200 OK
                .andExpect(jsonPath("$[0].id", is(invoice.getId()))) // JSON veri kontrolü
                .andDo(print()); // Sonucu konsola yazdır

        // Servisin çağrıldığını doğrula
        verify(invoiceService).getInvoices();
    }

    @Test
    @WithMockUser(username = "example", roles = {"USER"})
    void testGetInvoicesReturn403() throws Exception {
        mockMvc.perform(get("/api/Invoices/get-invoices")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void testGetInvoicesReturn401() throws Exception {
        mockMvc.perform(get("/api/Invoices/get-invoices")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void testCreateInvoiceWithRealUserObject() throws Exception {
        // Mock rol ve kullanıcı
        Role role = Role.builder().id(1).name("ROLE_USER").build();

        Company company = new Company();
        company.setTaxNumber("1234567890");

        User user = new User();
        user.setId(1);
        user.setUsername("example");
        user.setRoles(List.of(role));
        user.setCompany(company);

        // Role bilgilerini Spring Security formatına çevir
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        // SecurityContext oluştur ve set et
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        // JSON body
        String jsonBody = """
                {
                  "invoiceType": "SATIS",
                  "scenarioType": "Ticari",
                  "kdvRate": 0.18,
                  "withholdingRate": 0.05,
                  "discountRate": 0.02,
                  "receiverTaxNumber": "1234567890",
                  "items": [{
                      "name": "Ürün A",
                      "description": "Açıklama",
                      "quantity": 2,
                      "unitPrice": 100
                  }]
                }
                """;

        mockMvc.perform(post("/api/Invoices/create-invoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateInvoiceWithMockedUser() throws Exception {
        // Mocklar
        Role role = mock(Role.class);
        when(role.getName()).thenReturn("ROLE_USER");

        Company company = mock(Company.class);
        when(company.getTaxNumber()).thenReturn("1234567890");

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("example");
        when(user.getId()).thenReturn(1);
        when(user.getRoles()).thenReturn(List.of(role));
        when(user.getCompany()).thenReturn(company);

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        String jsonBody = """
                {
                  "invoiceType": "SATIS",
                  "scenarioType": "Ticari",
                  "kdvRate": 0.18,
                  "withholdingRate": 0.05,
                  "discountRate": 0.02,
                  "receiverTaxNumber": "1234567890",
                  "items": [{
                      "name": "Ürün A",
                      "description": "Açıklama",
                      "quantity": 2,
                      "unitPrice": 100
                  }]
                }
                """;

        mockMvc.perform(post("/api/Invoices/create-invoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk());
    }

}
