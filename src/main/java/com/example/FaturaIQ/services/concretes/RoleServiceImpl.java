package com.example.FaturaIQ.services.concretes;

import com.example.FaturaIQ.dtos.role.CreateRole;
import com.example.FaturaIQ.entities.Role;
import com.example.FaturaIQ.repositories.RoleRepository;
import com.example.FaturaIQ.repositories.UserRepository;
import com.example.FaturaIQ.services.abstracts.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public void createRole(CreateRole createRole){
        Role role = Role.builder()
                        .name(createRole.getName())
                        .build();
        roleRepository.save(role);
    }
}
