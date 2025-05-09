package com.example.FaturaIQ.services.concretes;

import com.example.FaturaIQ.dtos.user.CreateUser;
import com.example.FaturaIQ.dtos.user.UserFilterCretionDto;
import com.example.FaturaIQ.entities.Company;
import com.example.FaturaIQ.entities.Role;
import com.example.FaturaIQ.entities.User;
import com.example.FaturaIQ.exceptions.CompanyNotFoundException;
import com.example.FaturaIQ.exceptions.RoleException;
import com.example.FaturaIQ.exceptions.UserException;
import com.example.FaturaIQ.exceptions.UserNotFoundException;
import com.example.FaturaIQ.repositories.CompanyRepository;
import com.example.FaturaIQ.repositories.RoleRepository;
import com.example.FaturaIQ.repositories.Spesifications.UserSpecification;
import com.example.FaturaIQ.repositories.UserRepository;
import com.example.FaturaIQ.services.abstracts.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void createUser(CreateUser createUser) {
        if (userRepository.existsByUsername(createUser.getUsername())) {
            throw new UserException("Bu kullanıcı adı zaten mevcut.");
        }

        if (userRepository.existsByEmail(createUser.getEmail())) {
            throw new UserException("Bu e-posta adresi zaten kullanımda.");
        }

        Company company = companyRepository.findById(createUser.getCompanyId()).orElseThrow(CompanyNotFoundException::new);
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RoleException("User rolü bulunamadı"));
        User user = User.builder()
                .username(createUser.getUsername())
                .email(createUser.getEmail())
                .firstName(createUser.getFirstName())
                .lastName(createUser.getLastName())
                .company(company)
                .isActive(true)
                .credentialsNonExpired(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .isEnabled(true)
                .roles(List.of(role))
                .build();
        user.setPassword(passwordEncoder.encode(createUser.getPassword()));
        this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return this.userRepository.findAll().stream().toList();
    }

    @Override
    public List<User> getAllUserByCretion(UserFilterCretionDto request) {
        Pageable pageable = PageRequest.of(request.getPage() != null ? request.getPage() : 0,
                request.getSize() != null ? request.getSize() : 10);

        Specification<User> spec = Specification
                .where(request.getUsername() != null ? UserSpecification.hasUsername(request.getUsername()) : null)
                .and(request.getEmail() != null ? UserSpecification.hasEmail(request.getEmail()) : null);

        return this.userRepository.findAll(spec, pageable).stream().toList();
    }

    @Override
    public User getUserByUsername(String userName) {
        return this.userRepository.findByUsername(userName).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(UserNotFoundException::new);
    }
}
