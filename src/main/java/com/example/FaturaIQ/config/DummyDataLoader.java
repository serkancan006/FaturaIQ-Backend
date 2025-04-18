package com.example.FaturaIQ.config;

import com.example.FaturaIQ.entities.Company;
import com.example.FaturaIQ.entities.Role;
import com.example.FaturaIQ.entities.User;
import com.example.FaturaIQ.repositories.CompanyRepository;
import com.example.FaturaIQ.repositories.RoleRepository;
import com.example.FaturaIQ.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@RequiredArgsConstructor
public class DummyDataLoader implements CommandLineRunner {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        // 1. Rol Oluştur
        Role adminRole = Role.builder().name("ROLE_ADMIN").build();
        Role userRole = Role.builder().name("ROLE_USER").build();
        roleRepository.saveAll(List.of(adminRole, userRole));

        // 2. Şirket Oluştur
        Company company1 = Company.builder()
                .name("FaturaIQ A.Ş.")
                .taxNumber("1234567890")
                .email("info@faturaiq.com")
                .address("İstanbul, Türkiye")
                .phone("0212 123 45 67")
                .build();
        Company company2 = Company.builder()
                .name("İnfina Yazılım")

                .taxNumber("9876543210")
                .email("info@infina.com")
                .address("İstanbul, Türkiye")
                .phone("0212 123 45 88")
                .build();
        companyRepository.saveAll(List.of(company1, company2));

        // 3. Kullanıcı Oluştur (şifreyi BCrypt ile şifrele)
        User adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .email("admin@faturaiq.com")
                .firstName("Admin")
                .lastName("User")
                .isActive(true)
                .company(company1)
                .roles(List.of(adminRole))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .build();

        userRepository.save(adminUser);
    }
}
